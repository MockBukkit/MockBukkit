package org.mockbukkit.mockbukkit.matcher.plugin;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasFiredEventInstance;

@ExtendWith(MockBukkitExtension.class)
class PluginManagerFiredEventClassMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private PluginManagerMock pluginManager;

	@BeforeEach
	void setUp()
	{
		this.pluginManager = serverMock.getPluginManager();
	}

	@Test
	void hasFiredEvent_matches()
	{
		this.pluginManager.callEvent(new AnEvent());
		assertMatches(hasFiredEventInstance(AnEvent.class), pluginManager);
	}

	@Test
	void hasFiredEvent_noEventFired()
	{
		assertDoesNotMatch(hasFiredEventInstance(AnEvent.class), pluginManager);
	}

	@Test
	void hasFiredEvent_differentClass()
	{
		pluginManager.callEvent(new AnEvent());
		assertDoesNotMatch(hasFiredEventInstance(AsyncPlayerPreLoginEvent.class), pluginManager);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasFiredEventInstance(AsyncPlayerPreLoginEvent.class);
	}

}
