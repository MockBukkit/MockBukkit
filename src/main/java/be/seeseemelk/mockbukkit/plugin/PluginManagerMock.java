package be.seeseemelk.mockbukkit.plugin;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.PluginCommandUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.JavaPluginUtils;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;

public class PluginManagerMock implements PluginManager
{
	private final ServerMock server;
	private final JavaPluginLoader loader;
	private final List<Plugin> plugins = new ArrayList<>();
	private final List<PluginCommand> commands = new ArrayList<>();
	private final List<Event> events = new ArrayList<>();
	private final List<File> temporaryFiles = new LinkedList<>();
	private final List<Permission> permissions = new ArrayList<>();
	private final Map<Permissible, Set<String>> permissionSubscriptions = new HashMap<>();

	private final List<Class<?>> pluginConstructorTypes = Arrays.asList(JavaPluginLoader.class,
	        PluginDescriptionFile.class, File.class, File.class);

	@SuppressWarnings("deprecation")
	public PluginManagerMock(@NotNull ServerMock server)
	{
		this.server = server;
		loader = new JavaPluginLoader(this.server);
	}

	/**
	 * Should be called when the plugin manager is not used anymore.
	 */
	public void unload()
	{
		for (File file : temporaryFiles)
		{
			try
			{
				FileUtils.forceDelete(file);
			}
			catch (IOException e)
			{
				System.err.println("Could not remove file");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Asserts that at least one event conforms to the given predicate.
	 *
	 * @param message   The message to display when no event conforms.
	 * @param predicate The predicate to test against.
	 */
	public void assertEventFired(@NotNull String message, @NotNull Predicate<Event> predicate)
	{
		for (Event event : events)
		{
			if (predicate.test(event))
				return;
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
	@SuppressWarnings("unchecked")
	public <T extends Event> void assertEventFired(String message, Class<T> eventClass, Predicate<T> predicate)
	{
		for (Event event : events)
		{
			if (eventClass.isInstance(event) && predicate.test((T) event))
				return;
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
	public <T extends Event> void assertEventFired(Class<T> eventClass, Predicate<T> predicate)
	{
		assertEventFired("No event of the correct class tested true", eventClass, predicate);
	}

	/**
	 * Asserts that a specific event or once of it's sub-events has been fired at least once.
	 *
	 * @param eventClass The class of the event to check for.
	 */
	public void assertEventFired(@NotNull Class<? extends Event> eventClass)
	{
		assertEventFired("No event of that type has been fired", eventClass::isInstance);
	}

	@Override
	public Plugin getPlugin(@NotNull String name)
	{
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
	public Plugin[] getPlugins()
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
	 * @param types       The array of parameter types the constructor must support. Note that the first 4 parameters
	 *                    should be an exact match while the rest don't have to be.
	 * @return {@code true} if the constructor is compatible, {@code false} if it isn't.
	 */
	private boolean isConstructorCompatible(@NotNull Constructor<?> constructor, @NotNull Class<?>[] types)
	{
		Class<?>[] parameters = constructor.getParameterTypes();
		for (int i = 0; i < types.length; i++)
		{
			Class<?> type = types[i];
			Class<?> parameter = parameters[i];
			if (i < 4)
			{
				if (!type.equals(parameter))
				{
					return false;
				}
			}
			else if (!parameter.isAssignableFrom(type))
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
	 * @param types  The types of parameters that the constructor should be able to except. Note that the first 4
	 *               parameters should be an exact match while the rest don't have to be.
	 * @return A constructor that will take the given types.
	 * @throws NoSuchMethodException if no compatible constructor could be found.
	 */
	@SuppressWarnings("unchecked")
	private Constructor<? extends JavaPlugin> getCompatibleConstructor(Class<? extends JavaPlugin> class1,
	        Class<?>[] types) throws NoSuchMethodException
	{
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
	 * Tries to create a temporary directory.
	 *
	 * @param name The name of the directory to create.
	 * @return The created temporary directory.
	 * @throws IOException when the directory could not be created.
	 */
	private @NotNull File createTemporaryDirectory(@NotNull String name) throws IOException
	{
		Random random = ThreadLocalRandom.current();
		File directory = Files.createTempDirectory(name + "-" + random.nextInt()).toFile();
		temporaryFiles.add(directory);
		return directory;
	}

	/**
	 * Tries to create a temporary plugin file.
	 *
	 * @param name The name of the plugin.
	 * @return The created temporary file.
	 * @throws IOException when the file could not be created.
	 */
	private @NotNull File createTemporaryPluginFile(@NotNull String name) throws IOException
	{
		Random random = ThreadLocalRandom.current();
		File pluginFile = Files.createTempFile(name + "-" + random.nextInt(), ".jar").toFile();
		pluginFile.createNewFile();
		temporaryFiles.add(pluginFile);
		return pluginFile;
	}

	/**
	 * Registers a plugin that has already been loaded. This is necessary to register plugins loaded from external jars.
	 *
	 * @param plugin The plugin that has been loaded.
	 */
	public void registerLoadedPlugin(@NotNull Plugin plugin)
	{
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
	public JavaPlugin loadPlugin(Class<? extends JavaPlugin> class1, PluginDescriptionFile description,
	                             Object[] parameters)
	{
		try
		{
			List<Class<?>> types = new ArrayList<>(pluginConstructorTypes);
			for (Object parameter : parameters)
			{
				types.add(parameter.getClass());
			}

			Constructor<? extends JavaPlugin> constructor = getCompatibleConstructor(class1,
			        types.toArray(new Class<?>[0]));
			constructor.setAccessible(true);

			Object[] arguments = new Object[types.size()];
			arguments[0] = loader;
			arguments[1] = description;
			arguments[2] = createTemporaryDirectory(
			                   "MockBukkit-" + description.getName() + "-" + description.getVersion());
			arguments[3] = createTemporaryPluginFile(
			                   "MockBukkit-" + description.getName() + "-" + description.getVersion());
			System.arraycopy(parameters, 0, arguments, 4, parameters.length);

			JavaPlugin plugin = constructor.newInstance(arguments);
			registerLoadedPlugin(plugin);
			return plugin;
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
			        | IllegalArgumentException | IOException | InvocationTargetException e)
		{
			throw new RuntimeException("Failed to instantiate plugin", e);
		}
	}

	/**
	 * Load a plugin from a class. It will use the system resource {@code plugin.yml} as the resource file.
	 *
	 * @param description The {@link PluginDescriptionFile} that contains information about the plugin.
	 * @param class1      The plugin to load.
	 * @return The loaded plugin.
	 */
	public JavaPlugin loadPlugin(Class<? extends JavaPlugin> class1, PluginDescriptionFile description)
	{
		return loadPlugin(class1, description, new Object[0]);
	}

	/**
	 * Load a plugin from a class. It will use the system resource {@code plugin.yml} as the resource file.
	 *
	 * @param class1     The plugin to load.
	 * @param parameters Extra parameters to pass on to the plugin constructor.
	 * @return The loaded plugin.
	 */
	public JavaPlugin loadPlugin(Class<? extends JavaPlugin> class1, Object[] parameters)
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
	private PluginDescriptionFile findPluginDescription(Class<? extends JavaPlugin> class1)
	throws IOException, InvalidDescriptionException
	{
		Enumeration<URL> resources = class1.getClassLoader().getResources("plugin.yml");
		while (resources.hasMoreElements())
		{
			URL url = resources.nextElement();
			PluginDescriptionFile description = new PluginDescriptionFile(url.openStream());
			String mainClass = description.getMain();
			if (class1.getName().equals(mainClass))
				return description;
		}
		throw new FileNotFoundException(
		    "Could not find file plugin.yml. Maybe forgot to add the 'main' property?");
	}

	@Override
	public void callEvent(@NotNull Event event)
	{
		if (event.isAsynchronous() && server.isOnMainThread())
		{
			throw new IllegalStateException("Asynchronous Events cannot be called on the main Thread.");
		}

		events.add(event);
		HandlerList handlers = event.getHandlers();
		RegisteredListener[] listeners = handlers.getRegisteredListeners();
		for (RegisteredListener l : listeners)
		{
			callRegisteredListener(l, event);
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
		if (!event.isAsynchronous())
		{
			throw new IllegalStateException("Synchronous Events cannot be called asynchronously.");
		}

		// Our Scheduler will call the Event on a dedicated Event Thread Executor
		server.getScheduler().executeAsyncEvent(event);
	}

	private void callRegisteredListener(@NotNull RegisteredListener registration, @NotNull Event event)
	{
		if (!registration.getPlugin().isEnabled())
		{
			return;
		}
		try
		{
			registration.callEvent(event);
		}
		catch (AuthorNagException ex)
		{
			Plugin plugin = registration.getPlugin();
			if (plugin.isNaggable())
			{
				plugin.setNaggable(false);
				server.getLogger().log(Level.SEVERE, String.format(
				                           "Nag author(s): '%s' of '%s' about the following: %s",
				                           plugin.getDescription().getAuthors(),
				                           plugin.getDescription().getFullName(),
				                           ex.getMessage()
				                       ));
			}
		}
		catch (Throwable ex)
		{
			String msg = "Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getFullName();
			server.getLogger().log(Level.SEVERE, msg, ex);
		}
	}




	@Override
	public void enablePlugin(@NotNull Plugin plugin)
	{
		if (plugin instanceof JavaPlugin)
		{
			if (!plugin.isEnabled())
			{
				JavaPluginUtils.setEnabled((JavaPlugin) plugin, true);
				callEvent(new PluginEnableEvent(plugin));
			}
		}
		else
		{
			throw new IllegalArgumentException("Not a JavaPlugin");
		}
	}

	/**
	 * Adds a configuration section to a command.
	 *
	 * @param command The command to add it to.
	 * @param name    The name of the section, as read in a configuration file.
	 * @param value   The value of the section, as parsed by {@link YamlConfiguration}
	 */
	private void addSection(PluginCommand command, String name, Object value)
	{
		switch (name)
		{
		case "description":
			command.setDescription((String) value);
			break;
		case "aliases":
			if (value instanceof List<?>)
			{
				command.setAliases(
				    ((List<?>) value).stream().map(Object::toString).collect(Collectors.toList()));
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
			command.setPermission((String) value);
			break;
		case "permission-message":
			command.setPermissionMessage((String) value);
			break;
		case "usage":
			command.setUsage((String) value);
			break;
		default:
			throw new UnsupportedOperationException("Unknown section " + value);
		}
	}

	/**
	 * Add commands from a certain plugin to the internal list of commands.
	 *
	 * @param plugin The plugin from which to read commands.
	 */
	protected void addCommandsFrom(Plugin plugin)
	{
		Map<String, Map<String, Object>> commands = plugin.getDescription().getCommands();
		for (Entry<String, Map<String, Object>> entry : commands.entrySet())
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
	public boolean isPluginEnabled(Plugin plugin)
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
	public Plugin loadPlugin(@NotNull File file)
	throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		if (!plugin.isEnabled())
		{
			throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
		}

		for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet())
		{
			getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
		}
	}

	@Override
	public void registerEvent(@NotNull Class<? extends Event> event, @NotNull Listener listener, @NotNull EventPriority priority,
	                          @NotNull EventExecutor executor, @NotNull Plugin plugin)
	{
		registerEvent(event, listener, priority, executor, plugin, false);
	}

	@Override
	public void registerEvent(@NotNull Class<? extends Event> event, @NotNull Listener listener, @NotNull EventPriority priority,
	                          @NotNull EventExecutor executor, @NotNull Plugin plugin, boolean ignoreCancelled)
	{
		Validate.notNull(listener, "Listener cannot be null");
		Validate.notNull(priority, "Priority cannot be null");
		Validate.notNull(executor, "Executor cannot be null");
		Validate.notNull(plugin, "Plugin cannot be null");
		if (!plugin.isEnabled())
		{
			throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
		}
		getEventListeners(event).register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
	}

	private HandlerList getEventListeners(Class<? extends Event> type)
	{
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

	private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz)
	{
		try
		{
			clazz.getDeclaredMethod("getHandlerList");
			return clazz;
		}
		catch (NoSuchMethodException e)
		{
			if (clazz.getSuperclass() != null
			        && !clazz.getSuperclass().equals(Event.class)
			        && Event.class.isAssignableFrom(clazz.getSuperclass()))
			{
				return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
			}
			else
			{
				throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
			}
		}
	}

	@Override
	public void disablePlugin(@NotNull Plugin plugin)
	{
		if (plugin instanceof JavaPlugin)
		{
			if (plugin.isEnabled())
			{
				JavaPluginUtils.setEnabled((JavaPlugin) plugin, false);
				callEvent(new PluginDisableEvent(plugin));
			}
		}
		else
		{
			throw new IllegalArgumentException("Not a JavaPlugin");
		}
	}

	@Override
	public Permission getPermission(@NotNull String name)
	{
		return permissions.stream().filter(permission -> permission.getName().equals(name)).findFirst().orElse(null);
	}

	@Override
	public void addPermission(@NotNull Permission perm)
	{
		permissions.add(perm);
	}

	@Override
	public void removePermission(@NotNull Permission perm)
	{
		permissions.remove(perm);
	}

	@Override
	public void removePermission(@NotNull String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<Permission> getDefaultPermissions(boolean op)
	{
		Set<Permission> permissions = new HashSet<>();
		for (Permission permission : this.permissions)
		{
			PermissionDefault permissionDefault = permission.getDefault();
			if (permissionDefault == PermissionDefault.TRUE)
				permissions.add(permission);
			else if (op && permissionDefault == PermissionDefault.OP)
				permissions.add(permission);
		}
		return permissions;
	}

	@Override
	public void recalculatePermissionDefaults(@NotNull Permission perm)
	{

	}

	/**
	 * Gets a set of permissions that a {@link Permissible} is subscribed to.
	 *
	 * @param permissible The {@link Permissible} to check.
	 * @return A {@link Set} of permissions the permissible is subscribed to. Is the {@link Permissible} isn't
	 *         subscribed to any, returns an empty set.
	 */
	private Set<String> getPermissionSubscriptions(Permissible permissible)
	{
		if (permissionSubscriptions.containsKey(permissible))
		{
			return permissionSubscriptions.get(permissible);
		}
		else
		{
			Set<String> subscriptions = new HashSet<>();
			permissionSubscriptions.put(permissible, subscriptions);
			return subscriptions;
		}
	}

	@Override
	public void subscribeToPermission(@NotNull String permission, @NotNull Permissible permissible)
	{
		getPermissionSubscriptions(permissible).add(permission);
	}

	@Override
	public void unsubscribeFromPermission(@NotNull String permission, @NotNull Permissible permissible)
	{
		getPermissionSubscriptions(permissible).remove(permission);
	}

	@Override
	public @NotNull Set<Permissible> getPermissionSubscriptions(@NotNull String permission)
	{
		Set<Permissible> subscriptions = new HashSet<>();
		for (Entry<Permissible, Set<String>> entry : permissionSubscriptions.entrySet())
		{
			Permissible permissible = entry.getKey();
			Set<String> permissions = entry.getValue();
			if (permissions.contains(permission))
			{
				subscriptions.add(permissible);
			}
		}
		return subscriptions;
	}

	@Override
	public void subscribeToDefaultPerms(boolean op, @NotNull Permissible permissible)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean op, @NotNull Permissible permissible)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<Permissible> getDefaultPermSubscriptions(boolean op)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<Permission> getPermissions()
	{
		return Collections.unmodifiableSet(new HashSet<>(permissions));
	}

	/**
	 * Timings are used for event timings on a live server - they serve no purpose during a artificial test environ.
	 * @return boolean.false
	 */
	@Override
	public boolean useTimings()
	{
		return false;
	}

}
