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

import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent;

@ExtendWith(MockBukkitExtension.class)
class PluginManagerFiredEventFilterMatcherTest extends AbstractMatcherTest
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
		pluginManager.callEvent(new AnEvent());
		assertMatches(hasFiredFilteredEvent(AnEvent.class, ignored -> true), pluginManager);
	}

	@Test
	void hasFiredEvent_noEventFired()
	{
		assertDoesNotMatch(hasFiredFilteredEvent(AnEvent.class, ignored -> true), pluginManager);
	}

	@Test
	void hasFiredEvent_filteredOut()
	{
		pluginManager.callEvent(new AnEvent());
		assertDoesNotMatch(hasFiredFilteredEvent(AnEvent.class, ignored -> false), pluginManager);
	}

	@Test
	void hasFiredEvent_differentClass()
	{
		pluginManager.callEvent(new AnEvent());
		assertDoesNotMatch(hasFiredFilteredEvent(AsyncPlayerPreLoginEvent.class, ignored -> true), pluginManager);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasFiredFilteredEvent(AsyncPlayerPreLoginEvent.class, ignored -> true);
	}

}
