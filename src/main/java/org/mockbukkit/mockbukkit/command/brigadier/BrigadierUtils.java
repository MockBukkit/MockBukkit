package org.mockbukkit.mockbukkit.command.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.mockbukkit.mockbukkit.command.CommandSourceStackMock;
import org.mockbukkit.mockbukkit.command.PluginVanillaCommandWrapperMock;
import org.mockbukkit.mockbukkit.command.VanillaCommandWrapperMock;
import org.mockbukkit.mockbukkit.command.WrappedLiteralCommandNode;

import java.lang.reflect.Field;
import java.util.Map;

public class BrigadierUtils
{

	private static final CommandSourceStack DUMMY = new CommandSourceStackMock(new Location(null, 0, 0, 0), new DummyCommandSender(), null);

	private BrigadierUtils()
	{
		throw new IllegalStateException("Utility class");
	}

	public static WrappedLiteralCommandNode copyLiteral(final String newLiteral, final LiteralCommandNode<CommandSourceStack> source)
	{

		return new WrappedLiteralCommandNode(source, newLiteral);
	}


	public static void removeChildFromRoot(String name, CommandDispatcher<CommandSourceStack> dispatcher)
	{
		try
		{
			RootCommandNode<CommandSourceStack> rootNode = dispatcher.getRoot();
			getRootNodeMapFields("children", rootNode).remove(name);
			getRootNodeMapFields("literals", rootNode).remove(name);
			getRootNodeMapFields("arguments", rootNode).remove(name);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void clearRoot(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		try
		{
			RootCommandNode<CommandSourceStack> rootNode = dispatcher.getRoot();
			getRootNodeMapFields("children", rootNode).clear();
			getRootNodeMapFields("literals", rootNode).clear();
			getRootNodeMapFields("arguments", rootNode).clear();
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}


	private static Map<String, ?> getRootNodeMapFields(String fieldName, RootCommandNode<CommandSourceStack> rootNode) throws IllegalAccessException, NoSuchFieldException
	{
		Field field = CommandNode.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		return (Map<String, ?>) field.get(rootNode);
	}

	public static Command wrapNode(CommandNode<CommandSourceStack> node)
	{
		if (!(node instanceof WrappedLiteralCommandNode wrappedLiteralCommandNode))
		{
			throw new IllegalArgumentException("Unsure how to wrap a " + node);
		}
		ApiCommandMetaMock meta = null;
		if ((meta = wrappedLiteralCommandNode.getCommandMeta()) == null)
		{
			return new VanillaCommandWrapperMock(node);
		}
		CommandNode<CommandSourceStack> argumentCommandNode = node;
		if (argumentCommandNode.getRedirect() != null)
		{
			argumentCommandNode = argumentCommandNode.getRedirect();
		}

		Map<CommandNode<CommandSourceStack>, String> map = PaperCommandsMock.INSTANCE.getDispatcherInternal().getSmartUsage(argumentCommandNode, DUMMY);
		String usage = map.isEmpty() ? node.getUsageText() : node.getUsageText() + " " + String.join("\n" + node.getUsageText() + " ", map.values());

		// Internal command
		if (meta.pluginMeta() == null)
		{
			return new VanillaCommandWrapperMock(node.getName(), meta.description(), usage, meta.aliases(), node, meta.helpCommandNamespace());
		}

		return new PluginVanillaCommandWrapperMock(node.getName(), meta.description(), usage, meta.aliases(), node, meta.plugin());
	}

}
