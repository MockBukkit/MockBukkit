package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasFiredEventInstance;

@ExtendWith(MockBukkitExtension.class)
class ExplosiveMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;

	private ExplosiveMinecart minecart;

	@BeforeEach
	void setUp()
	{
		minecart = new ExplosiveMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.TNT_MINECART, minecart.getType());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(Material.TNT_MINECART, minecart.getMinecartMaterial());
	}

	@Test
	void testGetFuseTicksDefault()
	{
		assertEquals(-1, minecart.getFuseTicks());
	}

	@Test
	void testSetFuseTicks()
	{
		minecart.setFuseTicks(80);
		assertEquals(80, minecart.getFuseTicks());
	}

	@Test
	void testIgnite()
	{
		minecart.ignite();
		assertEquals(80, minecart.getFuseTicks());
	}

	@Test
	void testIsIgnited()
	{
		assertFalse(minecart.isIgnited());
		minecart.ignite();
		assertTrue(minecart.isIgnited());
	}

	@Test
	void testExplode()
	{
		minecart.explode();
		assertTrue(minecart.isDead());
		assertThat(server.getPluginManager(), hasFiredEventInstance(ExplosionPrimeEvent.class));
	}

	@Test
	void testExplodePower()
	{
		minecart.explode(2.5f);
		assertTrue(minecart.isDead());
		assertThat(server.getPluginManager(), hasFiredEventInstance(ExplosionPrimeEvent.class));
	}

	@Test
	void testExplodePowerTooSmall()
	{
		assertThrows(IllegalArgumentException.class, () -> minecart.explode(-1.0f));
	}

	@Test
	void testExplodePowerTooBig()
	{
		assertThrows(IllegalArgumentException.class, () -> minecart.explode(6.0f));
	}

}
