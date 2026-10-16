package io.papermc.paper.datacomponent.item;

import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SignTextMockTest
{

	private final SignText.Builder builder = new SignTextMock.BuilderMock();

	@Nested
	@DisplayName("Constructor & Immutability Tests")
	class ConstructorTests
	{

		@Test
		@DisplayName("Should correctly map values from constructor")
		void shouldMapConstructorValues()
		{
			List<Component> lines = List.of(Component.text("A"), Component.text("B"), Component.empty(), Component.empty());
			SignText signText = new SignTextMock(true, lines, DyeColor.RED);

			assertTrue(signText.hasGlowingText());
			assertEquals(DyeColor.RED, signText.color());
			assertEquals(lines, signText.lines());
		}

		@Test
		@DisplayName("Should ensure lines list is structurally immutable")
		void shouldBeImmutable()
		{
			List<Component> mutableLines = new java.util.ArrayList<>(List.of(Component.text("Test")));
			SignText signText = new SignTextMock(false, mutableLines, DyeColor.BLACK);

			assertThrows(UnsupportedOperationException.class, () -> signText.lines().add(Component.text("Fail")));
		}

	}

	@Nested
	@DisplayName("Builder Default State Tests")
	class DefaultStateTests
	{

		@Test
		@DisplayName("Should build with expected default values")
		void shouldHaveCorrectDefaults()
		{
			SignText signText = builder.build();

			assertFalse(signText.hasGlowingText());
			assertEquals(DyeColor.BLACK, signText.color());
			assertEquals(4, signText.lines().size());
			for (Component line : signText.lines())
			{
				assertEquals(Component.empty(), line);
			}
		}

	}

	@Nested
	@DisplayName("Builder Line Manipulation Tests")
	class LineManipulationTests
	{

		@Test
		@DisplayName("Should properly pad empty spaces when passing fewer than 4 lines")
		void shouldPadEmptyLines()
		{
			List<Component> shortLines = List.of(Component.text("Line 1"), Component.text("Line 2"));

			// Note: If your addAll bug is still present, this test will fail because lines.size() will be 8
			SignText signText = builder.lines(shortLines).build();

			assertEquals(4, signText.lines().size());
			assertEquals(Component.text("Line 1"), signText.lines().get(0));
			assertEquals(Component.text("Line 2"), signText.lines().get(1));
			assertEquals(Component.empty(), signText.lines().get(2));
			assertEquals(Component.empty(), signText.lines().get(3));
		}

		@Test
		@DisplayName("Should throw IllegalArgumentException when passing more than 4 lines")
		void shouldRejectOverFourLines()
		{
			List<Component> tooManyLines = List.of(
					Component.text("1"), Component.text("2"),
					Component.text("3"), Component.text("4"),
					Component.text("5")
			);

			assertThrows(IllegalArgumentException.class, () -> builder.lines(tooManyLines));
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3 })
		@DisplayName("Should set specific line index independently")
		void shouldSetSpecificLine(int index)
		{
			Component expected = Component.text("Custom Line");
			SignText signText = builder.line(index, expected).build();

			assertEquals(expected, signText.lines().get(index));
		}

		@Test
		@DisplayName("Should throw IndexOutOfBoundsException for invalid line indices")
		void shouldRejectInvalidIndex()
		{
			assertThrows(IndexOutOfBoundsException.class, () -> builder.line(-1, Component.text("Bad")));
			assertThrows(IndexOutOfBoundsException.class, () -> builder.line(4, Component.text("Bad")));
		}

	}

	@Nested
	@DisplayName("Builder Fluid State Changes")
	class FluidStateTests
	{

		@Test
		@DisplayName("Should modify color and glowing state fluently")
		void shouldModifyProperties()
		{
			SignText signText = builder
					.color(DyeColor.BLUE)
					.hasGlowingText(true)
					.build();

			assertEquals(DyeColor.BLUE, signText.color());
			assertTrue(signText.hasGlowingText());
		}

		@Test
		@DisplayName("Should convert back to a builder maintaining state")
		void shouldConvertToBuilder()
		{
			SignText original = builder
					.color(DyeColor.GREEN)
					.hasGlowingText(true)
					.line(0, Component.text("Hello"))
					.build();

			SignText copied = original.toBuilder().build();

			assertEquals(original.color(), copied.color());
			assertEquals(original.hasGlowingText(), copied.hasGlowingText());
			assertEquals(original.lines(), copied.lines());
		}

	}

}
