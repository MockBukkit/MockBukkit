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
class HatchableDataMockTest
{
	private HatchableDataMock egg;

	@BeforeEach
	void setUp()
	{
		this.egg = new HatchableDataMock(Material.SNIFFER_EGG);
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
