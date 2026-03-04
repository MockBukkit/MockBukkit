package org.mockbukkit.mockbukkit.command.brigadier;

import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public record ApiCommandMetaMock(@Nullable PluginMeta pluginMeta, @Nullable String description, List<String> aliases,
								 @Nullable String helpCommandNamespace, boolean serverSideOnly)
{

	public ApiCommandMetaMock
	{
		aliases = List.copyOf(aliases);
	}

	@Nullable
	public Plugin plugin()
	{
		return this.pluginMeta == null ? null : Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(this.pluginMeta.getName()));
	}

	public ApiCommandMetaMock withAliases(List<String> registeredAliases)
	{
		return new ApiCommandMetaMock(this.pluginMeta, this.description, List.copyOf(registeredAliases), this.helpCommandNamespace, this.serverSideOnly);
	}

}
