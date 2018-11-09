package be.seeseemelk.mockbukkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.util.CachedServerIcon;

import com.avaje.ebean.config.ServerConfig;

import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.ItemFactoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import be.seeseemelk.mockbukkit.scoreboard.ScoreboardManagerMock;

@SuppressWarnings("deprecation")
public class ServerMock implements Server
{
	private final Logger logger;
	private final Thread mainThread;
	@SuppressWarnings("unused")
	private final MockUnsafeValues unsafe = new MockUnsafeValues();
	
	private final List<PlayerMock> players = new ArrayList<>();
	private final List<PlayerMock> offlinePlayers = new ArrayList<>();
	private final Set<EntityMock> entities = new HashSet<>();
	private final List<World> worlds = new ArrayList<>();
	private List<Recipe> recipes = new LinkedList<>();
	private final ItemFactory factory = new ItemFactoryMock();
	private final PlayerMockFactory playerFactory = new PlayerMockFactory(this);
	private final PluginManagerMock pluginManager = new PluginManagerMock(this);
	private final ScoreboardManagerMock scoreboardManager = new ScoreboardManagerMock();
	private ConsoleCommandSender consoleSender;
	private BukkitSchedulerMock scheduler = new BukkitSchedulerMock();
	private PlayerList playerList = new PlayerList();
	private GameMode defaultGameMode = GameMode.SURVIVAL;
	
	public ServerMock()
	{
		mainThread = Thread.currentThread();
		logger = Logger.getLogger("ServerMock");
		try
		{
			InputStream stream = ClassLoader.getSystemResourceAsStream("logger.properties");
			LogManager.getLogManager().readConfiguration(stream);
		}
		catch (IOException e)
		{
			logger.warning("Could not load file logger.properties");
		}
		logger.setLevel(Level.ALL);
	}
	
	/**
	 * Checks if we are on the main thread. The main thread is the thread used to
	 * create this instance of the mock server.
	 * 
	 * @return {@code true} if we are on the main thread, {@code false} if we are
	 *         running on a different thread.
	 */
	public boolean isOnMainThread()
	{
		return mainThread.equals(Thread.currentThread());
	}
	
	/**
	 * Checks if we are running a method on the main thread. If not, a
	 * `ThreadAccessException` is thrown.
	 */
	public void assertMainThread() throws ThreadAccessException
	{
		if (!isOnMainThread())
			throw new ThreadAccessException("The Bukkit API was accessed from asynchronous code.");
	}
	
	/**
	 * Registers an entity so that the server can track it more easily. Should only
	 * be used internally.
	 * 
	 * @param entity The entity to register
	 */
	public void registerEntity(EntityMock entity)
	{
		assertMainThread();
		entities.add(entity);
	}
	
	/**
	 * Returns a set of entities that exist on the server instance.
	 * 
	 * @return A set of entities that exist on this server instance.
	 */
	public Set<EntityMock> getEntities()
	{
		return Collections.unmodifiableSet(entities);
	}
	
	/**
	 * Add a specific player to the set.
	 * 
	 * @param player The player to add.
	 */
	public void addPlayer(PlayerMock player)
	{
		assertMainThread();
		players.add(player);
		offlinePlayers.add(player);
		registerEntity(player);
	}
	
	/**
	 * Creates a random player and adds it.
	 */
	public PlayerMock addPlayer()
	{
		assertMainThread();
		PlayerMock player = playerFactory.createRandomPlayer();
		addPlayer(player);
		return player;
	}
	
	/**
	 * Set the numbers of mock players that are on this server. Note that it will
	 * remove all players that are already on this server.
	 * 
	 * @param num The number of players that are on this server.
	 */
	public void setPlayers(int num)
	{
		assertMainThread();
		players.clear();
		for (int i = 0; i < num; i++)
		{
			addPlayer();
		}
	}
	
