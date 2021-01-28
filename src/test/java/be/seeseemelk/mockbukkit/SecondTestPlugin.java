package be.seeseemelk.mockbukkit;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class SecondTestPlugin extends JavaPlugin implements Listener
{

	public SecondTestPlugin()
	{
		super();
	}

	protected SecondTestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
	{
		super(loader, description, dataFolder, file);
	}

}
