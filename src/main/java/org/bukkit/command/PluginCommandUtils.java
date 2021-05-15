package org.bukkit.command;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * This utility class allows us to create a {@link PluginCommand}. The constructor is marked as protected hence why this
 * is in this exact package.
 */
public final class PluginCommandUtils
{

	private PluginCommandUtils()
	{
		// Do not instantiate this.
	}

	/**
	 * Creates a plugin command.
	 *
	 * @param name  The name of the command to instantiate.
	 * @param owner The plugin that owns this command.
	 * @return The new command.
	 */
	public static @NotNull PluginCommand createPluginCommand(@NotNull String name, @NotNull Plugin owner)
	{
		return new PluginCommand(name, owner);
	}

}
