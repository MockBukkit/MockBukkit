package be.seeseemelk.mockbukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
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
		
		Level defaultLevel = mock.getLogger().getLevel();
		mock.getLogger().setLevel(Level.WARNING);
		Bukkit.setServer(mock);
		mock.getLogger().setLevel(defaultLevel);
		
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
	 * Checks if Bukkit is being mocked.
	 * 
	 * @return {@code true} if Bukkit is being mocked, {@code false} if it is not.
	 */
	public static boolean isMocked()
	{
		return mock != null;
	}
	
	/**
	 * Loads and enables a plugin for mocking.
	 * 
	 * @param <T>
	 *            The plugin's main class to load.
	 * @param plugin
	 *            The plugin to load for mocking.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> T load(Class<T> plugin)
	{
		return load(plugin, new Object[0]);
	}

	/**
	 * Loads and enables a plugin for mocking.
	 * 
	 * @param <T>
	 *            The plugin's main class to load.
	 * @param plugin
	 *            The plugin to load for mocking.
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
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
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to
	 * use as an extra {@link InputStream} argument.
	 * 
	 * @param <T>
	 *            The plugin's main class to load.
	 * @param plugin
	 *            The plugin to load for mocking.
	 * @param descriptionInput The input stream to use instead of {@code plugin.yml}
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends JavaPlugin> T loadWith(Class<T> plugin, InputStream descriptionInput, Object... parameters)
	{
		try
		{
			if (mock != null)
			{
				PluginDescriptionFile descriptionFile = new PluginDescriptionFile(descriptionInput);
				JavaPlugin instance = mock.getPluginManager().loadPlugin(plugin, descriptionFile, parameters);
				mock.getPluginManager().enablePlugin(instance);
				return (T) instance;
			}
			else
			{
				throw new IllegalStateException("Not mocking");
			}
		}
		catch (InvalidDescriptionException e)
		{
			// We don't want to have to add exception handling in a test to be able to run
			// it.
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to
	 * use as an extra {@link File} argument.
	 * 
	 * @param <T>
	 *            The plugin's main class to load.
	 * @param plugin
	 *            The plugin to load for mocking.
	 * @param descriptionFile The file to use instead of {@code plugin.yml}.
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> T loadWith(Class<T> plugin, File descriptionFile, Object... parameters)
	{
		try
		{
			return loadWith(plugin, new FileInputStream(descriptionFile), parameters);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to
	 * use as a resource in the default package from an extra {@link String}
	 * argument.
	 * 
	 * @param <T>
	 *            The plugin's main class to load.
	 * @param plugin
	 *            The plugin to load for mocking.
	 * @param descriptionFileName The name of the {@code plugin.yml} file as a system resource.
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> T loadWith(Class<T> plugin, String descriptionFileName, Object... parameters)
	{
		return loadWith(plugin, ClassLoader.getSystemResourceAsStream(descriptionFileName), parameters);
	}
	
	/**
	 * Loads and enables a plugin for mocking. It will not load the
	 * {@code plugin.yml} file, but rather it will use a mock one. This can be
	 * useful in certain multi-project plugins where one cannot always access the
	 * {@code plugin.yml} file easily during testing.
	 * 
	 * @param <T>
	 *            The plugin's main class to load.
	 * @param plugin
	 *            The plugin to load for mocking.
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends JavaPlugin> T loadSimple(Class<T> plugin, Object... parameters)
	{
		PluginDescriptionFile description = new PluginDescriptionFile(plugin.getSimpleName(), "1.0.0", plugin.getCanonicalName());
		if (mock != null)
		{
			JavaPlugin instance = mock.getPluginManager().loadPlugin(plugin, description, parameters);
			mock.getPluginManager().enablePlugin(instance);
			return (T) instance;
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
		if (mock != null && mock.getPluginManager() != null)
		{
			mock.getPluginManager().disablePlugins();
		}
		mock.getPluginManager().unload();
		setServerInstanceToNull();
	}
	
	/**
	 * Creates a mock instance of a {@link JavaPlugin} implementation. This plugin
	 * offers no functionality, but it does allow a plugin that might enable and
	 * disable other plugins to be tested.
	 * 
	 * @return An instance of a mock plugin.
	 */
	public static MockPlugin createMockPlugin()
	{
		if (mock != null)
		{
			PluginDescriptionFile description = new PluginDescriptionFile("MockPlugin", "1.0.0",
					MockPlugin.class.getName());
			JavaPlugin instance = mock.getPluginManager().loadPlugin(MockPlugin.class, description);
			mock.getPluginManager().enablePlugin(instance);
			return (MockPlugin) instance;
		}
		else
		{
			throw new IllegalStateException("Not mocking");
		}
	}
}
