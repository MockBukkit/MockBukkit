package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PiglinBruteMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private PiglinBruteMock piglinBrute;

	@BeforeEach
	void setUp()
	{
		piglinBrute = new PiglinBruteMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.PIGLIN_BRUTE, piglinBrute.getType());
	}

	@Test
	void canBreed_ShouldAlwaysBeFalse()
	{
		assertFalse(piglinBrute.canBreed());
	}

	@Test
	void getAgeLock_ShouldAlwaysBeFalse()
	{
		assertFalse(piglinBrute.getAgeLock());
	}

	@Nested
	class SetImmuneToZombification
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(piglinBrute.isImmuneToZombification());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenPossibleValues(boolean expectedValue)
		{
			piglinBrute.setImmuneToZombification(expectedValue);

			assertEquals(expectedValue, piglinBrute.isImmuneToZombification());
		}

	}

	@Nested
	class SetConversionTime
	{

		@Test
		void givenDefault_WhenEntityIsNotInWorld_ShouldThrow()
		{
			IllegalStateException e = assertThrows(IllegalStateException.class, () -> piglinBrute.getConversionTime());
			assertEquals("Entity is not in a world.", e.getMessage());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
		void givenPossibleConversionTime_ShouldSetConversionTime(int value)
		{
			WorldMock world = server.addSimpleWorld("world");
			PiglinBrute spawnedEntity = (PiglinBrute) world.spawnEntity(new Location(world, 0, 0, 0 ), EntityType.PIGLIN_BRUTE);

			spawnedEntity.setConversionTime(value);

			assertEquals(value, spawnedEntity.getConversionTime());
		}

		@ParameterizedTest
		@ValueSource(ints = {-5, -4, -3, -2, -1})
		void givenImpossibleConversionTime_ShouldResetConversion(int value)
		{
			WorldMock world = server.addSimpleWorld("world");
			PiglinBrute spawnedEntity = (PiglinBrute) world.spawnEntity(new Location(world, 0, 0, 0 ), EntityType.PIGLIN_BRUTE);

			spawnedEntity.setImmuneToZombification(true);

			spawnedEntity.setConversionTime(value);

			assertEquals(-1, spawnedEntity.getConversionTime());
			assertFalse(spawnedEntity.isImmuneToZombification());
		}

	}

	@Nested
	class IsConverting
	{

		@Test
		void giveEntityThatIsNotInWorld_ShouldThrow()
		{
			IllegalStateException e = assertThrows(IllegalStateException.class, () -> piglinBrute.isConverting());
			assertEquals("Entity is not in a world.", e.getMessage());
		}

		@Test
		void givenPiglinSafeWorld_ShouldReturnFalse()
		{
			WorldMock world = server.addSimpleWorld("world");
			world.setEnvironment(World.Environment.NETHER);
			PiglinBrute spawnedEntity = (PiglinBrute) world.spawnEntity(new Location(world, 0, 0, 0 ), EntityType.PIGLIN_BRUTE);

			assertFalse(spawnedEntity.isConverting());
		}

		@Test
		void givenNonPiglinSafeWorldAndImmuneToZombification_ShouldReturnFalse()
		{
			WorldMock world = server.addSimpleWorld("world");
			world.setEnvironment(World.Environment.NORMAL);
			PiglinBrute spawnedEntity = (PiglinBrute) world.spawnEntity(new Location(world, 0, 0, 0 ), EntityType.PIGLIN_BRUTE);
			spawnedEntity.setImmuneToZombification(true);

			assertFalse(spawnedEntity.isConverting());
		}

		@Test
		void givenNonPiglinSafeWorldAndNotImmuneToZombificationAndNoAi_ShouldReturnFalse()
		{
			WorldMock world = server.addSimpleWorld("world");
			world.setEnvironment(World.Environment.NORMAL);
			PiglinBrute spawnedEntity = (PiglinBrute) world.spawnEntity(new Location(world, 0, 0, 0 ), EntityType.PIGLIN_BRUTE);
			spawnedEntity.setImmuneToZombification(false);
			spawnedEntity.setAI(false);

			assertFalse(spawnedEntity.isConverting());
		}

		@Test
		void givenNonPiglinSafeWorldAndNotImmuneToZombificationAndWithAi_ShouldReturnTrue()
		{
			WorldMock world = server.addSimpleWorld("world");
			world.setEnvironment(World.Environment.NORMAL);
			PiglinBrute spawnedEntity = (PiglinBrute) world.spawnEntity(new Location(world, 0, 0, 0 ), EntityType.PIGLIN_BRUTE);
			spawnedEntity.setImmuneToZombification(false);
			spawnedEntity.setAI(true);

			assertTrue(spawnedEntity.isConverting());
		}

	}

	@Nested
	class SetBaby
	{

		@Test
		void givenDefault_ShouldReturnFalse()
		{
			assertFalse(piglinBrute.isBaby());
			assertTrue(piglinBrute.isAdult());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenPossibleValues(boolean expectedValue)
		{
			piglinBrute.setBaby(expectedValue);

			assertEquals(expectedValue, piglinBrute.isBaby());
			assertEquals(!expectedValue, piglinBrute.isAdult());
		}

		@Test
		void givenSetBabyWithoutArguments()
		{
			piglinBrute.setBaby();

			assertTrue(piglinBrute.isBaby());
			assertFalse(piglinBrute.isAdult());
		}

		@Test
		void givenSetAdultWithoutArguments()
		{
			piglinBrute.setBaby();
			piglinBrute.setAdult();

			assertFalse(piglinBrute.isBaby());
			assertTrue(piglinBrute.isAdult());
		}

	}

	@Nested
	class SetAge
	{

		@Test
		void givenDefault_ShouldReturnFalse()
		{
			assertEquals(0, piglinBrute.getAge());
			assertFalse(piglinBrute.isBaby());
			assertTrue(piglinBrute.isAdult());
		}

		@ParameterizedTest
		@ValueSource(ints = {-3, -2, -1})
		void givenPossibleBabyValues(int expectedValue)
		{
			piglinBrute.setAge(expectedValue);

			assertEquals(-1, piglinBrute.getAge());
			assertTrue(piglinBrute.isBaby());
			assertFalse(piglinBrute.isAdult());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2})
		void givenPossibleAdultValues(int expectedValue)
		{
			piglinBrute.setAge(expectedValue);

			assertEquals(0, piglinBrute.getAge());
			assertFalse(piglinBrute.isBaby());
			assertTrue(piglinBrute.isAdult());
		}

	}

}
