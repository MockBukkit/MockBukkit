package org.mockbukkit.mockbukkit.plugin;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitURLClassLoaderTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private MockBukkitURLClassLoader urlClassLoader;
	private static File TEST_PLUGIN_FILE = new File("extra/TestPlugin/build/libs/TestPlugin.jar");
	private static File PLUGIN_YAML_FILE = new File("extra/TestPlugin/src/main/resources/plugin.yml");

	@BeforeEach
	void setUp() throws IOException, InvalidDescriptionException
	{
		PluginDescriptionFile pluginDescriptionFile;
		try (InputStream inputStream = new FileInputStream(PLUGIN_YAML_FILE))
		{
			pluginDescriptionFile = new PluginDescriptionFile(inputStream);
		}
		this.urlClassLoader = new MockBukkitURLClassLoader(TEST_PLUGIN_FILE, getClass().getClassLoader(), serverMock, pluginDescriptionFile, new File(serverMock.getPluginsFolder(), "TestPlugin"));
	}

	@Test
	void getConfiguration()
	{
		PluginMeta meta = urlClassLoader.getConfiguration();
		assertEquals("1.15", meta.getAPIVersion());
		assertTrue(meta.getAuthors().contains("Thorinwasher"));
		assertEquals("TestPlugin", meta.getName());
		assertEquals("org.mockbukkit.testplugin.TestPlugin", meta.getMainClass());
	}

	@Test
	void loadClass_exists()
	{
		assertDoesNotThrow(() -> urlClassLoader.loadClass("org.mockbukkit.testplugin.TestPlugin"));
	}

	@Test
	void loadClass_notExists()
	{
		assertThrows(ClassNotFoundException.class, () -> urlClassLoader.loadClass("invalid.group.id.TestPlugin"));
	}

	@Test
	void loadClass_delegateToOtherClassLoader()
	{
		assertDoesNotThrow(() -> urlClassLoader.loadClass("org.mockbukkit.mockbukkit.MockBukkit"));
	}

	@Test
	void getResource() throws IOException
	{
		try (InputStream inputStream1 = urlClassLoader.getResourceAsStream("plugin.yml"))
		{
			try (InputStream inputStream2 = new FileInputStream(PLUGIN_YAML_FILE))
			{
				byte[] bytes1 = inputStream1.readAllBytes();
				byte[] bytes2 = inputStream2.readAllBytes();
				assertArrayEquals(bytes1, bytes2);
			}
		}
	}

}
