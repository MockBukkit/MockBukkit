package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShulkerBoxStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private ShulkerBoxStateMock shulkerBox;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.SHULKER_BOX);
		this.shulkerBox = new ShulkerBoxStateMock(this.block);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		for (Material material : Tag.SHULKER_BOXES.getValues())
		{
			assertDoesNotThrow(() -> new ShulkerBoxStateMock(material));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new ShulkerBoxStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		for (Material material : Tag.SHULKER_BOXES.getValues())
		{
			assertDoesNotThrow(() -> new ShulkerBoxStateMock(new BlockMock(material)));
		}
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new ShulkerBoxStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void testNullPointerUndyed()
	{
		assertThrows(NullPointerException.class, shulkerBox::getColor);
	}

	@ParameterizedTest
	@EnumSource(DyeColor.class)
	void testShulkerColors(@NotNull DyeColor color)
	{
		assertDyed(Material.valueOf(color.name() + "_SHULKER_BOX"), color);
	}

	private void assertDyed(@NotNull Material shulkerBox, DyeColor color)
	{
		Block block = new BlockMock(shulkerBox);
		assertTrue(block.getState() instanceof ShulkerBox);
		assertEquals(color, ((ShulkerBox) block.getState()).getColor());
	}

	@Test
	void testOpen()
	{
		shulkerBox.open();
		assertTrue(shulkerBox.isOpen());
	}

	@Test
	void testClose()
	{
		assertFalse(shulkerBox.isOpen());
		shulkerBox.open();
		shulkerBox.close();
		assertFalse(shulkerBox.isOpen());
	}

	@Test
	void testIsOpen()
	{
		assertFalse(shulkerBox.isOpen());
		shulkerBox.open();
		assertTrue(shulkerBox.isOpen());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(shulkerBox, shulkerBox.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(ShulkerBoxStateMock.class, BlockStateMock.mockState(block));
	}

}
