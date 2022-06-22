package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


class BarrelMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BarrelMock barrel;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BARREL);
		this.barrel = new BarrelMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BarrelMock(Material.BARREL));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BarrelMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BarrelMock(new BlockMock(Material.BARREL)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BarrelMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(barrel, barrel.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(BarrelMock.class, BlockStateMock.mockState(block));
	}

}
