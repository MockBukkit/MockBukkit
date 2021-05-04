package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class ShulkerBoxMockTest
{

	private ShulkerBoxMock shulkerBox;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		shulkerBox = new ShulkerBoxMock(Material.SHULKER_BOX);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialShulkerBoxBlockState()
	{
		Block block = new BlockMock(Material.SHULKER_BOX);
		assertTrue(block.getState() instanceof ShulkerBox);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShulkerBoxStateMaterialValidation()
	{
		new ShulkerBoxMock(Material.BREAD);
	}

	@Test
	public void testHasInventory()
	{
		Inventory inventory = shulkerBox.getInventory();
		assertNotNull(inventory);

		assertEquals(shulkerBox, inventory.getHolder());
		assertEquals(InventoryType.SHULKER_BOX, inventory.getType());
	}

	@Test
	public void testLocking()
	{
		String key = "key";

		assertFalse(shulkerBox.isLocked());
		assertEquals("", shulkerBox.getLock());

		shulkerBox.setLock("key");
		assertTrue(shulkerBox.isLocked());
		assertEquals(key, shulkerBox.getLock());
	}

	@Test
	public void testNullLocking()
	{
		shulkerBox.setLock(null);
		assertFalse(shulkerBox.isLocked());
		assertEquals("", shulkerBox.getLock());
	}

	@Test
	public void testNaming()
	{
		String name = "Cool Shulker";

		assertNull(shulkerBox.getCustomName());

		shulkerBox.setCustomName(name);
		assertEquals(name, shulkerBox.getCustomName());
	}

	@Test(expected = NullPointerException.class)
	public void testNullPointerUndyed()
	{
		shulkerBox.getColor();
	}

	@Test
	public void testShulkerColors()
	{
		for (DyeColor color : DyeColor.values())
		{
			assertDyed(Material.valueOf(color.name() + "_SHULKER_BOX"), color);
		}
	}

	private void assertDyed(Material shulkerBox, DyeColor color)
	{
		Block block = new BlockMock(shulkerBox);
		assertTrue(block.getState() instanceof ShulkerBox);
		assertEquals(color, ((ShulkerBox) block.getState()).getColor());
	}
}
