package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class BrewingStandDataMockTest
{

	private BrewingStandDataMock brewingStand;

	@BeforeEach
	void setUp()
	{
		this.brewingStand = new BrewingStandDataMock(Material.BREWING_STAND);
	}

	@Nested
	class HasBottle
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(brewingStand.hasBottle(0));
			assertFalse(brewingStand.hasBottle(1));
			assertFalse(brewingStand.hasBottle(2));
		}

		@ParameterizedTest
		@CsvSource({
			"false, false,	false",
			"true, 	false,	false",
			"false, true, 	false",
			"false, false, 	true",
			"true, 	false, 	true",
			"true, 	true, 	true",
			"false,	true, 	true",
		})
		void givenIndexCombination(boolean expected0, boolean expected1, boolean expected2)
		{
			brewingStand.setBottle(0, expected0);
			brewingStand.setBottle(1, expected1);
			brewingStand.setBottle(2, expected2);

			assertEquals(expected0, brewingStand.hasBottle(0));
			assertEquals(expected1, brewingStand.hasBottle(1));
			assertEquals(expected2, brewingStand.hasBottle(2));
		}

	}

	@Nested
	class GetBottle
	{

		@Test
		void givenDefault()
		{
			Set<Integer> indexes = brewingStand.getBottles();
			assertEquals(Collections.emptySet(), indexes);
		}

		@Test
		void givenIndexCombination()
		{
			brewingStand.setBottle(0, true);
			brewingStand.setBottle(1, false);
			brewingStand.setBottle(2, true);

			Set<Integer> indexes = brewingStand.getBottles();

			assertEquals(Set.of(0, 2), indexes);
		}

	}

	@Test
	void getMaximumBottles()
	{
		assertEquals(3, brewingStand.getMaximumBottles());
	}

}
