package be.seeseemelk.mockbukkit.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
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
		Plugin plugin = pluginManager.getPlugin("MockBukkitTestPlugin");
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
	public void assertEventFired_PredicateTrue_DoesNotAssert()
	{
		Player player = server.addPlayer();
		BlockBreakEvent eventToFire = new BlockBreakEvent(null, player);
		pluginManager.callEvent(eventToFire);
		pluginManager.assertEventFired(event -> {
			return event instanceof BlockBreakEvent && ((BlockBreakEvent) event).getPlayer().equals(player);
		});
	}
	
	@Test(expected = AssertionError.class)
	public void assertEventFired_PredicateFalse_Asserts()
	{
		Player player = server.addPlayer();
		BlockBreakEvent eventToFire = new BlockBreakEvent(null, player);
		pluginManager.callEvent(eventToFire);
		pluginManager.assertEventFired(event -> {
			return false;
		});
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
	
	@Test
	public void getPermission_NoPermission_Null()
	{
		assertNull(pluginManager.getPermission("mockbukkit.perm"));
	}

	@Test
	public void getPermission_PermissionAdded_NotNull()
	{
		Permission permission = new Permission("mockbukkit.perm");
		pluginManager.addPermission(permission);
		assertNotNull(pluginManager.getPermission(permission.getName()));
	}
	
	@Test
	public void getDefaultPermission_OpPermissionAddedAndAsked_ContainsPermission()
	{
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.OP);
		pluginManager.addPermission(permission);
		assertTrue(pluginManager.getDefaultPermissions(true).contains(permission));
	}
	
	@Test
	public void getDefaultPermission_OpPermissionAskedButNotAdded_DoesNotContainPermission()
	{
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.NOT_OP);
		pluginManager.addPermission(permission);
		assertFalse(pluginManager.getDefaultPermissions(true).contains(permission));
	}
	
	@Test
	public void disablePlugin_LoadedPlugin_PluginDisabled()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin.isEnabled());
		pluginManager.disablePlugin(plugin);
		pluginManager.assertEventFired(PluginDisableEvent.class, event -> event.getPlugin().equals(plugin));
		assertFalse("Plugin was not disabled", plugin.isEnabled());
		assertTrue(plugin.onDisableExecuted);
	}
	
	@Test
	public void disablePlugins_LoadedPlugins_AllDisabled()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin.isEnabled());
		pluginManager.disablePlugins();
		assertFalse("Plugin was not disabled", plugin.isEnabled());
		assertTrue(plugin.onDisableExecuted);
	}
	
	@Test
	public void clearPlugins_LoadedPlugins_AllPluginsRemove()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin.isEnabled());
		pluginManager.clearPlugins();
		assertFalse("Plugin was not disabled", plugin.isEnabled());
		Plugin[] plugins = pluginManager.getPlugins();
		assertEquals(0, plugins.length);
	}
	
}
























