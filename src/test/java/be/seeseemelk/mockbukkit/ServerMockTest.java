package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.command.CommandResult;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory;

public class ServerMockTest
{
	private ServerMock server;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
	}
	
	@After
	public void tearDown()
	{
		MockBukkit.unload();
	}
	
	@Test
	public void class_NumberOfPlayers_Zero()
	{
		assertEquals(0, server.getOnlinePlayers().size());
	}
	
	@Test
	public void addPlayer_TwoPlayers_SizeIsTwo()
	{
		PlayerMockFactory factory = new PlayerMockFactory();
		PlayerMock player1 = factory.createRandomPlayer();
		PlayerMock player2 = factory.createRandomPlayer();
		
		server.addPlayer(player1);
		assertEquals(1, server.getOnlinePlayers().size());
		server.addPlayer(player2);
		assertEquals(2, server.getOnlinePlayers().size());
		
		assertEquals(player1, server.getPlayer(0));
		assertEquals(player2, server.getPlayer(1));
	}
	
	@Test
	public void addPlayers_None_TwoUniquePlayers()
	{
		server.addPlayer();
		server.addPlayer();
		PlayerMock player1 = server.getPlayer(0);
		PlayerMock player2 = server.getPlayer(1);
		assertNotNull(player1);
		assertNotNull(player2);
		assertNotEquals(player1, player2);
	}
	
	@Test
	public void setPlayers_Two_TwoUniquePlayers()
	{
		server.setPlayers(2);
		PlayerMock player1 = server.getPlayer(0);
		PlayerMock player2 = server.getPlayer(1);
		assertNotNull(player1);
		assertNotNull(player2);
		assertNotEquals(player1, player2);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getPlayers_Negative_ArrayIndexOutOfBoundsException()
	{
		server.setPlayers(2);
		server.getPlayer(-1);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getPlayers_LargerThanNumberOfPlayers_ArrayIndexOutOfBoundsException()
	{
		server.setPlayers(2);
		server.getPlayer(2);
	}
	
	@Test
	public void getVersion_NotNull()
	{
		assertNotNull(server.getVersion());
	}
	
	@Test
	public void getBukkitVersion_NotNull()
	{
		assertNotNull(server.getBukkitVersion());
	}
	
	@Test
	public void getName_NotNull()
	{
		assertNotNull(server.getName());
	}
	
	@Test
	public void getPlayers_AllSame()
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
	public void getPluginCommand_testcommand_Command()
	{
		MockBukkit.load(TestPlugin.class);
		assertNotNull(server.getPluginCommand("testcommand"));
	}
	
	@Test
	public void getPluginCommand_tcAlias_Command()
	{
		MockBukkit.load(TestPlugin.class);
		assertNotNull(server.getPluginCommand("tc"));
	}
	
	@Test
	public void getPluginCommand_Unknown_Null()
	{
		MockBukkit.load(TestPlugin.class);
		assertNull(server.getPluginCommand("notknown"));
	}
	
	@Test
	public void executeCommand_PlayerAndTrueReturnValue_Succeeds()
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
	public void executeCommand_ConsoleAndFalseReturnValue_Fails()
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
	public void executeCommand_CommandAsStringAndTrueReturnValue_Succeeds()
	{
		TestPlugin plugin = (TestPlugin) MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = true;
		
		CommandResult result = server.executeConsole("testcommand");
		result.assertSucceeded();
	}
	
	@Test
	public void getConsoleSender_NotNull()
	{
		assertNotNull(server.getConsoleSender());
	}
	
	@Test
	public void getOfflinePlayers_AllSame()
	{
		server.setPlayers(2);
		PlayerMock player1 = server.getPlayer(0);
		PlayerMock player2 = server.getPlayer(1);
		OfflinePlayer[] players = server.getOfflinePlayers();
		assertEquals(player1, players[0]);
		assertEquals(player2, players[1]);
		assertEquals(2, players.length);
	}
	
	@Test
	public void createInventory_PlayerTypeNoHolder_PlayerInventory()
	{
		Inventory inventory = server.createInventory(null, InventoryType.PLAYER);
		assertTrue(inventory instanceof PlayerInventory);
	}
	
	@Test
	public void getItemFactory_NotNull()
	{
		assertNotNull(server.getItemFactory());
	}
	
	@Test
	public void addSimpleWorld_Name_WorldWithNameAdded()
	{
		WorldMock world = server.addSimpleWorld("MyWorld");
		assertEquals(1, server.getWorlds().size());
		assertSame(world, server.getWorlds().get(0));
		assertSame(world, server.getWorld(world.getName()));
		assertSame(world, server.getWorld(world.getUID()));
	}
}























