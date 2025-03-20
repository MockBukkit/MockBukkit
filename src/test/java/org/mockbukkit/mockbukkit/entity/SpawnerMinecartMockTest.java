package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SpawnerMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private SpawnerMinecart minecart;

	@BeforeEach
	public void setUp() throws Exception
	{
		minecart = new SpawnerMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.MINECART);
	}

	@Test
	void testGetType()
	{
		assertEquals(minecart.getType(), EntityType.SPAWNER_MINECART);
	}

	@Nested
	class GetBoundingBox
	{
		@Test
		void givenDefaultLocation()
		{
			BoundingBox actual = minecart.getBoundingBox();
			assertNotNull(actual);

			assertEquals(-0.49, actual.getMinX());
			assertEquals(0, actual.getMinY());
			assertEquals(-0.49, actual.getMinZ());

			assertEquals(0.49, actual.getMaxX());
			assertEquals(0.7, actual.getMaxY());
			assertEquals(0.49, actual.getMaxZ());
		}

		@Test
		void givenCustomLocation()
		{
			minecart.teleport(new Location(minecart.getWorld(), 10, 5, 10));

			BoundingBox actual = minecart.getBoundingBox();
			assertNotNull(actual);

			assertEquals(9.51, actual.getMinX());
			assertEquals(5, actual.getMinY());
			assertEquals(9.51, actual.getMinZ());

			assertEquals(10.49, actual.getMaxX());
			assertEquals(5.7, actual.getMaxY());
			assertEquals(10.49, actual.getMaxZ());
		}
	}

	@Nested
	class SetSpawnedType
	{

		@Test
		void givenDefaultValue_ShouldReturnNull()
		{
			assertNull(minecart.getSpawnedType());
		}

		@ParameterizedTest
		@NullSource
		@EnumSource(value = EntityType.class,
					mode = EnumSource.Mode.EXCLUDE,
					names = "UNKNOWN")
		void givenPossibleValues_ShouldReturnCorrectValue(EntityType entityType)
		{
			minecart.setSpawnedType(entityType);
			assertEquals(entityType, minecart.getSpawnedType());
		}

		@Test
		void givenUnknownValue_ShouldThrowIllegalArgumentException()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> minecart.setSpawnedType(EntityType.UNKNOWN));
			assertEquals("Can't spawn EntityType UNKNOWN from mob spawners!", e.getMessage());
		}

	}

	@Nested
	class SetDelay
	{

		@Test
		void givenDefaultValue_ShouldReturnTwenty()
		{
			assertEquals(20, minecart.getDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
		void givenPossibleValues_ShouldReturnCorrectValue(int delay)
		{
			minecart.setDelay(delay);
			assertEquals(delay, minecart.getDelay());
		}

	}

	@Nested
	class SetMinSpawnDelay
	{

		@Test
		void givenDefaultValue_ShouldReturnTwoHundred()
		{
			assertEquals(200, minecart.getMinSpawnDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 799, 800})
		void givenPossibleValues_ShouldReturnCorrectValue(int delay)
		{
			minecart.setMinSpawnDelay(delay);
			assertEquals(delay, minecart.getMinSpawnDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = {801, 802, 803, 804, 805})
		void givenValueBiggerThenMaximum_ShouldThrowIllegalArgumentException(int value)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> minecart.setMinSpawnDelay(value));
			assertEquals("Minimum Spawn Delay must be less than or equal to Maximum Spawn Delay", e.getMessage());
		}

	}

	@Nested
	class SetMaxSpawnDelay
	{

		@Test
		void givenDefaultValue_ShouldReturnEightHundred()
		{
			assertEquals(800, minecart.getMaxSpawnDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = {200, 201, 202, 203, 204, 205, 300, 400, 500, 600, 700, 800})
		void givenPossibleValues_ShouldReturnCorrectValue(int delay)
		{
			minecart.setMaxSpawnDelay(delay);
			assertEquals(delay, minecart.getMaxSpawnDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = {-3, -2, -1})
		void givenNegativeValue_ShouldThrowIllegalArgumentException(int value)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> minecart.setMaxSpawnDelay(value));
			assertEquals("Maximum Spawn Delay must be greater than 0.", e.getMessage());
		}

		@ParameterizedTest
		@ValueSource(ints = {197, 198, 199})
		void givenValueBiggerThenMaximum_ShouldThrowIllegalArgumentException(int value)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> minecart.setMaxSpawnDelay(value));
			assertEquals("Maximum Spawn Delay must be greater than or equal to Minimum Spawn Delay", e.getMessage());
		}

	}

	@Nested
	class SetSpawnCount
	{

		@Test
		void givenDefaultValue_ShouldReturnFour()
		{
			assertEquals(4, minecart.getSpawnCount());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
		void givenPossibleValues_ShouldReturnCorrectValue(int spawnCount)
		{
			minecart.setSpawnCount(spawnCount);
			assertEquals(spawnCount, minecart.getSpawnCount());
		}

	}

	@Nested
	class SetRequiredPlayerRange
	{

		@Test
		void givenDefaultValue_ShouldReturnSixteen()
		{
			assertEquals(16, minecart.getRequiredPlayerRange());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
		void givenPossibleValues_ShouldReturnCorrectValue(int spawnCount)
		{
			minecart.setRequiredPlayerRange(spawnCount);
			assertEquals(spawnCount, minecart.getRequiredPlayerRange());
		}

	}

	@Nested
	class SetSpawnRange
	{

		@Test
		void givenDefaultValue_ShouldReturnFour()
		{
			assertEquals(4, minecart.getSpawnRange());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
		void givenPossibleValues_ShouldReturnCorrectValue(int spawnCount)
		{
			minecart.setSpawnRange(spawnCount);
			assertEquals(spawnCount, minecart.getSpawnRange());
		}

	}

	@Nested
	class SetMaxNearbyEntities
	{

		@Test
		void givenDefaultValue_ShouldReturnSix()
		{
			assertEquals(6, minecart.getMaxNearbyEntities());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
		void givenPossibleValues_ShouldReturnCorrectValue(int spawnCount)
		{
			minecart.setMaxNearbyEntities(spawnCount);
			assertEquals(spawnCount, minecart.getMaxNearbyEntities());
		}

	}
}
