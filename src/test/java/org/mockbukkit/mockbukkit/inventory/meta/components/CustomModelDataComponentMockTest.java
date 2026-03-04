package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.Color;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomModelDataComponentMockTest
{

	private final CustomModelDataComponentMock component = CustomModelDataComponentMock.builder()
			.build();

	@Nested
	class Serialize
	{

		@Test
		void givenFloats()
		{
			var expected = List.of(1.0F, 3.0F, 2.0F);
			component.setFloats(expected);

			var actual = component.serialize();

			assertNotNull(actual);
			assertEquals(expected, actual.get("floats"));
		}

		@Test
		void givenFlags()
		{
			var expected = List.of(true, true, false);
			component.setFlags(expected);

			var actual = component.serialize();

			assertNotNull(actual);
			assertEquals(expected, actual.get("flags"));
		}

		@Test
		void givenStrings()
		{
			var expected = List.of("Hello", "MockBukkit", "World");
			component.setStrings(expected);

			var actual = component.serialize();

			assertNotNull(actual);
			assertEquals(expected, actual.get("strings"));
		}

		@Test
		void givenColors()
		{
			var expected = List.of(Color.RED, Color.BLUE);
			component.setColors(expected);

			var actual = component.serialize();

			assertNotNull(actual);
			assertEquals(expected.stream().map(Color::serialize).toList(), actual.get("colors"));
		}

	}

	@Nested
	class Deserialize
	{

		@Test
		void givenSerializationAndDeserialization()
		{
			component.setFloats(List.of(1.0F, 3.0F, 2.0F));
			component.setFlags(List.of(true, true, false));
			component.setStrings(List.of("Hello", "MockBukkit", "World"));
			component.setColors(List.of(Color.RED, Color.BLUE));

			var actual = CustomModelDataComponentMock.deserialize(component.serialize());

			assertNotNull(actual);
			assertEquals(component.getFloats(), actual.getFloats());
			assertEquals(component.getFlags(), actual.getFlags());
			assertEquals(component.getStrings(), actual.getStrings());
			assertEquals(component.getColors(), actual.getColors());
			assertEquals(component, actual);
		}

	}

}
