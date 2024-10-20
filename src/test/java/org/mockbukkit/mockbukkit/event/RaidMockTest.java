package org.mockbukkit.mockbukkit.event;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.RaiderMock;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Raider;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class RaidMockTest
{

	private final World raidWorld = new WorldMock();
	private final Location raidLocation = new Location(raidWorld, 0, 0 ,0);
	private final RaidMock raid = new RaidMock(1, raidLocation);

	@MockBukkitInject
	private ServerMock server;

	@Test
	void constructor_GivenNullLocation()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new RaidMock(1, null));
		assertEquals("location cannot be null", e.getMessage());
	}

	@Test
	void isStarted_GivenDefaultValue()
	{
		assertFalse(raid.isStarted());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isStarted_GivenUserValue(boolean expectedValue)
	{

		raid.setStarted(expectedValue);

		assertEquals(expectedValue, raid.isStarted());
	}

	@Test
	void getActiveTicks_GivenDefaultValue()
	{
		assertEquals(0, raid.getActiveTicks());
	}

	@ParameterizedTest
	@ValueSource(longs = {0, 1, 2, 3, 5, 10, 100, 1000})
	void getActiveTicks_GivenValidUserValue(long expectedValue)
	{
		raid.setActiveTicks(expectedValue);
		assertEquals(expectedValue, raid.getActiveTicks());
	}

	@ParameterizedTest
	@ValueSource(ints = {-1000, -100, -10, -5, -3, -2, -1})
	void getActiveTicks_GivenIllegalUserValue(long expectedValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setActiveTicks(expectedValue));
		assertEquals("active ticks cannot be negative", e.getMessage());
	}

	@Test
	void getBadOmenLevel_GivenDefaultValue()
	{
		assertEquals(0, raid.getBadOmenLevel());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5})
	void getBadOmenLevel_GivenValidUserValue(int expectedValue)
	{
		raid.setBadOmenLevel(expectedValue);
		assertEquals(expectedValue, raid.getBadOmenLevel());
	}

	@ParameterizedTest
	@ValueSource(ints = {-1000, -100, -10, -5, -3, -2, -1, 6, 7, 8, 9, 10, 100})
	void getBadOmenLevel_GivenIllegalUserValue(int expectedValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setBadOmenLevel(expectedValue));
		assertEquals("Bad Omen level must be between 0 and 5", e.getMessage());
	}

	@ParameterizedTest
	@CsvSource({
			"0, 0, 0",
			"10, 20, 30",
			"30, 20, 10",
	})
	void getLocation(int x, int y, int z)
	{
		World world = new WorldMock();
		Location location = new Location(world, x, y, z);

		Raid testRaid = new RaidMock(1, location);

		Location actualLocation = testRaid.getLocation();
		assertSame(location, actualLocation);
	}

	@Test
	void getStatus_GivenDefaultValue()
	{
		assertEquals(Raid.RaidStatus.ONGOING, raid.getStatus());
	}

	@ParameterizedTest
	@EnumSource(Raid.RaidStatus.class)
	void getStatus_GivenValidUserValue(Raid.RaidStatus expectedValue)
	{
		raid.setStatus(expectedValue);
		assertEquals(expectedValue, raid.getStatus());
	}

	@Test
	void setStatus_GivenIllegalUserValue()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setStatus(null));
		assertEquals("status cannot be null", e.getMessage());
	}

	@Test
	void getSpawnedGroups_GivenDefaultValue()
	{
		assertEquals(0, raid.getSpawnedGroups());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5})
	void getSpawnedGroups_GivenValidUserValue(int expectedValue)
	{
		raid.setSpawnedGroups(expectedValue);
		assertEquals(expectedValue, raid.getSpawnedGroups());
	}

	@ParameterizedTest
	@ValueSource(ints = {-1000, -100, -10, -5, -3, -2, -1})
	void getSpawnedGroups_GivenIllegalUserValue(int expectedValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setSpawnedGroups(expectedValue));
		assertEquals("spawnedGroups cannot be negative", e.getMessage());
	}

	@ParameterizedTest
	@CsvSource({
			"0, 0, 0",
			"1, 0, 1",
			"1, 1, 1",
			"1, 2, 2",
			"2, 2, 3",
			"2, 1, 2",
	})
	void getTotalGroups_Given(int groupCount, int badOmenCount, int expectedCount)
	{
		raid.setWaves(groupCount);
		raid.setBadOmenLevel(badOmenCount);

		assertEquals(expectedCount, raid.getTotalGroups());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100})
	void getTotalWaves_GivenValue(int expectedCount)
	{
		raid.setWaves(expectedCount);

		assertEquals(expectedCount, raid.getTotalWaves());
	}

	@ParameterizedTest
	@CsvSource({
			"EASY, 3",
			"NORMAL, 5",
			"HARD, 7",
			"PEACEFUL, 0",
	})
	void setWaves_GivenDifficulty(Difficulty difficulty, int expectedCount)
	{
		raid.setWaves(difficulty);

		assertEquals(expectedCount, raid.getTotalWaves());
	}

	@Test
	void setWaves_GivenNull()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setWaves(null));
		assertEquals("difficulty cannot be null", e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(ints = {-1000, -100, -10, -5, -3, -2, -1})
	void setWaves_GivenNegativeValues(int expectedCount)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setWaves(expectedCount));
		assertEquals("waves cannot be negative", e.getMessage());
	}

	@Test
	void getTotalHealth()
	{

		Raider raiderA = createTestRaider();
		Raider raiderB = createTestRaider();

		raiderA.setHealth(20F);
		raiderB.setHealth(10F);

		raid.setRaiders(List.of(raiderA, raiderB));

		assertEquals(30F, raid.getTotalHealth());
	}

	@Test
	void getHeroes_GivenSetOfUuids()
	{
		Set<UUID> heroes = Set.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				UUID.randomUUID()
		);
		raid.setHeroes(heroes);

		Set<UUID> actual = raid.getHeroes();
		assertEquals(heroes, actual);
		assertNotSame(heroes, actual);

		UUID uuid = UUID.randomUUID();
		assertThrows(UnsupportedOperationException.class, () -> actual.add(uuid));
	}

	@Test
	void setHeroes_GivenNull()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setHeroes(null));
		assertEquals("Heroes cannot be null", e.getMessage());
	}

	@Test
	void getRaiders_GivenSetOfUuids()
	{
		List<Raider> raiders = List.of(
				createTestRaider(),
				createTestRaider()
		);
		raid.setRaiders(raiders);

		List<Raider> actual = raid.getRaiders();
		assertEquals(raiders, actual);
		assertNotSame(raiders, actual);

		Raider raider = createTestRaider();
		assertThrows(UnsupportedOperationException.class, () -> actual.add(raider));
	}

	@Test
	void setRaiders_GivenNull()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> raid.setRaiders(null));
		assertEquals("Raiders cannot be null", e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100, 1000})
	void getId(int expectedId)
	{
		World world = new WorldMock();
		Location location = new Location(world, 0, 0, 0);

		Raid testRaid = new RaidMock(expectedId, location);

		assertEquals(expectedId, testRaid.getId());
	}

	@Test
	void getBossBar()
	{
		BossBar actual = raid.getBossBar();
		assertNotNull(actual);
		assertSame(actual, raid.getBossBar());
	}

	@Test
	void getPersistentDataContainer()
	{
		PersistentDataContainer actual = raid.getPersistentDataContainer();
		assertNotNull(actual);
		assertSame(actual, raid.getPersistentDataContainer());
	}

	/**
	 * Create a {@link Raider} to be used in the tests.
	 *
	 * @return the raider to be used in tests.
	 */
	private @NotNull Raider createTestRaider()
	{
		return new RaiderMock(server, UUID.randomUUID())
		{
			@Override
			public @NotNull Sound getCelebrationSound()
			{
				throw new UnimplementedOperationException();
			}
		};
	}

}
