package be.seeseemelk.mockbukkit;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A simple plugin that does nothing.
 */
public class MockPlugin extends JavaPlugin
{

	/**
	 * CraftBukkit constructor.
	 */
	public MockPlugin()
	{}

	/**
	 * MockBukkit constructor.
	 *
	 * @param loader      The plugin loader.
	 * @param description The plugin description file.
	 * @param dataFolder  The plugins data folder.
	 * @param file        The plugins file.
	 */
	protected MockPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description,
			@NotNull File dataFolder, @NotNull File file)
	{
		super(loader, description, dataFolder, file);
	}

}
