package be.seeseemelk.testplugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public class TestPlugin extends JavaPlugin
{
	private static TestPlugin plugin;

	public TestPlugin()
	{
		super();
	}

	@Override
	public void onEnable()
	{
		plugin = this;
		getLogger().info("Test plugin enabled");
	}

	@Override
	public void onDisable()
	{
		getLogger().info("Test plugin disabled");
	}

	/**
	 * Kills all players on the server.
	 */
	public void killAllPlayers()
	{
		Bukkit.getOnlinePlayers().forEach(p -> p.damage(99999999));
	}

	/**
	 * Gets the global test plugin.
	 * @return The global test plugin
	 */
	public static TestPlugin getPlugin()
	{
		return plugin;
	}
}
































