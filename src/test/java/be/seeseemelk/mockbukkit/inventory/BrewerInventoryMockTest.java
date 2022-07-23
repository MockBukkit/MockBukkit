package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrewerInventoryMockTest
{

	private BrewerInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.inventory = new BrewerInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
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
		ItemStack ingredient = new ItemStack(Material.POTION);
		ItemStack fuel = new ItemStack(Material.BLAZE_POWDER);

		inventory.setFuel(fuel);
		inventory.setIngredient(ingredient);

		InventoryMock snapshot = inventory.getSnapshot();

		assertEquals(ingredient, ((BrewerInventoryMock) snapshot).getIngredient());
		assertEquals(fuel, ((BrewerInventoryMock) snapshot).getFuel());
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
		ItemStack fuel = new ItemStack(Material.BLAZE_POWDER);
		inventory.setFuel(fuel);
		assertEquals(fuel, inventory.getFuel());
	}

	@Test
	void testSetIngredientWithValidIngredient()
	{
		ItemStack ingredient = new ItemStack(Material.SPIDER_EYE);
		inventory.setIngredient(ingredient);
		assertEquals(ingredient, inventory.getIngredient());
	}

}
