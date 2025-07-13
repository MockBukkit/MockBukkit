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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LightDataMockTest
{

	private LightDataMock light;

	@BeforeEach
	void setUp()
	{
		this.light = new LightDataMock(Material.LIGHT);
	}

	@Nested
	class SetLevel
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(15, light.getLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void givenLevelChange(int level)
		{
			light.setLevel(level);
			assertEquals(level, light.getLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 16, 17 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> light.setLevel(level));
			assertEquals("Level should be between 0 and 15", e.getMessage());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(light.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			light.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, light.isWaterlogged());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull LightDataMock cloned = light.clone();

		assertEquals(light, cloned);
		assertEquals(light.isWaterlogged(), cloned.isWaterlogged());

		light.setWaterlogged(true);

		assertNotEquals(light, cloned);
		assertTrue(light.isWaterlogged());
		assertFalse(cloned.isWaterlogged());
	}

}
