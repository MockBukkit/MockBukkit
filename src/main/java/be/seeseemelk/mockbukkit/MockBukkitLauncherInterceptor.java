package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.plugin.MockBukkitClassLoader;
import org.jetbrains.annotations.ApiStatus;
import org.junit.platform.launcher.LauncherInterceptor;

@ApiStatus.Internal
public class MockBukkitLauncherInterceptor implements LauncherInterceptor
{

	private final MockBukkitClassLoader customClassLoader;

	public MockBukkitLauncherInterceptor()
	{
		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		this.customClassLoader = new MockBukkitClassLoader(parent);
	}

	@Override
	public <T> T intercept(LauncherInterceptor.Invocation<T> invocation)
	{
		Thread currentThread = Thread.currentThread();
		ClassLoader originalClassLoader = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(customClassLoader);
		try
		{
			return invocation.proceed();
		}
		finally
		{
			currentThread.setContextClassLoader(originalClassLoader);
		}
	}

	@Override
	public void close()
	{
		// Nothing needs to be closed
	}

}
