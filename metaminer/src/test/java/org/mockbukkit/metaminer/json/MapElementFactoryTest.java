package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapElementFactoryTest
{
	@ParameterizedTest
	@NullAndEmptySource
	void givenNullOrEmptyValue(Map<?, ?> values)
	{
		JsonObject actual = MapElementFactory.toJson(values);
		assertNull(actual);
	}

	@Test
	void givenValidKey()
	{
		Map<?, ?> input = Map.of(
			"material",  Material.AIR,
			"amount", 10
		);

		JsonObject actual = MapElementFactory.toJson(input);
		assertNotNull(actual);
		assertTrue(actual.isJsonObject());
		assertEquals("minecraft:air", actual.get("material").getAsString());
		assertEquals(10, actual.get("amount").getAsInt());
	}
}
