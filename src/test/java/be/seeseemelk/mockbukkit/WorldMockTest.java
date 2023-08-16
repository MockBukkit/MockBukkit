package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.block.state.BlockStateMock;
import be.seeseemelk.mockbukkit.entity.AllayMock;
import be.seeseemelk.mockbukkit.entity.AreaEffectCloudMock;
import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import be.seeseemelk.mockbukkit.entity.AxolotlMock;
import be.seeseemelk.mockbukkit.entity.BatMock;
import be.seeseemelk.mockbukkit.entity.BeeMock;
import be.seeseemelk.mockbukkit.entity.BlazeMock;
import be.seeseemelk.mockbukkit.entity.CamelMock;
import be.seeseemelk.mockbukkit.entity.CatMock;
import be.seeseemelk.mockbukkit.entity.CaveSpiderMock;
import be.seeseemelk.mockbukkit.entity.ChickenMock;
import be.seeseemelk.mockbukkit.entity.CodMock;
import be.seeseemelk.mockbukkit.entity.CommandMinecartMock;
import be.seeseemelk.mockbukkit.entity.CowMock;
import be.seeseemelk.mockbukkit.entity.CreeperMock;
import be.seeseemelk.mockbukkit.entity.DonkeyMock;
import be.seeseemelk.mockbukkit.entity.DragonFireballMock;
import be.seeseemelk.mockbukkit.entity.EggMock;
import be.seeseemelk.mockbukkit.entity.ElderGuardianMock;
import be.seeseemelk.mockbukkit.entity.EndermanMock;
import be.seeseemelk.mockbukkit.entity.ExperienceOrbMock;
import be.seeseemelk.mockbukkit.entity.ExplosiveMinecartMock;
import be.seeseemelk.mockbukkit.entity.FireballMock;
import be.seeseemelk.mockbukkit.entity.FireworkMock;
import be.seeseemelk.mockbukkit.entity.FoxMock;
import be.seeseemelk.mockbukkit.entity.FrogMock;
import be.seeseemelk.mockbukkit.entity.GhastMock;
import be.seeseemelk.mockbukkit.entity.GiantMock;
import be.seeseemelk.mockbukkit.entity.GoatMock;
import be.seeseemelk.mockbukkit.entity.GuardianMock;
import be.seeseemelk.mockbukkit.entity.HopperMinecartMock;
import be.seeseemelk.mockbukkit.entity.HorseMock;
import be.seeseemelk.mockbukkit.entity.ItemEntityMock;
import be.seeseemelk.mockbukkit.entity.LlamaMock;
import be.seeseemelk.mockbukkit.entity.MuleMock;
import be.seeseemelk.mockbukkit.entity.MushroomCowMock;
import be.seeseemelk.mockbukkit.entity.PigMock;
import be.seeseemelk.mockbukkit.entity.PolarBearMock;
import be.seeseemelk.mockbukkit.entity.PoweredMinecartMock;
import be.seeseemelk.mockbukkit.entity.PufferFishMock;
import be.seeseemelk.mockbukkit.entity.RideableMinecartMock;
import be.seeseemelk.mockbukkit.entity.SalmonMock;
import be.seeseemelk.mockbukkit.entity.SheepMock;
import be.seeseemelk.mockbukkit.entity.SkeletonHorseMock;
import be.seeseemelk.mockbukkit.entity.SkeletonMock;
import be.seeseemelk.mockbukkit.entity.SmallFireballMock;
import be.seeseemelk.mockbukkit.entity.SpawnerMinecartMock;
import be.seeseemelk.mockbukkit.entity.SpiderMock;
import be.seeseemelk.mockbukkit.entity.StorageMinecartMock;
import be.seeseemelk.mockbukkit.entity.StrayMock;
import be.seeseemelk.mockbukkit.entity.TadpoleMock;
import be.seeseemelk.mockbukkit.entity.TropicalFishMock;
import be.seeseemelk.mockbukkit.entity.WardenMock;
import be.seeseemelk.mockbukkit.entity.WitherSkeletonMock;
import be.seeseemelk.mockbukkit.entity.WitherSkullMock;
import be.seeseemelk.mockbukkit.entity.WolfMock;
import be.seeseemelk.mockbukkit.entity.ZombieHorseMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.papermc.paper.event.world.WorldGameRuleChangeEvent;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class WorldMockTest
{

	@MockBukkitInject
	private ServerMock server;

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
	void getLivingEntities()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		world.dropItem(new Location(world, 0, 0, 0), new ItemStack(Material.STONE));
		assertEquals(2, world.getEntities().size());
		assertEquals(1, world.getLivingEntities().size());
	}

	@Test
	void getLivingEntities_EmptyList()
	{
		WorldMock world = new WorldMock();
		List<LivingEntity> entities = world.getLivingEntities();
		assertNotNull(entities);
		assertEquals(0, entities.size());
	}

	@Test
	void getEntitiesByClass()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		world.dropItem(new Location(world, 0, 0, 0), new ItemStack(Material.STONE));
		assertEquals(1, world.getEntitiesByClass(ZombieMock.class).size());
		assertEquals(1, world.getEntitiesByClass(ItemEntityMock.class).size());
	}

	@Test
	void getEntitiesByClasses()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		world.dropItem(new Location(world, 0, 0, 0), new ItemStack(Material.STONE));
		assertEquals(1, world.getEntitiesByClasses(ZombieMock.class).size());
		assertEquals(1, world.getEntitiesByClasses(ItemEntityMock.class).size());
		assertEquals(2, world.getEntitiesByClasses(ZombieMock.class, ItemEntityMock.class).size());
	}

	@Test
	@SuppressWarnings("unchecked")
	void getEntitiesByClasses_Generic()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		world.dropItem(new Location(world, 0, 0, 0), new ItemStack(Material.STONE));
		assertEquals(1, world.getEntitiesByClass(new Class[]{ ZombieMock.class }).size());
		assertEquals(1, world.getEntitiesByClass(new Class[]{ ItemEntityMock.class }).size());
		assertEquals(2, world.getEntitiesByClass(new Class[]{ ZombieMock.class, ItemEntityMock.class }).size());
	}

	@Test
	void getNearbyEntities_DifferentLocations()
	{
		WorldMock world = server.addSimpleWorld("world");
		Location centerLoc = new Location(world, 0, 0, 0);
		world.spawnEntity(centerLoc, EntityType.ZOMBIE);
		world.spawnEntity(centerLoc.add(64, 0, 64), EntityType.ZOMBIE);
		assertEquals(1, world.getNearbyEntities(
				centerLoc, 16, 1, 16).size());
	}

	@Test
	void getNearbyEntities_TypeFilter()
	{
		WorldMock world = server.addSimpleWorld("world");
		Location centerLoc = new Location(world, 0, 0, 0);
		world.spawnEntity(centerLoc, EntityType.ZOMBIE);
		world.spawnEntity(centerLoc, EntityType.ARMOR_STAND);
		assertEquals(1, world.getNearbyEntities(
				centerLoc, 1, 1, 1, (e) -> e instanceof ZombieMock).size());
	}

	@Test
	void getNearbyEntities_InBoundingBox()
	{
		WorldMock world = server.addSimpleWorld("world");
		Location centerLoc = new Location(world, 0, 0, 0);
		Location insideLoc = new Location(world, 0, 1, 7);
		Location outsideLoc = new Location(world, 0, 1, 8);
		world.spawnEntity(insideLoc, EntityType.LLAMA);
		world.spawnEntity(insideLoc, EntityType.LLAMA);
		world.spawnEntity(insideLoc, EntityType.FROG);
		world.spawnEntity(outsideLoc, EntityType.POLAR_BEAR);
		BoundingBox box = BoundingBox.of(centerLoc, 1,2,8);
		assertEquals(3, world.getNearbyEntities(box).size());
	}

	@Test
	void getNearbyEntities_InBoundingBox_TypeFilter()
	{
		WorldMock world = server.addSimpleWorld("world");
		Location centerLoc = new Location(world, 0, 0, 0);
		Location insideLoc = new Location(world, 0, 63, 7);
		Location outsideLoc = new Location(world, 0, 63, 8);
		world.spawnEntity(insideLoc, EntityType.LLAMA);
		world.spawnEntity(insideLoc, EntityType.LLAMA);
		world.spawnEntity(insideLoc, EntityType.FROG);
		world.spawnEntity(outsideLoc, EntityType.POLAR_BEAR);
		BoundingBox box = BoundingBox.of(centerLoc, 1,64,8);
		assertEquals(2, world.getNearbyEntities(box, (e) -> e instanceof LlamaMock).size());
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
	void getWorldBorder_NotNull()
	{
		WorldMock worldMock = new WorldMock();

		assertNotNull(worldMock.getWorldBorder());
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
	void getDefaultBiome()
	{
		WorldMock world = new WorldMock(Material.GRASS_BLOCK, Biome.JUNGLE, 0, 256);
		Biome biome = world.getBiome(0, 0, 0);
		assertNotNull(biome);
		assertEquals(Biome.JUNGLE, biome);
	}

	@Test
	void getBiomeLegacy()
	{
		WorldMock world = new WorldMock(Material.GRASS_BLOCK, Biome.JUNGLE, 0, 256);
		Biome biome3d = world.getBiome(0, 0, 0);
		Biome biome2d = world.getBiome(0, 0);
		assertNotNull(biome3d);
		assertNotNull(biome2d);
		assertEquals(biome3d, biome2d);
	}

	@Test
	void setBiome()
	{
		WorldMock world = new WorldMock(Material.GRASS_BLOCK, Biome.JUNGLE, 0, 256);
		world.setBiome(0, 0, 0, Biome.DESERT);
		Biome biome = world.getBiome(0, 0, 0);
		assertEquals(Biome.DESERT, biome);
	}

	@Test
	void setBiome_CustomFails()
	{
		WorldMock world = new WorldMock(Material.GRASS_BLOCK, Biome.JUNGLE, 0, 256);
		assertThrows(IllegalArgumentException.class, () ->
		{
			world.setBiome(0, 0, 0, Biome.CUSTOM);
		});
	}

	@Test
	void worldPlayEffect()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		world.playEffect(new Location(world, 0, 0, 0), Effect.STEP_SOUND, blockData);
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
		world.dropItem(new Location(world, 0, 5, 0), new ItemStack(Material.STONE));
		server.getPluginManager().assertEventFired(ItemSpawnEvent.class, (e) -> !e.isCancelled());
	}

	@Test
	void spawn_NullLocation_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(NullPointerException.class, () -> world.spawn(null, Zombie.class));
	}

	@Test
	void spawn_NullClass_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(NullPointerException.class, () -> world.spawn(new Location(world, 0, 5, 0), null));
	}

	@Test
	void spawn_NullReason_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(NullPointerException.class, () -> world.spawn(new Location(world, 0, 5, 0), Zombie.class, (CreatureSpawnEvent.SpawnReason) null));
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
	void spawn_ArmorStand_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ARMOR_STAND);
		server.getPluginManager().assertEventFired(CreatureSpawnEvent.class, (e) -> e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM);
		server.getPluginManager().assertEventFired(CreatureSpawnEvent.class, (e) -> !e.isCancelled());
	}

	@Test
	void spawn_Firework_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 5, 0), EntityType.FIREWORK);
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
	void spawn_Zombie_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ZOMBIE);
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

	@Test
	void testGetAllowAnimalsDefault()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertTrue(world.getAllowAnimals());
	}

	@Test
	void testGetAllowMonstersDefault()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertTrue(world.getAllowMonsters());
	}

	@Test
	void testSetSpawnFlags()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);

		world.setSpawnFlags(false, true);
		assertFalse(world.getAllowMonsters());
		assertTrue(world.getAllowAnimals());

		world.setSpawnFlags(true, false);
		assertTrue(world.getAllowMonsters());
		assertFalse(world.getAllowAnimals());
	}

	@Test
	void testCallSpawnEventOnDisallowedMonster()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setSpawnFlags(false, true);
		Entity zombie = world.spawn(new Location(world, 0, 0, 0), Zombie.class, CreatureSpawnEvent.SpawnReason.NATURAL);
		assertFalse(zombie.isValid());
		assertTrue(zombie.isDead());
	}

	@Test
	void testCallSpawnEventOnDisallowedAnimal()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setSpawnFlags(true, false);
		Entity sheep = world.spawn(new Location(world, 0, 0, 0), Sheep.class, CreatureSpawnEvent.SpawnReason.NATURAL);
		assertFalse(sheep.isValid());
	}

	@Test
	void getEmptyChunkSnapshot_AllBlocksAir()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(0, 0, true, true);

		for (int x = 0; x < 16; x++)
		{
			for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++)
			{
				for (int z = 0; z < 16; z++)
				{
					assertEquals(Material.AIR, snapshot.getBlockData(x, y, z).getMaterial());
				}
			}
		}
	}

	@Test
	void getEmptyChunkSnapshot_AllBiomesPlains()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(0, 0, true, true);

		for (int x = 0; x < 16; x++)
		{
			for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++)
			{
				for (int z = 0; z < 16; z++)
				{
					assertEquals(Biome.PLAINS, snapshot.getBiome(x, y, z));
				}
			}
		}
	}

	@Test
	void getEmptyChunkSnapshot_NoBiome_Error()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(0, 0, false, false);

		assertThrowsExactly(IllegalStateException.class, () -> snapshot.getBiome(0, 0, 0));
	}

	@Test
	void getEmptyChunkSnapshot_EitherBiome_ReturnsBiome()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);

		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(0, 0, true, false);
		assertEquals(Biome.PLAINS, snapshot.getBiome(0, 0, 0));

		snapshot = world.getEmptyChunkSnapshot(0, 0, false, true);
		assertEquals(Biome.PLAINS, snapshot.getBiome(0, 0, 0));
	}

	@Test
	void getEmptyChunkSnapshot_CorrectCoords()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(69, 420, false, false);

		assertEquals(69, snapshot.getX());
		assertEquals(420, snapshot.getZ());
	}

	@Test
	void getEmptyChunkSnapshot_CorrectName()
	{
		WorldMock world = new WorldMock(Material.GRASS_BLOCK, -64, 319, 3);
		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(0, 0, false, false);

		assertEquals("World", snapshot.getWorldName());
	}

	@Test
	void getEmptyChunkSnapshot_CorrectTime()
	{
		WorldMock world = new WorldMock(Material.GRASS_BLOCK, -64, 319, 3);
		world.setFullTime(69);

		ChunkSnapshot snapshot = world.getEmptyChunkSnapshot(0, 0, false, false);

		assertEquals(69, snapshot.getCaptureFullTime());
	}

	@ParameterizedTest
	@MethodSource("getSpawnableEntities")
	void testSpawnEntity(EntityType type, Class<? extends Entity> expectedClass)
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), type);
		assertInstanceOf(expectedClass, entity);
		assertTrue(entity.isValid());
	}

	public static Stream<Arguments> getSpawnableEntities()
	{
		return Stream.of(
				Arguments.of(EntityType.WITHER_SKULL, WitherSkullMock.class),
				Arguments.of(EntityType.DRAGON_FIREBALL, DragonFireballMock.class),
				Arguments.of(EntityType.FIREBALL, FireballMock.class),
				Arguments.of(EntityType.SMALL_FIREBALL, SmallFireballMock.class),
				Arguments.of(EntityType.ELDER_GUARDIAN, ElderGuardianMock.class),
				Arguments.of(EntityType.GUARDIAN, GuardianMock.class),
				Arguments.of(EntityType.POLAR_BEAR, PolarBearMock.class),
				Arguments.of(EntityType.PIG, PigMock.class),
				Arguments.of(EntityType.EGG, EggMock.class),
				Arguments.of(EntityType.WOLF, WolfMock.class),
				Arguments.of(EntityType.CREEPER, CreeperMock.class),
				Arguments.of(EntityType.GOAT, GoatMock.class),
				Arguments.of(EntityType.BEE, BeeMock.class),
				Arguments.of(EntityType.PUFFERFISH, PufferFishMock.class),
				Arguments.of(EntityType.TROPICAL_FISH, TropicalFishMock.class),
				Arguments.of(EntityType.SALMON, SalmonMock.class),
				Arguments.of(EntityType.COD, CodMock.class),
				Arguments.of(EntityType.TADPOLE, TadpoleMock.class),
				Arguments.of(EntityType.MUSHROOM_COW, MushroomCowMock.class),
				Arguments.of(EntityType.GHAST, GhastMock.class),
				Arguments.of(EntityType.FOX, FoxMock.class),
				Arguments.of(EntityType.FROG, FrogMock.class),
				Arguments.of(EntityType.CAT, CatMock.class),
				Arguments.of(EntityType.BAT, BatMock.class),
				Arguments.of(EntityType.AXOLOTL, AxolotlMock.class),
				Arguments.of(EntityType.GIANT, GiantMock.class),
				Arguments.of(EntityType.CAVE_SPIDER, CaveSpiderMock.class),
				Arguments.of(EntityType.WITHER_SKELETON, WitherSkeletonMock.class),
				Arguments.of(EntityType.SPIDER, SpiderMock.class),
				Arguments.of(EntityType.STRAY, StrayMock.class),
				Arguments.of(EntityType.BLAZE, BlazeMock.class),
				Arguments.of(EntityType.CHICKEN, ChickenMock.class),
				Arguments.of(EntityType.SKELETON, SkeletonMock.class),
				Arguments.of(EntityType.COW, CowMock.class),
				Arguments.of(EntityType.ZOMBIE_HORSE, ZombieHorseMock.class),
				Arguments.of(EntityType.SKELETON_HORSE, SkeletonHorseMock.class),
				Arguments.of(EntityType.MULE, MuleMock.class),
				Arguments.of(EntityType.DONKEY, DonkeyMock.class),
				Arguments.of(EntityType.LLAMA, LlamaMock.class),
				Arguments.of(EntityType.WARDEN, WardenMock.class),
				Arguments.of(EntityType.ENDERMAN, EndermanMock.class),
				Arguments.of(EntityType.ALLAY, AllayMock.class),
				Arguments.of(EntityType.SHEEP, SheepMock.class),
				Arguments.of(EntityType.HORSE, HorseMock.class),
				Arguments.of(EntityType.ARMOR_STAND, ArmorStandMock.class),
				Arguments.of(EntityType.ZOMBIE, ZombieMock.class),
				Arguments.of(EntityType.FIREWORK, FireworkMock.class),
				Arguments.of(EntityType.EXPERIENCE_ORB, ExperienceOrbMock.class),
				Arguments.of(EntityType.MINECART_FURNACE, PoweredMinecartMock.class),
				Arguments.of(EntityType.CAMEL, CamelMock.class),
				Arguments.of(EntityType.MINECART_COMMAND, CommandMinecartMock.class),
				Arguments.of(EntityType.MINECART_TNT, ExplosiveMinecartMock.class),
				Arguments.of(EntityType.MINECART_HOPPER, HopperMinecartMock.class),
				Arguments.of(EntityType.MINECART_MOB_SPAWNER, SpawnerMinecartMock.class),
				Arguments.of(EntityType.MINECART, RideableMinecartMock.class),
				Arguments.of(EntityType.MINECART_CHEST, StorageMinecartMock.class),
				Arguments.of(EntityType.AREA_EFFECT_CLOUD, AreaEffectCloudMock.class)
		);
	}

	@Test
	void testGetGameRules()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String[] gameRules = world.getGameRules();
		assertNotEquals(0, Arrays.stream(gameRules).count());
	}

	@Test
	void testGetGameRuleValue()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String gameRuleValue = world.getGameRuleValue("doFireTick");
		assertEquals("true", gameRuleValue);
	}

	@Test
	void testGetGameRuleValueNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String gameRuleValue = world.getGameRuleValue((String) null);
		assertNull(gameRuleValue);
	}

	@Test
	void testGetGameRuleNonExistent()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String gameRuleValue = world.getGameRuleValue("test");
		assertNull(gameRuleValue);
	}

	@Test
	void testIsGameRule()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertTrue(world.isGameRule("doFireTick"));
	}

	@Test
	void testIsGameRuleNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertFalse(world.isGameRule(null));
	}

	@Test
	void testIsGameRuleNonExistent()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertFalse(world.isGameRule("test"));
	}

	@Test
	void testSetGameRuleValue()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setGameRuleValue("announceAdvancements", "false");
		assertEquals("false", world.getGameRuleValue("announceAdvancements"));
		server.getPluginManager().assertEventFired(WorldGameRuleChangeEvent.class, worldGameRuleChangeEvent -> {
			return worldGameRuleChangeEvent.getGameRule().equals(GameRule.ANNOUNCE_ADVANCEMENTS)
					&& worldGameRuleChangeEvent.getValue().equals("false");
		});
		world.setGameRuleValue("announceAdvancements", "true");
		assertEquals("true", world.getGameRuleValue("announceAdvancements"));
		server.getPluginManager().assertEventFired(WorldGameRuleChangeEvent.class, worldGameRuleChangeEvent -> {
			return worldGameRuleChangeEvent.getGameRule().equals(GameRule.ANNOUNCE_ADVANCEMENTS)
					&& worldGameRuleChangeEvent.getValue().equals("true");
		});
	}

	@Test
	void testSetGameRuleValueNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setGameRuleValue(null, "false");
		assertNull(world.getGameRuleValue((String) null));
	}

	@Test
	void testSetGameRuleValueNonExistent()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setGameRuleValue("test", "false");
		assertNull(world.getGameRuleValue("test"));
	}

	@Test
	void testSetGameRuleValueInteger()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setGameRuleValue("randomTickSpeed", "10");
		assertEquals("10", world.getGameRuleValue("randomTickSpeed"));
		server.getPluginManager().assertEventFired(WorldGameRuleChangeEvent.class, worldGameRuleChangeEvent -> {
			return worldGameRuleChangeEvent.getGameRule().equals(GameRule.RANDOM_TICK_SPEED)
					&& worldGameRuleChangeEvent.getValue().equals("10");
		});
	}

	@Test
	void testSetGameRuleValueIntegerNonParseable()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String randomTickSpeed = world.getGameRuleValue("randomTickSpeed");
		world.setGameRuleValue("randomTickSpeed", "test");
		assertEquals(randomTickSpeed, world.getGameRuleValue("randomTickSpeed"));
	}

}
