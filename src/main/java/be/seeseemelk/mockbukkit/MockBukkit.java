package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Handles mocking the {@link Bukkit} server, along with containing some handy utility methods.
 */
public class MockBukkit
{

	private static @Nullable ServerMock mock = null;

	private MockBukkit()
	{
		throw new UnsupportedOperationException("Utility class");
	}

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
	 * Start mocking the {@code Bukkit} singleton. Also returns the {@link ServerMock} that was created for ease of
	 * use.
	 *
	 * @return The created {@link ServerMock}.
	 */
	public static @NotNull ServerMock mock()
	{
		return mock(new ServerMock());
	}

	/**
	 * Start mocking the {@code Bukkit} singleton. You can pass your own implementation of the {@link ServerMock}
	 * instance. The instance you passed is returned.
	 *
	 * @param <T>                      The mock implementation to use.
	 * @param serverMockImplementation your custom {@link ServerMock} implementation.
	 * @return The provided {@link ServerMock}.
	 */
	public static <T extends ServerMock> @NotNull T mock(@NotNull T serverMockImplementation)
	{
		if (mock != null)
		{
			throw new IllegalStateException("Already mocking");
		}

		mock = serverMockImplementation;

		// Hides the startup info message.
		Level defaultLevel = mock.getLogger().getLevel();
		mock.getLogger().setLevel(Level.WARNING);
		Bukkit.setServer(mock);
		mock.getLogger().setLevel(defaultLevel);

		return serverMockImplementation;
	}

	/**
	 * Get the mock server instance.
	 * If no instance exists one will be created. Otherwise existing one is returned
	 *
	 * @return The {@link ServerMock} instance.
	 */
	public static @Nullable ServerMock getOrCreateMock()
	{
		if (!isMocked())
		{
			mock();
		}

		return mock;
	}

