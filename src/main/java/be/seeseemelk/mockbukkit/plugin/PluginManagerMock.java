package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.PermissionManagerMock;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.exception.EventHandlerException;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import com.destroystokyo.paper.exception.ServerEventException;
import com.google.common.base.Preconditions;
import io.papermc.paper.plugin.PermissionManager;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.PluginCommandUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of a {@link PluginManager}.
 */
public class PluginManagerMock extends PermissionManagerMock implements PluginManager
{

	private static final Pattern VALID_PLUGIN_NAMES = Pattern.compile("^[A-Za-z0-9_.-]+$");

	private final @NotNull ServerMock server;
	private final List<Plugin> plugins = new ArrayList<>();
	private final List<PluginCommand> commands = new ArrayList<>();
	private final List<Event> events = new ArrayList<>();
	private File parentTemporaryDirectory;
	private final @NotNull Map<String, List<Listener>> listeners = new HashMap<>();

	/**
	 * Constructs a new {@link PluginManagerMock} for the provided {@link ServerMock}.
	 *
	 * @param server The server this is for.
	 */
	@ApiStatus.Internal
	@SuppressWarnings("deprecation")
	public PluginManagerMock(@NotNull ServerMock server)
	{
		Preconditions.checkNotNull(server, "Server cannot be null");
		this.server = server;
	}

	/**
	 * Should be called when the plugin manager is not used anymore.
	 */
	public void unload()
	{
		if (parentTemporaryDirectory == null)
			return;

		// Delete the temporary directory, from the deepest file to the root.
		try (Stream<Path> walk = Files.walk(parentTemporaryDirectory.toPath()))
		{
			walk.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		}
		catch (IOException e)
		{
			server.getLogger().log(Level.SEVERE, "Could not delete temporary directory", e);
		}
	}

	/**
	 * Asserts that at least one event conforms to the given predicate.
	 *
	 * @param message   The message to display when no event conforms.
	 * @param predicate The predicate to test against.
	 */
	public void assertEventFired(@Nullable String message, @NotNull Predicate<Event> predicate)
	{
		Preconditions.checkNotNull(predicate, "Predicate cannot be null");

		for (Event event : events)
		{
			if (predicate.test(event))
			{
				return;
			}
		}

		fail(message);
	}

	/**
	 * Asserts that at least one event conforms to the given predicate.
	 *
	 * @param predicate The predicate to test against.
	 */
	public void assertEventFired(@NotNull Predicate<Event> predicate)
	{
		Preconditions.checkNotNull(predicate, "Predicate cannot be null");
		assertEventFired("Event assert failed", predicate);
	}

	/**
	 * Asserts that there is at least one event of a certain class for which the predicate is true.
	 *
	 * @param <T>        The type of event that is expected.
	 * @param message    The message to display if no event is found.
	 * @param eventClass The class type that the event should be an instance of.
	 * @param predicate  The predicate to test the event against.
	 */
	public <T extends Event> void assertEventFired(@Nullable String message, @NotNull Class<T> eventClass,
			@NotNull Predicate<T> predicate)
	{
		Preconditions.checkNotNull(eventClass, "Class cannot be null");

		for (Event event : events)
		{
			if (eventClass.isInstance(event) && predicate.test(eventClass.cast(event)))
			{
				return;
			}
		}

		fail(message);
	}

	/**
	 * Asserts that there is at least one event of a certain class for which the predicate is true.
	 *
	 * @param <T>        The type of event that is expected.
	 * @param eventClass The class type that the event should be an instance of.
	 * @param predicate  The predicate to test the event against.
	 */
	public <T extends Event> void assertEventFired(@NotNull Class<T> eventClass, @NotNull Predicate<T> predicate)
	{
		Preconditions.checkNotNull(eventClass, "Class cannot be null");
		assertEventFired("No event of the correct class tested true", eventClass, predicate);
	}

	/**
	 * Asserts that a specific event or one of it's sub-events has been fired at least once.
	 *
	 * @param eventClass The class of the event to check for.
	 */
	public void assertEventFired(@NotNull Class<? extends Event> eventClass)
	{
		Preconditions.checkNotNull(eventClass, "Class cannot be null");
		assertEventFired("No event of that type has been fired", eventClass::isInstance);
	}

