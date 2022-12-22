package org.bukkit.plugin.java;

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * This utility class allows us to create a new {@link PluginClassLoader}. The constructor is marked as package-private
 * hence why this class is located in this exact package.
 */
public class PluginClassLoaderUtils
{

	private PluginClassLoaderUtils()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Constructs a new {@link PluginClassLoader}.
	 *
	 * @param loader        The plugin loader.
	 * @param parent        The classloader parent.
	 * @param description   The plugin description file.
	 * @param dataFolder    The plugin's data folder.
	 * @param file          The plugin's file.
	 * @param libraryLoader The library loader.
	 * @return The constructed {@link PluginClassLoader}.
	 * @throws IOException            If an IO exception occurs.
	 * @throws InvalidPluginException If the plugin is invalid.
	 */
	public static @NotNull PluginClassLoader createPluginClassLoader(@NotNull JavaPluginLoader loader,
																	 @Nullable ClassLoader parent, @NotNull PluginDescriptionFile description, @NotNull File dataFolder,
																	 @NotNull File file, @NotNull ClassLoader libraryLoader) throws IOException, InvalidPluginException
	{
		return new PluginClassLoader(loader, parent, description, dataFolder, file, libraryLoader);
	}

}
