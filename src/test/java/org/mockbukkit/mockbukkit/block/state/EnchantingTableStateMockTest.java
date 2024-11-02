package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class EnchantingTableStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private EnchantingTableStateMock enchantTable;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.ENCHANTING_TABLE);
		this.enchantTable = new EnchantingTableStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new EnchantingTableStateMock(Material.ENCHANTING_TABLE));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EnchantingTableStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new EnchantingTableStateMock(new BlockMock(Material.ENCHANTING_TABLE)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EnchantingTableStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(enchantTable, enchantTable.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(EnchantingTableStateMock.class, BlockStateMock.mockState(block));
	}

}