	/**
	 * Asserts that a specific event or one of it's sub-event has not been fired.
	 *
	 * @param eventClass The class of the event to check for.
	 */
	public void assertEventNotFired(@NotNull Class<? extends Event> eventClass)
	{
		Preconditions.checkNotNull(eventClass, "Class cannot be null");
		assertEventNotFired(eventClass,
				"An event of type " + eventClass.getSimpleName() + " has been fired when it shouldn't have been");
	}

	/**
	 * Asserts that a specific event or one of it's sub-event has not been fired.
	 *
	 * @param eventClass The class of the event to check for.
	 * @param message    The message to print when failed.
	 */
	public void assertEventNotFired(@NotNull Class<? extends Event> eventClass, @Nullable String message)
	{
		Preconditions.checkNotNull(eventClass, "Class cannot be null");
		for (Event event : this.events)
		{
			if (eventClass.isAssignableFrom(event.getClass()))
			{
				fail(message);
			}
		}
	}

	@Override
	public Plugin getPlugin(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		for (Plugin plugin : plugins)
		{
			if (name.equals(plugin.getName()))
			{
				return plugin;
			}
		}
		return null;
	}

	@Override
	public Plugin @NotNull [] getPlugins()
	{
		return plugins.toArray(new Plugin[0]);
	}

	/**
	 * Get a collection of all available commands.
	 *
	 * @return A collection of all available commands.
	 */
	public @NotNull Collection<PluginCommand> getCommands()
	{
		return Collections.unmodifiableList(commands);
	}

	/**
	 * Checks if a constructor is compatible with an array of types.
	 *
	 * @param constructor The constructor to check.
	 * @param types       The array of parameter types the constructor must support.
	 * @return {@code true} if the constructor is compatible, {@code false} if it isn't.
	 */
	private boolean isConstructorCompatible(@NotNull Constructor<?> constructor, @NotNull Class<?> @NotNull [] types)
	{
		Preconditions.checkNotNull(constructor, "Constructor cannot be null");
		Preconditions.checkNotNull(types, "Types cannot be null");

		Class<?>[] parameters = constructor.getParameterTypes();
		if (parameters.length < types.length)
			return false;
		for (int i = 0; i < types.length; i++)
		{
			Class<?> type = types[i];
			Preconditions.checkNotNull(type, "Type cannot be null");
			Class<?> parameter = parameters[i];
			if (!parameter.isAssignableFrom(type))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Looks for a compatible constructor of a plugin with a certain constructor.
	 *
	 * @param class1 The plugin class for which a constructor should be found.
	 * @param types  The types of parameters that the constructor should be able to except.
	 * @return A constructor that will take the given types.
	 * @throws NoSuchMethodException if no compatible constructor could be found.
	 */
	@SuppressWarnings("unchecked")
	private @NotNull Constructor<? extends JavaPlugin> getCompatibleConstructor(
			@NotNull Class<? extends JavaPlugin> class1, @NotNull Class<?> @NotNull [] types)
			throws NoSuchMethodException
	{
		Preconditions.checkNotNull(class1, "Class cannot be null");
		Preconditions.checkNotNull(types, "Types cannot be null");
		for (Constructor<?> constructor : class1.getDeclaredConstructors())
		{
			Class<?>[] parameters = constructor.getParameterTypes();
			if (parameters.length == types.length && isConstructorCompatible(constructor, types))
			{
				return (Constructor<? extends JavaPlugin>) constructor;
			}
		}

		StringBuilder parameters = new StringBuilder("[");
		for (Class<?> type : types)
			parameters.append(type.getName()).append(", ");
		String str = parameters.substring(0, parameters.length() - 2) + "]";
		throw new NoSuchMethodException(
				"No compatible constructor for " + class1.getName() + " with parameters " + str);
	}

	/**
	 * Creates a new unique temporary directory.
	 *
	 * @return The directory.
	 * @throws IOException If an IO error occurs.
	 */
	public @NotNull File getParentTemporaryDirectory() throws IOException
	{
		if (parentTemporaryDirectory == null)
		{
			Random random = ThreadLocalRandom.current();
			parentTemporaryDirectory = Files.createTempDirectory("MockBukkit-" + random.nextInt(0, Integer.MAX_VALUE))
					.toFile();
		}
		return parentTemporaryDirectory;
	}

	/**
	 * Tries to create a temporary directory.
	 *
	 * @param name The name of the directory to create.
	 * @return The created temporary directory.
	 * @throws IOException when the directory could not be created.
	 */
	public @NotNull File createTemporaryDirectory(@NotNull String name) throws IOException
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		File directory = new File(getParentTemporaryDirectory(), name);
		directory.mkdirs();
		return directory;
	}

	/**
	 * Tries to create a temporary plugin file.
	 *
	 * @param name The name of the plugin.
	 * @return The created temporary file.
	 * @throws IOException when the file could not be created.
	 */
	public @NotNull File createTemporaryPluginFile(@NotNull String name) throws IOException
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		File pluginFile = new File(getParentTemporaryDirectory(), name + ".jar");
		if (!pluginFile.exists() && !pluginFile.createNewFile())
		{
			throw new IOException("Could not create file " + pluginFile.getAbsolutePath());
		}
		return pluginFile;
	}

