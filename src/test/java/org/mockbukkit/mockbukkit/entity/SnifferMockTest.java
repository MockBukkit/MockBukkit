package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sniffer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SnifferMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private SnifferMock sniffer;

	@BeforeEach
	void setUp()
	{
		sniffer = new SnifferMock(server, UUID.randomUUID());
	}

	@Nested
	class AddExploredLocations
	{

		@Test
		void defaultExploredLocationsIsEmpty()
		{
			@NotNull Collection<Location> actual = sniffer.getExploredLocations();
			assertNotNull(actual);
			assertTrue(actual.isEmpty());
		}

		@Test
		void givenNullLocation()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sniffer.addExploredLocation(null));
			assertEquals("location cannot be null", e.getMessage());
		}

		@Test
		void givenDifferentWorlds()
		{
			World worldA = server.addSimpleWorld("worldA");
			sniffer.setLocation(new Location(worldA, 0, 0, 0));

			World worldB = server.addSimpleWorld("worldB");
			sniffer.addExploredLocation(new Location(worldB, 0.5, 0.5, 0.5));
			assertTrue(sniffer.getExploredLocations().isEmpty());
		}

		@Test
		void givenSameWorld()
		{
			World worldA = server.addSimpleWorld("worldA");
			Location spawnLocation = new Location(worldA, 0, 0, 0);
			sniffer.setLocation(spawnLocation);

			Location location = new Location(worldA, 0.5, -1.5, 0.5);
			sniffer.addExploredLocation(location);

			assertEquals(List.of(location.toBlockLocation()), sniffer.getExploredLocations());
		}

	}

	@Nested
	class RemoveExploredLocation
	{

		@Test
		void givenNullLocation()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sniffer.removeExploredLocation(null));
			assertEquals("location cannot be null", e.getMessage());
		}

		@Test
		void givenNullWorldShouldDefaultForCurrentWorld()
		{
			World worldA = server.addSimpleWorld("worldA");
			Location spawnLocation = new Location(worldA, 0, 0, 0);
			sniffer.setLocation(spawnLocation);

			Location locationA = new Location(worldA, 0.5, -1.5, 0.5);
			sniffer.addExploredLocation(locationA);

			Location locationB = new Location(null, 0.5, -1.5, 0.5);
			sniffer.removeExploredLocation(locationB);
			assertTrue(sniffer.getExploredLocations().isEmpty());
		}

		@Test
		void givenSameWorld()
		{
			World worldA = server.addSimpleWorld("worldA");
			Location spawnLocation = new Location(worldA, 0, 0, 0);
			sniffer.setLocation(spawnLocation);

			Location location = new Location(worldA, 0.5, -1.5, 0.5);
			sniffer.addExploredLocation(location);
			sniffer.removeExploredLocation(location);
			assertTrue(sniffer.getExploredLocations().isEmpty());
		}

	}

	@Nested
	class SetState
	{

		@Test
		void givenDefaultState()
		{
			assertEquals(Sniffer.State.IDLING, sniffer.getState());
		}

		@ParameterizedTest
		@EnumSource(Sniffer.State.class)
		void givenPossibleStates(Sniffer.State state)
		{
			assertDoesNotThrow(() -> sniffer.setState(state));
			assertEquals(state, sniffer.getState());
		}

	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SNIFFER, sniffer.getType());
	}

}
