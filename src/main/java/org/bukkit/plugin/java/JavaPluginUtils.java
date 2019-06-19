package org.bukkit.plugin.java;

public final class JavaPluginUtils
{

	/**
	 * Sets the enabled status of a java plugin.
	 * @param plugin The plugin of which to set the state.
	 * @param enabled The state to set it to.
	 */
	public static void setEnabled(JavaPlugin plugin, boolean enabled)
	{
		plugin.setEnabled(enabled);
	}

}
