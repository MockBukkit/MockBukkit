package be.seeseemelk.mockbukkit;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
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
	 * @param parameters Extra parameters to pass on to the plugin constructor.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends JavaPlugin> T load(Class<T> plugin, Object... parameters)
	{
		if (mock != null)
		{
			JavaPlugin instance = mock.getPluginManager().loadPlugin(plugin, parameters);
			mock.getPluginManager().enablePlugin(instance);
			return (T) instance;
		}
		else
		{
			throw new IllegalStateException("Not mocking");
		}
	}
	
	/**
	 * Loads and enables a plugin for mocking.
	 * 
	 * @param class1 The plugin to load for mocking.
	 * @param parameters Extra parameters to pass on to the plugin constructor.
	 */
	public static <T extends JavaPlugin> T load(Class<T> plugin)
	{
		return load(plugin, new Object[0]);
	}

	/**
	 * Unload all loaded plugins.
	 */
	public static void unload()
	{
		if (mock != null && mock.getPluginManager() != null)
		{
			for (Plugin plugin : mock.getPluginManager().getPlugins())
			{
				plugin.onDisable();
			}
		}
		setServerInstanceToNull();
	}
	
	/**
	 * Creates a mock instance of a {@link JavaPlugin} implementation.
	 */
	public static MockPlugin createMockPlugin()
	{
		if (mock != null)
		{
			PluginDescriptionFile description = new PluginDescriptionFile("MockPlugin", "1.0.0", MockPlugin.class.getName());
			JavaPlugin instance = mock.getPluginManager().loadPlugin(MockPlugin.class, description, null);
			mock.getPluginManager().enablePlugin(instance);
			return (MockPlugin) instance;
		}
		else
		{
			throw new IllegalStateException("Not mocking");
		}
	}
}























