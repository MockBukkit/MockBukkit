package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

class ShulkerBoxMockTest
{

	private ShulkerBoxMock shulkerBox;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		shulkerBox = new ShulkerBoxMock(Material.SHULKER_BOX);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialShulkerBoxBlockState()
	{
		Block block = new BlockMock(Material.SHULKER_BOX);
		assertTrue(block.getState() instanceof ShulkerBox);
	}

	@Test
	void testShulkerBoxStateMaterialValidation()
	{
		assertThrows(IllegalArgumentException.class, () -> new ShulkerBoxMock(Material.BREAD));
	}

	@Test
	void testHasInventory()
	{
		Inventory inventory = shulkerBox.getInventory();
		assertNotNull(inventory);

		assertEquals(shulkerBox, inventory.getHolder());
		assertEquals(InventoryType.SHULKER_BOX, inventory.getType());
	}

	@Test
	void testLocking()
	{
		String key = "key";

		assertFalse(shulkerBox.isLocked());
		assertEquals("", shulkerBox.getLock());

		shulkerBox.setLock("key");
		assertTrue(shulkerBox.isLocked());
		assertEquals(key, shulkerBox.getLock());
	}

	@Test
	void testNullLocking()
	{
		shulkerBox.setLock(null);
		assertFalse(shulkerBox.isLocked());
		assertEquals("", shulkerBox.getLock());
	}

	@Test
	void testNaming()
	{
		String name = "Cool Shulker";

		assertNull(shulkerBox.getCustomName());

		shulkerBox.setCustomName(name);
		assertEquals(name, shulkerBox.getCustomName());
	}

	@Test
	void testNullPointerUndyed()
	{
		assertThrows(NullPointerException.class, shulkerBox::getColor);
	}

	@ParameterizedTest
	@EnumSource(DyeColor.class)
	void testShulkerColors(DyeColor color)
	{
		assertDyed(Material.valueOf(color.name() + "_SHULKER_BOX"), color);
	}

	private void assertDyed(Material shulkerBox, DyeColor color)
	{
		Block block = new BlockMock(shulkerBox);
		assertTrue(block.getState() instanceof ShulkerBox);
		assertEquals(color, ((ShulkerBox) block.getState()).getColor());
	}
}
