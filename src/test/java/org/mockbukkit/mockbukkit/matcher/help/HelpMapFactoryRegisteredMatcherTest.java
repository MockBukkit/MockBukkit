package org.mockbukkit.mockbukkit.matcher.help;

import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.help.IndexHelpTopic;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.help.HelpMapMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.Collections;

import static org.mockbukkit.mockbukkit.matcher.help.HelpMapFactoryRegisteredMatcher.hasFactoryRegistered;

@ExtendWith(MockBukkitExtension.class)
class HelpMapFactoryRegisteredMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private HelpMapMock helpMap;
	private HelpTopicFactory<VersionCommand> helpTopicFactory;

	@BeforeEach
	void setUp()
	{
		this.helpMap = serverMock.getHelpMap();
		this.helpTopicFactory = new HelpTopicFactory<>()
		{
			@Override
			public @Nullable HelpTopic createTopic(@NotNull VersionCommand command)
			{
				return new IndexHelpTopic("", "short text", "perm", Collections.emptyList());
			}
		};
	}

	@Test
	void registered_matches()
	{
		helpMap.registerHelpTopicFactory(VersionCommand.class, helpTopicFactory);
		assertMatches(hasFactoryRegistered(helpTopicFactory), helpMap);
	}

	@Test
	void notRegistered()
	{
		assertDoesNotMatch(hasFactoryRegistered(helpTopicFactory), helpMap);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasFactoryRegistered(helpTopicFactory);
	}

}
