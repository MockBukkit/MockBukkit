package org.bukkit.plugin.java;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A ClassLoader for plugins, to allow shared classes across multiple plugins
 */
public final class PluginClassLoader extends URLClassLoader { // Spigot
	public JavaPlugin getPlugin() { return plugin; } // Spigot

	private final JavaPluginLoader loader;
	private final Set<String> availableClasses = new HashSet<>();
	private final Map<String, Class<?>> loadedClasses = new ConcurrentHashMap<>();
	private final PluginDescriptionFile description; PluginDescriptionFile getDescription() { return description; } // Paper
	private final File dataFolder;

	private final File file;
	private final JarFile jar;
	private final ClassLoader libraryLoader;
	final JavaPlugin plugin;
	private JavaPlugin pluginInit;
	private IllegalStateException pluginState;
	// Paper - add field
	private java.util.logging.Logger logger; // Paper - add field
	private final ClassLoader parent;

	static {
		ClassLoader.registerAsParallelCapable();
	}

	PluginClassLoader(@NotNull final JavaPluginLoader loader, @Nullable final ClassLoader parent, @NotNull final PluginDescriptionFile description, @NotNull final File dataFolder, @NotNull final File file, @Nullable ClassLoader libraryLoader) throws IOException, InvalidPluginException, MalformedURLException {
		super(file.getName(), new URL[] {file.toURI().toURL()}, null); // Paper - rewrite LogEvents to contain source jar info
		Preconditions.checkArgument(loader != null, "Loader cannot be null");

		this.loader = loader;
		this.description = description;
		this.dataFolder = dataFolder;
		this.file = file;
		this.jar = new JarFile(file, true, java.util.zip.ZipFile.OPEN_READ, JarFile.runtimeVersion()); // Paper - enable multi-release jars for Java 9+
		this.libraryLoader = libraryLoader;
		this.parent = parent;

		try (ZipInputStream zip = new ZipInputStream(new FileInputStream(file))) {
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
					String className = entry.getName().replace('/', '.');
					availableClasses.add(className.substring(0, className.length() - ".class".length()));
				}
			}

			Class<?> jarClass;
			try {
				jarClass = Class.forName(description.getMain(), true, this);
			} catch (ClassNotFoundException ex) {
				throw new InvalidPluginException("Cannot find main class `" + description.getMain() + "'", ex);
			}

			Class<? extends JavaPlugin> pluginClass;
			try {
				pluginClass = jarClass.asSubclass(JavaPlugin.class);
			} catch (ClassCastException ex) {
				throw new InvalidPluginException("main class `" + description.getMain() + "' does not extend JavaPlugin", ex);
			}

			plugin = pluginClass.newInstance();
		} catch (IllegalAccessException ex) {
			throw new InvalidPluginException("No public constructor", ex);
		} catch (InstantiationException ex) {
			throw new InvalidPluginException("Abnormal plugin type", ex);
		}
	}

	@Override
	public URL getResource(String name) {
		// Paper start
		URL resource = findResource(name);
		if (resource == null && libraryLoader != null) {
			return libraryLoader.getResource(name);
		}
		return resource;
		// Paper end
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		// Paper start
		java.util.ArrayList<URL> resources = new java.util.ArrayList<>();
		addEnumeration(resources, findResources(name));
		if (libraryLoader != null) {
			addEnumeration(resources, libraryLoader.getResources(name));
		}
		return Collections.enumeration(resources);
		// Paper end
	}

	// Paper start
	private <T> void addEnumeration(java.util.ArrayList<T> list, Enumeration<T> enumeration) {
		while (enumeration.hasMoreElements()) {
			list.add(enumeration.nextElement());
		}
	}
	// Paper end

	@NotNull
	Collection<Class<?>> getClasses() {
		return loadedClasses.values();
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		if (availableClasses.contains(name))
		{
			synchronized (loadedClasses)
			{
				Class<?> clazz = loadedClasses.get(name);
				if (clazz == null) {
					//System.out.println("Loading class " + name);
					clazz = super.findClass(name);
					loadedClasses.put(name, clazz);
				}
				return clazz;
			}
		}
		else
		{
			//System.out.println("Getting class from parent: " + name);
			return Class.forName(name, true, parent);
		}
	}

	synchronized void initialize(@NotNull JavaPlugin javaPlugin) {
		Preconditions.checkArgument(javaPlugin != null, "Initializing plugin cannot be null");
		Preconditions.checkArgument(javaPlugin.getClass().getClassLoader() == this, "Cannot initialize plugin outside of this class loader");
		if (this.plugin != null || this.pluginInit != null) {
			throw new IllegalArgumentException("Plugin already initialized!", pluginState);
		}

		pluginState = new IllegalStateException("Initial initialization");
		this.pluginInit = javaPlugin;

		javaPlugin.logger = this.logger; // Paper - set logger
		javaPlugin.init(loader, loader.server, description, dataFolder, file, this);
	}

	@Override
	public void close() throws IOException {
		try {
			super.close();
		} finally {
			jar.close();
		}
	}

	// Paper start
	@Override
	public String toString() {
		JavaPlugin currPlugin = plugin != null ? plugin : pluginInit;
		return "PluginClassLoader{" +
				"plugin=" + currPlugin +
				", pluginEnabled=" + (currPlugin == null ? "uninitialized" : currPlugin.isEnabled()) +
				", url=" + file +
				'}';
	}
	// Paper end
}
