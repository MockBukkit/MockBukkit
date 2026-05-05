package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class FoodComponentMockTest
{

	private final FoodComponentMock component = FoodComponentMock.builder().build();

	@Nested
	class Serialize
	{

		@ParameterizedTest
		@ValueSource(ints = {1, 2, 3, 4, 5})
		void givenNutritionSerialization(int nutrition)
		{
			component.setNutrition(nutrition);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(nutrition, actual.get("nutrition"));
		}

		@ParameterizedTest
		@ValueSource(floats = {1.0F, 1.5F, 3F, 4.12F, 5.7F})
		void givenSaturationSerialization(float saturation)
		{
			component.setSaturation(saturation);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(saturation, actual.get("saturation"));
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenSaturationSerialization(boolean canAlwaysEat)
		{
			component.setCanAlwaysEat(canAlwaysEat);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(canAlwaysEat, actual.get("can-always-eat"));
		}

	}

	@Nested
	class Deserialize
	{

		@ParameterizedTest
		@ValueSource(ints = {1, 2, 3, 4, 5})
		void givenNutritionDeserialization(int nutrition)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("nutrition", nutrition);
			input.put("saturation", 1);

			FoodComponentMock actual = FoodComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(nutrition, actual.getNutrition());
		}

		@ParameterizedTest
		@ValueSource(floats = {1.0F, 1.5F, 3F, 4.12F, 5.7F})
		void givenSaturationDeserialization(float saturation)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("nutrition", 1);
			input.put("saturation", saturation);

			FoodComponentMock actual = FoodComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(saturation, actual.getSaturation());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenCanAlwaysEatDeserialization(boolean canAlwaysEat)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("nutrition", 1);
			input.put("saturation", 1);
			input.put("can-always-eat", canAlwaysEat);

			FoodComponentMock actual = FoodComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(canAlwaysEat, actual.canAlwaysEat());
		}

		@Test
		void givenSerializationAndDeserialization()
		{
			component.setNutrition(123);
			component.setSaturation(3.21F);
			component.setCanAlwaysEat(true);

			FoodComponentMock actual = FoodComponentMock.deserialize(component.serialize());

			assertNotNull(actual);
			assertEquals(component.getNutrition(), actual.getNutrition());
			assertEquals(component.getSaturation(), actual.getSaturation());
			assertEquals(component.canAlwaysEat(), actual.canAlwaysEat());
		}

	}

}
