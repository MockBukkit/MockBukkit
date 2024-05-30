package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class AreaEffectCloudMockTest
{

	@MockBukkitInject
	ServerMock server;
	AreaEffectCloud areaEffectCloud;

	@BeforeEach
	void setUp()
	{
		areaEffectCloud = new AreaEffectCloudMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.AREA_EFFECT_CLOUD, areaEffectCloud.getType());
	}

	@Test
	void testGetDuration()
	{
		assertEquals(600, areaEffectCloud.getDuration());
	}

	@Test
	void testSetDuration()
	{
		areaEffectCloud.setDuration(20);
		assertEquals(20, areaEffectCloud.getDuration());
	}

	@Test
	void testGetWaitTime()
	{
		assertEquals(20, areaEffectCloud.getWaitTime());
	}

	@Test
	void testSetWaitTime()
	{
		areaEffectCloud.setWaitTime(10);
		assertEquals(10, areaEffectCloud.getWaitTime());
	}

	@Test
	void testGetReapplicationDelay()
	{
		assertEquals(20, areaEffectCloud.getReapplicationDelay());
	}

	@Test
	void testSetReapplicationDelay()
	{
		areaEffectCloud.setReapplicationDelay(10);
		assertEquals(10, areaEffectCloud.getReapplicationDelay());
	}

	@Test
	void testGetRadius()
	{
		assertEquals(3.0f, areaEffectCloud.getRadius());
	}

	@Test
	void testSetRadius()
	{
		areaEffectCloud.setRadius(10);
		assertEquals(10, areaEffectCloud.getRadius());
	}

	@Test
	void testGetRadiusOnUse()
	{
		assertEquals(0.0f, areaEffectCloud.getRadiusOnUse());
	}

	@Test
	void testSetRadiusOnUse()
	{
		areaEffectCloud.setRadiusOnUse(10);
		assertEquals(10, areaEffectCloud.getRadiusOnUse());
	}

	@Test
	void testGetRadiusPerTick()
	{
		assertEquals(0.0f, areaEffectCloud.getRadiusPerTick());
	}

	@Test
	void testSetRadiusPerTick()
	{
		areaEffectCloud.setRadiusPerTick(10);
		assertEquals(10, areaEffectCloud.getRadiusPerTick());
	}

	@Test
	void testGetDurationOnUse()
	{
		assertEquals(0, areaEffectCloud.getDurationOnUse());
	}

	@Test
	void testSetDurationOnUse()
	{
		areaEffectCloud.setDurationOnUse(10);
		assertEquals(10, areaEffectCloud.getDurationOnUse());
	}

	@Test
	void testGetParticle()
	{
		assertEquals(Particle.SPELL_MOB, areaEffectCloud.getParticle());
	}

	@Test
	void testSetParticle()
	{
		areaEffectCloud.setParticle(Particle.SPELL_WITCH);
		assertEquals(Particle.SPELL_WITCH, areaEffectCloud.getParticle());
	}

	@Test
	void testSetParticleNull()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			areaEffectCloud.setParticle(null);
		});

		assertEquals("Particle cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testSetParticleWithData()
	{
		areaEffectCloud.setParticle(Particle.CLOUD, null);
		assertEquals(Particle.CLOUD, areaEffectCloud.getParticle());

	}

	@Test
	void testGetBasePotionData()
	{
		assertEquals(new PotionData(PotionType.UNCRAFTABLE), areaEffectCloud.getBasePotionData());
	}

	@Test
	void testSetBasePotionData()
	{
		areaEffectCloud.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		assertEquals(new PotionData(PotionType.INSTANT_HEAL), areaEffectCloud.getBasePotionData());
	}

	@Test
	void testGetCustomEffects()
	{
		assertEquals(0, areaEffectCloud.getCustomEffects().size());
	}

	@Test
	void testAddCustomEffect()
	{
		assertTrue(areaEffectCloud.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1, 1), true));
		assertEquals(1, areaEffectCloud.getCustomEffects().size());
	}

	@Test
	void testHasCustomEffect()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1, 1);
		areaEffectCloud.addCustomEffect(effect, true);
		assertTrue(areaEffectCloud.hasCustomEffect(effect.getType()));
	}

	@Test
	void testHasCustomEffectEmpty()
	{
		assertTrue(areaEffectCloud.getCustomEffects().isEmpty());
		assertFalse(areaEffectCloud.hasCustomEffect(PotionEffectType.ABSORPTION));
	}

	@Test
	void testHasCustomEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1, 1);
		areaEffectCloud.addCustomEffect(effect, true);
		assertTrue(areaEffectCloud.hasCustomEffects());
	}

	@Test
	void testHasCustomEffectsEmpty()
	{
		assertTrue(areaEffectCloud.getCustomEffects().isEmpty());
		assertFalse(areaEffectCloud.hasCustomEffects());
	}

	@Test
	void testHasCustomEffectWithBasePotionDataWithEffect()
	{
		areaEffectCloud.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1, 1);
		assertTrue(areaEffectCloud.addCustomEffect(effect, true));
	}

	@Test
	void testHasCustomEffectWithBasePotionDataWithoutEffectAndMatchingInCustom()
	{
		areaEffectCloud.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		PotionEffect effect = new PotionEffect(PotionEffectType.HEAL, 1, 1);
		assertFalse(areaEffectCloud.hasCustomEffects());
	}

	@Test
	void testAddCustomEffectWithBasePotionDataWithEffect()
	{
		areaEffectCloud.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		PotionEffect effect = new PotionEffect(PotionEffectType.HEAL, 1, 1);
		assertTrue(areaEffectCloud.addCustomEffect(effect, true));
		PotionEffect effect2 = new PotionEffect(PotionEffectType.HEAL, 1, 1);
		assertTrue(areaEffectCloud.addCustomEffect(effect2, true));
		assertNotSame(effect, areaEffectCloud.getCustomEffects().get(0));
	}

	@Test
	void testAddCustomEffectWithBasePotionDataWithEffectOverrideFalse()
	{
		areaEffectCloud.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
		PotionEffect effect = new PotionEffect(PotionEffectType.HEAL, 1, 1);
		assertTrue(areaEffectCloud.addCustomEffect(effect, true));
		PotionEffect effect2 = new PotionEffect(PotionEffectType.HEAL, 1, 1);
		assertFalse(areaEffectCloud.addCustomEffect(effect2, false));
		assertSame(effect, areaEffectCloud.getCustomEffects().get(0));
	}

	@Test
	void testRemoveCustomEffect()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1, 1);
		areaEffectCloud.addCustomEffect(effect, true);
		assertTrue(areaEffectCloud.removeCustomEffect(effect.getType()));
	}

	@Test
	void testRemoveCustomEffectEmpty()
	{
		assertFalse(areaEffectCloud.removeCustomEffect(PotionEffectType.ABSORPTION));
	}

	@Test
	void testClearCustomEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1, 1);
		areaEffectCloud.addCustomEffect(effect, true);
		assertEquals(1, areaEffectCloud.getCustomEffects().size());
		areaEffectCloud.clearCustomEffects();
		assertTrue(areaEffectCloud.getCustomEffects().isEmpty());
	}

	@Test
	void testGetColor()
	{
		assertEquals(Color.fromRGB(0), areaEffectCloud.getColor());
	}

	@Test
	void testSetColor()
	{
		areaEffectCloud.setColor(Color.fromRGB(1));
		assertEquals(Color.fromRGB(1), areaEffectCloud.getColor());
	}

	@Test
	void testSetColorNullThrows()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			areaEffectCloud.setColor(null);
		});

		assertEquals("Color cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetSource()
	{
		assertNull(areaEffectCloud.getSource());
	}

	@Test
	void testSetSource()
	{
		PlayerMock player = server.addPlayer();
		areaEffectCloud.setSource(player);
		assertEquals(player, areaEffectCloud.getSource());
	}

	@Test
	void testSetSourceNull()
	{
		PlayerMock player = server.addPlayer();
		areaEffectCloud.setSource(player);
		areaEffectCloud.setSource(null);
		assertNull(areaEffectCloud.getSource());
	}

	@Test
	void testGetOwnerUUID()
	{
		assertNull(areaEffectCloud.getOwnerUniqueId());
	}

	@Test
	void testSetOwnerUUID()
	{
		UUID uuid = UUID.randomUUID();
		areaEffectCloud.setOwnerUniqueId(uuid);
		assertEquals(uuid, areaEffectCloud.getOwnerUniqueId());
	}

}
