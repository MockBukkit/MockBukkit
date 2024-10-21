package org.mockbukkit.mockbukkit;

import org.mockbukkit.mockbukkit.PlayerListMock;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.OfflinePlayerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockBukkitExtension.class)
class PlayerListMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private PlayerListMock playerList;

	@BeforeEach
	void setUp()
	{
		playerList = server.getPlayerList();
	}

	@Test
	void addPlayer_AddsToLists()
	{
		PlayerMock player = server.addPlayer();

		playerList.addPlayer(player);

		assertTrue(playerList.getOnlinePlayers().contains(player));
		assertTrue(Arrays.asList(playerList.getOfflinePlayers()).contains(player));
	}

	@Test
	void addPlayer_SetsFirstPlayed()
	{
		PlayerMock player = server.addPlayer();
		assertFalse(playerList.hasPlayedBefore(player.getUniqueId()));

		playerList.addPlayer(player);

		assertNotEquals(0, playerList.getFirstPlayed(player.getUniqueId()));
		assertTrue(playerList.hasPlayedBefore(player.getUniqueId()));
	}

	@Test
	void addPlayer_SetsLastLogin()
	{
		PlayerMock player = server.addPlayer();
		assertFalse(playerList.hasPlayedBefore(player.getUniqueId()));

		playerList.addPlayer(player);

		assertNotEquals(0, playerList.getLastLogin(player.getUniqueId()));
		assertTrue(playerList.hasPlayedBefore(player.getUniqueId()));
	}

	@Test
	void disconnect_RemovesFromOnline()
	{
		PlayerMock player = server.addPlayer();

		playerList.disconnectPlayer(player);

		assertFalse(playerList.getOnlinePlayers().contains(player));
	}

	@Test
	void disconnect_KeptInOffline()
	{
		PlayerMock player = server.addPlayer();

		playerList.disconnectPlayer(player);

		assertNotNull(playerList.getOfflinePlayer(player.getUniqueId()));
	}

	@Test
	void disconnect_SetsLastSeen()
	{
		PlayerMock player = server.addPlayer();
		assertFalse(playerList.hasPlayedBefore(player.getUniqueId()));

		playerList.disconnectPlayer(player);

		assertNotEquals(0, playerList.getLastSeen(player.getUniqueId()));
		assertTrue(playerList.hasPlayedBefore(player.getUniqueId()));
	}

	@Test
	void addOfflinePlayer()
	{
		PlayerMock player = server.addPlayer();

		playerList.addOfflinePlayer(player);

		assertNotNull(playerList.getOfflinePlayer(player.getUniqueId()));
	}

	@Test
	void getOperators()
	{
		PlayerMock player1 = server.addPlayer();
		PlayerMock player2 = server.addPlayer();
		OfflinePlayerMock offlinePlayer = new OfflinePlayerMock("offlinePlayer");
		player1.setOp(true);
		offlinePlayer.setOp(true);
		playerList.addOfflinePlayer(offlinePlayer);

		Set<OfflinePlayer> ops = playerList.getOperators();

		assertTrue(ops.contains(player1));
		assertTrue(ops.contains(offlinePlayer));
		assertFalse(ops.contains(player2));
	}

	@Test
	void getOnlinePlayers()
	{
		PlayerMock player = server.addPlayer();
		OfflinePlayerMock offlinePlayer = new OfflinePlayerMock("offlinePlayer");
		playerList.addOfflinePlayer(offlinePlayer);

		Collection<PlayerMock> players = playerList.getOnlinePlayers();

		assertEquals(1, players.size());
		assertTrue(players.contains(player));
	}

	@Test
	void getOfflinePlayers()
	{
		PlayerMock player = server.addPlayer();
		OfflinePlayerMock offlinePlayer = new OfflinePlayerMock("offlinePlayer");
		playerList.addOfflinePlayer(offlinePlayer);

		Collection<OfflinePlayer> players = Arrays.asList(playerList.getOfflinePlayers());

		assertTrue(players.contains(player));
		assertTrue(players.contains(offlinePlayer));
	}

	@Test
	void isSomeoneOnline_NoOneOnline_False()
	{
		assertFalse(playerList.isSomeoneOnline());
	}

	@Test
	void isSomeoneOnline_OneOnline_True()
	{
		server.addPlayer();

		assertTrue(playerList.isSomeoneOnline());
	}

	@Test
	void matchPlayer_PartialMatch()
	{
		PlayerMock player = server.addPlayer("player");

		assertTrue(playerList.matchPlayer("pla").contains(player));
	}

	@Test
	void matchPlayer_NoMatch()
	{
		server.addPlayer("player");

		assertTrue(playerList.matchPlayer("hehe").isEmpty());
	}

	@Test
	void getPlayerExact_ExactMatch()
	{
		PlayerMock player = server.addPlayer("player");

		assertEquals(player, playerList.getPlayerExact("player"));
	}

	@Test
	void getPlayerExact_PartialMatch_Null()
	{
		server.addPlayer("player");

		assertNull(playerList.getPlayerExact("pla"));
	}

	@Test
	void setFirstPlayed()
	{
		UUID uuid = UUID.randomUUID();
		assertFalse(playerList.hasPlayedBefore(uuid));

		playerList.setFirstPlayed(uuid, 10L);

		assertEquals(10, playerList.getFirstPlayed(uuid));
		assertTrue(playerList.hasPlayedBefore(uuid));
	}

	@Test
	void setFirstPlayed_NegativeNumber_ThrowsException()
	{
		UUID uuid = UUID.randomUUID();

		assertThrows(IllegalArgumentException.class, () -> playerList.setFirstPlayed(uuid, -1));
	}

	@Test
	void setLastSeen()
	{
		UUID uuid = UUID.randomUUID();
		assertFalse(playerList.hasPlayedBefore(uuid));

		playerList.setLastSeen(uuid, 10L);

		assertEquals(10, playerList.getLastSeen(uuid));
		assertTrue(playerList.hasPlayedBefore(uuid));
	}

	@Test
	void setLastSeen_NegativeNumber_ThrowsException()
	{
		UUID uuid = UUID.randomUUID();

		assertThrows(IllegalArgumentException.class, () -> playerList.setLastSeen(uuid, -1));
	}

	@Test
	void setLastSeen_PlayerOnline()
	{
		PlayerMock player = server.addPlayer();
		UUID uuid = player.getUniqueId();

		playerList.setLastSeen(uuid, 10L);

		assertNotEquals(10L, playerList.getLastSeen(uuid));
	}

	@Test
	void setLastLogin()
	{
		UUID uuid = UUID.randomUUID();
		assertFalse(playerList.hasPlayedBefore(uuid));

		playerList.setLastLogin(uuid, 10L);

		assertEquals(10, playerList.getLastLogin(uuid));
		assertTrue(playerList.hasPlayedBefore(uuid));
	}

	@Test
	void hasPlayedBefore_NeverOnline_False()
	{
		assertFalse(playerList.hasPlayedBefore(UUID.randomUUID()));
	}

	@Test
	void hasPlayedBefore_FirstJoin_False()
	{
		PlayerMock player = server.addPlayer();
		assertFalse(playerList.hasPlayedBefore(player.getUniqueId()));
	}

	@Test
	void hasPlayedBefore_SecondJoin_True()
	{
		PlayerMock player = server.addPlayer();
		player.disconnect();
		player.reconnect();

		assertTrue(playerList.hasPlayedBefore(player.getUniqueId()));
	}

	@Test
	void hasPlayedBefore_SecondJoin_Offline_True()
	{
		PlayerMock player = server.addPlayer();
		player.disconnect();
		player.reconnect();
		player.disconnect();

		assertTrue(playerList.hasPlayedBefore(player.getUniqueId()));
	}

}
