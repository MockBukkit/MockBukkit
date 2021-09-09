package be.seeseemelk.mockbukkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.StructureType;
import org.bukkit.Tag;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.boss.BossBarMock;
import be.seeseemelk.mockbukkit.boss.KeyedBossBarMock;
import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.command.MockCommandMap;
import be.seeseemelk.mockbukkit.enchantments.EnchantmentsMock;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.help.HelpMapMock;
import be.seeseemelk.mockbukkit.inventory.BarrelInventoryMock;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.DispenserInventoryMock;
import be.seeseemelk.mockbukkit.inventory.DropperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.ItemFactoryMock;
import be.seeseemelk.mockbukkit.inventory.LecternInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.ShulkerBoxInventoryMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import be.seeseemelk.mockbukkit.potion.MockPotionEffectType;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import be.seeseemelk.mockbukkit.scoreboard.ScoreboardManagerMock;
import be.seeseemelk.mockbukkit.services.ServicesManagerMock;
import be.seeseemelk.mockbukkit.tags.TagRegistry;
import be.seeseemelk.mockbukkit.tags.TagWrapperMock;
import be.seeseemelk.mockbukkit.tags.TagsMock;
import net.md_5.bungee.api.chat.BaseComponent;

@SuppressWarnings("deprecation")
public class ServerMock extends Server.Spigot implements Server
{
	private static final String BUKKIT_VERSION = "1.16.5";
	private static final String JOIN_MESSAGE = "%s has joined the server.";
	private static final String MOTD = "A Minecraft Server";

	private final Logger logger = Logger.getLogger("ServerMock");
	private final Thread mainThread = Thread.currentThread();
	private final MockUnsafeValues unsafe = new MockUnsafeValues();
	private final Map<String, TagRegistry> materialTags = new HashMap<>();
	private final Set<EntityMock> entities = new HashSet<>();
	private final List<World> worlds = new ArrayList<>();
	private final List<Recipe> recipes = new LinkedList<>();
	private final Map<NamespacedKey, KeyedBossBarMock> bossBars = new HashMap<>();
	private final ItemFactory factory = new ItemFactoryMock();
	private final PlayerMockFactory playerFactory = new PlayerMockFactory(this);
	private final PluginManagerMock pluginManager = new PluginManagerMock(this);
	private final ScoreboardManagerMock scoreboardManager = new ScoreboardManagerMock();
	private final BukkitSchedulerMock scheduler = new BukkitSchedulerMock();
	private final ServicesManagerMock servicesManager = new ServicesManagerMock();
	private final MockPlayerList playerList = new MockPlayerList();
	private final MockCommandMap commandMap = new MockCommandMap(this);
	private final HelpMapMock helpMap = new HelpMapMock();

	private GameMode defaultGameMode = GameMode.SURVIVAL;
	private ConsoleCommandSender consoleSender;

	public ServerMock()
	{
		ServerMock.registerSerializables();

		// Register default Minecraft Potion Effect Types
		createPotionEffectTypes();
		TagsMock.loadDefaultTags(this, true);
		EnchantmentsMock.registerDefaultEnchantments();

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
	 * Checks if we are on the main thread. The main thread is the thread used to create this instance of the mock
	 * server.
	 *
	 * @return {@code true} if we are on the main thread, {@code false} if we are running on a different thread.
	 */
	public boolean isOnMainThread()
	{
		return mainThread.equals(Thread.currentThread());
	}

	/**
	 * Checks if we are running a method on the main thread. If not, a `ThreadAccessException` is thrown.
	 */
	public void assertMainThread()
	{
		if (!isOnMainThread())
		{
			throw new ThreadAccessException("The Bukkit API was accessed from asynchronous code.");
		}
	}

	/**
	 * Registers an entity so that the server can track it more easily. Should only be used internally.
	 *
	 * @param entity The entity to register
	 */
	public void registerEntity(@NotNull EntityMock entity)
	{
		assertMainThread();
		entities.add(entity);
	}

	/**
	 * Returns a set of entities that exist on the server instance.
	 *
	 * @return A set of entities that exist on this server instance.
	 */
	@NotNull
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
		playerList.addPlayer(player);
		PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player,
		        String.format(JOIN_MESSAGE, player.getDisplayName()));
		Bukkit.getPluginManager().callEvent(playerJoinEvent);

