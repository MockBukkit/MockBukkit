package be.seeseemelk.mockbukkit.scoreboard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

/**
 * Created for the AddstarMC Project. Created by Narimm on 24/12/2018.
 */
class TeamMockTest
{
	private ServerMock server;
	private TeamMock team;
	private Scoreboard board;
	private PlayerMock playerA;

	private PlayerMock playerB;

	@BeforeEach
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		playerA = server.addPlayer();
		playerB = server.addPlayer();
		ScoreboardManager managerMock = server.getScoreboardManager();
		board = managerMock.getNewScoreboard();
		team = new TeamMock("Test", board);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void getName()
	{
		assertEquals("Test", team.getName());
	}

	@Test
	void getPrefix()
	{
		assertNull(team.getPrefix());
		team.setPrefix("THIS");
		assertEquals("THIS", team.getPrefix());
	}

	@Test
	void getColor()
	{
		assertNull(team.getColor());
		team.setColor(ChatColor.AQUA);
		assertEquals(ChatColor.AQUA, team.getColor());
	}

	@Test
	void allowFriendlyFire()
	{
		assertFalse(team.allowFriendlyFire());
		team.setAllowFriendlyFire(true);
		assertTrue(team.allowFriendlyFire());
	}

	@Test
	void canSeeFriendlyInvisibles()
	{
		assertTrue(team.canSeeFriendlyInvisibles());
		team.setCanSeeFriendlyInvisibles(false);
		assertFalse(team.canSeeFriendlyInvisibles());
	}

	@SuppressWarnings("deprecation")
	@Test
	void getNameTagVisibility()
	{
		assertEquals(org.bukkit.scoreboard.NameTagVisibility.ALWAYS, team.getNameTagVisibility());
	}

	@SuppressWarnings("deprecation")
	@Test
	void getPlayers()
	{
		assertEquals(0, team.getPlayers().size());
		team.addEntry(playerA.getName());
		assertEquals(1, team.getPlayers().size());

	}

	@Test
	void getEntries()
	{
		assertEquals(0, team.getEntries().size());
		team.addEntry(playerA.getName());
		assertEquals(1, team.getEntries().size());
	}

	@SuppressWarnings("deprecation")
	@Test
	void getSize()
	{
		assertEquals(0, team.getSize());
		team.addPlayer(playerA);
		assertEquals(1, team.getSize());
	}

	@Test
	void getScoreboard()
	{
		assertEquals(board, team.getScoreboard());
	}

	@SuppressWarnings("deprecation")
	@Test
	void removePlayer()
	{
		assertEquals(0, team.getSize());
		team.addPlayer(playerA);
		assertEquals(1, team.getSize());
		team.removePlayer(playerA);
		assertEquals(0, team.getSize());

	}

	@Test
	void removeEntry()
	{
		assertEquals(0, team.getSize());
		team.addEntry(playerB.getName());
		assertEquals(1, team.getSize());
		team.removeEntry(playerB.getName());
		assertEquals(0, team.getSize());
	}

	@SuppressWarnings("deprecation")
	@Test
	void hasPlayer()
	{
		assertFalse(team.hasPlayer(playerB));
		team.addEntry(playerB.getName());
		assertFalse(team.hasPlayer(playerA));
		assertTrue(team.hasPlayer(playerB));

	}

	@Test
	void hasEntry()
	{
		assertFalse(team.hasEntry(playerB.getName()));
		team.addEntry(playerB.getName());
		assertFalse(team.hasEntry(playerA.getName()));
		assertTrue(team.hasEntry(playerB.getName()));
	}

	@Test
	void getOption()
	{
		Team.OptionStatus status = team.getOption(Team.Option.NAME_TAG_VISIBILITY);
		assertEquals(Team.OptionStatus.ALWAYS, status);
	}

	@Test
	void unregister_FirstUnregister_Works()
	{
		assertDoesNotThrow(team::unregister);
	}

	@Test
	void unregister_UnregisteredTwice_ThrowsException()
	{
		team.unregister();
		assertThrows(IllegalStateException.class, () -> team.unregister());
	}
}
