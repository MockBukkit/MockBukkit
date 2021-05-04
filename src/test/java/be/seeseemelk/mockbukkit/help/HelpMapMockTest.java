package be.seeseemelk.mockbukkit.help;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Collections;

import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

public class HelpMapMockTest
{

	private ServerMock server;
	private HelpMap helpMap;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		helpMap = server.getHelpMap();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void helpmap_lookup()
	{
		IndexHelpTopic indexHelpTopic = new IndexHelpTopic("test", "short text", "perm", Collections.emptyList());
		helpMap.addTopic(indexHelpTopic);

		//test lookup by help topic name
		HelpTopic test = helpMap.getHelpTopic("test");
		assertSame(indexHelpTopic, test);

	}

	@Test
	public void helpmap_factory_registration()
	{
		helpMap.registerHelpTopicFactory(VersionCommand.class,
		                                 command -> new IndexHelpTopic("", "short text", "perm", Collections.emptyList()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void helpmap_factory_registration_incorrect_type()
	{
		helpMap.registerHelpTopicFactory(Object.class,
		                                 command -> new IndexHelpTopic("", "short text", "perm", Collections.emptyList()));
	}

}
