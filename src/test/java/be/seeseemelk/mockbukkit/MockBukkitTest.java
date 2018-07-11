package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;

import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginEnableEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
			MockBukkit.unload();
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
		MockBukkit.unload();
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
		TestPlugin plugin = MockBukkit.load(TestPlugin.class, new Integer(5));
		assertEquals(new Integer(5), plugin.extra);
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
		MockBukkit.unload();
		assertFalse(plugin.isEnabled());
		assertTrue(plugin.onDisableExecuted);
	}
	
}
