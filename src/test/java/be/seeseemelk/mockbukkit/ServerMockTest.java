package be.seeseemelk.mockbukkit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;
import be.seeseemelk.mockbukkit.entity.SimpleEntityMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;

class ServerMockTest
{
	private ServerMock server;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void class_NumberOfPlayers_Zero()
	{
		assertEquals(0, server.getOnlinePlayers().size());
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

	@ParameterizedTest
	@ValueSource(strings = {"testcommand", "tc", "othercommand"})
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
		TestPlugin plugin = (TestPlugin) MockBukkit.load(TestPlugin.class);
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
		TestPlugin plugin = (TestPlugin) MockBukkit.load(TestPlugin.class);
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
		TestPlugin plugin = (TestPlugin) MockBukkit.load(TestPlugin.class);
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
		;
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

	@Test
	void getPlayer_NameAndPlayerExists_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertSame(player, server.getPlayer("player"));
	}

	@Test
	void getPlayer_NameAndPlayerExistsButCasingWrong_PlayerNotFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertSame(player, server.getPlayer("PLAYER"));
	}

	@Test
	void getPlayer_UUIDAndPlayerExists_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player");
		server.addPlayer(player);
		assertSame(player, server.getPlayer(player.getUniqueId()));
	}

	@Test
	void getPlayer_PlayerNamePartiallyCorrect_PlayerFound()
	{
		PlayerMock player = new PlayerMock(server, "player_other");
		server.addPlayer(player);
		assertSame(player, server.getPlayer("player"));
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
	void assertMainThread_MainThread_Succeeds()
	{
		server.assertMainThread();
	}

	@Test
	void assertMainThread_NotMainThread_ThrowsException() throws Exception
	{
		AtomicReference<Exception> exceptionThrown = new AtomicReference<>();

		server.getScheduler().runTaskAsynchronously(null, () ->
		{
			try
			{
				server.assertMainThread();
			}
			catch (ThreadAccessException e)
			{
				exceptionThrown.set(e);
			}
		});

		server.getScheduler().waitAsyncTasksFinished();

		assertNotNull(exceptionThrown.get());
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
		assertEquals(32, PotionEffectType.values().length);

		for (PotionEffectType type : PotionEffectType.values())
		{
			assertNotNull(type);
		}
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
	public ItemStack getResult()
	{
		return result;
	}
}
