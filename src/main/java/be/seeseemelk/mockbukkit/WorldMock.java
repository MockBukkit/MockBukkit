package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.StructureType;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A mock world object. Note that it is made to be as simple as possible. It is
 * by no means an efficient implementation.
 */
@SuppressWarnings("deprecation")
public class WorldMock implements World
{
	private Map<Coordinate, BlockMock> blocks = new HashMap<>();
	private Material defaultBlock;
	private int height;
	private int grassHeight;
	private String name = "World";
	private UUID uuid = UUID.randomUUID();
	private Location spawnLocation;
	private int weatherDuration = 0;
	private int thunderDuration = 0;
	private boolean storming = false;
	private Map<GameRule<?>, Object> gameRules = new HashMap<>();
	
	/**
	 * Creates a new mock world.
	 * 
	 * @param defaultBlock The block that is spawned at locations 1 to
	 *            {@code grassHeight}
	 * @param height The height of the world.
	 * @param grassHeight The last {@code y} at which {@code defaultBlock} will
	 *            spawn.
	 */
	public WorldMock(Material defaultBlock, int height, int grassHeight)
	{
		this.defaultBlock = defaultBlock;
		this.height = height;
		this.grassHeight = grassHeight;
		
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
	 * Creates a new mock world with a height of 128.
	 * 
	 * @param defaultBlock The block that is spawned at locations 1 to
	 *            {@code grassHeight}
	 * @param grassHeight The last {@code y} at which {@code defaultBlock} will
	 *            spawn.
	 */
	public WorldMock(Material defaultBlock, int grassHeight)
	{
		this(defaultBlock, 128, grassHeight);
	}
	
	/**
	 * Creates a new mock world with a height of 128 and will spawn grass until a
	 * {@code y} of 4.
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
		if (c.y >= height)
			throw new ArrayIndexOutOfBoundsException("Y larger than height");
		else if (c.y < 0)
			throw new ArrayIndexOutOfBoundsException("Y smaller than 0");
		
		Location location = new Location(this, c.x, c.y, c.z);
		BlockMock block;
		if (c.y == 0)
			block = new BlockMock(Material.BEDROCK, location);
		else if (c.y <= grassHeight)
			block = new BlockMock(defaultBlock, location);
		else
			block = new BlockMock(location);
		
		blocks.put(c, block);
		return block;
	}
	
	@Override
	public BlockMock getBlockAt(int x, int y, int z)
	{
		Coordinate coordinate = new Coordinate(x, y, z);
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
	public BlockMock getBlockAt(Location location)
	{
		return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	@Override
	public String getName()
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
	public UUID getUID()
	{
		return uuid;
	}
	
	@Override
	public Location getSpawnLocation()
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
	public List<Entity> getEntities()
	{
		// MockBukkit.assertMocking();
		List<Entity> entities = new ArrayList<>();
		
		Collection<? extends PlayerMock> players = MockBukkit.getMock().getOnlinePlayers();
		players.stream().filter(player -> player.getWorld() == this).collect(Collectors.toCollection(() -> entities));
		
		return entities;
	}
	
	@Override
	public ChunkMock getChunkAt(int x, int z)
	{
		ChunkMock chunk = new ChunkMock(this, x, z);
		return chunk;
	}
	
	@Override
	public void sendPluginMessage(Plugin source, String channel, byte[] message)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<String> getListeningPluginChannels()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public List<MetadataValue> getMetadata(String metadataKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean hasMetadata(String metadataKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getHighestBlockYAt(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getHighestBlockYAt(Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Block getHighestBlockAt(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Block getHighestBlockAt(Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Chunk getChunkAt(Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Chunk getChunkAt(Block block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void loadChunk(Chunk chunk)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isChunkLoaded(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isChunkInUse(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void loadChunk(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean loadChunk(int x, int z, boolean generate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadChunk(Chunk chunk)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadChunk(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadChunk(int x, int z, boolean save)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	@Deprecated
	public boolean unloadChunk(int x, int z, boolean save, boolean safe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadChunkRequest(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadChunkRequest(int x, int z, boolean safe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean regenerateChunk(int x, int z)
	{
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
	public Item dropItem(Location location, ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Item dropItemNaturally(Location location, ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Arrow spawnArrow(Location location, Vector direction, float speed, float spread)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public <T extends Arrow> T spawnArrow(Location location, Vector direction, float speed, float spread,
			Class<T> clazz)
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
	public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Entity spawnEntity(Location loc, EntityType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public List<Player> getPlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public long getTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setTime(long time)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public long getFullTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setFullTime(long time)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public Environment getEnvironment()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	
	@Override
	public void save()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public List<BlockPopulator> getPopulators()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public FallingBlock spawnFallingBlock(Location location, MaterialData data) throws IllegalArgumentException
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
	public void playEffect(Location location, Effect effect, int data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void playEffect(Location location, Effect effect, int data, int radius)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public <T> void playEffect(Location location, Effect effect, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public <T> void playEffect(Location location, Effect effect, T data, int radius)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public Biome getBiome(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setBiome(int x, int z, Biome bio)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public double getTemperature(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public double getHumidity(int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getMaxHeight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getSeaLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public void setDifficulty(Difficulty difficulty)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Difficulty getDifficulty()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public File getWorldFolder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public WorldType getWorldType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean canGenerateStructures()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public long getTicksPerAnimalSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public long getTicksPerMonsterSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getMonsterSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setMonsterSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getAnimalSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setAnimalSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getWaterAnimalSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setWaterAnimalSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getAmbientSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setAmbientSpawnLimit(int limit)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void playSound(Location location, String sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String[] getGameRules()
	{
		return gameRules.values().stream()
				.map(Object::toString)
				.collect(Collectors.toList())
				.toArray(new String[0]);
	}
	
	@Override
	public String getGameRuleValue(String rule)
	{
		if (rule == null)
			return null;
		GameRule<?> gameRule = GameRule.getByName(rule);
		if (gameRule == null)
			return null;
		return getGameRuleValue(gameRule).toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean setGameRuleValue(String rule, String value)
	{
		if (rule == null)
			return false;
		GameRule<?> gameRule = GameRule.getByName(rule);
		if (gameRule == null)
			return false;
		if (gameRule.getType().equals(Boolean.TYPE) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")))
			return setGameRule((GameRule<Boolean>) gameRule, value.equalsIgnoreCase("true"));
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
			return false;
	}
	
	@Override
	public boolean isGameRule(String rule)
	{
		return rule != null && GameRule.getByName(rule) != null; 
	}

    @Override
    public Spigot spigot() {
        throw new UnimplementedOperationException();
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
	public FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getGameRuleValue(GameRule<T> rule)
	{
		return (T) gameRules.get(rule);
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
	
}
