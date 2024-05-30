package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.boss.BossBarMock;
import be.seeseemelk.mockbukkit.boss.KeyedBossBarMock;
import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.command.MockCommandMap;
import be.seeseemelk.mockbukkit.configuration.ServerConfiguration;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.help.HelpMapMock;
import be.seeseemelk.mockbukkit.inventory.AnvilInventoryMock;
import be.seeseemelk.mockbukkit.inventory.BarrelInventoryMock;
import be.seeseemelk.mockbukkit.inventory.BeaconInventoryMock;
import be.seeseemelk.mockbukkit.inventory.BrewerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.CartographyInventoryMock;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.DispenserInventoryMock;
import be.seeseemelk.mockbukkit.inventory.DropperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.EnchantingInventoryMock;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.FurnaceInventoryMock;
import be.seeseemelk.mockbukkit.inventory.GrindstoneInventoryMock;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.ItemFactoryMock;
import be.seeseemelk.mockbukkit.inventory.LecternInventoryMock;
import be.seeseemelk.mockbukkit.inventory.LoomInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.ShulkerBoxInventoryMock;
import be.seeseemelk.mockbukkit.inventory.SmithingInventoryMock;
import be.seeseemelk.mockbukkit.inventory.StonecutterInventoryMock;
import be.seeseemelk.mockbukkit.inventory.WorkbenchInventoryMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.map.MapViewMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import be.seeseemelk.mockbukkit.scheduler.paper.FoliaAsyncScheduler;
import be.seeseemelk.mockbukkit.scoreboard.CriteriaMock;
import be.seeseemelk.mockbukkit.scoreboard.ScoreboardManagerMock;
import be.seeseemelk.mockbukkit.services.ServicesManagerMock;
import be.seeseemelk.mockbukkit.tags.TagRegistry;
import be.seeseemelk.mockbukkit.tags.TagWrapperMock;
import be.seeseemelk.mockbukkit.tags.TagsMock;
import be.seeseemelk.mockbukkit.tags.internal.InternalTag;
import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import com.destroystokyo.paper.event.server.WhitelistToggleEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import io.papermc.paper.ban.BanListType;
import io.papermc.paper.datapack.DatapackManager;
import io.papermc.paper.math.Position;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.ServerTickManager;
import org.bukkit.StructureType;
import org.bukkit.Tag;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.WorldBorder;
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
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemCraftResult;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapCursor;
import org.bukkit.packs.DataPackManager;
import org.bukkit.packs.ResourcePack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Mock implementation of a {@link Server} and {@link Server.Spigot}.
 */
public class ServerMock extends Server.Spigot implements Server
{

	private Component motd = Component.text("A Minecraft Server");
	private static final Component NO_PERMISSION = Component.text(
			"I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.",
			NamedTextColor.RED);

	private final Logger logger = Logger.getLogger("ServerMock");
	private final Thread mainThread = Thread.currentThread();
	private final MockUnsafeValues unsafe = new MockUnsafeValues();
	private final Map<String, TagRegistry> materialTags = new HashMap<>();
	private final Set<EntityMock> entities = new HashSet<>();
	private final List<World> worlds = new ArrayList<>();
	private final List<Recipe> recipes = new LinkedList<>();
	private final Map<NamespacedKey, KeyedBossBarMock> bossBars = new HashMap<>();
	private final ItemFactoryMock factory = new ItemFactoryMock();
	private final PlayerMockFactory playerFactory = new PlayerMockFactory(this);
	private final PluginManagerMock pluginManager = new PluginManagerMock(this);
	private final ScoreboardManagerMock scoreboardManager = new ScoreboardManagerMock();
	private final Map<String, Criteria> criteria = new HashMap<>();
	private final BukkitSchedulerMock scheduler = new BukkitSchedulerMock();
	private final FoliaAsyncScheduler foliaAsyncScheduler = new FoliaAsyncScheduler(scheduler);
	private final ServicesManagerMock servicesManager = new ServicesManagerMock();
	private final MockPlayerList playerList = new MockPlayerList();
	private final MockCommandMap commandMap = new MockCommandMap(this);
	private final HelpMapMock helpMap = new HelpMapMock();
	private final StandardMessenger messenger = new StandardMessenger();
	private final Map<Integer, MapViewMock> mapViews = new HashMap<>();
	private CachedServerIconMock serverIcon = new CachedServerIconMock(null);
	private int nextMapId = 1;

	private GameMode defaultGameMode = GameMode.SURVIVAL;
	private ConsoleCommandSenderMock consoleSender;
	private int spawnRadius = 16;
	private @NotNull WarningState warningState = WarningState.DEFAULT;

	private boolean isWhitelistEnabled = false;
	private boolean isWhitelistEnforced = false;
	private final @NotNull Set<OfflinePlayer> whitelistedPlayers = new LinkedHashSet<>();

	private final @NotNull ServerConfiguration serverConfiguration = new ServerConfiguration();
	private final Map<Class<?>, Registry<?>> registry = new HashMap<>();

