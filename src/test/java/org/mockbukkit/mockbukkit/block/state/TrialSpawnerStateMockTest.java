package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class TrialSpawnerStateMockTest
{

	private final TrialSpawnerStateMock state = new TrialSpawnerStateMock(Material.TRIAL_SPAWNER);
	@MockBukkitInject
	private ServerMock server;

	@Nested
	class GetCooldownEnd
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, state.getCooldownEnd());
		}

		@ParameterizedTest
		@ValueSource(longs = {0L, 1L, 1000L, 20000L})
		void givenValueChange(long ticks)
		{
			state.setCooldownEnd(ticks);
			assertEquals(ticks, state.getCooldownEnd());
		}

	}

	@Nested
	class GetNextSpawnAttempt
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, state.getNextSpawnAttempt());
		}

		@ParameterizedTest
		@ValueSource(longs = {0L, 1L, 1000L, 20000L})
		void givenValueChange(long ticks)
		{
			state.setNextSpawnAttempt(ticks);
			assertEquals(ticks, state.getNextSpawnAttempt());
		}

	}

	@Nested
	class GetRequiredPlayerRange
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(16, state.getRequiredPlayerRange());
		}

		@ParameterizedTest
		@ValueSource(ints = {1, 2, 3, 4, 5, 20})
		void givenValueChange(int range)
		{
			state.setRequiredPlayerRange(range);
			assertEquals(range, state.getRequiredPlayerRange());
		}

	}

	@Nested
	class GetTrackedPlayers
	{

		@Test
		void givenDefaultValue()
		{
			assertTrue(state.getTrackedPlayers().isEmpty());
		}

		@Test
		void givenTrackedPlayer()
		{
			Player player = server.addPlayer();

			state.startTrackingPlayer(player);
			Collection<Player> trackedPlayersBefore = state.getTrackedPlayers();
			assertEquals(1, trackedPlayersBefore.size());
			assertTrue(trackedPlayersBefore.contains(player));

			state.stopTrackingPlayer(player);
			Collection<Player> trackedPlayersAfter = state.getTrackedPlayers();
			assertEquals(0, trackedPlayersAfter.size());
		}

	}

	@Nested
	class GetTrackedEntities
	{

		@Test
		void givenDefaultValue()
		{
			assertTrue(state.getTrackedEntities().isEmpty());
		}

		@Test
		void givenTrackedEntity()
		{
			World world = server.addSimpleWorld("test");
			Entity cow = world.spawnEntity(new Location(world, 0, 64, 0), EntityType.COW);

			state.startTrackingEntity(cow);
			Collection<Entity> trackedEntitiesBefore = state.getTrackedEntities();
			assertEquals(1, trackedEntitiesBefore.size());
			assertTrue(trackedEntitiesBefore.contains(cow));

			state.stopTrackingEntity(cow);
			Collection<Entity> trackedEntitiesAfter = state.getTrackedEntities();
			assertEquals(0, trackedEntitiesAfter.size());
		}

	}

	@Nested
	class IsOminous
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(state.isOminous());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenValueChange(boolean isOminous)
		{
			state.setOminous(isOminous);
			assertEquals(isOminous, state.isOminous());
		}

	}

}