	/**
	 * Set the numbers of mock offline players that are on this server. Note that
	 * even players that are online are also considered offline player because an
	 * {@link OfflinePlayer} really just refers to anyone that has at some point in
	 * time played on the server.
	 * 
	 * @param num The number of players that are on this server.
	 */
	public void setOfflinePlayers(int num)
	{
		assertMainThread();
		offlinePlayers.clear();
		offlinePlayers.addAll(players);
		
		for (int i = 0; i < num; i++)
		{
			PlayerMock player = playerFactory.createRandomOfflinePlayer();
			offlinePlayers.add(player);
			registerEntity(player);
		}
	}
	
	/**
	 * Get a specific mock player. A player's number will never change between
	 * invocations of {@link #setPlayers(int)}.
	 * 
	 * @param num The number of the player to retrieve.
	 * @return The chosen player.
	 */
	public PlayerMock getPlayer(int num)
	{
		if (num < 0 || num >= players.size())
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		else
		{
			return players.get(num);
		}
	}
	
	/**
	 * Adds a very simple super flat world with a given name.
	 * 
	 * @param name The name to give to the world.
	 * @return The {@link WorldMock} that has been created.
	 */
	public WorldMock addSimpleWorld(String name)
	{
		assertMainThread();
		WorldMock world = new WorldMock();
		world.setName(name);
		worlds.add(world);
		return world;
	}
	
