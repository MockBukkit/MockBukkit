package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.event.RaidMock;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RaiderMockTest
{

	private ServerMock server;
	private RaiderMock raider;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		raider = new RaiderMock(server, UUID.randomUUID())
		{
			@Override
			public @NotNull Sound getCelebrationSound()
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getRaid_GivenDefaultValue()
	{
		assertNull(raider.getRaid());
	}

	@Test
	void getRaid_GivenRaidValue()
	{
		World world = new WorldMock();
		Location location = new Location(world, 0, 0, 0);
		Raid raid = new RaidMock(1, location);

		raider.setRaid(raid);
		Raid actual = raider.getRaid();
		assertNotNull(actual);
		assertSame(raid, actual);
	}

	@Test
	void getWave_GivenDefaultValue()
	{
		assertEquals(0, raider.getWave());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
	void getWave_GivenValidUserValue(int expectedValue)
	{
		raider.setWave(expectedValue);
		assertEquals(expectedValue, raider.getWave());
	}

	@ParameterizedTest
	@ValueSource(ints = {-100, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1})
	void setWave_GivenIllegalUserValue(int expectedValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raider.setWave(expectedValue));
		assertEquals("wave must be >= 0", e.getMessage());
	}

	@Test
	void getPatrolTarget_GivenDefaultValue()
	{
		assertNull(raider.getPatrolTarget());
	}

	@Test
	void getPatrolTarget_GivenRaidValue()
	{
		Block block = new BlockMock(Material.GRASS_BLOCK);

		raider.setPatrolTarget(block);
		Block actual = raider.getPatrolTarget();
		assertNotNull(actual);
		assertSame(block, actual);
	}

	@Test
	void isPatrolLeader_GivenDefaultValue()
	{
		assertFalse(raider.isPatrolLeader());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isPatrolLeader_GivenUserValue(boolean expectedValue)
	{

		raider.setPatrolLeader(expectedValue);

		assertEquals(expectedValue, raider.isPatrolLeader());
	}

	@Test
	void isCanJoinRaid_GivenDefaultValue()
	{
		assertFalse(raider.isCanJoinRaid());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isCanJoinRaid_GivenUserValue(boolean expectedValue)
	{

		raider.setCanJoinRaid(expectedValue);

		assertEquals(expectedValue, raider.isCanJoinRaid());
	}

	@Test
	void getTicksOutsideRaid_GivenDefaultValue()
	{
		assertEquals(0, raider.getTicksOutsideRaid());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
	void getTicksOutsideRaid_GivenValidUserValue(int expectedValue)
	{
		raider.setTicksOutsideRaid(expectedValue);
		assertEquals(expectedValue, raider.getTicksOutsideRaid());
	}

	@ParameterizedTest
	@ValueSource(ints = {-100, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1})
	void setTicksOutsideRaid_GivenIllegalUserValue(int expectedValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raider.setTicksOutsideRaid(expectedValue));
		assertEquals("ticks must be >= 0", e.getMessage());
	}


	@Test
	void isCelebrating_GivenDefaultValue()
	{
		assertFalse(raider.isCelebrating());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isCelebrating_GivenUserValue(boolean expectedValue)
	{

		raider.setCelebrating(expectedValue);

		assertEquals(expectedValue, raider.isCelebrating());
	}

}
