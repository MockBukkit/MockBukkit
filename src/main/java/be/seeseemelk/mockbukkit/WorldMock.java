package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.entity.AllayMock;
import be.seeseemelk.mockbukkit.entity.AreaEffectCloudMock;
import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import be.seeseemelk.mockbukkit.entity.ArrowMock;
import be.seeseemelk.mockbukkit.entity.AxolotlMock;
import be.seeseemelk.mockbukkit.entity.BatMock;
import be.seeseemelk.mockbukkit.entity.BeeMock;
import be.seeseemelk.mockbukkit.entity.BlazeMock;
import be.seeseemelk.mockbukkit.entity.BlockDisplayMock;
import be.seeseemelk.mockbukkit.entity.BoatMock;
import be.seeseemelk.mockbukkit.entity.CamelMock;
import be.seeseemelk.mockbukkit.entity.CatMock;
import be.seeseemelk.mockbukkit.entity.CaveSpiderMock;
import be.seeseemelk.mockbukkit.entity.ChestBoatMock;
import be.seeseemelk.mockbukkit.entity.ChickenMock;
import be.seeseemelk.mockbukkit.entity.CodMock;
import be.seeseemelk.mockbukkit.entity.CommandMinecartMock;
import be.seeseemelk.mockbukkit.entity.CowMock;
import be.seeseemelk.mockbukkit.entity.CreeperMock;
import be.seeseemelk.mockbukkit.entity.DolphinMock;
import be.seeseemelk.mockbukkit.entity.DonkeyMock;
import be.seeseemelk.mockbukkit.entity.DragonFireballMock;
import be.seeseemelk.mockbukkit.entity.EggMock;
import be.seeseemelk.mockbukkit.entity.ElderGuardianMock;
import be.seeseemelk.mockbukkit.entity.EnderPearlMock;
import be.seeseemelk.mockbukkit.entity.EndermanMock;
import be.seeseemelk.mockbukkit.entity.EndermiteMock;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.ExperienceOrbMock;
import be.seeseemelk.mockbukkit.entity.ExplosiveMinecartMock;
import be.seeseemelk.mockbukkit.entity.FireworkMock;
import be.seeseemelk.mockbukkit.entity.FishHookMock;
import be.seeseemelk.mockbukkit.entity.FoxMock;
import be.seeseemelk.mockbukkit.entity.FrogMock;
import be.seeseemelk.mockbukkit.entity.GhastMock;
import be.seeseemelk.mockbukkit.entity.GiantMock;
import be.seeseemelk.mockbukkit.entity.GlowSquidMock;
import be.seeseemelk.mockbukkit.entity.GoatMock;
import be.seeseemelk.mockbukkit.entity.GuardianMock;
import be.seeseemelk.mockbukkit.entity.HopperMinecartMock;
import be.seeseemelk.mockbukkit.entity.HorseMock;
import be.seeseemelk.mockbukkit.entity.ItemDisplayMock;
import be.seeseemelk.mockbukkit.entity.ItemEntityMock;
import be.seeseemelk.mockbukkit.entity.LargeFireballMock;
import be.seeseemelk.mockbukkit.entity.LeashHitchMock;
import be.seeseemelk.mockbukkit.entity.LlamaMock;
import be.seeseemelk.mockbukkit.entity.LlamaSpitMock;
import be.seeseemelk.mockbukkit.entity.MagmaCubeMock;
import be.seeseemelk.mockbukkit.entity.MarkerMock;
import be.seeseemelk.mockbukkit.entity.MobMock;
import be.seeseemelk.mockbukkit.entity.MuleMock;
import be.seeseemelk.mockbukkit.entity.MushroomCowMock;
import be.seeseemelk.mockbukkit.entity.OcelotMock;
import be.seeseemelk.mockbukkit.entity.PandaMock;
import be.seeseemelk.mockbukkit.entity.ParrotMock;
import be.seeseemelk.mockbukkit.entity.PigMock;
import be.seeseemelk.mockbukkit.entity.PigZombieMock;
import be.seeseemelk.mockbukkit.entity.PolarBearMock;
import be.seeseemelk.mockbukkit.entity.PoweredMinecartMock;
import be.seeseemelk.mockbukkit.entity.PufferFishMock;
import be.seeseemelk.mockbukkit.entity.RabbitMock;
import be.seeseemelk.mockbukkit.entity.RideableMinecartMock;
import be.seeseemelk.mockbukkit.entity.SalmonMock;
import be.seeseemelk.mockbukkit.entity.SheepMock;
import be.seeseemelk.mockbukkit.entity.SilverfishMock;
import be.seeseemelk.mockbukkit.entity.SkeletonHorseMock;
import be.seeseemelk.mockbukkit.entity.SkeletonMock;
import be.seeseemelk.mockbukkit.entity.SlimeMock;
import be.seeseemelk.mockbukkit.entity.SmallFireballMock;
import be.seeseemelk.mockbukkit.entity.SnowballMock;
import be.seeseemelk.mockbukkit.entity.SpawnerMinecartMock;
import be.seeseemelk.mockbukkit.entity.SpectralArrowMock;
import be.seeseemelk.mockbukkit.entity.SpiderMock;
import be.seeseemelk.mockbukkit.entity.SquidMock;
import be.seeseemelk.mockbukkit.entity.StorageMinecartMock;
import be.seeseemelk.mockbukkit.entity.StrayMock;
import be.seeseemelk.mockbukkit.entity.TadpoleMock;
import be.seeseemelk.mockbukkit.entity.ThrownExpBottleMock;
import be.seeseemelk.mockbukkit.entity.TridentMock;
import be.seeseemelk.mockbukkit.entity.TropicalFishMock;
import be.seeseemelk.mockbukkit.entity.TurtleMock;
import be.seeseemelk.mockbukkit.entity.WardenMock;
import be.seeseemelk.mockbukkit.entity.WitherSkeletonMock;
import be.seeseemelk.mockbukkit.entity.WitherSkullMock;
import be.seeseemelk.mockbukkit.entity.WolfMock;
import be.seeseemelk.mockbukkit.entity.ZombieHorseMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import be.seeseemelk.mockbukkit.generator.BiomeProviderMock;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import com.destroystokyo.paper.HeightmapType;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import io.papermc.paper.block.fluid.FluidData;
import io.papermc.paper.event.world.WorldGameRuleChangeEvent;
import io.papermc.paper.math.Position;
import io.papermc.paper.world.MoonPhase;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.FeatureFlag;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameEvent;
import org.bukkit.GameRule;
import org.bukkit.HeightMap;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.StructureType;
import org.bukkit.Tag;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Animals;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Cat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.GlowSquid;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Mule;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Tadpole;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.Trident;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Turtle;
import org.bukkit.entity.Warden;
import org.bukkit.entity.WaterMob;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.structure.GeneratedStructure;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.util.BiomeSearchResult;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.StructureSearchResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Mock implementation of a {@link World}.
 */
public class WorldMock implements World
{

	private static final int SEA_LEVEL = 63;

	private final Map<Coordinate, BlockMock> blocks = new HashMap<>();
	private final Map<GameRule<?>, Object> gameRules = new HashMap<>();
	private final MetadataTable metadataTable = new MetadataTable();
	private final Map<ChunkCoordinate, ChunkMock> loadedChunks = new HashMap<>();
	private final Map<ChunkCoordinate, ChunkMock> savedChunks = new HashMap<>();
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();
	private final @Nullable ServerMock server;
	private final Material defaultBlock;
	private final Biome defaultBiome;
	private final int grassHeight;
	private final int minHeight;
	private final int maxHeight;
	private WorldBorderMock worldBorder;
	private final UUID uuid = UUID.randomUUID();

	private Environment environment = Environment.NORMAL;

	private String name = "World";
	private Location spawnLocation;
	private long fullTime = 0;
	private int weatherDuration;
	private boolean thundering;
	private int thunderDuration;
	private boolean storming;
	private int clearWeatherDuration;
	private long seed = 0;
	private @NotNull WorldType worldType = WorldType.NORMAL;
	private final BiomeProviderMock biomeProviderMock = new BiomeProviderMock();
	private final @NotNull Map<Coordinate, Biome> biomes = new HashMap<>();
	private @NotNull Difficulty difficulty = Difficulty.NORMAL;

	private boolean allowAnimals = true;
	private boolean allowMonsters = true;
	private boolean pvp;
	private boolean hardcore;
	private boolean getKeepSpawnInMemory = true;

	private final Object2LongOpenHashMap<SpawnCategory> ticksPerSpawn = new Object2LongOpenHashMap<>();
	private final Object2IntOpenHashMap<SpawnCategory> spawnLimits = new Object2IntOpenHashMap<>();

