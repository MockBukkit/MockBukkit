package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

class DropperMockTest
{

	private Dropper dropper;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		dropper = new DropperMock(Material.DROPPER);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialDropperBlockState()
	{
		Block block = new BlockMock(Material.DROPPER);
		assertTrue(block.getState() instanceof Dropper);
	}

	@Test
	void testHasInventory()
	{
		Inventory inventory = dropper.getInventory();
		assertNotNull(inventory);

		assertEquals(dropper, inventory.getHolder());
		assertEquals(InventoryType.DROPPER, inventory.getType());
	}

	@Test
	void testLocking()
	{
		String key = "key";

		assertFalse(dropper.isLocked());
		assertEquals("", dropper.getLock());

		dropper.setLock("key");
		assertTrue(dropper.isLocked());
		assertEquals(key, dropper.getLock());
	}

	@Test
	void testNullLocking()
	{
		dropper.setLock(null);
		assertFalse(dropper.isLocked());
		assertEquals("", dropper.getLock());
	}

	@Test
	void testNaming()
	{
		String name = "Cool Dropper";

		assertNull(dropper.getCustomName());

		dropper.setCustomName(name);
		assertEquals(name, dropper.getCustomName());
	}
}
