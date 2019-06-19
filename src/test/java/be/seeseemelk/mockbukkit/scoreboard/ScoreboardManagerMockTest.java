package be.seeseemelk.mockbukkit.scoreboard;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.junit.Before;
import org.junit.Test;

public class ScoreboardManagerMockTest
{
	private ScoreboardManager manager;

	@Before
	public void setUp() throws Exception
	{
		manager = new ScoreboardManagerMock();
	}

	@Test
	public void getMainScoreboard_NotNull()
	{
		assertNotNull(manager.getMainScoreboard());
	}
	
	@Test
	public void getMainScoreboard_MultipleCalls_ReturnsSame()
	{
		Scoreboard scoreboardA = manager.getMainScoreboard();
		Scoreboard scoreboardB = manager.getMainScoreboard();
		assertSame(scoreboardA, scoreboardB);
	}
	
	@Test
	public void getNewScoreboard_NotNull()
	{
		assertNotNull(manager.getNewScoreboard());
	}
	
	@Test
	public void getNewScoreboard_MultipleCalls_ReturnsDifferentInstances()
	{
		Scoreboard scoreboardA = manager.getNewScoreboard();
		Scoreboard scoreboardB = manager.getNewScoreboard();
		assertNotSame(scoreboardA, scoreboardB);
	}

}
