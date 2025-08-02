package org.mockbukkit.mockbukkit.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.command.brigadier.ApiCommandMetaMock;

public class WrappedLiteralCommandNode extends LiteralCommandNode<CommandSourceStack>
{

	private ApiCommandMetaMock commandMeta = null;

	public WrappedLiteralCommandNode(LiteralCommandNode<CommandSourceStack> commandNode, String newLiteral)
	{
		super(newLiteral, commandNode.getCommand(), commandNode.getRequirement(), commandNode.getRedirect(), commandNode.getRedirectModifier(), commandNode.isFork());
		commandNode.getChildren().forEach(this::addChild);
	}

	public @Nullable ApiCommandMetaMock getCommandMeta()
	{
		return commandMeta;
	}

	public void setCommandMeta(@Nullable ApiCommandMetaMock commandMeta)
	{
		this.commandMeta = commandMeta;
	}

}
