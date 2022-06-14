package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.block.state.BlockStateMock;
import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import be.seeseemelk.mockbukkit.entity.ExperienceOrbMock;
import be.seeseemelk.mockbukkit.entity.FireworkMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class WorldMockTest
{

	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getBlockAt_StandardWorld_DefaultBlocks()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(Material.BEDROCK, world.getBlockAt(0, 0, 0).getType());
		assertEquals(Material.DIRT, world.getBlockAt(0, 1, 0).getType());
		assertEquals(Material.DIRT, world.getBlockAt(0, 2, 0).getType());
		assertEquals(Material.DIRT, world.getBlockAt(0, 3, 0).getType());
		assertEquals(Material.AIR, world.getBlockAt(0, 4, 0).getType());
	}

	@Test
	void getBlockAt_BlockChanged_BlockChanged()
	{
		WorldMock world = new WorldMock();
		assertEquals(Material.AIR, world.getBlockAt(0, 10, 0).getType());
		world.getBlockAt(0, 10, 0).setType(Material.BIRCH_WOOD);
		assertEquals(Material.BIRCH_WOOD, world.getBlockAt(0, 10, 0).getType());
	}

	@Test
	void getBlockAt_AnyBlock_LocationSet()
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
	void getSpawnLocation_Default_JustAboveDirt()
	{
		WorldMock world = new WorldMock();
		Location spawn = world.getSpawnLocation();
		assertNotNull(spawn);
		assertEquals(Material.AIR, world.getBlockAt(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ()).getType());
		assertEquals(Material.GRASS, world.getBlockAt(spawn.getBlockX(), spawn.getBlockY() - 1, spawn.getBlockZ()).getType());
	}

	@Test
	void setSpawnLocation_SomeNewLocation_LocationChanged()
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
	void getEntities_NoEntities_EmptyList()
	{
		WorldMock world = new WorldMock();
		List<Entity> entities = world.getEntities();
		assertNotNull(entities);
		assertEquals(0, entities.size());
	}

	@Test
	void getEntities_OnePlayerInWorld_ListContainsOnlyPlayer()
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
	void getEntities_OnePlayerInDifferentWorld_EmptyList()
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
	void getChunkAt_DifferentLocations_DifferentChunks()
	{
		WorldMock world = server.addSimpleWorld("world");
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(1, 0);
		assertNotEquals(chunk1, chunk2);
	}

	@Test
	void getChunkAt_SameLocations_EqualsChunks()
	{
		WorldMock world = server.addSimpleWorld("world");
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 0);
		assertEquals(chunk1, chunk2);
	}

	@Test
	void getName_NameSet_NameExactly()
	{
		WorldMock world = new WorldMock();
		world.setName("world name");
		assertEquals("world name", world.getName());
	}

	@Test
	void setGameRule_CorrectValue_GameRuleSet()
	{
		WorldMock world = new WorldMock();
		assertTrue(world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
		assertTrue(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
		assertTrue(world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
		assertFalse(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
	}

	@Test
	void spawnZombieTest()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 100, 20, 50);
		Entity zombie = world.spawnEntity(location, EntityType.ZOMBIE);
		assertEquals(100, zombie.getLocation().getBlockX());
		assertEquals(20, zombie.getLocation().getBlockY());
		assertEquals(50, zombie.getLocation().getBlockZ());
		assertTrue(world.getEntities().size() > 0);
	}

	@Test
	void onCreated_WeatherDurationSetToZero()
	{
		WorldMock world = new WorldMock();
		assertEquals(0, world.getWeatherDuration(), "Weather duration should be zero");
	}

	@Test
	void setWeatherDuration_AnyPositiveValue_WeatherDurationSet()
	{
		WorldMock world = new WorldMock();
		int duration = 5;
		world.setWeatherDuration(duration);
		assertEquals(duration, world.getWeatherDuration(), "Weather duration should be set");
	}

	@Test
	void onCreated_NotStorming()
	{
		WorldMock world = new WorldMock();
		assertFalse(world.hasStorm(), "The world should not be storming");
	}

	@Test
	void setStorm_True_Storming()
	{
		WorldMock world = new WorldMock();
		assertFalse(world.hasStorm());
		world.setStorm(true);
		assertTrue(world.hasStorm(), "The world should be storming");
	}

	@Test
	void onCreated_ThunderDurationSetToZero()
	{
		WorldMock world = new WorldMock();
		assertEquals(0, world.getThunderDuration(), "Weather duration should be zero");
		assertFalse(world.isThundering());
	}

	@Test
	void setThunderDuration_AnyPositiveValue_ShouldBeThundering()
	{
		WorldMock world = new WorldMock();
		int duration = 20;
		world.setThunderDuration(duration);
		assertEquals(duration, world.getThunderDuration(), "Weather duration should be more than zero");
		assertFalse(world.isThundering());
	}

	@Test
	void setThundering_True_ThunderDurationShouldBePositive()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		assertEquals(0, world.getThunderDuration(), "Weather duration should be reset to zero");
		assertTrue(world.isThundering());
	}

	@Test
	void onCreated_TimeSetToBeZero()
	{
		WorldMock world = new WorldMock();
		assertEquals(0L, world.getFullTime(), "World time should be zero");
		assertEquals(0L, world.getTime(), "Day time should be zero");
	}

	@Test
	void setTime_DayTimeValue()
	{
		WorldMock world = new WorldMock();
		world.setTime(20L);
		assertEquals(20L, world.getTime());
		assertEquals(20L, world.getFullTime());
	}

	@Test
	void setTime_FullTimeValue()
	{
		WorldMock world = new WorldMock();
		world.setFullTime(3L * 24000L + 20L);
		assertEquals(20L, world.getTime());
		assertEquals(3L * 24000L + 20L, world.getFullTime());
	}

	@Test
	void setTime_FullTimeShouldBeAdjustedWithDayTime()
	{
		WorldMock world = new WorldMock();
		world.setFullTime(3L * 24000L + 20L);
		world.setTime(12000L);
		assertEquals(12000L, world.getTime());
		assertEquals(3L * 24000L + 12000L, world.getFullTime());
	}

	@Test
	void setTime_Event_Triggered()
	{
		WorldMock world = new WorldMock();
		world.setTime(6000L);
		world.setTime(10000L);
		server.getPluginManager().assertEventFired(TimeSkipEvent.class, event ->
		        event.getSkipAmount() == 4000L && event.getSkipReason() == TimeSkipEvent.SkipReason.CUSTOM);
	}

	@Test
	void onCreated_EnvironmentSetToBeNormal()
	{
		WorldMock world = new WorldMock();
		assertEquals(World.Environment.NORMAL, world.getEnvironment(), "World environment type should be normal");
	}

	@Test
	void getLoadedChunks_EmptyWorldHasNoLoadedChunks()
	{
		WorldMock world = new WorldMock();
		assertEquals(0, world.getLoadedChunks().length);
	}

	@Test
	void isChunkLoaded_IsFalseForUnloadedChunk()
	{
		WorldMock world = new WorldMock();
		assertFalse(world.isChunkLoaded(0, 0));
	}

	@Test
	void isChunkloaded_IsTrueForLoadedChunk()
	{
		WorldMock world = new WorldMock();
		BlockMock block = world.getBlockAt(64, 64, 64);
		assertNotNull(block.getChunk());
		Chunk chunk = block.getChunk();
		assertTrue(world.isChunkLoaded(chunk.getX(), chunk.getZ()));
	}

	@Test
	void getBlockState_ChangeBlock()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(Material.DIRT, world.getType(0, 1, 0));
		Location location = new Location(world, 0, 1, 0);
		Block block = world.getBlockAt(0, 1, 0);
		BlockMock block2 = new BlockMock();
		block2.setBlockData(block.getBlockData());
		BlockStateMock state = BlockStateMock.mockState(block);
		block2.setState(state);
		assertEquals(block2.getState().getType(), world.getBlockState(0, 1, 0).getType());
		assertEquals(block2.getState().getType(), world.getBlockState(location).getType());

	}

	@Test
	void setBlock_ChangeBlock()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location location = new Location(world, 0, 1, 0);
		Block block = world.getBlockAt(0, 1, 0);
		BlockData data = block.getBlockData();
		assertEquals(data.getMaterial(), world.getBlockData(location).getMaterial());
		BlockDataMock mock = new BlockDataMock(Material.GRASS);
		BlockDataMock mock2 = new BlockDataMock(Material.GRASS_BLOCK);
		world.setBlockData(location, mock);
		assertEquals(Material.GRASS, world.getBlockData(location).getMaterial());
		assertEquals(Material.GRASS, world.getBlockData(0, 1, 0).getMaterial());
		assertEquals(Material.GRASS, world.getType(0, 1, 0));
		world.setBlockData(0, 1, 0, mock2);
		assertEquals(Material.GRASS_BLOCK, world.getBlockData(location).getMaterial());
		assertEquals(Material.GRASS_BLOCK, world.getType(location));
		world.setType(location, Material.BEDROCK);
		assertEquals(Material.BEDROCK, world.getType(location));
		world.setType(0, 1, 0, Material.DIRT);
		assertEquals(Material.DIRT, world.getType(location));
	}

	@Test
	void worldPlayEffect()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.playEffect(new Location(world, 0, 0, 0), Effect.STEP_SOUND, Material.STONE);
	}

	@Test
	void testDropItem()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ItemStack item = new ItemStack(Material.DIAMOND);
		Location location = new Location(world, 100, 100, 100);

		Item entity = world.dropItem(location, item);

		// Is this the same Item we wanted to drop?
		assertEquals(item, entity.getItemStack());

		// Does our Item exist in the correct World?
		assertTrue(world.getEntities().contains(entity));

		// Is it at the right location?
		assertEquals(location, entity.getLocation());
	}

	@Test
	void testDropItemNaturally()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ItemStack item = new ItemStack(Material.EMERALD);
		Location location = new Location(world, 200, 100, 200);

		Item entity = world.dropItemNaturally(location, item);

		// Is this the same Item we wanted to drop?
		assertEquals(item, entity.getItemStack());

		// Does our Item exist in the correct World?
		assertTrue(world.getEntities().contains(entity));

		// Has the Location been slightly nudged?
		assertNotEquals(location, entity.getLocation());
	}

	@Test
	void testDropItemConsumer()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ItemStack item = new ItemStack(Material.BEACON);
		Location location = new Location(world, 200, 50, 500);

		Item entity = world.dropItem(location, item, n ->
		{
			// This consumer should be invoked BEFORE the actually spawned.
			assertFalse(world.getEntities().contains(n));
		});

		assertEquals(item, entity.getItemStack());
		assertTrue(world.getEntities().contains(entity));
	}

	@Test
	void drop_Item_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.dropItem(new Location(world, 0, 5, 0), new ItemStack(Material.STONE));
		server.getPluginManager().assertEventFired(ItemSpawnEvent.class, (e) -> !e.isCancelled());
	}

	@Test
	void spawn_NullLocation_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(IllegalArgumentException.class, () -> world.spawn(null, Zombie.class));
	}

	@Test
	void spawn_NullClass_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(IllegalArgumentException.class, () -> world.spawn(new Location(world, 0, 5, 0), null));
	}

	@Test
	void worldPlayEffect_NullData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location loc = new Location(world, 0, 0, 0);
		assertThrows(IllegalArgumentException.class, () ->
		{
			world.playEffect(loc, Effect.STEP_SOUND, null);
		});
	}

	@Test
	void worldPlayEffect_IncorrectData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location loc = new Location(world, 0, 0, 0);
		assertThrows(IllegalArgumentException.class, () ->
		{
			world.playEffect(loc, Effect.STEP_SOUND, 1.0f);
		});
	}

	@Test
	@SuppressWarnings("UnstableApiUsage")
	void testSendPluginMessage()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		MockPlugin plugin = MockBukkit.createMockPlugin();
		server.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("MockBukkit");
		world.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}

	@Test
	void onCreated_WeatherDurations_Zero()
	{
		WorldMock world = new WorldMock();
		assertEquals(0, world.getWeatherDuration());
		assertEquals(0, world.getThunderDuration());
		assertEquals(0, world.getClearWeatherDuration());
	}

	@Test
	void onCreated_Weather()
	{
		WorldMock world = new WorldMock();
		assertTrue(world.isClearWeather());
		assertFalse(world.isThundering());
		assertFalse(world.hasStorm());
	}

	@Test
	void setStorm_ChangeState_CallsEvent()
	{
		WorldMock world = new WorldMock();
		world.setStorm(true);
		server.getPluginManager().assertEventFired(WeatherChangeEvent.class, event ->
				event.getWorld().equals(world) && event.toWeatherState());
		world.setStorm(false);
		server.getPluginManager().assertEventFired(WeatherChangeEvent.class, event ->
				event.getWorld().equals(world) && !event.toWeatherState());
	}

	@Test
	void setStorm_SameState_DoesntCallEvent()
	{
		WorldMock world = new WorldMock();
		world.setStorm(false);
		server.getPluginManager().assertEventNotFired(WeatherChangeEvent.class);
	}

	@Test
	void setStorm_SetsStorming()
	{
		WorldMock world = new WorldMock();
		world.setStorm(true);
		assertTrue(world.hasStorm());
	}

	@Test
	void setStorm_ResetsWeatherDuration()
	{
		WorldMock world = new WorldMock();
		world.setStorm(true);
		assertEquals(0, world.getWeatherDuration());
	}

	@Test
	void setStorm_ResetsClearWeatherDuration()
	{
		WorldMock world = new WorldMock();
		world.setStorm(true);
		assertEquals(0, world.getClearWeatherDuration());
	}

	@Test
	void setWeatherDuration_SetsDuration()
	{
		WorldMock world = new WorldMock();
		world.setWeatherDuration(10);
		assertEquals(10, world.getWeatherDuration());
	}

	@Test
	void setThundering_ChangeState_CallsEvent()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		server.getPluginManager().assertEventFired(ThunderChangeEvent.class, event ->
				event.getWorld().equals(world) && event.toThunderState());
		world.setThundering(false);
		server.getPluginManager().assertEventFired(ThunderChangeEvent.class, event ->
				event.getWorld().equals(world) && !event.toThunderState());
	}

	@Test
	void setThundering_SameState_DoesntCallEvent()
	{
		WorldMock world = new WorldMock();
		world.setThundering(false);
		server.getPluginManager().assertEventNotFired(ThunderChangeEvent.class);
	}

	@Test
	void setThundering_SetsThundering()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		assertTrue(world.isThundering());
	}

	@Test
	void setThundering_ResetsThunderingDuration()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		assertEquals(0, world.getThunderDuration());
	}

	@Test
	void setThundering_ResetsClearWeatherDuration()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		assertEquals(0, world.getClearWeatherDuration());
	}

	@Test
	void setThunderDuration_SetsDuration()
	{
		WorldMock world = new WorldMock();
		world.setThunderDuration(10);
		assertEquals(10, world.getThunderDuration());
	}

	@Test
	void isClearWeather_ClearWeather()
	{
		WorldMock world = new WorldMock();
		assertTrue(world.isClearWeather());
	}

	@Test
	void isClearWeather_Thundering_False()
	{
		WorldMock world = new WorldMock();
		world.setThundering(true);
		assertFalse(world.isClearWeather());
	}

	@Test
	void isClearWeather_Storming_False()
	{
		WorldMock world = new WorldMock();
		world.setStorm(true);
		assertFalse(world.isClearWeather());
	}

	@Test
	void spawn_AddedToEntities()
	{
		WorldMock world = new WorldMock();
		Entity zombie = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ZOMBIE);
		assertEquals(1, world.getEntities().size());
		assertEquals(zombie, world.getEntities().get(0));
	}

	@Test
	void spawn_CorrectLocation()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 100, 20, 50);
		Entity zombie = world.spawnEntity(location, EntityType.ZOMBIE);
		assertEquals(100, zombie.getLocation().getBlockX());
		assertEquals(20, zombie.getLocation().getBlockY());
		assertEquals(50, zombie.getLocation().getBlockZ());
	}

	@Test
	void spawn_ArmorStand_CorrectType()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ARMOR_STAND);
		assertInstanceOf(ArmorStandMock.class, entity);
	}

	@Test
	void spawn_ArmorStand_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ARMOR_STAND);
		server.getPluginManager().assertEventFired(CreatureSpawnEvent.class, (e) -> e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM);
		server.getPluginManager().assertEventFired(CreatureSpawnEvent.class, (e) -> !e.isCancelled());
	}

	@Test
	void spawn_ExperienceOrb_CorrectType()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.EXPERIENCE_ORB);
		assertInstanceOf(ExperienceOrbMock.class, entity);
	}

	@Test
	void spawn_Firework_CorrectType()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.FIREWORK);
		assertInstanceOf(FireworkMock.class, entity);
	}

	@Test
	void spawn_Firework_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.FIREWORK);
		server.getPluginManager().assertEventFired(ProjectileLaunchEvent.class, (e) -> !e.isCancelled());
	}

	@Test
	void spawn_Item_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(IllegalArgumentException.class, () -> world.spawnEntity(new Location(world, 0, 5, 0), EntityType.DROPPED_ITEM));
	}

	@Test
	void spawn_Player_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(IllegalArgumentException.class, () -> world.spawnEntity(new Location(world, 0, 5, 0), EntityType.PLAYER));
	}

	@Test
	void spawn_Zombie_CorrectType()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ZOMBIE);
		assertInstanceOf(ZombieMock.class, entity);
	}

	@Test
	void spawn_Zombie_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		Entity entity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ZOMBIE);
		server.getPluginManager().assertEventFired(CreatureSpawnEvent.class, (e) -> e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM);
		server.getPluginManager().assertEventFired(CreatureSpawnEvent.class, (e) -> !e.isCancelled());
	}

	@Test
	void setDifficulty()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertNotNull(world.getDifficulty());
		world.setDifficulty(Difficulty.HARD);
		assertEquals(Difficulty.HARD, world.getDifficulty());
	}

	@Test
	void spawnMonster_Peaceful()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setDifficulty(Difficulty.PEACEFUL);
		Entity zombie = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		assertFalse(zombie.isValid());
		assertTrue(zombie.isDead());
	}

	@Test
	void spawnFriendly_Peaceful()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setDifficulty(Difficulty.PEACEFUL);
		Entity armorStand = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);
		assertTrue(armorStand.isValid());
		assertFalse(armorStand.isDead());
	}

}