	/**
	 * Creates a new mock world.
	 *
	 * @param defaultBlock The block that is spawned at locations 1 to {@code grassHeight}
	 * @param minHeight    The minimum height of the world.
	 * @param maxHeight    The maximum height of the world.
	 * @param grassHeight  The last {@code y} at which {@code defaultBlock} will spawn.
	 */
	public WorldMock(Material defaultBlock, int minHeight, int maxHeight, int grassHeight)
	{
		this(defaultBlock, Biome.PLAINS, minHeight, maxHeight, grassHeight);
	}

	/**
	 * Creates a new mock world.
	 *
	 * @param defaultBlock The block that is spawned at locations 1 to {@code grassHeight}
	 * @param defaultBiome The biome that every block will be in by default.
	 * @param minHeight    The minimum height of the world.
	 * @param maxHeight    The maximum height of the world.
	 * @param grassHeight  The last {@code y} at which {@code defaultBlock} will spawn.
	 */
	public WorldMock(Material defaultBlock, Biome defaultBiome, int minHeight, int maxHeight, int grassHeight)
	{
		this.defaultBlock = defaultBlock;
		this.defaultBiome = defaultBiome;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.grassHeight = grassHeight;
		this.server = MockBukkit.getMock();

		if (this.server != null)
		{
			this.pvp = this.server.getServerConfiguration().isPvpEnabled();
			this.ticksPerSpawn.putAll(this.server.getServerConfiguration().getTicksPerSpawn());
			this.spawnLimits.putAll(this.server.getServerConfiguration().getSpawnLimits());
		}
		else
		{
			this.pvp = true;

			// Set the default ticks per spawn values.
			ticksPerSpawn.put(SpawnCategory.ANIMAL, 400);
			ticksPerSpawn.put(SpawnCategory.MONSTER, 1);
			ticksPerSpawn.put(SpawnCategory.WATER_AMBIENT, 1);
			ticksPerSpawn.put(SpawnCategory.WATER_UNDERGROUND_CREATURE, 1);
			ticksPerSpawn.put(SpawnCategory.WATER_ANIMAL, 1);
			ticksPerSpawn.put(SpawnCategory.AMBIENT, 1);

			spawnLimits.put(SpawnCategory.MONSTER, 70);
			spawnLimits.put(SpawnCategory.ANIMAL, 10);
			spawnLimits.put(SpawnCategory.WATER_ANIMAL, 5);
			spawnLimits.put(SpawnCategory.WATER_AMBIENT, 20);
			spawnLimits.put(SpawnCategory.WATER_UNDERGROUND_CREATURE, 5);
			spawnLimits.put(SpawnCategory.AMBIENT, 15);
		}

		// Set the default gamerule values.
		gameRules.put(GameRule.ANNOUNCE_ADVANCEMENTS, true);
		gameRules.put(GameRule.COMMAND_BLOCK_OUTPUT, true);
		gameRules.put(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, false);
		gameRules.put(GameRule.DO_DAYLIGHT_CYCLE, true);
		gameRules.put(GameRule.DO_ENTITY_DROPS, true);
		gameRules.put(GameRule.DO_FIRE_TICK, true);
		gameRules.put(GameRule.DO_LIMITED_CRAFTING, false);
		gameRules.put(GameRule.DO_MOB_LOOT, true);
		gameRules.put(GameRule.DO_MOB_SPAWNING, true);
		gameRules.put(GameRule.DO_TILE_DROPS, true);
		gameRules.put(GameRule.DO_WEATHER_CYCLE, true);
		gameRules.put(GameRule.KEEP_INVENTORY, false);
		gameRules.put(GameRule.LOG_ADMIN_COMMANDS, true);
		gameRules.put(GameRule.MAX_COMMAND_CHAIN_LENGTH, 65536);
		gameRules.put(GameRule.MAX_ENTITY_CRAMMING, 24);
		gameRules.put(GameRule.MOB_GRIEFING, true);
		gameRules.put(GameRule.NATURAL_REGENERATION, true);
		gameRules.put(GameRule.RANDOM_TICK_SPEED, 3);
		gameRules.put(GameRule.REDUCED_DEBUG_INFO, false);
		gameRules.put(GameRule.SEND_COMMAND_FEEDBACK, true);
		gameRules.put(GameRule.SHOW_DEATH_MESSAGES, true);
		gameRules.put(GameRule.SPAWN_RADIUS, 10);
		gameRules.put(GameRule.SPECTATORS_GENERATE_CHUNKS, true);
	}

	/**
	 * Creates a new mock world.
	 *
	 * @param creator The {@link WorldCreator} to use to create the world.
	 */
	public WorldMock(@NotNull WorldCreator creator)
	{
		this();
		this.name = creator.name();
		this.worldType = creator.type();
		this.seed = creator.seed();
		this.environment = creator.environment();
	}

	/**
	 * Creates a new mock world with a specific height from 0.
	 *
	 * @param defaultBlock The block that is spawned at locations 1 to {@code grassHeight}
	 * @param defaultBiome The biome that every block will be in by default.
	 * @param maxHeight    The maximum height of the world.
	 * @param grassHeight  The last {@code y} at which {@code defaultBlock} will spawn.
	 */
	public WorldMock(Material defaultBlock, Biome defaultBiome, int maxHeight, int grassHeight)
	{
		this(defaultBlock, defaultBiome, 0, maxHeight, grassHeight);
	}

	/**
	 * Creates a new mock world with a specific height from 0.
	 *
	 * @param defaultBlock The block that is spawned at locations 1 to {@code grassHeight}
	 * @param maxHeight    The maximum height of the world.
	 * @param grassHeight  The last {@code y} at which {@code defaultBlock} will spawn.
	 */
	public WorldMock(Material defaultBlock, int maxHeight, int grassHeight)
	{
		this(defaultBlock, 0, maxHeight, grassHeight);
	}

	/**
	 * Creates a new mock world with a height of 128.
	 *
	 * @param defaultBlock The block that is spawned at locations 1 to {@code grassHeight}
	 * @param grassHeight  The last {@code y} at which {@code defaultBlock} will spawn.
	 */
	public WorldMock(Material defaultBlock, int grassHeight)
	{
		this(defaultBlock, 128, grassHeight);
	}

	/**
	 * Creates a new mock world with a height of 128 and will spawn grass until a {@code y} of 4.
	 */
	public WorldMock()
	{
		this(Material.GRASS_BLOCK, 4);
	}

	/**
	 * Makes sure that a certain block exists on the coordinate. Returns that block.
	 *
	 * @param c Creates a block on the given coordinate.
	 * @return A newly created block at that location.
	 */
	public @NotNull BlockMock createBlock(@NotNull Coordinate c)
	{
		if (c.y >= maxHeight)
		{
			throw new ArrayIndexOutOfBoundsException("Y larger than max height");
		}
		else if (c.y < minHeight)
		{
			throw new ArrayIndexOutOfBoundsException("Y smaller than min height");
		}

		Location location = new Location(this, c.x, c.y, c.z);
		BlockMock block;
		if (c.y == minHeight)
		{
			block = new BlockMock(Material.BEDROCK, location);
		}
		else if (c.y <= grassHeight)
		{
			block = new BlockMock(defaultBlock, location);
		}
		else
		{
			block = new BlockMock(location);
		}

		blocks.put(c, block);
		return block;
	}

	@Override
	public int getEntityCount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTileEntityCount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTickableTileEntityCount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getChunkCount()
	{
		return loadedChunks.size();
	}

	@Override
	public int getPlayerCount()
	{
		return getPlayers().size();
	}

	@Override
	public boolean hasStructureAt(@NotNull Position position, @NotNull Structure structure)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull MoonPhase getMoonPhase()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean lineOfSightExists(@NotNull Location from, @NotNull Location to)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCollisionsIn(@NotNull BoundingBox boundingBox)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockMock getBlockAt(int x, int y, int z)
	{
		return getBlockAt(new Coordinate(x, y, z));
	}

	/**
	 * Gets the block at a coordinate.
	 *
	 * @param coordinate The coordinate at which to get the block.
	 * @return The block.
	 */
	public @NotNull BlockMock getBlockAt(@NotNull Coordinate coordinate)
	{
		if (blocks.containsKey(coordinate))
		{
			return blocks.get(coordinate);
		}
		else
		{
			return createBlock(coordinate);
		}
	}

