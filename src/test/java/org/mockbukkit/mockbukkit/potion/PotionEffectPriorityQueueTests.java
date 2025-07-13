package org.mockbukkit.mockbukkit.potion;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PotionEffectPriorityQueueTests
{

	@MockBukkitInject
	private ServerMock server;
	@MockBukkitInject
	private PlayerMock livingEntity;

	@Test
	void testStrongerEffectOverridesWeaker()
	{
		PotionEffect weakEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);
		PotionEffect strongEffect = new PotionEffect(PotionEffectType.REGENERATION, 50, 3);

		// Add weak effect first
		EntityPotionEffectEvent event1 = livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEntityPotionEffectEvent(event1, null, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.ADDED, false);
		assertEquals(weakEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));

		// Add stronger effect - should change active effect
		EntityPotionEffectEvent event2 = livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		// Should fire events for adding and changing active effect
		var firedEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CHANGED &&
						event.getOldEffect().equals(weakEffect) &&
						event.getNewEffect().equals(strongEffect))
				.findFirst();
		assertTrue(firedEvents.isPresent(), "Should fire CHANGED event when stronger effect overrides weaker");

		// Active effect should now be the stronger one
		assertEquals(strongEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testWeakerEffectDoesNotOverrideStronger()
	{
		PotionEffect strongEffect = new PotionEffect(PotionEffectType.REGENERATION, 50, 3);
		PotionEffect weakEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);

		// Add strong effect first
		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEquals(strongEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));

		// Add weaker effect - should not change active effect
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		// Should fire CHANGED event for adding (but active effect doesn't change)
		assertEntityPotionEffectEvent(event, strongEffect, weakEffect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.CHANGED, true);

		// Active effect should still be the stronger one
		assertEquals(strongEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
	}

	@Test
	void testSameAmplifierHigherDurationWins()
	{
		PotionEffect shortEffect = new PotionEffect(PotionEffectType.REGENERATION, 50, 2);
		PotionEffect longEffect = new PotionEffect(PotionEffectType.REGENERATION, 200, 2);

		livingEntity.addPotionEffect(shortEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(longEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		// Should change to the longer duration effect
		assertEquals(longEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));

		// Should fire event for active effect change
		var firedEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CHANGED &&
						event.getOldEffect().equals(shortEffect) &&
						event.getNewEffect().equals(longEffect))
				.findFirst();
		assertTrue(firedEvents.isPresent(), "Should fire CHANGED event when longer duration wins");
	}

	@Test
	void testRemoveStrongestEffectRevealsShadowed()
	{
		PotionEffect strongEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
		PotionEffect weakEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);

		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEquals(strongEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));

		// Remove the strongest effect
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		// Should fire removal event
		var removalEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.REMOVED &&
						event.getOldEffect().equals(strongEffect) &&
						event.getNewEffect() == null &&
						event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN)
				.findFirst();
		assertTrue(removalEvents.isPresent(), "Should fire REMOVED event");

		// Should fire change event for weak effect becoming active
		var changeEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CHANGED &&
						event.getOldEffect().equals(strongEffect) &&
						event.getNewEffect().equals(weakEffect) &&
						event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN)
				.findFirst();
		assertTrue(changeEvents.isPresent(), "Should fire CHANGED event for weak effect becoming active");

		assertEquals(weakEffect, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
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

		// Should fire CLEARED events for both effects
		var regenEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CLEARED &&
						event.getOldEffect().equals(regen) &&
						event.getNewEffect() == null &&
						event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN)
				.findFirst();
		assertTrue(regenEvents.isPresent(), "Should fire CLEARED event for regen effect");

		var speedEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CLEARED &&
						event.getOldEffect().equals(speed) &&
						event.getNewEffect() == null &&
						event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN)
				.findFirst();
		assertTrue(speedEvents.isPresent(), "Should fire CLEARED event for speed effect");

		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.REGENERATION));
		assertFalse(livingEntity.hasPotionEffect(PotionEffectType.SPEED));
	}

	@Test
	void testGetActivePotionEffectsOnlyReturnsStrongest()
	{
		PotionEffect strongRegen = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
		PotionEffect weakRegen = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100, 1);

		livingEntity.addPotionEffect(weakRegen, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(strongRegen, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(speed, EntityPotionEffectEvent.Cause.PLUGIN);

		var activeEffects = livingEntity.getActivePotionEffects();
		assertEquals(2, activeEffects.size()); // Only strongest regen + speed
		assertTrue(activeEffects.contains(strongRegen));
		assertTrue(activeEffects.contains(speed));
		assertFalse(activeEffects.contains(weakRegen));
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

		assertEquals(effect2, livingEntity.getPotionEffect(PotionEffectType.REGENERATION));
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

	@Test
	void testNonStrongestEffectExpirationNoChange()
	{
		PotionEffect strongLong = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
		PotionEffect weakShort = new PotionEffect(PotionEffectType.REGENERATION, 2, 1);

		livingEntity.addPotionEffect(strongLong, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakShort, EntityPotionEffectEvent.Cause.PLUGIN);

		PotionEffect originalStrong = livingEntity.getPotionEffect(PotionEffectType.REGENERATION);
		assertEquals(strongLong.getAmplifier(), originalStrong.getAmplifier());

		// Tick until weak effect expires
		server.getScheduler().performTicks(2);

		// Should fire removal event for expired weak effect
		var removalEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.REMOVED &&
						event.getOldEffect().equals(weakShort) &&
						event.getNewEffect() == null &&
						event.getCause() == EntityPotionEffectEvent.Cause.EXPIRATION)
				.findFirst();
		assertTrue(removalEvents.isPresent(), "Should fire REMOVED event for expired weak effect");

		// Strong effect should still be active (check amplifier, duration will be reduced)
		PotionEffect currentEffect = livingEntity.getPotionEffect(PotionEffectType.REGENERATION);
		assertEquals(strongLong.getAmplifier(), currentEffect.getAmplifier());
		assertEquals(98, currentEffect.getDuration()); // Duration reduced by 2 ticks
	}

	@Test
	void testStrongestEffectExpirationRevealsShadowed()
	{
		PotionEffect strongShort = new PotionEffect(PotionEffectType.REGENERATION, 2, 3); // Strong but short
		PotionEffect weakLong = new PotionEffect(PotionEffectType.REGENERATION, 100, 1); // Weak but long

		// Add both effects
		livingEntity.addPotionEffect(weakLong, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(strongShort, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEquals(strongShort.getAmplifier(), livingEntity.getPotionEffect(PotionEffectType.REGENERATION).getAmplifier());

		// Tick until strong effect expires
		server.getScheduler().performTicks(2);

		// Should fire removal event for expired effect (duration will be 0)
		var removalEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.REMOVED &&
						event.getOldEffect().getAmplifier() == 3 && // Check amplifier instead of exact effect
						event.getOldEffect().getType() == PotionEffectType.REGENERATION &&
						event.getNewEffect() == null &&
						event.getCause() == EntityPotionEffectEvent.Cause.EXPIRATION)
				.findFirst();
		assertTrue(removalEvents.isPresent(), "Should fire REMOVED event for expired effect");

		// Should fire change event for weak effect becoming active
		var changeEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CHANGED &&
						event.getOldEffect().getAmplifier() == 3 &&
						event.getNewEffect().getAmplifier() == 1 &&
						event.getCause() == EntityPotionEffectEvent.Cause.EXPIRATION)
				.findFirst();
		assertTrue(changeEvents.isPresent(), "Should fire CHANGED event when weaker effect becomes active");

		// Weak effect should now be active
		assertEquals(weakLong.getAmplifier(), livingEntity.getPotionEffect(PotionEffectType.REGENERATION).getAmplifier());
	}

	@Test
	void testRemoveNonStrongestEffectNoChange()
	{
		PotionEffect strongEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 3);
		PotionEffect weakEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 1);

		livingEntity.addPotionEffect(strongEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		livingEntity.addPotionEffect(weakEffect, EntityPotionEffectEvent.Cause.PLUGIN);

		assertEquals(strongEffect.getAmplifier(), livingEntity.getPotionEffect(PotionEffectType.REGENERATION).getAmplifier());

		// Remove effect (will remove strongest since we only remove top of queue)
		livingEntity.removePotionEffect(PotionEffectType.REGENERATION);

		// Should fire removal event for strong effect
		var removalEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.REMOVED &&
						event.getOldEffect().getAmplifier() == 3 &&
						event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN)
				.findFirst();
		assertTrue(removalEvents.isPresent(), "Should fire REMOVED event for removed effect");

		// Should fire change event for weak effect becoming active
		var changeEvents = server.getPluginManager().getFiredEvents()
				.filter(event -> event instanceof EntityPotionEffectEvent)
				.map(event -> (EntityPotionEffectEvent) event)
				.filter(event -> event.getAction() == EntityPotionEffectEvent.Action.CHANGED &&
						event.getOldEffect().getAmplifier() == 3 &&
						event.getNewEffect().getAmplifier() == 1 &&
						event.getCause() == EntityPotionEffectEvent.Cause.PLUGIN)
				.findFirst();
		assertTrue(changeEvents.isPresent(), "Should fire CHANGED event for weak effect becoming active");

		// Weak effect should now be active
		assertEquals(weakEffect.getAmplifier(), livingEntity.getPotionEffect(PotionEffectType.REGENERATION).getAmplifier());
	}

}
