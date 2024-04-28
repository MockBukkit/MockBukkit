package org.mockbukkit.mockbukkit.matcher.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasFiredEventClass;

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
		assertMatches(hasFiredEventClass(AnEvent.class), pluginManager);
	}

	@Test
	void hasFiredEvent_noEventFired()
	{
		assertDoesNotMatch(hasFiredEventClass(AnEvent.class), pluginManager);
	}

	@Test
	void hasFiredEvent_differentClass()
	{
		pluginManager.callEvent(new AnEvent());
		assertDoesNotMatch(hasFiredEventClass(AsyncPlayerPreLoginEvent.class), pluginManager);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasFiredEventClass(AsyncPlayerPreLoginEvent.class);
	}

	/**
	 * An event constructor can always change, let's instead have a custom event that does not change constructor.
	 */
	private static class AnEvent extends Event
	{

		private final static HandlerList handlers = new HandlerList();

		@Override
		public @NotNull HandlerList getHandlers()
		{
			return handlers;
		}

		public static @NotNull HandlerList getHandlerList()
		{
			return handlers;
		}

	}

}
