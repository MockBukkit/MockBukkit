package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import com.destroystokyo.paper.event.entity.CreeperIgniteEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasFiredEventInstance;

class CreeperMockTest
{

	private CreeperMock creeper;
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		creeper = new CreeperMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.CREEPER, creeper.getType());
	}

	@Test
	void testIsPoweredDefault()
	{
		assertFalse(creeper.isPowered());
	}

	@Test
	void testSetPowered()
	{
		creeper.setPowered(true);
		assertTrue(creeper.isPowered());
		assertThat(server.getPluginManager(), hasFiredEventInstance(CreeperPowerEvent.class));
	}

	@Test
	void testSetPoweredWithCancelledEvent()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			void onCreeperPower(CreeperPowerEvent event)
			{
				event.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		creeper.setPowered(true);
		assertFalse(creeper.isPowered());
	}

	@Test
	void testGetMaxFuseTicksDefault()
	{
		assertEquals(30, creeper.getMaxFuseTicks());
	}

	@Test
	void testSetMaxFuseTicks()
	{
		creeper.setMaxFuseTicks(10);
		assertEquals(10, creeper.getMaxFuseTicks());
	}

	@Test
	void testSetMaxFuseTicksNegativeThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> creeper.setMaxFuseTicks(-1));
	}

	@Test
	void testGetFuseTicksDefault()
	{
		assertEquals(0, creeper.getFuseTicks());
	}

	@Test
	void testSetFuseTicks()
	{
		creeper.setFuseTicks(10);
		assertEquals(10, creeper.getFuseTicks());
	}

	@Test
	void testSetFuseTicksNegativeThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> creeper.setFuseTicks(-1));
	}

	@Test
	void testSetFuseTicksGreaterThanMaxThrows()
	{
		creeper.setMaxFuseTicks(10);
		assertThrows(IllegalArgumentException.class, () -> creeper.setFuseTicks(11));
	}

	@Test
	void testIgnite()
	{
		creeper.ignite();
		assertThat(server.getPluginManager(), hasFiredEventInstance(CreeperIgniteEvent.class));
		assertTrue(creeper.isIgnited());
	}

	@Test
	void testIgniteWithCancelledEvent()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			void onCreeperIgnite(CreeperIgniteEvent event)
			{
				event.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		creeper.ignite();
		assertFalse(creeper.isIgnited());
	}

	@Test
	void testIsIgnitedDefault()
	{
		assertFalse(creeper.isIgnited());
	}

	@Test
	void testIsIgnited()
	{
		creeper.setIgnited(true);
		assertTrue(creeper.isIgnited());
	}

	@Test
	void testIsIgnitedSameValue()
	{
		creeper.setIgnited(true);
		assertDoesNotThrow(() -> creeper.setIgnited(true));
	}

	@Test
	void testGetExplosionRadiusDefault()
	{
		assertEquals(3, creeper.getExplosionRadius());
	}

	@Test
	void testSetExplosionRadius()
	{
		creeper.setExplosionRadius(10);
		assertEquals(10, creeper.getExplosionRadius());
	}

	@Test
	void testSetExplosionRadiusNegativeThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> creeper.setExplosionRadius(-1));
	}

	@Test
	void testExplode()
	{
		creeper.explode();
		assertThat(server.getPluginManager(), hasFiredEventInstance(ExplosionPrimeEvent.class));
		assertTrue(creeper.isDead());
	}

}
