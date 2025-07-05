package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.TrialSpawner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class TrialSpawnerDataMockTest
{

	private TrialSpawnerDataMock spawner;

	@BeforeEach
	void setUp()
	{
		this.spawner = new TrialSpawnerDataMock(Material.TRIAL_SPAWNER);
	}

	@Nested
	class SetTrialSpawnerState
	{

		@Test
		void givenDefaultAxis()
		{
			assertEquals(TrialSpawner.State.INACTIVE, spawner.getTrialSpawnerState());
		}

		@ParameterizedTest
		@EnumSource(TrialSpawner.State.class)
		void givenStateChange(TrialSpawner.State shape)
		{
			spawner.setTrialSpawnerState(shape);
			assertEquals(shape, spawner.getTrialSpawnerState());
		}

	}

	@Nested
	class SetOminous
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(spawner.isOminous());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean ominous)
		{
			spawner.setOminous(ominous);
			assertEquals(ominous, spawner.isOminous());
		}

	}

}
