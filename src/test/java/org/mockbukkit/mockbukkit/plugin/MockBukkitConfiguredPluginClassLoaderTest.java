package org.mockbukkit.mockbukkit.plugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitConfiguredPluginClassLoaderTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private MockBukkitConfiguredPluginClassLoader classLoader;

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

	@Test
	void loadClass_notExists_throwsClassNotFoundException()
	{
		assertThrows(ClassNotFoundException.class, () -> classLoader.loadClass("invalid.group.id.TestPlugin"));
	}

	@Test
	void loadClass_delegatesToParentClassLoader()
	{
		assertThrows(ClassNotFoundException.class, () -> classLoader.loadClass("invalid.group.id.TestPlugin", true, true, true));
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
