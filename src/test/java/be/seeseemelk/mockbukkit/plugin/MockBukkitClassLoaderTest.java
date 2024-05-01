package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.TestPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitClassLoaderTest
{

	@Test
	void pluginHasRightClassLoader()
	{
		assertEquals(MockBukkitPluginClassLoader.class, TestPlugin.class.getClassLoader().getClass());
	}

}
