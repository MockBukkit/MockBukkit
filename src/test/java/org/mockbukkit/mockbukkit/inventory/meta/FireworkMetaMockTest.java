package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.inventory.meta.FireworkMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FireworkMetaMockTest
{

	@Test
	void testEffectDefaultNone()
	{
		FireworkMeta meta = new FireworkMetaMock();
		assertTrue(meta.getEffects().isEmpty());
		assertFalse(meta.hasEffects());
		assertEquals(0, meta.getEffectsSize());
	}

	@Test
	void testAddSingleEffect()
	{
		FireworkMeta meta = new FireworkMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();

		assertFalse(meta.hasEffects());

		meta.addEffect(effect);

		assertTrue(meta.hasEffects());
		assertEquals(1, meta.getEffectsSize());
		assertEquals(effect, meta.getEffects().get(0));
	}

	@Test
	void testAddEffectsArray()
	{
		FireworkMeta meta = new FireworkMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();
		FireworkEffect effect2 = FireworkEffect.builder().withColor(Color.RED).with(Type.CREEPER).build();

		assertFalse(meta.hasEffects());

		meta.addEffects(effect, effect2);

		assertTrue(meta.hasEffects());
		assertEquals(2, meta.getEffectsSize());

		assertEquals(effect, meta.getEffects().get(0));
		assertEquals(effect2, meta.getEffects().get(1));
	}

	@Test
	void testAddEffectsIterable()
	{
		FireworkMeta meta = new FireworkMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();
		FireworkEffect effect2 = FireworkEffect.builder().withColor(Color.RED).with(Type.CREEPER).build();

		assertFalse(meta.hasEffects());

		meta.addEffects(Arrays.asList(effect, effect2));

		assertTrue(meta.hasEffects());
		assertEquals(2, meta.getEffectsSize());

		assertEquals(effect, meta.getEffects().get(0));
		assertEquals(effect2, meta.getEffects().get(1));
	}

	@Test
	void testRemoveEffect()
	{
		FireworkMeta meta = new FireworkMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();
		meta.addEffect(effect);

		assertTrue(meta.hasEffects());

		meta.removeEffect(0);
		assertFalse(meta.hasEffects());
		assertEquals(0, meta.getEffectsSize());
	}

	@Test
	void testClearEffects()
	{
		FireworkMeta meta = new FireworkMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();
		meta.addEffect(effect);

		assertTrue(meta.hasEffects());

		meta.clearEffects();
		assertFalse(meta.hasEffects());
		assertEquals(0, meta.getEffectsSize());
	}

	@Test
	void testClone()
	{
		FireworkMeta meta = new FireworkMetaMock();
		FireworkMeta clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void testPower()
	{
		FireworkMeta meta = new FireworkMetaMock();
		meta.setPower(8);
		assertEquals(8, meta.getPower());
	}

	@Test
	void testPowerTooLow()
	{
		FireworkMeta meta = new FireworkMetaMock();
		assertThrows(IllegalArgumentException.class, () -> meta.setPower(-200));
	}

	@Test
	void testPowerTooHigh()
	{
		FireworkMeta meta = new FireworkMetaMock();
		assertThrows(IllegalArgumentException.class, () -> meta.setPower(1024));
	}

}
