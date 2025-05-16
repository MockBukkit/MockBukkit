package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class VexMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private VexMock vex;

	@BeforeEach
	void setUp()
	{
		vex = new VexMock(server, UUID.randomUUID());
	}

	@Nested
	class SetCharging
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(vex.isCharging());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenValueChange(boolean expectedValue)
		{
			vex.setCharging(expectedValue);
			assertEquals(expectedValue, vex.isCharging());
		}

	}

	@Nested
	class SetBound
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(vex.getBound());
		}

		@Test
		void givenLocationInDifferentWorld_ExceptionIsThrown()
		{
			World worldA = server.addSimpleWorld("A");
			Location locationA = new Location(worldA, 0, 0, 0);
			vex.setLocation(locationA);

			World worldB = server.addSimpleWorld("B");
			Location locationB = new Location(worldB, 0, 0, 0);

			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> vex.setBound(locationB));
			assertEquals("The bound world cannot be different to the entity's world.", e.getMessage());
		}

		@Test
		void givenLocationInSameWorld_ShouldSucceed()
		{
			World worldA = server.addSimpleWorld("A");
			Location locationA = new Location(worldA, 0, 0, 0);
			vex.setLocation(locationA);

			Location locationB = new Location(worldA, 1, 2, 3);
			vex.setBound(locationB);

			assertEquals(locationB, vex.getBound());
			assertNotSame(locationB, vex.getBound());
		}

		@Test
		void givenLocationInNullWorld_ShouldSucceed()
		{
			Location locationA = new Location(null, 0, 0, 0);
			vex.setLocation(locationA);

			Location locationB = new Location(null, 1, 2, 3);
			vex.setBound(locationB);

			assertEquals(locationB, vex.getBound());
			assertNotSame(locationB, vex.getBound());

			vex.setBound(null);
			assertNull(vex.getBound());
		}

	}

	@Nested
	class SetSummoner
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(vex.getSummoner());
		}

		@Test
		void givenChangeInValue()
		{
			Mob mob = new IllusionerMock(server, UUID.randomUUID());

			vex.setSummoner(mob);
			assertEquals(mob, vex.getSummoner());

			vex.setSummoner(null);
			assertNull(vex.getSummoner());
		}

	}

	@Nested
	class SetLimitedLifetime
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(vex.hasLimitedLifetime());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenChangeInValue(boolean expectedValue)
		{
			vex.setLimitedLifetime(expectedValue);
			assertEquals(expectedValue, vex.hasLimitedLifetime());
		}

	}

	@Nested
	class SetLimitedLifetimeTicks
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(-1, vex.getLimitedLifetimeTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
		void givenChangeInValue(int expectedValue)
		{
			vex.setLimitedLifetimeTicks(expectedValue);
			assertEquals(expectedValue, vex.getLimitedLifetimeTicks());
		}

	}

}
