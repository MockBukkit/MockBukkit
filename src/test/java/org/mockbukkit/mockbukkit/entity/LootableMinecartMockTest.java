package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LootableMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private LootableMinecart minecart;

	@BeforeEach
	void setUp() throws Exception
	{
		minecart = new StorageMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testHasPlayerLootedDefault()
	{
		Player player = server.addPlayer();
		assertFalse(minecart.hasPlayerLooted(player));
	}

	@Test
	void testHasPlayerLooted()
	{
		Player player = server.addPlayer();
		minecart.setHasPlayerLooted(player, true);
		assertTrue(minecart.hasPlayerLooted(player));
	}

	@Test
	void testGetLastLooted()
	{
		Player player = server.addPlayer();
		long time = System.currentTimeMillis();
		minecart.setHasPlayerLooted(player, true);
		assertTrue(time <= minecart.getLastLooted(player));
	}

	@Test
	void testSetHasPlayerLootedTwiceSameValue() throws InterruptedException
	{
		Player player = server.addPlayer();
		long time = System.currentTimeMillis();
		minecart.setHasPlayerLooted(player, true);
		assertEquals(time, minecart.getLastLooted(player));
		TimeUnit.MILLISECONDS.sleep(10);
		minecart.setHasPlayerLooted(player, true);
		assertEquals(time, minecart.getLastLooted(player));
	}

	@Test
	void testSetHasPlayerLootedLootedFalse()
	{
		Player player = server.addPlayer();
		minecart.setHasPlayerLooted(player, true);
		assertTrue(minecart.hasPlayerLooted(player));
		minecart.setHasPlayerLooted(player, false);
		assertFalse(minecart.hasPlayerLooted(player));
	}

	@Test
	void testIsRefillEnabledDefault()
	{
		assertFalse(minecart.isRefillEnabled());
	}

	@Test
	void testSetRefillEnabled()
	{
		minecart.setRefillEnabled(true);
		assertTrue(minecart.isRefillEnabled());
	}

	@Test
	void testHasBeenFilledDefault()
	{
		assertFalse(minecart.hasBeenFilled());
	}

	@Test
	void testSetHasBeenFilled()
	{
		minecart.setNextRefill(1);
		server.getScheduler().performTicks(2);
		assertTrue(minecart.hasBeenFilled());
	}

	@Test
	void testGetNextRefill()
	{
		minecart.setNextRefill(1);
		assertEquals(1, minecart.getNextRefill());
	}

	@Test
	void testGetLastFilled()
	{
		assertEquals(-1, minecart.getLastFilled());
		minecart.setNextRefill(1);
		server.getScheduler().performTicks(2);
		assertEquals(1, minecart.getLastFilled());
	}

	@Test
	void testHasPendingRefill()
	{
		assertFalse(minecart.hasPendingRefill());
		minecart.setNextRefill(1);
		assertTrue(minecart.hasPendingRefill());
	}

	@Test
	void testGetLastLootedMapNull()
	{
		assertNull(minecart.getLastLooted(UUID.randomUUID()));
	}

}
