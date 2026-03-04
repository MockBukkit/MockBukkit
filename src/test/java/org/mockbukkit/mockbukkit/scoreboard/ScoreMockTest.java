package org.mockbukkit.mockbukkit.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockBukkitExtension.class)
class ScoreMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private ScoreboardMock scoreboard;
	private ObjectiveMock objective;
	private ScoreMock score;

	@BeforeEach
	void setUp()
	{
		scoreboard = server.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective("Objective", "dummy");
		score = objective.getScore("Entry");
	}

	@Test
	void getEntry_ReturnsEntry()
	{
		assertEquals("Entry", score.getEntry());
	}

	@Test
	void getObjective_ReturnsParentObjective()
	{
		assertSame(objective, score.getObjective());
	}

	@Test
	void getScore_ObjectiveRegisteredButNoScoreSet_ReturnsZero()
	{
		assertEquals(0, score.getScore());
	}

	@Test
	void getScore_ObjectiveUnregistered_ThrowsError()
	{
		objective.unregister();
		assertThrows(IllegalStateException.class, () ->
				score.getScore());
	}

	@Test
	void getScore_ObjectiveRegisteredAndScoreSet_ReturnsNumber()
	{
		score.setScore(5);
		assertEquals(5, score.getScore());
	}

	@Test
	void getPlayer_PlayerSet_ReturnsPlayer()
	{
		PlayerMock player = server.addPlayer();
		score.setPlayer(player);
		assertSame(player, score.getPlayer());
	}

	@Test
	void isSet_NotSet_ReturnsFalse()
	{
		assertFalse(score.isScoreSet());
	}

	@Test
	void isSet_Set_ReturnsTrue()
	{
		score.setScore(5);
		assertTrue(score.isScoreSet());
	}

	@Test
	void getScoreboard_ReturnsScoreboard()
	{
		assertSame(scoreboard, score.getScoreboard());
	}

	@Test
	void testResetScore()
	{
		score.setScore(5);
		score.resetScore();
		assertEquals(0, score.getScore());
		assertFalse(score.isScoreSet());
	}

}
