package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class AnaloguePowerableBlockDataMockTest
{

	private AnaloguePowerableBlockDataMock analogue;

	@BeforeEach
	void setUp()
	{
		this.analogue = new AnaloguePowerableBlockDataMock(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
	}

	@Nested
	class SetPower
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, analogue.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void givenLevelChange(int level)
		{
			analogue.setPower(level);
			assertEquals(level, analogue.getPower());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 16, 17 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> analogue.setPower(level));
			assertEquals("Power must be between 0 and 15", e.getMessage());
		}

	}

	@Nested
	class GetMaximumLevel
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(15, analogue.getMaximumPower());
		}

	}

	@Test
	void validateClone()
	{

		@NotNull AnaloguePowerableBlockDataMock cloned = analogue.clone();

		assertEquals(analogue, cloned);
		assertEquals(analogue.getPower(), cloned.getPower());

		analogue.setPower(3);

		assertNotEquals(analogue, cloned);
		assertEquals(3, analogue.getPower());
		assertEquals(0, cloned.getPower());
	}

}
