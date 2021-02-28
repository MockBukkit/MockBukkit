package be.seeseemelk.mockbukkit.help;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.HelpTopicFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

/**
 * The {@link HelpMapMock} is our mock of Bukkit's {@link HelpMap}.
 *
 * @author NeumimTo
 *
 */
public class HelpMapMock implements HelpMap
{

	private HelpTopic defaultTopic;
	private final Map<String, HelpTopic> topics = new TreeMap<>(HelpTopicComparator.topicNameComparatorInstance());
	private final Map<Class<?>, HelpTopicFactory<?>> factories = new HashMap<>();

	@Override
	public HelpTopic getHelpTopic(final String topicName)
	{
		if ("".equals(topicName))
		{
			return this.defaultTopic;
		}

		return topics.get(topicName);
	}

	@Override
	public Collection<HelpTopic> getHelpTopics()
	{
		return topics.values();
	}

	@Override
	public void addTopic(HelpTopic topic)
	{
		if ("".equals(topic.getName()))
		{
			defaultTopic = topic;
		}
		else
		{
			topics.put(topic.getName(), topic);
		}
	}

	@Override
	public void clear()
	{
		topics.clear();
	}

	@Override
	public List<String> getIgnoredPlugins()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void registerHelpTopicFactory(Class<?> commandClass, HelpTopicFactory<?> factory)
	{
		if (!Command.class.isAssignableFrom(commandClass) && !CommandExecutor.class.isAssignableFrom(commandClass))
		{
			throw new IllegalArgumentException("CommandClass must inherit from types Command or CommandExecutor");
		}

		factories.put(commandClass, factory);
	}

}
