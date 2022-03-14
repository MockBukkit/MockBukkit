package be.seeseemelk.mockbukkit.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.ChunkCoordinate;
import be.seeseemelk.mockbukkit.ChunkMock;
import be.seeseemelk.mockbukkit.Coordinate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;

class BlockMockTest
{
	private BlockMock block;

	@BeforeEach
	public void setUp()
	{
		World world = new WorldMock();
		block = new BlockMock(new Location(world, 120, 60, 120));
	}

	@Test
	void getType_Default_Air()
	{
		assertEquals(Material.AIR, block.getType());
	}

	@Test
	void setType_Stone_Set()
	{
		block.setType(Material.STONE);
		assertEquals(Material.STONE, block.getType());
	}

	@Test
	void getLocation_Default_Null()
	{
		assertNull(new BlockMock().getLocation());
	}

	@Test
	void getLocation_CustomLocation_LocationSet()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 5, 2, 1);
		block = new BlockMock(Material.AIR, location);
		assertEquals(location, block.getLocation());
	}

	@Test
	void getLocation_CustomLocation_ApplyToProvided()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 5, 2, 1);
		block = new BlockMock(Material.AIR, location);
		Location location2 = new Location(null, 0, 0, 0);
		block.getLocation(location2);
		assertEquals(block.getLocation(), location2);
	}

	@Test
	void getChunk_LocalBlock_Matches()
	{
		WorldMock world = new WorldMock();
		Coordinate coordinate = new Coordinate(-10, 5, 30);
		Block worldBlock = world.getBlockAt(coordinate);
		Block chunkBlock = ((ChunkMock) worldBlock.getChunk()).getBlock(coordinate.toLocalCoordinate());
		assertEquals(worldBlock, chunkBlock);
	}

	@Test
	void getChunk_LocalBlock_NegativeY()
	{
		WorldMock world = new WorldMock(Material.STONE, -64, 320, 70);
		Coordinate coordinate = new Coordinate(55, -40, 100);
		Block worldBlock = world.getBlockAt(coordinate);
		ChunkCoordinate chunkCoordinate = coordinate.toChunkCoordinate();
		Block chunkBlock = world.getChunkAt(chunkCoordinate).getBlock(coordinate.toLocalCoordinate());
		assertEquals(worldBlock, chunkBlock);
	}

	@Test
	void getWorld_AnyWorld_WorldReturned()
	{
		WorldMock world = new WorldMock();
		block = new BlockMock(new Location(world, 0, 0, 0));
		assertEquals(world, block.getWorld());
	}

	@Test
	void getXYZ_FromLocation_XYZReturned()
	{
		block = new BlockMock(new Location(null, 1, 2, 3));
		assertEquals(1, block.getX());
		assertEquals(2, block.getY());
		assertEquals(3, block.getZ());
	}

	@Test
	void assertType_CorrectType_DoesNotFail()
	{
		block.setType(Material.STONE);
		block.assertType(Material.STONE);
		block.setType(Material.DIRT);
		block.assertType(Material.DIRT);
	}

	@Test
	void assertType_IncorrectType_Fails()
	{
		block.setType(Material.STONE);
		assertThrows(AssertionError.class, () -> block.assertType(Material.DIRT));
	}

	@Test
	void testGetRelativeBlockFace()
	{
		Block relative = block.getRelative(BlockFace.UP);
		assertEquals(block.getX(), relative.getX());
		assertEquals(block.getY() + 1, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	void testGetRelativeBlockFaceAndDistance()
	{
		Block relative = block.getRelative(BlockFace.UP, 4);
		assertEquals(block.getX(), relative.getX());
		assertEquals(block.getY() + 4, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	void testGetRelativeCordinates()
	{
		Block relative = block.getRelative(2, 6, 0);
		assertEquals(block.getX() + 2, relative.getX());
		assertEquals(block.getY() + 6, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	void testGetBlockData()
	{
		assertEquals(block.getType(), block.getBlockData().getMaterial());
	}

	@Test
	void testSetBlockData()
	{
		BlockDataMock blockData = new BlockDataMock(Material.DIRT);
		Material oldType = block.getType();

		block.setBlockData(blockData);
		assertEquals(blockData, block.getBlockData());
		assertEquals(blockData.getMaterial(), block.getType());
		block.setType(oldType);
	}

	@Test
	void testWaterIsLiquid()
	{
		block.setType(Material.WATER);
		assertTrue(block.isLiquid());
	}

	@Test
	void testLavaIsLiquid()
	{
		block.setType(Material.LAVA);
		assertTrue(block.isLiquid());
	}

	@Test
	void testBubbleColumnIsLiquid()
	{
		block.setType(Material.BUBBLE_COLUMN);
		assertTrue(block.isLiquid());
	}

	@Test
	void testAirIsLiquid()
	{
		block.setType(Material.AIR);
		assertTrue(block.isEmpty());
	}

	@Test
	void testStoneIsNotLiquid()
	{
		block.setType(Material.STONE);
		assertFalse(block.isLiquid());
	}

	@Test
	void testStoneIsNotEmpty()
	{
		block.setType(Material.STONE);
		assertFalse(block.isEmpty());
	}

	@Test
	void testBreakNaturally()
	{
		block.setType(Material.STONE);
		block.breakNaturally();
		assertTrue(block.isEmpty());
	}
}
