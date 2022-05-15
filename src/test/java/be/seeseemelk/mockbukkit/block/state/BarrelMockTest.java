package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void testOpen()
	{
		assertFalse(barrel.isOpen());

		barrel.open();

		assertTrue(barrel.isOpen());
	}

	@Test
	void testClose()
	{

		assertFalse(barrel.isOpen());

		barrel.open();
		assertTrue(barrel.isOpen());

		barrel.close();
		assertFalse(barrel.isOpen());
	}

	@Test
	void testIsOpen()
	{
		assertFalse(barrel.isOpen());
	}

}
