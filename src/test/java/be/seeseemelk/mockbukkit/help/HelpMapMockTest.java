package be.seeseemelk.mockbukkit.help;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.help.IndexHelpTopic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HelpMapMockTest
{

	private ServerMock server;
	private HelpMapMock helpMap;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		helpMap = server.getHelpMap();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void helpmap_lookup()
	{
		IndexHelpTopic indexHelpTopic = new IndexHelpTopic("test", "short text", "perm", Collections.emptyList());
		helpMap.addTopic(indexHelpTopic);

		// test lookup by help topic name
		HelpTopic test = helpMap.getHelpTopic("test");
		assertSame(indexHelpTopic, test);

	}

	@Test
	void helpmap_factory_registration()
	{
		helpMap.registerHelpTopicFactory(VersionCommand.class,
				command -> new IndexHelpTopic("", "short text", "perm", Collections.emptyList()));
	}

	@Test
	void helpmap_factory_registration_incorrect_type()
	{
		assertThrows(IllegalArgumentException.class, () -> helpMap.registerHelpTopicFactory(Object.class,
				command -> new IndexHelpTopic("", "short text", "perm", Collections.emptyList())));
	}

	@Test
	void testGetHelpTopics()
	{
		assertSame(0, helpMap.getHelpTopics().size());
		IndexHelpTopic indexHelpTopic = new IndexHelpTopic("test", "short text", "perm", Collections.emptyList());
		helpMap.addTopic(indexHelpTopic);

		assertSame(1, helpMap.getHelpTopics().size());
	}

	@Test
	void testClear()
	{
		IndexHelpTopic indexHelpTopic = new IndexHelpTopic("test", "short text", "perm", Collections.emptyList());
		helpMap.addTopic(indexHelpTopic);
		assertSame(1, helpMap.getHelpTopics().size());
		helpMap.clear();
		assertSame(0, helpMap.getHelpTopics().size());
	}

	@Test
	void testAddDefaultTopic()
	{
		IndexHelpTopic indexHelpTopic = new IndexHelpTopic("", "short text", "perm", Collections.emptyList());
		helpMap.addTopic(indexHelpTopic);
		assertSame(indexHelpTopic, helpMap.getHelpTopic(""));
	}

	@Test
	void testAssertRegistered()
	{
		HelpTopicFactory<VersionCommand> helpTopicFactory = new HelpTopicFactory<>()
		{
			@Override
			public @Nullable HelpTopic createTopic(@NotNull VersionCommand command)
			{
				return new IndexHelpTopic("", "short text", "perm", Collections.emptyList());
			}
		};
		helpMap.registerHelpTopicFactory(VersionCommand.class, helpTopicFactory);

		helpMap.assertRegistered(helpTopicFactory);
	}

}
