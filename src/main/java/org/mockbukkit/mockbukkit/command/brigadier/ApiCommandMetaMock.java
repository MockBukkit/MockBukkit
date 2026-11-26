package org.mockbukkit.mockbukkit.command.brigadier;

import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record ApiCommandMetaMock(@Nullable PluginMeta pluginMeta, @Nullable String description, List<String> aliases,
								 @Nullable String helpCommandNamespace, boolean serverSideOnly)
{

	public ApiCommandMetaMock
	{
		aliases = List.copyOf(aliases);
	}

	/**
	 * The plugin that registered this command, if it is still registered with the server.
	 * <p>
	 * Returns null once that plugin is gone -- during a reload, for instance, the command outlives the plugin
	 * instance that added it -- which is why the return is nullable.
	 *
	 * @return The owning plugin, or null if it has no plugin meta or is no longer registered.
	 */
	@Nullable
	public Plugin plugin()
	{
		return this.pluginMeta == null ? null : Bukkit.getPluginManager().getPlugin(this.pluginMeta.getName());
	}

	public ApiCommandMetaMock withAliases(List<String> registeredAliases)
	{
		return new ApiCommandMetaMock(this.pluginMeta, this.description, List.copyOf(registeredAliases), this.helpCommandNamespace, this.serverSideOnly);
	}

}
