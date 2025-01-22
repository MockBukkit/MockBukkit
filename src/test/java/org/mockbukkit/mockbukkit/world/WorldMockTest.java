package org.mockbukkit.mockbukkit.world;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.papermc.paper.event.world.WorldGameRuleChangeEvent;
import io.papermc.paper.world.MoonPhase;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameRule;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;
import org.mockbukkit.mockbukkit.block.state.BlockStateMock;
import org.mockbukkit.mockbukkit.entity.AllayMock;
import org.mockbukkit.mockbukkit.entity.AreaEffectCloudMock;
import org.mockbukkit.mockbukkit.entity.ArmadilloMock;
import org.mockbukkit.mockbukkit.entity.ArmorStandMock;
import org.mockbukkit.mockbukkit.entity.AxolotlMock;
import org.mockbukkit.mockbukkit.entity.BatMock;
import org.mockbukkit.mockbukkit.entity.BeeMock;
import org.mockbukkit.mockbukkit.entity.BlazeMock;
import org.mockbukkit.mockbukkit.entity.BlockDisplayMock;
import org.mockbukkit.mockbukkit.entity.BoggedMock;
import org.mockbukkit.mockbukkit.entity.BreezeMock;
import org.mockbukkit.mockbukkit.entity.BreezeWindChargeMock;
import org.mockbukkit.mockbukkit.entity.CamelMock;
import org.mockbukkit.mockbukkit.entity.CatMock;
import org.mockbukkit.mockbukkit.entity.CaveSpiderMock;
import org.mockbukkit.mockbukkit.entity.ChickenMock;
import org.mockbukkit.mockbukkit.entity.CodMock;
import org.mockbukkit.mockbukkit.entity.CommandMinecartMock;
import org.mockbukkit.mockbukkit.entity.CowMock;
import org.mockbukkit.mockbukkit.entity.CreakingMock;
import org.mockbukkit.mockbukkit.entity.CreeperMock;
import org.mockbukkit.mockbukkit.entity.DolphinMock;
import org.mockbukkit.mockbukkit.entity.DonkeyMock;
import org.mockbukkit.mockbukkit.entity.DragonFireballMock;
import org.mockbukkit.mockbukkit.entity.EggMock;
import org.mockbukkit.mockbukkit.entity.ElderGuardianMock;
import org.mockbukkit.mockbukkit.entity.EnderCrystalMock;
import org.mockbukkit.mockbukkit.entity.EnderDragonMock;
import org.mockbukkit.mockbukkit.entity.EnderPearlMock;
import org.mockbukkit.mockbukkit.entity.EnderSignalMock;
import org.mockbukkit.mockbukkit.entity.EndermanMock;
import org.mockbukkit.mockbukkit.entity.EndermiteMock;
import org.mockbukkit.mockbukkit.entity.EvokerFangsMock;
import org.mockbukkit.mockbukkit.entity.EvokerMock;
import org.mockbukkit.mockbukkit.entity.ExperienceOrbMock;
import org.mockbukkit.mockbukkit.entity.ExplosiveMinecartMock;
import org.mockbukkit.mockbukkit.entity.FallingBlockMock;
import org.mockbukkit.mockbukkit.entity.FireballMock;
import org.mockbukkit.mockbukkit.entity.FireworkMock;
import org.mockbukkit.mockbukkit.entity.FishHookMock;
import org.mockbukkit.mockbukkit.entity.FoxMock;
import org.mockbukkit.mockbukkit.entity.FrogMock;
import org.mockbukkit.mockbukkit.entity.GhastMock;
import org.mockbukkit.mockbukkit.entity.GiantMock;
import org.mockbukkit.mockbukkit.entity.GlowItemFrameMock;
import org.mockbukkit.mockbukkit.entity.GlowSquidMock;
import org.mockbukkit.mockbukkit.entity.GoatMock;
import org.mockbukkit.mockbukkit.entity.GuardianMock;
import org.mockbukkit.mockbukkit.entity.HopperMinecartMock;
import org.mockbukkit.mockbukkit.entity.HorseMock;
import org.mockbukkit.mockbukkit.entity.HuskMock;
import org.mockbukkit.mockbukkit.entity.IllusionerMock;
import org.mockbukkit.mockbukkit.entity.InteractionMock;
import org.mockbukkit.mockbukkit.entity.IronGolemMock;
import org.mockbukkit.mockbukkit.entity.ItemDisplayMock;
import org.mockbukkit.mockbukkit.entity.ItemEntityMock;
import org.mockbukkit.mockbukkit.entity.ItemFrameMock;
import org.mockbukkit.mockbukkit.entity.LeashHitchMock;
import org.mockbukkit.mockbukkit.entity.LlamaMock;
import org.mockbukkit.mockbukkit.entity.LlamaSpitMock;
import org.mockbukkit.mockbukkit.entity.MagmaCubeMock;
import org.mockbukkit.mockbukkit.entity.MarkerMock;
import org.mockbukkit.mockbukkit.entity.MuleMock;
import org.mockbukkit.mockbukkit.entity.MushroomCowMock;
import org.mockbukkit.mockbukkit.entity.OcelotMock;
import org.mockbukkit.mockbukkit.entity.PaintingMock;
import org.mockbukkit.mockbukkit.entity.PandaMock;
import org.mockbukkit.mockbukkit.entity.ParrotMock;
import org.mockbukkit.mockbukkit.entity.PigMock;
import org.mockbukkit.mockbukkit.entity.PigZombieMock;
import org.mockbukkit.mockbukkit.entity.PillagerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.entity.PolarBearMock;
import org.mockbukkit.mockbukkit.entity.PoweredMinecartMock;
import org.mockbukkit.mockbukkit.entity.PufferFishMock;
import org.mockbukkit.mockbukkit.entity.RabbitMock;
import org.mockbukkit.mockbukkit.entity.RavagerMock;
import org.mockbukkit.mockbukkit.entity.RideableMinecartMock;
import org.mockbukkit.mockbukkit.entity.SalmonMock;
import org.mockbukkit.mockbukkit.entity.SheepMock;
import org.mockbukkit.mockbukkit.entity.ShulkerMock;
import org.mockbukkit.mockbukkit.entity.SilverfishMock;
import org.mockbukkit.mockbukkit.entity.SkeletonHorseMock;
import org.mockbukkit.mockbukkit.entity.SkeletonMock;
import org.mockbukkit.mockbukkit.entity.SlimeMock;
import org.mockbukkit.mockbukkit.entity.SmallFireballMock;
import org.mockbukkit.mockbukkit.entity.SnifferMock;
import org.mockbukkit.mockbukkit.entity.SnowballMock;
import org.mockbukkit.mockbukkit.entity.SnowmanMock;
import org.mockbukkit.mockbukkit.entity.SpawnerMinecartMock;
import org.mockbukkit.mockbukkit.entity.SpiderMock;
import org.mockbukkit.mockbukkit.entity.SquidMock;
import org.mockbukkit.mockbukkit.entity.StorageMinecartMock;
import org.mockbukkit.mockbukkit.entity.StrayMock;
import org.mockbukkit.mockbukkit.entity.StriderMock;
import org.mockbukkit.mockbukkit.entity.TadpoleMock;
import org.mockbukkit.mockbukkit.entity.ThrownExpBottleMock;
import org.mockbukkit.mockbukkit.entity.ThrownPotionMock;
import org.mockbukkit.mockbukkit.entity.TraderLlamaMock;
import org.mockbukkit.mockbukkit.entity.TropicalFishMock;
import org.mockbukkit.mockbukkit.entity.TurtleMock;
import org.mockbukkit.mockbukkit.entity.VillagerMock;
import org.mockbukkit.mockbukkit.entity.VindicatorMock;
import org.mockbukkit.mockbukkit.entity.WardenMock;
import org.mockbukkit.mockbukkit.entity.WindChargeMock;
import org.mockbukkit.mockbukkit.entity.WitchMock;
import org.mockbukkit.mockbukkit.entity.WitherMock;
import org.mockbukkit.mockbukkit.entity.WitherSkeletonMock;
import org.mockbukkit.mockbukkit.entity.WitherSkullMock;
import org.mockbukkit.mockbukkit.entity.WolfMock;
import org.mockbukkit.mockbukkit.entity.ZoglinMock;
import org.mockbukkit.mockbukkit.entity.ZombieHorseMock;
import org.mockbukkit.mockbukkit.entity.ZombieMock;
import org.mockbukkit.mockbukkit.entity.ZombieVillagerMock;
import org.mockbukkit.mockbukkit.entity.boat.AcaciaBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.AcaciaChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.BambooChestRaftMock;
import org.mockbukkit.mockbukkit.entity.boat.BambooRaftMock;
import org.mockbukkit.mockbukkit.entity.boat.BirchBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.BirchChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.CherryBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.CherryChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.DarkOakBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.DarkOakChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.JungleBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.JungleChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.MangroveBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.MangroveChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.OakBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.OakChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.PaleOakBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.PaleOakChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.SpruceBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.SpruceChestBoatMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasNotFiredEventInstance;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent;

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
		assertEquals(Material.GRASS_BLOCK, world.getBlockAt(spawn.getBlockX(), spawn.getBlockY() - 1, spawn.getBlockZ()).getType());
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
		world.dropItem(new Location(world, 0, 0, 0), new ItemStackMock(Material.STONE));
		assertEquals(2, world.getEntities().size());
		assertEquals(1, world.getLivingEntities().size());
	}

	@Test
	void hardcore()
	{
		WorldMock world = new WorldMock();
		assertFalse(world.isHardcore());
		world.setHardcore(true);
		assertTrue(world.isHardcore());
	}

	@Test
	void getChunkCount()
	{
		WorldMock world = new WorldMock();
		assertEquals(0, world.getChunkCount());
		world.getChunkAt(0, 0);
		assertEquals(1, world.getChunkCount());
		/* getting an already loaded chunk should not increase the count */
		world.getChunkAt(0, 0);
		assertEquals(1, world.getChunkCount());
	}

	@Test
	void getChunkCount_Unload()
	{
		WorldMock world = new WorldMock();
		world.loadChunk(0, 1);
		assertEquals(1, world.getChunkCount());
		/* unloading a different chunk should not decrease the count */
		world.unloadChunk(0, 2);
		assertEquals(1, world.getChunkCount());
		world.unloadChunk(0, 1);
		assertEquals(0, world.getChunkCount());
	}

	@Test
	void getPlayerCount()
	{
		World worldA = server.addSimpleWorld("worldA");
		World worldB = server.addSimpleWorld("worldB");
		assertEquals(0, worldA.getPlayerCount());
		assertEquals(0, worldB.getPlayerCount());
		PlayerMock player = server.addPlayer();
		assertEquals(1, worldA.getPlayerCount());
		assertEquals(0, worldB.getPlayerCount());
		player.teleport(worldB.getSpawnLocation());
		assertEquals(0, worldA.getPlayerCount());
		assertEquals(1, worldB.getPlayerCount());
		player.disconnect();
		assertEquals(0, worldA.getPlayerCount());
		assertEquals(0, worldB.getPlayerCount());
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
		world.dropItem(new Location(world, 0, 0, 0), new ItemStackMock(Material.STONE));
		assertEquals(1, world.getEntitiesByClass(ZombieMock.class).size());
		assertEquals(1, world.getEntitiesByClass(ItemEntityMock.class).size());
	}

	@Test
	void getEntitiesByClasses()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		world.dropItem(new Location(world, 0, 0, 0), new ItemStackMock(Material.STONE));
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
		world.dropItem(new Location(world, 0, 0, 0), new ItemStackMock(Material.STONE));
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
		BoundingBox box = BoundingBox.of(centerLoc, 1, 2, 8);
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
		BoundingBox box = BoundingBox.of(centerLoc, 1, 64, 8);
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(TimeSkipEvent.class, event ->
				event.getSkipAmount() == 4000L && event.getSkipReason() == TimeSkipEvent.SkipReason.CUSTOM));
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
	void getLoadedChunks_LoadTwice()
	{
		WorldMock world = new WorldMock();
		Chunk chunk = world.getChunkAt(0, 1);
		world.getChunkAt(0, 1);
		Chunk[] loaded = world.getLoadedChunks();
		assertEquals(1, loaded.length);
		assertEquals(chunk, loaded[0]);
	}

	@Test
	void getLoadedChunks_Unload()
	{
		WorldMock world = new WorldMock();
		world.getChunkAt(0, 1);
		world.unloadChunk(0, 1);
		assertEquals(0, world.getLoadedChunks().length);
	}

	@Test
	void loadChunk_AfterUnload()
	{
		WorldMock world = new WorldMock();
		world.loadChunk(0, 1);
		assertTrue(world.isChunkLoaded(0, 1));
		world.unloadChunk(0, 1);
		assertFalse(world.isChunkLoaded(0, 1));
		world.loadChunk(0, 1);
		assertTrue(world.isChunkLoaded(0, 1));
	}

	@Test
	void unloadChunk_NoSaving()
	{
		WorldMock world = new WorldMock();
		Chunk chunk = world.getChunkAt(0, 1);
		chunk.unload(false);
		assertNotSame(chunk, world.getChunkAt(0, 1));
		assertEquals(chunk, world.getChunkAt(0, 1));
	}

	@Test
	void unloadChunk_Save()
	{
		WorldMock world = new WorldMock();
		Chunk chunk = world.getChunkAt(0, 1);
		chunk.unload();
		assertSame(chunk, world.getChunkAt(0, 1));
	}

	@Test
	void unloadChunk_Overwrite()
	{
		WorldMock world = new WorldMock();
		Chunk chunk = world.getChunkAt(0, 1);
		world.unloadChunk(0, 1);
		world.loadChunk(0, 1);
		world.unloadChunk(0, 1, false);
		assertSame(chunk, world.getChunkAt(0, 1));
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
		BlockDataMock mock = new BlockDataMock(Material.SHORT_GRASS);
		BlockDataMock mock2 = new BlockDataMock(Material.GRASS_BLOCK);
		world.setBlockData(location, mock);
		assertEquals(Material.SHORT_GRASS, world.getBlockData(location).getMaterial());
		assertEquals(Material.SHORT_GRASS, world.getBlockData(0, 1, 0).getMaterial());
		assertEquals(Material.SHORT_GRASS, world.getType(0, 1, 0));
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
		ItemStack item = new ItemStackMock(Material.DIAMOND);
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
		ItemStack item = new ItemStackMock(Material.EMERALD);
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
		ItemStack item = new ItemStackMock(Material.BEACON);
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
		world.dropItem(new Location(world, 0, 5, 0), new ItemStackMock(Material.STONE));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(ItemSpawnEvent.class, (e) -> !e.isCancelled()));
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
		PluginMock plugin = MockBukkit.createMockPlugin();
		server.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("MockBukkit");
		assertDoesNotThrow(() -> world.sendPluginMessage(plugin, "BungeeCord", out.toByteArray()));
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(WeatherChangeEvent.class, event ->
				event.getWorld().equals(world) && event.toWeatherState()));
		world.setStorm(false);
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(WeatherChangeEvent.class, event ->
				event.getWorld().equals(world) && !event.toWeatherState()));
	}

	@Test
	void setStorm_SameState_DoesntCallEvent()
	{
		WorldMock world = new WorldMock();
		world.setStorm(false);
		assertThat(server.getPluginManager(), hasNotFiredEventInstance(WeatherChangeEvent.class));
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(ThunderChangeEvent.class, event ->
				event.getWorld().equals(world) && event.toThunderState()));
		world.setThundering(false);
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(ThunderChangeEvent.class, event ->
				event.getWorld().equals(world) && !event.toThunderState()));
	}

	@Test
	void setThundering_SameState_DoesntCallEvent()
	{
		WorldMock world = new WorldMock();
		world.setThundering(false);
		assertThat(server.getPluginManager(), hasNotFiredEventInstance(ThunderChangeEvent.class));
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(CreatureSpawnEvent.class, event -> event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(CreatureSpawnEvent.class, (e) -> !e.isCancelled()));
	}

	@Test
	void spawn_Firework_CorrectEvent()
	{
		WorldMock world = new WorldMock();
		world.spawnEntity(new Location(world, 0, 5, 0), EntityType.FIREWORK_ROCKET);
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(ProjectileLaunchEvent.class, event -> !event.isCancelled()));
	}

	@Test
	void spawn_Item_ThrowsException()
	{
		WorldMock world = new WorldMock();
		assertThrowsExactly(IllegalArgumentException.class, () -> world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ITEM));
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(CreatureSpawnEvent.class, (e) -> e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(CreatureSpawnEvent.class, (e) -> !e.isCancelled()));
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
		Location spawnLocation = new Location(world, 1, 3, 2);

		Entity entity = world.spawnEntity(spawnLocation, type);

		assertInstanceOf(expectedClass, entity);
		assertTrue(entity.isValid());
		assertEquals(type, entity.getType());

		Location actualLocation = entity.getLocation();
		assertEquals(spawnLocation, actualLocation);
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
				Arguments.of(EntityType.VILLAGER, VillagerMock.class),
				Arguments.of(EntityType.PIG, PigMock.class),
				Arguments.of(EntityType.PAINTING, PaintingMock.class),
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
				Arguments.of(EntityType.MOOSHROOM, MushroomCowMock.class),
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
				Arguments.of(EntityType.DROWNED, Drowned.class),
				Arguments.of(EntityType.ZOMBIE_VILLAGER, ZombieVillagerMock.class),
				Arguments.of(EntityType.FIREWORK_ROCKET, FireworkMock.class),
				Arguments.of(EntityType.EXPERIENCE_ORB, ExperienceOrbMock.class),
				Arguments.of(EntityType.FURNACE_MINECART, PoweredMinecartMock.class),
				Arguments.of(EntityType.CAMEL, CamelMock.class),
				Arguments.of(EntityType.COMMAND_BLOCK_MINECART, CommandMinecartMock.class),
				Arguments.of(EntityType.TNT_MINECART, ExplosiveMinecartMock.class),
				Arguments.of(EntityType.HOPPER_MINECART, HopperMinecartMock.class),
				Arguments.of(EntityType.SPAWNER_MINECART, SpawnerMinecartMock.class),
				Arguments.of(EntityType.MINECART, RideableMinecartMock.class),
				Arguments.of(EntityType.CHEST_MINECART, StorageMinecartMock.class),
				Arguments.of(EntityType.AREA_EFFECT_CLOUD, AreaEffectCloudMock.class),
				// Boats
				Arguments.of(EntityType.ACACIA_BOAT, AcaciaBoatMock.class),
				Arguments.of(EntityType.ACACIA_CHEST_BOAT, AcaciaChestBoatMock.class),
				Arguments.of(EntityType.BAMBOO_RAFT, BambooRaftMock.class),
				Arguments.of(EntityType.BAMBOO_CHEST_RAFT, BambooChestRaftMock.class),
				Arguments.of(EntityType.BIRCH_BOAT, BirchBoatMock.class),
				Arguments.of(EntityType.BIRCH_CHEST_BOAT, BirchChestBoatMock.class),
				Arguments.of(EntityType.CHERRY_BOAT, CherryBoatMock.class),
				Arguments.of(EntityType.CHERRY_CHEST_BOAT, CherryChestBoatMock.class),
				Arguments.of(EntityType.DARK_OAK_BOAT, DarkOakBoatMock.class),
				Arguments.of(EntityType.DARK_OAK_CHEST_BOAT, DarkOakChestBoatMock.class),
				Arguments.of(EntityType.JUNGLE_BOAT, JungleBoatMock.class),
				Arguments.of(EntityType.JUNGLE_CHEST_BOAT, JungleChestBoatMock.class),
				Arguments.of(EntityType.MANGROVE_BOAT, MangroveBoatMock.class),
				Arguments.of(EntityType.MANGROVE_CHEST_BOAT, MangroveChestBoatMock.class),
				Arguments.of(EntityType.OAK_BOAT, OakBoatMock.class),
				Arguments.of(EntityType.OAK_CHEST_BOAT, OakChestBoatMock.class),
				Arguments.of(EntityType.PALE_OAK_BOAT, PaleOakBoatMock.class),
				Arguments.of(EntityType.PALE_OAK_CHEST_BOAT, PaleOakChestBoatMock.class),
				Arguments.of(EntityType.SPRUCE_BOAT, SpruceBoatMock.class),
				Arguments.of(EntityType.SPRUCE_CHEST_BOAT, SpruceChestBoatMock.class),
				//
				Arguments.of(EntityType.ENDER_PEARL, EnderPearlMock.class),
				Arguments.of(EntityType.FISHING_BOBBER, FishHookMock.class),
				Arguments.of(EntityType.PANDA, PandaMock.class),
				Arguments.of(EntityType.RABBIT, RabbitMock.class),
				Arguments.of(EntityType.OCELOT, OcelotMock.class),
				Arguments.of(EntityType.SLIME, SlimeMock.class),
				Arguments.of(EntityType.PARROT, ParrotMock.class),
				Arguments.of(EntityType.SQUID, SquidMock.class),
				Arguments.of(EntityType.GLOW_SQUID, GlowSquidMock.class),
				Arguments.of(EntityType.LLAMA_SPIT, LlamaSpitMock.class),
				Arguments.of(EntityType.DOLPHIN, DolphinMock.class),
				Arguments.of(EntityType.MAGMA_CUBE, MagmaCubeMock.class),
				Arguments.of(EntityType.ENDERMITE, EndermiteMock.class),
				Arguments.of(EntityType.SILVERFISH, SilverfishMock.class),
				Arguments.of(EntityType.EXPERIENCE_BOTTLE, ThrownExpBottleMock.class),
				Arguments.of(EntityType.SNOWBALL, SnowballMock.class),
				Arguments.of(EntityType.TURTLE, TurtleMock.class),
				Arguments.of(EntityType.LEASH_KNOT, LeashHitchMock.class),
				Arguments.of(EntityType.ZOMBIFIED_PIGLIN, PigZombieMock.class),
				Arguments.of(EntityType.BLOCK_DISPLAY, BlockDisplayMock.class),
				Arguments.of(EntityType.ITEM_DISPLAY, ItemDisplayMock.class),
				Arguments.of(EntityType.ZOMBIFIED_PIGLIN, PigZombieMock.class),
				Arguments.of(EntityType.TRIDENT, Trident.class),
				Arguments.of(EntityType.SPECTRAL_ARROW, SpectralArrow.class),
				Arguments.of(EntityType.ARROW, Arrow.class),
				Arguments.of(EntityType.MARKER, MarkerMock.class),
				Arguments.of(EntityType.END_CRYSTAL, EnderCrystalMock.class),
				Arguments.of(EntityType.PILLAGER, PillagerMock.class),
				Arguments.of(EntityType.WITCH, WitchMock.class),
				Arguments.of(EntityType.RAVAGER, RavagerMock.class),
				Arguments.of(EntityType.ZOGLIN, ZoglinMock.class),
				Arguments.of(EntityType.VINDICATOR, VindicatorMock.class),
				Arguments.of(EntityType.EVOKER, EvokerMock.class),
				Arguments.of(EntityType.EVOKER_FANGS, EvokerFangsMock.class),
				Arguments.of(EntityType.ILLUSIONER, IllusionerMock.class),
				Arguments.of(EntityType.INTERACTION, InteractionMock.class),
				Arguments.of(EntityType.IRON_GOLEM, IronGolemMock.class),
				Arguments.of(EntityType.SNOW_GOLEM, SnowmanMock.class),
				Arguments.of(EntityType.ITEM_FRAME, ItemFrameMock.class),
				Arguments.of(EntityType.GLOW_ITEM_FRAME, GlowItemFrameMock.class),
				Arguments.of(EntityType.SHULKER, ShulkerMock.class),
				Arguments.of(EntityType.FALLING_BLOCK, FallingBlockMock.class),
				Arguments.of(EntityType.POTION, ThrownPotionMock.class),
				Arguments.of(EntityType.ARMADILLO, ArmadilloMock.class),
				Arguments.of(EntityType.EYE_OF_ENDER, EnderSignalMock.class),
				Arguments.of(EntityType.HUSK, HuskMock.class),
				Arguments.of(EntityType.STRIDER, StriderMock.class),
				Arguments.of(EntityType.CREAKING, CreakingMock.class),
				Arguments.of(EntityType.TRADER_LLAMA, TraderLlamaMock.class),
				Arguments.of(EntityType.BOGGED, BoggedMock.class),
				Arguments.of(EntityType.BREEZE, BreezeMock.class),
				Arguments.of(EntityType.BREEZE_WIND_CHARGE, BreezeWindChargeMock.class),
				Arguments.of(EntityType.WIND_CHARGE, WindChargeMock.class),
				Arguments.of(EntityType.WITHER, WitherMock.class),
				Arguments.of(EntityType.ENDER_DRAGON, EnderDragonMock.class),
				Arguments.of(EntityType.SNIFFER, SnifferMock.class)
		);
	}

	@Test
	void testGetGameRules()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String[] gameRules = world.getGameRules();
		assertNotEquals(0, Arrays.stream(gameRules).count());
		for (String gameRule : gameRules)
		{
			assertNotNull(GameRule.getByName(gameRule));
		}
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(WorldGameRuleChangeEvent.class, worldGameRuleChangeEvent ->
		{
			return worldGameRuleChangeEvent.getGameRule().equals(GameRule.ANNOUNCE_ADVANCEMENTS)
					&& worldGameRuleChangeEvent.getValue().equals("false");
		}));
		world.setGameRuleValue("announceAdvancements", "true");
		assertEquals("true", world.getGameRuleValue("announceAdvancements"));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(WorldGameRuleChangeEvent.class, worldGameRuleChangeEvent ->
		{
			return worldGameRuleChangeEvent.getGameRule().equals(GameRule.ANNOUNCE_ADVANCEMENTS)
					&& worldGameRuleChangeEvent.getValue().equals("true");
		}));
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(WorldGameRuleChangeEvent.class, worldGameRuleChangeEvent ->
		{
			return worldGameRuleChangeEvent.getGameRule().equals(GameRule.RANDOM_TICK_SPEED)
					&& worldGameRuleChangeEvent.getValue().equals("10");
		}));
	}

	@Test
	void testSetGameRuleValueIntegerNonParseable()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		String randomTickSpeed = world.getGameRuleValue("randomTickSpeed");
		world.setGameRuleValue("randomTickSpeed", "test");
		assertEquals(randomTickSpeed, world.getGameRuleValue("randomTickSpeed"));
	}

	@Test
	void getHighestBlockYAt_FlatWorld()
	{
		WorldMock world = new WorldMock(Material.COAL_BLOCK, 3);
		assertEquals(3, world.getHighestBlockYAt(0, 0, HeightMap.WORLD_SURFACE));
	}

	@Test
	void getHighestBlockAt_FlatWorld()
	{
		WorldMock world = new WorldMock(Material.COAL_BLOCK, 3);
		assertEquals(3, world.getHighestBlockAt(0, 0, HeightMap.WORLD_SURFACE).getY());
	}

	@Test
	void getHighestBlockYAt_AddedMaterial()
	{
		WorldMock worldMock = new WorldMock(Material.COAL_BLOCK, 3);
		Location location = new Location(worldMock, 2, 20, 3);
		location.getBlock().setType(Material.GRASS_BLOCK);
		assertEquals(20, worldMock.getHighestBlockYAt(2, 3, HeightMap.WORLD_SURFACE));
	}

	@Test
	void getHighestBlockYAt_Water()
	{
		WorldMock worldMock = new WorldMock(Material.COAL_BLOCK, 3);
		Location location = new Location(worldMock, 2, 20, 3);
		location.getBlock().setType(Material.WATER);
		assertEquals(3, worldMock.getHighestBlockYAt(2, 3, HeightMap.OCEAN_FLOOR));
		assertEquals(20, worldMock.getHighestBlockYAt(2, 3, HeightMap.WORLD_SURFACE));
		assertEquals(20, worldMock.getHighestBlockYAt(2, 3, HeightMap.MOTION_BLOCKING));
	}

	@Test
	void getHighestBlockYAt_Leaves()
	{
		WorldMock worldMock = new WorldMock(Material.COAL_BLOCK, 3);
		Location location = new Location(worldMock, 2, 20, 3);
		location.getBlock().setType(Material.ACACIA_LEAVES);
		assertEquals(3, worldMock.getHighestBlockYAt(2, 3, HeightMap.MOTION_BLOCKING_NO_LEAVES));
		assertEquals(20, worldMock.getHighestBlockYAt(2, 3, HeightMap.MOTION_BLOCKING));
	}

	@Test
	void getHighestBlockYAt_NoBlockInCoordinate()
	{
		WorldMock worldMock = new WorldMock(Material.COAL_BLOCK, 0);
		Location location = new Location(worldMock, 2, 0, 3);
		location.getBlock().setType(Material.AIR);
		assertEquals(-1, worldMock.getHighestBlockYAt(2, 3, HeightMap.WORLD_SURFACE));
	}

	@Test
	void getHighestBlockYAt_Worldgen()
	{
		WorldMock worldMock = new WorldMock(Material.COAL_BLOCK, 2);
		Location location = new Location(worldMock, 2, 3, 3);
		location.getBlock().setType(Material.COAL_BLOCK);
		assertEquals(2, worldMock.getHighestBlockYAt(2, 3, HeightMap.WORLD_SURFACE_WG));
	}

	@Test
	void setThundering_cancelled()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onThunder(ThunderChangeEvent event)
			{
				event.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		world.setThundering(true);
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(ThunderChangeEvent.class, ThunderChangeEvent::isCancelled));
		assertFalse(world.isThundering());
	}

	@Test
	void setStorm_cancelled()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onThunder(WeatherChangeEvent event)
			{
				event.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		world.setStorm(true);
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(WeatherChangeEvent.class, WeatherChangeEvent::isCancelled));
		assertFalse(world.hasStorm());
	}

	@Test
	void testCreateBlockYToBig()
	{
		Coordinate coordinate = new Coordinate(0, 256, 0);
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> world.createBlock(coordinate));
	}

	@Test
	void testCreateBlockYToSmall()
	{
		Coordinate coordinate = new Coordinate(0, -1, 0);
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> world.createBlock(coordinate));
	}

	@Test
	void testGetLocationAtKey()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location location = new Location(world, 0, 0, 0);
		long blockKey = location.toBlockKey();
		assertEquals(location, world.getLocationAtKey(blockKey));
	}

	@Test
	void testGetBlockAtKey()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location location = new Location(world, 100, 44, 0);
		long blockKey = location.toBlockKey();
		assertEquals(location, world.getBlockAtKey(blockKey).getLocation());
	}

	@Test
	@SuppressWarnings("UnstableApiUsage")
	void testSendPluginMessageWithPlayers()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		PluginMock plugin = MockBukkit.createMockPlugin();
		server.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("MockBukkit");
		assertDoesNotThrow(() -> world.sendPluginMessage(plugin, "BungeeCord", out.toByteArray()));

	}

	@Test
	void testGetMetadataDefault()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(0, world.getMetadata("test").size());
	}

	@Test
	void testGetMetadata()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setMetadata("test", new FixedMetadataValue(MockBukkit.createMockPlugin(), "test"));
		assertEquals(1, world.getMetadata("test").size());
		assertEquals("test", world.getMetadata("test").get(0).value());
	}

	@Test
	void testHasMetadata()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setMetadata("test", new FixedMetadataValue(MockBukkit.createMockPlugin(), "test"));
		assertTrue(world.hasMetadata("test"));
	}

	@Test
	void testRemoveMetaData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setMetadata("test", new FixedMetadataValue(MockBukkit.createMockPlugin(), "test"));
		assertTrue(world.hasMetadata("test"));
		world.removeMetadata("test", MockBukkit.createMockPlugin());
		assertFalse(world.hasMetadata("test"));
	}

	@Test
	void testClearMetadata()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		world.setMetadata("test", new FixedMetadataValue(pluginMock, "test"));
		world.setMetadata("test2", new FixedMetadataValue(MockBukkit.createMockPlugin("test"), "test2"));
		assertTrue(world.hasMetadata("test"));
		assertTrue(world.hasMetadata("test2"));
		world.clearMetadata(pluginMock);
		assertFalse(world.hasMetadata("test"));
		assertTrue(world.hasMetadata("test2"));
	}

	@Test
	void testSpawnWithConsumer()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Consumer<Zombie> consumer = entity ->
		{
			entity.setCustomName("test");
		};
		Entity entity = world.spawn(new Location(world, 0, 0, 0), Zombie.class, consumer);
		assertEquals("test", entity.getCustomName());
	}

	@Test
	void testSpawnWithConsumerAndRandomizeData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Consumer<Zombie> consumer = entity ->
		{
			entity.setCustomName("test");
		};
		Entity entity = world.spawn(new Location(world, 0, 0, 0), Zombie.class, true, consumer);
		assertEquals("test", entity.getCustomName());
	}

	@Test
	void testSpawnEntityRandomizeData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE, true);
		assertTrue(entity.isValid());
	}

	@Test
	void testSetEnvironment()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setEnvironment(World.Environment.NETHER);
		assertEquals(World.Environment.NETHER, world.getEnvironment());
	}

	@Test
	void spawnFallingBlock_GivenLocationAndMaterialAndData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location spawnLoc = new Location(world, 0, 0, 0);
		FallingBlock entity = world.spawnFallingBlock(spawnLoc, Material.OAK_WOOD, (byte) 0);

		assertNotNull(entity);
		assertEquals(Material.OAK_WOOD, entity.getBlockData().getMaterial());
	}

	@Test
	void spawnFallingBlock_GivenLocationAndBlockData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location spawnLoc = new Location(world, 0, 0, 0);
		BlockData blockData = new BlockDataMock(Material.OAK_WOOD);
		FallingBlock entity = world.spawnFallingBlock(spawnLoc, blockData);

		assertNotNull(entity);
		assertEquals(Material.OAK_WOOD, entity.getBlockData().getMaterial());
	}

	@Test
	void testPlayEffectIntegerData()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertDoesNotThrow(() -> world.playEffect(new Location(world, 0, 0, 0), Effect.STEP_SOUND, 1));
	}

	@Test
	void testPlayEffectIntegerDataRadius()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertDoesNotThrow(() -> world.playEffect(new Location(world, 0, 0, 0), Effect.STEP_SOUND, 1, 1));
	}

	@Test
	void testPlayEffectNullLocation()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () ->
		{
			world.playEffect(null, Effect.STEP_SOUND, 1);
		});

		assertEquals("Location cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testPlayEffectNullEffect()
	{

		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location location = new Location(world, 0, 0, 0);
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () ->
		{
			world.playEffect(location, null, 1);
		});

		assertEquals("Effect cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testPlayEffectNullWorld()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Location location = new Location(null, 0, 0, 0);
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () ->
		{

			world.playEffect(location, Effect.STEP_SOUND, 1);
		});

		assertEquals("World cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testSetBiome()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setBiome(0, 0, Biome.DESERT);
		assertEquals(Biome.DESERT, world.getBiome(0, 0));
	}

	@Test
	void testGetSeaLevel()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(63, world.getSeaLevel());
	}

	@Test
	void testPlaySound()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world.playSound(new Location(world, 0, 0, 0),
				Sound.BLOCK_ANVIL_BREAK, 1, 1));

		playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1;
		});

	}

	@Test
	void testPlaySoundStringSound()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world.playSound(new Location(world, 0, 0, 0),
				"block.anvil.break", 1, 1));

		playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1;
		});
	}

	@Test
	void testPlaySoundEntity()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world.playSound(playerMock, Sound.BLOCK_ANVIL_BREAK, 1, 1));

		playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1;
		});
	}

	@Test
	void testPlaySoundEntityNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world.playSound((Entity) null, Sound.BLOCK_ANVIL_BREAK, 1, 1));
		assertThrows(AssertionFailedError.class, () -> playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1;
		}));
	}

	@Test
	void testPlaySoundEntityOtherWorld()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		WorldMock world2 = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world2.playSound(playerMock, Sound.BLOCK_ANVIL_BREAK, 1, 1));
		assertThrows(AssertionFailedError.class, () -> playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1 && audio.getLocation().getWorld() == world2;
		}));
	}

	@Test
	void testPlaySoundEntityNullSound()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world.playSound(playerMock, (Sound) null, 1, 1));
		assertThrows(AssertionFailedError.class, () -> playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1;
		}));
	}

	@Test
	void testPlaySoundEntityNullCategory()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PlayerMock playerMock = server.addPlayer();
		playerMock.teleport(world.getSpawnLocation());
		assertDoesNotThrow(() -> world.playSound(playerMock, Sound.ITEM_GOAT_HORN_SOUND_0, null, 1, 1));
		assertThrows(AssertionFailedError.class, () -> playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_BREAK, (audio) ->
		{
			return audio.getVolume() == 1 && audio.getPitch() == 1;
		}));
	}

	@Test
	void testSetGameRuleValueEventCancelled()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onGameRuleChange(WorldGameRuleChangeEvent event)
			{
				event.setCancelled(true);
			}
		}, pluginMock);
		world.setGameRuleValue("doFireTick", "false");
		assertEquals("true", world.getGameRuleValue("doFireTick"));
	}

	@Test
	void testSetGameRuleValueEventCancelledIntegerValue()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		PluginMock pluginMock = MockBukkit.createMockPlugin();
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onGameRuleChange(WorldGameRuleChangeEvent event)
			{
				event.setCancelled(true);
			}
		}, pluginMock);
		world.setGameRuleValue("randomTickSpeed", "10");
		assertEquals("3", world.getGameRuleValue("randomTickSpeed"));
	}

	@Test
	void testGetPvpDefault()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(server.getServerConfiguration().isPvpEnabled(), world.getPVP());
	}

	@Test
	void testSetPvp()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setPVP(false);
		assertFalse(world.getPVP());
	}

	@Test
	void testGetKeepSpawnInMemoryDefault()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertTrue(world.getKeepSpawnInMemory());
	}

	@Test
	void testSetKeepSpawnInMemory()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setKeepSpawnInMemory(false);
		assertFalse(world.getKeepSpawnInMemory());
	}

	@ParameterizedTest
	@MethodSource("getTicksPerSpawnCategory")
	void testGetTicksPerSpawn(SpawnCategory category, int expectedTicksPerSpawn)
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(expectedTicksPerSpawn, world.getTicksPerSpawns(category));
	}

	public static Stream<Arguments> getTicksPerSpawnCategory()
	{
		return Stream.of(
				Arguments.of(SpawnCategory.MONSTER, 1),
				Arguments.of(SpawnCategory.ANIMAL, 400),
				Arguments.of(SpawnCategory.WATER_AMBIENT, 1),
				Arguments.of(SpawnCategory.WATER_ANIMAL, 1),
				Arguments.of(SpawnCategory.AMBIENT, 1),
				Arguments.of(SpawnCategory.WATER_UNDERGROUND_CREATURE, 1)
		);
	}

	@Test
	void testGetTicksPerSpawnNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.getTicksPerSpawns(null));
	}

	@Test
	void testGetTicksPerSpawnInvalidCategory()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.getTicksPerSpawns(SpawnCategory.MISC));
	}

	@Test
	void testSetTicksPerSpawn()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerSpawns(SpawnCategory.MONSTER, 10);
		assertEquals(10, world.getTicksPerSpawns(SpawnCategory.MONSTER));
	}

	@Test
	void testSetTicksPerSpawnNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.setTicksPerSpawns(null, 10));
	}

	@Test
	void testSetTicksPerSpawnInvalidCategory()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.setTicksPerSpawns(SpawnCategory.MISC, 10));
	}

	@Test
	void testGetTicksPerWaterUndergroundCreatureSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(1, world.getTicksPerWaterUndergroundCreatureSpawns());
	}

	@Test
	void testSetTicksPerWaterUndergroundCreatureSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerWaterUndergroundCreatureSpawns(10);
		assertEquals(10, world.getTicksPerWaterUndergroundCreatureSpawns());
	}

	@Test
	void testGetTicksPerAmbientSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(1, world.getTicksPerAmbientSpawns());
	}

	@Test
	void testSetTicksPerAmbientSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerAmbientSpawns(10);
		assertEquals(10, world.getTicksPerAmbientSpawns());
	}

	@Test
	void testGetTicksPerWaterAmbientSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(1, world.getTicksPerWaterAmbientSpawns());
	}

	@Test
	void testSetTicksPerWaterAmbientSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerWaterAmbientSpawns(10);
		assertEquals(10, world.getTicksPerWaterAmbientSpawns());
	}

	@Test
	void testGetTicksPerWaterAnimalSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(1, world.getTicksPerWaterSpawns());
	}

	@Test
	void testSetTicksPerWaterAnimalSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerWaterSpawns(10);
		assertEquals(10, world.getTicksPerWaterSpawns());
	}

	@Test
	void testGetTicksPerAnimalSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(400, world.getTicksPerAnimalSpawns());
	}

	@Test
	void testSetTicksPerAnimalSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerAnimalSpawns(10);
		assertEquals(10, world.getTicksPerAnimalSpawns());
	}

	@Test
	void testGetTicksPerMonsterSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(1, world.getTicksPerMonsterSpawns());
	}

	@Test
	void testSetTicksPerMonsterSpawns()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setTicksPerMonsterSpawns(10);
		assertEquals(10, world.getTicksPerMonsterSpawns());
	}

	@ParameterizedTest
	@MethodSource("getSpawnLimits")
	void testGetSpawnLimits(SpawnCategory category, int limit)
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(limit, world.getSpawnLimit(category));
	}

	public static Stream<Arguments> getSpawnLimits()
	{
		return Stream.of(
				Arguments.of(SpawnCategory.MONSTER, 70),
				Arguments.of(SpawnCategory.WATER_ANIMAL, 5),
				Arguments.of(SpawnCategory.ANIMAL, 10),
				Arguments.of(SpawnCategory.WATER_AMBIENT, 20),
				Arguments.of(SpawnCategory.WATER_UNDERGROUND_CREATURE, 5),
				Arguments.of(SpawnCategory.AMBIENT, 15));
	}

	@Test
	void testGetSpawnLimitsNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.getSpawnLimit(null));
	}

	@Test
	void testGetSpawnLimitsInvalidCategory()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.getSpawnLimit(SpawnCategory.MISC));
	}

	@Test
	void testGetSpawnLimitNegativeValue()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setSpawnLimit(SpawnCategory.MONSTER, -2);

		assertEquals(70, world.getSpawnLimit(SpawnCategory.MONSTER));
	}

	@Test
	void testSetSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setSpawnLimit(SpawnCategory.WATER_AMBIENT, 3);

		assertEquals(3, world.getSpawnLimit(SpawnCategory.WATER_AMBIENT));
	}

	@Test
	void testSetSpawnLimitNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.setSpawnLimit(null, 3));
	}

	@Test
	void testSetSpawnLimitInvalidCategory()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.setSpawnLimit(SpawnCategory.MISC, 3));
	}

	@Test
	void testGetMonsterSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(70, world.getMonsterSpawnLimit());
	}

	@Test
	void testSetMonsterSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setMonsterSpawnLimit(5);
		assertEquals(5, world.getSpawnLimit(SpawnCategory.MONSTER));
	}

	@Test
	void testGetAnimalSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(10, world.getAnimalSpawnLimit());
	}

	@Test
	void testSetAnimalSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setAnimalSpawnLimit(5);
		assertEquals(5, world.getSpawnLimit(SpawnCategory.ANIMAL));
	}

	@Test
	void testGetAmbientSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(15, world.getAmbientSpawnLimit());
	}

	@Test
	void testSetAmbientSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setAmbientSpawnLimit(5);
		assertEquals(5, world.getSpawnLimit(SpawnCategory.AMBIENT));
	}

	@Test
	void testGetWaterAnimalSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(5, world.getWaterAnimalSpawnLimit());
	}

	@Test
	void testSetWaterAnimalSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setWaterAnimalSpawnLimit(42);
		assertEquals(42, world.getSpawnLimit(SpawnCategory.WATER_ANIMAL));
	}

	@Test
	void testGetWaterAmbientSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(20, world.getWaterAmbientSpawnLimit());
	}

	@Test
	void testSetWaterAmbientSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setWaterAmbientSpawnLimit(5);
		assertEquals(5, world.getSpawnLimit(SpawnCategory.WATER_AMBIENT));
	}

	@Test
	void testGetWaterUndergroundCreatureSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(5, world.getWaterUndergroundCreatureSpawnLimit());
	}

	@Test
	void testSetWaterUndergroundCreatureSpawnLimit()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setWaterUndergroundCreatureSpawnLimit(42);
		assertEquals(42, world.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE));
	}

	@Test
	void testIsRespawnAnchorWorks()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		world.setEnvironment(World.Environment.NETHER);
		assertTrue(world.isRespawnAnchorWorks());
	}

	@Test
	void testIsRespawnAnchorWorksFalse()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertFalse(world.isRespawnAnchorWorks());
		world.setEnvironment(World.Environment.THE_END);
		assertFalse(world.isRespawnAnchorWorks());
	}

	@Test
	void testIsFixedTime()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertFalse(world.isFixedTime());

		world.setEnvironment(World.Environment.NETHER);
		assertTrue(world.isFixedTime());

		world.setEnvironment(World.Environment.THE_END);
		assertTrue(world.isFixedTime());
	}

	@Test
	void testGetEntity()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		assertEquals(entity, world.getEntity(entity.getUniqueId()));
	}

	@Test
	void testGetEntityNull()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertThrows(IllegalArgumentException.class, () -> world.getEntity(null));
	}

	@Test
	void testGetEntityWrongWorld()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		WorldMock world2 = new WorldMock(Material.DIRT, 3);
		Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
		assertNull(world2.getEntity(entity.getUniqueId()));
	}

	@Test
	void testGetLogicalHeight()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(384, world.getLogicalHeight());

		world.setEnvironment(World.Environment.NETHER);
		assertEquals(256, world.getLogicalHeight());

		world.setEnvironment(World.Environment.THE_END);
		assertEquals(256, world.getLogicalHeight());
	}

	@Test
	void getEntityCount()
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);
		assertEquals(0, world.getEntityCount());

		Entity zombie = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.ZOMBIE);
		assertEquals(1, world.getEntityCount());

		Entity wolf = world.spawnEntity(new Location(world, 5, 5, 5), EntityType.WOLF);
		assertEquals(2, world.getEntityCount());

		wolf.remove();
		assertEquals(1, world.getEntityCount());

		zombie.remove();
		assertEquals(0, world.getEntityCount());
	}

	@ParameterizedTest
	@CsvSource({
		"FULL_MOON, 0",
		"FULL_MOON, 23999",
		"WANING_GIBBOUS, 24000",
		"WANING_GIBBOUS, 47999",
		"LAST_QUARTER, 48000",
		"LAST_QUARTER, 71999",
		"WANING_CRESCENT, 72000",
		"WANING_CRESCENT, 95999",
		"NEW_MOON, 96000",
		"NEW_MOON, 119999",
		"WAXING_CRESCENT, 120000",
		"WAXING_CRESCENT, 143999",
		"FIRST_QUARTER, 144000",
		"FIRST_QUARTER, 167999",
		"WAXING_GIBBOUS, 168000",
		"WAXING_GIBBOUS, 191999",
		"FULL_MOON, 192000",
	})
	void getMoonPhase(MoonPhase expected, long time)
	{
		WorldMock world = new WorldMock(Material.DIRT, 3);

		world.setFullTime(time);

		assertEquals(expected, world.getMoonPhase());
	}

	@Nested
	class SetGameTime
	{
		@ParameterizedTest
		@ValueSource(longs = {0, 1, 10, 25, 50, 75, 100})
		void givenValidValues(long expected)
		{
			WorldMock world = new WorldMock(Material.DIRT, 3);

			world.setGameTime(expected);

			assertEquals(expected, world.getGameTime());
		}

		@ParameterizedTest
		@ValueSource(longs = {-1, -10, -25, -50, -75, -100})
		void givenInValidValues(long invalidValue)
		{
			WorldMock world = new WorldMock(Material.DIRT, 3);

			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> world.setGameTime(invalidValue));
			assertEquals("Time must be greater or equal than 0", e.getMessage());
		}

	}

}
