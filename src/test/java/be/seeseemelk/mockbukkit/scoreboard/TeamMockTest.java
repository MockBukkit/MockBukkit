package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created for the AddstarMC Project. Created by Narimm on 24/12/2018.
 */
public class TeamMockTest {
    private ServerMock server;
    private Team team;
    private Scoreboard board;
    private PlayerMock playerA;
    
    private PlayerMock playerB;
    
    
    
    @Before
    public void setUp() throws Exception {
        server = MockBukkit.mock();
        playerA = server.addPlayer();
        playerB = server.addPlayer();
        ScoreboardManager managerMock = server.getScoreboardManager();
        board = managerMock.getNewScoreboard();
        team = new TeamMock("Test",board);
    }
    
    @After
    public void tearDown() throws Exception
    {
        MockBukkit.unload();
    }
    @Test
    public void getName() {
        assertEquals("Test",team.getName());
    }
    
    @Test
    public void getPrefix() {
        assertNull(team.getPrefix());
        team.setPrefix("THIS");
        assertEquals("THIS",team.getPrefix());
    }
    
    @Test
    public void getColor() {
        assertNull(team.getColor());
        team.setColor(ChatColor.AQUA);
        assertEquals(ChatColor.AQUA,team.getColor());
    }
    
    @Test
    public void allowFriendlyFire() {
        assertFalse(team.allowFriendlyFire());
        team.setAllowFriendlyFire(true);
        assertTrue(team.allowFriendlyFire());
    }
    
    @Test
    public void canSeeFriendlyInvisibles() {
        assertTrue(team.canSeeFriendlyInvisibles());
        team.setCanSeeFriendlyInvisibles(false);
        assertFalse(team.canSeeFriendlyInvisibles());
    }
    
    @Test
    public void getNameTagVisibility() {
        assertEquals(NameTagVisibility.ALWAYS,team.getNameTagVisibility());
    }
    
    @Test
    public void getPlayers() {
        assertEquals(0,team.getPlayers().size());
        team.addEntry(playerA.getName());
        assertEquals(1,team.getPlayers().size());
        
    }
    
    @Test
    public void getEntries() {
        assertEquals(0,team.getEntries().size());
        team.addEntry(playerA.getName());
        assertEquals(1,team.getEntries().size());
    }
    
    @Test
    public void getSize() {
        assertEquals(0,team.getSize());
        team.addPlayer(playerA);
        assertEquals(1,team.getSize());
    }
    
    @Test
    public void getScoreboard() {
        assertEquals(board,team.getScoreboard());
    }

    
    @Test
    public void removePlayer() {
        assertEquals(0,team.getSize());
        team.addPlayer(playerA);
        assertEquals(1,team.getSize());
        team.removePlayer(playerA);
        assertEquals(0,team.getSize());
    
    }
    
    @Test
    public void removeEntry() {
        assertEquals(0,team.getSize());
        team.addEntry(playerB.getName());
        assertEquals(1,team.getSize());
        team.removeEntry(playerB.getName());
        assertEquals(0,team.getSize());
    }
    
    @Test
    public void unregister() {
    }
    
    @Test
    public void hasPlayer() {
        assertFalse(team.hasPlayer(playerB));
        team.addEntry(playerB.getName());
        assertFalse(team.hasPlayer(playerA));
        assertTrue(team.hasPlayer(playerB));
    
    }
    
    @Test
    public void hasEntry() {
        assertFalse(team.hasEntry(playerB.getName()));
        team.addEntry(playerB.getName());
        assertFalse(team.hasEntry(playerA.getName()));
        assertTrue(team.hasEntry(playerB.getName()));
    }
    
    @Test
    public void getOption() {
        Team.OptionStatus status = team.getOption(Team.Option.NAME_TAG_VISIBILITY);
        assertEquals(Team.OptionStatus.ALWAYS,status);
    }
    
    @Test(expected = IllegalStateException.class)
    public void unRegister() {
        team.unregister();
        
    }
}