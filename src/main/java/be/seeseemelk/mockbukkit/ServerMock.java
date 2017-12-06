package be.seeseemelk.mockbukkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.advancement.Advancement;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;

import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.inventory.ItemFactoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;

@SuppressWarnings("deprecation")
public class ServerMock implements Server
{
	private final Logger logger = Logger.getLogger("ServerMock");
	
	private final List<PlayerMock> players = new ArrayList<>();

	private final ItemFactory factory = new ItemFactoryMock();
	private final PlayerMockFactory playerFactory = new PlayerMockFactory();
	private final PluginManagerMock pluginManager = new PluginManagerMock(this);
	private ConsoleCommandSender consoleSender;
	
	/**
	 * Add a specific player to the set.
	 * @param player The player to add.
	 */
	public void addPlayer(PlayerMock player)
	{
		players.add(player);
	}
	
	/**
	 * Creates a random player and adds it.
	 */
	public void addPlayer()
	{
		addPlayer(playerFactory.createRandomPlayer());
	}
	
	/**
	 * Set the numbers of mock players that are on this server.
	 * Note that it will remove all players that are already on this server. 
	 * @param players The number of players that are on this server.
	 */
	public void setPlayers(int num)
	{
		players.clear();
		for (int i = 0; i < num; i++)
		{
			addPlayer();
		}
	}
	
	/**
	 * Get a specific mock player.
	 * A player's number will never change between invocations of {@link setPlayers}.
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
	public String getName()
	{
		return "ServerMock";
	}

	@Override
	public String getVersion()
	{
		return "0.0.1";
	}

	@Override
	public String getBukkitVersion()
	{
		return "1.12.1";
	}

	@Override
	public Collection<? extends Player> getOnlinePlayers()
	{
		return players;
	}

	@Override
	public int getMaxPlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	/**
	 * Executes a command as the console.
	 * @param command The command to execute.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executeConsole(Command command, String... args)
	{
		return execute(command, getConsoleSender(), args);
	}
	
	/**
	 * Executes a command as a player.
	 * @param command The command to execute.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult executePlayer(Command command, String... args)
	{
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
	 * Executes a command.
	 * @param command The command to execute.
	 * @param sender The person that executed the command.
	 * @param args The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public CommandResult execute(Command command, CommandSender sender, String... args)
	{
		if (!(sender instanceof MessageTarget))
		{
			throw new IllegalArgumentException("Only a MessageTarget can be the sender of the command");
		}
		
		boolean status = command.execute(sender, command.getName(), args);
		CommandResult result = new CommandResult(status, (MessageTarget) sender);
		return result;
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
	public int broadcastMessage(String message)
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
	public Player getPlayer(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Player getPlayerExact(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<Player> matchPlayer(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Player getPlayer(UUID id)
	{
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

	@Override
	public BukkitScheduler getScheduler()
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
	public List<World> getWorlds()
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
	public World getWorld(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public World getWorld(UUID uid)
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
	public void reloadData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Logger getLogger()
	{
		return logger;
	}

	@Override
	public PluginCommand getPluginCommand(String name)
	{
		for (PluginCommand command : getPluginManager().getCommands())
		{
			if (name.equals(command.getName()))
			{
				return command;
			}
			else
			{
				for (String alias : command.getAliases())
				{
					if (name.equals(alias))
					{
						return command;
					}
				}
			}
		}
		
		throw new IllegalArgumentException("No such command");
	}

	@Override
	public void savePlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean dispatchCommand(CommandSender sender, String commandLine) throws CommandException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addRecipe(Recipe recipe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<Recipe> getRecipesFor(ItemStack result)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Iterator<Recipe> recipeIterator()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearRecipes()
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
	public Set<String> getIPBans()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void banIP(String address)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void unbanIP(String address)
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
	public BanList getBanList(Type type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<OfflinePlayer> getOperators()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public GameMode getDefaultGameMode()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setDefaultGameMode(GameMode mode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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

	@Override
	public File getWorldContainer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public OfflinePlayer[] getOfflinePlayers()
	{
		return players.toArray(new OfflinePlayer[0]);
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
	public Inventory createInventory(InventoryHolder owner, InventoryType type)
	{
		switch (type)
		{
			case PLAYER:
				PlayerInventoryMock inventory = new PlayerInventoryMock("Inventory");
				return inventory;
			default:
				throw new UnimplementedOperationException("Inventory type not yet supported");
		}
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, InventoryType type, String title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException
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
	public ItemFactory getItemFactory()
	{
		return factory;
	}

	@Override
	public ScoreboardManager getScoreboardManager()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public CachedServerIcon getServerIcon()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public CachedServerIcon loadServerIcon(File file) throws IllegalArgumentException, Exception
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public CachedServerIcon loadServerIcon(BufferedImage image) throws IllegalArgumentException, Exception
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public UnsafeValues getUnsafe()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
