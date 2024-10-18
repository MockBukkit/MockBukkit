package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SuspiciousStewMetaMockTest
{

	@Test
	void testEffectsDefaultEmpty()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		assertFalse(meta.hasCustomEffects());
	}

	@Test
	void testAddEffect()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		assertFalse(meta.hasCustomEffect(PotionEffectType.SPEED));

		meta.addCustomEffect(effect, true);

		assertTrue(meta.hasCustomEffects());
		assertTrue(meta.hasCustomEffect(PotionEffectType.SPEED));
	}

	@Test
	void testOverrideEffect()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 60, 2);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.SPEED, 60, 1);

		meta.addCustomEffect(effect, true);
		assertEquals(effect, meta.getCustomEffects().get(0));

		meta.addCustomEffect(effect2, false);
		assertNotEquals(effect2, meta.getCustomEffects().get(0));

		meta.addCustomEffect(effect2, true);
		assertNotEquals(effect, meta.getCustomEffects().get(0));

		meta.addCustomEffect(effect3, true);
		assertNotEquals(effect3, meta.getCustomEffects().get(0));
	}

	@Test
	void testClearEffects()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		assertFalse(meta.clearCustomEffects());

		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		meta.addCustomEffect(effect, true);
		assertTrue(meta.hasCustomEffects());

		assertTrue(meta.clearCustomEffects());
		assertFalse(meta.hasCustomEffects());
	}

	@Test
	void testRemoveEffect()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		assertFalse(meta.clearCustomEffects());

		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.BLINDNESS, 40, 1);

		meta.addCustomEffect(effect, true);
		meta.addCustomEffect(effect2, true);

		assertTrue(meta.removeCustomEffect(PotionEffectType.SPEED));
		assertFalse(meta.hasCustomEffect(PotionEffectType.SPEED));
		assertFalse(meta.removeCustomEffect(PotionEffectType.SPEED));
		assertTrue(meta.hasCustomEffects());
	}

	@Test
	void testEquals()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		assertEquals(meta, meta);
		assertNotEquals(meta, new ItemMetaMock());

		SuspiciousStewMetaMock meta2 = new SuspiciousStewMetaMock();
		assertEquals(meta, meta2);

		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		meta.addCustomEffect(effect, true);
		assertNotEquals(meta, meta2);

		meta2.addCustomEffect(effect, true);
		assertEquals(meta, meta2);
	}

	@Test
	void testClone()
	{
		SuspiciousStewMetaMock meta = new SuspiciousStewMetaMock();
		SuspiciousStewMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

}
