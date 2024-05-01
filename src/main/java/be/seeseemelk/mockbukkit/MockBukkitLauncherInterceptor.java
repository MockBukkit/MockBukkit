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
		InterceptionThread<T> newThread = new InterceptionThread<>(invocation);
		newThread.setContextClassLoader(customClassLoader);
		newThread.start();
		try
		{
			newThread.join();
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		return newThread.getOutputValue();
	}

	@Override
	public void close()
	{
		// Nothing needs to be closed
	}

	private static class InterceptionThread<T> extends Thread
	{

		private final Invocation<T> invocation;
		private T outputValue = null;

		private InterceptionThread(LauncherInterceptor.Invocation<T> invocation)
		{
			this.invocation = invocation;
		}

		@Override
		public void run()
		{
			outputValue = invocation.proceed();
		}

		private T getOutputValue()
		{
			return outputValue;
		}

	}

}
