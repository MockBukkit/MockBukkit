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

class BellMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BellMock bell;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BELL);
		this.bell = new BellMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BellMock(Material.BELL));
	}

	@Test
	void constructor_Material_NotBeehive_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BellMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BellMock(new BlockMock(Material.BELL)));
	}

	@Test
	void constructor_Block_NotBeehive_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BellMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(bell, bell.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(BellMock.class, BlockStateMock.mockState(block));
	}

}
