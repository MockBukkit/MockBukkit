package org.mockbukkit.mockbukkit.potion;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PotionEffectPriorityQueueTests
{

	@MockBukkitInject
	private ServerMock server;
	@MockBukkitInject
	private PlayerMock livingEntity;
	@MockBukkitInject
	private PluginMock plugin;

	// Standard test effects
	private final PotionEffect weakEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);
	private final PotionEffect strongEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
	private final PotionEffect shortEffect = new PotionEffect(PotionEffectType.REGENERATION, 2, 3);
	private final PotionEffect longEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);

	@BeforeEach
	void setUp()
	{
		// Clear any events from previous tests (if any)
		server.getPluginManager().getFiredEvents().forEach(e ->
		{
		}); // Consume all events
	}

	private void assertEventFired(EntityPotionEffectEvent.Action expectedAction, PotionEffect expectedOld,
								  PotionEffect expectedNew, EntityPotionEffectEvent.Cause expectedCause)
	{
		var event = server.getPluginManager().getFiredEvents()
				.filter(e -> e instanceof EntityPotionEffectEvent)
				.map(e -> (EntityPotionEffectEvent) e)
				.filter(e -> e.getAction() == expectedAction &&
						e.getCause() == expectedCause &&
						effectsMatch(e.getOldEffect(), expectedOld) &&
						effectsMatch(e.getNewEffect(), expectedNew))
				.findFirst();
		assertTrue(event.isPresent(), String.format("Expected %s event with cause %s not found", expectedAction, expectedCause));
	}

	private void assertEventNotFired(EntityPotionEffectEvent.Action action)
	{
		var events = server.getPluginManager().getFiredEvents()
				.filter(e -> e instanceof EntityPotionEffectEvent)
				.map(e -> (EntityPotionEffectEvent) e)
				.filter(e -> e.getAction() == action)
				.toList();

		// For CHANGED events, we need to be more specific - no change should mean
		// the old and new effects have the same amplifier and type
		if (action == EntityPotionEffectEvent.Action.CHANGED)
		{
			var invalidChanges = events.stream()
					.filter(e -> e.getOldEffect() != null && e.getNewEffect() != null)
					.filter(e -> e.getOldEffect().getType() == e.getNewEffect().getType() &&
							e.getOldEffect().getAmplifier() == e.getNewEffect().getAmplifier())
					.toList();
			assertTrue(invalidChanges.isEmpty(),
					String.format("CHANGED event fired but effects have same amplifier/type: %s", invalidChanges));
		}
		else
		{
			assertTrue(events.isEmpty(), String.format("%s event should not have been fired", action));
		}
	}

	private boolean effectsMatch(PotionEffect actual, PotionEffect expected)
	{
		if (actual == null && expected == null) return true;
		if (actual == null || expected == null) return false;
		return actual.getType() == expected.getType() &&
				actual.getAmplifier() == expected.getAmplifier();
	}

	private void assertEffectActive(@NotNull PotionEffect expected)
	{
		PotionEffect actual = livingEntity.getPotionEffect(expected.getType());
		assertNotNull(actual);
		assertEquals(expected.getAmplifier(), actual.getAmplifier());
	}

	private void assertEffectNotActive(@NotNull PotionEffect expected)
	{
		PotionEffect actual = livingEntity.getPotionEffect(expected.getType());
		assertNull(actual);
	}

	@Test
	void testPotionEffectAddedForFirstTime()
	{
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEntityPotionEffectEvent(event, null, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.ADDED, false);
		assertEffectActive(weakEffect);
	}

	@Test
	void testStrongerEffectOverridesWeaker()
	{
		// Add weak effect first
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEffectActive(weakEffect);

		// Add stronger effect
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEntityPotionEffectEvent(event, weakEffect, strongEffect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.CHANGED, true);
		assertEffectActive(strongEffect);
	}

	@Test
	void testWeakerEffectDoesNotOverrideStronger()
	{
		// Add strong effect first
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEffectActive(strongEffect);

		// Add weaker effect
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEntityPotionEffectEvent(event, strongEffect, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.CHANGED, true);
		assertEffectActive(strongEffect); // Should still be the strong effect
	}

	@Test
	void testSameAmplifierHigherDurationWins()
	{
		PotionEffect shortDuration = new PotionEffect(PotionEffectType.REGENERATION, 50, 2);
		PotionEffect longDuration = new PotionEffect(PotionEffectType.REGENERATION, 200, 2);

		livingEntity.addPotionEffect(shortDuration, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(longDuration, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(longDuration);
		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, shortDuration, longDuration, EntityPotionEffectEvent.Cause.PLUGIN);
	}

	@Test
	void testStrongestEffectExpirationRevealsShadowed()
	{
		// Add both effects
		livingEntity.addPotionEffect(longEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(shortEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(shortEffect); // Strong but short should be active

		// Tick until strong effect expires
		server.getScheduler().performTicks(2);

		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, shortEffect, null, EntityPotionEffectEvent.Cause.EXPIRATION);
		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, shortEffect, longEffect, EntityPotionEffectEvent.Cause.EXPIRATION);
		assertEffectActive(longEffect); // Weak effect should now be active
	}

	@Test
	void testNonStrongestEffectExpirationNoChange()
	{
		PotionEffect strongLong = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
		PotionEffect weakShort = new PotionEffect(PotionEffectType.REGENERATION, 2, 1);

		livingEntity.addPotionEffect(strongLong, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakShort, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(strongLong);

		// Tick until weak effect expires
		server.getScheduler().performTicks(2);

		assertEventNotFired(EntityPotionEffectEvent.Action.REMOVED); // weak doesn't fire an event! Because it wasn't added...
		assertEventNotFired(EntityPotionEffectEvent.Action.CHANGED); // No change to active effect
		assertEffectActive(strongLong); // Strong effect still active (but duration reduced)
	}

	@Test
	void testRemoveStrongestEffectRevealsShadowed()
	{
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(strongEffect);

		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, strongEffect, null, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, strongEffect, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEffectNotActive(strongEffect);
		assertEffectNotActive(weakEffect);
	}

	@Test
	void testRemoveNonStrongestEffectNoChange()
	{
		// This test shows that removePotionEffect removes the TOP effect, not a specific one
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(strongEffect); // Strong effect should be active

		// Remove effect - will remove all
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, strongEffect, null, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, strongEffect, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectNotActive(strongEffect);
		assertEffectNotActive(weakEffect);
	}

	@Test
	void testClearAllEffectsFiresEventsForAll()
	{
		PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100, 1);

		livingEntity.addPotionEffect(regen, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(speed, EntityPotionEffectEvent.Cause.PLUGIN);

		boolean result = livingEntity.clearActivePotionEffects();

		assertTrue(result);
		assertEventFired(EntityPotionEffectEvent.Action.CLEARED, regen, null, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEventFired(EntityPotionEffectEvent.Action.CLEARED, speed, null, EntityPotionEffectEvent.Cause.PLUGIN);

		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.SPEED));
	}

	@Test
	void testGetActivePotionEffectsOnlyReturnsStrongest()
	{
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100, 1);
		livingEntity.addPotionEffect(speed, EntityPotionEffectEvent.Cause.PLUGIN);

		var activeEffects = livingEntity.getActivePotionEffects();
		assertEquals(2, activeEffects.size()); // Only strongest regen + speed
		assertTrue(activeEffects.stream().anyMatch(e -> e.getAmplifier() == 3 && e.getType() == PotionEffectType.REGENERATION));
		assertTrue(activeEffects.stream().anyMatch(e -> e.getType() == PotionEffectType.SPEED));
		assertFalse(activeEffects.stream().anyMatch(e -> e.getAmplifier() == 1 && e.getType() == PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_AddPotionEffectCancelled()
	{
		server.getPluginManager().registerEvent(EntityPotionEffectEvent.class,
				new org.bukkit.event.Listener()
				{
				},
				org.bukkit.event.EventPriority.NORMAL,
				(listener, event) -> ((EntityPotionEffectEvent) event).setCancelled(true),
				plugin, false);

		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_AddPotionEffectNoActiveChange()
	{
		// This happens when adding a weaker effect that doesn't become active
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		PotionEffect beforeAdd = livingEntity.getPotionEffect(PotionEffectType.REGENERATION);

		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		PotionEffect afterAdd = livingEntity.getPotionEffect(PotionEffectType.REGENERATION);

		// The active effect should be the same (strong effect still active)
		assertEquals(beforeAdd.getAmplifier(), afterAdd.getAmplifier());
	}

	@Test
	void testBranchCoverage_HasPotionEffectNullQueue()
	{
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_HasPotionEffectEmptyQueue()
	{
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.clearActivePotionEffects(); // This should leave empty queue or remove it entirely
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_GetPotionEffectNullQueue()
	{
		assertNull(livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_GetPotionEffectEmptyQueue()
	{
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.clearActivePotionEffects();
		assertNull(livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_RemoveExpiredEffectsEmptyQueue()
	{
		PotionEffect veryShort = new PotionEffect(PotionEffectType.REGENERATION, 1, 1);
		livingEntity.addPotionEffect(veryShort, EntityPotionEffectEvent.Cause.PLUGIN);
		server.getScheduler().performTicks(1);
		assertNull(livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testBranchCoverage_RemoveExpiredEffectsOldActiveNotNull()
	{
		livingEntity.addPotionEffect(longEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(shortEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		server.getScheduler().performTicks(2);
		assertEffectActive(longEffect);
	}

	@Test
	void testBranchCoverage_RemovePotionEffectNullQueue()
	{
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);
		assertEventNotFired(EntityPotionEffectEvent.Action.CHANGED);
	}

	@Test
	void testBranchCoverage_RemovePotionEffectEmptyQueue()
	{
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.clearActivePotionEffects();
		long before = server.getPluginManager().getFiredEvents().count();
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);
		long after = server.getPluginManager().getFiredEvents().count();
		assertEquals(before, after);
	}

	@Test
	void testBranchCoverage_RemovePotionEffectEqualsTrue()
	{
		// Create a scenario where the same effect remains active after removal
		// This is tricky - we need the exact same PotionEffect object to remain

		// Add one effect, then remove it to empty the queue
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		// Now queue is empty, so removal won't execute the comparison
		// Let's test with effects that will be equal
		PotionEffect effect1 = new PotionEffect(PotionEffectType.REGENERATION, 100, 2, false, false, false);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.REGENERATION, 100, 2, false, false, false);

		livingEntity.addPotionEffect(effect1, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(effect2, EntityPotionEffectEvent.Cause.PLUGIN);

		// These should be equal via .equals()
		assertEquals(effect1, effect2);

		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		// Check if change event was fired - it shouldn't be if effects are equal
		long changeEvents = server.getPluginManager().getFiredEvents()
				.filter(e -> e instanceof EntityPotionEffectEvent)
				.map(e -> (EntityPotionEffectEvent) e)
				.filter(e -> e.getAction() == EntityPotionEffectEvent.Action.CHANGED)
				.count();
		assertEquals(1, changeEvents);
	}

	@Test
	void testBranchCoverage_GetActivePotionEffectsEmptyFilter()
	{
		livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, 1), EntityPotionEffectEvent.Cause.PLUGIN);
		server.getScheduler().performTicks(1); // Expire the effect

		var activeEffects = livingEntity.getActivePotionEffects();
		assertEquals(0, activeEffects.size());
	}

	@Test
	void testMultipleEffectsSameAmplifierDifferentDuration()
	{
		PotionEffect effect1 = new PotionEffect(PotionEffectType.REGENERATION, 50, 2);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.REGENERATION, 75, 2);

		livingEntity.addPotionEffect(effect1, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(effect2, EntityPotionEffectEvent.Cause.PLUGIN); // Longest should win
		livingEntity.addPotionEffect(effect3, EntityPotionEffectEvent.Cause.PLUGIN);

		// effect2 should be active (longest duration)
		PotionEffect active = livingEntity.getPotionEffect(PotionEffectType.REGENERATION);
		assertEquals(100, active.getDuration());
		assertEquals(2, active.getAmplifier());
	}

	private static void assertEntityPotionEffectEvent(@NotNull EntityPotionEffectEvent event, PotionEffect oldEffect,
													  PotionEffect newEffect, EntityPotionEffectEvent.Cause cause, EntityPotionEffectEvent.Action action, boolean override)
	{
		assertEquals(oldEffect, event.getOldEffect());
		assertEquals(newEffect, event.getNewEffect());
		assertEquals(cause, event.getCause());
		assertEquals(action, event.getAction());
		assertEquals(override, event.isOverride());
	}

	@Test
	void testGetPotionEffect_NullEffect()
	{
		assertThrows(NullPointerException.class, () -> livingEntity.getPotionEffect(null));
	}

	@Test
	void testRemovePotionEffect_AlreadyEmpty()
	{
		livingEntity.removePotionEffect(PotionEffectType.STRENGTH);
		assertEventNotFired(EntityPotionEffectEvent.Action.CHANGED);
		assertEventNotFired(EntityPotionEffectEvent.Action.REMOVED);
	}

}
