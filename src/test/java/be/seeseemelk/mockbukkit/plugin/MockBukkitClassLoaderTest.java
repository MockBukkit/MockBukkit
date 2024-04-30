package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.TestPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitClassLoaderTest
{

	@Test
	void pluginHasRightClassLoader() throws ClassNotFoundException
	{
		System.out.println("After class loading");
		System.out.println(Class.forName("be.seeseemelk.mockbukkit.TestPlugin").getClassLoader());
		TestPlugin testPlugin = new TestPlugin();
		assertInstanceOf(MockBukkitPluginClassLoader.class, testPlugin.getClass().getClassLoader());
	}

}
