package be.seeseemelk.mockbukkit.event;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.entity.CowMock;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class EventFactoryMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void callEntityRemoveEvent()
	{
		Plugin plugin = MockBukkit.load(TestPlugin.class);
		CowMock cowMock = new CowMock(server, UUID.randomUUID());
		EventHolder eventHolder = new EventHolder();
		Bukkit.getPluginManager().registerEvents(eventHolder, plugin);

		EventFactoryMock.callEntityRemoveEvent(cowMock, EntityRemoveEvent.Cause.DEATH);

		Event e = eventHolder.getEvent();
		assertNotNull(e);

		EntityRemoveEvent entityRemoveEvent = assertInstanceOf(EntityRemoveEvent.class, e);
		assertSame(cowMock, entityRemoveEvent.getEntity());
		assertEquals(EntityRemoveEvent.Cause.DEATH, entityRemoveEvent.getCause());
	}

	/**
	 * Test listener used to "catch" events.
	 */
	private static class EventHolder implements Listener
	{
		private @Nullable Event event;

		@Nullable
		public Event getEvent()
		{
			return event;
		}

		@EventHandler
		public void onEntityRemoveEvent(EntityRemoveEvent event)
		{
			this.event = event;
		}
	}

}
