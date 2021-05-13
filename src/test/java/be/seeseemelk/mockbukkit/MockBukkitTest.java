package be.seeseemelk.mockbukkit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MockBukkitTest
{
	@BeforeEach
	public void setUp()
	{
		MockBukkit.setServerInstanceToNull();
	}

	@AfterEach
	public void tearDown()
	{
		if (MockBukkit.isMocked())
		{
			MockBukkit.unmock();
		}
	}

	@Test
	void setServerInstanceToNull()
	{
		MockBukkit.mock();
		assumeFalse(Bukkit.getServer() == null);
		MockBukkit.setServerInstanceToNull();
		assertNull(Bukkit.getServer());
	}

	@Test
	void mock_ServerMocked()
	{
		ServerMock server = MockBukkit.mock();
		assertNotNull(server);
		assertEquals(server, MockBukkit.getMock());
		assertEquals(server, Bukkit.getServer());
	}

	@Test
	void mock_ServerSafeMocked()
	{
		ServerMock server = MockBukkit.getOrCreateMock();
		assertNotNull(server);
		assertEquals(server, MockBukkit.getMock());
		assertEquals(server, MockBukkit.getOrCreateMock());
	}

	@Test
	void mock_CustomServerMocked()
	{
		CustomServerMock server = MockBukkit.mock(new CustomServerMock());
		assertNotNull(server);
		assertEquals(server, MockBukkit.getMock());
		assertEquals(server, Bukkit.getServer());
	}

	@Test
	void isMocked_ServerNotMocked_False()
	{
		assertFalse(MockBukkit.isMocked());
	}

	@Test
	void isMocked_ServerMocked_True()
	{
		MockBukkit.mock();
		assertTrue(MockBukkit.isMocked());
	}

	@Test
	void isMocked_ServerUnloaded_False()
	{
		MockBukkit.mock();
		MockBukkit.unmock();
		assertFalse(MockBukkit.isMocked());
	}

	@Test
	void load_MyPlugin()
	{
		ServerMock server = MockBukkit.mock();
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		server.getPluginManager().assertEventFired(PluginEnableEvent.class, event -> event.getPlugin().equals(plugin));
		assertTrue(plugin.isEnabled(), "Plugin not enabled");
		assertTrue(plugin.onEnableExecuted, "Plugin's onEnable method not executed");
	}

	@Test
	void load_TestPluginWithExtraParameter_ExtraParameterPassedOn()
	{
		MockBukkit.mock();
		TestPlugin plugin = MockBukkit.load(TestPlugin.class, Integer.valueOf(5));
		assertThat(plugin.extra, equalTo(5));
	}

	@Test
	void load_TestPluginWithExtraIncorrectParameter_ExceptionThrown()
	{
		MockBukkit.mock();
		assertThrows(RuntimeException.class, () -> MockBukkit.load(TestPlugin.class, "Hello"));
	}

	@Test
	void loadWith_SecondTextPluginAndResourceFileAsString_PluginLoaded()
	{
		MockBukkit.mock();
		SecondTestPlugin plugin = MockBukkit.loadWith(SecondTestPlugin.class, "second_plugin.yml");
		assertEquals("SecondTestPlugin", plugin.getName(), "Name was not loaded correctly");
	}

	@Test
	void loadSimple_SecondTextPlugin_PluginLoaded()
	{
		MockBukkit.mock();
		SecondTestPlugin plugin = MockBukkit.loadSimple(SecondTestPlugin.class);
		assertEquals("SecondTestPlugin", plugin.getName(), "Name was not set correctly");
		assertEquals("1.0.0", plugin.getDescription().getVersion(), "Version was not set correctly");
	}

	@Test
	void createMockPlugin_CreatesMockPlugin()
	{
		MockBukkit.mock();
		MockPlugin plugin = MockBukkit.createMockPlugin();
		assertEquals("MockPlugin", plugin.getName());
		assertEquals("1.0.0", plugin.getDescription().getVersion());
		assertTrue(plugin.isEnabled());
	}

	@Test
	void unload_PluginLoaded_PluginDisabled()
	{
		MockBukkit.mock();
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		assumeFalse(plugin.onDisableExecuted);
		MockBukkit.unmock();
		assertFalse(plugin.isEnabled());
		assertTrue(plugin.onDisableExecuted);
	}

	@Test
	void load_CanLoadPluginFromExternalSource_PluginLoaded()
	{
		MockBukkit.mock();
		MockBukkit.loadJar("extra/TestPlugin/TestPlugin.jar");
		Plugin[] plugins = MockBukkit.getMock().getPluginManager().getPlugins();
		assertThat(plugins.length, equalTo(1));
		assertThat(plugins[0].getName(), equalTo("TestPlugin"));
	}

	private static class CustomServerMock extends ServerMock
	{
	}
}
