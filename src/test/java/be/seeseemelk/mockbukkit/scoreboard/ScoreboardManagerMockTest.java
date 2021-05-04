package be.seeseemelk.mockbukkit.scoreboard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreboardManagerMockTest
{
	private ScoreboardManager manager;

	@BeforeEach
	public void setUp() throws Exception
	{
		manager = new ScoreboardManagerMock();
	}

	@Test
	void getMainScoreboard_NotNull()
	{
		assertNotNull(manager.getMainScoreboard());
	}

	@Test
	void getMainScoreboard_MultipleCalls_ReturnsSame()
	{
		Scoreboard scoreboardA = manager.getMainScoreboard();
		Scoreboard scoreboardB = manager.getMainScoreboard();
		assertSame(scoreboardA, scoreboardB);
	}

	@Test
	void getNewScoreboard_NotNull()
	{
		assertNotNull(manager.getNewScoreboard());
	}

	@Test
	void getNewScoreboard_MultipleCalls_ReturnsDifferentInstances()
	{
		Scoreboard scoreboardA = manager.getNewScoreboard();
		Scoreboard scoreboardB = manager.getNewScoreboard();
		assertNotSame(scoreboardA, scoreboardB);
	}

}
