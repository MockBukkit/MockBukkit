package org.mockbukkit.mockbukkit.event;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.TestPlugin;
import org.mockbukkit.mockbukkit.entity.CowMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class EventFactoryMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void callEntityRemoveEvent_GivenSuccess()
	{
		Plugin plugin = MockBukkit.load(TestPlugin.class);
		CowMock cow = new CowMock(server, UUID.randomUUID());
		EventHolder eventHolder = new EventHolder();
		Bukkit.getPluginManager().registerEvents(eventHolder, plugin);

		EventFactoryMock.callEntityRemoveEvent(cow, EntityRemoveEvent.Cause.DEATH);

		Event e = eventHolder.getEvent();
		assertNotNull(e);

		EntityRemoveEvent entityRemoveEvent = assertInstanceOf(EntityRemoveEvent.class, e);
		assertSame(cow, entityRemoveEvent.getEntity());
		assertEquals(EntityRemoveEvent.Cause.DEATH, entityRemoveEvent.getCause());
	}

	@Test
	void callEntityRemoveEvent_GivenWorldChange()
	{
		Plugin plugin = MockBukkit.load(TestPlugin.class);
		CowMock cow = new CowMock(server, UUID.randomUUID());
		EventHolder eventHolder = new EventHolder();
		Bukkit.getPluginManager().registerEvents(eventHolder, plugin);

		EventFactoryMock.callEntityRemoveEvent(cow, null);

		Event e = eventHolder.getEvent();
		assertNull(e);
	}

	@Test
	void callEntityRemoveEvent_GivenPlayerEntity()
	{
		Plugin plugin = MockBukkit.load(TestPlugin.class);
		PlayerMock player = server.addPlayer();
		EventHolder eventHolder = new EventHolder();
		Bukkit.getPluginManager().registerEvents(eventHolder, plugin);

		EventFactoryMock.callEntityRemoveEvent(player, null);

		Event e = eventHolder.getEvent();
		assertNull(e);
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
