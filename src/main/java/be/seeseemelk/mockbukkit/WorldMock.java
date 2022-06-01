package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.ExperienceOrbMock;
import be.seeseemelk.mockbukkit.entity.FireworkMock;
import be.seeseemelk.mockbukkit.entity.ItemEntityMock;
import be.seeseemelk.mockbukkit.entity.MobMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import com.destroystokyo.paper.HeightmapType;
import io.papermc.paper.world.MoonPhase;
import org.apache.commons.lang.Validate;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameEvent;
import org.bukkit.GameRule;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.StructureType;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A mock world object. Note that it is made to be as simple as possible. It is by no means an efficient implementation.
 */
public class WorldMock implements World
{

	private static final int SEA_LEVEL = 63;

	private final Map<Coordinate, BlockMock> blocks = new HashMap<>();
	private final Map<GameRule<?>, Object> gameRules = new HashMap<>();
	private final MetadataTable metadataTable = new MetadataTable();
	private final Map<ChunkCoordinate, ChunkMock> loadedChunks = new HashMap<>();
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();
	private final ServerMock server;
	private final Material defaultBlock;
	private final int grassHeight;
	private final int minHeight;
	private final int maxHeight;
	private final UUID uuid = UUID.randomUUID();

	private Environment environment = Environment.NORMAL;
	private String name = "World";
	private Location spawnLocation;
	private long fullTime = 0;
	private int weatherDuration = 0;
	private int thunderDuration = 0;
	private boolean storming = false;
	private long seed = 0;
	private WorldType worldType = WorldType.NORMAL;
	private Difficulty difficulty = Difficulty.NORMAL;

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
		this.defaultBlock = defaultBlock;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.grassHeight = grassHeight;
		this.server = MockBukkit.getMock();

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
		this(Material.GRASS, 4);
	}

	/**
	 * Makes sure that a certain block exists on the coordinate. Returns that block.
	 *
	 * @param c Creates a block on the given coordinate.
	 * @return A newly created block at that location.
	 */
	public BlockMock createBlock(Coordinate c)
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getPlayerCount()
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
	public @NotNull BlockMock getBlockAt(int x, int y, int z)
	{
		return getBlockAt(new Coordinate(x, y, z));
	}

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
	public @NotNull BlockMock getBlockAt(Location location)
	{
		return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	@Override
	@Deprecated
	public @NotNull Block getBlockAtKey(long key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public boolean setSpawnLocation(Location location)
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
		// MockBukkit.assertMocking();
		List<Entity> entities = new ArrayList<>();

		Collection<? extends EntityMock> serverEntities = MockBukkit.getMock().getEntities();
		serverEntities.stream().filter(entity -> entity.getWorld() == this)
		.collect(Collectors.toCollection(() -> entities));
		return entities;
	}

	@Override
	public @NotNull ChunkMock getChunkAt(int x, int z)
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
			chunk = new ChunkMock(this, coordinate.getX(), coordinate.getZ());
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

	@Override
	public int getHighestBlockYAt(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHighestBlockYAt(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Block getHighestBlockAt(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Block getHighestBlockAt(Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getHighestBlockYAt(int x, int z, @NotNull HeightmapType heightmap) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Chunk getChunkAt(Location location)
	{
		return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
	}

	@Override
	public Chunk getChunkAt(Block block)
	{
		return getChunkAt(block.getLocation());
	}

	@Override
	public boolean isChunkLoaded(Chunk chunk)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Chunk[] getLoadedChunks()
	{
		return loadedChunks.values().toArray(new Chunk[0]);
	}

	@Override
	public void loadChunk(Chunk chunk)
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
	@Deprecated
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean unloadChunk(Chunk chunk)
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean unloadChunkRequest(int x, int z)
	{
		AsyncCatcher.catchOp("chunk unload");
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean regenerateChunk(int x, int z)
	{
		AsyncCatcher.catchOp("chunk regenerate");
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean refreshChunk(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemEntityMock dropItem(@NotNull Location loc, @NotNull ItemStack item, @Nullable Consumer<Item> function)
	{
		Validate.notNull(loc, "The provided location must not be null.");
		Validate.notNull(item, "Cannot drop items that are null.");
		Validate.isTrue(!item.getType().isAir(), "Cannot drop air.");

		ItemEntityMock entity = new ItemEntityMock(server, UUID.randomUUID(), item);
		entity.setLocation(loc);

		if (function != null)
		{
			function.accept(entity);
		}

		server.registerEntity(entity);
		callSpawnEvent(entity, null);

		return entity;
	}

	@Override
	public @NotNull ItemEntityMock dropItem(@NotNull Location loc, @NotNull ItemStack item)
	{
		return dropItem(loc, item, null);
	}

	@Override
	public @NotNull ItemEntityMock dropItemNaturally(@NotNull Location location, @NotNull ItemStack item, @Nullable Consumer<Item> function)
	{
		Validate.notNull(location, "The provided location must not be null.");

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
	public @NotNull ItemEntityMock dropItemNaturally(@NotNull Location loc, @NotNull ItemStack item)
	{
		return dropItemNaturally(loc, item, null);
	}

	@Override
	public Arrow spawnArrow(Location location, Vector direction, float speed, float spread)
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
	@Deprecated
	public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean generateTree(Location location, Random random, TreeType type, Predicate<BlockState> statePredicate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz) throws IllegalArgumentException
	{
		return this.spawn(location, clazz, null, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}

	@Override
	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz, Consumer<T> function) throws IllegalArgumentException
	{
		return this.spawn(location, clazz, function, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}

	@Override
	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz, boolean randomizeData, Consumer<T> function) throws IllegalArgumentException
	{
		return this.spawn(location, clazz, function, CreatureSpawnEvent.SpawnReason.CUSTOM, randomizeData);
	}

	public <T extends Entity> @NotNull T spawn(@NotNull Location location, @NotNull Class<T> clazz, Consumer<T> function, CreatureSpawnEvent.@NotNull SpawnReason reason) throws IllegalArgumentException
	{
		return this.spawn(location, clazz, function, reason, true);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function, CreatureSpawnEvent.SpawnReason reason, boolean randomizeData) throws IllegalArgumentException
	{
		if (location == null || clazz == null)
		{
			throw new IllegalArgumentException("Location or entity class cannot be null");
		}

		EntityMock entity = this.mockEntity(location, clazz, randomizeData);

		entity.setLocation(location);

		if (entity instanceof MobMock mob)
		{
			mob.finalizeSpawn();
		}

		// CraftBukkit doesn't check this when spawning, it's done when the entity is ticking so
		// it ends up being spawned for one tick before being removed. We don't have a great way
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
		callSpawnEvent(entity, reason);

		return (T) entity;
	}

	@Override
	public @NotNull Entity spawnEntity(@NotNull Location loc, EntityType type)
	{
		return spawn(loc, type.getEntityClass());
	}

	@NotNull
	@Override
	public Entity spawnEntity(@NotNull Location loc, @NotNull EntityType type, boolean randomizeData)
	{
		return this.spawn(loc, type.getEntityClass(), randomizeData, null);
	}

	private <T extends Entity> EntityMock mockEntity(@NotNull Location location, @NotNull Class<T> clazz, boolean randomizeData)
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
			BlockFace[] faces = (ItemFrame.class.isAssignableFrom(clazz))
					? new BlockFace[]{ BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN }
					: new BlockFace[]{ BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
			for (BlockFace face : faces)
			{
				Block block = this.getBlockAt(location.add(face.getModX(), face.getModY(), face.getModZ()));
				if (!block.getType().isSolid() && (block.getType() != Material.REPEATER && block.getType() != Material.COMPARATOR))
					continue;

				boolean taken = false;

				// TODO: Check if the entity's bounding box collides with any other hanging entities.

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
		else if (clazz == Player.class)
		{
			throw new IllegalArgumentException("Player Entities cannot be spawned, use ServerMock#addPlayer(...)");
		}
		else if (clazz == Zombie.class)
		{
			return new ZombieMock(server, UUID.randomUUID());
		}
		throw new UnimplementedOperationException();
	}

	private void callSpawnEvent(EntityMock entity, CreatureSpawnEvent.SpawnReason reason)
	{

		boolean canceled; // Here for future implementation (see below)

		if (entity instanceof LivingEntity living && !(entity instanceof Player)) {
			/* getAllowAnimals and getAllowMonsters aren't implemented yet
			boolean isAnimal = entity instanceof Animals || entity instanceof WaterMob || entity instanceof Golem;
			boolean isMonster = entity instanceof Monster || entity instanceof Ghast || entity instanceof Slime;
			if (reason != CreatureSpawnEvent.SpawnReason.CUSTOM)
			{
				if (isAnimal && !getAllowAnimals() || isMonster && !getAllowMonsters())
				{
					entity.remove();
					yield false;
				}
			}
			*/

			canceled = new CreatureSpawnEvent(living, reason).callEvent();
		} else if (entity instanceof Item item) {
			canceled = new ItemSpawnEvent(item).callEvent();
		} else if (entity instanceof Player player) {
			canceled = true; // Shouldn't ever be called here but just for parody.
		} else if (entity instanceof Projectile projectile) {
			canceled = new ProjectileLaunchEvent(entity).callEvent();
		} else {
			canceled = new EntitySpawnEvent(entity).callEvent();
		}

		/* EntityMock#getPassengers() and #getVehicle() isn't implemented
		if (canceled || entity.isValid())
		{
			Entity vehicle = entity.getVehicle();
			if (vehicle != null)
			{
				vehicle.remove();
			}
			for (Entity passenger : entity.getPassengers())
			{
				passenger.remove();
			}
			entity.remove();
		}
		*/
	}

	@Override
	public LightningStrike strikeLightning(Location loc)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LightningStrike strikeLightningEffect(Location loc)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
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
	public List<LivingEntity> getLivingEntities()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> cls)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Collection<Entity> getEntitiesByClasses(Class<?>... classes)
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
	public List<Player> getPlayers()
	{
		return Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld() == this).collect(Collectors.toList());
	}

	@Override
	public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Entity getEntity(@NotNull UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		return storming;
	}

	@Override
	public void setStorm(boolean hasStorm)
	{
		storming = hasStorm;
	}

	@Override
	public int getWeatherDuration()
	{
		return weatherDuration;
	}

	@Override
	public void setWeatherDuration(int duration)
	{
		weatherDuration = duration;
	}

	@Override
	public boolean isThundering()
	{
		return thunderDuration > 0;
	}

	@Override
	public void setThundering(boolean thundering)
	{
		thunderDuration = thundering ? 600 : 0;
	}

	@Override
	public int getThunderDuration()
	{
		return thunderDuration;
	}

	@Override
	public void setThunderDuration(int duration)
	{
		thunderDuration = duration;
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
	public boolean createExplosion(@Nullable Entity source, @NotNull Location loc, float power, boolean setFire, boolean breakBlocks)
	{
		return false;
	}

	@Override
	public Environment getEnvironment()
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPVP(boolean pvp)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void save()
	{
		AsyncCatcher.catchOp("world save");
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<BlockPopulator> getPopulators()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@SuppressWarnings("deprecation")
	@Override
	public FallingBlock spawnFallingBlock(Location location, org.bukkit.material.MaterialData data) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public FallingBlock spawnFallingBlock(Location location, Material material, byte data)
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
		Validate.notNull(location, "Location cannot be null");
		Validate.notNull(effect, "Effect cannot be null");
		Validate.notNull(location.getWorld(), "World cannot be null");
	}

	@Override
	public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, T data)
	{
		this.playEffect(location, effect, data, 64);
	}

	@Override
	public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, T data, int radius)
	{
		if (data != null)
		{
			Validate.isTrue(effect.getData() != null && effect.getData().isAssignableFrom(data.getClass()), "Wrong kind of data for this effect!");
		}
		else
		{
			// Special case: the axis is optional for ELECTRIC_SPARK
			Validate.isTrue(effect.getData() == null || effect == Effect.ELECTRIC_SPARK, "Wrong kind of data for this effect!");
		}
	}

	@Override
	public @NotNull ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain)
	{
		return new ChunkSnapshotMock(x, z, getMinHeight(), getMaxHeight(), getName(), getFullTime(), Map.of());
	}

	@Override
	public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getAllowAnimals()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getAllowMonsters()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public Biome getBiome(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setBiome(int x, int z, Biome bio)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public double getTemperature(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setKeepSpawnInMemory(boolean keepLoaded)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public File getWorldFolder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
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
	@Deprecated
	public long getTicksPerAnimalSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public long getTicksPerMonsterSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getMonsterSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setMonsterSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getAnimalSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setAnimalSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getWaterAnimalSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setWaterAnimalSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getWaterUndergroundCreatureSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	@Deprecated
	public void setWaterUndergroundCreatureSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	@Deprecated
	public int getAmbientSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setAmbientSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch)
	{
		this.playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(Location location, String sound, float volume, float pitch)
	{
		this.playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch)
	{
		for (Player player : getPlayers())
		{
			player.playSound(location, sound, category, volume, pitch);
		}
	}

	@Override
	public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch)
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
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch)
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
	public String[] getGameRules()
	{
		return gameRules.values().stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[0]);
	}

	@Override
	@Deprecated
	public String getGameRuleValue(String rule)
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
	@Deprecated
	public boolean setGameRuleValue(String rule, String value)
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
		if (gameRule.getType().equals(Boolean.TYPE)
		        && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")))
		{
			return setGameRule((GameRule<Boolean>) gameRule, value.equalsIgnoreCase("true"));
		}
		else if (gameRule.getType().equals(Integer.TYPE))
		{
			try
			{
				int intValue = Integer.parseInt(value);
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
	public boolean isGameRule(String rule)
	{
		return rule != null && GameRule.getByName(rule) != null;
	}

	@Override
	public WorldBorder getWorldBorder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public <T> void spawnParticle(@NotNull Particle particle, @Nullable List<Player> receivers, @Nullable Player source, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> T getGameRuleValue(GameRule<T> rule)
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
	public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z,
	        Predicate<Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Collection<Entity> getNearbyEntities(BoundingBox boundingBox)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Collection<Entity> getNearbyEntities(BoundingBox boundingBox, Predicate<Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	                                       Predicate<Entity> filter)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize,
	                                       Predicate<Entity> filter)
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
	                               Predicate<Entity> filter)
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
	public Location locateNearestStructure(Location origin, StructureType structureType, int radius,
	                                       boolean findUnexplored)
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
	@Deprecated
	public boolean isUltrawarm()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getCoordinateScale()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean hasSkylight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean hasBedrockCeiling()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean doesBedWork()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean doesRespawnAnchorWork()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFixedTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public Collection<Chunk> getForceLoadedChunks()
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
	public Collection<Plugin> getPluginChunkTickets(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Map<Plugin, Collection<Chunk>> getPluginChunkTickets()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends AbstractArrow> T spawnArrow(Location location, Vector direction, float speed, float spread,
	        Class<T> clazz)
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
	public List<Raid> getRaids()
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
	public int getHighestBlockYAt(int x, int z, HeightMap heightMap)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHighestBlockYAt(Location location, HeightMap heightMap)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Block getHighestBlockAt(int x, int z, HeightMap heightMap)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Block getHighestBlockAt(Location location, HeightMap heightMap)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public Biome getBiome(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public Biome getBiome(int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setBiome(int x, int y, int z, Biome bio)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public boolean generateTree(@NotNull Location location, @NotNull Random random, @NotNull TreeType type, @Nullable Consumer<BlockState> stateConsumer)
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isNatural()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBedWorks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasSkyLight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCeiling()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPiglinSafe()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isRespawnAnchorWorks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasRaids()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isUltraWarm()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isHardcore()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHardcore(boolean hardcore)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public long getTicksPerWaterSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setTicksPerWaterSpawns(int ticksPerWaterSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public long getTicksPerAmbientSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public DragonBattle getEnderDragonBattle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setSpawnLocation(int x, int y, int z, float angle)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public long getTicksPerWaterAmbientSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setTicksPerWaterAmbientSpawns(int ticksPerAmbientSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public long getTicksPerWaterUndergroundCreatureSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	@Deprecated
	public void setTicksPerWaterUndergroundCreatureSpawns(int ticksPerWaterUndergroundCreatureSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getWaterAmbientSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWaterAmbientSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	@Deprecated
	public int getNoTickViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
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
	public Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isClearWeather()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getClearWeatherDuration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setClearWeatherDuration(int duration)
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTicksPerSpawns(@NotNull SpawnCategory spawnCategory, int ticksPerCategorySpawn)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSpawnLimit(@NotNull SpawnCategory spawnCategory)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSpawnLimit(@NotNull SpawnCategory spawnCategory, int limit)
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