	/**
	 * Executes a command as the console.
	 * 
	 * @param command The command to execute.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executeConsole(Command command, String... args)
	{
		assertMainThread();
		return execute(command, getConsoleSender(), args);
	}
	
	/**
	 * Executes a command as the console.
	 * 
	 * @param command The command to execute.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executeConsole(String command, String... args)
	{
		assertMainThread();
		return executeConsole(getPluginCommand(command), args);
	}
	
	/**
	 * Executes a command as a player.
	 * 
	 * @param command The command to execute.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executePlayer(Command command, String... args)
	{
		assertMainThread();
		if (players.size() > 0)
		{
			return execute(command, players.get(0), args);
		}
		else
		{
			throw new IllegalStateException("Need at least one player to run the command");
		}
	}
	
	/**
	 * Executes a command as a player.
	 * 
	 * @param command The command to execute.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executePlayer(String command, String... args)
	{
		assertMainThread();
		return executePlayer(getPluginCommand(command), args);
	}
	
	/**
	 * Executes a command.
	 * 
	 * @param command The command to execute.
	 * @param sender The person that executed the command.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult execute(Command command, CommandSender sender, String... args)
	{
		assertMainThread();
		if (!(sender instanceof MessageTarget))
		{
			throw new IllegalArgumentException("Only a MessageTarget can be the sender of the command");
		}
		
		boolean status = command.execute(sender, command.getName(), args);
		CommandResult result = new CommandResult(status, (MessageTarget) sender);
		return result;
	}
	
	/**
	 * Executes a command.
	 * 
	 * @param command The command to execute.
	 * @param sender The person that executed the command.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult execute(String command, CommandSender sender, String... args)
	{
		assertMainThread();
		return execute(getPluginCommand(command), sender, args);
	}
	
	@Override
	public String getName()
	{
		return "ServerMock";
	}
	
	@Override
	public String getVersion()
	{
		return "0.1.0";
	}
	
	@Override
	public String getBukkitVersion()
	{
		return "1.12.1";
	}
	
	@Override
	public Collection<? extends PlayerMock> getOnlinePlayers()
	{
		assertMainThread();
		return players;
	}
	
	@Override
	public OfflinePlayer[] getOfflinePlayers()
	{
		return offlinePlayers.toArray(new OfflinePlayer[0]);
	}
	
	@Override
	public Player getPlayer(String name)
	{
		Player player = getPlayerExact(name);
		if (player != null)
			return player;
		
		final String lowercase = name.toLowerCase(Locale.ENGLISH);
		int delta = Integer.MAX_VALUE;
		for (Player namedPlayer : players)
		{
			if (namedPlayer.getName().toLowerCase(Locale.ENGLISH).startsWith(lowercase))
			{
				int currentDelta = Math.abs(namedPlayer.getName().length() - lowercase.length());
				if (currentDelta < delta)
				{
					delta = currentDelta;
					player = namedPlayer;
				}
			}
		}
		return player;
	}
	
	@Override
	public Player getPlayerExact(String name)
	{
		assertMainThread();
		return this.players.stream().filter(playerMock -> playerMock.getName().equals(name)).findFirst().orElse(null);
	}
	
	@Override
	public List<Player> matchPlayer(String name)
	{
		assertMainThread();
		return players.stream().filter(player -> player.getName().toLowerCase(Locale.ENGLISH).startsWith(name))
				.collect(Collectors.toList());
	}
	
	@Override
	public Player getPlayer(UUID id)
	{
		assertMainThread();
		for (Player player : getOnlinePlayers())
		{
			if (id.equals(player.getUniqueId()))
			{
				return player;
			}
		}
		return null;
	}
	
	@Override
	public PluginManagerMock getPluginManager()
	{
		return pluginManager;
	}
	
	/**
	 * Checks if the label given is a possible label of the command.
	 * 
	 * @param command The command to check against.
	 * @param label The label that should be checked if it's a label for the
	 *            command.
	 * @return {@code true} if the label is a label of the command, {@code false} if
	 *         it's not.
	 */
	private boolean isLabelOfCommand(PluginCommand command, String label)
	{
		assertMainThread();
		if (label.equals(command.getName()))
		{
			return true;
		}
		for (String alias : command.getAliases())
		{
			if (label.equals(alias))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public PluginCommand getPluginCommand(String name)
	{
		assertMainThread();
		for (PluginCommand command : getPluginManager().getCommands())
		{
			if (isLabelOfCommand(command, name))
			{
				return command;
			}
		}
		return null;
	}
	
	@Override
	public Logger getLogger()
	{
		return logger;
	}
	
	@Override
	public ConsoleCommandSender getConsoleSender()
	{
		if (consoleSender == null)
		{
			consoleSender = new ConsoleCommandSenderMock();
		}
		return consoleSender;
	}
	
	public InventoryMock createInventory(InventoryHolder owner, InventoryType type, String title, int size)
	{
		assertMainThread();
		InventoryMock inventory;
		switch (type)
		{
			case PLAYER:
				inventory = new PlayerInventoryMock((HumanEntity) owner, title);
				return inventory;
			case CHEST:
				inventory = new ChestInventoryMock(owner, title, size > 0 ? size : 9 * 3);
				return inventory;
			default:
				throw new UnimplementedOperationException("Inventory type not yet supported");
		}
	}
	
	@Override
	public InventoryMock createInventory(InventoryHolder owner, InventoryType type)
	{
		return createInventory(owner, type, "Inventory");
	}
	
	@Override
	public InventoryMock createInventory(InventoryHolder owner, InventoryType type, String title)
	{
		return createInventory(owner, type, title, -1);
	}
	
	@Override
	public InventoryMock createInventory(InventoryHolder owner, int size) throws IllegalArgumentException
	{
		return createInventory(owner, size, "Inventory");
	}
	
	@Override
	public InventoryMock createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException
	{
		return createInventory(owner, InventoryType.CHEST, title, size);
	}
	
	@Override
	public ItemFactory getItemFactory()
	{
		return factory;
	}
	
	@Override
	public List<World> getWorlds()
	{
		return new ArrayList<>(worlds);
	}
	
	@Override
	public World getWorld(String name)
	{
		return worlds.stream().filter(world -> world.getName().equals(name)).findAny().orElse(null);
	}
	
	@Override
	public World getWorld(UUID uid)
	{
		return worlds.stream().filter(world -> world.getUID().equals(uid)).findAny().orElse(null);
	}
	
	@Override
	public BukkitSchedulerMock getScheduler()
	{
		return scheduler;
	}
	
	@Override
	public int getMaxPlayers()
	{
		return playerList.getMaxPlayers();
	}
	
	@Override
	public Set<String> getIPBans()
	{
		return this.playerList.getIPBans().getBanEntries().stream().map(BanEntry::getTarget)
				.collect(Collectors.toSet());
	}
	
	@Override
	public void banIP(String address)
	{
		assertMainThread();
		this.playerList.getIPBans().addBan(address, null, null, null);
	}
	
	@Override
	public void unbanIP(String address)
	{
		assertMainThread();
		this.playerList.getIPBans().pardon(address);
	}
	
	@Override
	public BanList getBanList(Type type)
	{
		switch (type)
		{
			case IP:
				return playerList.getIPBans();
			case NAME:
			default:
				return playerList.getProfileBans();
		}
	}
	
	@Override
	public Set<OfflinePlayer> getOperators()
	{
		assertMainThread();
		final Set<OfflinePlayer> players = new HashSet<>();
		players.addAll(this.offlinePlayers);
		players.addAll(this.players);
		return players.stream().filter(OfflinePlayer::isOp).collect(Collectors.toSet());
	}
	
	@Override
	public GameMode getDefaultGameMode()
	{
		return this.defaultGameMode;
	}
	
	@Override
	public void setDefaultGameMode(GameMode mode)
	{
		assertMainThread();
		this.defaultGameMode = mode;
	}
	
	@Override
	public int broadcastMessage(String message)
	{
		assertMainThread();
		for (Player player : players)
			player.sendMessage(message);
		return players.size();
	}
	
	@Override
	public boolean addRecipe(Recipe recipe)
	{
		assertMainThread();
		recipes.add(recipe);
		return true;
	}
	
	@Override
	public List<Recipe> getRecipesFor(ItemStack result)
	{
		assertMainThread();
		return recipes.stream().filter(recipe -> recipe.getResult().equals(result)).collect(Collectors.toList());
	}
	
	@Override
	public Iterator<Recipe> recipeIterator()
	{
		assertMainThread();
		return recipes.iterator();
	}
	
	@Override
	public void clearRecipes()
	{
		assertMainThread();
		recipes.clear();
	}
	
	@Override
	public boolean dispatchCommand(CommandSender sender, String commandLine) throws CommandException
	{
		assertMainThread();
		String[] commands = commandLine.split(" ");
		String commandLabel = commands[0];
		String[] args = Arrays.copyOfRange(commands, 1, commands.length);
		Command command = getPluginCommand(commandLabel);
		if (command != null)
			return command.execute(sender, commandLabel, args);
		else
			return false;
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
	public int getPort()
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
	public String getIp()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String getServerName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String getServerId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String getWorldType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean getGenerateStructures()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean getAllowEnd()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean getAllowNether()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean hasWhitelist()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setWhitelist(boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<OfflinePlayer> getWhitelistedPlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void reloadWhitelist()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String getUpdateFolder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public File getUpdateFolderFile()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public long getConnectionThrottle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getTicksPerAnimalSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getTicksPerMonsterSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ServicesManager getServicesManager()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public World createWorld(WorldCreator creator)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadWorld(String name, boolean save)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean unloadWorld(World world, boolean save)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public MapView getMap(short id)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public MapView createMap(World world)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void reload()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void savePlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void resetRecipes()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Map<String, String[]> getCommandAliases()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getSpawnRadius()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setSpawnRadius(int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean getOnlineMode()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean getAllowFlight()
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
	public void shutdown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int broadcast(String message, String permission)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public OfflinePlayer getOfflinePlayer(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public OfflinePlayer getOfflinePlayer(UUID id)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<OfflinePlayer> getBannedPlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public File getWorldContainer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Messenger getMessenger()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public HelpMap getHelpMap()
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
	public int getAnimalSpawnLimit()
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
	public int getAmbientSpawnLimit()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isPrimaryThread()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String getMotd()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public String getShutdownMessage()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public WarningState getWarningState()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ScoreboardManagerMock getScoreboardManager()
	{
		return scoreboardManager;
	}
	
	@Override
	public CachedServerIcon getServerIcon()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public CachedServerIcon loadServerIcon(File file)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public CachedServerIcon loadServerIcon(BufferedImage image)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setIdleTimeout(int threshold)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getIdleTimeout()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public ChunkData createChunkData(World world)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public UnsafeValues getUnsafe()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Player[] _INVALID_getOnlinePlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void configureDbConfig(ServerConfig config)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean useExactLoginLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
