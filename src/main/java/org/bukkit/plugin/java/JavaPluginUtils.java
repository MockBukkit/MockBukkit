package org.bukkit.plugin.java;

import org.jetbrains.annotations.NotNull;

/**
 * This utility class allows us to enable and disable a {@link JavaPlugin}. The method
 * {@link JavaPlugin#setEnabled(boolean)} is protected hence why this utility class is located in this exact package.
 */
public final class JavaPluginUtils
{

	private JavaPluginUtils()
	{
		// Do not instantiate this.
	}

	/**
	 * Sets the enabled status of a java plugin.
	 *
	 * @param plugin  The plugin of which to set the state.
	 * @param enabled The state to set it to.
	 */
	public static void setEnabled(@NotNull JavaPlugin plugin, boolean enabled)
	{
		plugin.setEnabled(enabled);
	}

}
