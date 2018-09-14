package be.seeseemelk.mockbukkit.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.WorldMock;

public class BlockMockTest
{
	private BlockMock block;

	@Before
	public void setUp() throws Exception
	{
		block = new BlockMock();
	}

	@Test
	public void getType_Default_Air()
	{
		assertEquals(Material.AIR, block.getType());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getTypeId_TypeSet_TypeGotten()
	{
		assertEquals(Material.AIR.getId(), block.getTypeId());
		block.setType(Material.STONE);
		assertEquals(Material.STONE.getId(), block.getTypeId());
	}
	
	@Test
	public void setType_Stone_Set()
	{
		block.setType(Material.STONE);
		assertEquals(Material.STONE, block.getType());
	}
	
	@Test
	public void getState_Default_NotNull()
	{
		assertNotNull(block.getState());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getData_DataSet_DateGotten()
	{
		block.setData((byte) 25);
		assertEquals(25, block.getData());
	}
	
	@Test
	public void getLocation_Default_Null()
	{
		assertNull(block.getLocation());
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

}
