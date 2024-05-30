package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.configuration.ServerConfiguration;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.entity.SimpleEntityMock;
import be.seeseemelk.mockbukkit.inventory.AnvilInventoryMock;
import be.seeseemelk.mockbukkit.inventory.BarrelInventoryMock;
import be.seeseemelk.mockbukkit.inventory.BeaconInventoryMock;
import be.seeseemelk.mockbukkit.inventory.BrewerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.CartographyInventoryMock;
import be.seeseemelk.mockbukkit.inventory.DispenserInventoryMock;
import be.seeseemelk.mockbukkit.inventory.DropperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.EnchantingInventoryMock;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.FurnaceInventoryMock;
import be.seeseemelk.mockbukkit.inventory.GrindstoneInventoryMock;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.LecternInventoryMock;
import be.seeseemelk.mockbukkit.inventory.LoomInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.ShulkerBoxInventoryMock;
import be.seeseemelk.mockbukkit.inventory.SmithingInventoryMock;
import be.seeseemelk.mockbukkit.inventory.StonecutterInventoryMock;
import be.seeseemelk.mockbukkit.inventory.WorkbenchInventoryMock;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import com.destroystokyo.paper.event.server.WhitelistToggleEvent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.net.InetAddresses;
import io.papermc.paper.world.structure.ConfiguredStructure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Art;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.loot.LootTables;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ServerMockTest
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
	void class_NumberOfPlayers_Zero()
	{
		assertEquals(0, server.getOnlinePlayers().size());
	}

	@Test
	void createWorld_WorldCreator()
	{
		WorldCreator worldCreator = new WorldCreator("test").seed(12345).type(WorldType.FLAT)
				.environment(World.Environment.NORMAL);
		World world = server.createWorld(worldCreator);

		assertEquals(1, server.getWorlds().size());
		assertEquals("test", world.getName());
		assertEquals(12345, world.getSeed());
		assertEquals(WorldType.FLAT, world.getWorldType());
		assertEquals(World.Environment.NORMAL, world.getEnvironment());
	}

	@Test
	void addPlayer_TwoPlayers_SizeIsTwo()
	{
		PlayerMockFactory factory = new PlayerMockFactory(server);
		PlayerMock player1 = factory.createRandomPlayer();
		PlayerMock player2 = factory.createRandomPlayer();

		server.addPlayer(player1);
		assertEquals(1, server.getOnlinePlayers().size());
		server.addPlayer(player2);
		assertEquals(2, server.getOnlinePlayers().size());

		assertEquals(player1, server.getPlayer(0));
		assertEquals(player2, server.getPlayer(1));

		Set<EntityMock> entities = server.getEntities();
		assertTrue(entities.contains(player1), "Player 1 was not registered");
		assertTrue(entities.contains(player2), "Player 2 was not registered");
	}

	@Test
	void addPlayers_None_TwoUniquePlayers()
	{
		PlayerMock playerA = server.addPlayer();
		PlayerMock playerB = server.addPlayer();
		PlayerMock player1 = server.getPlayer(0);
		PlayerMock player2 = server.getPlayer(1);
		assertNotNull(player1);
		assertNotNull(player2);
		assertEquals(playerA, player1);
		assertEquals(playerB, player2);
		assertNotEquals(player1, player2);
	}

	@Test
	void addPlayer_Calls_AsyncPreLoginEvent()
	{
		PlayerMock player = server.addPlayer();
		server.getPluginManager().assertEventFired(AsyncPlayerPreLoginEvent.class);
	}

	@Test
	void addPlayer_Calls_PlayerJoinEvent()
	{
		PlayerMock player = server.addPlayer();
		server.getPluginManager().assertEventFired(PlayerJoinEvent.class);
	}

	@Test
	void addPlayer_Calls_PlayerLoginEvent()
	{
		PlayerMock player = server.addPlayer();
		server.getPluginManager().assertEventFired(PlayerLoginEvent.class);
	}

	@Test
	void addPlayer_Calls_PlayerSpawnLocationEvent()
	{
		PlayerMock player = server.addPlayer();
		server.getPluginManager().assertEventFired(PlayerSpawnLocationEvent.class);
	}

	@Test
	void setPlayers_Two_TwoUniquePlayers()
	{
		server.setPlayers(2);
		PlayerMock player1 = server.getPlayer(0);
		PlayerMock player2 = server.getPlayer(1);
		assertNotNull(player1);
		assertNotNull(player2);
		assertNotEquals(player1, player2);
	}

	@Test
	void getPlayers_Negative_ArrayIndexOutOfBoundsException()
	{
		server.setPlayers(2);
		assertThrows(IndexOutOfBoundsException.class, () -> server.getPlayer(-1));
	}

	@Test
	void getPlayers_LargerThanNumberOfPlayers_ArrayIndexOutOfBoundsException()
	{
		server.setPlayers(2);
		assertThrows(IndexOutOfBoundsException.class, () -> server.getPlayer(2));
	}

	@Test
	void getVersion_NotNull()
	{
		assertNotNull(server.getVersion());
	}

	@Test
	void getVersion_CorrectPattern()
	{
		assertTrue(server.getVersion().matches("MockBukkit \\(MC: (\\d)\\.(\\d+)\\.?(\\d+?)?\\)"));
	}

	@Test
	void getBukkitVersion_NotNull()
	{
		assertNotNull(server.getBukkitVersion());
	}

	@Test
	void getBukkitVersion_CorrectPattern()
	{
		assertTrue(server.getBukkitVersion().matches("1\\.[0-9]+(\\.[0-9]+)?-.*SNAPSHOT.*"));
	}

	@Test
	void getMinecraftVersion_NotNull()
	{
		assertNotNull(server.getMinecraftVersion());
	}

	@Test
	void getMinecraftVersion_CorrectPattern()
	{
		assertTrue(server.getMinecraftVersion().matches("1\\.[0-9]+(\\.[0-9]+)?"));
	}

	@Test
	void getName_NotNull()
	{
		assertNotNull(server.getName());
	}

	@Test
	void getPlayers_AllSame()
	{
		server.setPlayers(2);
		PlayerMock player1 = server.getPlayer(0);
		PlayerMock player2 = server.getPlayer(1);
		Iterator<? extends Player> players = server.getOnlinePlayers().iterator();
		assertEquals(player1, players.next());
		assertEquals(player2, players.next());
		assertFalse(players.hasNext());
	}

	@Test
	void getOfflinePlayers_CorrectArraySize()
	{
		server.setPlayers(1);
		server.setOfflinePlayers(2);
		assertEquals(3, server.getOfflinePlayers().length);
	}

	@Test
	void getOfflinePlayerByUnknownId_returnsOfflinePlayerWithGivenId()
	{
		UUID id = UUID.randomUUID();
		OfflinePlayer offlinePlayer = server.getOfflinePlayer(id);
		assertEquals(id, offlinePlayer.getUniqueId());
	}

	@ParameterizedTest
	@ValueSource(strings =
	{ "testcommand", "tc", "othercommand" })
	void testPluginCommand(@NotNull String cmd)
	{
		MockBukkit.load(TestPlugin.class);
		assertNotNull(server.getPluginCommand(cmd));
	}

	@Test
	void getPluginCommand_Unknown_Null()
	{
		MockBukkit.load(TestPlugin.class);
		assertNull(server.getPluginCommand("notknown"));
	}

	@Test
	void executeCommand_PlayerAndTrueReturnValue_Succeeds()
	{
		server.setPlayers(1);
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = true;

		Command command = server.getPluginCommand("testcommand");
		CommandResult result = server.executePlayer(command, "a", "b");
		result.assertSucceeded();
		assertEquals(server.getPlayer(0), plugin.commandSender);
		assertEquals(command, plugin.command);

		assertEquals(2, plugin.commandArguments.length);
		assertEquals("a", plugin.commandArguments[0]);
		assertEquals("b", plugin.commandArguments[1]);
	}

	@Test
	void executeCommand_ConsoleAndFalseReturnValue_Fails()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = false;

		Command command = server.getPluginCommand("testcommand");
		CommandResult result = server.executeConsole(command, "a", "b");
		result.assertFailed();
		assertEquals(server.getConsoleSender(), plugin.commandSender);
		assertEquals(command, plugin.command);

		assertEquals(2, plugin.commandArguments.length);
		assertEquals("a", plugin.commandArguments[0]);
		assertEquals("b", plugin.commandArguments[1]);
	}

	@Test
	void executeCommand_CommandAsStringAndTrueReturnValue_Succeeds()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = true;

		CommandResult result = server.executeConsole("testcommand");
		result.assertSucceeded();
	}

	@Test
	void getConsoleSender_NotNull()
	{
		assertNotNull(server.getConsoleSender());
	}

	@Test
	void getItemFactory_NotNull()
	{
		assertNotNull(server.getItemFactory());
	}

	@Test
	void addSimpleWorld_Name_WorldWithNameAdded()
	{
		WorldMock world = server.addSimpleWorld("MyWorld");
		assertEquals(1, server.getWorlds().size());
		assertSame(world, server.getWorlds().get(0));
		assertSame(world, server.getWorld(world.getName()));
		assertSame(world, server.getWorld(world.getUID()));
	}

	@Test
	void getScheduler_Default_NotNull()
	{
		assertNotNull(server.getScheduler());
	}

	@Test
	void broadcastMessage_TwoPlayers_BothReceivedMessage()
	{
		PlayerMock playerA = server.addPlayer();
		PlayerMock playerB = server.addPlayer();
		server.broadcastMessage("Hello world");
		playerA.assertSaid("Hello world");
		playerB.assertSaid("Hello world");
	}

	@Test
	void addRecipe_AddsRecipe_ReturnsTrue()
	{
		TestRecipe recipe1 = new TestRecipe();
		TestRecipe recipe2 = new TestRecipe();
		server.addRecipe(recipe1);
		server.addRecipe(recipe2);
		Iterator<Recipe> recipes = server.recipeIterator();
		assertSame(recipe1, recipes.next());
		assertSame(recipe2, recipes.next());
		assertFalse(recipes.hasNext());
	}

	@Test
	void clearRecipes_SomeRecipes_AllRecipesRemoved()
	{
		TestRecipe recipe = new TestRecipe();
		server.addRecipe(recipe);
		assumeTrue(server.recipeIterator().hasNext());
		server.clearRecipes();
		assertFalse(server.recipeIterator().hasNext());
	}

	@Test
	void getRecipesFor_ManyRecipes_OnlyCorrectRecipes()
	{
		TestRecipe recipe1 = new TestRecipe(new ItemStack(Material.STONE));
		TestRecipe recipe2 = new TestRecipe(new ItemStack(Material.APPLE));
		server.addRecipe(recipe1);
		server.addRecipe(recipe2);
		List<Recipe> recipes = server.getRecipesFor(new ItemStack(Material.APPLE));
		assertEquals(1, recipes.size());
		assertSame(recipe2, recipes.get(0));
	}

	@Test
	void getRecipesFor_IgnoresAmount()
	{
		TestRecipe recipe = new TestRecipe(new ItemStack(Material.IRON_NUGGET));
		server.addRecipe(recipe);

		List<Recipe> recipes = server.getRecipesFor(new ItemStack(Material.IRON_NUGGET, 1));
		List<Recipe> recipes2 = server.getRecipesFor(new ItemStack(Material.IRON_NUGGET, 10));
		assertEquals(recipes, recipes2);
	}

	@Test
	void getDataFolder_CleanEnvironment_CreatesTemporaryDataDirectory() throws IOException
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		File folder = plugin.getDataFolder();
		assertNotNull(folder);
		assertTrue(folder.isDirectory());
		File file = new File(folder, "data.txt");
		assertFalse(file.exists());
		file.createNewFile();
		assertTrue(file.exists());
		MockBukkit.unmock();
		MockBukkit.mock();
		assertFalse(file.exists());
	}

	@Test
	void createInventory_WithSize_CreatesInventory()
	{
		PlayerMock player = server.addPlayer();
		InventoryMock inventory = server.createInventory(player, 9, "title");
		assertEquals(9, inventory.getSize());
		assertSame(player, inventory.getHolder());
	}

	@Test
	void createInventory_ChestInventoryWithoutSize_CreatesInventoryWithThreeLines()
	{
		InventoryMock inventory = server.createInventory(null, InventoryType.CHEST);
		assertEquals(9 * 3, inventory.getSize());
	}

	@Test
	void performCommand_PerformsCommand()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = true;
		Player player = server.addPlayer();
		assertTrue(server.dispatchCommand(player, "mockcommand argA argB"));
		assertEquals("argA", plugin.commandArguments[0]);
		assertEquals("argB", plugin.commandArguments[1]);
		assertSame(player, plugin.commandSender);
	}

	@Test
	void getEntities_NoEntities_EmptySet()
	{
		assertTrue(server.getEntities().isEmpty(), "Entities set was not empty");
	}

	@Test
	void getEntities_TwoEntitiesRegistered_SetContainsEntities()
	{
		EntityMock entity1 = new SimpleEntityMock(server);
		EntityMock entity2 = new SimpleEntityMock(server);
		server.registerEntity(entity1);
		server.registerEntity(entity2);
		Set<EntityMock> entities = server.getEntities();
		assertTrue(entities.contains(entity1), "Set did not contain first entity");
		assertTrue(entities.contains(entity2), "Set did not contain second entity");
	}

	@ParameterizedTest
	@CsvSource(
	{ "player, player", "player, PLAYER", "player_other, player", })
	void getPlayer_NameAndPlayerExists_PlayerFound(@NotNull String actual, @NotNull String expected)
	{
		PlayerMock player = new PlayerMock(server, actual);
		server.addPlayer(player);
		assertSame(player, server.getPlayer(expected));
	}

	@Test
	void getPlayer_UUIDAndPlayerExists_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertSame(player, server.getPlayer(player.getUniqueId()));
	}

	@Test
	void getPlayer_PlayerNameIncorrect_PlayerNotFound()
	{
		PlayerMock player = new PlayerMock(server, "player_other");
		server.addPlayer(player);
		assertNull(server.getPlayer("other_player"));
	}

	@Test
	void getPlayer_PlayerNameCasingIncorrect_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertSame(player, server.getPlayer("PLAYER"));
	}

	@Test
	void getPlayerExact_CasingMatches_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertSame(player, server.getPlayerExact("player"));
	}

	@Test
	void getPlayerExact_CasingDoesNotMatch_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertNotNull(server.getPlayerExact("PLAYER"));
	}

	@Test
	void getPlayerExact_PlayerNameIncorrect_PlayerNotFound()
	{
		PlayerMock player = new PlayerMock(server, "player_other");
		server.addPlayer(player);
		assertNull(server.getPlayerExact("player"));
	}

	@Test
	void getOperators_OneOperator()
	{
		PlayerMock player = new PlayerMock(server, "operator");
		server.addPlayer(player);
		player.setOp(true);

		assertTrue(server.getOperators().contains(player));
		assertEquals(1, server.getOperators().size());
	}

	@Test
	void getScoreboardManager_NotNull()
	{
		ScoreboardManager manager = server.getScoreboardManager();
		assertNotNull(manager);
	}

	@Test
	void matchPlayer_NoMatchingPlayers_EmptyList()
	{
		server.addPlayer("Player");
		List<Player> players = server.matchPlayer("Others");
		assertEquals(0, players.size(), "Player list was not empty");
	}

	@Test
	void matchPlayer_SomeMatchingPlayers_ListHasPlayer()
	{
		PlayerMock playerA = server.addPlayer("PlayerA");
		PlayerMock playerAB = server.addPlayer("PlayerAB");
		server.addPlayer("PlayerB");
		List<Player> players = server.matchPlayer("PlayerA");
		assertTrue(players.contains(playerA));
		assertTrue(players.contains(playerAB));
	}

	@Test
	void testOfflinePlayerJoin()
	{
		OfflinePlayerMock offlinePlayer = new OfflinePlayerMock("IAmOffline");
		assertFalse(server.getOfflinePlayer(offlinePlayer.getUniqueId()) instanceof PlayerMock);

		PlayerMock onlinePlayer = offlinePlayer.join(server);

		assertTrue(offlinePlayer.isOnline());
		assertTrue(onlinePlayer.isOnline());

		// Assert that this is still the same Player (as far as name and uuid are
		// concerned)
		assertEquals(offlinePlayer.getName(), onlinePlayer.getName());
		assertEquals(offlinePlayer.getUniqueId(), onlinePlayer.getUniqueId());

		// Assert that the PlayerMock takes priority over the OfflinePlayerMock
		assertTrue(server.getOfflinePlayer(offlinePlayer.getUniqueId()) instanceof PlayerMock);
	}

	@Test
	void testDefaultPotionEffects()
	{
		assertEquals(33, PotionEffectType.values().length);

		for (PotionEffectType type : PotionEffectType.values())
		{
			assertNotNull(type);
		}
	}

	@Test
	void testSetSpawnRadius()
	{
		server.setSpawnRadius(51);
		assertEquals(51, server.getSpawnRadius());
	}

	@Test
	void testGetEntity()
	{
		EntityMock entity = new SimpleEntityMock(server);
		UUID uuid = entity.getUniqueId();
		server.registerEntity(entity);
		assertNotNull(server.getEntity(uuid));
	}

	@Test
	void testCreateBlockData()
	{
		BlockData blockData = server.createBlockData(Material.STONE);
		assertEquals(Material.STONE, blockData.getMaterial());
	}

	@Test
	void testWarningState()
	{
		assertEquals(Warning.WarningState.DEFAULT, server.getWarningState());
		server.setWarningState(Warning.WarningState.ON);
		assertEquals(Warning.WarningState.ON, server.getWarningState());
	}

	@Test
	@SuppressWarnings("UnstableApiUsage")
	void testSendPluginMessage()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		server.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("MockBukkit");
		assertDoesNotThrow(() -> server.sendPluginMessage(plugin, "BungeeCord", out.toByteArray()));
	}

	@Test
	void testGetPlayerUniqueID()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		UUID uuid = player.getUniqueId();
		assertEquals(uuid, server.getPlayerUniqueId(player.getName()));
	}

	@Test
	void testGetPlayerUniqueID_OfflineMode()
	{
		OfflinePlayer player = new OfflinePlayerMock("OfflinePlayer");
		assertEquals(player.getUniqueId(), server.getPlayerUniqueId(player.getName()));
	}

	@Test
	void testSetMaxPlayers()
	{
		server.setMaxPlayers(69420);
		assertEquals(69420, server.getMaxPlayers());
	}

	@Test
	void testBroadCastMessageWithComponent()
	{
		PlayerMock playerA = server.addPlayer();
		PlayerMock playerB = server.addPlayer();

		Component component = Component.text("Hello");

		server.broadcast(component);

		playerA.assertSaid(PlainTextComponentSerializer.plainText().serialize(component));
		playerB.assertSaid(PlainTextComponentSerializer.plainText().serialize(component));
	}

	@Test
	void reload()
	{
		assertDoesNotThrow(server::reload);
	}

	@Test
	void reload_ServerLoadEvent_IsCalled()
	{
		server.reload();
		server.getPluginManager().assertEventFired(ServerLoadEvent.class,
				(e) -> e.getType() == ServerLoadEvent.LoadType.RELOAD);
	}

	@Test
	void reload_ReEnablesPlugins()
	{
		Plugin plugin1 = MockBukkit.createMockPlugin("plugin1");
		Plugin plugin2 = MockBukkit.createMockPlugin("plugin2");

		server.reload();

		assertEquals(2, server.getPluginManager().getPlugins().length);
		assertTrue(server.getPluginManager().getPlugin("plugin1").isEnabled());
		assertTrue(server.getPluginManager().getPlugin("plugin2").isEnabled());
		// A new instance of the plugin should be created.
		assertFalse(server.getPluginManager().isPluginEnabled(plugin1));
		assertFalse(server.getPluginManager().isPluginEnabled(plugin2));
	}

	@Test
	void testGetPlayerList()
	{
		PlayerMock playerA = server.addPlayer();
		PlayerMock playerB = server.addPlayer();

		assertTrue(server.getPlayerList().getOnlinePlayers().contains(playerA));
		assertTrue(server.getPlayerList().getOnlinePlayers().contains(playerB));
	}

	@Test
	void testPlayerListDisconnectPlayer()
	{
		MockPlayerList playerList = server.getPlayerList();
		PlayerMock playerA = server.addPlayer();
		playerList.disconnectPlayer(playerA);

		assertFalse(playerList.getOnlinePlayers().contains(playerA));
	}

	@Test
	void createProfile_NameOnly()
	{
		PlayerProfileMock profile = server.createProfile("Test");

		assertEquals("Test", profile.getName());
	}

	@Test
	void createProfile_UuidOnly()
	{
		UUID uuid = UUID.fromString("b9d9f8f9-f8d9-4f9d-9f8f-9f8f8f8f8f8");
		PlayerProfileMock profile = server.createProfile(uuid);

		assertEquals(uuid, profile.getUniqueId());
	}

	@Test
	void createProfile_NameUuid()
	{
		UUID uuid = UUID.fromString("b9d9f8f9-f8d9-4f9d-9f8f-9f8f8f8f8f8");
		PlayerProfileMock profile = server.createProfile(uuid, "Test");

		assertEquals("Test", profile.getName());
		assertEquals(uuid, profile.getUniqueId());
	}

	@Test
	void getCurrentTick_CorrectTick()
	{
		assertEquals(0, server.getCurrentTick());
		server.getScheduler().performOneTick();
		assertEquals(1, server.getCurrentTick());
		server.getScheduler().performTicks(10);
		assertEquals(11, server.getCurrentTick());
	}

	@Test
	void createMap_IdIncrements()
	{
		WorldMock world = new WorldMock();
		assertEquals(1, server.createMap(world).getId());
		assertEquals(2, server.createMap(world).getId());
		assertEquals(3, server.createMap(world).getId());
	}

	@Test
	void getMap_ValidId_ReturnsMap()
	{
		WorldMock world = new WorldMock();
		int id = server.createMap(world).getId();

		assertNotNull(server.getMap(id));
		assertEquals(id, server.getMap(id).getId());
	}

	@Test
	void createMap_CallsMapInitEvent()
	{
		MapView mapView = server.createMap(new WorldMock());

		server.getPluginManager().assertEventFired(MapInitializeEvent.class, (e) -> e.getMap().equals(mapView));
	}

	@Test
	void getCommandTabComplete_ReturnsExpectedResults()
	{
		MockBukkit.load(TestPlugin.class);
		Player player = server.addPlayer();
		assertEquals(Arrays.asList("Tab", "Complete", "Results"), server.getCommandTabComplete(player, "mockcommand "));
		assertEquals(Arrays.asList("Other", "Results"), server.getCommandTabComplete(player, "mockcommand argA "));
	}

	@Test
	void testHasWhiteListDefault()
	{
		assertFalse(server.hasWhitelist());
	}

	@Test
	void testSetWhiteList()
	{
		server.setWhitelist(true);
		assertTrue(server.hasWhitelist());
		server.getPluginManager().assertEventFired(WhitelistToggleEvent.class, WhitelistToggleEvent::isEnabled);
	}

	@Test
	void testIsWhiteListEnforcedDefault()
	{
		assertFalse(server.isWhitelistEnforced());
	}

	@Test
	void testSetWhiteListEnforced()
	{
		server.setWhitelistEnforced(true);
		assertTrue(server.isWhitelistEnforced());
	}

	@Test
	void testReloadWhiteList()
	{
		assertDoesNotThrow(() -> server.reloadWhitelist());
	}

	@Test
	void testReloadWhiteListWithEnforcedWhiteList()
	{
		PlayerMock playerMock = server.addPlayer();

		server.setWhitelist(true);
		server.setWhitelistEnforced(true);

		server.reloadWhitelist();

		assertFalse(server.getOnlinePlayers().contains(playerMock));
		server.getPluginManager().assertEventFired(PlayerKickEvent.class);
	}

	@Test
	void tstReloadWhiteListWithNotEnforcedWhiteList()
	{
		PlayerMock playerMock = server.addPlayer();

		server.setWhitelist(true);
		server.setWhitelistEnforced(false);

		server.reloadWhitelist();

		assertTrue(server.getOnlinePlayers().contains(playerMock));
		server.getPluginManager().assertEventNotFired(PlayerKickEvent.class);
	}

	@Test
	void testReloadWhiteListWithNotEnforcedWhiteListAndPlayerIsWhitelisted()
	{
		PlayerMock playerMock = server.addPlayer();
		playerMock.setWhitelisted(true);
		server.setWhitelist(true);
		server.setWhitelistEnforced(true);

		server.reloadWhitelist();

		assertTrue(server.getOnlinePlayers().contains(playerMock));
		server.getPluginManager().assertEventNotFired(PlayerKickEvent.class);
	}

	@Test
	void testReloadWithListWithoutWhitelistEnabled()
	{
		PlayerMock playerMock = server.addPlayer();
		playerMock.setWhitelisted(true);
		server.setWhitelist(false);
		server.setWhitelistEnforced(true);

		server.reloadWhitelist();

		assertTrue(server.getOnlinePlayers().contains(playerMock));
		server.getPluginManager().assertEventNotFired(PlayerKickEvent.class);
	}

	@Test
	void testAddPlayerWithWhitelistEnabled()
	{
		server.setWhitelist(true);

		PlayerMock playerMock = new PlayerMock(server, "Player", UUID.randomUUID());
		playerMock.setWhitelisted(true);

		server.addPlayer(playerMock);

		assertTrue(server.getOnlinePlayers().contains(playerMock));
		server.getPluginManager().assertEventNotFired(PlayerKickEvent.class);
	}

	@Test
	void testAddPlayerWithWhitelistEnabledAndNotWhitelisted()
	{
		server.setWhitelist(true);

		PlayerMock player = server.addPlayer();

		assertFalse(server.getOnlinePlayers().contains(player));
		server.getPluginManager().assertEventFired(PlayerConnectionCloseEvent.class);
	}

	@Test
	void testGetBannedPlayersDefault()
	{
		assertEquals(0, server.getBannedPlayers().size());
	}

	@Test
	void testGetBannedPlayers()
	{
		PlayerMock player = server.addPlayer();
		player.banPlayer("test");

		assertEquals(1, server.getBannedPlayers().size());
		assertTrue(server.getBannedPlayers().contains(player));
	}

	@Test
	void getPermissionMessage_NotNull()
	{
		assertNotNull(server.getPermissionMessage());
	}

	@Test
	void permissionMessage_NotNull()
	{
		assertNotNull(server.permissionMessage());
	}

	@Test
	void loadServerIcon_NullFile_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> server.loadServerIcon((File) null));
	}

	@Test
	void loadServerIcon_NullImage_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> server.loadServerIcon((BufferedImage) null));
	}

	@Test
	void loadServerIcon_WrongWidth_ThrowsException()
	{
		BufferedImage image63 = new BufferedImage(63, 64, BufferedImage.TYPE_INT_RGB);
		BufferedImage image65 = new BufferedImage(65, 64, BufferedImage.TYPE_INT_RGB);
		assertThrows(IllegalArgumentException.class, () -> server.loadServerIcon(image63));
		assertThrows(IllegalArgumentException.class, () -> server.loadServerIcon(image65));
	}

	@Test
	void loadServerIcon_WrongHeight_ThrowsException()
	{
		BufferedImage image63 = new BufferedImage(64, 63, BufferedImage.TYPE_INT_RGB);
		BufferedImage image65 = new BufferedImage(64, 65, BufferedImage.TYPE_INT_RGB);
		assertThrows(IllegalArgumentException.class, () -> server.loadServerIcon(image63));
		assertThrows(IllegalArgumentException.class, () -> server.loadServerIcon(image65));
	}

	@Test
	void loadServerIcon_CorrectSize()
	{
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
		assertDoesNotThrow(() -> server.loadServerIcon(image));
	}

	@Test
	void loadServerIcon_CorrectData() throws IOException
	{
		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawOval(0, 0, 64, 64);
		g.dispose();

		CachedServerIconMock icon = server.loadServerIcon(image);
		byte[] decodedBase64 = Base64.getDecoder()
				.decode(icon.getData().replace(CachedServerIconMock.PNG_BASE64_PREFIX, ""));
		BufferedImage decodedImage = ImageIO.read(new ByteArrayInputStream(decodedBase64));

		for (int x = 0; x < 64; x++)
		{
			for (int y = 0; y < 64; y++)
			{
				assertEquals(image.getRGB(x, y), decodedImage.getRGB(x, y));
			}
		}
	}

	@Test
	void testGetPortDefault()
	{
		assertEquals(25565, server.getPort());
	}

	@Test
	void testSetPort()
	{
		server.setPort(2212);
		assertEquals(2212, server.getPort());
	}

	@Test
	void testGetViewDistanceDefault()
	{
		assertEquals(10, server.getViewDistance());
	}

	@Test
	void testSetViewDistance()
	{
		server.setViewDistance(2);
		assertEquals(2, server.getViewDistance());
	}

	@Test
	void testGetIpDefault()
	{
		assertEquals("", server.getIp());
	}

	@Test
	void testSetIp()
	{
		server.setIp("::1");
		assertEquals("::1", server.getIp());
	}

	@Test
	void testGetWorldTypeDefault()
	{
		assertEquals(ServerConfiguration.LevelType.DEFAULT.getKey(), server.getWorldType());
	}

	@Test
	void testSetLevelType()
	{
		server.setWorldType(ServerConfiguration.LevelType.FLAT);
		assertEquals(ServerConfiguration.LevelType.FLAT.getKey(), server.getWorldType());
	}

	@Test
	void testIsGenerateStructuresDefault()
	{
		assertTrue(server.getGenerateStructures());
	}

	@Test
	void testSetGenerateStructures()
	{
		server.setGenerateStructures(false);
		assertFalse(server.getGenerateStructures());
	}

	@Test
	void testIsAllowEndDefault()
	{
		assertTrue(server.getAllowEnd());
	}

	@Test
	void testSetAllowEnd()
	{
		server.setAllowEnd(false);
		assertFalse(server.getAllowEnd());
	}

	@Test
	void testIsAllowNetherDefault()
	{
		assertTrue(server.getAllowNether());
	}

	@Test
	void testSetAllowNether()
	{
		server.setAllowNether(false);
		assertFalse(server.getAllowNether());
	}

	@Test
	void testGetUpdateFolderDefault()
	{
		assertEquals("update", server.getUpdateFolder());
	}

	@Test
	void testSetUpdateFolder()
	{
		server.setUpdateFolder("test");
		assertEquals("test", server.getUpdateFolder());
	}

	@Test
	void testGetSimulationDistanceDefault()
	{
		assertEquals(10, server.getSimulationDistance());
	}

	@Test
	void testSetSimulationDistance()
	{
		server.setSimulationDistance(12);
		assertEquals(12, server.getSimulationDistance());
	}

	@Test
	void testIsHideOnlinePlayersDefault()
	{
		assertFalse(server.getHideOnlinePlayers());
	}

	@Test
	void testSetHideOnlinePlayers()
	{
		server.setHideOnlinePlayers(true);
		assertTrue(server.getHideOnlinePlayers());
	}

	@Test
	void testIsShouldSendingChatPreviewsDefault()
	{
		assertFalse(server.shouldSendChatPreviews());
	}

	@Test
	void testSetShouldSendingChatPreviews()
	{
		server.setShouldSendChatPreviews(true);
		assertTrue(server.shouldSendChatPreviews());
	}

	@Test
	void testGetOnlineModeDefault()
	{
		assertTrue(server.getOnlineMode());
	}

	@Test
	void testSetOnlineMode()
	{
		server.setOnlineMode(false);
		assertFalse(server.getOnlineMode());
	}

	@Test
	void testIsEnforcingSecureProfiles()
	{
		assertTrue(server.isEnforcingSecureProfiles());
	}

	@Test
	void testSetEnforcingSecureProfiles()
	{
		server.setEnforcingSecureProfiles(false);
		assertFalse(server.isEnforcingSecureProfiles());
	}

	@Test
	void testIsAllowFlight()
	{
		assertFalse(server.getAllowFlight());
	}

	@Test
	void testSetAllowFlight()
	{
		server.setAllowFlight(true);
		assertTrue(server.getAllowFlight());
	}

	@Test
	void testIsHardcoreDefault()
	{
		assertFalse(server.isHardcore());
	}

	@Test
	void testSetHardCore()
	{
		server.setHardcore(true);
		assertTrue(server.isHardcore());
	}

	@Test
	void testGetMaxChainedNeighborUpdatesDefault()
	{
		assertEquals(1000000, server.getMaxChainedNeighborUpdates());
	}

	@Test
	void testSetMaxChainedNeighborUpdates()
	{
		server.setMaxChainedNeighborUpdates(1);
		assertEquals(1, server.getMaxChainedNeighborUpdates());
	}

	@Test
	void testGetShutdownMessageDefault()
	{
		assertEquals("Server closed", server.getShutdownMessage());
	}

	@Test
	void testSetShutdownMessage()
	{
		server.setShutdownMessage(Component.text("Test"));
		assertEquals("Test", server.getShutdownMessage());
	}

	@Test
	void testShutdownMessage()
	{
		assertEquals(Component.text("Server closed"), server.shutdownMessage());
	}

	@Test
	void testGetMaxWorldSizeDefault()
	{
		assertEquals(29999984, server.getMaxWorldSize());
	}

	@Test
	void testSetMaxWorldSize()
	{
		server.setMaxWorldSize(42);
		assertEquals(42, server.getMaxWorldSize());
	}

	@Test
	void testGetDefaultGamemodeDefault()
	{
		assertEquals(GameMode.SURVIVAL, server.getDefaultGameMode());
	}

	@Test
	void testSetDefaultGameMode()
	{
		server.setDefaultGameMode(GameMode.CREATIVE);
		assertEquals(GameMode.CREATIVE, server.getDefaultGameMode());
	}

	@Test
	void testCreateUncreateableInventory()
	{
		assertThrows(IllegalArgumentException.class, () -> server.createInventory(null, InventoryType.CREATIVE, "", 9));
	}

	@Test
	void testCreateDispenserInventory()
	{
		assertInstanceOf(DispenserInventoryMock.class, server.createInventory(null, InventoryType.DISPENSER, "", 9));
	}

	@Test
	void testCreateDropperInventory()
	{
		assertInstanceOf(DropperInventoryMock.class, server.createInventory(null, InventoryType.DROPPER, "", 9));
	}

	@Test
	void testCreatePlayerInventory()
	{
		PlayerMock playerMock = server.addPlayer();
		assertInstanceOf(PlayerInventoryMock.class, server.createInventory(playerMock, InventoryType.PLAYER, "", 9));
	}

	@Test
	void testCreatePlayerWithNonPlayerHolderThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> server.createInventory(null, InventoryType.PLAYER, "", 9));
	}

	@Test
	void testCreateEnderChestInventory()
	{
		assertInstanceOf(EnderChestInventoryMock.class, server.createInventory(null, InventoryType.ENDER_CHEST, "", 9));
	}

	@Test
	void testCreateHopperInventory()
	{
		assertInstanceOf(HopperInventoryMock.class, server.createInventory(null, InventoryType.HOPPER, "", 9));
	}

	@Test
	void testCreateShulkerBoxInventory()
	{
		assertInstanceOf(ShulkerBoxInventoryMock.class, server.createInventory(null, InventoryType.SHULKER_BOX, "", 9));
	}

	@Test
	void testCreateBarrelInventory()
	{
		assertInstanceOf(BarrelInventoryMock.class, server.createInventory(null, InventoryType.BARREL, "", 9));
	}

	@Test
	void testCreateLecternInventory()
	{
		assertInstanceOf(LecternInventoryMock.class, server.createInventory(null, InventoryType.LECTERN, "", 9));
	}

	@Test
	void testCreateGrindstoneInventory()
	{
		assertInstanceOf(GrindstoneInventoryMock.class, server.createInventory(null, InventoryType.GRINDSTONE, "", 9));
	}

	@Test
	void testCreateStonecutterInventory()
	{
		assertInstanceOf(StonecutterInventoryMock.class,
				server.createInventory(null, InventoryType.STONECUTTER, "", 9));
	}

	@Test
	void testCreateCartographyInventory()
	{
		assertInstanceOf(CartographyInventoryMock.class,
				server.createInventory(null, InventoryType.CARTOGRAPHY, "", 9));
	}

	@Test
	void testCreateFurnaceInventory()
	{
		assertInstanceOf(FurnaceInventoryMock.class, server.createInventory(null, InventoryType.FURNACE, "", 9));
		assertInstanceOf(FurnaceInventoryMock.class, server.createInventory(null, InventoryType.BLAST_FURNACE, "", 9));
		assertInstanceOf(FurnaceInventoryMock.class, server.createInventory(null, InventoryType.SMOKER, "", 9));
	}

	@Test
	void testCreateLoomInventory()
	{
		assertInstanceOf(LoomInventoryMock.class, server.createInventory(null, InventoryType.LOOM, "", 9));
	}

	@Test
	void testCreateAnvilInventory()
	{
		assertInstanceOf(AnvilInventoryMock.class, server.createInventory(null, InventoryType.ANVIL, "", 9));
	}

	@Test
	void testCreateSmithingInventory()
	{
		assertInstanceOf(SmithingInventoryMock.class, server.createInventory(null, InventoryType.SMITHING, "", 9));
	}

	@Test
	void testCreateBeaconInventory()
	{
		assertInstanceOf(BeaconInventoryMock.class, server.createInventory(null, InventoryType.BEACON, "", 9));
	}

	@Test
	void testCreateWorkbenchInventory()
	{
		assertInstanceOf(WorkbenchInventoryMock.class, server.createInventory(null, InventoryType.WORKBENCH, "", 9));
	}

	@Test
	void testCreateEnchantingInventory()
	{
		assertInstanceOf(EnchantingInventoryMock.class, server.createInventory(null, InventoryType.ENCHANTING, "", 9));
	}

	@Test
	void testCreateBrewerInventory()
	{
		assertInstanceOf(BrewerInventoryMock.class, server.createInventory(null, InventoryType.BREWING, "", 9));
	}

	@Test
	void testMotdDefault()
	{
		assertEquals(Component.text("A Minecraft Server"), server.motd());
	}

	@Test
	void testMotd()
	{
		server.motd(Component.text("Test"));
		assertEquals(Component.text("Test"), server.motd());
	}

	@Test
	void testGetMotdDefault()
	{
		assertEquals("A Minecraft Server", server.getMotd());
	}

	@Test
	void testSetMotd()
	{
		server.setMotd("Test");
		assertEquals("Test", server.getMotd());
	}

	@ValueSource(classes =
	{ Art.class, Attribute.class, Biome.class, Enchantment.class, EntityType.class, Fluid.class, Frog.Variant.class,
			KeyedBossBar.class, LootTables.class, Material.class, MemoryKey.class, PotionEffectType.class, Sound.class,
			Statistic.class, Villager.Profession.class, Villager.Type.class, })
	@ParameterizedTest
	void getRegistry_ValidType_HasValues(Class<? extends Keyed> clazz)
	{
		Registry<?> registry = Bukkit.getRegistry(clazz);
		assertNotNull(registry);
		if (clazz != KeyedBossBar.class)
			assertTrue(registry.iterator().hasNext());
	}

	@ValueSource(classes =
	{ ConfiguredStructure.class })
	@ParameterizedTest
	void getRegistry_InvalidType_Throws(Class<? extends Keyed> clazz)
	{
		Registry<? extends Keyed> registry = Bukkit.getRegistry(clazz);
		assertNotNull(registry);
		assertThrows(UnimplementedOperationException.class, () -> registry.iterator());
	}

	@Test
	void testGetServerConfiguration()
	{
		assertNotNull(server.getServerConfiguration());
		assertInstanceOf(ServerConfiguration.class, server.getServerConfiguration());
	}

	@ParameterizedTest
	@MethodSource("testGetTicksPerSpawnsArguments")
	void testGetTicksPerSpawns(SpawnCategory category, int expected)
	{
		assertEquals(expected, server.getTicksPerSpawns(category));
	}

	public static Stream<Arguments> testGetTicksPerSpawnsArguments()
	{
		return Stream.of(Arguments.of(SpawnCategory.MONSTER, 1), Arguments.of(SpawnCategory.ANIMAL, 400),
				Arguments.of(SpawnCategory.WATER_AMBIENT, 1), Arguments.of(SpawnCategory.WATER_ANIMAL, 1),
				Arguments.of(SpawnCategory.AMBIENT, 1), Arguments.of(SpawnCategory.WATER_UNDERGROUND_CREATURE, 1));
	}

	@Test
	void testGetTicksPerSpawns_NullCategory()
	{
		assertThrows(IllegalArgumentException.class, () -> server.getTicksPerSpawns(null));
	}

	@Test
	void testGetTicksPerSpawns_InvalidCategory()
	{
		assertThrows(IllegalArgumentException.class, () -> server.getTicksPerSpawns(SpawnCategory.MISC));
	}

	@Test
	void testGetTicksPerMonsterSpawns()
	{
		assertEquals(1, server.getTicksPerMonsterSpawns());
	}

	@Test
	void testGetTicksPerWaterAmbientSpawns()
	{
		assertEquals(1, server.getTicksPerWaterAmbientSpawns());
	}

	@Test
	void testGetTicksPerWaterSpawns()
	{
		assertEquals(1, server.getTicksPerWaterSpawns());
	}

	@Test
	void testGetTicksPerAmbientSpawns()
	{
		assertEquals(1, server.getTicksPerAmbientSpawns());
	}

	@Test
	void testGetTicksPerWaterUndergroundCreatureSpawns()
	{
		assertEquals(1, server.getTicksPerWaterUndergroundCreatureSpawns());
	}

	@Test
	void testGetTicksPerAnimalSpawns()
	{
		assertEquals(400, server.getTicksPerAnimalSpawns());
	}

	@ParameterizedTest
	@MethodSource("getSpawnLimitArguments")
	void testGetSpawnLimit(SpawnCategory category, int expected)
	{
		assertEquals(expected, server.getSpawnLimit(category));
	}

	public static Stream<Arguments> getSpawnLimitArguments()
	{
		return Stream.of(Arguments.of(SpawnCategory.MONSTER, 70), Arguments.of(SpawnCategory.ANIMAL, 10),
				Arguments.of(SpawnCategory.WATER_AMBIENT, 20), Arguments.of(SpawnCategory.WATER_ANIMAL, 5),
				Arguments.of(SpawnCategory.AMBIENT, 15), Arguments.of(SpawnCategory.WATER_UNDERGROUND_CREATURE, 5));
	}

	@Test
	void testGetSpawnLimit_NullCategory()
	{
		assertThrows(IllegalArgumentException.class, () -> server.getSpawnLimit(null));
	}

	@Test
	void testGetSpawnLimit_InvalidCategory()
	{
		assertThrows(IllegalArgumentException.class, () -> server.getSpawnLimit(SpawnCategory.MISC));
	}

	@Test
	void testGetMonsterSpawnLimit()
	{
		assertEquals(70, server.getMonsterSpawnLimit());
	}

	@Test
	void testGetWaterAmbientSpawnLimit()
	{
		assertEquals(20, server.getWaterAmbientSpawnLimit());
	}

	@Test
	void testGetWaterAnimalSpawnLimit()
	{
		assertEquals(5, server.getWaterAnimalSpawnLimit());
	}

	@Test
	void testGetAmbientSpawnLimit()
	{
		assertEquals(15, server.getAmbientSpawnLimit());
	}

	@Test
	void testGetWaterUndergroundCreatureSpawnLimit()
	{
		assertEquals(5, server.getWaterUndergroundCreatureSpawnLimit());
	}

	@Test
	void testGetAnimalSpawnLimit()
	{
		assertEquals(10, server.getAnimalSpawnLimit());
	}

	@Test
	void testBanIP()
	{
		InetAddress address = InetAddresses.fromInteger(ThreadLocalRandom.current().nextInt());
		assertFalse(server.getBanList(BanList.Type.IP).isBanned(address));
		server.banIP(address);
		assertTrue(server.getBanList(BanList.Type.IP).isBanned(address));
	}

	@Test
	void testBanIPNullThrows()
	{
		assertThrows(NullPointerException.class, () -> server.banIP((InetAddress) null));
	}

	@Test
	void testUnbanIP()
	{
		InetAddress address = InetAddresses.fromInteger(ThreadLocalRandom.current().nextInt());
		server.banIP(address);
		assertTrue(server.getBanList(BanList.Type.IP).isBanned(address));

		server.unbanIP(address);
		assertFalse(server.getBanList(BanList.Type.IP).isBanned(address));
	}

	@Test
	void testUnbanIPNullThrows()
	{
		assertThrows(NullPointerException.class, () -> server.unbanIP((InetAddress) null));
	}

	@Test
	void testBanIPString()
	{
		InetAddress address = InetAddresses.fromInteger(ThreadLocalRandom.current().nextInt());
		String addressString = address.getHostAddress();
		assertFalse(server.getBanList(BanList.Type.IP).isBanned(addressString));
		server.banIP(addressString);
		assertTrue(server.getBanList(BanList.Type.IP).isBanned(addressString));
	}

	@Test
	void testBanIPStringNullThrows()
	{
		assertThrows(NullPointerException.class, () -> server.banIP((String) null));
	}

	@Test
	void testUnbanIPString()
	{
		InetAddress address = InetAddresses.fromInteger(ThreadLocalRandom.current().nextInt());
		String addressString = address.getHostAddress();
		server.banIP(addressString);
		assertTrue(server.getBanList(BanList.Type.IP).isBanned(addressString));
		server.unbanIP(addressString);
		assertFalse(server.getBanList(BanList.Type.IP).isBanned(addressString));
	}

	@Test
	void testUnbanIPStringNullThrows()
	{
		assertThrows(NullPointerException.class, () -> server.unbanIP((String) null));
	}

	@Test
	void testGetIPBans()
	{
		InetAddress address = InetAddresses.fromInteger(ThreadLocalRandom.current().nextInt());
		server.banIP(address);
		assertEquals(1, server.getIPBans().size());
		assertTrue(server.getIPBans().contains(address.getHostAddress()));

	}

	@Test
	void testGetOfflinePlayerIfCached_notRegistered()
	{
		String name = "headstalls";
		OfflinePlayer offlinePlayer = server.getOfflinePlayerIfCached(name);
		assertNull(offlinePlayer);
	}

	@Test
	void testGetOfflinePlayerIfCached_offlinePlayerRegistered()
	{
		PlayerMock playerMock = server.addPlayer("CapitalizedName");
		playerMock.disconnect();
		OfflinePlayer offlinePlayer = server.getOfflinePlayerIfCached(playerMock.getName());
		assertEquals(playerMock, offlinePlayer);
	}

	@Test
	void testGetOfflinePlayerIfCached_playerRegistered()
	{
		PlayerMock playerMock = server.addPlayer("CapitalizedName");
		OfflinePlayer offlinePlayer = server.getOfflinePlayerIfCached(playerMock.getName());
		assertEquals(playerMock, offlinePlayer);
	}

}

class TestRecipe implements Recipe
{

	private final @NotNull ItemStack result;

	public TestRecipe(@NotNull ItemStack result)
	{
		this.result = result;
	}

	public TestRecipe()
	{
		this(null);
	}

	@Override
	public @NotNull ItemStack getResult()
	{
		return result;
	}

}
