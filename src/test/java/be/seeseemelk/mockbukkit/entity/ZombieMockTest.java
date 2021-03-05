package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class ZombieMockTest
{
	private ServerMock server;
	private ZombieMock zombie;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		zombie = new ZombieMock(server, UUID.randomUUID());
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void simulateKilledBy_GivenPlayer()
	{
		AtomicBoolean eventDispatched = new AtomicBoolean();
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		PlayerMock player = server.addPlayer();

		server.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onDeath(EntityDeathEvent event)
			{
				eventDispatched.set(true);
			}
		}, plugin);
		zombie.simulateKilledBy(player);

		assertTrue(eventDispatched.get());
		assertEquals(player, zombie.getKiller());
	}

	@Test
	public void simulateKilledBy_NoPlayer()
	{
		zombie.simulateKilledBy(null);
		assertNull(zombie.getKiller());
		assertTrue(zombie.isDead());
	}
}
