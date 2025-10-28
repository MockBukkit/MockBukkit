package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.minecart.StorageMinecart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import static org.bukkit.entity.EntityType.CHEST_MINECART;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class StorageMinecartMockTest
{

	@MockBukkitInject
	private StorageMinecart minecart;

	@Test
	void testGetInventory()
	{
		minecart.getInventory().setItem(0, new ItemStackMock(Material.DIRT));
		assertEquals(Material.DIRT, minecart.getInventory().getItem(0).getType());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(Material.CHEST_MINECART, minecart.getMinecartMaterial());
	}

	@Test
	void testGetEntity()
	{
		assertEquals(minecart, minecart.getEntity());
	}

	@Test
	void testGetType()
	{
		assertEquals(CHEST_MINECART, minecart.getType());
	}

}
