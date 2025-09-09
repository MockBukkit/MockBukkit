package org.mockbukkit.mockbukkit.block.data.decoder;

import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EnumPropertyDecodeTest
{
	private final EnumPropertyDecode<BlockFace> decoder = EnumPropertyDecode.of(BlockFace.class);

	@Test
	void givenNullEnumValue()
	{
		var actual = decoder.decode(null);
		assertNull(actual);
	}

	@ParameterizedTest
	@EnumSource(BlockFace.class)
	void givenPossibleStringEnumValues(BlockFace expected)
	{
		String input = expected.name();
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

	@ParameterizedTest
	@EnumSource(BlockFace.class)
	void givenPossibleStringEnumValuesAsLowerCase(BlockFace expected)
	{
		String input = expected.name().toLowerCase(Locale.ROOT);
		var actual = decoder.decode(input);
		assertEquals(expected, actual);
	}

	@Test
	void givenNonExistingStringEnum()
	{
		String input = "non-existing";
		var actual = decoder.decode(input);
		assertNull(actual);
	}

	@ParameterizedTest
	@NullSource
	@EnumSource(BlockFace.class)
	void givenPossibleEnumValues(BlockFace expected)
	{
		var actual = decoder.decode(expected);
		assertEquals(expected, actual);
	}
}
