package org.mockbukkit.mockbukkit.command;

import com.mojang.brigadier.tree.CommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.Command;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PluginVanillaCommandWrapperMock extends VanillaCommandWrapperMock implements PluginIdentifiableCommand
{

	private final Plugin plugin;
	private final List<String> aliases;

	public PluginVanillaCommandWrapperMock(String name, String description, String usageMessage, List<String> aliases, CommandNode<CommandSourceStack> vanillaCommand, Plugin plugin)
	{
		super(name, description, usageMessage, aliases, vanillaCommand, null);
		this.plugin = plugin;
		this.aliases = aliases;
	}

	@Override
	public @NotNull List<String> getAliases()
	{
		return this.aliases;
	}

	@Override
	public @NotNull Command setAliases(@NotNull List<String> aliases)
	{
		return this;
	}

	@Override
	public @NotNull Plugin getPlugin()
	{
		return this.plugin;
	}

}
