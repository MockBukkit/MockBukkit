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
class CandleDataMockTest
{

	private CandleDataMock candle;

	@BeforeEach
	void setUp()
	{
		this.candle = new CandleDataMock(Material.BLACK_CANDLE);
	}

	@Nested
	class SetCandles
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(1, candle.getCandles());
		}

		@ParameterizedTest
		@ValueSource(ints = {1, 2, 3, 4})
		void givenPossibleValues(int candles)
		{
			candle.setCandles(candles);
			assertEquals(candles, candle.getCandles());
		}

		@ParameterizedTest
		@ValueSource(ints = {-1, 0, 5, 6})
		void givenInvalidValues(int candles)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> candle.setCandles(candles));
			assertEquals("Candles must be between 1 and 4", e.getMessage());
		}

	}

	@Nested
	class GetMaximumCandles
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(4, candle.getMaximumCandles());
		}

	}

	@Nested
	class GetMinimumCandles
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(1, candle.getMinimumCandles());
		}

	}

	@Nested
	class SetLit
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(candle.isLit());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenPossibleValues(boolean isLit)
		{
			candle.setLit(isLit);
			assertEquals(isLit, candle.isLit());
		}

	}

	@Nested
	class SetWaterlogged
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(candle.isWaterlogged());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenPossibleValues(boolean isWaterLogged)
		{
			candle.setWaterlogged(isWaterLogged);
			assertEquals(isWaterLogged, candle.isWaterlogged());
		}

	}

}
