package be.seeseemelk.mockbukkit.help;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.help.IndexHelpTopic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

public class MockHelpMapTest {

    private ServerMock server;
    private World world;
    private HelpMap helpMap;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();
        world = new WorldMock();
        helpMap = server.getHelpMap();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void testHelpLookup() {

        IndexHelpTopic indexHelpTopic = new IndexHelpTopic("test", "short text", "perm", Collections.emptyList());
        helpMap.addTopic(indexHelpTopic);

        //test lookup by help topic name
        HelpTopic test = helpMap.getHelpTopic("test");
        assertSame(indexHelpTopic, test);

    }

    @Test
    public void testHelpTopicFactoryReigstration() {
        helpMap.registerHelpTopicFactory(VersionCommand.class,
                command -> new IndexHelpTopic("","short text", "perm", Collections.emptyList()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHelpTopicFactoryRegistrationMustFail() {
        helpMap.registerHelpTopicFactory(Object.class,
                command -> new IndexHelpTopic("","short text", "perm", Collections.emptyList()));
    }

}
