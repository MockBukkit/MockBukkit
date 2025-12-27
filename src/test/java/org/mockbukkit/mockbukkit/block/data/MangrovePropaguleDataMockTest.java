package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
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
class MangrovePropaguleDataMockTest
{
	private MangrovePropaguleDataMock mangrove;

	@BeforeEach
	void setUp()
	{
		this.mangrove = new MangrovePropaguleDataMock(Material.MANGROVE_PROPAGULE);
	}

	@Nested
	class SetAge
	{

		@Test
		void getAge()
		{
			assertEquals(0, mangrove.getAge());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4})
		void setAge(int age)
		{
			mangrove.setAge(age);
			assertEquals(age, mangrove.getAge());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1, 5, 6, 7, 8 })
		void givenInvalid(int age)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> mangrove.setAge(age));
			assertEquals("The age must be between 0 and 4", e.getMessage());
		}

	}

	@Nested
	class SetHanging
	{
		@Test
		void givenDefaultValue()
		{
			assertFalse(mangrove.isHanging());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isHanging)
		{
			mangrove.setHanging(isHanging);
			assertEquals(isHanging, mangrove.isHanging());
		}
	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(mangrove.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isWaterLogged)
		{
			mangrove.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, mangrove.isWaterlogged());
		}

	}

	@Nested
	class SetStage
	{

		@Test
		void getStage()
		{
			assertEquals(0, mangrove.getStage());
		}

		@Test
		void getMaximumStage()
		{
			assertEquals(1, mangrove.getMaximumStage());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1 })
		void givenChangeWithValidValues(int age)
		{
			mangrove.setStage(age);
			assertEquals(age, mangrove.getStage());
		}

		@ParameterizedTest
		@ValueSource(ints = { -2, -1, 2, 3 })
		void givenChangeWithInvalidValues(int age)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> mangrove.setStage(age));
			assertEquals("The stage must be between 0 and 1", e.getMessage());
		}

	}

}
