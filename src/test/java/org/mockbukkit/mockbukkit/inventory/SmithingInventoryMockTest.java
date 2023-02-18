package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SmithingInventoryMockTest
{

	private SmithingInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		inventory = new SmithingInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetResultDefault()
	{
		assertNull(inventory.getResult());
	}

	@Test
	void testSetResult()
	{
		ItemStack item = new ItemStack(Material.OAK_BOAT);

		inventory.setResult(item);

		assertEquals(item, inventory.getResult());
	}

	@Test
	void testSetResult_SetsItemInSlot()
	{
		ItemStack item = new ItemStack(Material.OAK_BOAT);

		inventory.setResult(item);

		assertEquals(item, inventory.getItem(0));
	}

}
