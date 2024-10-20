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

class DropperStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private DropperStateMock dropper;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.DROPPER);
		this.dropper = new DropperStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new DropperStateMock(Material.DROPPER));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new DropperStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new DropperStateMock(new BlockMock(Material.DROPPER)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new DropperStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(dropper, dropper.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(DropperStateMock.class, BlockStateMock.mockState(block));
	}

}
