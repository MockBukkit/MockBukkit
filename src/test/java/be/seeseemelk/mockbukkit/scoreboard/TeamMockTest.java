package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	void setUp() throws Exception
	{
		server = MockBukkit.mock();
		playerA = server.addPlayer();
		playerB = server.addPlayer();
		ScoreboardManager managerMock = server.getScoreboardManager();
		board = managerMock.getNewScoreboard();
		team = (TeamMock) board.registerNewTeam("Test");
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void getName()
	{
		assertEquals("Test", team.getName());
	}

	@Test
	void getDisplayName()
	{
		assertEquals(team.getName(), team.getDisplayName());
		assertEquals(Component.text(team.getName()), team.displayName());
		team.setDisplayName("DisplayName");
		assertEquals("DisplayName", team.getDisplayName());
		assertEquals(Component.text("DisplayName"), team.displayName());
	}

	@Test
	void getPrefix()
	{
		assertEquals("", team.getPrefix());
		assertEquals(Component.empty(), team.prefix());
		team.setPrefix("THIS");
		assertEquals("THIS", team.getPrefix());
		assertEquals(Component.text("THIS"), team.prefix());
	}

	@Test
	void getSuffix()
	{
		assertEquals("", team.getSuffix());
		assertEquals(Component.empty(), team.suffix());
		team.setSuffix("THAT");
		assertEquals("THAT", team.getSuffix());
		assertEquals(Component.text("THAT"), team.suffix());
	}

	@Test
	void nullComponents()
	{
		team.setDisplayName("something");
		team.displayName(null);
		assertEquals("", team.getDisplayName());
		assertEquals(Component.empty(), team.displayName());
	}

	@Test
	void getColor_IsFormat()
	{
		assertEquals(ChatColor.RESET, team.getColor());
		assertFalse(team.hasColor());
		assertThrows(IllegalStateException.class, () -> team.color(),
				"An exception should be thrown when the ChatColor is a format.");
	}

	@Test
	void getColor_IsColor()
	{
		team.setColor(ChatColor.AQUA);
		assertTrue(team.hasColor());
		assertEquals(ChatColor.AQUA, team.getColor());
		assertEquals(NamedTextColor.AQUA, team.color());

		team.color(NamedTextColor.YELLOW);
		assertEquals(ChatColor.YELLOW, team.getColor());
		assertEquals(NamedTextColor.YELLOW, team.color());
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
		assertSame(board, team.getScoreboard());
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

	@Test
	void removeEntity()
	{
		assertEquals(0, team.getSize());
		team.addEntity(playerA);
		assertEquals(1, team.getSize());
		assertTrue(team.removeEntity(playerA));
		assertEquals(0, team.getSize());
		assertFalse(team.removeEntity(playerA));
	}

	@Test
	void removeEntities()
	{
		Set<Entity> players = Set.of(playerA, playerB);
		Set<String> entries = players.stream().map(Entity::getName).collect(Collectors.toSet());
		team.addEntities(players);
		assertEquals(entries, team.getEntries());
		assertTrue(team.removeEntities(players));
		assertEquals(Set.of(), team.getEntries());
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
		assertFalse(team.hasEntity(playerB));
		assertFalse(team.hasEntry(playerB.getName()));
		team.addEntry(playerB.getName());
		assertFalse(team.hasEntity(playerA));
		assertFalse(team.hasEntry(playerA.getName()));
		assertTrue(team.hasEntity(playerB));
		assertTrue(team.hasEntry(playerB.getName()));
	}

	@Test
	void setOption()
	{
		Team.OptionStatus status = team.getOption(Team.Option.NAME_TAG_VISIBILITY);
		assertEquals(Team.OptionStatus.ALWAYS, status);
		team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
		status = team.getOption(Team.Option.NAME_TAG_VISIBILITY);
		assertEquals(Team.OptionStatus.FOR_OWN_TEAM, status);
	}

	@Test
	void unregister_FirstUnregister_Works()
	{
		assertSame(team, board.getTeam("Test"), "Team was registered");
		assertDoesNotThrow(team::unregister);
		assertNull(board.getTeam("Test"), "Team is no longer registered");
	}

	@Test
	void unregister_UnregisteredTwice_ThrowsException()
	{
		team.unregister();
		assertThrows(IllegalStateException.class, () -> team.unregister());
	}

}