	@Override
	public @NotNull BlockMock getBlockAt(@NotNull Location location)
	{
		return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	@Override
	@Deprecated(since = "1.18")
	public @NotNull Block getBlockAtKey(long key)
	{
		return getBlockAt(getLocationAtKey(key));
	}

	@Override
	public @NotNull Location getLocationAtKey(long key)
	{
		return World.super.getLocationAtKey(key);
	}

	@Override
	public @NotNull String getName()
	{
		return name;
	}

	/**
	 * Give a new name to this world.
	 *
	 * @param name The new name of this world.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public @NotNull UUID getUID()
	{
		return uuid;
	}

	@Override
	public @NotNull Location getSpawnLocation()
	{
		if (spawnLocation == null)
		{
			setSpawnLocation(0, grassHeight + 1, 0);
		}
		return spawnLocation;
	}

	@Override
	public boolean setSpawnLocation(@NotNull Location location)
	{
		return setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	@Override
	public boolean setSpawnLocation(int x, int y, int z)
	{
		if (spawnLocation == null)
		{
			spawnLocation = new Location(this, x, y, z);
		}
		else
		{
			spawnLocation.setX(x);
			spawnLocation.setY(y);
			spawnLocation.setZ(z);
		}
		return true;
	}

	@Override
	public @NotNull List<Entity> getEntities()
	{
		return server.getEntities().stream().filter(entity -> entity.getWorld() == this).filter(EntityMock::isValid)
				.collect(Collectors.toList());
	}

	@Override
	public @NotNull ChunkMock getChunkAt(int x, int z)
	{
		return getChunkAt(x, z, false);
	}

	@Override
	public @NotNull ChunkMock getChunkAt(int x, int z, boolean generate)
	{
		return getChunkAt(new ChunkCoordinate(x, z));
	}

	/**
	 * Gets the chunk at a specific chunk coordinate.
	 * <p>
	 * If there is no chunk recorded at the location, one will be created.
	 *
	 * @param coordinate The coordinate at which to get the chunk.
	 * @return The chunk at the location.
	 */
	@NotNull
	public ChunkMock getChunkAt(@NotNull ChunkCoordinate coordinate)
	{
		ChunkMock chunk = loadedChunks.get(coordinate);
		if (chunk == null)
		{
			chunk = savedChunks.get(coordinate);
			if (chunk == null)
			{
				chunk = new ChunkMock(this, coordinate.getX(), coordinate.getZ());
			}
			loadedChunks.put(coordinate, chunk);
		}
		return chunk;
	}

	@Override
	public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, byte[] message)
	{
		StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);

