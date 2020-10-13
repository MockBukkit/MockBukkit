package be.seeseemelk.mockbukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;

public class MockBukkitTest
{
    @Before
    public void setUp()
    {
        MockBukkit.setServerInstanceToNull();
    }

    @After
    public void tearDown()
    {
        if (MockBukkit.isMocked())
        {
            MockBukkit.unmock();
        }
    }

    @Test
    public void setServerInstanceToNull()
    {
        MockBukkit.mock();
        assumeNotNull(Bukkit.getServer());
        MockBukkit.setServerInstanceToNull();
        assertNull(Bukkit.getServer());
    }

    @Test
    public void mock_ServerMocked()
    {
        ServerMock server = MockBukkit.mock();
        assertNotNull(server);
        assertEquals(server, MockBukkit.getMock());
        assertEquals(server, Bukkit.getServer());
    }

    @Test
    public void mock_ServerSafeMocked()
    {
        ServerMock server = MockBukkit.getOrCreateMock();
        assertNotNull(server);
        assertEquals(server, MockBukkit.getMock());
        assertEquals(server, MockBukkit.getOrCreateMock());
    }

    @Test
    public void mock_CustomServerMocked()
    {
        CustomServerMock server = MockBukkit.mock(new CustomServerMock());
        assertNotNull(server);
        assertEquals(server, MockBukkit.getMock());
        assertEquals(server, Bukkit.getServer());
    }

    @Test
    public void isMocked_ServerNotMocked_False()
    {
        assertFalse(MockBukkit.isMocked());
    }

    @Test
    public void isMocked_ServerMocked_True()
    {
        MockBukkit.mock();
        assertTrue(MockBukkit.isMocked());
    }

    @Test
    public void isMocked_ServerUnloaded_False()
    {
        MockBukkit.mock();
        MockBukkit.unmock();
        assertFalse(MockBukkit.isMocked());
    }

    @Test
    public void load_MyPlugin()
    {
        ServerMock server = MockBukkit.mock();
        TestPlugin plugin = MockBukkit.load(TestPlugin.class);
        server.getPluginManager().assertEventFired(PluginEnableEvent.class, event -> event.getPlugin().equals(plugin));
        assertTrue("Plugin not enabled", plugin.isEnabled());
        assertTrue("Plugin's onEnable method not executed", plugin.onEnableExecuted);
    }

    @Test
    public void load_TestPluginWithExtraParameter_ExtraParameterPassedOn()
    {
        MockBukkit.mock();
        TestPlugin plugin = MockBukkit.load(TestPlugin.class, Integer.valueOf(5));
        assertThat(plugin.extra, equalTo(5));
    }

    @Test(expected = RuntimeException.class)
    public void load_TestPluginWithExtraIncorrectParameter_ExceptionThrown()
    {
        MockBukkit.mock();
        MockBukkit.load(TestPlugin.class, "Hello");
    }

    @Test
    public void loadWith_SecondTextPluginAndResourceFileAsString_PluginLoaded()
    {
        MockBukkit.mock();
        SecondTestPlugin plugin = MockBukkit.loadWith(SecondTestPlugin.class, "second_plugin.yml");
        assertEquals("Name was not loaded correctly", "SecondTestPlugin", plugin.getName());
    }

    @Test
    public void loadSimple_SecondTextPlugin_PluginLoaded()
    {
        MockBukkit.mock();
        SecondTestPlugin plugin = MockBukkit.loadSimple(SecondTestPlugin.class);
        assertEquals("Name was not set correctly", "SecondTestPlugin", plugin.getName());
        assertEquals("Version was not set correctly", "1.0.0", plugin.getDescription().getVersion());
    }

    @Test
    public void createMockPlugin_CreatesMockPlugin()
    {
        MockBukkit.mock();
        MockPlugin plugin = MockBukkit.createMockPlugin();
        assertEquals("MockPlugin", plugin.getName());
        assertEquals("1.0.0", plugin.getDescription().getVersion());
        assertTrue(plugin.isEnabled());
    }

    @Test
    public void unload_PluginLoaded_PluginDisabled()
    {
        MockBukkit.mock();
        TestPlugin plugin = MockBukkit.load(TestPlugin.class);
        assumeFalse(plugin.onDisableExecuted);
        MockBukkit.unmock();
        assertFalse(plugin.isEnabled());
        assertTrue(plugin.onDisableExecuted);
    }

    @Test
    public void load_CanLoadPluginFromExternalSource_PluginLoaded()
    {
        MockBukkit.mock();
        MockBukkit.loadJar("extra/TestPlugin/TestPlugin.jar");
        Plugin[] plugins = MockBukkit.getMock().getPluginManager().getPlugins();
        assertThat(plugins.length, equalTo(1));
        assertThat(plugins[0].getName(), equalTo("TestPlugin"));
    }

    public static class CustomServerMock extends ServerMock
    {
    }
}
