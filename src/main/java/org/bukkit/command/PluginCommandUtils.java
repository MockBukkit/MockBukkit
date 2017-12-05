package org.bukkit.command;

import org.bukkit.plugin.Plugin;

public final class PluginCommandUtils
{

	/**
	 * Creates a plugin command.
	 * @param name The name of the command to instantiate.
	 * @param owner The plugin that owns this command.
	 * @return The new command.
	 */
	public static PluginCommand createPluginCommand(String name, Plugin owner)
	{
		return new PluginCommand(name, owner);
	}

}
