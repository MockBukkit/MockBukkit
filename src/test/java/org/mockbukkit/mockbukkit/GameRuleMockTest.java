package org.mockbukkit.mockbukkit;

import com.google.gson.JsonObject;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameRuleMockTest
{

	@Test
	void fromGivenValidPayload()
	{
		JsonObject json = new JsonObject();
		json.addProperty("key", "minecraft:advance_time");
		json.addProperty("translationKey", "gamerule.minecraft.advance_time");
		json.addProperty("type", "java.lang.Boolean");

		GameRule<?> actual = GameRuleMock.from(json);

		assertNotNull(actual);
		assertEquals(NamespacedKey.minecraft("advance_time"), actual.getKey());
		assertEquals("gamerule.minecraft.advance_time", actual.translationKey());
		assertEquals(Boolean.class, actual.getType());
	}

}
