package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class SpawnerMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private SpawnerMinecart minecart;

	@BeforeEach
	public void setUp() throws Exception
	{
		minecart = new SpawnerMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.MINECART);
	}

	@Test
	void testGetType()
	{
		assertEquals(minecart.getType(), EntityType.SPAWNER_MINECART);
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = minecart.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.49, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.49, actual.getMinZ());

		assertEquals(0.49, actual.getMaxX());
		assertEquals(0.7, actual.getMaxY());
		assertEquals(0.49, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		minecart.teleport(new Location(minecart.getWorld(), 10, 5, 10));

		BoundingBox actual = minecart.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.51, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.51, actual.getMinZ());

		assertEquals(10.49, actual.getMaxX());
		assertEquals(5.7, actual.getMaxY());
		assertEquals(10.49, actual.getMaxZ());
	}

}
