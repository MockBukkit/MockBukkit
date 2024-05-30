package be.seeseemelk.mockbukkit.statistic;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatisticsTest
{

	private ServerMock mock;
	private PlayerMock player;

	@BeforeEach
	void setUp()
	{
		mock = MockBukkit.mock();
		player = mock.addPlayer();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testDefaults()
	{
		assertEquals(0, player.getStatistic(Statistic.DEATHS));
		assertEquals(0, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(0, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));
	}

	@Test
	void testGet()
	{
		player.setStatistic(Statistic.DEATHS, 9);
		player.setStatistic(Statistic.MINE_BLOCK, Material.STONE, 2);
		player.setStatistic(Statistic.KILL_ENTITY, EntityType.SQUID, 8);

		assertEquals(9, player.getStatistic(Statistic.DEATHS));
		assertEquals(2, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(8, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));
	}

	@Test
	void testIncrement()
	{
		player.setStatistic(Statistic.DEATHS, 400);
		player.setStatistic(Statistic.MINE_BLOCK, Material.STONE, 500);
		player.setStatistic(Statistic.KILL_ENTITY, EntityType.SQUID, 600);

		player.incrementStatistic(Statistic.DEATHS);
		player.incrementStatistic(Statistic.MINE_BLOCK, Material.STONE);
		player.incrementStatistic(Statistic.KILL_ENTITY, EntityType.SQUID);

		assertEquals(401, player.getStatistic(Statistic.DEATHS));
		assertEquals(501, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(601, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));

		player.incrementStatistic(Statistic.DEATHS, 10);
		player.incrementStatistic(Statistic.MINE_BLOCK, Material.STONE, 20);
		player.incrementStatistic(Statistic.KILL_ENTITY, EntityType.SQUID, 30);

		assertEquals(411, player.getStatistic(Statistic.DEATHS));
		assertEquals(521, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(631, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));
	}

	@Test
	void testDecrement()
	{
		player.setStatistic(Statistic.DEATHS, 411);
		player.setStatistic(Statistic.MINE_BLOCK, Material.STONE, 521);
		player.setStatistic(Statistic.KILL_ENTITY, EntityType.SQUID, 631);

		player.decrementStatistic(Statistic.DEATHS);
		player.decrementStatistic(Statistic.MINE_BLOCK, Material.STONE);
		player.decrementStatistic(Statistic.KILL_ENTITY, EntityType.SQUID);

		assertEquals(410, player.getStatistic(Statistic.DEATHS));
		assertEquals(520, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(630, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));

		player.decrementStatistic(Statistic.DEATHS, 10);
		player.decrementStatistic(Statistic.MINE_BLOCK, Material.STONE, 20);
		player.decrementStatistic(Statistic.KILL_ENTITY, EntityType.SQUID, 30);

		assertEquals(400, player.getStatistic(Statistic.DEATHS));
		assertEquals(500, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(600, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));
	}

	@Test
	void testTyped()
	{
		player.setStatistic(Statistic.MINE_BLOCK, Material.STONE, 3);
		player.setStatistic(Statistic.KILL_ENTITY, EntityType.SQUID, 8);

		player.incrementStatistic(Statistic.MINE_BLOCK, Material.CACTUS);
		player.incrementStatistic(Statistic.KILL_ENTITY, EntityType.ZOGLIN);

		assertEquals(3, player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
		assertEquals(8, player.getStatistic(Statistic.KILL_ENTITY, EntityType.SQUID));
	}

	@Test
	void testNegativeIncrement()
	{
		player.setStatistic(Statistic.DEATHS, 7);

		assertThrows(IllegalArgumentException.class, () -> player.incrementStatistic(Statistic.DEATHS, -1));

		assertThrows(IllegalArgumentException.class, () -> player.incrementStatistic(Statistic.DEATHS, 0));
	}

	@Test
	void testNegativeDecrement()
	{
		player.setStatistic(Statistic.DEATHS, 7);

		assertThrows(IllegalArgumentException.class, () -> player.decrementStatistic(Statistic.DEATHS, -1));

		assertThrows(IllegalArgumentException.class, () -> player.decrementStatistic(Statistic.DEATHS, 0));
	}

	@Test
	void testNegativeSet()
	{
		player.setStatistic(Statistic.DEATHS, 5);

		assertThrows(IllegalArgumentException.class, () -> player.setStatistic(Statistic.DEATHS, -1));

		player.setStatistic(Statistic.DEATHS, 0);
	}

	@Test
	void testType()
	{
		assertThrows(IllegalArgumentException.class,
				() -> player.setStatistic(Statistic.DEATHS, Material.ACACIA_LOG, 5));

		assertThrows(IllegalArgumentException.class, () -> player.setStatistic(Statistic.DEATHS, EntityType.SQUID, 4));

		assertThrows(IllegalArgumentException.class, () -> player.setStatistic(Statistic.MINE_BLOCK, 10));

		assertThrows(IllegalArgumentException.class,
				() -> player.setStatistic(Statistic.MINE_BLOCK, EntityType.BAT, 10));
	}

}
