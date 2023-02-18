package org.mockbukkit.mockbukkit.scoreboard;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.scoreboard.ScoreboardManagerMock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class ScoreboardManagerMockTest
{

	private ScoreboardManager manager;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		manager = new ScoreboardManagerMock();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
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
