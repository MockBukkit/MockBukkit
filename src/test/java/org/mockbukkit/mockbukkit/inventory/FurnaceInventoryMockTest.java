package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.block.state.BlastFurnaceStateMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class FurnaceInventoryMockTest
{

	private FurnaceInventoryMock inventory;

	private BlastFurnaceStateMock holder;

	@BeforeEach
	void setUp()
	{
		holder = new BlastFurnaceStateMock(Material.BLAST_FURNACE);
		inventory = new FurnaceInventoryMock(holder);
	}

	@Test
	void testGetFuel()
	{
		ItemStack fuel = new ItemStackMock(Material.COAL);
		inventory.setFuel(fuel);
		assertEquals(fuel, inventory.getFuel());
	}

	@Test
	void testSetFuel_SetsItemInSlot()
	{
		ItemStack fuel = new ItemStackMock(Material.COAL);
		inventory.setFuel(fuel);
		assertEquals(fuel, inventory.getItem(1));
	}

	@Test
	void testGetResult()
	{
		ItemStack result = new ItemStackMock(Material.IRON_INGOT);
		inventory.setResult(result);
		assertEquals(result, inventory.getResult());
	}

	@Test
	void testSetResult_SetsItemInSlot()
	{
		ItemStack fuel = new ItemStackMock(Material.IRON_INGOT);
		inventory.setResult(fuel);
		assertEquals(fuel, inventory.getItem(2));
	}

	@Test
	void testGetSmelting()
	{
		ItemStack smelting = new ItemStackMock(Material.IRON_ORE);
		inventory.setSmelting(smelting);
		assertEquals(smelting, inventory.getSmelting());
	}

	@Test
	void testSetSmelting_SetsItemInSlot()
	{
		ItemStack fuel = new ItemStackMock(Material.IRON_ORE);
		inventory.setSmelting(fuel);
		assertEquals(fuel, inventory.getItem(0));
	}

	@Test
	void testIsFuel()
	{
		ItemStack fuel = new ItemStackMock(Material.COAL);
		assertTrue(inventory.isFuel(fuel));
	}

	@Test
	void testGetHolder()
	{
		assertEquals(holder, inventory.getHolder());
	}

}
