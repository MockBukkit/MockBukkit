package be.seeseemelk.mockbukkit.plugin;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.PluginCommandUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.JavaPluginUtils;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class PluginManagerMock implements PluginManager
{
	private final ServerMock server;
	private final List<Plugin> plugins = new ArrayList<>();
	private final JavaPluginLoader loader;
	private final List<PluginCommand> commands = new ArrayList<>();
	private final Map<Plugin, List<ListenerEntry>> eventListeners = new HashMap<>();
	private final List<Event> events = new ArrayList<>();
	private final List<File> temporaryFiles = new LinkedList<>();
	private final List<Class<?>> pluginConstructorTypes = Arrays.asList(JavaPluginLoader.class,
			PluginDescriptionFile.class, File.class, File.class);
	private final List<Permission> permissions = new ArrayList<>();
	private final Map<Permissible, Set<String>> permissionSubscriptions = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public PluginManagerMock(ServerMock server)
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
				FileUtils.deleteDirectory(file);
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
	 * @param message The message to display when no event conforms.
	 * @param predicate The predicate to test against.
	 */
	public void assertEventFired(String message, Predicate<Event> predicate)
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
	 * @param predicate The predicate to test against.
	 */
	public void assertEventFired(Predicate<Event> predicate)
	{
		assertEventFired("Event assert failed", predicate);
	}
	
	/**
	 * Asserts that there is at least one event of a certain class for which the predicate is true.
	 * @param message The message to display if no event is found.
	 * @param eventClass The class type that the event should be an instance of.
	 * @param predicate The predicate to test the event against.
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
	 * @param eventClass The class type that the event should be an instance of.
	 * @param predicate The predicate to test the event against.
	 */
	public <T extends Event> void assertEventFired(Class<T> eventClass, Predicate<T> predicate)
	{
		assertEventFired("No event of the correct class tested true", eventClass, predicate);
	}
	
	/**
	 * Asserts that a specific event or once of it's sub-events has been fired at
	 * least once.
	 * 
	 * @param eventClass
	 *            The class of the event to check for.
	 */
	public void assertEventFired(Class<? extends Event> eventClass)
	{
		assertEventFired("No event of that type has been fired", event -> eventClass.isInstance(event));
	}
	
	@Override
	public Plugin getPlugin(String name)
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
		return plugins.toArray(new Plugin[plugins.size()]);
	}
	
	/**
	 * Get a collection of all available commands.
	 * 
	 * @return A collection of all available commands.
	 */
	public Collection<PluginCommand> getCommands()
	{
		return Collections.unmodifiableList(commands);
	}
	
	/**
	 * Checks if a constructor is compatible with an array of types.
	 * 
	 * @param constructor
	 *            The constructor to check.
	 * @param types
	 *            The array of parameter types the constructor must support. Note
	 *            that the first 4 parameters should be an exact match while the
	 *            rest don't have to be.
	 * @return {@code true} if the constructor is compatible, {@code false} if it
	 *         isn't.
	 */
	private boolean isConstructorCompatible(Constructor<?> constructor, Class<?>[] types)
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
	 * @param class1
	 *            The plugin class for which a constructor should be found.
	 * @param types
	 *            The types of parameters that the constructor should be able to
	 *            except. Note that the first 4 parameters should be an exact match
	 *            while the rest don't have to be.
	 * @return A constructor that will take the given types.
	 * @throws NoSuchMethodException
	 *             if no compatible constructor could be found.
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
		throw new NoSuchMethodException("No compatible constructor for " + class1.getName() + " with parameters " + str);
	}
	
	/**
	 * Tries to create a temporary directory.
	 * 
	 * @param name
	 *            The name of the directory to create.
	 * @return The created temporary directory.
	 * @throws IOException
	 *             when the directory could not be created.
	 */
	private File createTemporaryDirectory(String name) throws IOException
	{
		Random random = new Random();
		File directory = File.createTempFile(name + "-" + random.nextInt(), ".d");
		if (!directory.delete())
			throw new IOException("Could not create temporary directory: file could not be removed");
		if (!directory.mkdir())
			throw new IOException("Could not create temporary directory: directory could not be created");
		temporaryFiles.add(directory);
		return directory;
	}
	
	/**
	 * Load a plugin from a class. It will use the system resource
	 * {@code plugin.yml} as the resource file.
	 * 
	 * @param description
	 *            The {@link PluginDescriptionFile} that contains information about
	 *            the plugin.
	 * @param class1
	 *            The plugin to load.
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor. Must not be
	 *            {@code null}.
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
			System.arraycopy(parameters, 0, arguments, 4, parameters.length);
			
			JavaPlugin plugin = constructor.newInstance(arguments);
			addCommandsFrom(plugin);
			plugins.add(plugin);
			plugin.onLoad();
			return plugin;
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | IOException | InvocationTargetException e)
		{
			throw new RuntimeException("Failed to instantiate plugin", e);
		}
	}
	
	/**
	 * Load a plugin from a class. It will use the system resource
	 * {@code plugin.yml} as the resource file.
	 * 
	 * @param description
	 *            The {@link PluginDescriptionFile} that contains information about
	 *            the plugin.
	 * @param class1
	 *            The plugin to load.
	 * @return The loaded plugin.
	 */
	public JavaPlugin loadPlugin(Class<? extends JavaPlugin> class1, PluginDescriptionFile description)
	{
		return loadPlugin(class1, description, new Object[0]);
	}
	
	/**
	 * Load a plugin from a class. It will use the system resource
	 * {@code plugin.yml} as the resource file.
	 * 
	 * @param class1
	 *            The plugin to load.
	 * @param parameters
	 *            Extra parameters to pass on to the plugin constructor.
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
	 * Tries to find the correct plugin.yml for a given plugin.
	 * It does this by opening each plugin.yml that it finds and
	 * comparing the 'main' property to the qualified name of the
	 * plugin class.
	 * @param class1 The class that is in a subpackage of where to get the file.
	 * @return The plugin description file.
	 * @throws IOException Thrown when the file wan't be found or loaded.
	 * @throws InvalidDescriptionException If the plugin description file is formatted incorrectly.
	 */
	private PluginDescriptionFile findPluginDescription(Class<? extends JavaPlugin> class1) throws IOException, InvalidDescriptionException
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
		throw new FileNotFoundException(String.format("Could not find file plugin.yml. Maybe forgot to add the 'main' property?"));
	}
	
	@Override
	public void callEvent(Event event) throws IllegalStateException
	{
		events.add(event);
		for (List<ListenerEntry> listeners : eventListeners.values())
		{
			for (ListenerEntry entry : listeners)
			{
				if (entry.isCompatibleFor(event))
				{
					entry.invokeUnsafe(event);
				}
			}
		}
	}
	
	@Override
	public void registerEvents(Listener listener, Plugin plugin)
	{
		if (!eventListeners.containsKey(plugin))
			eventListeners.put(plugin, new LinkedList<>());
		
		List<ListenerEntry> listeners = eventListeners.get(plugin);
		for (Method method : listener.getClass().getMethods())
		{
			EventHandler annotation = method.getAnnotation(EventHandler.class);
			if (annotation != null)
				listeners.add(new ListenerEntry(plugin, listener, method));
		}
	}
	
	@Override
	public void enablePlugin(Plugin plugin)
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
	 * @param command
	 *            The command to add it to.
	 * @param name
	 *            The name of the section, as read in a configuration file.
	 * @param value
	 *            The value of the section, as parsed by {@link YamlConfiguration}
	 */
	private void addSection(PluginCommand command, String name, Object value)
	{
		switch (name)
		{
			case "description":
				command.setDescription((String) value);
				break;
			case "aliases":
				List<String> aliases = new ArrayList<>();
				if (value instanceof List<?>)
					command.setAliases(
							((List<?>) aliases).stream().map(object -> object.toString()).collect(Collectors.toList()));
				else
					command.setAliases(Arrays.asList(value.toString()));
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
	 * @param plugin
	 *            The plugin from which to read commands.
	 */
	protected void addCommandsFrom(JavaPlugin plugin)
	{
		Map<String, Map<String, Object>> commands = plugin.getDescription().getCommands();
		if (commands != null)
		{
			for (Entry<String, Map<String, Object>> entry : commands.entrySet())
			{
				PluginCommand command = PluginCommandUtils.createPluginCommand(entry.getKey(), plugin);
				for (Entry<String, Object> section : entry.getValue().entrySet())
				{
					addSection(command, section.getKey(), section.getValue());
				}
				this.commands.add(command);
			}
		}
	}
	
	@Override
	public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isPluginEnabled(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isPluginEnabled(Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Plugin loadPlugin(File file)
			throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Plugin[] loadPlugins(File directory)
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
	
	@Override
	public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority,
			EventExecutor executor, Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority,
			EventExecutor executor, Plugin plugin, boolean ignoreCancelled)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void disablePlugin(Plugin plugin)
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
	public Permission getPermission(String name)
	{
		return permissions.stream().filter(permission -> permission.getName().equals(name)).findFirst().orElse(null);
	}
	
	@Override
	public void addPermission(Permission perm)
	{
		permissions.add(perm);
	}
	
	@Override
	public void removePermission(Permission perm)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void removePermission(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<Permission> getDefaultPermissions(boolean op)
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
	public void recalculatePermissionDefaults(Permission perm)
	{
		
	}
	
	/**
	 * Gets a set of permissions that a {@link Permissible} is subscribed to.
	 * 
	 * @param permissible
	 *            The {@link Permissible} to check.
	 * @return A {@link Set} of permissions the permissible is subscribed to. Is the
	 *         {@link Permissible} isn't subscribed to any, returns an empty set.
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
	public void subscribeToPermission(String permission, Permissible permissible)
	{
		getPermissionSubscriptions(permissible).add(permission);
	}
	
	@Override
	public void unsubscribeFromPermission(String permission, Permissible permissible)
	{
		getPermissionSubscriptions(permissible).remove(permission);
	}
	
	@Override
	public Set<Permissible> getPermissionSubscriptions(String permission)
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
	public void subscribeToDefaultPerms(boolean op, Permissible permissible)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<Permissible> getDefaultPermSubscriptions(boolean op)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Set<Permission> getPermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean useTimings()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
}