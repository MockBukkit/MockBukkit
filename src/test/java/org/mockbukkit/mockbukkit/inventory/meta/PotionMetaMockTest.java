package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PotionMetaMockTest
{

	@Test
	void testEffectsDefaultEmpty()
	{
		PotionMeta meta = new PotionMetaMock();
		assertFalse(meta.hasCustomEffects());
	}

	@Test
	void testAddEffect()
	{
		PotionMeta meta = new PotionMetaMock();
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		assertFalse(meta.hasCustomEffect(PotionEffectType.SPEED));

		meta.addCustomEffect(effect, true);

		assertTrue(meta.hasCustomEffects());
		assertTrue(meta.hasCustomEffect(PotionEffectType.SPEED));
	}

	@Test
	void testOverrideEffect()
	{
		PotionMeta meta = new PotionMetaMock();
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
		PotionMeta meta = new PotionMetaMock();
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
		PotionMeta meta = new PotionMetaMock();
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
		PotionMeta meta = new PotionMetaMock();
		assertEquals(meta, meta);
		assertNotEquals(meta, new ItemMetaMock());

		PotionMeta meta2 = new PotionMetaMock();
		assertEquals(meta, meta2);

		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		meta.addCustomEffect(effect, true);
		assertNotEquals(meta, meta2);

		meta2.addCustomEffect(effect, true);
		assertEquals(meta, meta2);
	}

	@Test
	void testColor()
	{
		PotionMeta meta = new PotionMetaMock();

		assertFalse(meta.hasColor());
		assertNull(meta.getColor());

		meta.setColor(Color.FUCHSIA);
		assertTrue(meta.hasColor());
		assertEquals(Color.FUCHSIA, meta.getColor());

		meta.setColor(null);
		assertFalse(meta.hasColor());
		assertNull(meta.getColor());
	}

	@Test
	void testClone()
	{
		PotionMeta meta = new PotionMetaMock();
		PotionMeta clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void testBasePotionType()
	{
		PotionMeta meta = new PotionMetaMock();
		assertNull(meta.getBasePotionType());

		meta.setBasePotionType(PotionType.HEALING);
		assertEquals(PotionType.HEALING, meta.getBasePotionType());

		meta.setBasePotionType(null);
		assertNull(meta.getBasePotionType());
	}

	@Test
	void setCustomPotionName()
	{
		PotionMeta meta = new PotionMetaMock();
		assertNull(meta.getCustomPotionName());
		assertFalse(meta.hasCustomPotionName());

		meta.setCustomPotionName("Custom potion name");
		assertEquals("Custom potion name", meta.getCustomPotionName());
		assertTrue(meta.hasCustomPotionName());
	}

}
