package org.mockbukkit.mockbukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.exception.PluginIOException;
import org.mockbukkit.mockbukkit.exception.PluginLoadException;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.plugin.SecondTestPlugin;
import org.mockbukkit.mockbukkit.plugin.TestPlugin;

import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent;

class MockBukkitTest
{

	private static final String TEST_PLUGIN_FILE_PATH = "extra/TestPlugin/build/libs/TestPlugin.jar";

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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(PluginEnableEvent.class, event -> event.getPlugin().equals(plugin)));
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
		assertThrows(PluginLoadException.class, () -> MockBukkit.load(TestPlugin.class, "Hello"));
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
		PluginMock plugin = MockBukkit.createMockPlugin();
		assertEquals("MockPlugin", plugin.getName());
		assertEquals("1.0.0", plugin.getDescription().getVersion());
		assertTrue(plugin.isEnabled());
	}

	@Test
	void createMockPlugin_CustomName()
	{
		MockBukkit.mock();
		PluginMock plugin = MockBukkit.createMockPlugin("MyPlugin");
		assertEquals("MyPlugin", plugin.getName());
		assertTrue(plugin.isEnabled());
	}

	@Test
	void createMockPlugin_CustomName_CustomVersion()
	{
		MockBukkit.mock();
		PluginMock plugin = MockBukkit.createMockPlugin("MyPlugin", "6.9.0");
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

	@Test
	void loadJar_file() throws InvalidPluginException
	{
		MockBukkit.mock();
		Plugin javaPlugin = MockBukkit.loadJar(new File(TEST_PLUGIN_FILE_PATH));
		assertNotNull(javaPlugin.getServer());
		assertNotNull(javaPlugin.getDataFolder());
		assertEquals("TestPlugin", javaPlugin.getName());
		FileConfiguration config = javaPlugin.getConfig();
		assertEquals("goo", config.getString("foo"));
		assertEquals("soo", config.getString("boo"));
		assertEquals("bas", config.getList("baa").getLast());
	}

	@Test
	void loadJar_fileName()
	{
		MockBukkit.mock();
		Plugin javaPlugin = MockBukkit.loadJar(TEST_PLUGIN_FILE_PATH);
		assertNotNull(javaPlugin.getServer());
		assertNotNull(javaPlugin.getDataFolder());
		assertEquals("TestPlugin", javaPlugin.getName());
		FileConfiguration config = javaPlugin.getConfig();
		assertEquals("goo", config.getString("foo"));
		assertEquals("soo", config.getString("boo"));
		assertEquals("bas", config.getList("baa").getLast());
	}

	private static class CustomServerMock extends ServerMock
	{

	}

	@Test
	void load_WithConfig_FileConfiguration()
	{
		FileConfiguration configuration = new YamlConfiguration();
		configuration.set("foo", "notbar");

		MockBukkit.mock();
		TestPlugin plugin = MockBukkit.loadWithConfig(TestPlugin.class, configuration);
		assertNotNull(plugin);

		FileConfiguration config = plugin.getConfig();
		String value = config.getString("foo");
		assertEquals("notbar", value);
	}

	@Test
	void load_WithConfig_File()
	{
		URL resource = this.getClass().getClassLoader().getResource("loadWithConfig/config_test.yml");
		if (resource == null)
		{
			fail();
		}
		File file = new File(resource.getFile());
		MockBukkit.mock();
		TestPlugin plugin = MockBukkit.loadWithConfig(TestPlugin.class, file);
		assertNotNull(plugin);

		FileConfiguration config = plugin.getConfig();
		String value = config.getString("foo");
		assertEquals("notbar", value);
	}

	@Test
	void load_WithConfig_InputStream() throws FileNotFoundException
	{
		URL resource = this.getClass().getClassLoader().getResource("loadWithConfig/config_test.yml");
		if (resource == null)
		{
			fail();
		}
		File file = new File(resource.getFile());
		try (InputStream inputStream = new FileInputStream(file))
		{
			MockBukkit.mock();
			TestPlugin plugin = MockBukkit.loadWithConfig(TestPlugin.class, inputStream);
			assertNotNull(plugin);

			FileConfiguration config = plugin.getConfig();
			String value = config.getString("foo");
			assertEquals("notbar", value);
		}
		catch (IOException e)
		{
			fail("Couldn't read config input stream", e);
		}

	}

	@Test
	void load_WithConfig_InputStream_FileNotExists() throws FileNotFoundException
	{

		try (InputStream inputStream = new ByteArrayInputStream("test data".getBytes()))
		{
			MockBukkit.mock();
			PluginLoadException runtimeException = assertThrows(PluginLoadException.class, () ->
			{
				MockBukkit.loadWithConfig(TestPlugin.class, inputStream);
			});
			assertEquals("Couldn't read config input stream", runtimeException.getMessage());
		}
		catch (IOException e)
		{
			fail("Couldn't read config input stream", e);
		}

	}

}
