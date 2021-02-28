package be.seeseemelk.mockbukkit;

import java.io.File;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import org.jetbrains.annotations.NotNull;

/**
 * Empty JavaPlugin used for testing only
 */
public class EmptyPlugin extends JavaPlugin
{

	public EmptyPlugin()
	{
		super();
	}

	public EmptyPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file)
	{
		super(loader, description, dataFolder, file);
	}
}
