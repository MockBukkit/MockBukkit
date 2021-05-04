package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class FireworkEffectMetaMockTest
{

	@Before
	public void setUp()
	{
		MockBukkit.mock();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testEffectDefaultNone()
	{
		FireworkEffectMeta meta = new FireworkEffectMetaMock();
		assertNull(meta.getEffect());
		assertFalse(meta.hasEffect());
	}

	@Test
	public void testSetEffect()
	{
		FireworkEffectMeta meta = new FireworkEffectMetaMock();
		FireworkEffect effect = FireworkEffect.builder().withColor(Color.BLUE).with(Type.BALL_LARGE).build();

		assertFalse(meta.hasEffect());

		meta.setEffect(effect);

		assertTrue(meta.hasEffect());
		assertEquals(effect, meta.getEffect());
	}

	@Test
	public void testClone()
	{
		FireworkEffectMeta meta = new FireworkEffectMetaMock();
		FireworkEffectMeta clone = meta.clone();
		assertEquals(meta, clone);
	}
}
