package org.mockbukkit.mockbukkit.event;

import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class GameEventMockTest
{

	private NamespacedKey key;
	private GameEventMock gameEvent;

	@BeforeEach
	void setUp()
	{
		this.key = new NamespacedKey("mock_bukkit", "custom_game_event");
		this.gameEvent = new GameEventMock(key);
	}

	@Test
	void getKey()
	{
		assertEquals(key, gameEvent.getKey());
	}

	@Test
	void from()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> GameEventMock.from(invalid));
	}

}