	/**
	 * Constructs a new ServerMock and sets it up.
	 * Does <b>NOT</b> set the server returned from {@link Bukkit#getServer()}.
	 */
	public ServerMock()
	{
		ServerMock.registerSerializables();

		TagsMock.loadDefaultTags(this, true);
		InternalTag.loadInternalTags();

		try
		{
			InputStream stream = getClass().getClassLoader().getResourceAsStream("logger.properties");
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
	 * Registers an entity so that the server can track it more easily. Should only be used internally.
	 *
	 * @param entity The entity to register
	 */
	public void registerEntity(@NotNull EntityMock entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		AsyncCatcher.catchOp("entity add");
		entities.add(entity);
	}

	/**
	 * Unregisters an entity from the server. Should only be used internally.
	 *
	 * @param entity The entity to unregister
	 */
	public void unregisterEntity(@NotNull EntityMock entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		Preconditions.checkArgument(!entity.isValid(), "Entity is not marked for removal");
		AsyncCatcher.catchOp("entity remove");
		entities.remove(entity);
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
	public void addPlayer(@NotNull PlayerMock player)
	{
		AsyncCatcher.catchOp("player add");
		playerList.addPlayer(player);

		CountDownLatch conditionLatch = new CountDownLatch(1);

		InetSocketAddress address = player.getAddress();
		AsyncPlayerPreLoginEvent preLoginEvent = new AsyncPlayerPreLoginEvent(player.getName(), address.getAddress(),
				player.getUniqueId());
		getPluginManager().callEventAsynchronously(preLoginEvent, (e) -> conditionLatch.countDown());
		try
		{
			conditionLatch.await();
		}
		catch (InterruptedException e)
		{
			getLogger().severe("Interrupted while waiting for AsyncPlayerPreLoginEvent! "
					+ (StringUtils.isEmpty(e.getMessage()) ? "" : e.getMessage()));
			Thread.currentThread().interrupt();
		}

		PlayerLoginEvent playerLoginEvent = new PlayerLoginEvent(player, address.getHostString(), address.getAddress());
		Bukkit.getPluginManager().callEvent(playerLoginEvent);

		Component joinMessage = MiniMessage.miniMessage().deserialize("<name> has joined the Server!",
				Placeholder.component("name", player.displayName()));

		PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player, joinMessage);
		Bukkit.getPluginManager().callEvent(playerJoinEvent);

		if (isWhitelistEnabled && !whitelistedPlayers.contains(player))
		{
			PlayerConnectionCloseEvent playerConnectionCloseEvent = new PlayerConnectionCloseEvent(player.getUniqueId(),
					player.getName(), player.getAddress().getAddress(), false);

			getPluginManager().callEvent(playerConnectionCloseEvent);
			playerList.disconnectPlayer(player);
			return;
		}

		PlayerSpawnLocationEvent playerSpawnLocationEvent = new PlayerSpawnLocationEvent(player, player.getLocation());
		getPluginManager().callEvent(playerSpawnLocationEvent);
		player.setLocation(playerSpawnLocationEvent.getSpawnLocation());

		registerEntity(player);
	}

	/**
	 * Creates a random player and adds it.
	 *
	 * @return The player that was added.
	 */
	public @NotNull PlayerMock addPlayer()
	{
		AsyncCatcher.catchOp("player add");
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
	public @NotNull PlayerMock addPlayer(@NotNull String name)
	{
		AsyncCatcher.catchOp("player add");
		PlayerMock player = new PlayerMock(this, name, UUID.randomUUID());
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
		AsyncCatcher.catchOp("set players");
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
		AsyncCatcher.catchOp("set offline players");
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
	public @NotNull PlayerMock getPlayer(int num)
	{
		return playerList.getPlayer(num);
	}

	/**
	 * Returns the {@link MockPlayerList} instance that is used by this server.
	 *
	 * @return The {@link MockPlayerList} instance.
	 */
	public @NotNull MockPlayerList getPlayerList()
	{
		return playerList;
	}

	@Override
	public @Nullable UUID getPlayerUniqueId(@NotNull String playerName)
	{
		return playerList.getOfflinePlayer(playerName).getUniqueId();
	}

	/**
	 * Adds a very simple super flat world with a given name.
	 *
	 * @param name The name to give to the world.
	 * @return The {@link WorldMock} that has been created.
	 */
	public @NotNull WorldMock addSimpleWorld(String name)
	{
		AsyncCatcher.catchOp("world creation");
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
		AsyncCatcher.catchOp("world add");
		worlds.add(world);
	}

	/**
	 * Removes a mocked world from this server.
	 *
	 * @param world The world to remove.
	 * @return true if the world was removed, otherwise false.
	 */
	public boolean removeWorld(WorldMock world)
	{
		AsyncCatcher.catchOp("world remove");
		return worlds.remove(world);
	}

	/**
	 * Executes a command as the console.
	 *
	 * @param command The command to execute.
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public @NotNull CommandResult executeConsole(@NotNull Command command, String... args)
	{
		return execute(command, getConsoleSender(), args);
	}

	/**
	 * Executes a command as the console.
	 *
	 * @param command The command to execute.
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public @NotNull CommandResult executeConsole(@NotNull String command, String... args)
	{
		return executeConsole(getCommandMap().getCommand(command), args);
	}

	/**
	 * Executes a command as a player.
	 *
	 * @param command The command to execute.
	 * @param args    The arguments to pass to the commands.
	 * @return The value returned by {@link Command#execute}.
	 */
	public @NotNull CommandResult executePlayer(@NotNull Command command, String... args)
	{
		AsyncCatcher.catchOp("command dispatch");

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
	public @NotNull CommandResult executePlayer(@NotNull String command, String... args)
	{
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
	public @NotNull CommandResult execute(@NotNull Command command, CommandSender sender, String... args)
	{
		AsyncCatcher.catchOp("command dispatch");

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
	public @NotNull CommandResult execute(@NotNull String command, CommandSender sender, String... args)
	{
		AsyncCatcher.catchOp("command dispatch");
		return execute(getCommandMap().getCommand(command), sender, args);
	}

	@Override
	public @NotNull String getName()
	{
		return "ServerMock";
	}

	@Override
	public @NotNull String getVersion()
	{
		return String.format("MockBukkit (MC: %s)", getMinecraftVersion());
	}

	@Override
	public @NotNull String getBukkitVersion()
	{
		return BuildParameters.PAPER_API_FULL_VERSION;
	}

	@Override
	public @NotNull String getMinecraftVersion()
	{
		return this.getBukkitVersion().split("-")[0];
	}

	@Override
	public @NotNull Collection<? extends PlayerMock> getOnlinePlayers()
	{
		return playerList.getOnlinePlayers();
	}

	@Override
	public OfflinePlayer @NotNull [] getOfflinePlayers()
	{
		return playerList.getOfflinePlayers();
	}

	@Override
	public @Nullable OfflinePlayer getOfflinePlayerIfCached(@NotNull String name)
	{
		return playerList.getOfflinePlayerIfCached(name);
	}

	@Override
	public Player getPlayer(@NotNull String name)
	{
		return playerList.getPlayer(name);
	}

	@Override
	public Player getPlayerExact(@NotNull String name)
	{
		return playerList.getPlayerExact(name);
	}

	@Override
	public @NotNull List<Player> matchPlayer(@NotNull String name)
	{
		return playerList.matchPlayer(name);
	}

	@Override
	public Player getPlayer(@NotNull UUID id)
	{
		return playerList.getPlayer(id);
	}

	@Override
	public @NotNull PluginManagerMock getPluginManager()
	{
		return pluginManager;
	}

	@NotNull
	public MockCommandMap getCommandMap()
	{
		return commandMap;
	}

	@Override
	public PluginCommand getPluginCommand(@NotNull String name)
	{
		Command command = getCommandMap().getCommand(name);
		return command instanceof PluginCommand ? (PluginCommand) command : null;
	}

	@Override
	public @NotNull Logger getLogger()
	{
		return logger;
	}

	@Override
	public @NotNull ConsoleCommandSenderMock getConsoleSender()
	{
		if (consoleSender == null)
		{
			consoleSender = new ConsoleCommandSenderMock();
		}
		return consoleSender;
	}

	@Override
	public @NotNull CommandSender createCommandSender(@NotNull Consumer<? super Component> feedback)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Creates an inventory with the provided parameters.
	 *
	 * @param owner The holder of the inventory.
	 * @param type  The type of the inventory.
	 * @param title The title of the inventory view.
	 * @param size  The size of the inventory.
	 * @return The created inventory.
	 * @throws IllegalArgumentException If the InventoryType is not creatable.
	 * @see InventoryType#isCreatable()
	 */
	@NotNull
	@Deprecated
	public InventoryMock createInventory(InventoryHolder owner, @NotNull InventoryType type, String title, int size)
	{
		Preconditions.checkArgument(type.isCreatable(), "Inventory Type '" + type + "' is not creatable!");

		switch (type)
		{
		case CHEST:
			return new ChestInventoryMock(owner, size > 0 ? size : 9 * 3);
		case DISPENSER:
			return new DispenserInventoryMock(owner);
		case DROPPER:
			return new DropperInventoryMock(owner);
		case PLAYER:
			if (owner instanceof HumanEntity he)
			{
				return new PlayerInventoryMock(he);
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
			return new GrindstoneInventoryMock(owner);
		case STONECUTTER:
			return new StonecutterInventoryMock(owner);
		case CARTOGRAPHY:
			return new CartographyInventoryMock(owner);
		case SMOKER, FURNACE, BLAST_FURNACE:
			return new FurnaceInventoryMock(owner);
		case LOOM:
			return new LoomInventoryMock(owner);
		case ANVIL:
			return new AnvilInventoryMock(owner);
		case SMITHING:
			return new SmithingInventoryMock(owner);
		case BEACON:
			return new BeaconInventoryMock(owner);
		case WORKBENCH:
			return new WorkbenchInventoryMock(owner);
		case ENCHANTING:
			return new EnchantingInventoryMock(owner);
		case BREWING:
			return new BrewerInventoryMock(owner);
		default:
			throw new UnimplementedOperationException("Inventory type not yet supported");
		}
	}

	@Override
	public @NotNull InventoryMock createInventory(InventoryHolder owner, @NotNull InventoryType type)
	{
		return createInventory(owner, type, "Inventory");
	}

	@Override
	public @NotNull InventoryMock createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type,
			@NotNull Component title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull InventoryMock createInventory(InventoryHolder owner, @NotNull InventoryType type, String title)
	{
		return createInventory(owner, type, title, -1);
	}

	@Override
	public @NotNull InventoryMock createInventory(InventoryHolder owner, int size)
	{
		return createInventory(owner, size, "Inventory");
	}

	@Override
	public @NotNull InventoryMock createInventory(@Nullable InventoryHolder owner, int size, @NotNull Component title)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull InventoryMock createInventory(InventoryHolder owner, int size, String title)
	{
		return createInventory(owner, InventoryType.CHEST, title, size);
	}

	@Override
	public @NotNull Merchant createMerchant(@Nullable Component title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemFactory getItemFactory()
	{
		return factory;
	}

	@Override
	public @NotNull List<World> getWorlds()
	{
		return new ArrayList<>(worlds);
	}

	@Override
	public boolean isTickingWorlds()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @Nullable World getWorld(@NotNull NamespacedKey worldKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public WorldBorder createWorldBorder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BukkitSchedulerMock getScheduler()
	{
		return scheduler;
	}

	@Override
	public int getMaxPlayers()
	{
		return playerList.getMaxPlayers();
	}

	@Override
	public void setMaxPlayers(int maxPlayers)
	{
		playerList.setMaxPlayers(maxPlayers);
	}

	@Override
	public @NotNull Set<String> getIPBans()
	{
		return this.playerList.getIPBans().getEntries().stream().map(BanEntry::getBanTarget)
				.map(InetAddress::getHostAddress).collect(Collectors.toSet());
	}

	@Override
	public void banIP(@NotNull String address)
	{
		this.playerList.getIPBans().addBan(address, null, null, null);
	}

	@Override
	public void unbanIP(@NotNull String address)
	{
		this.playerList.getIPBans().pardon(address);
	}

	@Override
	public void banIP(@NotNull InetAddress address)
	{
		Preconditions.checkNotNull(address, "Address cannot be null");
		this.playerList.getIPBans().addBan(address, null, (Date) null, null);
	}

	@Override
	public void unbanIP(@NotNull InetAddress address)
	{
		Preconditions.checkNotNull(address, "Address cannot be null");
		this.playerList.getIPBans().pardon(address);
	}

	@Override
	public @NotNull BanList getBanList(@NotNull Type type)
	{
		return switch (type)
		{
		case IP -> playerList.getIPBans();
		case NAME, PROFILE -> playerList.getProfileBans();
		};
	}

	@Override
	public @NotNull Set<OfflinePlayer> getOperators()
	{
		return playerList.getOperators();
	}

	@Override
	public @NotNull GameMode getDefaultGameMode()
	{
		return this.defaultGameMode;
	}

	@Override
	public void setDefaultGameMode(GameMode mode)
	{
		this.defaultGameMode = mode;
	}

	@Override
	@Deprecated
	public int broadcastMessage(@NotNull String message)
	{
		Collection<? extends PlayerMock> players = getOnlinePlayers();

		for (Player player : players)
		{
			player.sendMessage(message);
		}

		return players.size();
	}

	@Override
	@Deprecated
	public int broadcast(@NotNull String message, @NotNull String permission)
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

	@Override
	public int broadcast(@NotNull Component message)
	{
		Collection<? extends PlayerMock> players = getOnlinePlayers();

		for (Player player : players)
		{
			player.sendMessage(message);
		}

		return players.size();
	}

	@Override
	public int broadcast(@NotNull Component message, @NotNull String permission)
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
		Preconditions.checkNotNull(recipe, "recipe cannot be null");
		return addRecipe(recipe, false);
	}

	@Override
	public boolean addRecipe(@Nullable Recipe recipe, boolean resendRecipes)
	{
		AsyncCatcher.catchOp("Recipe add");
		if (recipe == null)
			return false;
		// Pretend we sent the packet if resendRecipes is true
		return recipes.add(recipe);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull List<Recipe> getRecipesFor(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item, "item cannot be null");
		return recipes.stream().filter(recipe -> {
			ItemStack result = recipe.getResult();
			return result.getType() == item.getType()
					&& (result.getDurability() == -1 || result.getDurability() == item.getDurability());
		}).toList();
	}

	@Nullable
	@Override
	public Recipe getRecipe(@NotNull NamespacedKey key)
	{
		Preconditions.checkNotNull(key, "key cannot be null");

		for (Recipe recipe : recipes)
		{
			// Seriously why can't the Recipe interface itself just extend Keyed...
			if (recipe instanceof Keyed keyed && keyed.getKey().equals(key))
			{
				return recipe;
			}
		}

		return null;
	}

	@Nullable
	@Override
	public Recipe getCraftingRecipe(@NotNull ItemStack[] craftingMatrix, @NotNull World world)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public ItemStack craftItem(@NotNull ItemStack[] craftingMatrix, @NotNull World world, @NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemCraftResult craftItemResult(@NotNull ItemStack[] craftingMatrix, @NotNull World world)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemCraftResult craftItemResult(@NotNull ItemStack[] craftingMatrix, @NotNull World world,
			@NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack craftItem(@NotNull ItemStack[] craftingMatrix, @NotNull World world)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeRecipe(@NotNull NamespacedKey key)
	{
		return removeRecipe(key, false);
	}

	@Override
	public boolean removeRecipe(@NotNull NamespacedKey key, boolean resendRecipes)
	{
		Preconditions.checkNotNull(key, "key cannot be null");
		Iterator<Recipe> iterator = recipeIterator();

		while (iterator.hasNext())
		{
			Recipe recipe = iterator.next();

			// Seriously why can't the Recipe interface itself just extend Keyed...
			if (recipe instanceof Keyed keyed && keyed.getKey().equals(key))
			{
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	@Override
	public @NotNull Iterator<Recipe> recipeIterator()
	{
		return recipes.iterator();
	}

	@Override
	public void clearRecipes()
	{
		recipes.clear();
	}

	@Override
	public boolean dispatchCommand(@NotNull CommandSender sender, @NotNull String commandLine)
	{
		AsyncCatcher.catchOp("command dispatch");
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

	/**
	 * Gets the tab completion result for a command.
	 *
	 * @param sender      The command sender.
	 * @param commandLine The command string, without a leading slash.
	 * @return The tab completion result, or an empty list.
	 */
	public @NotNull List<String> getCommandTabComplete(@NotNull CommandSender sender, @NotNull String commandLine)
	{
		AsyncCatcher.catchOp("command tabcomplete");
		int idx = commandLine.indexOf(' ');
		String commandLabel = commandLine.substring(0, idx);
		String[] args = commandLine.substring(idx + 1).split(" ", -1);
		Command command = getCommandMap().getCommand(commandLabel);

		if (command != null)
		{
			return command.tabComplete(sender, commandLabel, args);
		}
		else
		{
			return Collections.emptyList();
		}
	}

	@Override
	public @NotNull HelpMapMock getHelpMap()
	{
		return helpMap;
	}

	@Override
	public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, byte[] message)
	{
		StandardMessenger.validatePluginMessage(this.getMessenger(), source, channel, message);

		for (Player player : this.getOnlinePlayers())
		{
			player.sendPluginMessage(source, channel, message);
		}
	}

	@Override
	public @NotNull Set<String> getListeningPluginChannels()
	{
		Set<String> result = new HashSet<>();

		for (Player player : this.getOnlinePlayers())
		{
			result.addAll(player.getListeningPluginChannels());
		}

		return result;
	}

	@Override
	public int getPort()
	{
		return this.serverConfiguration.getServerPort();
	}

	/**
	 * Sets the server listen port.
	 *
	 * @param port The server listen port.
	 * @see ServerMock#getPort()
	 */
	public void setPort(int port)
	{
		this.serverConfiguration.setServerPort(port);
	}

	@Override
	public int getViewDistance()
	{
		return this.serverConfiguration.getViewDistance();
	}

	/**
	 * Sets the global view distance for all players.
	 *
	 * @param viewDistance The new view distance.
	 * @see ServerMock#getViewDistance()
	 */
	public void setViewDistance(int viewDistance)
	{
		this.serverConfiguration.setViewDistance(viewDistance);
	}

	@Override
	public @NotNull String getIp()
	{
		return this.serverConfiguration.getServerIp();
	}

	/**
	 * Sets the server listen IP.
	 *
	 * @param serverIp The server listen IP.
	 * @see ServerMock#getIp()
	 */
	public void setIp(@NotNull String serverIp)
	{
		this.serverConfiguration.setServerIp(serverIp);
	}

	@Override
	public @NotNull String getWorldType()
	{
		return this.serverConfiguration.getLevelType().getKey();
	}

	/**
	 * Sets the global default World Type
	 *
	 * @param worldType The new {@link ServerConfiguration.LevelType}
	 * @see ServerMock#getWorldType()
	 */
	public void setWorldType(@NotNull ServerConfiguration.LevelType worldType)
	{
		this.serverConfiguration.setLevelType(worldType);
	}

	@Override
	public boolean getGenerateStructures()
	{
		return this.serverConfiguration.isGenerateStructures();
	}

	/**
	 * Sets whether structures should be generated.
	 *
	 * @param generateStructures Whether structures should be generated.
	 * @see ServerMock#getGenerateStructures()
	 */
	public void setGenerateStructures(boolean generateStructures)
	{
		this.serverConfiguration.setGenerateStructures(generateStructures);
	}

	@Override
	public boolean getAllowEnd()
	{
		return this.serverConfiguration.isAllowEnd();
	}

	/**
	 * Sets whether the End should be allowed.
	 *
	 * @param allowEnd Whether the End should be allowed.
	 * @see ServerMock#getAllowEnd()
	 */
	public void setAllowEnd(boolean allowEnd)
	{
		this.serverConfiguration.setAllowEnd(allowEnd);
	}

	@Override
	public boolean getAllowNether()
	{
		return this.serverConfiguration.isAllowNether();
	}

	@Override
	public boolean isLoggingIPs()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<String> getInitialEnabledPacks()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<String> getInitialDisabledPacks()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.19")
	public @NotNull DataPackManager getDataPackManager()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ServerTickManager getServerTickManager()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ResourcePack getServerResourcePack()
	{
		throw new UnimplementedOperationException();
	}

	/**
	 * Sets whether the Nether should be allowed.
	 *
	 * @param allowNether Whether the Nether should be allowed.
	 * @see ServerMock#getAllowNether()
	 */
	public void setAllowNether(boolean allowNether)
	{
		this.serverConfiguration.setAllowNether(allowNether);
	}

	@NotNull
	@Override
	public String getResourcePack()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public String getResourcePackHash()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public String getResourcePackPrompt()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isResourcePackRequired()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasWhitelist()
	{
		return this.isWhitelistEnabled;
	}

	@Override
	public void setWhitelist(boolean value)
	{
		this.isWhitelistEnabled = value;
		WhitelistToggleEvent event = new WhitelistToggleEvent(value);
		this.getPluginManager().callEvent(event);
	}

	@Override
	public boolean isWhitelistEnforced()
	{
		return this.isWhitelistEnforced;
	}

	@Override
	public void setWhitelistEnforced(boolean value)
	{
		this.isWhitelistEnforced = value;
	}

	@Override
	public @NotNull Set<OfflinePlayer> getWhitelistedPlayers()
	{
		return this.whitelistedPlayers;
	}

	@Override
	public void reloadWhitelist()
	{
		// Pretend we load the Whitelist from Disk
		if (!isWhitelistEnforced && isWhitelistEnabled)
		{
			return;
		}

		MockBukkit.getMock().getOnlinePlayers().forEach(p -> {
			if (!MockBukkit.getMock().getWhitelistedPlayers().contains(p))
			{
				p.kick();
			}
		});
	}

	@Override
	public @NotNull String getUpdateFolder()
	{
		return this.serverConfiguration.getUpdateFolder();
	}

	/**
	 * Sets the global update folder.
	 *
	 * @param updateFolder The new update folder.
	 * @see ServerConfiguration#setUpdateFolder(String)
	 */
	public void setUpdateFolder(@NotNull String updateFolder)
	{
		this.serverConfiguration.setUpdateFolder(updateFolder);
	}

	@Override
	public @NotNull File getUpdateFolderFile()
	{
		return new File(this.getPluginsFolder(), this.getUpdateFolder());
	}

	@Override
	public long getConnectionThrottle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getTicksPerAnimalSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.ANIMAL);
	}

	@Override
	@Deprecated
	public int getTicksPerMonsterSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.MONSTER);
	}

	@Override
	public @NotNull ServicesManagerMock getServicesManager()
	{
		return servicesManager;
	}

	@Override
	public World createWorld(@NotNull WorldCreator creator)
	{
		WorldMock world = new WorldMock(creator);
		addWorld(world);
		return world;
	}

	@Override
	public boolean unloadWorld(String name, boolean save)
	{
		return unloadWorld(getWorld(name), save);
	}

	@Override
	public boolean unloadWorld(World world, boolean save)
	{
		// TODO Handle save
		if (!(world instanceof WorldMock worldMock))
		{
			return false;
		}
		if (!worldMock.getPlayers().isEmpty())
		{
			return false;
		}
		if (new WorldUnloadEvent(worldMock).callEvent())
		{
			return false;
		}
		return removeWorld(worldMock);
	}

	@Override
	public @NotNull MapViewMock createMap(@NotNull World world)
	{
		MapViewMock mapView = new MapViewMock(world, nextMapId++);
		mapViews.put(mapView.getId(), mapView);
		new MapInitializeEvent(mapView).callEvent();
		return mapView;
	}

	@Override
	public void reload()
	{
		Plugin[] pluginsClone = pluginManager.getPlugins().clone();
		this.pluginManager.clearPlugins();
		this.commandMap.clearCommands();
		for (Plugin plugin : pluginsClone)
		{
			getPluginManager().disablePlugin(plugin);
			getWorlds().stream().map(WorldMock.class::cast).forEach(w -> w.clearMetadata(plugin));
			getEntities().forEach(e -> e.clearMetadata(plugin));
			getOnlinePlayers().forEach(p -> p.clearMetadata(plugin));
		}

		// reloadData(); Not implemented.

		// Wait up to 2.5 seconds for plugins to finish async tasks.
		int pollCount = 0;
		while (pollCount < 50 && getScheduler().getActiveWorkers().size() > 0)
		{
			try
			{
				Thread.sleep(50); // TODO: Can we avoid busy waiting?
			}
			catch (InterruptedException ignored)
			{
				Thread.currentThread().interrupt();
			}
			pollCount++;
		}

		getScheduler().saveOverdueTasks();

		List<Plugin> newPlugins = new ArrayList<>(pluginsClone.length);
		for (Plugin oldPlugin : pluginsClone)
		{
			if (!(oldPlugin instanceof JavaPlugin oldJavaPlugin))
				continue;
			// This is a little sketchy, but we have to do it since when initializing
			// plugins we create a subclass of the main class.
			// If we try to then load that subclass as the plugin, it doesn't work, so we
			// need to get the original class to subclass from again.
			@SuppressWarnings("unchecked")
			Class<? extends JavaPlugin> originalClass = (Class<? extends JavaPlugin>) oldJavaPlugin.getClass()
					.getSuperclass();
			// Don't use MockBukkit#load here since we enable later.
			JavaPlugin plugin = getPluginManager().loadPlugin(originalClass, oldJavaPlugin.getDescription(),
					new Object[0]);
			newPlugins.add(plugin);
		}

		newPlugins.stream().sorted(Comparator.comparing(p -> p.getDescription().getLoad()))
				.forEach(plugin -> getPluginManager().enablePlugin(plugin));

		new ServerLoadEvent(ServerLoadEvent.LoadType.RELOAD).callEvent();
	}

	@Override
	public void reloadData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void updateResources()
	{
		// This only sends packets to players in Paper.
	}

	@Override
	public void updateRecipes()
	{
		// This only sends packets to players in Paper.
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
	public @NotNull Map<String, String[]> getCommandAliases()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSpawnRadius()
	{
		return spawnRadius;
	}

	@Override
	public void setSpawnRadius(int spawnRadius)
	{
		this.spawnRadius = spawnRadius;
	}

	/**
	 * @return true if the server should send a preview, false otherwise
	 * @deprecated Chat previews were removed in 1.19.3.
	 */
	@Override
	@Deprecated(forRemoval = true)
	public boolean shouldSendChatPreviews()
	{
		return this.serverConfiguration.shouldSendChatPreviews();
	}

	/**
	 * Sets whether the server should send chat previews.
	 *
	 * @param shouldSendChatPreviews Whether the server should send chat previews.
	 * @see ServerMock#shouldSendChatPreviews()
	 * @deprecated Chat previews were removed in 1.19.3.
	 */
	@Deprecated(forRemoval = true)
	public void setShouldSendChatPreviews(boolean shouldSendChatPreviews)
	{
		this.serverConfiguration.setShouldSendChatPreviews(shouldSendChatPreviews);
	}

	@Override
	public boolean isEnforcingSecureProfiles()
	{
		return this.serverConfiguration.isEnforceSecureProfiles() && this.getOnlineMode();
	}

	/**
	 * Sets whether the server should enforce secure profiles.
	 *
	 * @param enforcingSecureProfiles Whether the server should enforce secure profiles.
	 * @see ServerMock#isEnforcingSecureProfiles()
	 */
	public void setEnforcingSecureProfiles(boolean enforcingSecureProfiles)
	{
		this.serverConfiguration.setEnforceSecureProfiles(enforcingSecureProfiles);
	}

	@Override
	public boolean getOnlineMode()
	{
		return this.serverConfiguration.isOnlineMode();
	}

	/**
	 * Sets whether the server should be in online mode.
	 *
	 * @param onlineMode Whether the server should be in online mode.
	 * @see ServerMock#getOnlineMode()
	 */
	public void setOnlineMode(boolean onlineMode)
	{
		this.serverConfiguration.setOnlineMode(onlineMode);
	}

	@Override
	public boolean getAllowFlight()
	{
		return this.serverConfiguration.isAllowFlight();
	}

	/**
	 * Sets whether the server should allow flight.
	 *
	 * @param allowFlight Whether the server should allow flight.
	 * @see ServerMock#getAllowFlight()
	 */
	public void setAllowFlight(boolean allowFlight)
	{
		this.serverConfiguration.setAllowFlight(allowFlight);
	}

	@Override
	public boolean isHardcore()
	{
		return this.serverConfiguration.isHardcore();
	}

	/**
	 * Sets whether the server should be in hardcore mode.
	 *
	 * @param hardcore Whether the server should be in hardcore mode.
	 * @see ServerMock#isHardcore()
	 */
	public void setHardcore(boolean hardcore)
	{
		this.serverConfiguration.setHardcore(hardcore);
	}

	@Override
	public void shutdown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull OfflinePlayer getOfflinePlayer(@NotNull String name)
	{
		return playerList.getOfflinePlayer(name);
	}

	@Override
	public @NotNull OfflinePlayer getOfflinePlayer(@NotNull UUID id)
	{
		OfflinePlayer player = playerList.getOfflinePlayer(id);

		if (player != null)
		{
			return player;
		}
		else
		{
			return playerFactory.createOfflinePlayer(id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull Set<OfflinePlayer> getBannedPlayers()
	{
		return (Set<OfflinePlayer>) this.getBanList(Type.PROFILE).getEntries().stream().map(banEntry -> {
			return ((BanEntry<PlayerProfile>) banEntry).getBanTarget().getId();
		}).map(uuid -> this.getOfflinePlayer((UUID) uuid)).collect(Collectors.toSet());
	}

	@Override
	public <B extends BanList<E>, E> @NotNull B getBanList(@NotNull BanListType<B> type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull File getWorldContainer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Messenger getMessenger()
	{
		return this.messenger;
	}

	@Override
	@Deprecated
	public @NotNull Merchant createMerchant(String title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxChainedNeighborUpdates()
	{
		return this.serverConfiguration.getMaxChainedNeighbourUpdates();
	}

	/**
	 * Sets the maximum number of chained neighbour updates before skipping additional ones.
	 * Negative values remove the limit.
	 *
	 * @param maxChainedNeighborUpdates The maximum number of chained neighbour updates.
	 * @see ServerMock#getMaxChainedNeighborUpdates()
	 */
	public void setMaxChainedNeighborUpdates(int maxChainedNeighborUpdates)
	{
		this.serverConfiguration.setMaxChainedNeighbourUpdates(maxChainedNeighborUpdates);
	}

	@Override
	@Deprecated
	public int getMonsterSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.MONSTER);
	}

	@Override
	@Deprecated
	public int getAnimalSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.ANIMAL);
	}

	@Override
	@Deprecated
	public int getWaterAnimalSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
	}

	@Override
	@Deprecated
	public int getAmbientSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.AMBIENT);
	}

	@Override
	public boolean isPrimaryThread()
	{
		return this.isOnMainThread();
	}

	@Override
	public @NotNull Component motd()
	{
		return this.motd;
	}

	@Override
	public void motd(@NotNull Component motd)
	{
		Preconditions.checkNotNull(motd, "motd cannot be null");
		this.motd = motd;
	}

	@Override
	@Deprecated
	public @NotNull String getMotd()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.motd);
	}

	@Override
	public void setMotd(@NotNull String motd)
	{
		Preconditions.checkNotNull(motd, "motd cannot be null");
		this.motd = LegacyComponentSerializer.legacySection().deserialize(motd);
	}

	@Override
	public @Nullable Component shutdownMessage()
	{
		return this.serverConfiguration.getShutdownMessage();
	}

	/**
	 * Sets the shutdown message.
	 *
	 * @param shutdownMessage The shutdown message.
	 * @see ServerMock#shutdownMessage()
	 */
	public void setShutdownMessage(@NotNull Component shutdownMessage)
	{
		this.serverConfiguration.setShutdownMessage(shutdownMessage);
	}

	@Override
	@Deprecated
	public String getShutdownMessage()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.serverConfiguration.getShutdownMessage());
	}

	/**
	 * Sets the return value of {@link #getWarningState}.
	 *
	 * @param warningState The {@link WarningState} to set.
	 */
	public void setWarningState(@NotNull WarningState warningState)
	{
		Preconditions.checkNotNull(warningState, "warningState cannot be null");
		this.warningState = warningState;
	}

	@Override
	public @NotNull WarningState getWarningState()
	{
		return this.warningState;
	}

	@Override
	public @NotNull ScoreboardManagerMock getScoreboardManager()
	{
		return scoreboardManager;
	}

	@Override
	public @NotNull Criteria getScoreboardCriteria(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Scoreboard criteria name cannot be null");
		return this.criteria.computeIfAbsent(name, CriteriaMock::new);
	}

	/**
	 * Sets the return value of {@link #getServerIcon()}.
	 *
	 * @param serverIcon The icon to set.
	 */
	public void setServerIcon(CachedServerIconMock serverIcon)
	{
		this.serverIcon = serverIcon;
	}

	@Override
	public CachedServerIconMock getServerIcon()
	{
		return this.serverIcon;
	}

	@Override
	public @NotNull CachedServerIconMock loadServerIcon(@NotNull File file) throws IOException
	{
		Preconditions.checkNotNull(file, "File cannot be null");
		Preconditions.checkArgument(file.isFile(), file + " isn't a file");
		return loadServerIcon(ImageIO.read(file));
	}

	@Override
	public @NotNull CachedServerIconMock loadServerIcon(@NotNull BufferedImage image) throws IOException
	{
		Preconditions.checkNotNull(image, "Image cannot be null");
		Preconditions.checkArgument(image.getWidth() == 64, "Image must be 64 pixels wide");
		Preconditions.checkArgument(image.getHeight() == 64, "Image must be 64 pixels high");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", out);
		String encoded = Base64.getEncoder().encodeToString(out.toByteArray());

		return new CachedServerIconMock(CachedServerIconMock.PNG_BASE64_PREFIX + encoded);
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
	public @NotNull ChunkData createChunkData(@NotNull World world)
	{
		Preconditions.checkNotNull(world, "World cannot be null");
		return new MockChunkData(world);
	}

	@Override

	@Deprecated(forRemoval = true)
	public @NotNull ChunkData createVanillaChunkData(@NotNull World world, int x, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BossBar createBossBar(@NotNull String title, @NotNull BarColor color, @NotNull BarStyle style,
			BarFlag... flags)
	{
		return new BossBarMock(title, color, style, flags);
	}

	@Override
	public @Nullable Entity getEntity(@NotNull UUID uuid)
	{
		Preconditions.checkNotNull(uuid, "uuid cannot be null");

		for (EntityMock entity : entities)
		{
			if (entity.getUniqueId().equals(uuid))
			{
				return entity;
			}
		}
		return null;
	}

	@Override
	public @NotNull double[] getTPS()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull long[] getTickTimes()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getAverageTickTime()
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
	public @NotNull Iterator<Advancement> advancementIterator()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull MockUnsafeValues getUnsafe()
	{
		return unsafe;
	}

	@Override
	public @NotNull BlockData createBlockData(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Must provide material");
		return BlockDataMock.mock(material);
	}

	@Override
	public @NotNull BlockData createBlockData(@NotNull Material material,
			@Nullable Consumer<? super BlockData> consumer)
	{
		BlockData blockData = createBlockData(material);

		if (consumer != null)
		{
			consumer.accept(blockData);
		}

		return blockData;
	}

	@Override
	public @NotNull BlockData createBlockData(String data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockData createBlockData(Material material, String data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * This creates a new Mock {@link Tag} for the {@link Material} class.<br>
	 * Call this in advance before you are gonna access {@link #getTag(String, NamespacedKey, Class)} or any of the
	 * constants defined in {@link Tag}.
	 *
	 * @param key         The {@link NamespacedKey} for this {@link Tag}
	 * @param registryKey The name of the {@link TagRegistry}.
	 * @param materials   {@link Material Materials} which should be covered by this {@link Tag}
	 * @return The newly created {@link Tag}
	 */
	@NotNull
	public Tag<Material> createMaterialTag(@NotNull NamespacedKey key, @NotNull String registryKey,
			@NotNull Material... materials)
	{
		Preconditions.checkNotNull(key, "A NamespacedKey must never be null");

		TagRegistry registry = materialTags.get(registryKey);
		TagWrapperMock tag = new TagWrapperMock(registry, key);
		registry.getTags().put(key, tag);
		return tag;
	}

	/**
	 * Adds a tag registry.
	 *
	 * @param registry The registry to add.
	 */
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

		// Per definition this method should return null if the given tag does not
		// exist.
		return null;
	}

	@Override
	public LootTable getLootTable(NamespacedKey key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack createExplorerMap(World world, Location location, StructureType structureType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack createExplorerMap(World world, Location location, StructureType structureType, int radius,
			boolean findUnexplored)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack createExplorerMap(@NotNull World world, @NotNull Location location,
			@NotNull org.bukkit.generator.structure.StructureType structureType, @NotNull MapCursor.Type mapIcon,
			int radius, boolean findUnexplored)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull KeyedBossBar createBossBar(@NotNull NamespacedKey key, @NotNull String title,
			@NotNull BarColor color, @NotNull BarStyle style, BarFlag... flags)
	{
		Preconditions.checkNotNull(key, "A NamespacedKey must never be null");
		KeyedBossBarMock bar = new KeyedBossBarMock(key, title, color, style, flags);
		bossBars.put(key, bar);
		return bar;
	}

	@Override
	public @NotNull Iterator<KeyedBossBar> getBossBars()
	{
		return bossBars.values().stream().map(bossbar -> (KeyedBossBar) bossbar).iterator();
	}

	@Override
	public KeyedBossBar getBossBar(NamespacedKey key)
	{
		Preconditions.checkNotNull(key, "A NamespacedKey must never be null");
		return bossBars.get(key);
	}

	@Override
	public boolean removeBossBar(NamespacedKey key)
	{
		Preconditions.checkNotNull(key, "A NamespacedKey must never be null");
		return bossBars.remove(key, bossBars.get(key));
	}

	@Override
	public @NotNull List<Entity> selectEntities(CommandSender sender, String selector)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public StructureManager getStructureManager()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	@SuppressWarnings("unchecked")
	public @Nullable <T extends Keyed> Registry<T> getRegistry(@NotNull Class<T> tClass)
	{
		if (!registry.containsKey(tClass))
		{
			registry.put(tClass, RegistryMock.createRegistry(tClass));
		}
		return (Registry<T>) registry.get(tClass);
	}

	@Override
	@Deprecated
	public MapViewMock getMap(int id)
	{
		return mapViews.get(id);
	}

	@Override
	public <T extends Keyed> @NotNull Iterable<Tag<T>> getTags(String registry, Class<T> clazz)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getTicksPerWaterSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
	}

	@Override
	@Deprecated
	public int getTicksPerAmbientSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.AMBIENT);
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
		return this.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
	}

	@Override
	@Deprecated
	public int getTicksPerWaterUndergroundCreatureSpawns()
	{
		return this.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE);
	}

	@Override
	@Deprecated
	public int getWaterAmbientSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
	}

	@Override
	@Deprecated
	public int getWaterUndergroundCreatureSpawnLimit()
	{
		return this.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE);
	}

	@Override
	public int getMaxWorldSize()
	{
		return this.serverConfiguration.getMaxWorldSize();
	}

	/**
	 * Set the maximum world size
	 *
	 * @param maxWorldSize The maximum world size
	 * @see ServerMock#getMaxWorldSize()
	 */
	public void setMaxWorldSize(int maxWorldSize)
	{
		this.serverConfiguration.setMaxWorldSize(maxWorldSize);
	}

	@Override
	public int getSimulationDistance()
	{
		return this.serverConfiguration.getSimulationDistance();
	}

	/**
	 * Set the simulation distance
	 *
	 * @param simulationDistance The simulation distance
	 * @see ServerMock#getSimulationDistance()
	 */
	public void setSimulationDistance(int simulationDistance)
	{
		this.serverConfiguration.setSimulationDistance(simulationDistance);
	}

	@Override
	public boolean getHideOnlinePlayers()
	{
		return this.serverConfiguration.isHideOnlinePlayers();
	}

	/**
	 * Set whether to hide online players
	 *
	 * @param hideOnlinePlayers Whether to hide online players
	 * @see ServerMock#getHideOnlinePlayers()
	 */
	public void setHideOnlinePlayers(boolean hideOnlinePlayers)
	{
		this.serverConfiguration.setHideOnlinePlayers(hideOnlinePlayers);
	}

	@Override
	public Server.@NotNull Spigot spigot()
	{
		return this;
	}

	@Override
	public void reloadPermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean reloadCommandAliases()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean suggestPlayerNamesWhenNullTabCompletions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getPermissionMessage()
	{
		return unsafe.legacyComponentSerializer().serialize(NO_PERMISSION);
	}

	@Override
	public @NotNull Component permissionMessage()
	{
		return NO_PERMISSION;
	}

	@Override
	public @NotNull PlayerProfileMock createProfile(@NotNull UUID uuid)
	{
		return createProfile(uuid, null);
	}

	@Override
	public @NotNull PlayerProfileMock createProfile(@NotNull String name)
	{
		return createProfile(null, name);
	}

	@Override
	public @NotNull PlayerProfileMock createProfile(@Nullable UUID uuid, @Nullable String name)
	{
		return new PlayerProfileMock(name, uuid);
	}

	@Override
	public @NotNull PlayerProfileMock createProfileExact(@Nullable UUID uuid, @Nullable String name)
	{
		return new PlayerProfileMock(name, uuid);
	}

	@Override
	public int getCurrentTick()
	{
		return (int) getScheduler().getCurrentTick();
	}

	@Override
	public boolean isStopping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull MobGoals getMobGoals()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull DatapackManager getDatapackManager()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public YamlConfiguration getConfig()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void broadcast(@NotNull BaseComponent component)
	{
		for (Player player : getOnlinePlayers())
		{
			player.spigot().sendMessage(component);
		}
	}

	@Override
	@Deprecated
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

	@Override
	public int getTicksPerSpawns(@NotNull SpawnCategory spawnCategory)
	{
		Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
		Preconditions.checkArgument(spawnCategory != SpawnCategory.MISC, "SpawnCategory.%s are not supported",
				spawnCategory);

		return (int) this.serverConfiguration.getTicksPerSpawn().getLong(spawnCategory);
	}

	@Override
	@Deprecated
	public @NotNull PlayerProfileMock createPlayerProfile(@Nullable UUID uniqueId, @Nullable String name)
	{
		return new PlayerProfileMock(name, uniqueId);
	}

	@Override
	@Deprecated
	public @NotNull PlayerProfileMock createPlayerProfile(@NotNull UUID uniqueId)
	{
		return createPlayerProfile(uniqueId, null);
	}

	@Override
	@Deprecated
	public @NotNull PlayerProfileMock createPlayerProfile(@NotNull String name)
	{
		return createPlayerProfile(null, name);
	}

	@Override
	public int getSpawnLimit(@NotNull SpawnCategory spawnCategory)
	{
		Preconditions.checkArgument(spawnCategory != null, "SpawnCategory cannot be null");
		Preconditions.checkArgument(spawnCategory != SpawnCategory.MISC, "SpawnCategory.%s are not supported",
				spawnCategory);

		return this.serverConfiguration.getSpawnLimits().getOrDefault(spawnCategory, -1);
	}

	@Override
	public @NotNull PotionBrewer getPotionBrewer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull RegionScheduler getRegionScheduler()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull AsyncScheduler getAsyncScheduler()
	{
		return this.foliaAsyncScheduler;
	}

	@Override
	public @NotNull GlobalRegionScheduler getGlobalRegionScheduler()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull World world, @NotNull Position position)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull World world, @NotNull Position position, int squareRadiusChunks)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull Location location)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull Location location, int squareRadiusChunks)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull World world, int chunkX, int chunkZ)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull World world, int chunkX, int chunkZ, int squareRadiusChunks)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOwnedByCurrentRegion(@NotNull Entity entity)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull File getPluginsFolder()
	{
		try
		{
			return getPluginManager().getParentTemporaryDirectory();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public @NotNull Iterable<? extends Audience> audiences()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Exposes the {@link ServerConfiguration} of this {@link ServerMock}.
	 *
	 * @return The {@link ServerConfiguration} of this {@link ServerMock}.
	 */
	public @NotNull ServerConfiguration getServerConfiguration()
	{
		return this.serverConfiguration;
	}

}
