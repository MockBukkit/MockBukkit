package org.mockbukkit.mockbukkit.map;

import org.bukkit.NamespacedKey;
import org.bukkit.map.MapCursor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapCursorTypeMockTest
{

	@Test
	void successfullyLoaded()
	{
		MapCursor.Type mapCursorType = MapCursor.Type.PLAYER_OFF_MAP;
		assertEquals("PLAYER_OFF_MAP", mapCursorType.name());
		assertEquals(6, mapCursorType.getValue());
		assertEquals(NamespacedKey.fromString("minecraft:player_off_map"), mapCursorType.getKey());
	}

}
