package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

class ChestMockTest
{

	private Chest chest;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		chest = new ChestMock(Material.CHEST);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialChestBlockState()
	{
		Block block = new BlockMock(Material.CHEST);
		assertTrue(block.getState() instanceof Chest);
	}

	@Test
	void testHasInventory()
	{
		Inventory inventory = chest.getInventory();
		assertNotNull(inventory);
		assertEquals(inventory, chest.getBlockInventory());

		assertEquals(chest, inventory.getHolder());
		assertEquals(InventoryType.CHEST, inventory.getType());
	}

	@Test
	void testLocking()
	{
		String key = "key";

		assertFalse(chest.isLocked());
		assertEquals("", chest.getLock());

		chest.setLock("key");
		assertTrue(chest.isLocked());
		assertEquals(key, chest.getLock());
	}

	@Test
	void testNullLocking()
	{
		chest.setLock(null);
		assertFalse(chest.isLocked());
		assertEquals("", chest.getLock());
	}

	@Test
	void testNaming()
	{
		String name = "Cool Chest";

		assertNull(chest.getCustomName());

		chest.setCustomName(name);
		assertEquals(name, chest.getCustomName());
	}
}
