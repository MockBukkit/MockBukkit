package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class DaylightDetectorDataMockTest
{

	private final DaylightDetectorDataMock daylightDetector = new DaylightDetectorDataMock(Material.DAYLIGHT_DETECTOR);

	@Nested
	class SetInverted
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(daylightDetector.isInverted());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			daylightDetector.setInverted(isWaterLogged);
			assertEquals(isWaterLogged, daylightDetector.isInverted());
		}

	}

	@Nested
	class SetPower
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, daylightDetector.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void givenLevelChange(int level)
		{
			daylightDetector.setPower(level);
			assertEquals(level, daylightDetector.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 16, 17 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> daylightDetector.setPower(level));
			assertEquals("Power must be between 0 and 15", e.getMessage());
		}

	}

}
