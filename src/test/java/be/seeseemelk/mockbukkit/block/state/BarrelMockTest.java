package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

class BarrelMockTest
{

	private Barrel barrel;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		barrel = new BarrelMock(Material.BARREL);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialBarrelBlockState()
	{
		Block block = new BlockMock(Material.BARREL);
		assertTrue(block.getState() instanceof Barrel);
	}

	@Test
	void testHasInventory()
	{
		Inventory inventory = barrel.getInventory();
		assertNotNull(inventory);

		assertEquals(barrel, inventory.getHolder());
		assertEquals(InventoryType.BARREL, inventory.getType());
	}

	@Test
	void testLocking()
	{
		String key = "key";

		assertFalse(barrel.isLocked());
		assertEquals("", barrel.getLock());

		barrel.setLock("key");
		assertTrue(barrel.isLocked());
		assertEquals(key, barrel.getLock());
	}

	@Test
	void testNullLocking()
	{
		barrel.setLock(null);
		assertFalse(barrel.isLocked());
		assertEquals("", barrel.getLock());
	}

	@Test
	void testNaming()
	{
		String name = "Cool Chest";

		assertNull(barrel.getCustomName());

		barrel.setCustomName(name);
		assertEquals(name, barrel.getCustomName());
	}
}
