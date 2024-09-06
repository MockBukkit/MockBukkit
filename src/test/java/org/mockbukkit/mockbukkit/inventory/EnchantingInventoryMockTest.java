package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class EnchantingInventoryMockTest
{

	private EnchantingInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new EnchantingInventoryMock(null);
	}

	@Test
	void setItem()
	{
		inventory.setItem(new ItemStackMock(Material.DIAMOND_SWORD));

		assertNotNull(inventory.getItem());
		assertEquals(Material.DIAMOND_SWORD, inventory.getItem().getType());
	}

	@Test
	void setItem_SetsItemInSlot()
	{
		inventory.setItem(new ItemStackMock(Material.DIAMOND_SWORD));

		assertNotNull(inventory.getItem(0));
		assertEquals(Material.DIAMOND_SWORD, inventory.getItem(0).getType());
	}

	@Test
	void setSecondary()
	{
		inventory.setSecondary(new ItemStackMock(Material.LAPIS_LAZULI));

		assertNotNull(inventory.getSecondary());
		assertEquals(Material.LAPIS_LAZULI, inventory.getSecondary().getType());
	}

	@Test
	void setSecondary_SetsItemInSlot()
	{
		inventory.setSecondary(new ItemStackMock(Material.LAPIS_LAZULI));

		assertNotNull(inventory.getItem(1));
		assertEquals(Material.LAPIS_LAZULI, inventory.getItem(1).getType());
	}

}
