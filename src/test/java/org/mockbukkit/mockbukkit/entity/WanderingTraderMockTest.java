package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class WanderingTraderMockTest
{

	@MockBukkitInject
	private WanderingTraderMock wanderingTrader;

	@Nested
	class SetDespawnDelay
	{

		@Test
		void givenDefaultValue_ShouldReturnZero()
		{
			assertEquals(0, wanderingTrader.getDespawnDelay());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
		void givenPossibleValues_ShouldReturnCorrectValue(int delay)
		{
			wanderingTrader.setDespawnDelay(delay);
			assertEquals(delay, wanderingTrader.getDespawnDelay());
		}

	}

	@Nested
	class SetCanDrinkPotion
	{

		@Test
		void givenDefaultValue_ShouldReturnTrue()
		{
			assertTrue(wanderingTrader.canDrinkPotion());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues_ShouldReturnCorrectValue(boolean canDrink)
		{
			wanderingTrader.setCanDrinkPotion(canDrink);
			assertEquals(canDrink, wanderingTrader.canDrinkPotion());
		}

	}

	@Nested
	class SetCanDrinkMilk
	{

		@Test
		void givenDefaultValue_ShouldReturnTrue()
		{
			assertTrue(wanderingTrader.canDrinkMilk());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues_ShouldReturnCorrectValue(boolean canDrink)
		{
			wanderingTrader.setCanDrinkMilk(canDrink);
			assertEquals(canDrink, wanderingTrader.canDrinkMilk());
		}

	}

	@Nested
	class SetWanderingTowards
	{

		@Test
		void givenDefaultValue_ShouldReturnNull()
		{
			assertNull(wanderingTrader.getWanderingTowards());
		}

		@Test
		void givenPossibleValue_ShouldReturnCorrectValue()
		{
			Location location = new Location(null, 0.5, 2.3, 5.7);
			wanderingTrader.setWanderingTowards(location);

			Location expectedLocation = new Location(null, 0, 2, 5);
			assertEquals(expectedLocation, wanderingTrader.getWanderingTowards());

			wanderingTrader.setWanderingTowards(null);
			assertNull(wanderingTrader.getWanderingTowards());
		}

	}

}
