package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class KeyedElementFactoryTest
{
	@Test
	void givenNull()
	{
		JsonElement actual = KeyedElementFactory.toJson(null);
		assertNull(actual);
	}

	@Test
	void givenValidKey()
	{
		JsonElement actual = KeyedElementFactory.toJson(NamespacedKey.minecraft("test"));
		assertNotNull(actual);
		assertEquals("minecraft:test", actual.getAsString());
	}
}
