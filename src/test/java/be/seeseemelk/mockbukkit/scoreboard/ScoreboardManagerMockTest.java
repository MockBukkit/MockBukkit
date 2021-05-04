package be.seeseemelk.mockbukkit.scoreboard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.junit.Before;
import org.junit.jupiter.api.Test;

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
