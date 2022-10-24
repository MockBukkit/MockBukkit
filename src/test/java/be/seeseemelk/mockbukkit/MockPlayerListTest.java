package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.OfflinePlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class MockPlayerListTest
{

	private ServerMock server;
	private MockPlayerList playerList;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		playerList = server.getPlayerList();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
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

		playerList.addPlayer(player);

		assertNotEquals(0, playerList.getFirstPlayed(player.getUniqueId()));
	}

	@Test
	void addPlayer_SetsLastLogin()
	{
		PlayerMock player = server.addPlayer();

		playerList.addPlayer(player);

		assertNotEquals(0, playerList.getLastLogin(player.getUniqueId()));
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

		playerList.disconnectPlayer(player);

		assertNotEquals(0, playerList.getLastSeen(player.getUniqueId()));
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

		playerList.setFirstPlayed(uuid, 10L);

		assertEquals(10, playerList.getFirstPlayed(uuid));
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

		playerList.setLastSeen(uuid, 10L);

		assertEquals(10, playerList.getLastSeen(uuid));
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

		playerList.setLastLogin(uuid, 10L);

		assertEquals(10, playerList.getLastLogin(uuid));
	}

}
