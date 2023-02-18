package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfflinePlayerMockTest
{

	private ServerMock server;
	private UUID uuid;
	private OfflinePlayerMock player;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		uuid = UUID.randomUUID();
		player = new OfflinePlayerMock(uuid, "player");
		server.getPlayerList().addOfflinePlayer(player);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void isOnline_NotOnline_False()
	{
		assertFalse(player.isOnline());
	}

	@Test
	void isOnline_IsOnline_True()
	{
		player.join(server);

		assertTrue(player.isOnline());
	}

	@Test
	void getName()
	{
		assertEquals("player", player.getName());
	}

	@Test
	void getUniqueId()
	{
		assertEquals(uuid, player.getUniqueId());
	}

	@Test
	void serialize_CorrectValues()
	{
		Map<String, Object> serialized = player.serialize();
		assertEquals(1, serialized.size());
		assertEquals(uuid.toString(), serialized.get("UUID").toString());
	}

	@Test
	void serialize_IsImmutable()
	{
		Map<String, Object> serialized = player.serialize();
		assertThrows(UnsupportedOperationException.class, () -> serialized.put("key", "value"));
	}

	@Test
	void isBanned()
	{
		assertFalse(player.isBanned());
		player.banPlayer(null);
		assertTrue(player.isBanned());
	}

	@Test
	void setWhitelisted()
	{
		player.setWhitelisted(true);
		assertTrue(player.isWhitelisted());
	}

	@Test
	void getPlayer_NotOnline_Null()
	{
		assertNull(player.getPlayer());
	}

	@Test
	void getPlayer_IsOnline_NotNull()
	{
		player.join(server);

		assertNotNull(player.getPlayer());
	}

	@Test
	void isOpDefault()
	{
		assertFalse(player.isOp());
		assertFalse(MockBukkit.getMock().getOperators().contains(player));
	}

	@Test
	void setOp()
	{
		player.setOp(true);
		assertTrue(player.isOp());
		assertTrue(MockBukkit.getMock().getOperators().contains(player));
	}

	@Test
	void setOpFalse()
	{
		player.setOp(true);
		player.setOp(false);
		assertFalse(player.isOp());
		assertFalse(MockBukkit.getMock().getOperators().contains(player));
	}

}
