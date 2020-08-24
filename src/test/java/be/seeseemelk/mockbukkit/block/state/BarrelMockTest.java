package be.seeseemelk.mockbukkit.block.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class BarrelMockTest
{

	private Barrel barrel;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		barrel = new BarrelMock(Material.BARREL);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialChestBlockState()
	{
		Block block = new BlockMock(Material.BARREL);
		assertTrue(block.getState() instanceof Barrel);
	}

	@Test
	public void testHasInventory()
	{
		Inventory inventory = barrel.getInventory();
		assertNotNull(inventory);

		assertEquals(barrel, inventory.getHolder());
		assertEquals(InventoryType.BARREL, inventory.getType());
	}

	@Test
	public void testLocking()
	{
		String key = "key";

		assertFalse(barrel.isLocked());
		assertEquals("", barrel.getLock());

		barrel.setLock("key");
		assertTrue(barrel.isLocked());
		assertEquals(key, barrel.getLock());
	}

	@Test
	public void testNullLocking()
	{
		barrel.setLock(null);
		assertFalse(barrel.isLocked());
		assertEquals("", barrel.getLock());
	}

	@Test
	public void testNaming()
	{
		String name = "Cool Chest";

		assertNull(barrel.getCustomName());

		barrel.setCustomName(name);
		assertEquals(name, barrel.getCustomName());
	}
}
