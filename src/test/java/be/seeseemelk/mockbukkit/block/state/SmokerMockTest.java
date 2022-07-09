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

class SmokerMockTest
{

	private WorldMock world;
	private BlockMock block;
	private SmokerMock furnace;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.SMOKER);
		this.furnace = new SmokerMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new SmokerMock(Material.SMOKER));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SmokerMock(Material.FURNACE));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new SmokerMock(new BlockMock(Material.SMOKER)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SmokerMock(new BlockMock(Material.FURNACE)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(furnace, furnace.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(SmokerMock.class, BlockStateMock.mockState(block));
	}

}
