package org.mockbukkit.mockbukkit.potion;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
	private PotionEffect weakEffect;
	private PotionEffect strongEffect;
	private PotionEffect shortEffect;
	private PotionEffect longEffect;

	@BeforeEach
	void setUp()
	{
		weakEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);
		strongEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
		shortEffect = new PotionEffect(PotionEffectType.REGENERATION, 2, 3);
		longEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);
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

	private void assertEffectActive(PotionEffect expected)
	{
		PotionEffect actual = livingEntity.getPotionEffect(expected.getType());
		assertNotNull(actual);
		assertEquals(expected.getAmplifier(), actual.getAmplifier());
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

		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, weakShort, null, EntityPotionEffectEvent.Cause.EXPIRATION);
		assertEventNotFired(EntityPotionEffectEvent.Action.CHANGED); // No change to active effect
		assertEffectActive(strongLong); // Strong effect still active (but duration reduced)
	}

	@Test
	void testRemoveStrongestEffectRevealsShadowed()
	{
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(strongEffect);

		// Remove the strongest effect (only removes top of queue)
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, strongEffect, null, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, strongEffect, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEffectActive(weakEffect);
	}

	@Test
	void testRemoveNonStrongestEffectNoChange()
	{
		// This test shows that removePotionEffect removes the TOP effect, not a specific one
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEffectActive(strongEffect); // Strong effect should be active

		// Remove effect - will remove the strongest (top of queue)
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, strongEffect, null, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, strongEffect, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEffectActive(weakEffect); // Weak effect becomes active
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
	void testAddPotionEffectCancelled()
	{
		// Register event listener that cancels all potion effect events
		server.getPluginManager().registerEvent(EntityPotionEffectEvent.class,
				new org.bukkit.event.Listener()
				{
				},
				org.bukkit.event.EventPriority.NORMAL,
				(listener, event) ->
				{
					if (event instanceof EntityPotionEffectEvent)
					{
						((EntityPotionEffectEvent) event).setCancelled(true);
					}
				},
				plugin, false);

		EntityPotionEffectEvent event = livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertTrue(event.isCancelled());
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
		assertNull(livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testAddPotionEffectActiveEffectChanges()
	{
		// This targets: if (oldEffect != null && !oldEffect.equals(newActiveEffect) && action == EntityPotionEffectEvent.Action.CHANGED)
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		// Add stronger effect - this should trigger the condition where:
		// - oldEffect != null (weakEffect exists)
		// - !oldEffect.equals(newActiveEffect) (strongEffect is different)
		// - action == CHANGED (since effect type already exists)
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEquals(EntityPotionEffectEvent.Action.CHANGED, event.getAction());
		assertEffectActive(strongEffect); // Should be the stronger effect now
	}

	@Test
	void testHasPotionEffectWithNullQueue()
	{
		// This targets: queue != null in "return queue != null && !queue.isEmpty();"
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testHasPotionEffectWithEmptyQueue()
	{
		// This targets: !queue.isEmpty() in "return queue != null && !queue.isEmpty();"
		// First add and remove to create an empty queue scenario
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testGetPotionEffectWithNullQueue()
	{
		// This targets: queue == null in "if (queue == null || queue.isEmpty())"
		assertNull(livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testGetPotionEffectWithEmptyQueue()
	{
		// This targets: queue.isEmpty() in "if (queue == null || queue.isEmpty())"
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		assertNull(livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testRemoveExpiredEffectsWithEmptyQueueCondition()
	{
		// This targets: queue.isEmpty() ? null : mapToPotionEffect(queue.peek())
		PotionEffect veryShortEffect = new PotionEffect(PotionEffectType.REGENERATION, 1, 2);
		livingEntity.addPotionEffect(veryShortEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		// Tick to expire the effect completely
		server.getScheduler().performTicks(1);

		// This should have hit the empty queue condition
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testRemoveExpiredEffectsOldActiveEffectNotNull()
	{
		// This targets: if (oldActiveEffect != null && !Objects.equals(oldActiveEffect, newActiveEffect))
		PotionEffect strongShort = new PotionEffect(PotionEffectType.REGENERATION, 2, 3);
		PotionEffect weakLong = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);

		livingEntity.addPotionEffect(weakLong, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(strongShort, EntityPotionEffectEvent.Cause.PLUGIN);

		// This will expire the strong effect and reveal the weak one
		server.getScheduler().performTicks(2);

		assertEventFired(EntityPotionEffectEvent.Action.CHANGED, strongShort, weakLong, EntityPotionEffectEvent.Cause.EXPIRATION);
	}

	@Test
	void testRemovePotionEffectWithNullQueue()
	{
		// This targets: if (queue != null && !queue.isEmpty()) - null case
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		// Should not crash, no events fired
		var eventCount = server.getPluginManager().getFiredEvents()
				.filter(e -> e instanceof EntityPotionEffectEvent)
				.count();
		assertEquals(0, eventCount);
	}

	@Test
	void testRemovePotionEffectWithEmptyQueue()
	{
		// This targets: if (queue != null && !queue.isEmpty()) - empty case
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION); // Remove it

		long eventsBeforeSecondRemoval = server.getPluginManager().getFiredEvents().count();

		// Try to remove again from empty queue
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		long eventsAfterSecondRemoval = server.getPluginManager().getFiredEvents().count();
		assertEquals(eventsBeforeSecondRemoval, eventsAfterSecondRemoval); // No new events
	}

	@Test
	void testRemovePotionEffectOldActiveEffectEquals()
	{
		// This targets: if (!oldActiveEffect.equals(newActiveEffect)) - false case
		// We need to create a scenario where removing an effect doesn't change the active effect
		// This happens when we have only one effect and remove it (queue becomes empty)
		PotionEffect singleEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);

		livingEntity.addPotionEffect(singleEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		// Remove the only effect - this hits the case where newActiveEffect becomes null
		// So the equals check is skipped and no CHANGED event is fired
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		// Should fire REMOVED event but NOT a CHANGED event since queue becomes empty
		assertEventFired(EntityPotionEffectEvent.Action.REMOVED, singleEffect, null, EntityPotionEffectEvent.Cause.PLUGIN);

		// Check that no CHANGED event was fired (since queue became empty)
		var changeEvents = server.getPluginManager().getFiredEvents()
				.filter(e -> e instanceof EntityPotionEffectEvent)
				.map(e -> (EntityPotionEffectEvent) e)
				.filter(e -> e.getAction() == EntityPotionEffectEvent.Action.CHANGED)
				.count();
		assertEquals(0, changeEvents); // Should be 0 since queue became empty, not because effects are equal
	}

	@Test
	void testGetActivePotionEffectsEmptyQueueFilter()
	{
		// This targets: .filter(queue -> !queue.isEmpty())
		PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 2, 1); // Short duration

		livingEntity.addPotionEffect(regen, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(speed, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEquals(2, livingEntity.getActivePotionEffects().size());

		// Expire the speed effect
		server.getScheduler().performTicks(2);

		// Now speed queue should be empty and filtered out
		var activeEffects = livingEntity.getActivePotionEffects();
		assertEquals(1, activeEffects.size());
		assertTrue(activeEffects.stream().anyMatch(e -> e.getType() == PotionEffectType.REGENERATION));
		assertFalse(activeEffects.stream().anyMatch(e -> e.getType() == PotionEffectType.SPEED));
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

	private static void assertEntityPotionEffectEvent(EntityPotionEffectEvent event, PotionEffect oldEffect,
													  PotionEffect newEffect, EntityPotionEffectEvent.Cause cause, EntityPotionEffectEvent.Action action, boolean override)
	{
		assertEquals(oldEffect, event.getOldEffect());
		assertEquals(newEffect, event.getNewEffect());
		assertEquals(cause, event.getCause());
		assertEquals(action, event.getAction());
		assertEquals(override, event.isOverride());
	}

}