	/**
	 * Registers a plugin that has already been loaded. This is necessary to register plugins loaded from external jars.
	 *
	 * @param plugin The plugin that has been loaded.
	 */
	public void registerLoadedPlugin(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		addCommandsFrom(plugin);
		plugins.add(plugin);
		plugin.onLoad();
	}

	/**
	 * Load a plugin from a class. It will use the system resource {@code plugin.yml} as the resource file.
	 *
	 * @param description The {@link PluginDescriptionFile} that contains information about the plugin.
	 * @param class1      The plugin to load.
	 * @param parameters  Extra parameters to pass on to the plugin constructor. Must not be {@code null}.
	 * @return The loaded plugin.
	 */
	public @NotNull JavaPlugin loadPlugin(@NotNull Class<? extends JavaPlugin> class1,
			@NotNull PluginDescriptionFile description, @NotNull Object @NotNull [] parameters)
	{
		Preconditions.checkNotNull(class1, "Class cannot be null");
		Preconditions.checkNotNull(description, "Description cannot be null");
		Preconditions.checkNotNull(parameters, "Parameters cannot be null");
		try
		{
			class1 = createClassLoader(description).loadProxyClass(class1);

			List<Class<?>> types = new ArrayList<>();
			for (Object parameter : parameters)
			{
				Preconditions.checkNotNull(parameter, "Parameters cannot be null");
				types.add(parameter.getClass());
			}

			Constructor<? extends JavaPlugin> constructor = getCompatibleConstructor(class1,
					types.toArray(new Class<?>[0]));
			constructor.setAccessible(true);

			JavaPlugin plugin = constructor.newInstance(parameters);
			registerLoadedPlugin(plugin);
			return plugin;
		}
		catch (ReflectiveOperationException | IOException e)
		{
			throw new RuntimeException("Failed to instantiate plugin", e);
		}
	}

	private MockBukkitConfiguredPluginClassLoader createClassLoader(PluginDescriptionFile description)
			throws IOException
	{
		String name = description.getName();
		if (name.equalsIgnoreCase("bukkit") || name.equalsIgnoreCase("minecraft") || name.equalsIgnoreCase("mojang"))
		{
			throw new RuntimeException("Restricted Name");
		}
		if (!VALID_PLUGIN_NAMES.matcher(name).matches())
		{
			throw new RuntimeException("Invalid name. Must match " + VALID_PLUGIN_NAMES.pattern());
		}
		File dataFolder = createTemporaryDirectory(name + "-" + description.getVersion());
		File pluginFile = createTemporaryPluginFile(name + "-" + description.getVersion());
		return new MockBukkitConfiguredPluginClassLoader(server, description, dataFolder, pluginFile);
	}

