package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import net.kyori.adventure.util.TriState;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LivingEntityMockTest
{

	private ServerMock server;
	private CowMock livingEntity;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		livingEntity = new CowMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsJumpingDefault()
	{
		assertFalse(livingEntity.isJumping());
	}

	@Test
	void testSetJumping()
	{
		livingEntity.setJumping(true);
		assertTrue(livingEntity.isJumping());
	}

	@Test
	void testGetKillerDefault()
	{
		assertNull(livingEntity.getKiller());
	}

	@Test
	void testSetKiller()
	{
		PlayerMock player = server.addPlayer();

		livingEntity.setKiller(player);
		assertEquals(player, livingEntity.getKiller());
	}

	@Test
	void testSetKillerNullDoesNotThrow()
	{
		livingEntity.setKiller(null);
		assertNull(livingEntity.getKiller());
	}

	@Test
	void testIsInvisibleDefault()
	{
		assertFalse(livingEntity.isInvisible());
	}

	@Test
	void testSetInvisible()
	{
		livingEntity.setInvisible(true);
		assertTrue(livingEntity.isInvisible());
	}

	@Test
	void testSwingMainHand()
	{
		assertDoesNotThrow(() -> livingEntity.swingMainHand());
	}

	@Test
	void testSwingOffHand()
	{
		assertDoesNotThrow(() -> livingEntity.swingOffHand());
	}

	@Test
	void testGetFrictionStateDefault()
	{
		assertEquals(TriState.NOT_SET, livingEntity.getFrictionState());
	}

	@Test
	void testSetFrictionState()
	{
		livingEntity.setFrictionState(TriState.TRUE);
		assertEquals(TriState.TRUE, livingEntity.getFrictionState());
	}

	@Test
	void testSetFrictionStateNullDoesThrow()
	{
		assertThrows(NullPointerException.class, () -> livingEntity.setFrictionState(null));
	}

	@Test
	void testSetLeashHolder()
	{
		WorldMock world = new WorldMock();
		Entity holder = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.CREEPER);
		assertTrue(livingEntity.setLeashHolder(holder));
		assertEquals(holder, livingEntity.getLeashHolder());
		assertTrue(livingEntity.isLeashed());
	}

	@Test
	void testSetLeashHolderNull()
	{
		assertTrue(livingEntity.setLeashHolder(null));
		assertFalse(livingEntity.isLeashed());
	}

	@Test
	void testGetLeashHolderWhenNotLeashed()
	{
		livingEntity.setLeashHolder(null);
		assertThrows(IllegalStateException.class, () -> livingEntity.getLeashHolder());
	}

	@Test
	void testPotionEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, 3, 1);
		assertTrue(livingEntity.addPotionEffect(effect));

		assertTrue(livingEntity.hasPotionEffect(effect.getType()));
		assertTrue(livingEntity.getActivePotionEffects().contains(effect));

		assertEquals(effect, livingEntity.getPotionEffect(effect.getType()));

		livingEntity.removePotionEffect(effect.getType());
		assertFalse(livingEntity.hasPotionEffect(effect.getType()));
		assertFalse(livingEntity.getActivePotionEffects().contains(effect));

	}

	@Test
	void clearPotionEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, 5, 1);
		livingEntity.addPotionEffect(effect);
		assertTrue(livingEntity.clearActivePotionEffects());
	}

	@Test
	void testInstantEffect()
	{
		PotionEffect instant = new PotionEffect(PotionEffectType.HEAL, 0, 1);
		assertTrue(livingEntity.addPotionEffect(instant));
		assertFalse(livingEntity.hasPotionEffect(instant.getType()));
	}

	@Test
	void testMultiplePotionEffects()
	{
		Collection<PotionEffect> effects = Arrays.asList(new PotionEffect(PotionEffectType.BAD_OMEN, 3, 1),
				new PotionEffect(PotionEffectType.LUCK, 5, 2));

		assertTrue(livingEntity.addPotionEffects(effects));

		for (PotionEffect effect : effects)
		{
			assertTrue(livingEntity.hasPotionEffect(effect.getType()));
		}
	}

}
