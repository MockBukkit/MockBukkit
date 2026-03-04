package org.mockbukkit.mockbukkit.command;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.mockbukkit.mockbukkit.command.brigadier.PaperCommandsMock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanillaCommandWrapperMock extends BukkitCommand
{

	public final CommandNode<CommandSourceStack> vanillaCommand;
	public final String helpCommandNamespace;

	public VanillaCommandWrapperMock(String name, String description, String usageMessage, List<String> aliases, CommandNode<CommandSourceStack> vanillaCommand, String helpCommandNamespace)
	{
		super(name, description, usageMessage, aliases);
		this.vanillaCommand = vanillaCommand;
		this.helpCommandNamespace = helpCommandNamespace;
	}

	public VanillaCommandWrapperMock(CommandNode<CommandSourceStack> vanillaCommand)
	{
		super(vanillaCommand.getName(), "A Mojang provided command.", vanillaCommand.getUsageText(), Collections.emptyList());
		this.vanillaCommand = vanillaCommand;
		this.setPermission(VanillaCommandWrapperMock.getPermission(vanillaCommand));
		this.helpCommandNamespace = "Minecraft";
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args)
	{
		if (!this.testPermission(sender))
		{
			return true;
		}

		CommandSourceStack source = CommandSourceStackMock.from(sender);
		ParseResults<CommandSourceStack> parseResults = PaperCommandsMock.INSTANCE.getDispatcherInternal().parse(commandLabel + (args.length == 0 ? "" : " " + String.join(" ", args)), source);
		try
		{
			PaperCommandsMock.INSTANCE.getDispatcherInternal().execute(parseResults);
		}
		catch (CommandSyntaxException e)
		{
			sender.sendMessage(MessageComponentSerializer.message().deserialize(e.getRawMessage()));
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException
	{
		Preconditions.checkArgument(sender != null, "Sender cannot be null");
		Preconditions.checkArgument(args != null, "Arguments cannot be null");
		Preconditions.checkArgument(alias != null, "Alias cannot be null");

		CommandSourceStack source = CommandSourceStackMock.from(sender);
		ParseResults<CommandSourceStack> parsed = PaperCommandsMock.INSTANCE.getDispatcherInternal().parse(this.toDispatcher(args, this.getName()), source); // Paper

		List<String> results = new ArrayList<>();
		PaperCommandsMock.INSTANCE.getDispatcherInternal().getCompletionSuggestions(parsed).thenAccept((suggestions) ->
		{ // Paper
			suggestions.getList().forEach((s) -> results.add(s.getText()));
		});

		return results;
	}

	public static String getPermission(CommandNode<CommandSourceStack> vanillaCommand)
	{
		// Paper start - Vanilla command permission fixes
		while (vanillaCommand.getRedirect() != null)
		{
			vanillaCommand = vanillaCommand.getRedirect();
		}
		final String commandName = vanillaCommand.getName();
		return "minecraft.command." + stripDefaultNamespace(commandName);
	}

	private static String stripDefaultNamespace(final String maybeNamespaced)
	{
		final String prefix = "minecraft:";
		if (maybeNamespaced.startsWith(prefix))
		{
			return maybeNamespaced.substring(prefix.length());
		}
		return maybeNamespaced;
		// Paper end - Vanilla command permission fixes
	}

	private String toDispatcher(String[] args, String name)
	{
		return name + ((args.length > 0) ? " " + Joiner.on(' ').join(args) : "");
	}

	@Override
	public boolean canBeOverriden()
	{
		return true;
	}

	@Override
	public boolean isRegistered()
	{
		return true;
	}

}
