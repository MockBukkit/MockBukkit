package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ColorElementFactoryTest
{
	@Test
	void givenNull()
	{
		JsonObject actual = ColorElementFactory.toJson(null);
		assertNull(actual);
	}

	@Test
	void givenValidColor()
	{
		JsonObject actual = ColorElementFactory.toJson(Color.AQUA);
		assertNotNull(actual);
		assertEquals(255, actual.get("alpha").getAsInt());
		assertEquals(0, actual.get("red").getAsInt());
		assertEquals(255, actual.get("green").getAsInt());
		assertEquals(255, actual.get("blue").getAsInt());
	}
}