	/**
	 * Get the mock server instance.
	 *
	 * @return The {@link ServerMock} instance or {@code null} if none is set up yet.
	 */
	public static @Nullable ServerMock getMock()
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
	 * Loads a plugin from a jar.
	 *
	 * @param path Path to the jar.
	 */
	public static void loadJar(@NotNull String path)
	{
		try
		{
			loadJar(new File(path));
		}
		catch (InvalidPluginException e)
		{
			// We *really* don't want to bother users with this error.
			// It's only supposed to be used during unit tests, so if
			// it fails it'll fail your test anyway.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads a plugin from a jar.
	 *
	 * @param jarFile Path to the jar.
	 * @throws InvalidPluginException If an exception occurred while loading a plugin.
	 */
	@SuppressWarnings("deprecation")
	public static void loadJar(@NotNull File jarFile) throws InvalidPluginException
	{
		mock.getPluginManager().loadPlugin(jarFile);
	}

	/**
	 * Loads and enables a plugin for mocking.
	 *
	 * @param <T>    The plugin's main class to load.
	 * @param plugin The plugin to load for mocking.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T load(@NotNull Class<T> plugin)
	{
		return load(plugin, new Object[0]);
	}

	/**
	 * Loads and enables a plugin for mocking.
	 *
	 * @param <T>        The plugin's main class to load.
	 * @param plugin     The plugin to load for mocking.
	 * @param parameters Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T load(@NotNull Class<T> plugin, Object @NotNull... parameters)
	{
		ensureMocking();
		PluginManagerMock pluginManager = mock.getPluginManager();
		JavaPlugin instance = pluginManager.loadPlugin(plugin, parameters);
		List<Permission> permissionList = instance.getDescription().getPermissions();
		List<Permission> permissionsToLoad = new ArrayList<>();
		permissionList.stream().filter(permission -> pluginManager.getPermission(permission.getName()) == null)
				.forEach(permissionsToLoad::add);

		pluginManager.addPermissions(permissionsToLoad);
		pluginManager.enablePlugin(instance);
		return plugin.cast(instance);
	}

	/**
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to use as an extra {@link InputStream}
	 * argument.
	 *
	 * @param <T>             The plugin's main class to load.
	 * @param plugin          The plugin to load for mocking.
	 * @param descriptionFile The plugin description file to use instead of {@code plugin.yml}.
	 * @param parameters      Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T loadWith(@NotNull Class<T> plugin,
			@NotNull PluginDescriptionFile descriptionFile, Object @NotNull... parameters)
	{
		ensureMocking();
		JavaPlugin instance = mock.getPluginManager().loadPlugin(plugin, descriptionFile, parameters);
		mock.getPluginManager().enablePlugin(instance);
		return plugin.cast(instance);
	}

	/**
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to use as an extra {@link File}
	 * argument.
	 *
	 * @param <T>              The plugin's main class to load.
	 * @param plugin           The plugin to load for mocking.
	 * @param descriptionInput The input stream to use instead of {@code plugin.yml}.
	 * @param parameters       Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T loadWith(@NotNull Class<T> plugin,
			@NotNull InputStream descriptionInput, Object... parameters)
	{
		try
		{
			return loadWith(plugin, new PluginDescriptionFile(descriptionInput), parameters);
		}
		catch (InvalidDescriptionException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to use as an extra {@link File}
	 * argument.
	 *
	 * @param <T>             The plugin's main class to load.
	 * @param plugin          The plugin to load for mocking.
	 * @param descriptionFile The file to use instead of {@code plugin.yml}.
	 * @param parameters      Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T loadWith(@NotNull Class<T> plugin, @NotNull File descriptionFile,
			Object... parameters)
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
	 * Loads and enables a plugin for mocking. It receives the {@code plugin.yml} to use as a resource in the default
	 * package from an extra {@link String} argument.
	 *
	 * @param <T>                 The plugin's main class to load.
	 * @param plugin              The plugin to load for mocking.
	 * @param descriptionFileName The name of the {@code plugin.yml} file as a system resource.
	 * @param parameters          Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T loadWith(@NotNull Class<T> plugin, String descriptionFileName,
			Object... parameters)
	{
		return loadWith(plugin, ClassLoader.getSystemResourceAsStream(descriptionFileName), parameters);
	}

	/* public static <T extends JavaPlugin> T loadWith(Class<T> plugin, File
	 * configFile, Object... parameters) { ensureMocking(); JavaPlugin instance =
	 * mock.getPluginManager().loadPlugin(plugin, parameters); YamlConfiguration
	 * yamlConfig = new YamlConfiguration();
	 *
	 * try { yamlConfig.load(configFile); } catch (IOException |
	 * InvalidConfigurationException e) { throw new RuntimeException(e); }
	 * instance.getConfig().setDefaults(yamlConfig);
	 * mock.getPluginManager().enablePlugin(instance); return plugin.cast(instance);
	 * } */

	/**
	 * Loads and enables a plugin for mocking. It will not load the {@code plugin.yml} file, but rather it will use a
	 * mock one. This can be useful in certain multi-project plugins where one cannot always access the
	 * {@code plugin.yml} file easily during testing.
	 *
	 * @param <T>        The plugin's main class to load.
	 * @param plugin     The plugin to load for mocking.
	 * @param parameters Extra parameters to pass on to the plugin constructor.
	 * @return An instance of the plugin's main class.
	 */
	public static <T extends JavaPlugin> @NotNull T loadSimple(@NotNull Class<T> plugin, Object @NotNull... parameters)
	{
		ensureMocking();
		PluginDescriptionFile description = new PluginDescriptionFile(plugin.getSimpleName(), "1.0.0",
				plugin.getCanonicalName());
		JavaPlugin instance = mock.getPluginManager().loadPlugin(plugin, description, parameters);
		mock.getPluginManager().enablePlugin(instance);
		return plugin.cast(instance);
	}

	/**
	 * Unload all loaded plugins.
	 */
	public static void unmock()
	{
		if (mock == null)
		{
			// We aren't mocking anyway
			return;
		}

		if (mock.getPluginManager() != null)
		{
			mock.getPluginManager().disablePlugins();
		}

		try
		{
			mock.getScheduler().shutdown();
		}
		finally
		{
			mock.getPluginManager().unload();
			setServerInstanceToNull();
		}
	}

	/**
	 * Creates a mock instance of a {@link JavaPlugin} implementation. This plugin offers no functionality, but it does
	 * allow a plugin that might enable and disable other plugins to be tested.
	 *
	 * @return An instance of a mock plugin.
	 */
	public static @NotNull MockPlugin createMockPlugin()
	{
		return createMockPlugin("MockPlugin");
	}

	/**
	 * Creates a mock instance of a {@link JavaPlugin} implementation and gives you a chance to name the plugin. This plugin offers no functionality, but it does
	 * allow a plugin that might enable and disable other plugins to be tested.
	 *
	 * @param pluginName A name of a new plugin.
	 * @return An instance of a mock plugin.
	 */
	public static @NotNull MockPlugin createMockPlugin(@NotNull String pluginName)
	{
		return createMockPlugin(pluginName, "1.0.0");
	}

	/**
	 * Creates a mock instance of a {@link JavaPlugin} implementation and gives you a chance to name the plugin. This plugin offers no functionality, but it does
	 * allow a plugin that might enable and disable other plugins to be tested.
	 *
	 * @param pluginName A name of a new plugin.
	 * @param pluginVersion The version of the new plugin.
	 * @return An instance of a mock plugin.
	 */
	public static @NotNull MockPlugin createMockPlugin(@NotNull String pluginName, @NotNull String pluginVersion)
	{
		ensureMocking();
		PluginDescriptionFile description = new PluginDescriptionFile(pluginName, pluginVersion,
				MockPlugin.class.getName());
		JavaPlugin instance = mock.getPluginManager().loadPlugin(MockPlugin.class, description, new Object[0]);
		mock.getPluginManager().enablePlugin(instance);
		return (MockPlugin) instance;
	}

	/**
	 * Throws an IllegalStateException when not mocked.
	 */
	public static void ensureMocking()
	{
		if (!isMocked())
		{
			throw new IllegalStateException("Not mocking");
		}
	}

}
