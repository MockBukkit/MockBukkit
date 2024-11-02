package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.minecart.StorageMinecart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.bukkit.entity.EntityType.CHEST_MINECART;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class StorageMinecartMockTest
{

	@MockBukkitInject
	ServerMock server;
	StorageMinecart minecart;

	@BeforeEach
	void setUp() throws Exception
	{
		minecart = new StorageMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testGetInventory()
	{
		minecart.getInventory().setItem(0, new ItemStackMock(Material.DIRT));
		assertEquals(Material.DIRT, minecart.getInventory().getItem(0).getType());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.CHEST_MINECART);
	}

	@Test
	void testGetEntity()
	{
		assertEquals(minecart, minecart.getEntity());
	}

	@Test
	void testGetType()
	{
		assertEquals(minecart.getType(), CHEST_MINECART);
	}

}
