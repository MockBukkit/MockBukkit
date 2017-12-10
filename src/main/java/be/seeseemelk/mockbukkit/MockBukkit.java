package be.seeseemelk.mockbukkit;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MockBukkit
{
	private static ServerMock mock = null;

	/**
	 * Sets the global server singleton in {@link Bukkit} back to zero.
	 */
	protected static void setServerInstanceToNull()
	{
		try
		{
			Field server = Bukkit.class.getDeclaredField("server");
			server.setAccessible(true);
			server.set(null, null);
			mock = null;
		}
		catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Start mocking the <code>Bukkit</code> singleton. Also returns the
	 * {@link ServerMock} that was created for ease of use.
	 * 
	 * @return The created {@link ServerMock}.
	 */
	public static ServerMock mock()
	{
		if (mock != null)
		{
			throw new IllegalStateException("Already mocking");
		}

		mock = new ServerMock();
		Bukkit.setServer(mock);
		return mock;
	}

	/**
	 * Get the mock server instance.
	 * 
	 * @return The {@link ServerMock} instance or {@code null} if none is set up
	 *         yet.
	 */
	public static ServerMock getMock()
	{
		return mock;
	}

	/**
	 * Loads and enables a plugin for mocking.
	 * 
	 * @param class1 The plugin to load for mocking.
	 */
	public static JavaPlugin load(Class<? extends JavaPlugin> plugin)
	{
		if (mock != null)
		{
			JavaPlugin instance = mock.getPluginManager().loadPlugin(plugin);
			mock.getPluginManager().enablePlugin(instance);
			return instance;
		}
		else
		{
			throw new IllegalStateException("Not mocking");
		}
	}

	/**
	 * Unload all loaded plugins.
	 */
	public static void unload()
	{
		for (Plugin plugin : mock.getPluginManager().getPlugins())
		{
			plugin.onDisable();
		}
		setServerInstanceToNull();
	}
}























