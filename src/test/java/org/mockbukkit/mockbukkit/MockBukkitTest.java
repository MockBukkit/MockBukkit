package org.mockbukkit.mockbukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class MockBukkitTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.setServerInstanceToNull();
	}

	@AfterEach
	void tearDown()
	{
		if (MockBukkit.isMocked())
		{
			MockBukkit.unmock();
		}
	}

	@Test
	void paperApiFullVersion_IsReplaced()
	{
		assertNotNull(BuildParameters.PAPER_API_FULL_VERSION);
		assertNotEquals("{{ paperApiFullVersion }}", BuildParameters.PAPER_API_FULL_VERSION);
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
		assertEquals(5, plugin.extra);
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
	void createMockPlugin_CustomName()
	{
		MockBukkit.mock();
		MockPlugin plugin = MockBukkit.createMockPlugin("MyPlugin");
		assertEquals("MyPlugin", plugin.getName());
		assertTrue(plugin.isEnabled());
	}

	@Test
	void createMockPlugin_CustomName_CustomVersion()
	{
		MockBukkit.mock();
		MockPlugin plugin = MockBukkit.createMockPlugin("MyPlugin", "6.9.0");
		assertEquals("MyPlugin", plugin.getName());
		assertEquals("6.9.0", plugin.getDescription().getVersion());
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
		MockBukkit.loadJar("extra/TestPlugin/build/libs/TestPlugin.jar");
		Plugin[] plugins = MockBukkit.getMock().getPluginManager().getPlugins();
		assertEquals(1, plugins.length);
		assertEquals("TestPlugin", plugins[0].getName());
	}

	@Test
	void load_PluginWithConfigFile_ConfigFileParsed()
	{
		MockBukkit.mock();
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		FileConfiguration config = plugin.getConfig();
		String value = config.getString("foo");
		assertEquals("bar", value);
	}

	@Test
	void ensureMocking_Mocking_DoesNothing()
	{
		MockBukkit.mock();
		assertDoesNotThrow(MockBukkit::ensureMocking);
	}

	@Test
	void ensureMocking_NotMocking_ThrowsException()
	{
		assertThrows(IllegalStateException.class, MockBukkit::ensureMocking);
	}

	private static class CustomServerMock extends ServerMock
	{

	}

}
