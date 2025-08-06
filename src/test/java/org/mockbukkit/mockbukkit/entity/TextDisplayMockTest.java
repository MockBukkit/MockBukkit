package org.mockbukkit.mockbukkit.entity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class TextDisplayMockTest
{

	@MockBukkitInject
	private TextDisplayMock textDisplay;

	@Nested
	class SetText
	{

		@ParameterizedTest
		@ValueSource(strings = {
				"Hello!",
				"&4Hello!",
				"This is\nan example\nwith newline",
				"&aH&be&cl&dl&eo&f!",
		})
		void givenValidStringValues(String expected)
		{
			textDisplay.setText(expected);

			String actual = textDisplay.getText();
			assertEquals(expected, actual);
		}

		@ParameterizedTest
		@NullAndEmptySource
		void givenNullOrEmptyString(String input)
		{
			textDisplay.setText(input);

			String actual = textDisplay.getText();
			assertEquals("", actual);
		}

	}

	@Nested
	class Text
	{

		@ParameterizedTest
		@ValueSource(strings = {
				"Hello!",
				"&4Hello!",
				"This is\nan example\nwith newline",
				"&aH&be&cl&dl&eo&f!",
		})
		void givenValidValues(String expected)
		{
			TextComponent expectedComponent = LegacyComponentSerializer.legacySection().deserializeOrNull(expected);

			textDisplay.text(expectedComponent);

			Component actual = textDisplay.text();
			assertNotNull(actual);
			assertEquals(expectedComponent, actual);
		}

		@ParameterizedTest
		@NullAndEmptySource
		void givenNullOrEmpty(String input)
		{
			TextComponent textComponent = LegacyComponentSerializer.legacySection().deserializeOrNull(input);
			textDisplay.text(textComponent);

			Component actual = textDisplay.text();
			assertNotNull(actual);
			assertEquals(Component.empty(), actual);
		}

	}

	@Nested
	class SetLineWidth
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(200, textDisplay.getLineWidth());
		}

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4, 5, 10, 25, 50, 75, 100, 200 })
		void givenPossibleLineWidthValues(int expected)
		{
			textDisplay.setLineWidth(expected);

			assertEquals(expected, textDisplay.getLineWidth());
		}

	}

	@Nested
	class SetBackgroundColor
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(textDisplay.getBackgroundColor());
		}

		@ParameterizedTest
		@NullSource
		@MethodSource("getColors")
		void givenPossibleColors(Color expected)
		{
			textDisplay.setBackgroundColor(expected);

			assertEquals(expected, textDisplay.getBackgroundColor());
		}

		private static Stream<Color> getColors()
		{
			return Stream.of(
					Color.AQUA,
					Color.BLACK,
					Color.BLUE,
					Color.FUCHSIA,
					Color.GRAY,
					Color.GREEN,
					Color.LIME,
					Color.MAROON,
					Color.NAVY,
					Color.OLIVE,
					Color.ORANGE,
					Color.PURPLE,
					Color.PURPLE,
					Color.RED,
					Color.SILVER,
					Color.TEAL,
					Color.WHITE,
					Color.YELLOW
			);
		}

	}

	@Nested
	class SetTextOpacity
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(-1, textDisplay.getTextOpacity());
		}

		@ParameterizedTest
		@ValueSource(bytes = { -1, 0, 1, 2, 3, 4, 5, 10, 25, 50, 75, 100 })
		void givenPossibleLineWidthValues(byte expected)
		{
			textDisplay.setTextOpacity(expected);

			assertEquals(expected, textDisplay.getTextOpacity());
		}

	}

	@Nested
	class SetShadowed
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(textDisplay.isShadowed());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expected)
		{
			textDisplay.setShadowed(expected);

			assertEquals(expected, textDisplay.isShadowed());
		}

	}

	@Nested
	class SetSeeThrough
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(textDisplay.isSeeThrough());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expected)
		{
			textDisplay.setSeeThrough(expected);

			assertEquals(expected, textDisplay.isSeeThrough());
		}

	}

	@Nested
	class SetDefaultBackground
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(textDisplay.isDefaultBackground());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expected)
		{
			textDisplay.setDefaultBackground(expected);

			assertEquals(expected, textDisplay.isDefaultBackground());
		}

	}

	@Nested
	class SetAlignment
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(TextDisplay.TextAlignment.CENTER, textDisplay.getAlignment());
		}

		@ParameterizedTest
		@EnumSource(TextDisplay.TextAlignment.class)
		void givenPossibleColors(TextDisplay.TextAlignment expected)
		{
			textDisplay.setAlignment(expected);

			assertEquals(expected, textDisplay.getAlignment());
		}

		@Test
		void giveNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> textDisplay.setAlignment(null));
			assertEquals("Alignment cannot be null", e.getMessage());
		}

	}

}
