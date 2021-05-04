package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class HopperMockTest
{

	private Hopper hopper;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		hopper = new HopperMock(Material.HOPPER);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialHopperBlockState()
	{
		Block block = new BlockMock(Material.HOPPER);
		assertTrue(block.getState() instanceof Hopper);
	}

	@Test
	public void testHasInventory()
	{
		Inventory inventory = hopper.getInventory();
		assertNotNull(inventory);

		assertEquals(hopper, inventory.getHolder());
		assertEquals(InventoryType.HOPPER, inventory.getType());
	}

	@Test
	public void testLocking()
	{
		String key = "key";

		assertFalse(hopper.isLocked());
		assertEquals("", hopper.getLock());

		hopper.setLock("key");
		assertTrue(hopper.isLocked());
		assertEquals(key, hopper.getLock());
	}

	@Test
	public void testNullLocking()
	{
		hopper.setLock(null);
		assertFalse(hopper.isLocked());
		assertEquals("", hopper.getLock());
	}

	@Test
	public void testNaming()
	{
		String name = "Cool Hopper";

		assertNull(hopper.getCustomName());

		hopper.setCustomName(name);
		assertEquals(name, hopper.getCustomName());
	}
}
