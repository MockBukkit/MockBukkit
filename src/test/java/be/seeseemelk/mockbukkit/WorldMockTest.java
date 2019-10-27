package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.util.List;

import be.seeseemelk.mockbukkit.entity.EntityMock;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
		world.getBlockAt(0, 10, 0).setType(Material.BIRCH_WOOD);
		assertEquals(Material.BIRCH_WOOD, world.getBlockAt(0, 10, 0).getType());
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
		
		world.setSpawnLocation(spawn);
		assertEquals(spawn.getBlockX(), world.getSpawnLocation().getBlockX());
		assertEquals(spawn.getBlockY(), world.getSpawnLocation().getBlockY());
		assertEquals(spawn.getBlockZ(), world.getSpawnLocation().getBlockZ());
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
	
	@Test
	public void setGameRule_CorrectValue_GameRuleSet()
	{
		WorldMock world = new WorldMock();
		assertTrue(world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
		assertTrue(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
		assertTrue(world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
		assertFalse(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
	}
	
	@Test
	public void onCreated_WeatherDurationSetToZero()
	{
		WorldMock world = new WorldMock();
		assertEquals("Weather duration should be zero", 0, world.getWeatherDuration());
	}
	
	@Test
	public void setWeatherDuration_AnyPositiveValue_WeatherDurationSet()
	{
		WorldMock world = new WorldMock();
		int duration = 5;
		world.setWeatherDuration(duration);
		assertEquals("Weather duration should be set", duration, world.getWeatherDuration());
	}
	
	@Test
	public void onCreated_NotStorming()
	{
		WorldMock world = new WorldMock();
		assertFalse("The world should not be storming", world.hasStorm());
	}
	
	@Test
	public void setStorm_True_Storming()
	{
		WorldMock world = new WorldMock();
		assumeFalse(world.hasStorm());
		world.setStorm(true);
		assertTrue("The world should be storming", world.hasStorm());
	}
	
	@Test
	public void onCreated_ThunderDurationSetToZero()
	{
		WorldMock world = new WorldMock();
		assertEquals("Weather duration should be zero", 0, world.getThunderDuration());
		assertFalse(world.isThundering());
	}
	
	@Test
	public void setThunderDuration_AnyPositiveValue_ShouldBeThundering()
	{
		WorldMock world = new WorldMock();
		int duration = 20;
		world.setThunderDuration(duration);
		assertEquals("Weather duration should be more than zero", duration, world.getThunderDuration());
		assertTrue(world.isThundering());
	}

	@Test
	public void setThundering_True_ThunderDurationShouldBePositive()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		assertTrue("Weather duration should be more than zero", world.getThunderDuration() > 0);
		assertTrue(world.isThundering());
	}
	@Test
	public void spawnEntityTest(){

	}

	@Test
	public void spawnZombieTest(){
		WorldMock world = new WorldMock();
		Location location = new Location(world, 100, 20, 50);
		Entity zombie = world.spawnEntity(location, EntityType.ZOMBIE);
		assertEquals(zombie.getLocation().getBlockX(), 100);
		assertEquals(zombie.getLocation().getBlockY(), 20);
		assertEquals(zombie.getLocation().getBlockZ(), 50);
		assertTrue(world.getEntities().size()>0);

	}
}












