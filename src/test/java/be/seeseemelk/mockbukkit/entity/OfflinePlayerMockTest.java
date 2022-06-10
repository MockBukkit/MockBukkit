package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OfflinePlayerMockTest
{

	private ServerMock server;
	private UUID uuid;
	private OfflinePlayer player;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		uuid = UUID.randomUUID();
		player = new OfflinePlayerMock(uuid, "player");
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testOfflinePlayerSerialization()
	{
		Map<String, Object> serialized = player.serialize();
		assertEquals(uuid.toString(), serialized.get("UUID").toString());
	}

}
