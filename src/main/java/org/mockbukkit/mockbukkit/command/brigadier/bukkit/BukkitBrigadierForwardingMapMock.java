package org.mockbukkit.mockbukkit.command.brigadier.bukkit;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.command.brigadier.BrigadierUtils;
import org.mockbukkit.mockbukkit.command.brigadier.PaperCommandsMock;

import java.util.AbstractMap;
import java.util.Set;
import java.util.stream.Collectors;

public class BukkitBrigadierForwardingMapMock extends AbstractMap<String, Command>
{

	public static final BukkitBrigadierForwardingMapMock INSTANCE = new BukkitBrigadierForwardingMapMock();

	private CommandDispatcher<CommandSourceStack> getDispatcher()
	{
		return PaperCommandsMock.INSTANCE.getDispatcherInternal();
	}

	@Override
	public int size()
	{
		return getDispatcher().getRoot().getChildren().size();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return key instanceof String stringKey && getDispatcher().getRoot().getChild(stringKey) != null;
	}

	@Override
	public Command get(Object key)
	{
		if (!(key instanceof String stringKey))
		{
			return null;
		}

		CommandNode<?> node = getDispatcher().getRoot().getChild(stringKey);
		if (node == null)
		{
			return null;
		}

		return node instanceof BukkitCommandNode bukkitNode
				? bukkitNode.getBukkitCommand()
				: BrigadierUtils.wrapNode((CommandNode<CommandSourceStack>) node);
	}

	@Nullable
	@Override
	public Command put(String key, Command value)
	{
		Command old = get(key);
		BrigadierUtils.removeChildFromRoot(key, getDispatcher());
		getDispatcher().getRoot().addChild(BukkitCommandNode.of(key, value));
		return old;
	}

	@Override
	public Command remove(Object key)
	{
		if (!(key instanceof String stringKey))
		{
			return null;
		}

		Command old = get(key);
		if (old != null)
		{
			BrigadierUtils.removeChildFromRoot(stringKey, getDispatcher());
		}
		return old;
	}

	@Override
	public void clear()
	{
		BrigadierUtils.clearRoot(getDispatcher());
	}

	@NotNull
	@Override
	public Set<Entry<String, Command>> entrySet()
	{
		return getDispatcher().getRoot().getChildren().stream()
				.map(this::nodeToEntry)
				.collect(Collectors.toSet());
	}

	private Entry<String, Command> nodeToEntry(CommandNode<?> node)
	{
		String key = node.getName();
		Command command = node instanceof BukkitCommandNode bukkitNode
				? bukkitNode.getBukkitCommand()
				: BrigadierUtils.wrapNode((CommandNode<CommandSourceStack>) node);

		return new SimpleEntry<>(key, command)
		{
			@Override
			public Command setValue(Command value)
			{
				return BukkitBrigadierForwardingMapMock.this.put(key, value);
			}
		};
	}

}
