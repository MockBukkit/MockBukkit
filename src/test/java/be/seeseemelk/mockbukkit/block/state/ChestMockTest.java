package be.seeseemelk.mockbukkit.block.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class ChestMockTest
{

	private Chest chest;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		chest = new ChestMock(Material.CHEST);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialChestBlockState()
	{
		Block block = new BlockMock(Material.CHEST);
		assertTrue(block.getState() instanceof Chest);
	}

	@Test
	public void testHasInventory()
	{
		Inventory inventory = chest.getInventory();
		assertNotNull(inventory);
		assertEquals(inventory, chest.getBlockInventory());

		assertEquals(chest, inventory.getHolder());
		assertEquals(InventoryType.CHEST, inventory.getType());
	}

	@Test
	public void testLocking()
	{
		String key = "key";

		assertFalse(chest.isLocked());
		assertEquals("", chest.getLock());

		chest.setLock("key");
		assertTrue(chest.isLocked());
		assertEquals(key, chest.getLock());
	}

	@Test
	public void testNullLocking()
	{
		chest.setLock(null);
		assertFalse(chest.isLocked());
		assertEquals("", chest.getLock());
	}

	@Test
	public void testNaming()
	{
		String name = "Cool Chest";

		assertNull(chest.getCustomName());

		chest.setCustomName(name);
		assertEquals(name, chest.getCustomName());
	}
}