		for (Player player : this.getPlayers())
		{
			player.sendPluginMessage(source, channel, message);
		}
	}

	@Override
	public @NotNull Set<String> getListeningPluginChannels()
	{
		Set<String> result = new HashSet<>();

		for (Player player : this.getPlayers())
		{
			result.addAll(player.getListeningPluginChannels());
		}

		return result;
	}

	@Override
	public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue)
	{
		metadataTable.setMetadata(metadataKey, newMetadataValue);
	}

	@Override
	public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey)
	{
		return metadataTable.getMetadata(metadataKey);
	}

	@Override
	public boolean hasMetadata(@NotNull String metadataKey)
	{
		return metadataTable.hasMetadata(metadataKey);
	}

	@Override
	public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin)
	{
		metadataTable.removeMetadata(metadataKey, owningPlugin);
	}

	/**
	 * @see MetadataTable#clearMetadata(Plugin)
	 */
	public void clearMetadata(Plugin plugin)
	{
		metadataTable.clearMetadata(plugin);
	}

	@Override
	public int getHighestBlockYAt(int x, int z)
	{
		return this.getHighestBlockYAt(x, z, HeightMap.MOTION_BLOCKING);
	}

	@Override
	public int getHighestBlockYAt(@NotNull Location location)
	{
		return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
	}

	@Override
	public @NotNull Block getHighestBlockAt(int x, int z)
	{
		return getHighestBlockAt(x, z, HeightMap.MOTION_BLOCKING);
	}

	@Override
	public @NotNull Block getHighestBlockAt(Location location)
	{
		return getHighestBlockAt(location, HeightMap.MOTION_BLOCKING);
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.15")
	@ApiStatus.ScheduledForRemoval(inVersion = "1.21")
	public int getHighestBlockYAt(int x, int z, @NotNull HeightmapType heightmap) throws UnsupportedOperationException
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Chunk getChunkAt(@NotNull Location location)
	{
		return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
	}

	@Override
	public @NotNull Chunk getChunkAt(@NotNull Block block)
	{
		return getChunkAt(block.getLocation());
	}

	@Override
	public boolean isChunkLoaded(Chunk chunk)
	{
		return isChunkLoaded(chunk.getX(), chunk.getZ());
	}

	@Override
	public Chunk @NotNull [] getLoadedChunks()
	{
		return loadedChunks.values().toArray(new Chunk[0]);
	}

	@Override
	public void loadChunk(@NotNull Chunk chunk)
	{
		loadChunk(chunk.getX(), chunk.getZ());
	}

	@Override
	public boolean isChunkLoaded(int x, int z)
	{
		ChunkCoordinate coordinate = new ChunkCoordinate(x, z);
		return loadedChunks.containsKey(coordinate);
	}

	@Override
	@Deprecated(since = "1.14")
	public boolean isChunkInUse(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void loadChunk(int x, int z)
	{
		loadChunk(x, z, true);
	}

	@Override
	public boolean loadChunk(int x, int z, boolean generate)
	{
		AsyncCatcher.catchOp("chunk load");
		getChunkAt(x, z, generate);
		return true;
	}

	@Override
	public boolean unloadChunk(@NotNull Chunk chunk)
	{
		return this.unloadChunk(chunk.getX(), chunk.getZ());
	}

	@Override
	public boolean unloadChunk(int x, int z)
	{
		return unloadChunk(x, z, true);
	}

	@Override
	public boolean unloadChunk(int x, int z, boolean save)
	{
		AsyncCatcher.catchOp("chunk unload");
		ChunkCoordinate chunkCoordinate = new ChunkCoordinate(x, z);
		ChunkMock chunk = loadedChunks.remove(chunkCoordinate);
		if (chunk == null)
		{
			return true;
		}
		if (save)
		{
			savedChunks.put(chunkCoordinate, chunk);
		}
		return true;
	}

	@Override
	public boolean unloadChunkRequest(int x, int z)
	{
		AsyncCatcher.catchOp("chunk unload");
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.13")
	public boolean regenerateChunk(int x, int z)
	{
		AsyncCatcher.catchOp("chunk regenerate");
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.8")
	public boolean refreshChunk(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Item dropItem(@NotNull Location loc, @NotNull ItemStack item)
	{
		return dropItem(loc, item, null);
	}

	@Override
	public @NotNull Item dropItem(@NotNull Location location, @NotNull ItemStack item,
			@Nullable Consumer<? super Item> function)
	{
		Preconditions.checkNotNull(location, "The provided location must not be null.");
		Preconditions.checkNotNull(item, "Cannot drop items that are null.");
		Preconditions.checkArgument(!item.getType().isAir(), "Cannot drop air.");

		ItemEntityMock entity = new ItemEntityMock(server, UUID.randomUUID(), item);
		entity.setLocation(location);

		if (function != null)
		{
			function.accept(entity);
		}

		server.registerEntity(entity);
		callSpawnEvent(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

		return entity;
	}

	@Override
	public @NotNull Item dropItemNaturally(@NotNull Location loc, @NotNull ItemStack item)
	{
		return dropItemNaturally(loc, item, null);
	}

	@Override
	public @NotNull Item dropItemNaturally(@NotNull Location location, @NotNull ItemStack item,
			@Nullable Consumer<? super Item> function)
	{
		Preconditions.checkNotNull(location, "The provided location must not be null.");

		Random random = ThreadLocalRandom.current();

		double xs = random.nextFloat() * 0.5F + 0.25;
		double ys = random.nextFloat() * 0.5F + 0.25;
		double zs = random.nextFloat() * 0.5F + 0.25;

		Location loc = location.clone();
		loc.setX(loc.getX() + xs);
		loc.setY(loc.getY() + ys);
		loc.setZ(loc.getZ() + zs);

		return dropItem(loc, item, function);
	}

	@Override
	public @NotNull Arrow spawnArrow(Location location, Vector direction, float speed, float spread)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean generateTree(Location location, TreeType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.17")
	public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean generateTree(Location location, Random random, TreeType type,
			Predicate<? super BlockState> statePredicate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz)
			throws IllegalArgumentException
	{
		return this.spawn(location, clazz, null, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}

	@Override
	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz,
			boolean randomizeData, @Nullable Consumer<? super T> function) throws IllegalArgumentException
	{
		return this.spawn(location, clazz, function, CreatureSpawnEvent.SpawnReason.CUSTOM, randomizeData, true);
	}

	@Override
	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz,
			@Nullable Consumer<? super T> function, CreatureSpawnEvent.@NotNull SpawnReason reason)
			throws IllegalArgumentException
	{
		return this.spawn(location, clazz, function, reason, true, true);
	}

	/**
	 * Spawns an entity.
	 *
	 * @param location       The location to spawn the entity at.
	 * @param clazz          The class of entity to spawn. This should be the class of the Bukkit interface, not the mock.
	 * @param function       A function to call once the entity has been spawned.
	 * @param reason         The reason for spawning the entity.
	 * @param randomizeData  Whether data should be randomized. Currently, does nothing.
	 * @param callSpawnEvent Whether the entities spawn event should be called
	 * @param <T>            The entity type.
	 * @return The spawned entity.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity> @NotNull T spawn(@Nullable Location location, @Nullable Class<T> clazz,
			@Nullable Consumer<? super T> function, CreatureSpawnEvent.@NotNull SpawnReason reason,
			boolean randomizeData, boolean callSpawnEvent)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		Preconditions.checkNotNull(clazz, "Class cannot be null");
		Preconditions.checkNotNull(reason, "Reason cannot be null");

		EntityMock entity = this.mockEntity(location, clazz, randomizeData);

		entity.setLocation(location);
		entity.setSpawnReason(reason);

		if (entity instanceof MobMock mob)
		{
			mob.finalizeSpawn();
		}

		// CraftBukkit doesn't check this when spawning, it's done when the entity is
		// ticking so
		// it ends up being spawned for one tick before being removed. We don't have a
		// great way
		// to do that, so we just do it here.
		if (entity instanceof Monster && this.getDifficulty() == Difficulty.PEACEFUL)
		{
			entity.remove();
		}

		if (function != null)
		{
			function.accept((T) entity);
		}

		server.registerEntity(entity);
		if (callSpawnEvent)
		{
			callSpawnEvent(entity, reason);
		}

		return (T) entity;
	}

	@Override
	public @NotNull Entity spawnEntity(@NotNull Location loc, @NotNull EntityType type)
	{
		return spawn(loc, type.getEntityClass());
	}

	@NotNull
	@Override
	public Entity spawnEntity(@NotNull Location loc, @NotNull EntityType type, boolean randomizeData)
	{
		return this.spawn(loc, type.getEntityClass(), randomizeData, null);
	}

	private <T extends Entity> @NotNull EntityMock mockEntity(@NotNull Location location, @NotNull Class<T> clazz,
			boolean randomizeData)
	{
		AsyncCatcher.catchOp("entity add");
		if (clazz == ArmorStand.class)
		{
			return new ArmorStandMock(server, UUID.randomUUID());
		}
		else if (clazz == ExperienceOrb.class)
		{
			return new ExperienceOrbMock(server, UUID.randomUUID());
		}
		else if (clazz == Firework.class)
		{
			return new FireworkMock(server, UUID.randomUUID());
		}
		else if (clazz == Hanging.class)
		{
			// LeashHitch has no direction and is always centered
			if (LeashHitch.class.isAssignableFrom(clazz))
			{
				throw new UnimplementedOperationException();
			}
			BlockFace spawnFace = BlockFace.SELF;
			BlockFace[] faces = (ItemFrame.class.isAssignableFrom(clazz)) ? new BlockFace[]
			{ BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN }
					: new BlockFace[]
					{ BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
			for (BlockFace face : faces)
			{
				Block block = this.getBlockAt(location.add(face.getModX(), face.getModY(), face.getModZ()));
				if (!block.getType().isSolid()
						&& (block.getType() != Material.REPEATER && block.getType() != Material.COMPARATOR))
					continue;

				boolean taken = false;

				// TODO: Check if the entity's bounding box collides with any other hanging
				// entities.

				if (taken)
					continue;

				spawnFace = face;
				break;
			}
			if (spawnFace == BlockFace.SELF)
			{
				spawnFace = BlockFace.SOUTH;
			}
			spawnFace = spawnFace.getOppositeFace();
			// TODO: Spawn entities here.
			throw new UnimplementedOperationException();
		}
		else if (clazz == Item.class)
		{
			throw new IllegalArgumentException("Items must be spawned using World#dropItem(...)");
		}
		else if (clazz == FishHook.class)
		{
			return new FishHookMock(server, UUID.randomUUID());
		}
		else if (clazz == Player.class)
		{
			throw new IllegalArgumentException("Player Entities cannot be spawned, use ServerMock#addPlayer(...)");
		}
		else if (clazz == Zombie.class)
		{
			return new ZombieMock(server, UUID.randomUUID());
		}
		else if (clazz == Enderman.class)
		{
			return new EndermanMock(server, UUID.randomUUID());
		}
		else if (clazz == Horse.class)
		{
			return new HorseMock(server, UUID.randomUUID());
		}
		else if (clazz == Sheep.class)
		{
			return new SheepMock(server, UUID.randomUUID());
		}
		else if (clazz == Allay.class)
		{
			return new AllayMock(server, UUID.randomUUID());
		}
		else if (clazz == Warden.class)
		{
			return new WardenMock(server, UUID.randomUUID());
		}
		else if (clazz == Donkey.class)
		{
			return new DonkeyMock(server, UUID.randomUUID());
		}
		else if (clazz == Llama.class)
		{
			return new LlamaMock(server, UUID.randomUUID());
		}
		else if (clazz == Mule.class)
		{
			return new MuleMock(server, UUID.randomUUID());
		}
		else if (clazz == SkeletonHorse.class)
		{
			return new SkeletonHorseMock(server, UUID.randomUUID());
		}
		else if (clazz == ZombieHorse.class)
		{
			return new ZombieHorseMock(server, UUID.randomUUID());
		}
		else if (clazz == Cow.class)
		{
			return new CowMock(server, UUID.randomUUID());
		}
		else if (clazz == Chicken.class)
		{
			return new ChickenMock(server, UUID.randomUUID());
		}
		else if (clazz == Skeleton.class)
		{
			return new SkeletonMock(server, UUID.randomUUID());
		}
		else if (clazz == Stray.class)
		{
			return new StrayMock(server, UUID.randomUUID());
		}
		else if (clazz == WitherSkeleton.class)
		{
			return new WitherSkeletonMock(server, UUID.randomUUID());
		}
		else if (clazz == Spider.class)
		{
			return new SpiderMock(server, UUID.randomUUID());
		}
		else if (clazz == Blaze.class)
		{
			return new BlazeMock(server, UUID.randomUUID());
		}
		else if (clazz == CaveSpider.class)
		{
			return new CaveSpiderMock(server, UUID.randomUUID());
		}
		else if (clazz == Giant.class)
		{
			return new GiantMock(server, UUID.randomUUID());
		}
		else if (clazz == Axolotl.class)
		{
			return new AxolotlMock(server, UUID.randomUUID());
		}
		else if (clazz == Bat.class)
		{
			return new BatMock(server, UUID.randomUUID());
		}
		else if (clazz == Cat.class)
		{
			return new CatMock(server, UUID.randomUUID());
		}
		else if (clazz == Frog.class)
		{
			return new FrogMock(server, UUID.randomUUID());
		}
		else if (clazz == Fox.class)
		{
			return new FoxMock(server, UUID.randomUUID());
		}
		else if (clazz == Ghast.class)
		{
			return new GhastMock(server, UUID.randomUUID());
		}
		else if (clazz == MushroomCow.class)
		{
			return new MushroomCowMock(server, UUID.randomUUID());
		}
		else if (clazz == Tadpole.class)
		{
			return new TadpoleMock(server, UUID.randomUUID());
		}
		else if (clazz == Cod.class)
		{
			return new CodMock(server, UUID.randomUUID());
		}
		else if (clazz == TropicalFish.class)
		{
			return new TropicalFishMock(server, UUID.randomUUID());
		}
		else if (clazz == Salmon.class)
		{
			return new SalmonMock(server, UUID.randomUUID());
		}
		else if (clazz == PufferFish.class)
		{
			return new PufferFishMock(server, UUID.randomUUID());
		}
		else if (clazz == Bee.class)
		{
			return new BeeMock(server, UUID.randomUUID());
		}
		else if (clazz == Creeper.class)
		{
			return new CreeperMock(server, UUID.randomUUID());
		}
		else if (clazz == Wolf.class)
		{
			return new WolfMock(server, UUID.randomUUID());
		}
		else if (clazz == Goat.class)
		{
			return new GoatMock(server, UUID.randomUUID());
		}
		else if (clazz == Egg.class)
		{
			return new EggMock(server, UUID.randomUUID());
		}
		else if (clazz == Pig.class)
		{
			return new PigMock(server, UUID.randomUUID());
		}
		else if (clazz == ElderGuardian.class)
		{
			return new ElderGuardianMock(server, UUID.randomUUID());
		}
		else if (clazz == Guardian.class)
		{
			return new GuardianMock(server, UUID.randomUUID());
		}
		else if (clazz == PolarBear.class)
		{
			return new PolarBearMock(server, UUID.randomUUID());
		}
		else if (clazz == SmallFireball.class)
		{
			return new SmallFireballMock(server, UUID.randomUUID());
		}
		else if (clazz == LargeFireball.class)
		{
			return new LargeFireballMock(server, UUID.randomUUID());
		}
		else if (clazz == DragonFireball.class)
		{
			return new DragonFireballMock(server, UUID.randomUUID());
		}
		else if (clazz == WitherSkull.class)
		{
			return new WitherSkullMock(server, UUID.randomUUID());
		}
		else if (clazz == PoweredMinecart.class)
		{
			return new PoweredMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == Camel.class)
		{
			return new CamelMock(server, UUID.randomUUID());
		}
		else if (clazz == CommandMinecart.class)
		{
			return new CommandMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == ExplosiveMinecart.class)
		{
			return new ExplosiveMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == HopperMinecart.class)
		{
			return new HopperMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == SpawnerMinecart.class)
		{
			return new SpawnerMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == RideableMinecart.class)
		{
			return new RideableMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == StorageMinecart.class)
		{
			return new StorageMinecartMock(server, UUID.randomUUID());
		}
		else if (clazz == AreaEffectCloud.class)
		{
			return new AreaEffectCloudMock(server, UUID.randomUUID());
		}
		else if (clazz == Boat.class)
		{
			return new BoatMock(server, UUID.randomUUID());
		}
		else if (clazz == ChestBoat.class)
		{
			return new ChestBoatMock(server, UUID.randomUUID());
		}
		else if (clazz == EnderPearl.class)
		{
			return new EnderPearlMock(server, UUID.randomUUID());
		}
		else if (clazz == Panda.class)
		{
			return new PandaMock(server, UUID.randomUUID());
		}
		else if (clazz == Rabbit.class)
		{
			return new RabbitMock(server, UUID.randomUUID());
		}
		else if (clazz == Slime.class)
		{
			return new SlimeMock(server, UUID.randomUUID());
		}
		else if (clazz == Ocelot.class)
		{
			return new OcelotMock(server, UUID.randomUUID());
		}
		else if (clazz == Parrot.class)
		{
			return new ParrotMock(server, UUID.randomUUID());
		}
		else if (clazz == Squid.class)
		{
			return new SquidMock(server, UUID.randomUUID());
		}
		else if (clazz == GlowSquid.class)
		{
			return new GlowSquidMock(server, UUID.randomUUID());
		}
		else if (clazz == LlamaSpit.class)
		{
			return new LlamaSpitMock(server, UUID.randomUUID());
		}
		else if (clazz == Dolphin.class)
		{
			return new DolphinMock(server, UUID.randomUUID());
		}
		else if (clazz == MagmaCube.class)
		{
			return new MagmaCubeMock(server, UUID.randomUUID());
		}
		else if (clazz == Endermite.class)
		{
			return new EndermiteMock(server, UUID.randomUUID());
		}
		else if (clazz == Silverfish.class)
		{
			return new SilverfishMock(server, UUID.randomUUID());
		}
		else if (clazz == Turtle.class)
		{
			return new TurtleMock(server, UUID.randomUUID());
		}
		else if (clazz == ThrownExpBottle.class)
		{
			return new ThrownExpBottleMock(server, UUID.randomUUID());
		}
		else if (clazz == Snowball.class)
		{
			return new SnowballMock(server, UUID.randomUUID());
		}
		else if (clazz == LeashHitch.class)
		{
			return new LeashHitchMock(server, UUID.randomUUID());
		}
		else if (clazz == PigZombie.class)
		{
			return new PigZombieMock(server, UUID.randomUUID());
		}
		else if (clazz == BlockDisplay.class)
		{
			return new BlockDisplayMock(server, UUID.randomUUID());
		}
		else if (clazz == ItemDisplay.class)
		{
			return new ItemDisplayMock(server, UUID.randomUUID());
		}
		else if (clazz == Arrow.class)
		{
			return new ArrowMock(server, UUID.randomUUID());
		}
		else if (clazz == SpectralArrow.class)
		{
			return new SpectralArrowMock(server, UUID.randomUUID());
		}
		else if (clazz == Trident.class)
		{
			return new TridentMock(server, UUID.randomUUID());
		}
		else if (clazz == Marker.class)
		{
			return new MarkerMock(server, UUID.randomUUID());
		}
		throw new UnimplementedOperationException();
	}

	private void callSpawnEvent(EntityMock entity, CreatureSpawnEvent.@NotNull SpawnReason reason)
	{

		boolean success; // Here for future implementation (see below)

		if (entity instanceof LivingEntity living && !(entity instanceof Player))
		{
			boolean isAnimal = entity instanceof Animals || entity instanceof WaterMob || entity instanceof Golem;
			boolean isMonster = entity instanceof Monster || entity instanceof Ghast || entity instanceof Slime;

			if (reason != CreatureSpawnEvent.SpawnReason.CUSTOM)
			{
				if (isAnimal && !getAllowAnimals() || isMonster && !getAllowMonsters())
				{
					entity.remove();
					return;
				}
			}

			success = new CreatureSpawnEvent(living, reason).callEvent();
		}
		else if (entity instanceof Item item)
		{
			success = new ItemSpawnEvent(item).callEvent();
		}
		else if (entity instanceof Player)
		{
			success = false; // Shouldn't ever be called here but just for parody.
		}
		else if (entity instanceof Projectile)
		{
			success = new ProjectileLaunchEvent(entity).callEvent();
		}
		else
		{
			success = new EntitySpawnEvent(entity).callEvent();
		}

		if (!success || !entity.isValid())
		{
			Entity vehicle = entity.getVehicle();
			if (vehicle != null)
			{
				vehicle.remove();
			}
			for (Entity passenger : entity.getTransitivePassengers())
			{
				passenger.remove();
			}
			entity.remove();
		}
	}

	@Override
	public @NotNull LightningStrike strikeLightning(Location loc)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull LightningStrike strikeLightningEffect(Location loc)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public @Nullable Location findLightningRod(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location findLightningTarget(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<LivingEntity> getLivingEntities()
	{
		return getEntities().stream().filter(LivingEntity.class::isInstance).map(LivingEntity.class::cast)
				.collect(Collectors.toList());
	}

	@Override
	@SafeVarargs
	public final <T extends Entity> @NotNull Collection<T> getEntitiesByClass(Class<T> @NotNull... classes)
	{
		List<T> entities = new ArrayList<>();
		for (Class<T> clazz : classes)
		{
			entities.addAll(getEntitiesByClass(clazz));
		}
		return entities;
	}

	@Override
	public <T extends Entity> @NotNull Collection<T> getEntitiesByClass(@NotNull Class<T> cls)
	{
		return getEntities().stream().filter(entity -> cls.isAssignableFrom(entity.getClass())).map(cls::cast)
				.collect(Collectors.toList());
	}

	@Override
	public @NotNull Collection<Entity> getEntitiesByClasses(Class<?> @NotNull... classes)
	{
		List<Entity> entities = new ArrayList<>();
		for (Class<?> clazz : classes)
		{
			entities.addAll(
					getEntities().stream().filter(entity -> clazz.isAssignableFrom(entity.getClass())).toList());
		}
		return entities;
	}

	@Override
	public <T extends Entity> @NotNull T createEntity(@NotNull Location location, @NotNull Class<T> aClass)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull CompletableFuture<Chunk> getChunkAtAsync(int x, int z, boolean gen, boolean urgent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<Player> getPlayers()
	{
		return Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == this).collect(Collectors.toList());
	}

	@Override
	public @NotNull Collection<Entity> getNearbyEntities(Location location, double x, double y, double z)
	{
		return getNearbyEntities(location, x, y, z, null);
	}

	@Override
	public @Nullable Entity getEntity(@NotNull UUID uuid)
	{
		Preconditions.checkArgument(uuid != null, "UUID cannot be null");
		return getEntities().stream().filter(entity -> entity.getUniqueId().equals(uuid)).findFirst().orElse(null);
	}

	@Override
	public long getTime()
	{
		return this.getFullTime() % 24000L;
	}

	@Override
	public void setTime(long time)
	{
		long base = this.getFullTime() - this.getFullTime() % 24000L;
		this.setFullTime(base + time % 24000L);
	}

	@Override
	public long getFullTime()
	{
		return this.fullTime;
	}

	@Override
	public void setFullTime(long time)
	{
		TimeSkipEvent event = new TimeSkipEvent(this, TimeSkipEvent.SkipReason.CUSTOM, time - this.getFullTime());
		this.server.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			this.fullTime += event.getSkipAmount();
		}
	}

	@Override
	public boolean isDayTime()
	{
		return false;
	}

	@Override
	public boolean hasStorm()
	{
		return this.storming;
	}

	@Override
	public void setStorm(boolean hasStorm)
	{
		if (this.storming == hasStorm)
		{
			return;
		}
		WeatherChangeEvent weather = new WeatherChangeEvent(this, hasStorm, WeatherChangeEvent.Cause.PLUGIN);
		Bukkit.getServer().getPluginManager().callEvent(weather);
		if (weather.isCancelled())
		{
			return;
		}
		this.storming = hasStorm;
		this.setWeatherDuration(0);
		this.setClearWeatherDuration(0);
	}

	@Override
	public int getWeatherDuration()
	{
		return this.weatherDuration;
	}

	@Override
	public void setWeatherDuration(int duration)
	{
		this.weatherDuration = duration;
	}

	@Override
	public boolean isThundering()
	{
		return this.thundering;
	}

	@Override
	public void setThundering(boolean thundering)
	{
		if (this.thundering == thundering)
		{
			return;
		}
		ThunderChangeEvent thunder = new ThunderChangeEvent(this, thundering, ThunderChangeEvent.Cause.PLUGIN); // Paper
		Bukkit.getServer().getPluginManager().callEvent(thunder);
		if (thunder.isCancelled())
		{
			return;
		}
		this.thundering = thundering;
		this.setThunderDuration(0);
		this.setClearWeatherDuration(0);
	}

	@Override
	public int getThunderDuration()
	{
		return this.thunderDuration;
	}

	@Override
	public void setThunderDuration(int duration)
	{
		this.thunderDuration = duration;
	}

	@Override
	public boolean isClearWeather()
	{

		return !this.hasStorm() && !this.isThundering();
	}

	@Override
	public int getClearWeatherDuration()
	{
		return this.clearWeatherDuration;
	}

	@Override
	public void setClearWeatherDuration(int duration)
	{
		this.clearWeatherDuration = duration;
	}

	@Override
	public boolean createExplosion(double x, double y, double z, float power)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean createExplosion(double x, double y, double z, float power, boolean setFire)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean createExplosion(Location loc, float power)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean createExplosion(Location loc, float power, boolean setFire)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean createExplosion(@Nullable Entity source, @NotNull Location loc, float power, boolean setFire,
			boolean breakBlocks)
	{
		return false;
	}

	@Override
	public @NotNull Environment getEnvironment()
	{
		return this.environment;
	}

	/**
	 * Set a new environment type for this world.
	 *
	 * @param environment The world environnement type.
	 */
	public void setEnvironment(Environment environment)
	{
		this.environment = environment;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public boolean getPVP()
	{
		return this.pvp;
	}

	@Override
	public void setPVP(boolean pvp)
	{
		this.pvp = pvp;
	}

	@Override
	public ChunkGenerator getGenerator()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public BiomeProvider getBiomeProvider()
	{
		return biomeProviderMock;
	}

	@Override
	public void save()
	{
		AsyncCatcher.catchOp("world save");
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<BlockPopulator> getPopulators()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends LivingEntity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz,
			CreatureSpawnEvent.@NotNull SpawnReason spawnReason, boolean randomizeData,
			@Nullable Consumer<? super T> function) throws IllegalArgumentException
	{
		throw new UnimplementedOperationException();
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull FallingBlock spawnFallingBlock(Location location, org.bukkit.material.MaterialData data)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.7.5")
	public @NotNull FallingBlock spawnFallingBlock(Location location, Material material, byte data)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playEffect(@NotNull Location location, @NotNull Effect effect, int data)
	{
		this.playEffect(location, effect, data, 64);
	}

	@Override
	public void playEffect(@NotNull Location location, @NotNull Effect effect, int data, int radius)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		Preconditions.checkNotNull(effect, "Effect cannot be null");
		Preconditions.checkNotNull(location.getWorld(), "World cannot be null");
	}

	@Override
	public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, T data)
	{
		this.playEffect(location, effect, data, 64);
	}

	@Override
	public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T data, int radius)
	{
		if (data != null)
		{
			Preconditions.checkArgument(effect.getData() != null && effect.getData().isAssignableFrom(data.getClass()),
					"Wrong kind of data for this effect!");
		}
		else
		{
			// Special case: the axis is optional for ELECTRIC_SPARK
			Preconditions.checkArgument(effect.getData() == null || effect == Effect.ELECTRIC_SPARK,
					"Wrong kind of data for this effect!");

		}
	}

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public @NotNull ChunkSnapshotMock getEmptyChunkSnapshot(int chunkX, int chunkZ, boolean includeBiome,
			boolean includeBiomeTempRain)
	{
		// Cubic size of the chunk (w * w * h).
		int size = (16 * 16) * Math.abs((getMaxHeight() - getMinHeight()));
		ImmutableMap.Builder<Coordinate, BlockData> chunkBlockData = ImmutableMap.builderWithExpectedSize(size);
		ImmutableMap.Builder<Coordinate, Biome> chunkBiomes = ImmutableMap.builderWithExpectedSize(size);
		for (int x = 0; x < 16; x++)
		{
			for (int y = getMinHeight(); y < getMaxHeight(); y++)
			{
				for (int z = 0; z < 16; z++)
				{
					Coordinate coord = new Coordinate(x, y, z);
					chunkBlockData.put(coord, new BlockDataMock(Material.AIR));
					if (includeBiome || includeBiomeTempRain)
					{
						chunkBiomes.put(coord, Biome.PLAINS);
					}
				}
			}
		}
		return new ChunkSnapshotMock(chunkX, chunkZ, getMinHeight(), getMaxHeight(), getName(), getFullTime(),
				chunkBlockData.build(), (includeBiome || includeBiomeTempRain) ? chunkBiomes.build() : null);
	}

	@Override
	public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals)
	{
		this.allowMonsters = allowMonsters;
		this.allowAnimals = allowAnimals;
	}

	@Override
	public boolean getAllowAnimals()
	{
		return this.allowAnimals;
	}

	@Override
	public boolean getAllowMonsters()
	{
		return this.allowMonsters;
	}

	@Override
	@Deprecated(since = "1.15")
	public @NotNull Biome getBiome(int x, int z)
	{
		return this.getBiome(x, 0, z);
	}

	@Override
	@Deprecated(since = "1.15")
	public void setBiome(int x, int z, @NotNull Biome bio)
	{
		for (int y = this.getMinHeight(); y < this.getMaxHeight(); y++)
		{
			this.setBiome(x, y, z, bio);
		}
	}

	@Override
	@Deprecated(since = "1.15")
	public double getTemperature(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.15")
	public double getHumidity(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMinHeight()
	{
		return minHeight;
	}

	@Override
	public int getMaxHeight()
	{
		return maxHeight;
	}

	@Override
	public @NotNull BiomeProvider vanillaBiomeProvider()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSeaLevel()
	{
		return SEA_LEVEL;
	}

	@Override
	public boolean getKeepSpawnInMemory()
	{
		return this.getKeepSpawnInMemory;
	}

	@Override
	public void setKeepSpawnInMemory(boolean keepLoaded)
	{
		this.getKeepSpawnInMemory = keepLoaded;
	}

	@Override
	public boolean isAutoSave()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAutoSave(boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Difficulty getDifficulty()
	{
		return this.difficulty;
	}

	@Override
	public void setDifficulty(@NotNull Difficulty difficulty)
	{
		this.difficulty = difficulty;
	}

	@Override
	public @NotNull File getWorldFolder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.16")
	public WorldType getWorldType()
	{
		return this.worldType;
	}

	@Override
	public boolean canGenerateStructures()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.18")
	public long getTicksPerAnimalSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.ANIMAL);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns)
	{
		this.setTicksPerSpawns(SpawnCategory.ANIMAL, ticksPerAnimalSpawns);
	}

	@Override
	@Deprecated(since = "1.18")
	public long getTicksPerMonsterSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.MONSTER);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns)
	{
		this.setTicksPerSpawns(SpawnCategory.MONSTER, ticksPerMonsterSpawns);
	}

	@Override
	@Deprecated(since = "1.18")
	public int getMonsterSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.MONSTER);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setMonsterSpawnLimit(int limit)
	{
		this.setSpawnLimit(SpawnCategory.MONSTER, limit);
	}

	@Override
	@Deprecated(since = "1.18")
	public int getAnimalSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.ANIMAL);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setAnimalSpawnLimit(int limit)
	{
		this.setSpawnLimit(SpawnCategory.ANIMAL, limit);
	}

	@Override
	@Deprecated(since = "1.18")
	public int getWaterAnimalSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setWaterAnimalSpawnLimit(int limit)
	{
		this.setSpawnLimit(SpawnCategory.WATER_ANIMAL, limit);
	}

	@Override
	@Deprecated(since = "1.18")
	public int getWaterUndergroundCreatureSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setWaterUndergroundCreatureSpawnLimit(int limit)
	{
		this.setSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE, limit);
	}

	@Override
	@Deprecated(since = "1.18")
	public int getAmbientSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.AMBIENT);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setAmbientSpawnLimit(int limit)
	{
		this.setSpawnLimit(SpawnCategory.AMBIENT, limit);
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, float volume, float pitch)
	{
		this.playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, float volume, float pitch)
	{
		this.playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category,
			float volume, float pitch)
	{
		for (Player player : getPlayers())
		{
			player.playSound(location, sound, category, volume, pitch);
		}
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category,
			float volume, float pitch)
	{
		for (Player player : getPlayers())
		{
			player.playSound(location, sound, category, volume, pitch);
		}
	}

	@Override
	public void playSound(Entity entity, Sound sound, float volume, float pitch)
	{
		this.playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull String sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull String sound, @NotNull SoundCategory category, float volume,
			float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@Nullable Entity entity, @Nullable Sound sound, @Nullable SoundCategory category,
			float volume, float pitch)
	{
		if (entity == null || entity.getWorld() != this || sound == null || category == null)
		{
			// Null values are simply ignored - This is inline with CB behaviour
			return;
		}

		for (Player player : getPlayers())
		{
			player.playSound(entity, sound, category, volume, pitch);
		}
	}

	@Override
	public String @NotNull [] getGameRules()
	{
		return gameRules.values().stream().map(Object::toString).toList().toArray(new String[0]);
	}

	@Override
	@Deprecated(since = "1.13")
	public String getGameRuleValue(@Nullable String rule)
	{
		if (rule == null)
		{
			return null;
		}
		GameRule<?> gameRule = GameRule.getByName(rule);
		if (gameRule == null)
		{
			return null;
		}
		return getGameRuleValue(gameRule).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated(since = "1.13")
	public boolean setGameRuleValue(@Nullable String rule, @NotNull String value)
	{
		if (rule == null)
		{
			return false;
		}
		GameRule<?> gameRule = GameRule.getByName(rule);
		if (gameRule == null)
		{
			return false;
		}
		if (gameRule.getType().equals(Boolean.class)
				&& (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")))
		{
			WorldGameRuleChangeEvent event = new WorldGameRuleChangeEvent(this, null, gameRule, value);
			if (!event.callEvent())
			{
				return false;
			}
			return setGameRule((GameRule<Boolean>) gameRule, value.equalsIgnoreCase("true"));
		}
		else if (gameRule.getType().equals(Integer.class))
		{
			try
			{
				int intValue = Integer.parseInt(value);
				WorldGameRuleChangeEvent event = new WorldGameRuleChangeEvent(this, null, gameRule, value);
				if (!event.callEvent())
				{
					return false;
				}
				return setGameRule((GameRule<Integer>) gameRule, intValue);
			}
			catch (NumberFormatException e)
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean isGameRule(@Nullable String rule)
	{
		return rule != null && GameRule.getByName(rule) != null;
	}

	@Override
	public @NotNull WorldBorderMock getWorldBorder()
	{
		if (this.worldBorder == null)
		{
			this.worldBorder = new WorldBorderMock(this);
		}
		return this.worldBorder;
	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @Nullable List<Player> receivers, @Nullable Player source,
			double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra,
			@Nullable T data, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> T getGameRuleValue(@NotNull GameRule<T> rule)
	{
		return rule.getType().cast(gameRules.get(rule));
	}

	@Override
	public <T> T getGameRuleDefault(GameRule<T> rule)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> boolean setGameRule(GameRule<T> rule, T newValue)
	{
		gameRules.put(rule, newValue);
		return true;
	}

	@Override
	public boolean isChunkGenerated(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Entity> getNearbyEntities(Location location, double x, double y, double z,
			Predicate<? super Entity> filter)
	{
		return getNearbyEntities(BoundingBox.of(location, x, y, z), filter);
	}

	@Override
	public @NotNull Collection<Entity> getNearbyEntities(BoundingBox boundingBox)
	{
		return getNearbyEntities(boundingBox, null);
	}

	@Override
	public @NotNull Collection<Entity> getNearbyEntities(BoundingBox boundingBox, Predicate<? super Entity> filter)
	{
		return getEntities().stream().filter(entity -> filter == null || filter.test(entity))
				.filter(entity -> boundingBox.contains(entity.getLocation().toVector())).collect(Collectors.toSet());
	}

	@Override
	public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance,
			Predicate<? super Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize,
			Predicate<? super Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BiomeSearchResult locateNearestBiome(@NotNull Location origin, int radius, int horizontalInterval,
			int verticalInterval, @NotNull Biome... biomes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BiomeSearchResult locateNearestBiome(@NotNull Location origin, int radius,
			@NotNull Biome... biomes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RayTraceResult rayTrace(@NotNull Position start, @NotNull Vector direction, double maxDistance,
			@NotNull FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, double raySize,
			@Nullable Predicate<? super Entity> filter, @Nullable Predicate<? super Block> canCollide)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(@NotNull Position start, @NotNull Vector direction,
			double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks,
			@Nullable Predicate<? super Block> canCollide)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RayTraceResult rayTraceEntities(@NotNull Position start, @NotNull Vector direction,
			double maxDistance, double raySize, @Nullable Predicate<? super Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance,
			FluidCollisionMode fluidCollisionMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance,
			FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance,
			FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, double raySize,
			Predicate<? super Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, T data, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra, T data, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.19")
	public Location locateNearestStructure(Location origin, StructureType structureType, int radius,
			boolean findUnexplored)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable StructureSearchResult locateNearestStructure(@NotNull Location origin,
			org.bukkit.generator.structure.@NotNull StructureType structureType, int radius, boolean findUnexplored)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable StructureSearchResult locateNearestStructure(@NotNull Location origin,
			@NotNull Structure structure, int radius, boolean findUnexplored)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location locateNearestBiome(@NotNull Location origin, @NotNull Biome biome, int radius)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location locateNearestBiome(@NotNull Location origin, @NotNull Biome biome, int radius, int step)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Entity> @NotNull T addEntity(@NotNull T t)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.21")
	public boolean isUltrawarm()
	{
		return this.isUltraWarm();
	}

	@Override
	public double getCoordinateScale()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.21")
	public boolean hasSkylight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.21")
	public boolean hasBedrockCeiling()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.21")
	public boolean doesBedWork()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.21")
	public boolean doesRespawnAnchorWork()
	{
		return isRespawnAnchorWorks();
	}

	@Override
	public boolean isFixedTime()
	{
		return this.environment == Environment.THE_END || this.environment == Environment.NETHER;
	}

	@Override
	public @NotNull Collection<Material> getInfiniburn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendGameEvent(@Nullable Entity sourceEntity, @NotNull GameEvent gameEvent, @NotNull Vector position)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isChunkForceLoaded(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setChunkForceLoaded(int x, int z, boolean forced)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Chunk> getForceLoadedChunks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPluginChunkTicket(int x, int z, Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removePluginChunkTicket(int x, int z, Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removePluginChunkTickets(Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Plugin> getPluginChunkTickets(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Map<Plugin, Collection<Chunk>> getPluginChunkTickets()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Chunk> getIntersectingChunks(@NotNull BoundingBox box)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends AbstractArrow> @NotNull T spawnArrow(Location location, Vector direction, float speed,
			float spread, Class<T> clazz)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Raid locateNearestRaid(Location location, int radius)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Raid getRaid(int id)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<Raid> getRaids()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks,
			Entity source)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks, Entity source)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHighestBlockYAt(int x, int z, @NotNull HeightMap heightMap)
	{
		Preconditions.checkNotNull(heightMap);
		return getHighestBlockYAt(x, z, (block) -> isOfHeightMap(block, heightMap));
	}

	private int getHighestBlockYAt(int x, int z, Function<Block, Boolean> filter)
	{
		for (int y = this.getMaxHeight() - 1; y >= this.getMinHeight(); y--)
		{
			Block block = this.getBlockAt(x, y, z);
			if (filter.apply(block))
			{
				return block.getY();
			}
		}
		return this.getMinHeight() - 1;
	}

	private boolean isOfHeightMap(Block block, HeightMap heightMap)
	{
		return switch (heightMap)
		{
		case MOTION_BLOCKING ->
		{
			boolean isWaterLogged = block.getBlockData() instanceof Waterlogged waterlogged
					&& waterlogged.isWaterlogged();
			yield block.isSolid() || isWaterLogged || block.isLiquid();
		}
		case MOTION_BLOCKING_NO_LEAVES ->
		{
			boolean isWaterLogged = block.getBlockData() instanceof Waterlogged waterlogged
					&& waterlogged.isWaterlogged();
			yield (block.isSolid() || isWaterLogged || block.isLiquid()) && !Tag.LEAVES.isTagged(block.getType());
		}
		case OCEAN_FLOOR -> block.isSolid();
		case OCEAN_FLOOR_WG -> (block.getY() <= this.grassHeight && this.defaultBlock.isSolid()) || block.getY() == 0;
		case WORLD_SURFACE_WG -> (block.getY() <= this.grassHeight && !this.defaultBlock.isAir()) || block.getY() == 0;
		case WORLD_SURFACE -> !block.getType().isAir();
		};
	}

	@Override
	public int getHighestBlockYAt(Location location, @NotNull HeightMap heightMap)
	{
		return getHighestBlockYAt(location.getBlockX(), location.getBlockZ(), heightMap);
	}

	@Override
	public @NotNull Block getHighestBlockAt(int x, int z, @NotNull HeightMap heightMap)
	{
		return getBlockAt(x, getHighestBlockYAt(x, z), z);
	}

	@Override
	public @NotNull Block getHighestBlockAt(Location location, @NotNull HeightMap heightMap)
	{
		return getBlockAt(location.getBlockX(), getHighestBlockYAt(location), location.getBlockZ());
	}

	@NotNull
	@Override
	public Biome getBiome(@NotNull Location location)
	{
		return this.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	@Override
	public @NotNull Biome getBiome(int x, int y, int z)
	{
		return biomes.getOrDefault(new Coordinate(x, y, z), defaultBiome);
	}

	@Override
	public @NotNull Biome getComputedBiome(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBiome(@NotNull Location location, @NotNull Biome biome)
	{
		this.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome);
	}

	@Override
	public void setBiome(int x, int y, int z, @NotNull Biome bio)
	{
		Preconditions.checkArgument(bio != Biome.CUSTOM, "Cannot set the biome to %s", bio);
		biomes.put(new Coordinate(x, y, z), bio);
	}

	/**
	 * Gets a map of what biome is at each coordinate.
	 *
	 * @return A clone of the internal biome map.
	 */
	protected @NotNull Map<Coordinate, Biome> getBiomeMap()
	{
		return new HashMap<>(biomes);
	}

	/**
	 * @return The default biome of this world.
	 */
	public Biome getDefaultBiome()
	{
		return defaultBiome;
	}

	@NotNull
	@Override
	public BlockState getBlockState(@NotNull Location location)
	{
		Block block = this.getBlockAt(location);
		return block.getState();
	}

	@NotNull
	@Override
	public BlockState getBlockState(int x, int y, int z)
	{
		Block block = this.getBlockAt(x, y, z);
		return block.getState();
	}

	@Override
	public @NotNull FluidData getFluidData(int i, int i1, int i2)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public BlockData getBlockData(@NotNull Location location)
	{
		Block block = this.getBlockAt(location);
		return block.getBlockData();
	}

	@NotNull
	@Override
	public BlockData getBlockData(int x, int y, int z)
	{
		Block block = this.getBlockAt(x, y, z);
		return block.getBlockData();
	}

	@NotNull
	@Override
	public Material getType(@NotNull Location location)
	{
		Block block = this.getBlockAt(location);
		return block.getType();
	}

	@NotNull
	@Override
	public Material getType(int x, int y, int z)
	{
		Block block = this.getBlockAt(x, y, z);
		return block.getType();
	}

	@Override
	public void setBlockData(@NotNull Location location, @NotNull BlockData blockData)
	{
		Block block = this.getBlockAt(location);
		block.setBlockData(blockData);
	}

	@Override
	public void setBlockData(int x, int y, int z, @NotNull BlockData blockData)
	{
		Block block = this.getBlockAt(x, y, z);
		block.setBlockData(blockData);
	}

	@Override
	public void setType(@NotNull Location location, @NotNull Material material)
	{
		Block block = this.getBlockAt(location);
		block.setType(material);
	}

	@Override
	public void setType(int x, int y, int z, @NotNull Material material)
	{
		Block block = this.getBlockAt(x, y, z);
		block.setType(material);
	}

	@Override
	public boolean generateTree(@NotNull Location location, @NotNull Random random, @NotNull TreeType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean generateTree(@NotNull Location location, @NotNull Random random, @NotNull TreeType type,
			@Nullable Consumer<? super BlockState> stateConsumer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getTemperature(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getHumidity(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getLogicalHeight()
	{
		return switch (environment)
		{
		case NETHER, THE_END -> 256;
		case NORMAL -> 384;
		case CUSTOM -> throw new UnimplementedOperationException("We don't have support for Datapacks");
		};
	}

	@Override
	public boolean isNatural()
	{
		return switch (environment)
		{
		case THE_END, NETHER -> false;
		default -> true;
		};
	}

	@Override
	public boolean isBedWorks()
	{
		return switch (environment)
		{
		case THE_END, NETHER -> false;
		default -> true;
		};
	}

	@Override
	public boolean hasSkyLight()
	{
		return switch (environment)
		{
		case THE_END, NETHER -> false;
		default -> true;
		};
	}

	@Override
	public boolean hasCeiling()
	{
		return environment == Environment.NETHER;
	}

	@Override
	public boolean isPiglinSafe()
	{
		return environment == Environment.NETHER;
	}

	@Override
	public boolean isRespawnAnchorWorks()
	{
		return environment == Environment.NETHER;
	}

	@Override
	public boolean hasRaids()
	{
		return environment != Environment.NETHER;
	}

	@Override
	public boolean isUltraWarm()
	{
		return environment == Environment.NETHER;
	}

	@Override
	public boolean isHardcore()
	{
		return hardcore;
	}

	@Override
	public void setHardcore(boolean hardcore)
	{
		this.hardcore = hardcore;
	}

	@Override
	@Deprecated(since = "1.18")
	public long getTicksPerWaterSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTicksPerWaterSpawns(int ticksPerWaterSpawns)
	{
		this.setTicksPerSpawns(SpawnCategory.WATER_ANIMAL, ticksPerWaterSpawns);
	}

	@Override
	@Deprecated(since = "1.18")
	public long getTicksPerAmbientSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.AMBIENT);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns)
	{
		this.setTicksPerSpawns(SpawnCategory.AMBIENT, ticksPerAmbientSpawns);
	}

	@Override
	public DragonBattle getEnderDragonBattle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<FeatureFlag> getFeatureFlags()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setSpawnLocation(int x, int y, int z, float angle)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.18")
	public long getTicksPerWaterAmbientSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTicksPerWaterAmbientSpawns(int ticksPerAmbientSpawns)
	{
		this.setTicksPerSpawns(SpawnCategory.WATER_AMBIENT, ticksPerAmbientSpawns);
	}

	@Override
	@Deprecated(since = "1.18")
	public long getTicksPerWaterUndergroundCreatureSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTicksPerWaterUndergroundCreatureSpawns(int ticksPerWaterUndergroundCreatureSpawns)
	{
		this.setTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE, ticksPerWaterUndergroundCreatureSpawns);
	}

	@Override
	@Deprecated(since = "1.18")
	public int getWaterAmbientSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setWaterAmbientSpawnLimit(int limit)
	{
		this.setSpawnLimit(SpawnCategory.WATER_AMBIENT, limit);
	}

	@Override
	public int getViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setViewDistance(int viewDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSimulationDistance(int simulationDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.18")
	public int getNoTickViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.18")
	public void setNoTickViewDistance(int viewDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSendViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSendViewDistance(int viewDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<GeneratedStructure> getStructures(int x, int z)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<GeneratedStructure> getStructures(int x, int z, @NotNull Structure structure)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getGameTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSimulationDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return this.persistentDataContainer;
	}

	@Override
	public long getTicksPerSpawns(@NotNull SpawnCategory spawnCategory)
	{
		Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
		Preconditions.checkArgument(spawnCategory != null && spawnCategory != SpawnCategory.MISC,
				"SpawnCategory.%s are not supported", spawnCategory);

		return this.ticksPerSpawn.getLong(spawnCategory);
	}

	@Override
	public void setTicksPerSpawns(@NotNull SpawnCategory spawnCategory, int ticksPerCategorySpawn)
	{
		Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
		Preconditions.checkArgument(spawnCategory != null && spawnCategory != SpawnCategory.MISC,
				"SpawnCategory.%s are not supported", spawnCategory);

		this.ticksPerSpawn.put(spawnCategory, ticksPerCategorySpawn);
	}

	@Override
	public int getSpawnLimit(@NotNull SpawnCategory spawnCategory)
	{
		Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
		Preconditions.checkArgument(spawnCategory != SpawnCategory.MISC, "SpawnCategory.%s are not supported",
				spawnCategory);

		return this.getSpawnLimitUnsafe(spawnCategory);
	}

	public final int getSpawnLimitUnsafe(final SpawnCategory spawnCategory)
	{
		int limit = this.spawnLimits.getOrDefault(spawnCategory, -1);
		if (limit < 0)
		{
			limit = this.server.getSpawnLimit(spawnCategory);
		}
		return limit;
	}

	@Override
	public void setSpawnLimit(@NotNull SpawnCategory spawnCategory, int limit)
	{
		Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
		Preconditions.checkArgument(spawnCategory != SpawnCategory.MISC, "SpawnCategory.%s are not supported",
				spawnCategory);

		this.spawnLimits.put(spawnCategory, limit);
	}

	@Override
	public void playNote(@NotNull Location loc, @NotNull Instrument instrument, @NotNull Note note)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull String sound, @NotNull SoundCategory category, float volume,
			float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory category, float volume,
			float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category,
			float volume, float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category,
			float volume, float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