	/**
	 * Load a plugin from a class. It will use the system resource {@code plugin.yml} as the resource file.
	 *
	 * @param class1     The plugin to load.
	 * @param parameters Extra parameters to pass on to the plugin constructor.
	 * @return The loaded plugin.
	 */
	public @NotNull JavaPlugin loadPlugin(@NotNull Class<? extends JavaPlugin> class1, Object @NotNull [] parameters)
	{
		try
		{
			PluginDescriptionFile description = findPluginDescription(class1);
			return loadPlugin(class1, description, parameters);
		}
		catch (IOException | InvalidDescriptionException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Tries to find the correct plugin.yml for a given plugin. It does this by opening each plugin.yml that it finds
	 * and comparing the 'main' property to the qualified name of the plugin class.
	 *
	 * @param class1 The class that is in a subpackage of where to get the file.
	 * @return The plugin description file.
	 * @throws IOException                 Thrown when the file wan't be found or loaded.
	 * @throws InvalidDescriptionException If the plugin description file is formatted incorrectly.
	 */
	private @NotNull PluginDescriptionFile findPluginDescription(@NotNull Class<? extends JavaPlugin> class1)
			throws IOException, InvalidDescriptionException
	{
		Preconditions.checkNotNull(class1, "Class cannot be null");
		Enumeration<URL> resources = class1.getClassLoader().getResources("plugin.yml");
		while (resources.hasMoreElements())
		{
			URL url = resources.nextElement();
			PluginDescriptionFile description = new PluginDescriptionFile(url.openStream());
			String mainClass = description.getMain();
			if (class1.getName().equals(mainClass))
				return description;
		}
		throw new FileNotFoundException("Could not find file plugin.yml. Maybe forgot to add the 'main' property?");
	}

	@Override
	public void callEvent(@NotNull Event event)
	{
		Preconditions.checkNotNull(event, "Event cannot be null");
		if (event.isAsynchronous() && server.isOnMainThread())
		{
			throw new IllegalStateException("Asynchronous Events cannot be called on the main Thread.");
		}

		events.add(event);
		HandlerList handlers = event.getHandlers();
		for (RegisteredListener listener : handlers.getRegisteredListeners())
		{
			callRegisteredListener(listener, event);
		}
	}

	/**
	 * This method invokes {@link #callEvent(Event)} from a different {@link Thread}
	 * using the {@link BukkitSchedulerMock}.
	 *
	 * @param event The asynchronous {@link Event} to call.
	 */
	public void callEventAsynchronously(@NotNull Event event)
	{
		callEventAsynchronously(event, null);
	}

	/**
	 * This method invokes {@link #callEvent(Event)} from a different {@link Thread}
	 * using the {@link BukkitSchedulerMock}.
	 *
	 * @param event The asynchronous {@link Event} to call.
	 * @param func  A function to invoke after the event has been called.
	 * @param <T>   The event type.
	 */
	public <T extends Event> void callEventAsynchronously(@NotNull T event, @Nullable Consumer<T> func)
	{
		Preconditions.checkNotNull(event, "Event cannot be null");
		if (!event.isAsynchronous())
		{
			throw new IllegalStateException("Synchronous Events cannot be called asynchronously.");
		}

		// Our Scheduler will call the Event on a dedicated Event Thread Executor
		server.getScheduler().executeAsyncEvent(event, func);
	}

	private void callRegisteredListener(@NotNull RegisteredListener registration, @NotNull Event event)
	{
		Preconditions.checkNotNull(registration, "Listener cannot be null");
		Preconditions.checkNotNull(event, "Event cannot be null");
		if (!registration.getPlugin().isEnabled())
		{
			return;
		}
		try
		{
			registration.callEvent(event);
		}
		catch (EventException eventException)
		{
			Throwable ex = eventException.getCause();
			if (!(event instanceof ServerExceptionEvent))
			{ // Don't cause an endless loop
				String msg = "Could not pass event " + event.getEventName() + " to "
						+ registration.getPlugin().getDescription().getFullName();
				callEvent(new ServerExceptionEvent(new ServerEventException(msg, ex, registration.getPlugin(),
						registration.getListener(), event)));
			}
			if (ex instanceof RuntimeException r)
			{
				throw r; // Rethrow same exception if possible
			}
			else
			{
				throw new EventHandlerException(ex);
			}
		}
	}

	@Override
	public void enablePlugin(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Preconditions.checkArgument(plugin instanceof JavaPlugin, "Not a JavaPlugin");
		if (!plugin.isEnabled())
		{
			JavaPluginUtils.setEnabled((JavaPlugin) plugin, true);
			callEvent(new PluginEnableEvent(plugin));
		}
	}

	/**
	 * Adds a configuration section to a command.
	 *
	 * @param command The command to add it to.
	 * @param name    The name of the section, as read in a configuration file.
	 * @param value   The value of the section, as parsed by {@link YamlConfiguration}
	 */
	private void addSection(@NotNull PluginCommand command, @NotNull String name, @UnknownNullability Object value)
	{
		Preconditions.checkNotNull(command, "Command cannot be null");
		Preconditions.checkNotNull(name, "Name cannot be null");
		switch (name)
		{
		case "description":
			Preconditions.checkNotNull(value, "Value cannot be null");
			Preconditions.checkArgument(value instanceof String, "Not a String");
			command.setDescription((String) value);
			break;
		case "aliases":
			if (value instanceof List<?>)
			{
				command.setAliases(((List<?>) value).stream().map(Object::toString).toList());
			}
			else if (value != null)
			{
				command.setAliases(Collections.singletonList(value.toString()));
			}
			else
			{
				command.setAliases(Collections.emptyList());
			}
			break;
		case "permission":
			Preconditions.checkArgument(value == null || value instanceof String, "Not a String");
			command.setPermission(value == null ? null : (String) value);
			break;
		case "permission-message":
			Preconditions.checkArgument(value == null || value instanceof String, "Not a String");
			command.setPermissionMessage(value == null ? null : (String) value);
			break;
		case "usage":
			Preconditions.checkNotNull(value, "Value cannot be null");
			Preconditions.checkArgument(value instanceof String, "Not a String");
			command.setUsage((String) value);
			break;
		default:
			throw new UnsupportedOperationException("Unknown section " + name + " with value '" + value
					+ "'. Are you sure this is allowed here? (Reference guide: https://docs.papermc.io/paper/dev/plugin-yml#commands)");
		}
	}

	/**
	 * Add commands from a certain plugin to the internal list of commands.
	 *
	 * @param plugin The plugin from which to read commands.
	 */
	protected void addCommandsFrom(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Map<String, Map<String, Object>> pluginCommands = plugin.getDescription().getCommands();
		for (Entry<String, Map<String, Object>> entry : pluginCommands.entrySet())
		{
			PluginCommand command = PluginCommandUtils.createPluginCommand(entry.getKey(), plugin);
			for (Entry<String, Object> section : entry.getValue().entrySet())
			{
				addSection(command, section.getKey(), section.getValue());
			}
			this.commands.add(command);
			this.server.getCommandMap().register(plugin.getName(), command);
		}
	}

	@Override
	public void registerInterface(@NotNull Class<? extends PluginLoader> loader) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPluginEnabled(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		boolean result = false;

		for (Plugin mockedPlugin : plugins)
		{
			if (mockedPlugin.getName().equals(name))
			{
				result = mockedPlugin.isEnabled();
			}
		}

		return result;
	}

	@Override
	public boolean isPluginEnabled(@Nullable Plugin plugin)
	{
		boolean result = false;

		for (Plugin mockedPlugin : plugins)
		{
			if (mockedPlugin.equals(plugin))
			{
				result = plugin.isEnabled();
			}
		}

		return result;
	}

	@Override
	public Plugin loadPlugin(@NotNull File file) throws UnknownDependencyException
	{
		try
		{
			JarFile jarFile = new JarFile(file);
			PluginDescriptionFile descriptionFile = new PluginDescriptionFile(
					jarFile.getInputStream(jarFile.getEntry("plugin.yml")));
			MockBukkitConfiguredPluginClassLoader classLoader = createClassLoader(descriptionFile);
			classLoader.setJarFile(jarFile);

			Class<?> pluginClass = classLoader.loadClass(descriptionFile.getMainClass(), true, false, false);
			JavaPlugin plugin = (JavaPlugin) pluginClass.getConstructor().newInstance();
			registerLoadedPlugin(plugin);
			return plugin;
		}
		catch (IOException | InvalidDescriptionException | ClassNotFoundException | NoSuchMethodException
				| InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public Plugin[] loadPlugins(@NotNull File directory)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void disablePlugins()
	{
		for (Plugin plugin : plugins)
			disablePlugin(plugin);
	}

	@Override
	public void clearPlugins()
	{
		disablePlugins();
		plugins.clear();
	}

	/**
	 * This method clears the history of {@link Event events}. Doing that can be very useful if you want to assert fresh
	 * events using {@link #assertEventFired(Class)} or similar.
	 */
	@SuppressWarnings("unused")
	public void clearEvents()
	{
		events.clear();
	}

	@Override
	public void registerEvents(@NotNull Listener listener, @NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(listener, "Listener cannot be null");
		Preconditions.checkNotNull(plugin, "Listener cannot be null");
		if (!plugin.isEnabled())
		{
			throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
		}
		addListener(listener, plugin);
		for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader()
				.createRegisteredListeners(listener, plugin).entrySet())
		{
			getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
		}

	}

	private void addListener(@NotNull Listener listener, @NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(listener, "Listener cannot be null");
		Preconditions.checkNotNull(plugin, "Listener cannot be null");
		List<Listener> l = listeners.getOrDefault(plugin.getName(), new ArrayList<>());
		if (!l.contains(listener))
		{
			l.add(listener);
			listeners.put(plugin.getName(), l);
		}
	}

	/**
	 * Unregisters all listeners for a plugin.
	 *
	 * @param plugin The plugin.
	 */
	public void unregisterPluginEvents(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Listener cannot be null");
		List<Listener> listListener = listeners.get(plugin.getName());
		if (listListener != null)
		{
			for (Listener l : listListener)
			{
				for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader()
						.createRegisteredListeners(l, plugin).entrySet())
				{
					getEventListeners(getRegistrationClass(entry.getKey())).unregister(plugin);
				}
			}
		}

	}

	@Override
	public void registerEvent(@NotNull Class<? extends Event> event, @NotNull Listener listener,
			@NotNull EventPriority priority, @NotNull EventExecutor executor, @NotNull Plugin plugin)
	{
		registerEvent(event, listener, priority, executor, plugin, false);
	}

	@Override
	public void registerEvent(@NotNull Class<? extends Event> event, @NotNull Listener listener,
			@NotNull EventPriority priority, @NotNull EventExecutor executor, @NotNull Plugin plugin,
			boolean ignoreCancelled)
	{
		Preconditions.checkNotNull(listener, "Listener cannot be null");
		Preconditions.checkNotNull(listener, "Listener cannot be null");
		Preconditions.checkNotNull(priority, "Priority cannot be null");
		Preconditions.checkNotNull(executor, "Executor cannot be null");
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		if (!plugin.isEnabled())
		{
			throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
		}
		addListener(listener, plugin);
		getEventListeners(event)
				.register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
	}

	private HandlerList getEventListeners(@NotNull Class<? extends Event> type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		try
		{
			Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList");
			method.setAccessible(true);
			return (HandlerList) method.invoke(null);
		}
		catch (Exception e)
		{
			throw new IllegalPluginAccessException(e.toString());
		}
	}

	private @NotNull Class<? extends Event> getRegistrationClass(@NotNull Class<? extends Event> clazz)
	{
		Preconditions.checkNotNull(clazz, "Class cannot be null");
		try
		{
			clazz.getDeclaredMethod("getHandlerList");
			return clazz;
		}
		catch (NoSuchMethodException e)
		{
			if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class)
					&& Event.class.isAssignableFrom(clazz.getSuperclass()))
			{
				return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
			}
			else
			{
				throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName()
						+ ". Static getHandlerList method required!");
			}
		}
	}

	@Override
	public void disablePlugin(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Preconditions.checkArgument(plugin instanceof JavaPlugin, "Not a JavaPlugin");
		if (!plugin.isEnabled())
			return;

		// Don't print the "disabling x plugin" message
		Level prevLevel = plugin.getLogger().getLevel();
		plugin.getLogger().setLevel(Level.WARNING);
		plugin.getPluginLoader().disablePlugin(plugin);
		plugin.getLogger().setLevel(prevLevel);

		unregisterPluginEvents(plugin);
		server.getScheduler().cancelTasks(plugin);
		server.getServicesManager().unregisterAll(plugin);
		server.getMessenger().unregisterIncomingPluginChannel(plugin);
		server.getMessenger().unregisterOutgoingPluginChannel(plugin);
		// todo: implement chunk tickets
		// for (World world : server.getWorlds())
		// {
		// world.removePluginChunkTickets(plugin);
		// }
	}

	/**
	 * Timings are used for event timings on a live server - they serve no purpose during a artificial test environ.
	 *
	 * @return boolean.false
	 */
	@Override
	public boolean useTimings()
	{
		return false;
	}

	@Override
	public boolean isTransitiveDependency(PluginMeta pluginMeta, PluginMeta dependencyConfig)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void overridePermissionManager(@NotNull Plugin plugin, @Nullable PermissionManager permissionManager)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
