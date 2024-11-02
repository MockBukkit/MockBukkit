package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class SmithingInventoryMockTest
{

	private SmithingInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		inventory = new SmithingInventoryMock(null);
	}

	@Test
	void testGetResultDefault()
	{
		assertNull(inventory.getResult());
	}

	@Test
	void testSetResult()
	{
		ItemStack item = new ItemStackMock(Material.OAK_BOAT);

		inventory.setResult(item);

		assertEquals(item, inventory.getResult());
	}

	@Test
	void testSetResult_SetsItemInSlot()
	{
		ItemStack item = new ItemStackMock(Material.OAK_BOAT);

		inventory.setResult(item);

		assertEquals(item, inventory.getItem(0));
	}

}
