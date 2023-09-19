package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ChestBoatMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private ChestBoatMock boat;

	@BeforeEach
	void setUp() throws Exception
	{
		boat = new ChestBoatMock(server, UUID.randomUUID());
	}

	@Test
	void testIsRefillEnabledDefault()
	{
		assertFalse(boat.isRefillEnabled());
	}

	@Test
	void testIsRefillEnabled()
	{
		boat.setRefillEnabled(true);
		assertTrue(boat.isRefillEnabled());
	}

	@Test
	void testHasBeenFilledDefault()
	{
		assertFalse(boat.hasBeenFilled());
	}

	@Test
	void testHasBeenFilled()
	{
		boat.setNextRefill(1);
		server.getScheduler().performTicks(2);
		assertTrue(boat.hasBeenFilled());
	}

	@Test
	void testHasPlayerLootedDefault()
	{
		Player player = server.addPlayer();
		assertFalse(boat.hasPlayerLooted(player));
	}

	@Test
	void testHasPlayerLooted()
	{
		Player player = server.addPlayer();
		boat.setHasPlayerLooted(player, true);
		assertTrue(boat.hasPlayerLooted(player));
	}

	@Test
	void testGetLastLooted()
	{
		Player player = server.addPlayer();
		long time = System.currentTimeMillis();
		boat.setHasPlayerLooted(player, true);
		assertNotNull(boat.getLastLooted(player));
		assertTrue(time <= boat.getLastLooted(player));
	}

	@Test
	void testSetHasPlayerLootedTwiceSameValue() throws InterruptedException
	{
		Player player = server.addPlayer();
		long time = System.currentTimeMillis();
		boat.setHasPlayerLooted(player, true);
		assertEquals(time, boat.getLastLooted(player));
		TimeUnit.MILLISECONDS.sleep(10);
		boat.setHasPlayerLooted(player, true);
		assertEquals(time, boat.getLastLooted(player));
	}

	@Test
	void testSetHasPlayerLootedLootedFalse()
	{
		Player player = server.addPlayer();
		boat.setHasPlayerLooted(player, true);
		assertTrue(boat.hasPlayerLooted(player));
		boat.setHasPlayerLooted(player, false);
		assertFalse(boat.hasPlayerLooted(player));
	}

	@Test
	void testSetRefillEnabled()
	{
		boat.setRefillEnabled(true);
		assertTrue(boat.isRefillEnabled());
	}

	@Test
	void testHasPendingRefill()
	{
		assertFalse(boat.hasPendingRefill());
		boat.setNextRefill(1);
		assertTrue(boat.hasPendingRefill());
	}

	@Test
	void testGetLastFilled()
	{
		assertEquals(-1, boat.getLastFilled());
		boat.setNextRefill(1);
		server.getScheduler().performTicks(2);
		assertTrue(boat.getLastFilled() >= 1);
	}

	@Test
	void testGetNextRefill()
	{
		assertEquals(-1, boat.getNextRefill());
		boat.setNextRefill(1);
		assertEquals(1, boat.getNextRefill());
	}

	@Test
	void testGetInventory()
	{
		assertNotNull(boat.getInventory());
		assertEquals(27, boat.getInventory().getSize());
		assertEquals(boat, boat.getInventory().getHolder());
	}

	@Test
	void testGetEntity()
	{
		assertEquals(boat, boat.getEntity());
	}

}
