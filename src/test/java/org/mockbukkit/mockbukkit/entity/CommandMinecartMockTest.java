package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class CommandMinecartMockTest
{

	CommandMinecart minecart;
	@MockBukkitInject
	ServerMock server;

	@BeforeEach
	void setUp()
	{
		minecart = new CommandMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void tesTGetCommandDefault()
	{
		assertEquals("", minecart.getCommand());
	}

	@Test
	void testSetCommand()
	{
		minecart.setCommand("say Hello World!");
		assertEquals("say Hello World!", minecart.getCommand());
	}

	@Test
	void testSetCommandNull()
	{
		minecart.setCommand("say Hello World!");
		minecart.setCommand(null);
		assertEquals("", minecart.getCommand());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(Material.COMMAND_BLOCK_MINECART, minecart.getMinecartMaterial());
	}

	@Test
	void testGetSuccessCountDefault()
	{
		assertEquals(0, minecart.getSuccessCount());
	}

	@Test
	void testGetSuccessCount()
	{
		minecart.setSuccessCount(42);
		assertEquals(42, minecart.getSuccessCount());
	}

	@Test
	void testSuccessCountResetWhenChangingCommand()
	{
		minecart.setSuccessCount(42);
		assertEquals(42, minecart.getSuccessCount());
		minecart.setCommand("say Hello World!");
		assertEquals(0, minecart.getSuccessCount());
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
