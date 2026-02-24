package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SuppressWarnings("UnstableApiUsage")
class UseCooldownComponentMockTest
{

	private final UseCooldownComponent component = UseCooldownComponentMock.builder().build();

	@Nested
	class GetCooldownSeconds
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, component.getCooldownSeconds());
		}

		@ParameterizedTest
		@ValueSource(floats = {0.0f, 1.0f, 2.5f, 3.75f})
		void givenCustomCooldownValue(final float value)
		{
			component.setCooldownSeconds(value);
			assertEquals(value, component.getCooldownSeconds());
		}

	}

	@Nested
	class GetCooldownGroup
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(component.getCooldownGroup());
		}

		@ParameterizedTest
		@ValueSource(strings = {
			"minecraft:test",
			"custom:test"
		})
		void givenCustomCooldownGroup(final String key)
		{
			NamespacedKey namespacedKey = NamespacedKey.fromString(key);
			component.setCooldownGroup(namespacedKey);
			assertEquals(namespacedKey, component.getCooldownGroup());
		}

	}

	@Nested
	class Serialize
	{

		@Test
		void givenDefaultValue()
		{
			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(1, actual.size());
			assertEquals(0.0f, actual.get("seconds"));
		}

		@Test
		void givenCustomValue()
		{
			component.setCooldownSeconds(10);
			component.setCooldownGroup(NamespacedKey.minecraft( "test" ));

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(2, actual.size());
			assertEquals(10.0f, actual.get("seconds"));
			assertEquals("minecraft:test", actual.get("cooldown-group"));
		}

	}

	@Nested
	class Deserialize
	{

		@Test
		void givenComponentWithCooldownGroup()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("seconds", 10.0f);
			input.put("cooldown-group", "minecraft:test");

			UseCooldownComponent actual = UseCooldownComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(10.0f, actual.getCooldownSeconds());
			assertEquals(NamespacedKey.minecraft("test"), actual.getCooldownGroup());
		}

		@Test
		void givenComponentWithoutCooldownGroup()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("seconds", 10.0f);

			UseCooldownComponent actual = UseCooldownComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(10.0f, actual.getCooldownSeconds());
			assertNull(actual.getCooldownGroup());
		}

		@Test
		void givenSerializationAndDeserialization()
		{
			component.setCooldownSeconds(10);
			component.setCooldownGroup(NamespacedKey.minecraft( "test" ));

			UseCooldownComponent actual = UseCooldownComponentMock.deserialize(component.serialize());

			assertNotNull(actual);
			assertEquals(10.0f, actual.getCooldownSeconds());
			assertEquals(NamespacedKey.minecraft("test"), actual.getCooldownGroup());
		}

	}

}
