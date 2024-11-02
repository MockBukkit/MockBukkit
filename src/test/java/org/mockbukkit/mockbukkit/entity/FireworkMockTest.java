package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkMetaMock;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FireworkMockTest
{

	private ServerMock server;
	private World world;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testEntitySpawning()
	{
		Location location = new Location(world, 100, 100, 100);
		Firework firework = (Firework) world.spawnEntity(location, EntityType.FIREWORK_ROCKET);

		// Does our Firework exist in the correct World?
		assertTrue(world.getEntities().contains(firework));

		// Does it have a default FireworkMeta?
		assertNotNull(firework.getFireworkMeta());

		// Is it at the right location?
		assertEquals(location, firework.getLocation());
	}

	@Test
	void testEntityType()
	{
		Firework firework = new FireworkMock(server, UUID.randomUUID());
		assertEquals(EntityType.FIREWORK_ROCKET, firework.getType());
	}

	@Test
	void testSecondConstructor()
	{
		FireworkMeta meta = new FireworkMetaMock();
		meta.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(Color.BLUE).build());

		Firework firework = new FireworkMock(server, UUID.randomUUID(), meta);
		assertEquals(meta, firework.getFireworkMeta());
	}

	@Test
	void testShotAtAngle()
	{
		Firework firework = new FireworkMock(server, UUID.randomUUID());

		// Default should be false
		assertFalse(firework.isShotAtAngle());

		firework.setShotAtAngle(true);
		assertTrue(firework.isShotAtAngle());
	}

	@Test
	void testSetFireworkMeta()
	{
		FireworkMeta meta = new FireworkMetaMock();
		meta.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE).withColor(Color.BLUE).build());

		Firework firework = new FireworkMock(server, UUID.randomUUID());

		assertNotEquals(meta, firework.getFireworkMeta());

		firework.setFireworkMeta(meta);
		assertEquals(meta, firework.getFireworkMeta());
	}

	@Test
	void testSetFireworkMetaNotNull()
	{
		Firework firework = new FireworkMock(server, UUID.randomUUID());
		assertThrows(NullPointerException.class, () -> firework.setFireworkMeta(null));
	}

}
