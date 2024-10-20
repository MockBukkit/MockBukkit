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

class EnderChestStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private EnderChestStateMock enderChest;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.ENDER_CHEST);
		this.enderChest = new EnderChestStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new EnderChestStateMock(Material.ENDER_CHEST));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EnderChestStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new EnderChestStateMock(new BlockMock(Material.ENDER_CHEST)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EnderChestStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(enderChest, enderChest.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(EnderChestStateMock.class, BlockStateMock.mockState(block));
	}

}
