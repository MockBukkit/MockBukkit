package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class BrewerInventoryMockTest
{

	private BrewerInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new BrewerInventoryMock(null);
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(BrewerInventoryMock.class, snapshot);
		assertNotEquals(inventory, snapshot);
	}

	@Test
	void testGetSnapShotWithSetFuelAndIngredient()
	{
		ItemStack ingredient = new ItemStackMock(Material.POTION);
		ItemStack fuel = new ItemStackMock(Material.BLAZE_POWDER);

		inventory.setFuel(fuel);
		inventory.setIngredient(ingredient);

		BrewerInventoryMock snapshot = inventory.getSnapshot();

		assertEquals(ingredient, snapshot.getIngredient());
		assertEquals(fuel, snapshot.getFuel());
	}

	@Test
	void testGetFuelWithNoFuelSet()
	{
		assertThrows(IllegalStateException.class, () -> inventory.getFuel());
	}

	@Test
	void testGetIngredientWithNoIngredientSet()
	{
		assertThrows(IllegalStateException.class, () -> inventory.getIngredient());
	}

	@Test
	void testSetFuelWithNullFuel()
	{
		assertThrows(NullPointerException.class, () -> inventory.setFuel(null));
	}

	@Test
	void testSetIngredientWithNullIngredient()
	{
		assertThrows(NullPointerException.class, () -> inventory.setIngredient(null));
	}

	@Test
	void testSetFuelWithValidFuel()
	{
		ItemStack fuel = new ItemStackMock(Material.BLAZE_POWDER);
		inventory.setFuel(fuel);
		assertEquals(fuel, inventory.getFuel());
	}

	@Test
	void testSetIngredientWithValidIngredient()
	{
		ItemStack ingredient = new ItemStackMock(Material.SPIDER_EYE);
		inventory.setIngredient(ingredient);
		assertEquals(ingredient, inventory.getIngredient());
	}

	@Test
	void testSetFuel_SetsSlot()
	{
		ItemStack fuel = new ItemStackMock(Material.BLAZE_POWDER);

		inventory.setFuel(fuel);

		assertEquals(fuel, inventory.getItem(4));
	}

	@Test
	void testSetIngredient_SetsSlot()
	{
		ItemStack fuel = new ItemStackMock(Material.BLAZE_POWDER);

		inventory.setIngredient(fuel);

		assertEquals(fuel, inventory.getItem(3));
	}

}
