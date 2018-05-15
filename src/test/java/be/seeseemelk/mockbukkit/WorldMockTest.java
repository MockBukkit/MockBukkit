package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.block.BlockMock;

public class WorldMockTest
{
	private ServerMock server;
	
	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
	}
	
	@After
	public void tearDown()
	{
		MockBukkit.unload();
	}
	
	@Test
	public void getBlockAt_StandardWorld_DefaultBlocks()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(Material.BEDROCK, world.getBlockAt(0, 0, 0).getType());
		assertEquals(Material.DIRT, world.getBlockAt(0, 1, 0).getType());
		assertEquals(Material.DIRT, world.getBlockAt(0, 2, 0).getType());
		assertEquals(Material.DIRT, world.getBlockAt(0, 3, 0).getType());
		assertEquals(Material.AIR, world.getBlockAt(0, 4, 0).getType());
	}
	
	@Test
	public void getBlockAt_BlockChanged_BlockChanged()
	{
		WorldMock world = new WorldMock();
		assertEquals(Material.AIR, world.getBlockAt(0, 10, 0).getType());
		world.getBlockAt(0, 10, 0).setType(Material.WOOD);
		assertEquals(Material.WOOD, world.getBlockAt(0, 10, 0).getType());
	}
	
	@Test
	public void getBlockAt_AnyBlock_LocationSet()
	{
		WorldMock world = new WorldMock();
		BlockMock block = world.getBlockAt(1, 2, 3);
		Location location = block.getLocation();
		assertEquals(1, location.getBlockX());
		assertEquals(2, location.getBlockY());
		assertEquals(3, location.getBlockZ());
		assertEquals(world, block.getWorld());
	}
	
	@Test
	public void getSpawnLocation_Default_JustAboveDirt()
	{
		WorldMock world = new WorldMock();
		Location spawn = world.getSpawnLocation();
		assertNotNull(spawn);
		assertEquals(Material.AIR, world.getBlockAt(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ()).getType());
		assertEquals(Material.GRASS, world.getBlockAt(spawn.getBlockX(), spawn.getBlockY()-1, spawn.getBlockZ()).getType());
	}
	
	@Test
	public void setSpawnLocation_SomeNewLocation_LocationChanged()
	{
		WorldMock world = new WorldMock();
		Location spawn = world.getSpawnLocation().clone();
		world.setSpawnLocation(spawn.getBlockX() + 10, spawn.getBlockY() + 10, spawn.getBlockZ() + 10);
		assertEquals(spawn.getBlockX() + 10, world.getSpawnLocation().getBlockX());
		assertEquals(spawn.getBlockY() + 10, world.getSpawnLocation().getBlockY());
		assertEquals(spawn.getBlockZ() + 10, world.getSpawnLocation().getBlockZ());
	}
	
	@Test
	public void getEntities_NoEntities_EmptyList()
	{
		WorldMock world = new WorldMock();
		List<Entity> entities = world.getEntities();
		assertNotNull(entities);
		assertEquals(0, entities.size());
	}
	
	@Test
	public void getEntities_OnePlayerInWorld_ListContainsOnlyPlayer()
	{
		World world = server.addSimpleWorld("world");
		server.addSimpleWorld("otherWorld");
		Player player = server.addPlayer();
		player.teleport(world.getSpawnLocation());
		List<Entity> entities = world.getEntities();
		assertNotNull(entities);
		assertEquals(1, entities.size());
		assertSame(player, entities.get(0));
	}
	
	@Test
	public void getEntities_OnePlayerInDifferentWorld_EmptyList()
	{
		World world = server.addSimpleWorld("world");
		World otherWorld = server.addSimpleWorld("otherWorld");
		Player player = server.addPlayer();
		player.teleport(otherWorld.getSpawnLocation());
		List<Entity> entities = world.getEntities();
		assertNotNull(entities);
		assertEquals(0, entities.size());
	}
	
	@Test
	public void getChunkAt_DifferentLocations_DifferentChunks()
	{
		WorldMock world = server.addSimpleWorld("world");
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(1, 0);
		assertNotEquals(chunk1, chunk2);
	}
	
	@Test
	public void getChunkAt_SameLocations_EqualsChunks()
	{
		WorldMock world = server.addSimpleWorld("world");
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 0);
		assertEquals(chunk1, chunk2);
	}
	
	@Test
	public void getName_NameSet_NameExactly()
	{
		WorldMock world = new WorldMock();
		world.setName("world name");
		assertEquals("world name", world.getName());
	}
	
}












