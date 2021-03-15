package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.event.entity.EntityDeathEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		PlayerMock player = server.addPlayer();

		zombie.simulateKilledBy(player);

		server.getPluginManager().assertEventFired(EntityDeathEvent.class);
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
