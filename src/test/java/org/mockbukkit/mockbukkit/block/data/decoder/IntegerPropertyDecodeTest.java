package org.mockbukkit.mockbukkit.block.data.decoder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerPropertyDecodeTest
{
	private final IntegerPropertyDecode decoder = IntegerPropertyDecode.INSTANCE;

	@ParameterizedTest
	@CsvSource(value = {
		"null, null",
		"'1', 1",
		"'002', 2",
		"'1234567', 1234567",
	}, nullValues = "null")
	void givenPossibleStringValues(String input, Integer expected)
	{
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

	@ParameterizedTest
	@CsvSource(value = {
		"null, null",
		"123, 123",
		"4567, 4567",
	}, nullValues = "null")
	void givenPossibleIntegerValues(Integer input, Integer expected)
	{
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

	@ParameterizedTest
	@CsvSource(value = {
		"null, null",
		"5.65, 5",
		"6.0, 6",
	}, nullValues = "null")
	void givenPossibleDoubleValues(Double input, Integer expected)
	{
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

}
