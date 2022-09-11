package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.exception.EventHandlerException;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PluginManagerMockTest
{

	private ServerMock server;
	private PluginManagerMock pluginManager;
	private TestPlugin plugin;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		pluginManager = server.getPluginManager();
		plugin = MockBukkit.load(TestPlugin.class);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void callEvent_UnregisteredPlayerInteractEvent_NoneCalled()
	{
		PlayerInteractEvent event = new PlayerInteractEvent(null, null, null, null, null);
		pluginManager.callEvent(event);
		assertFalse(plugin.unannotatedPlayerInteractEventExecuted);
		assertFalse(plugin.annotatedBlockBreakEventExecuted);
		assertFalse(plugin.annotatedPlayerInteractEventExecuted);
	}

	@Test
	void callEvent_RegisteredPlayerInteractEvent_OneCalled()
	{
		PlayerInteractEvent event = new PlayerInteractEvent(null, null, null, null, null);
		pluginManager.registerEvents(plugin, plugin);
		pluginManager.callEvent(event);
		assertFalse(plugin.unannotatedPlayerInteractEventExecuted);
		assertFalse(plugin.annotatedBlockBreakEventExecuted);
		assertTrue(plugin.annotatedPlayerInteractEventExecuted);
	}

	@Test
	void test_ManualListener_Registration()
	{
		MockBukkit.getMock().getPluginManager().registerEvents(plugin, plugin);
		assertEquals(3, BlockBreakEvent.getHandlerList().getRegisteredListeners().length);
		pluginManager.unregisterPluginEvents(plugin);
		assertEquals(0, BlockBreakEvent.getHandlerList().getRegisteredListeners().length);
		MockBukkit.getMock().getPluginManager().registerEvents(plugin, plugin);
		MockBukkit.getMock().getPluginManager().registerEvents(plugin, plugin);
		assertEquals(6, BlockBreakEvent.getHandlerList().getRegisteredListeners().length);
		pluginManager.unregisterPluginEvents(plugin);
		assertEquals(0, BlockBreakEvent.getHandlerList().getRegisteredListeners().length);
	}

	@Test
	void test_AutomaticListener_DeRegistration()
	{
		MockBukkit.getMock().getPluginManager().registerEvents(plugin, plugin);
		assertEquals(3, BlockBreakEvent.getHandlerList().getRegisteredListeners().length);
		MockBukkit.unmock();
		assertEquals(0, BlockBreakEvent.getHandlerList().getRegisteredListeners().length);

	}

	@Test
	void getPlugin_PluginName_Plugin()
	{
		Plugin plugin = pluginManager.getPlugin("MockBukkitTestPlugin");
		assertNotNull(plugin);
		assertTrue(plugin instanceof TestPlugin);
	}

	@Test
	void getPlugin_UnknownName_Nothing()
	{
		Plugin plugin = pluginManager.getPlugin("NoPlugin");
		assertNull(plugin);
	}

	@Test
	void getCommands_Default_PluginCommand()
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
	void assertEventFired_PredicateTrue_DoesNotAssert()
	{
		Player player = server.addPlayer();
		BlockBreakEvent eventToFire = new BlockBreakEvent(null, player);
		pluginManager.callEvent(eventToFire);
		pluginManager.assertEventFired(event ->
				event instanceof BlockBreakEvent && ((BlockBreakEvent) event).getPlayer().equals(player)
		);
	}

	@Test
	void assertEventFired_PredicateFalse_Asserts()
	{
		Player player = server.addPlayer();
		BlockBreakEvent eventToFire = new BlockBreakEvent(null, player);
		pluginManager.callEvent(eventToFire);
		assertThrows(AssertionError.class, () -> pluginManager.assertEventFired(event -> false));
	}

	@Test
	void assertListenerRan_With_Order()
	{
		server.getPluginManager().registerEvents(plugin, plugin);
		Player p = server.addPlayer();
		BlockBreakEvent event = new BlockBreakEvent(null, p);
		assertTrue(plugin.ignoredCancelledEvent);
		pluginManager.callEvent(event);
		assertTrue(event.isCancelled());
		assertTrue(plugin.ignoredCancelledEvent);
	}


	@Test
	void assertEventFired_EventWasFired_DoesNotAssert()
	{
		BlockBreakEvent event = new BlockBreakEvent(null, null);
		pluginManager.callEvent(event);
		pluginManager.assertEventFired(BlockBreakEvent.class);
	}

	@Test
	void assertEventFired_EventWasNotFired_Asserts()
	{
		assertThrows(AssertionError.class, () -> pluginManager.assertEventFired(BlockBreakEvent.class));
	}

	@Test
	void getPermission_NoPermission_Null()
	{
		assertNull(pluginManager.getPermission("mockbukkit.perm"));
	}

	@Test
	void getPermission_PermissionAdded_NotNull()
	{
		Permission permission = new Permission("mockbukkit.perm");
		pluginManager.addPermission(permission);
		assertNotNull(pluginManager.getPermission(permission.getName()));
	}

	@Test
	void getDefaultPermission_OpPermissionAddedAndAsked_ContainsPermission()
	{
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.OP);
		pluginManager.addPermission(permission);
		assertTrue(pluginManager.getDefaultPermissions(true).contains(permission));
	}

	@Test
	void getDefaultPermission_OpPermissionAskedButNotAdded_DoesNotContainPermission()
	{
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.NOT_OP);
		pluginManager.addPermission(permission);
		assertFalse(pluginManager.getDefaultPermissions(true).contains(permission));
	}

	@Test
	void removePermission_String() {
		Permission permission = new Permission("mockbukkit.perm");
		pluginManager.addPermission(permission);
		assertTrue(pluginManager.getPermissions().contains(permission));

		pluginManager.removePermission("mockbukkit.perm");

		assertFalse(pluginManager.getPermissions().contains(permission));
	}

	@Test
	void disablePlugin_LoadedPlugin_PluginDisabled()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin.isEnabled());
		pluginManager.disablePlugin(plugin);
		pluginManager.assertEventFired(PluginDisableEvent.class, event -> event.getPlugin().equals(plugin));
		assertFalse(plugin.isEnabled(), "Plugin was not disabled");
		assertTrue(plugin.onDisableExecuted);
	}

	@Test
	void disablePlugins_LoadedPlugins_AllDisabled()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin.isEnabled());
		pluginManager.disablePlugins();
		assertFalse(plugin.isEnabled(), "Plugin was not disabled");
		assertTrue(plugin.onDisableExecuted);
	}

	@Test
	void clearPlugins_LoadedPlugins_AllPluginsRemove()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin.isEnabled());
		pluginManager.clearPlugins();
		assertFalse(plugin.isEnabled(), "Plugin was not disabled");
		Plugin[] plugins = pluginManager.getPlugins();
		assertEquals(0, plugins.length);
	}

	@Test
	void subscribeToDefaultPerms()
	{
		Permissible player = server.addPlayer();

		pluginManager.subscribeToDefaultPerms(true, player);

		assertTrue(pluginManager.getDefaultPermSubscriptions(true).contains(player));
	}

	@Test
	void unsubscribeToDefaultPerms()
	{
		Permissible player = server.addPlayer();
		pluginManager.subscribeToDefaultPerms(true, player);

		pluginManager.unsubscribeFromDefaultPerms(true, player);

		assertFalse(pluginManager.getDefaultPermSubscriptions(true).contains(player));
	}

	@Test
	void eventThrowsException_RuntimeException_RethrowsSame()
	{
		pluginManager.registerEvents(new Listener()
		{
			@EventHandler
			public void event(BlockBreakEvent e)
			{
				throw new IllegalStateException();
			}
		}, MockBukkit.createMockPlugin());
		assertThrowsExactly(IllegalStateException.class, () -> pluginManager.callEvent(new BlockBreakEvent(null, null)));
	}

	@Test
	void eventThrowsException_NotRuntimeException_ThrowsEventHandlerException()
	{
		pluginManager.registerEvents(new Listener()
		{
			@EventHandler
			public void event(BlockBreakEvent e) throws Exception
			{
				throw new Exception();
			}
		}, MockBukkit.createMockPlugin());
		assertThrowsExactly(EventHandlerException.class, () -> pluginManager.callEvent(new BlockBreakEvent(null, null)));
	}

}
