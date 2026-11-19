package org.mockbukkit.mockbukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ComponentUtilsTest
{

	@Nested
	class AsPlainString
	{

		@Test
		void givenNull()
		{
			assertNull(ComponentUtils.asPlainString(null));
		}

		@Test
		void givenSimpleTextComponent()
		{
			Component component = Component.text("Hello world!");
			String expected = "Hello world!";

			assertEquals(expected, ComponentUtils.asPlainString(component));
		}

		@Test
		void givenTextWithColorsComponent()
		{
			Component component = Component.text("Hello ").color(NamedTextColor.RED)
					.append(Component.text("world!").color(NamedTextColor.BLUE));
			String expected = "Hello world!";

			assertEquals(expected, ComponentUtils.asPlainString(component));
		}

	}

}
