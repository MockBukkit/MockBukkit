package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallMockTest
{

	private WallMock wall;

	@BeforeEach
	void setUp()
	{
		this.wall = new WallMock(Material.COBBLESTONE_WALL);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new WallMock(Material.COBBLESTONE_WALL));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new WallMock(Material.BEDROCK));
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(Wall.Height.NONE, wall.getHeight(BlockFace.EAST));
		assertEquals(Wall.Height.NONE, wall.getHeight(BlockFace.WEST));
		assertEquals(Wall.Height.NONE, wall.getHeight(BlockFace.NORTH));
		assertEquals(Wall.Height.NONE, wall.getHeight(BlockFace.SOUTH));
		assertFalse(wall.isUp());
		assertFalse(wall.isWaterlogged());
	}


	@Test
	void setWallUp()
	{
		wall.setUp(true);
		assertTrue(wall.isUp());
	}

	@Test
	void setWaterlogged()
	{
		wall.setWaterlogged(true);
		assertTrue(wall.isWaterlogged());
	}

	@Test
	void setHeightEast()
	{
		wall.setHeight(BlockFace.EAST, Wall.Height.TALL);
		assertEquals(Wall.Height.TALL, wall.getHeight(BlockFace.EAST));
	}

	@Test
	void setHeightWest()
	{
		wall.setHeight(BlockFace.WEST, Wall.Height.TALL);
		assertEquals(Wall.Height.TALL, wall.getHeight(BlockFace.WEST));
	}

	@Test
	void setHeightNorth()
	{
		wall.setHeight(BlockFace.NORTH, Wall.Height.LOW);
		assertEquals(Wall.Height.LOW, wall.getHeight(BlockFace.NORTH));
	}

	@Test
	void setHeightSouth()
	{
		wall.setHeight(BlockFace.SOUTH, Wall.Height.LOW);
		assertEquals(Wall.Height.LOW, wall.getHeight(BlockFace.SOUTH));
	}

	@Test
	void setHeight_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> wall.setHeight(null, null));
	}

	@Test
	void setHeight_InvalidBlockFace()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> wall.setHeight(BlockFace.NORTH_EAST, Wall.Height.TALL));
	}

	@Test
	void getHeight_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> wall.getHeight(null));
	}

	@Test
	void getHeight_InvalidBlockFace()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> wall.getHeight(BlockFace.NORTH_EAST));
	}

	@Test
	void getAsString()
	{
		assertEquals("minecraft:cobblestone_wall[east=none,west=none,north=none,south=none,up=false,waterlogged=false]", wall.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.WALLS.getValues())
		{
			assertInstanceOf(Wall.class, BlockDataMock.mock(material));
		}
	}

}
