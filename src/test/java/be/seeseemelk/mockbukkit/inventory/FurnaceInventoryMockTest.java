package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.state.BlastFurnaceMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FurnaceInventoryMockTest
{

	private ServerMock server;
	private FurnaceInventoryMock inventory;

	private BlastFurnaceMock holder;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		inventory = new FurnaceInventoryMock(holder);
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
	void testGetResult()
	{
		ItemStack result = new ItemStack(Material.IRON_INGOT);
		inventory.setResult(result);
		assertEquals(result, inventory.getResult());
	}

	@Test
	void testGetSmelting()
	{
		ItemStack smelting = new ItemStack(Material.IRON_INGOT);
		inventory.setSmelting(smelting);
		assertEquals(smelting, inventory.getSmelting());
	}

	@Test
	void testIsFuel()
	{
		ItemStack fuel = new ItemStack(Material.COAL);
		assertTrue(inventory.isFuel(fuel));
	}

	@Test
	void testGetHolder()
	{
		assertEquals(holder, inventory.getHolder());
	}
}
