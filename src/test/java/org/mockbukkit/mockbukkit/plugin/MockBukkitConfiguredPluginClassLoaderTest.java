package org.mockbukkit.mockbukkit.plugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitConfiguredPluginClassLoaderTest
{

	private static final File TEST_PLUGIN_FILE = new File("extra/TestPlugin/build/libs/TestPlugin.jar");
	private static final String TEST_PLUGIN_CLASS = "org.mockbukkit.testplugin.TestPlugin";

	@MockBukkitInject
	private ServerMock serverMock;
	private MockBukkitConfiguredPluginClassLoader classLoader;
	private JarFile jarFile;

	@BeforeEach
	void setUp()
	{
		PluginDescriptionFile description = new PluginDescriptionFile("TestPlugin", "1.0.0", TestPlugin.class.getName());
		this.classLoader = new MockBukkitConfiguredPluginClassLoader(
				serverMock,
				description,
				new File(serverMock.getPluginsFolder(), "TestPlugin"),
				new File(serverMock.getPluginsFolder(), "TestPlugin.jar")
		);
	}

	@AfterEach
	void tearDown() throws IOException
	{
		if (jarFile != null)
		{
			jarFile.close();
		}
	}

	@Test
	void loadClass_notExists_throwsClassNotFoundException()
	{
		assertThrows(ClassNotFoundException.class, () -> classLoader.loadClass("invalid.group.id.TestPlugin"));
	}

	@Test
	void loadClass_delegatesToParentClassLoader()
	{
		assertDoesNotThrow(() -> classLoader.loadClass("org.mockbukkit.mockbukkit.MockBukkit", true, true, true));
	}

	@Test
	void findClass_noJarFile_throwsClassNotFoundException()
	{
		assertThrows(ClassNotFoundException.class, () -> classLoader.findClass(TEST_PLUGIN_CLASS));
	}

	@Test
	void findClass_notContainedInJarFile_throwsClassNotFoundException() throws IOException
	{
		this.jarFile = new JarFile(TEST_PLUGIN_FILE);
		classLoader.setJarFile(jarFile);
		assertThrows(ClassNotFoundException.class, () -> classLoader.findClass("invalid.group.id.TestPlugin"));
	}

	@Test
	void findClass_containedInJarFile() throws IOException
	{
		this.jarFile = new JarFile(TEST_PLUGIN_FILE);
		classLoader.setJarFile(jarFile);

		Class<?> found = assertDoesNotThrow(() -> classLoader.findClass(TEST_PLUGIN_CLASS));
		assertEquals(TEST_PLUGIN_CLASS, found.getName());
		assertSame(classLoader, found.getClassLoader());
	}

	@Test
	void loadProxyClass_definesProxyOnThisClassLoader()
	{
		Class<? extends Plugin> proxy = classLoader.loadProxyClass(TestPlugin.class);
		assertNotNull(proxy);
		assertTrue(TestPlugin.class.isAssignableFrom(proxy));
		assertSame(classLoader, proxy.getClassLoader());
	}

}
