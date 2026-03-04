package org.mockbukkit.mockbukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NbtParserTest
{

	@Nested
	class ParseBoolean
	{

		@Test
		void givenNull()
		{
			Boolean actual = NbtParser.parseBoolean(null);
			assertNull(actual);
		}

		@ParameterizedTest
		@NullSource
		@ValueSource(booleans = {true, false})
		void givenBoolean(Boolean expected)
		{
			Boolean actual = NbtParser.parseBoolean(expected);
			assertEquals(expected, actual);
		}

		@ParameterizedTest
		@CsvSource({
			"-2, false",
			"-1, false",
			"0, false",
			"1, true",
			"2, false",
			"3, false",
		})
		void givenInteger(Integer input, Boolean expected)
		{
			Boolean actual = NbtParser.parseBoolean(input);
			assertEquals(expected, actual);
		}

		@ParameterizedTest
		@CsvSource({
			"false, false",
			"False, false",
			"FALSE, false",
			"true, true",
			"True, true",
			"TRUE, true",
			"non-existing, false",
		})
		void givenString(String input, Boolean expected)
		{
			Boolean actual = NbtParser.parseBoolean(input);
			assertEquals(expected, actual);
		}

		@Test
		void givenUnknown()
		{
			A input = new A();
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> NbtParser.parseBoolean(input));
			assertEquals("Cannot parse double: org.mockbukkit.mockbukkit.util.NbtParserTest$A", e.getMessage());
		}

	}

	@Nested
	class ParseDouble
	{

		@Test
		void givenNull()
		{
			Double actual = NbtParser.parseDouble(null);
			assertNull(actual);
		}

		@ParameterizedTest
		@ValueSource(doubles = {
			-1.123D, 0.0D, 1.123D
		})
		void givenDouble(Double expected)
		{
			Double actual = NbtParser.parseDouble(expected);
			assertEquals(expected, actual);
		}

		@ParameterizedTest
		@ValueSource(doubles = {
				-1.123D, 0.0D, 1.123D
		})
		void givenString(Double expected)
		{
			Double actual = NbtParser.parseDouble(String.valueOf(expected));
			assertEquals(expected, actual);
		}

		@Test
		void givenUnknown()
		{
			A input = new A();
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> NbtParser.parseDouble(input));
			assertEquals("Cannot parse double: org.mockbukkit.mockbukkit.util.NbtParserTest$A", e.getMessage());
		}

	}

	@Nested
	class ParseInteger
	{

		@Test
		void givenNull()
		{
			Integer actual = NbtParser.parseInteger(null);
			assertNull(actual);
		}

		@ParameterizedTest
		@ValueSource(ints = {
			-1, 0, 1
		})
		void givenDouble(Integer expected)
		{
			Integer actual = NbtParser.parseInteger(expected);
			assertEquals(expected, actual);
		}

		@ParameterizedTest
		@ValueSource(ints = {
			-1, 0, 1
		})
		void givenString(Integer expected)
		{
			Integer actual = NbtParser.parseInteger(String.valueOf(expected));
			assertEquals(expected, actual);
		}

		@Test
		void givenUnknown()
		{
			A input = new A();
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> NbtParser.parseInteger(input));
			assertEquals("Cannot parse integer: org.mockbukkit.mockbukkit.util.NbtParserTest$A", e.getMessage());
		}

	}

	@Nested
	class ParseByte
	{

		@Test
		void givenNull()
		{
			Byte actual = NbtParser.parseByte(null);
			assertNull(actual);
		}

		@ParameterizedTest
		@ValueSource(bytes = {
			-1, 0, 1
		})
		void givenDouble(Byte expected)
		{
			Byte actual = NbtParser.parseByte(expected);
			assertEquals(expected, actual);
		}

		@ParameterizedTest
		@ValueSource(bytes = {
			-1, 0, 1
		})
		void givenString(Byte expected)
		{
			Byte actual = NbtParser.parseByte(String.valueOf(expected));
			assertEquals(expected, actual);
		}

		@Test
		void givenUnknown()
		{
			A input = new A();
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> NbtParser.parseByte(input));
			assertEquals("Cannot parse byte: org.mockbukkit.mockbukkit.util.NbtParserTest$A", e.getMessage());
		}

	}

	@Nested
	class ParseString
	{

		@Test
		void givenNull()
		{
			String actual = NbtParser.parseString(null);
			assertNull(actual);
		}

		@ParameterizedTest
		@EmptySource
		@ValueSource(strings = {
			"Hello world!",
			"MockBukkit"
		})
		void givenString(String expected)
		{
			String actual = NbtParser.parseString(expected);
			assertEquals(expected, actual);
		}

		@Test
		void givenUnknown()
		{
			A input = new A();
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> NbtParser.parseString(input));
			assertEquals("Cannot parse string: org.mockbukkit.mockbukkit.util.NbtParserTest$A", e.getMessage());
		}

	}

	@Nested
	class ParseComponent
	{

		@Test
		void givenNull()
		{
			Component actual = NbtParser.parseComponent(null);
			assertNull(actual);
		}

		@Test
		void givenSimpleText()
		{
			Component expected = Component.text("Hello world!");
			Component actual = NbtParser.parseComponent("\"Hello world!\"");
			assertEquals(expected, actual);
		}

		@Test
		void givenColoredText()
		{
			Component expected = Component.text("Hello world!").color(NamedTextColor.RED);
			Component actual = NbtParser.parseComponent("{\"color\":\"red\",\"text\":\"Hello world!\"}");
			assertEquals(expected, actual);
		}

	}

	@Nested
	class ParseNamespacedKey
	{

		@Test
		void givenNull()
		{
			NamespacedKey actual = NbtParser.parseNamespacedKey(null);
			assertNull(actual);
		}

		@Test
		void givenPrefixedKey()
		{
			NamespacedKey expected = NamespacedKey.fromString("minecraft:test");
			NamespacedKey actual = NbtParser.parseNamespacedKey("minecraft:test");
			assertEquals(expected, actual);
		}

		@Test
		void givenNonPrefixedKey()
		{
			NamespacedKey expected = NamespacedKey.fromString("minecraft:test");
			NamespacedKey actual = NbtParser.parseNamespacedKey("test");
			assertEquals(expected, actual);
		}

	}

	/**
	 * This test class is used to validate that an error is thrown
	 * when an unknown type is provided.
	 */
	record A() {}

}
