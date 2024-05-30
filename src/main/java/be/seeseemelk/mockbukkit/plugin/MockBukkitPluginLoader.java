package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class MockBukkitPluginLoader implements PluginLoader
{

	@Override
	public @NotNull Plugin loadPlugin(@NotNull File file) throws InvalidPluginException, UnknownDependencyException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PluginDescriptionFile getPluginDescription(@NotNull File file) throws InvalidDescriptionException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Pattern[] getPluginFileFilters()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(
			@NotNull Listener listener, @NotNull Plugin plugin)
	{
		return new JavaPluginLoader(plugin.getServer()).createRegisteredListeners(listener, plugin);
	}

	@Override
	public void enablePlugin(@NotNull Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void disablePlugin(@NotNull Plugin plugin)
	{
		((JavaPlugin) plugin).setEnabled(false);
		plugin.getServer().getPluginManager().callEvent(new PluginDisableEvent(plugin));
	}

}
