package org.mockbukkit.mockbukkit.scoreboard;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.scoreboard.ObjectiveMock;
import org.mockbukkit.mockbukkit.scoreboard.ScoreboardMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class ObjectiveMockTest
{

	private ServerMock server;
	private ScoreboardMock scoreboard;
	private ObjectiveMock objective;

	@BeforeEach
	void setUp() throws Exception
	{
		server = MockBukkit.mock();
		scoreboard = new ScoreboardMock();
		objective = scoreboard.registerNewObjective("Objective", "dummy");
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_PropertiesSet()
	{
		assertSame(scoreboard, objective.getScoreboard());
		assertEquals("Objective", objective.getName());
		assertEquals("Objective", objective.getDisplayName());
		assertEquals("dummy", objective.getCriteria());
		assertEquals(Criteria.DUMMY, objective.getTrackedCriteria());
		assertNull(objective.getDisplaySlot());
	}

	@Test
	void setDisplayName_AnyString_DisplayNameSet()
	{
		objective.setDisplayName("New name");
		assertEquals("New name", objective.getDisplayName(), "Display name not changed");
		assertEquals("Objective", objective.getName(), "Internal name was changed");
	}

	@Test
	void unregister_ObjectiveWasRegistered_ObjectiveIsRemoved()
	{
		String name = objective.getName();
		assumeFalse(scoreboard.getObjective(name) == null, "Objective was not registered");
		objective.unregister();
		assertNull(scoreboard.getObjective(name), "Objective was not registered");
	}

	@SuppressWarnings("deprecation")
	@Test
	void getScore_Player_ReturnsNotNull()
	{
		PlayerMock player = server.addPlayer();
		assertNotNull(objective.getScore(player));
	}

	@SuppressWarnings("deprecation")
	@Test
	void getScore_SamePlayer_ReturnsSame()
	{
		PlayerMock player = server.addPlayer();
		Score score1 = objective.getScore(player);
		Score score2 = objective.getScore(player);
		assumeFalse(score1 == null);
		assertSame(score1, score2);
	}

	@Test
	void getScore_String_ReturnsNotNull()
	{
		Score score = objective.getScore("The score");
		assertNotNull(score);
	}

	@Test
	void getScore_SameString_ReturnsSame()
	{
		Score score1 = objective.getScore("The score");
		Score score2 = objective.getScore("The score");
		assertSame(score1, score2);
	}

	@Test
	void testSetDisplayNameComponent()
	{
		objective.displayName(Component.text("New name"));

		assertEquals(Component.text("New name"), objective.displayName());
	}

	@Test
	void testGetDisplayNameComponent()
	{
		objective.displayName(Component.text("Insert interesting text here"));

		assertEquals(Component.text("Insert interesting text here"), objective.displayName());
	}

}
