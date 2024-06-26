package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.plugin.MockBukkitClassLoader;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.LauncherConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MockBukkitLauncherInterceptorTest
{

	@Test
	void modifiedClassLoader()
	{
		assertInstanceOf(MockBukkitClassLoader.class, Thread.currentThread().getContextClassLoader());
	}

	@Test
	void propertySet()
	{
		assertEquals("true", System.getProperty(LauncherConstants.ENABLE_LAUNCHER_INTERCEPTORS));
	}

}
