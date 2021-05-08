package be.seeseemelk.mockbukkit.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;

public class BlockMockTest
{
	private BlockMock block;

	@Before
	public void setUp()
	{
		World world = new WorldMock();
		block = new BlockMock(new Location(world, 120, 60, 120));
	}

	@Test
	public void getType_Default_Air()
	{
		assertEquals(Material.AIR, block.getType());
	}

	@Test
	public void setType_Stone_Set()
	{
		block.setType(Material.STONE);
		assertEquals(Material.STONE, block.getType());
	}

	@Test
	public void getLocation_Default_Null()
	{
		assertNull(new BlockMock().getLocation());
	}

	@Test
	public void getLocation_CustomLocation_LocationSet()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 5, 2, 1);
		block = new BlockMock(Material.AIR, location);
		assertEquals(location, block.getLocation());
	}

	@Test
	public void getChunk_LocalBlock_Matches()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, -10, 5, 30);
		Block worldBlock = world.getBlockAt(location);
		Chunk chunk = worldBlock.getChunk();
		int localX = location.getBlockX() - (chunk.getX() << 4);
		int localZ = location.getBlockZ() - (chunk.getZ() << 4);
		Block chunkBlock = chunk.getBlock(localX, location.getBlockY(), localZ);
		assertEquals(worldBlock, chunkBlock);
	}

	@Test
	public void getWorld_AnyWorld_WorldReturned()
	{
		WorldMock world = new WorldMock();
		block = new BlockMock(new Location(world, 0, 0, 0));
		assertEquals(world, block.getWorld());
	}

	@Test
	public void getXYZ_FromLocation_XYZReturned()
	{
		block = new BlockMock(new Location(null, 1, 2, 3));
		assertEquals(1, block.getX());
		assertEquals(2, block.getY());
		assertEquals(3, block.getZ());
	}

	@Test
	public void assertType_CorrectType_DoesNotFail()
	{
		block.setType(Material.STONE);
		block.assertType(Material.STONE);
		block.setType(Material.DIRT);
		block.assertType(Material.DIRT);
	}

	@Test(expected = AssertionError.class)
	public void assertType_IncorrectType_Fails()
	{
		block.setType(Material.STONE);
		block.assertType(Material.DIRT);
	}

	@Test
	public void testGetRelativeBlockFace()
	{
		Block relative = block.getRelative(BlockFace.UP);
		assertEquals(block.getX(), relative.getX());
		assertEquals(block.getY() + 1, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	public void testGetRelativeBlockFaceAndDistance()
	{
		Block relative = block.getRelative(BlockFace.UP, 4);
		assertEquals(block.getX(), relative.getX());
		assertEquals(block.getY() + 4, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	public void testGetRelativeCordinates()
	{
		Block relative = block.getRelative(2, 6, 0);
		assertEquals(block.getX() + 2, relative.getX());
		assertEquals(block.getY() + 6, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	public void testGetBlockData()
	{
		Assert.assertEquals(block.getType(), block.getBlockData().getMaterial());
	}

	@Test
	public void testSetBlockData()
	{
		BlockDataMock blockData = new BlockDataMock(Material.DIRT);
		Material oldType = block.getType();

		block.setBlockData(blockData);
		Assert.assertEquals(blockData, block.getBlockData());
		Assert.assertEquals(blockData.getMaterial(), block.getType());
		block.setType(oldType);
	}

	@Test
	public void testWaterIsLiquid()
	{
		block.setType(Material.WATER);
		assertTrue(block.isLiquid());
	}

	@Test
	public void testLavaIsLiquid()
	{
		block.setType(Material.LAVA);
		assertTrue(block.isLiquid());
	}

	@Test
	public void testBubbleColumnIsLiquid()
	{
		block.setType(Material.BUBBLE_COLUMN);
		assertTrue(block.isLiquid());
	}

	@Test
	public void testAirIsLiquid()
	{
		block.setType(Material.AIR);
		assertTrue(block.isEmpty());
	}

	@Test
	public void testStoneIsNotLiquid()
	{
		block.setType(Material.STONE);
		assertFalse(block.isLiquid());
	}

	@Test
	public void testStoneIsNotEmpty()
	{
		block.setType(Material.STONE);
		assertFalse(block.isEmpty());
	}

	@Test
	public void testBreakNaturally()
	{
		block.setType(Material.STONE);
		block.breakNaturally();
		assertTrue(block.isEmpty());
	}
}
