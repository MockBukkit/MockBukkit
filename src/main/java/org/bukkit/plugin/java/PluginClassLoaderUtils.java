package org.bukkit.plugin.java;

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class PluginClassLoaderUtils
{
    public static PluginClassLoader createPluginClassLoader(@NotNull final JavaPluginLoader loader,
                                                            @Nullable final ClassLoader parent,
                                                            @NotNull final PluginDescriptionFile description,
                                                            @NotNull final File dataFolder,
                                                            @NotNull final File file) throws
            IOException,
            InvalidPluginException,
            MalformedURLException
    {
        return new PluginClassLoader(loader, parent, description, dataFolder, file);
    }
}
