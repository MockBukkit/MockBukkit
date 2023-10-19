package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LootableContainerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private BarrelMock barrel;

	@BeforeEach
	public void setUp() throws Exception
	{
		World world = new WorldMock();
		Block block = world.getBlockAt(0, 10, 0);
		block.setType(Material.BARREL);
		this.barrel = new BarrelMock(block);
	}

	@Test
	void testIsRefillEnabledDefault()
	{
		assertFalse(barrel.isRefillEnabled());
	}

	@Test
	void testSetRefillEnabled()
	{
		barrel.setRefillEnabled(true);
		assertTrue(barrel.isRefillEnabled());
	}

	@Test
	void testHasBeenFilledDefault()
	{
		assertFalse(barrel.hasBeenFilled());
	}

	@Test
	void testSetNextRefill()
	{
		barrel.setNextRefill(1);
		server.getScheduler().performTicks(2);
		assertTrue(barrel.hasBeenFilled());
	}

	@Test
	void testGetNextRefill()
	{
		barrel.setNextRefill(1);
		assertEquals(1, barrel.getNextRefill());
	}

	@Test
	void testGetLastFilled()
	{
		assertEquals(-1, barrel.getLastFilled());
		barrel.setNextRefill(1);
		server.getScheduler().performTicks(2);
		assertEquals(1, barrel.getLastFilled());
	}

	@Test
	void testHasPendingRefill()
	{
		assertFalse(barrel.hasPendingRefill());
		barrel.setNextRefill(1);
		assertTrue(barrel.hasPendingRefill());
	}

	@Test
	void testGetLastLootedMapNull()
	{
		assertNull(barrel.getLastLooted(UUID.randomUUID()));
	}

	@Test
	void testHasPlayerLootedDefault()
	{
		Player player = server.addPlayer();
		assertFalse(barrel.hasPlayerLooted(player));
	}

	@Test
	void testHasPlayerLooted()
	{
		Player player = server.addPlayer();
		barrel.setHasPlayerLooted(player, true);
		assertTrue(barrel.hasPlayerLooted(player));
	}

	@Test
	void testGetLastLooted()
	{
		Player player = server.addPlayer();
		long time = System.currentTimeMillis();
		barrel.setHasPlayerLooted(player, true);
		assertTrue(time <= barrel.getLastLooted(player));
	}

	@Test
	void testSetHasPlayerLootedTwiceSameValue() throws InterruptedException
	{
		Player player = server.addPlayer();
		long time = System.currentTimeMillis();
		barrel.setHasPlayerLooted(player, true);
		assertEquals(time, barrel.getLastLooted(player));
		TimeUnit.MILLISECONDS.sleep(10);
		barrel.setHasPlayerLooted(player, true);
		assertEquals(time, barrel.getLastLooted(player));
	}

	@Test
	void testSetHasPlayerLootedLootedFalse()
	{
		Player player = server.addPlayer();
		barrel.setHasPlayerLooted(player, true);
		assertTrue(barrel.hasPlayerLooted(player));
		barrel.setHasPlayerLooted(player, false);
		assertFalse(barrel.hasPlayerLooted(player));
	}

	@Test
	void testSetNextRefillNegative()
	{
		barrel.setNextRefill(-2);
		assertEquals(-1, barrel.getNextRefill());
	}

	@Test
	void testEquals()
	{
		BarrelMock barrel2 = new BarrelMock(barrel);
		assertEquals(barrel, barrel2);
	}

	@Test
	void testNotEquals()
	{
		World world = new WorldMock();
		Block block = world.getBlockAt(0, 10, 0);
		block.setType(Material.BARREL);
		BarrelMock barrel2 = new BarrelMock(block);
		assertNotEquals(barrel, barrel2);
	}

	@Test
	void testHashcode()
	{
		BarrelMock barrel2 = new BarrelMock(barrel);
		assertEquals(barrel.hashCode(), barrel2.hashCode());
	}

}
