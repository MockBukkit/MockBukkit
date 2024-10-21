package org.mockbukkit.mockbukkit.help;

import com.google.common.base.Preconditions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.HelpTopicFactory;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mock implementation of a {@link HelpMap}.
 */
public class HelpMapMock implements HelpMap
{

	private HelpTopic defaultTopic;
	private final Map<String, HelpTopic> topics = new TreeMap<>(HelpTopicComparator.topicNameComparatorInstance());
	private final Map<Class<?>, HelpTopicFactory<?>> factories = new HashMap<>();
	private static final String FACTORY_NOT_NULL = "Factory cannot be null";

	@Override
	public HelpTopic getHelpTopic(final @NotNull String topicName)
	{
		Preconditions.checkNotNull(topicName, "TopicName cannot be null");
		return topicName.isEmpty() ? this.defaultTopic : this.topics.get(topicName);
	}

	@Override
	public @NotNull Collection<HelpTopic> getHelpTopics()
	{
		return topics.values();
	}

	@Override
	public void addTopic(@NotNull HelpTopic topic)
	{
		Preconditions.checkNotNull(topic, "Topic cannot be null");
		Preconditions.checkNotNull(topic.getName(), "Topic name cannot be null");
		if (topic.getName().isEmpty())
		{
			this.defaultTopic = topic;
		}
		else
		{
			this.topics.put(topic.getName(), topic);
		}
	}

	@Override
	public void clear()
	{
		topics.clear();
	}

	@Override
	public @NotNull List<String> getIgnoredPlugins()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void registerHelpTopicFactory(@NotNull Class<?> commandClass, @NotNull HelpTopicFactory<?> factory)
	{
		Preconditions.checkNotNull(commandClass, "CommandClass cannot be null");
		Preconditions.checkNotNull(factory, FACTORY_NOT_NULL);
		if (!Command.class.isAssignableFrom(commandClass) && !CommandExecutor.class.isAssignableFrom(commandClass))
		{
			throw new IllegalArgumentException("CommandClass must inherit from types Command or CommandExecutor");
		}

		factories.put(commandClass, factory);
	}

	/**
	 * Asserts that a {@link HelpTopicFactory} is registered.
	 *
	 * @param factory The factory to check.
	 */
	@Deprecated(forRemoval = true)
	public void assertRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		Preconditions.checkNotNull(factory, FACTORY_NOT_NULL);
		assertTrue(factories.containsValue(factory));
	}

	/**
	 * Whether the specified factory has been registered to this instance
	 * @param factory The factory that should have been registered
	 * @return True if the specified factory has been registered to this instance
	 */
	public boolean hasRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		Preconditions.checkNotNull(factory, FACTORY_NOT_NULL);
		return factories.containsValue(factory);
	}

}
