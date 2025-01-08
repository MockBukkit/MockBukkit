package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BarrelStateMockTest extends ContainerStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BarrelStateMock barrel;

	@Override
	protected ContainerStateMock instance()
	{
		return barrel;
	}

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BARREL);
		this.barrel = new BarrelStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BarrelStateMock(Material.BARREL));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BarrelStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BarrelStateMock(new BlockMock(Material.BARREL)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BarrelStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(barrel, barrel.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(BarrelStateMock.class, BlockStateMock.mockState(block));
	}

	@Test
	void testIsOpenDefault()
	{
		assertFalse(barrel.isOpen());
	}

	@Test
	void testOpen()
	{
		barrel.open();
		assertTrue(barrel.isOpen());
	}

	@Test
	void testClose()
	{
		barrel.close();
		assertFalse(barrel.isOpen());
	}

}
