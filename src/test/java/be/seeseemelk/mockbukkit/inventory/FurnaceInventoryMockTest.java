package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FurnaceInventoryMockTest
{

	private ServerMock server;
	private FurnaceInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		inventory = new FurnaceInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetFuel()
	{
		ItemStack fuel = new ItemStack(Material.COAL);
		inventory.setFuel(fuel);
		assertEquals(fuel, inventory.getFuel());
	}

	@Test
	void testGetFuelWithNullFuel()
	{
		assertThrows(IllegalStateException.class, () -> inventory.getFuel());
	}

	@Test
	void testSetFuelWithNullFuel()
	{
		assertThrows(NullPointerException.class, () -> inventory.setFuel(null));
	}

	@Test
	void testGetResult()
	{
		ItemStack result = new ItemStack(Material.IRON_INGOT);
		inventory.setResult(result);
		assertEquals(result, inventory.getResult());
	}

	@Test
	void testGetResultWithNullResult()
	{
		assertThrows(IllegalStateException.class, () -> inventory.getResult());
	}

	@Test
	void testSetResultWithNullResult()
	{
		assertThrows(NullPointerException.class, () -> inventory.setResult(null));
	}

	@Test
	void testGetSmelting()
	{
		ItemStack smelting = new ItemStack(Material.IRON_INGOT);
		inventory.setSmelting(smelting);
		assertEquals(smelting, inventory.getSmelting());
	}

	@Test
	void testGetSmeltingWithNullSmelting()
	{
		assertThrows(IllegalStateException.class, () -> inventory.getSmelting());
	}

	@Test
	void testSetSmeltingWithNullSmelting()
	{
		assertThrows(NullPointerException.class, () -> inventory.setSmelting(null));
	}

	@Test
	void testIsFuel()
	{
		ItemStack fuel = new ItemStack(Material.COAL);
		assertTrue(inventory.isFuel(fuel));
	}

	@Test
	void testIsFuelWithNullFuel()
	{
		assertThrows(NullPointerException.class, () -> inventory.isFuel(null));
	}

}
