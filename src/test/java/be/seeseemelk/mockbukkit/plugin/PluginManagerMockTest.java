package be.seeseemelk.mockbukkit.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;

public class PluginManagerMockTest
{
	private ServerMock server;
	private PluginManagerMock pluginManager;
	private TestPlugin plugin;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		pluginManager = server.getPluginManager();
		plugin = MockBukkit.load(TestPlugin.class);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}
	
	@Test
	public void callEvent_UnregisteredPlayerInteractEvent_NoneCalled()
	{
		PlayerInteractEvent event = new PlayerInteractEvent(null, null, null, null, null);
		pluginManager.callEvent(event);
		assertFalse(plugin.unannotatedPlayerInteractEventExecuted);
		assertFalse(plugin.annotatedBlockBreakEventExecuted);
		assertFalse(plugin.annotatedPlayerInteractEventExecuted);
	}

	@Test
	public void callEvent_RegisteredPlayerInteractEvent_OneCalled()
	{
		PlayerInteractEvent event = new PlayerInteractEvent(null, null, null, null, null);
		pluginManager.registerEvents(plugin, plugin);
		pluginManager.callEvent(event);
		assertFalse(plugin.unannotatedPlayerInteractEventExecuted);
		assertFalse(plugin.annotatedBlockBreakEventExecuted);
		assertTrue(plugin.annotatedPlayerInteractEventExecuted);
	}
	
	@Test
	public void getPlugin_PluginName_Plugin()
	{
		Plugin plugin = pluginManager.getPlugin("MockBukkitTest");
		assertNotNull(plugin);
		assertTrue(plugin instanceof TestPlugin);
	}

	@Test
	public void getPlugin_UnknownName_Nothing()
	{
		Plugin plugin = pluginManager.getPlugin("NoPlugin");
		assertNull(plugin);
	}
	
	@Test
	public void getCommands_Default_PluginCommand()
	{
		Collection<PluginCommand> commands = pluginManager.getCommands();
		assertEquals(3, commands.size());
		Iterator<PluginCommand> iterator = commands.iterator();
		assertEquals("mockcommand", iterator.next().getName());
		assertEquals("testcommand", iterator.next().getName());
		assertEquals("othercommand", iterator.next().getName());
		assertFalse(iterator.hasNext());
	}
	
	@Test
	public void assertEventFired_EventWasFired_DoesNotAssert()
	{
		BlockBreakEvent event = new BlockBreakEvent(null, null);
		pluginManager.callEvent(event);
		pluginManager.assertEventFired(BlockBreakEvent.class);
	}
	
	@Test(expected = AssertionError.class)
	public void assertEventFired_EventWasNotFired_Asserts()
	{
		pluginManager.assertEventFired(BlockBreakEvent.class);
	}

}
























