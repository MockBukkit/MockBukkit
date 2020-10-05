package be.seeseemelk.mockbukkit.block.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class DropperMockTest
{

	private Dropper dropper;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		dropper = new DropperMock(Material.DROPPER);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialDropperBlockState()
	{
		Block block = new BlockMock(Material.DROPPER);
		assertTrue(block.getState() instanceof Dropper);
	}

	@Test
	public void testHasInventory()
	{
		Inventory inventory = dropper.getInventory();
		assertNotNull(inventory);

		assertEquals(dropper, inventory.getHolder());
		assertEquals(InventoryType.DROPPER, inventory.getType());
	}

	@Test
	public void testLocking()
	{
		String key = "key";

		assertFalse(dropper.isLocked());
		assertEquals("", dropper.getLock());

		dropper.setLock("key");
		assertTrue(dropper.isLocked());
		assertEquals(key, dropper.getLock());
	}

	@Test
	public void testNullLocking()
	{
		dropper.setLock(null);
		assertFalse(dropper.isLocked());
		assertEquals("", dropper.getLock());
	}

	@Test
	public void testNaming()
	{
		String name = "Cool Dropper";

		assertNull(dropper.getCustomName());

		dropper.setCustomName(name);
		assertEquals(name, dropper.getCustomName());
	}
}
