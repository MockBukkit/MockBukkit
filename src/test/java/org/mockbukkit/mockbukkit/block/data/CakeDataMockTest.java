package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class CakeDataMockTest
{

	private final CakeDataMock cake = new CakeDataMock(Material.CAKE);

	@Nested
	class SetBites
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, cake.getBites());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
		void givenPossibleLineWidthValues(int expected)
		{
			cake.setBites(expected);

			assertEquals(expected, cake.getBites());
		}

		@Test
		void givenDefaultMaximumValue()
		{
			assertEquals(6, cake.getMaximumBites());
		}

	}

}
