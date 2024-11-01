package org.mockbukkit.mockbukkit.plugin;

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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPluginUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.EventHandlerException;
import org.mockbukkit.mockbukkit.exception.PluginLoadException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasFiredEventInstance;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasNotFiredEventInstance;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent;

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
		assertThat(pluginManager, hasFiredFilteredEvent(BlockBreakEvent.class, event -> event.getPlayer().equals(player)));
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
		assertThat(server.getPluginManager(), hasFiredEventInstance(BlockBreakEvent.class));
	}

	@Test
	void assertEventFired_EventWasNotFired_Asserts()
	{
		assertThat(server.getPluginManager(), hasNotFiredEventInstance(BlockBreakEvent.class));
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
	void removePermission_String()
	{
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

		assertFalse(plugin.isEnabled(), "Plugin was not disabled");
		assertTrue(plugin.onDisableExecuted);
	}

	@Test
	void disablePlugins_LoadedPlugins_AllDisabled()
	{
		TestPlugin plugin1 = MockBukkit.load(TestPlugin.class);
		TestPlugin plugin2 = MockBukkit.load(TestPlugin.class);
		assertTrue(plugin1.isEnabled());
		assertTrue(plugin2.isEnabled());

		pluginManager.disablePlugins();

		assertFalse(plugin1.isEnabled(), "Plugin1 was not disabled");
		assertFalse(plugin2.isEnabled(), "Plugin2 was not disabled");
		assertTrue(plugin1.onDisableExecuted);
		assertTrue(plugin2.onDisableExecuted);
	}

	@Test
	void disablePlugin_PluginDisableEvent_IsFired()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);

		pluginManager.disablePlugin(plugin);

		assertThat(pluginManager, hasFiredFilteredEvent(PluginDisableEvent.class, event -> event.getPlugin().equals(plugin)));
	}

	@Test
	void disablePlugin_Unloaded_PluginDisableEvent_NotFired()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		JavaPluginUtils.setEnabled(plugin, false);

		pluginManager.disablePlugin(plugin);
		assertThat(pluginManager, hasNotFiredEventInstance(PluginDisableEvent.class));
	}

	@Test
	void disablePlugin_TasksCanceled()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		BukkitRunnable runnable = new BukkitRunnable()
		{
			@Override
			public void run()
			{
			}
		};
		runnable.runTaskTimer(plugin, 1, 1);

		pluginManager.disablePlugin(plugin);

		assertTrue(runnable.isCancelled());
	}

	@Test
	void disablePlugin_ServicesUnregistered()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		server.getServicesManager().register(Object.class, new Object(), plugin, ServicePriority.High);
		assertTrue(server.getServicesManager().isProvidedFor(Object.class));

		pluginManager.disablePlugin(plugin);

		assertFalse(server.getServicesManager().isProvidedFor(Object.class));
	}

	@Test
	void disablePlugin_DisablesPluginChannels()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		server.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		server.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", (channel, player, message) ->
		{
		});
		assertTrue(server.getMessenger().isOutgoingChannelRegistered(plugin, "BungeeCord"));
		assertTrue(server.getMessenger().isIncomingChannelRegistered(plugin, "BungeeCord"));

		pluginManager.disablePlugin(plugin);

		assertFalse(server.getMessenger().isOutgoingChannelRegistered(plugin, "BungeeCord"));
		assertFalse(server.getMessenger().isIncomingChannelRegistered(plugin, "BungeeCord"));
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
	void eventThrowsException_IllegalStateException_RethrowsSame()
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

	@ParameterizedTest
	@CsvSource("Name(with)[other]<chars>!,bukkit,minecraft,mojang")
	void loadPlugin_InvalidName_DoesntLoad(String name) throws ReflectiveOperationException
	{
		// Won't let us create an invalid name.
		Field nameField = PluginDescriptionFile.class.getDeclaredField("name");
		nameField.setAccessible(true);
		PluginDescriptionFile sillyName = new PluginDescriptionFile("Name", "1.0.0", TestPlugin.class.getName());
		nameField.set(sillyName, name);

		assertThrows(PluginLoadException.class, () -> pluginManager.loadPlugin(TestPlugin.class, sillyName, new Object[0]));
	}

	@Test
	void test_customClassLoader()
	{
		assertDoesNotThrow(() -> plugin.createCustomClass());
		assertTrue(plugin.classLoadSucceed);
	}

}
