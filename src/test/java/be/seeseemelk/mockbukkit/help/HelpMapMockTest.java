package be.seeseemelk.mockbukkit.help;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;

import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

class HelpMapMockTest
{

	private ServerMock server;
	private HelpMapMock helpMap;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		helpMap = server.getHelpMap();
	}

	@AfterEach
	public void tearDown()
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

}
