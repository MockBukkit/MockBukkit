package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.entity.SimpleEntityMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
		WorldCreator worldCreator = new WorldCreator("test")
				.seed(12345)
				.type(WorldType.FLAT)
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
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> server.getPlayer(-1));
	}

	@Test
	void getPlayers_LargerThanNumberOfPlayers_ArrayIndexOutOfBoundsException()
	{
		server.setPlayers(2);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> server.getPlayer(2));
	}

	@Test
	void getVersion_NotNull()
	{
		assertNotNull(server.getVersion());
	}

	@Test
	void getBukkitVersion_NotNull()
	{
		assertNotNull(server.getBukkitVersion());
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
		assertThat(offlinePlayer.getUniqueId(), equalTo(id));
	}

	@ParameterizedTest
	@ValueSource(strings = { "testcommand", "tc", "othercommand" })
	void testPluginCommand(String cmd)
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
	@CsvSource({
			"player, player",
			"player, PLAYER",
			"player_other, player",
	})
	void getPlayer_NameAndPlayerExists_PlayerFound(String actual, String expected)
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
		assertThat(players, containsInAnyOrder(playerA, playerAB));
	}

	@Test
	void testOfflinePlayerJoin()
	{
		OfflinePlayerMock offlinePlayer = new OfflinePlayerMock("IAmOffline");
		assertFalse(server.getOfflinePlayer(offlinePlayer.getUniqueId()) instanceof PlayerMock);

		PlayerMock onlinePlayer = offlinePlayer.join(server);

		assertFalse(offlinePlayer.isOnline());
		assertTrue(onlinePlayer.isOnline());

		// Assert that this is still the same Player (as far as name and uuid are concerned)
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
		server.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
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
		server.getPluginManager().assertEventFired(ServerLoadEvent.class, (e) -> e.getType() == ServerLoadEvent.LoadType.RELOAD);
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

		assertThat(server.getPlayerList().getOnlinePlayers(), containsInAnyOrder(playerA, playerB));
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

}

class TestRecipe implements Recipe
{

	private final ItemStack result;

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
