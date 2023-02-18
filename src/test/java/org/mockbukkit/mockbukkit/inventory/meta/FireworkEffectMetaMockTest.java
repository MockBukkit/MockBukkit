package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FireworkEffectMetaMockTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testEffectDefaultNone()
	{
		FireworkEffectMeta meta = new FireworkEffectMetaMock();
		assertNull(meta.getEffect());
		assertFalse(meta.hasEffect());
	}

	@Test
	void testSetEffect()
	{
		FireworkEffectMeta meta = new FireworkEffectMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();

		assertFalse(meta.hasEffect());

		meta.setEffect(effect);

		assertTrue(meta.hasEffect());
		assertEquals(effect, meta.getEffect());
	}

	@Test
	void testClone()
	{
		FireworkEffectMeta meta = new FireworkEffectMetaMock();
		FireworkEffectMeta clone = meta.clone();
		assertEquals(meta, clone);
	}

}
