package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class PotionMetaMockTest
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
	public void testEffectsDefaultEmpty()
	{
		PotionMeta meta = new PotionMetaMock();
		assertFalse(meta.hasCustomEffects());
	}

	@Test
	public void testAddEffect()
	{
		PotionMeta meta = new PotionMetaMock();
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		assertFalse(meta.hasCustomEffect(PotionEffectType.SPEED));

		meta.addCustomEffect(effect, true);

		assertTrue(meta.hasCustomEffects());
		assertTrue(meta.hasCustomEffect(PotionEffectType.SPEED));
	}

	@Test
	public void testOverrideEffect()
	{
		PotionMeta meta = new PotionMetaMock();
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 60, 2);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.SPEED, 60, 1);

		meta.addCustomEffect(effect, true);
		assertTrue(meta.getCustomEffects().get(0) == effect);

		meta.addCustomEffect(effect2, false);
		assertFalse(meta.getCustomEffects().get(0) == effect2);

		meta.addCustomEffect(effect2, true);
		assertFalse(meta.getCustomEffects().get(0) == effect);

		meta.addCustomEffect(effect3, true);
		assertFalse(meta.getCustomEffects().get(0) == effect3);
	}

	@Test
	public void testClearEffects()
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
	public void testRemoveEffect()
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
	public void testEquals()
	{
		PotionMeta meta = new PotionMetaMock();
		assertEquals(meta, meta);
		assertFalse(meta.equals(new ItemMetaMock()));

		PotionMeta meta2 = new PotionMetaMock();
		assertEquals(meta, meta2);

		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 40, 1);

		meta.addCustomEffect(effect, true);
		assertFalse(meta.equals(meta2));

		meta2.addCustomEffect(effect, true);
		assertEquals(meta, meta2);
	}

	@Test
	public void testColor()
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
	public void testPotionData()
	{
		PotionMeta meta = new PotionMetaMock();
		assertNotNull(meta.getBasePotionData());
		assertEquals(PotionType.UNCRAFTABLE, meta.getBasePotionData().getType());

		PotionData data = new PotionData(PotionType.INSTANT_HEAL, false, true);
		meta.setBasePotionData(data);
		assertEquals(data, meta.getBasePotionData());
	}

	@Test
	public void testClone()
	{
		PotionMeta meta = new PotionMetaMock();
		PotionMeta clone = meta.clone();
		assertEquals(meta, clone);
	}
}