		player.setLastPlayed(getCurrentServerTime());
		registerEntity(player);
	}

	/**
	 * Creates a random player and adds it.
	 *
	 * @return The player that was added.
	 */
	public PlayerMock addPlayer()
	{
		assertMainThread();
		PlayerMock player = playerFactory.createRandomPlayer();
		addPlayer(player);
		return player;
	}

	/**
	 * Creates a player with a given name and adds it.
	 *
	 * @param name The name to give to the player.
	 * @return The added player.
	 */
	public PlayerMock addPlayer(String name)
	{
		assertMainThread();
		PlayerMock player = new PlayerMock(this, name);
		addPlayer(player);
		return player;
	}

	/**
	 * Set the numbers of mock players that are on this server. Note that it will remove all players that are already on
	 * this server.
	 *
	 * @param num The number of players that are on this server.
	 */
	public void setPlayers(int num)
	{
		assertMainThread();
		playerList.clearOnlinePlayers();

		for (int i = 0; i < num; i++)
			addPlayer();
	}

	/**
	 * Set the numbers of mock offline players that are on this server. Note that even players that are online are also
	 * considered offline player because an {@link OfflinePlayer} really just refers to anyone that has at some point in
	 * time played on the server.
	 *
	 * @param num The number of players that are on this server.
	 */
	public void setOfflinePlayers(int num)
	{
		assertMainThread();
		playerList.clearOfflinePlayers();

		for (PlayerMock player : getOnlinePlayers())
		{
			playerList.addPlayer(player);
		}

		for (int i = 0; i < num; i++)
		{
			OfflinePlayer player = playerFactory.createRandomOfflinePlayer();
			playerList.addOfflinePlayer(player);
		}
	}

	/**
	 * Get a specific mock player. A player's number will never change between invocations of {@link #setPlayers(int)}.
	 *
	 * @param num The number of the player to retrieve.
	 * @return The chosen player.
	 */
	public PlayerMock getPlayer(int num)
	{
		return playerList.getPlayer(num);
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
	 * Adds the given mocked world to this server.
	 *
	 * @param world The world to add.
	 */
	public void addWorld(WorldMock world)
	{
		assertMainThread();
		worlds.add(world);
	}

	/**
	 * Executes a command as the console.
	 *
	 * @param command The command to execute.
	 * @param args    The arguments to pass to the commands.
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
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executeConsole(String command, String... args)
	{
		assertMainThread();
		return executeConsole(getCommandMap().getCommand(command), args);
	}

	/**
	 * Executes a command as a player.
	 *
	 * @param command The command to execute.
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executePlayer(Command command, String... args)
	{
		assertMainThread();

		if (playerList.isSomeoneOnline())
			return execute(command, getPlayer(0), args);
		else
			throw new IllegalStateException("Need at least one player to run the command");
	}

	/**
	 * Executes a command as a player.
	 *
	 * @param command The command to execute.
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executePlayer(String command, String... args)
	{
		assertMainThread();
		return executePlayer(getCommandMap().getCommand(command), args);
	}

	/**
	 * Executes a command.
	 *
	 * @param command The command to execute.
	 * @param sender  The person that executed the command.
	 * @param args    The arguments to pass to the commands.
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
		return new CommandResult(status, (MessageTarget) sender);
	}

	/**
	 * Executes a command.
	 *
	 * @param command The command to execute.
	 * @param sender  The person that executed the command.
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult execute(String command, CommandSender sender, String... args)
	{
		assertMainThread();
		return execute(getCommandMap().getCommand(command), sender, args);
	}

	@Override
	public String getName()
	{
		return "ServerMock";
	}

	@Override
	public String getVersion()
	{
		return String.format("MockBukkit (MC: %s)", BUKKIT_VERSION);
	}

	@Override
	public String getBukkitVersion()
	{
		return BUKKIT_VERSION;
	}

	@Override
	public Collection<? extends PlayerMock> getOnlinePlayers()
	{
		return playerList.getOnlinePlayers();
	}

	@Override
	public OfflinePlayer[] getOfflinePlayers()
	{
		return playerList.getOfflinePlayers();
	}

	@Override
	public Player getPlayer(String name)
	{
		return playerList.getPlayer(name);
	}

	@Override
	public Player getPlayerExact(String name)
	{
		return playerList.getPlayerExact(name);
	}

	@Override
	public List<Player> matchPlayer(String name)
	{
		return playerList.matchPlayer(name);
	}

	@Override
	public Player getPlayer(UUID id)
	{
		return playerList.getPlayer(id);
	}

	@Override
	public PluginManagerMock getPluginManager()
	{
		return pluginManager;
	}

	@NotNull
	public MockCommandMap getCommandMap()
	{
		return commandMap;
	}

	@Override
	public PluginCommand getPluginCommand(String name)
	{
		assertMainThread();
		Command command = getCommandMap().getCommand(name);
		return command instanceof PluginCommand ? (PluginCommand) command : null;
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

	@NotNull
	public InventoryMock createInventory(InventoryHolder owner, InventoryType type, String title, int size)
	{
		assertMainThread();

		if (!type.isCreatable())
		{
			throw new IllegalArgumentException("Inventory Type is not creatable!");
		}

		switch (type)
		{
		case CHEST:
			return new ChestInventoryMock(owner, size > 0 ? size : 9 * 3);
		case DISPENSER:
			return new DispenserInventoryMock(owner);
		case DROPPER:
			return new DropperInventoryMock(owner);
		case PLAYER:
			if (owner instanceof HumanEntity)
			{
				return new PlayerInventoryMock((HumanEntity) owner);
			}
			else
			{
				throw new IllegalArgumentException("Cannot create a Player Inventory for: " + owner);
			}
		case ENDER_CHEST:
			return new EnderChestInventoryMock(owner);
		case HOPPER:
			return new HopperInventoryMock(owner);
		case SHULKER_BOX:
			return new ShulkerBoxInventoryMock(owner);
		case BARREL:
			return new BarrelInventoryMock(owner);
		case LECTERN:
			return new LecternInventoryMock(owner);
		case GRINDSTONE:
		// TODO: This Inventory Type needs to be implemented
		case STONECUTTER:
		// TODO: This Inventory Type needs to be implemented
		case CARTOGRAPHY:
		// TODO: This Inventory Type needs to be implemented
		case SMOKER:
		// TODO: This Inventory Type needs to be implemented
		case LOOM:
		// TODO: This Inventory Type needs to be implemented
		case BLAST_FURNACE:
		// TODO: This Inventory Type needs to be implemented
		case ANVIL:
		// TODO: This Inventory Type needs to be implemented
		case SMITHING:
		// TODO: This Inventory Type needs to be implemented
		case BEACON:
		// TODO: This Inventory Type needs to be implemented
		case FURNACE:
		// TODO: This Inventory Type needs to be implemented
		case WORKBENCH:
		// TODO: This Inventory Type needs to be implemented
		case ENCHANTING:
		// TODO: This Inventory Type needs to be implemented
		case BREWING:
		// TODO: This Inventory Type needs to be implemented
		case CRAFTING:
		// TODO: This Inventory Type needs to be implemented
		case CREATIVE:
		// TODO: This Inventory Type needs to be implemented
		case MERCHANT:
		// TODO: This Inventory Type needs to be implemented
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
	public InventoryMock createInventory(InventoryHolder owner, int size)
	{
		return createInventory(owner, size, "Inventory");
	}

	@Override
	public InventoryMock createInventory(InventoryHolder owner, int size, String title)
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
		return playerList.getOperators();
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
		Collection<? extends PlayerMock> players = getOnlinePlayers();

		for (Player player : players)
		{
			player.sendMessage(message);
		}

		return players.size();
	}

	@Override
	public int broadcast(String message, String permission)
	{
		Collection<? extends PlayerMock> players = getOnlinePlayers();
		int count = 0;

		for (Player player : players)
		{
			if (player.hasPermission(permission))
			{
				player.sendMessage(message);
				count++;
			}
		}

		return count;
	}

	/**
	 * Registers any classes that are serializable with the ConfigurationSerializable system of Bukkit.
	 */
	public static void registerSerializables()
	{
		ConfigurationSerialization.registerClass(ItemMetaMock.class);
	}

	@Override
	public boolean addRecipe(Recipe recipe)
	{
		assertMainThread();
		recipes.add(recipe);
		return true;
	}

	@Override
	public List<Recipe> getRecipesFor(@NotNull ItemStack item)
	{
		assertMainThread();

		return recipes.stream().filter(recipe ->
		{
			ItemStack result = recipe.getResult();
			// Amount is explicitly ignored here
			return result.getType() == item.getType() && result.getItemMeta().equals(item.getItemMeta());
		}).collect(Collectors.toList());
	}

	@Override
	public Recipe getRecipe(NamespacedKey key)
	{
		assertMainThread();

		for (Recipe recipe : recipes)
		{
			// Seriously why can't the Recipe interface itself just extend Keyed...
			if (recipe instanceof Keyed && ((Keyed) recipe).getKey().equals(key))
			{
				return recipe;
			}
		}

		return null;
	}

	@Override
	public boolean removeRecipe(NamespacedKey key)
	{
		assertMainThread();

		Iterator<Recipe> iterator = recipeIterator();

		while (iterator.hasNext())
		{
			Recipe recipe = iterator.next();

			// Seriously why can't the Recipe interface itself just extend Keyed...
			if (recipe instanceof Keyed && ((Keyed) recipe).getKey().equals(key))
			{
				iterator.remove();
				return true;
			}
		}

		return false;
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
	public boolean dispatchCommand(CommandSender sender, String commandLine)
	{
		assertMainThread();
		String[] commands = commandLine.split(" ");
		String commandLabel = commands[0];
		String[] args = Arrays.copyOfRange(commands, 1, commands.length);
		Command command = getCommandMap().getCommand(commandLabel);

		if (command != null)
		{
			return command.execute(sender, commandLabel, args);
		}
		else
		{
			return false;
		}
	}

	@Override
	public HelpMapMock getHelpMap()
	{
		return helpMap;
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
	public ServicesManagerMock getServicesManager()
	{
		return servicesManager;
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
	public void reloadData()
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
	public OfflinePlayer getOfflinePlayer(String name)
	{
		return playerList.getOfflinePlayer(name);
	}

	@Override
	public OfflinePlayer getOfflinePlayer(UUID id)
	{
		OfflinePlayer player = playerList.getOfflinePlayer(id);

		if (player != null)
		{
			return player;
		}
		else
		{
			return playerFactory.createRandomOfflinePlayer();
		}
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
	public Merchant createMerchant(String title)
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
		return this.isOnMainThread();
	}

	@Override
	public String getMotd()
	{
		return MOTD;
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
	public BossBar createBossBar(String title, BarColor color, BarStyle style, BarFlag... flags)
	{
		return new BossBarMock(title, color, style, flags);
	}

	@Override
	public Entity getEntity(UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Advancement getAdvancement(NamespacedKey key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Iterator<Advancement> advancementIterator()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public UnsafeValues getUnsafe()
	{
		return unsafe;
	}

	@Override
	public BlockData createBlockData(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockData createBlockData(Material material, Consumer<BlockData> consumer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockData createBlockData(String data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockData createBlockData(Material material, String data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * This creates a new Mock {@link Tag} for the {@link Material} class.<br>
	 * Call this in advance before you are gonna access {@link #getTag(String, NamespacedKey, Class)} or any of the
	 * constants defined in {@link Tag}.
	 *
	 * @param key       The {@link NamespacedKey} for this {@link Tag}
	 * @param registryKey The name of the {@link TagRegistry}.
	 * @param materials {@link Material Materials} which should be covered by this {@link Tag}
	 *
	 * @return The newly created {@link Tag}
	 */
	@NotNull
	public Tag<Material> createMaterialTag(@NotNull NamespacedKey key, @NotNull String registryKey, @NotNull Material... materials)
	{
		Validate.notNull(key, "A NamespacedKey must never be null");

		TagRegistry registry = materialTags.get(registryKey);
		TagWrapperMock tag = new TagWrapperMock(registry, key);
		registry.getTags().put(key, tag);
		return tag;
	}

	public void addTagRegistry(@NotNull TagRegistry registry)
	{
		materialTags.put(registry.getRegistry(), registry);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Keyed> Tag<T> getTag(String registryKey, NamespacedKey key, Class<T> clazz)
	{
		if (clazz == Material.class)
		{
			TagRegistry registry = materialTags.get(registryKey);

			if (registry != null)
			{
				Tag<Material> tag = registry.getTags().get(key);

				if (tag != null)
				{
					return (Tag<T>) tag;
				}
			}
		}

		// Per definition this method should return null if the given tag does not exist.
		return null;
	}

	/**
	 * This registers Minecrafts default {@link PotionEffectType PotionEffectTypes}. It also prevents any new effects to
	 * be created afterwards.
	 */
	private void createPotionEffectTypes()
	{
		for (PotionEffectType type : PotionEffectType.values())
		{
			// We probably already registered all Potion Effects
			// otherwise this would be null
			if (type != null)
			{
				// This is not perfect, but it works.
				return;
			}
		}

		registerPotionEffectType(1, "SPEED", false, 8171462);
		registerPotionEffectType(2, "SLOWNESS", false, 5926017);
		registerPotionEffectType(3, "HASTE", false, 14270531);
		registerPotionEffectType(4, "MINING_FATIGUE", false, 4866583);
		registerPotionEffectType(5, "STRENGTH", false, 9643043);
		registerPotionEffectType(6, "INSTANT_HEALTH", true, 16262179);
		registerPotionEffectType(7, "INSTANT_DAMAGE", true, 4393481);
		registerPotionEffectType(8, "JUMP_BOOST", false, 2293580);
		registerPotionEffectType(9, "NAUSEA", false, 5578058);
		registerPotionEffectType(10, "REGENERATION", false, 13458603);
		registerPotionEffectType(11, "RESISTANCE", false, 10044730);
		registerPotionEffectType(12, "FIRE_RESISTANCE", false, 14981690);
		registerPotionEffectType(13, "WATER_BREATHING", false, 3035801);
		registerPotionEffectType(14, "INVISIBILITY", false, 8356754);
		registerPotionEffectType(15, "BLINDNESS", false, 2039587);
		registerPotionEffectType(16, "NIGHT_VISION", false, 2039713);
		registerPotionEffectType(17, "HUNGER", false, 5797459);
		registerPotionEffectType(18, "WEAKNESS", false, 4738376);
		registerPotionEffectType(19, "POISON", false, 5149489);
		registerPotionEffectType(20, "WITHER", false, 3484199);
		registerPotionEffectType(21, "HEALTH_BOOST", false, 16284963);
		registerPotionEffectType(22, "ABSORPTION", false, 2445989);
		registerPotionEffectType(23, "SATURATION", true, 16262179);
		registerPotionEffectType(24, "GLOWING", false, 9740385);
		registerPotionEffectType(25, "LEVITATION", false, 13565951);
		registerPotionEffectType(26, "LUCK", false, 3381504);
		registerPotionEffectType(27, "UNLUCK", false, 12624973);
		registerPotionEffectType(28, "SLOW_FALLING", false, 16773073);
		registerPotionEffectType(29, "CONDUIT_POWER", false, 1950417);
		registerPotionEffectType(30, "DOLPHINS_GRACE", false, 8954814);
		registerPotionEffectType(31, "BAD_OMEN", false, 745784);
		registerPotionEffectType(32, "HERO_OF_THE_VILLAGE", false, 45217);
		PotionEffectType.stopAcceptingRegistrations();
	}

	private void registerPotionEffectType(int id, @NotNull String name, boolean instant, int rgb)
	{
		PotionEffectType type = new MockPotionEffectType(id, name, instant, Color.fromRGB(rgb));
		PotionEffectType.registerPotionEffectType(type);
	}

	@Override
	public LootTable getLootTable(NamespacedKey key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack createExplorerMap(World world, Location location, StructureType structureType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack createExplorerMap(World world, Location location, StructureType structureType, int radius,
	                                   boolean findUnexplored)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public KeyedBossBar createBossBar(NamespacedKey key, String title, BarColor color, BarStyle style, BarFlag... flags)
	{
		Validate.notNull(key, "A NamespacedKey must never be null");
		KeyedBossBarMock bar = new KeyedBossBarMock(key, title, color, style, flags);
		bossBars.put(key, bar);
		return bar;
	}

	@Override
	public Iterator<KeyedBossBar> getBossBars()
	{
		return bossBars.values().stream().map(bossbar -> (KeyedBossBar) bossbar).iterator();
	}

	@Override
	public KeyedBossBar getBossBar(NamespacedKey key)
	{
		Validate.notNull(key, "A NamespacedKey must never be null");
		return bossBars.get(key);
	}

	@Override
	public boolean removeBossBar(NamespacedKey key)
	{
		Validate.notNull(key, "A NamespacedKey must never be null");
		return bossBars.remove(key, bossBars.get(key));
	}

	@Override
	public List<Entity> selectEntities(CommandSender sender, String selector)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public MapView getMap(int id)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Keyed> Iterable<Tag<T>> getTags(String registry, Class<T> clazz)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTicksPerWaterSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTicksPerAmbientSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * This returns the current time of the {@link Server} in milliseconds
	 *
	 * @return The current {@link Server} time
	 */
	protected long getCurrentServerTime()
	{
		return System.currentTimeMillis();
	}

	@Override
	public int getTicksPerWaterAmbientSpawns()
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
	public int getMaxWorldSize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Server.Spigot spigot()
	{
		return this;
	}

	// Methods from Server.Spigot:

	@NotNull
	@Override
	public YamlConfiguration getConfig()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void broadcast(@NotNull BaseComponent component)
	{
		for (Player player : getOnlinePlayers())
		{
			player.spigot().sendMessage(component);
		}
	}

	@Override
	public void broadcast(@NotNull BaseComponent... components)
	{
		for (Player player : getOnlinePlayers())
		{
			player.spigot().sendMessage(components);
		}
	}

	@Override
	public void restart()
	{
		throw new UnsupportedOperationException("Not supported.");
	}
}
