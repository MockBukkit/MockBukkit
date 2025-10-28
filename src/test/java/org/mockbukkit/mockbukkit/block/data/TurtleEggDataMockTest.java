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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class TurtleEggDataMockTest
{

	private TurtleEggDataMock egg;

	@BeforeEach
	void setUp()
	{
		this.egg = new TurtleEggDataMock(Material.TURTLE_EGG);
	}

	@Nested
	class SetEggs
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(1, egg.getEggs());
		}

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4 })
		void givenValidValues(int age)
		{
			egg.setEggs(age);
			assertEquals(age, egg.getEggs());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1, 0, 5, 6, 7 })
		void giveInvalidValues(int age)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> egg.setEggs(age));
			assertEquals("Eggs must be between 1 and 4", e.getMessage());
		}

	}

	@Nested
	class SetHatch
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, egg.getHatch());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2 })
		void givenValidValues(int age)
		{
			egg.setHatch(age);
			assertEquals(age, egg.getHatch());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1, 3, 4, 5 })
		void giveInvalidValues(int age)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> egg.setHatch(age));
			assertEquals("Hatch must be between 0 and 2", e.getMessage());
		}

	}

}
