package org.bukkit.plugin.java;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This utility class allows us to create a new {@link PluginClassLoader}. The constructor is marked as package-private
 * hence why this class is located in this exact package.
 *
 */
public class PluginClassLoaderUtils
{
	private PluginClassLoaderUtils()
	{
		// Do not instantiate this.
	}

	public static @NotNull PluginClassLoader createPluginClassLoader(@NotNull JavaPluginLoader loader,
	        @Nullable ClassLoader parent, @NotNull PluginDescriptionFile description, @NotNull File dataFolder,
	        @NotNull File file, @NotNull ClassLoader libraryLoader)
	throws IOException, InvalidPluginException, MalformedURLException
	{
		return new PluginClassLoader(loader, parent, description, dataFolder, file, libraryLoader);
	}
}
