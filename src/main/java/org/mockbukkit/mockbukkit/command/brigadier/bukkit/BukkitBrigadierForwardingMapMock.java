package org.mockbukkit.mockbukkit.command.brigadier.bukkit;

import com.google.common.collect.Iterators;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.command.brigadier.BrigadierUtils;
import org.mockbukkit.mockbukkit.command.brigadier.PaperCommandsMock;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BukkitBrigadierForwardingMapMock extends AbstractMap<String, Command>
{

	public static final BukkitBrigadierForwardingMapMock INSTANCE = new BukkitBrigadierForwardingMapMock();

	private final transient EntrySet entrySet1 = new EntrySet();
	private final transient KeySet keySet1 = new KeySet();
	private final transient Values values1 = new Values();

	public CommandDispatcher<CommandSourceStack> getDispatcher()
	{
		return PaperCommandsMock.INSTANCE.getDispatcherInternal();
	}

	@Override
	public int size()
	{
		return this.getDispatcher().getRoot().getChildren().size();
	}

	@Override
	public boolean isEmpty()
	{
		return this.size() == 0;
	}

	@Override
	public boolean containsKey(Object key)
	{
		if (!(key instanceof String stringKey))
		{
			return false;
		}

		// Do any children match?
		return this.getDispatcher().getRoot().getChild(stringKey) != null;
	}

	@Override
	public boolean containsValue(@Nullable final Object value)
	{
		if (!(value instanceof Command))
		{
			return false;
		}

		for (CommandNode<CommandSourceStack> child : this.getDispatcher().getRoot().getChildren())
		{
			// If child is a bukkit command node, we can convert it!
			if (child instanceof BukkitCommandNode bukkitCommandNode)
			{
				return bukkitCommandNode.getBukkitCommand().equals(value);
			}
		}

		return false;
	}

	@Override
	public Command get(Object key)
	{
		CommandNode<?> node = this.getDispatcher().getRoot().getChild((String) key);
		if (node == null)
		{
			return null;
		}

		if (node instanceof BukkitCommandNode bukkitCommandNode)
		{
			return bukkitCommandNode.getBukkitCommand();
		}

		return BrigadierUtils.wrapNode((CommandNode<CommandSourceStack>) node);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Nullable
	@Override
	public Command put(String key, Command value)
	{
		Command old = this.get(key);
		BrigadierUtils.removeChildFromRoot(key, getDispatcher());
		this.getDispatcher().getRoot().addChild(BukkitCommandNode.of(key, value));
		return old;
	}

	@Override
	public Command remove(Object key)
	{
		if (!(key instanceof String string))
		{
			return null;
		}

		Command old = this.get(key);
		if (old != null)
		{
			BrigadierUtils.removeChildFromRoot(string, getDispatcher());
		}

		return old;
	}

	@Override
	public boolean remove(Object key, Object value)
	{
		Command old = this.get(key);
		if (Objects.equals(old, value))
		{
			this.remove(key);
			return true;
		}

		return false;
	}

	@Override
	public void putAll(@NotNull Map<? extends String, ? extends Command> m)
	{
		for (Entry<? extends String, ? extends Command> entry : m.entrySet())
		{
			this.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear()
	{
		BrigadierUtils.clearRoot(getDispatcher());
	}

	@NotNull
	@Override
	public Set<String> keySet()
	{
		return this.keySet1;
	}

	@NotNull
	@Override
	public Collection<Command> values()
	{
		return this.values1;
	}

	@NotNull
	@Override
	public Set<Entry<String, Command>> entrySet()
	{
		return this.entrySet1;
	}

	final class Values extends AbstractCollection<Command>
	{

		@Override
		public Iterator<Command> iterator()
		{
			// AVOID CME since commands can modify multiple commands now through alises, which means it may appear in the iterator even if removed.
			// Oh well!
			Iterator<CommandNode<CommandSourceStack>> iterator = new ArrayList<>(BukkitBrigadierForwardingMapMock.this.getDispatcher().getRoot().getChildren()).iterator();

			return new Iterator<>()
			{

				private CommandNode<CommandSourceStack> lastFetched;

				@Override
				public void remove()
				{
					if (this.lastFetched == null)
					{
						throw new IllegalStateException("next not yet called");
					}

					BukkitBrigadierForwardingMapMock.this.remove(this.lastFetched.getName());
					iterator.remove();
				}

				@Override
				public boolean hasNext()
				{
					return iterator.hasNext();
				}

				@Override
				public Command next()
				{
					CommandNode<CommandSourceStack> next = iterator.next();
					this.lastFetched = next;
					if (next instanceof BukkitCommandNode bukkitCommandNode)
					{
						return bukkitCommandNode.getBukkitCommand();
					}
					else
					{
						return BrigadierUtils.wrapNode(next);
					}
				}
			};
		}

		@Override
		public int size()
		{
			return BukkitBrigadierForwardingMapMock.this.getDispatcher().getRoot().getChildren().size();
		}

		@Override
		public void clear()
		{
			BukkitBrigadierForwardingMapMock.this.clear();
		}

	}


	final class KeySet extends AbstractSet<String>
	{

		@Override
		public int size()
		{
			return BukkitBrigadierForwardingMapMock.this.size();
		}

		@Override
		public void clear()
		{
			BukkitBrigadierForwardingMapMock.this.clear();
		}

		@Override
		public Iterator<String> iterator()
		{
			return Iterators.transform(BukkitBrigadierForwardingMapMock.this.values1.iterator(), Command::getName); // Wrap around the values iterator for consistancy
		}

		@Override
		public boolean contains(Object o)
		{
			return BukkitBrigadierForwardingMapMock.this.containsKey(o);
		}

		@Override
		public boolean remove(Object o)
		{
			return BukkitBrigadierForwardingMapMock.this.remove(o) != null;
		}

		@Override
		public Spliterator<String> spliterator()
		{
			return this.entryStream().spliterator();
		}

		@Override
		public void forEach(Consumer<? super String> action)
		{
			this.entryStream().forEach(action);
		}

		private Stream<String> entryStream()
		{
			return BukkitBrigadierForwardingMapMock.this.getDispatcher().getRoot().getChildren().stream().map(CommandNode::getName);
		}

	}

	final class EntrySet extends AbstractSet<Entry<String, Command>>
	{

		@Override
		public int size()
		{
			return BukkitBrigadierForwardingMapMock.this.size();
		}


		@Override
		public void clear()
		{
			BukkitBrigadierForwardingMapMock.this.clear();
		}

		@Override
		public Iterator<Entry<String, Command>> iterator()
		{
			return this.entryStream().iterator();
		}

		@Override
		public boolean contains(Object o)
		{
			if (!(o instanceof Map.Entry<?, ?> entry))
			{
				return false;
			}

			Object key = entry.getKey();
			Command candidate = get(key);
			return candidate != null && candidate.equals(entry.getValue());
		}

		@Override
		public boolean remove(Object o)
		{
			if (o instanceof Map.Entry<?, ?> e)
			{
				Object key = e.getKey();
				Object value = e.getValue();
				return BukkitBrigadierForwardingMapMock.this.remove(key, value);
			}
			return false;
		}

		@Override
		public Spliterator<Entry<String, Command>> spliterator()
		{
			return this.entryStream().spliterator();
		}

		@Override
		public void forEach(Consumer<? super Entry<String, Command>> action)
		{
			this.entryStream().forEach(action);
		}

		private Stream<Map.Entry<String, Command>> entryStream()
		{
			return BukkitBrigadierForwardingMapMock.this.getDispatcher().getRoot().getChildren().stream().map(BukkitBrigadierForwardingMapMock.this::nodeToEntry);
		}

	}

	private Map.Entry<String, Command> nodeToEntry(CommandNode<?> node)
	{
		if (node instanceof BukkitCommandNode bukkitCommandNode)
		{
			return this.mutableEntry(bukkitCommandNode.getName(), bukkitCommandNode.getBukkitCommand());
		}
		else
		{
			return this.mutableEntry(node.getName(), BrigadierUtils.wrapNode((CommandNode<CommandSourceStack>) node));
		}
	}

	private Map.Entry<String, Command> mutableEntry(String key, Command command)
	{
		return new Entry<>()
		{
			@Override
			public String getKey()
			{
				return key;
			}

			@Override
			public Command getValue()
			{
				return command;
			}

			@Override
			public Command setValue(Command value)
			{
				return BukkitBrigadierForwardingMapMock.this.put(key, value);
			}
		};
	}

}
