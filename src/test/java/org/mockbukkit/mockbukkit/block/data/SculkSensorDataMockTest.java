package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.SculkSensor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SculkSensorDataMockTest
{

	private final SculkSensorDataMock sculkSensor = new SculkSensorDataMock(Material.SCULK_SENSOR);

	@Nested
	class SetSculkSensorPhase
	{
		@Test
		void givenDefaultValue()
		{
			assertEquals(SculkSensor.Phase.INACTIVE, sculkSensor.getSculkSensorPhase());
		}

		@ParameterizedTest
		@EnumSource(SculkSensor.Phase.class)
		void givenPossibleValues(SculkSensor.Phase phase)
		{
			sculkSensor.setSculkSensorPhase(phase);
			assertEquals(phase, sculkSensor.getSculkSensorPhase());
		}

	}

	@Nested
	class SetPower
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, sculkSensor.getPower());
		}

		@Test
		void givenMaxValue()
		{
			assertEquals(15, sculkSensor.getMaximumPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void givenLevelChange(int level)
		{
			sculkSensor.setPower(level);
			assertEquals(level, sculkSensor.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 16, 17 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sculkSensor.setPower(level));
			assertEquals("Power must be between 0 and 15", e.getMessage());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(sculkSensor.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			sculkSensor.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, sculkSensor.isWaterlogged());
		}

	}

}
