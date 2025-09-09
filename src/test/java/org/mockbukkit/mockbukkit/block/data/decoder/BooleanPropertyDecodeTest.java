package org.mockbukkit.mockbukkit.block.data.decoder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BooleanPropertyDecodeTest
{

	private final BooleanPropertyDecode decoder = BooleanPropertyDecode.INSTANCE;

	@ParameterizedTest
	@CsvSource(value = {
		"null, null",
		"'true', true",
		"'TRUE', true",
		"'false', false",
		"'FALSE', false",
	}, nullValues = "null")
	void givenPossibleStringValues(String input, Boolean expected)
	{
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

	@ParameterizedTest
	@CsvSource(value = {
		"null, null",
		"true, true",
		"false, false",
	}, nullValues = "null")
	void givenPossibleBooleanValues(Boolean input, Boolean expected)
	{
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

}
