package be.seeseemelk.mockbukkit.scoreboard;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.SimpleEntityMock;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardMockTest
{

	private ServerMock server;
	private ScoreboardMock scoreboard;

	@BeforeEach
	void setUp() throws Exception
	{
		this.server = MockBukkit.mock();
		this.scoreboard = new ScoreboardMock();
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void registerObjective_DummyObjective_ObjectiveRegistered()
	{
		Objective objective = scoreboard.registerNewObjective("My objective", "dummy");
		assertNotNull(objective);
		assertSame(objective, scoreboard.getObjective("My objective"));
	}

	@Test
	void getObjectivesByCriteria_TwoDifferentObjectives_ReturnsOne()
	{
		Objective objective = scoreboard.registerNewObjective("Correct", "dummy");
		scoreboard.registerNewObjective("Incorrect", "player_kills");

		Set<Objective> objectives = scoreboard.getObjectivesByCriteria("dummy");
		assertEquals(1, objectives.size());
		assertSame(objective, objectives.iterator().next());
	}

	@Test
	void getObjectivesByCriteria_TwoDifferentObjectives_ReturnsOne_CriteriaObject()
	{
		Objective objective = scoreboard.registerNewObjective("Correct", Criteria.DUMMY, text("Objective name"));
		scoreboard.registerNewObjective("Incorrect", Criteria.PLAYER_KILL_COUNT, text("Another name"));

		Set<Objective> objectives = scoreboard.getObjectivesByCriteria(Criteria.DUMMY);
		assertEquals(Set.of(objective), objectives);
	}

	@Test
	void getObjectives_TwoObjectives_ReturnsTwo()
	{
		Objective objective1 = scoreboard.registerNewObjective("One", "dummy");
		Objective objective2 = scoreboard.registerNewObjective("Two", "dummy");

		Set<Objective> objectives = scoreboard.getObjectives();

		assertEquals(2, objectives.size(), "getObjectives() did not return 2 objectives");
		assertTrue(objectives.contains(objective1));
		assertTrue(objectives.contains(objective2));
	}

	@Test
	void getObjective_EmptyDisplaySlot_ReturnsNull()
	{
		scoreboard.registerNewObjective("Objective", "dummy");
		assertNull(scoreboard.getObjective(DisplaySlot.BELOW_NAME));
		assertNull(scoreboard.getObjective(DisplaySlot.PLAYER_LIST));
		assertNull(scoreboard.getObjective(DisplaySlot.SIDEBAR));
	}

	@Test
	void getObjective_ObjectiveInDisplaySlot_ReturnsObjective()
	{
		ObjectiveMock objective = scoreboard.registerNewObjective("Objective", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		assertNull(scoreboard.getObjective(DisplaySlot.BELOW_NAME));
		assertNull(scoreboard.getObjective(DisplaySlot.PLAYER_LIST));
		assertSame(objective, scoreboard.getObjective(DisplaySlot.SIDEBAR));
	}

	@Test
	void clearSlot_NotUnregisterObjectives()
	{
		ObjectiveMock objective = scoreboard.registerNewObjective("Objective", "dummy");
		assertTrue(objective.isRegistered());
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
		assertTrue(objective.isRegistered());
	}

	@Test
	void registerNewObjective_CheckForDuplicates()
	{
		assertDoesNotThrow(() -> scoreboard.registerNewObjective("Objective", "dummy"));
		assertThrows(IllegalArgumentException.class, () -> scoreboard.registerNewObjective("Objective", "dummy"));
	}

	@Test
	void registerNewTeam_CheckForDuplicates()
	{
		assertDoesNotThrow(() -> scoreboard.registerNewTeam("blue"));
		assertThrows(IllegalArgumentException.class, () -> scoreboard.registerNewTeam("blue"));
	}

	@Test
	void getTeams()
	{
		Set<Team> excepted = Set.of(this.scoreboard.registerNewTeam("red"), this.scoreboard.registerNewTeam("green"),
				this.scoreboard.registerNewTeam("yellow"));
		assertEquals(excepted, this.scoreboard.getTeams());
	}

	@Test
	void getEntries()
	{
		EntityMock entity = new SimpleEntityMock(this.server);
		Team team = this.scoreboard.registerNewTeam("red");
		team.addEntry("player");
		team.addEntity(entity);
		this.scoreboard.registerNewTeam("cyan").addEntry("player");
		assertEquals(Set.of("player", entity.getScoreboardEntry()), this.scoreboard.getEntries());
	}

	@Test
	void getScoresFor()
	{
		EntityMock entity = new SimpleEntityMock(this.server);
		Objective objectiveA = this.scoreboard.registerNewObjective("test", Criteria.DUMMY, empty());
		Score scoreA = objectiveA.getScore(entity.getScoreboardEntry());
		scoreA.setScore(78);
		Objective objectiveB = this.scoreboard.registerNewObjective("test2", Criteria.DEATH_COUNT, empty());
		Score scoreB = objectiveB.getScoreFor(entity);

		Set<Score> excepted = Set.of(scoreA, scoreB);
		assertEquals(excepted, this.scoreboard.getScoresFor(entity));
		assertEquals(excepted, this.scoreboard.getScores(entity.getScoreboardEntry()));
	}

	@Test
	void resetScores()
	{
		EntityMock entity = new SimpleEntityMock(this.server);
		EntityMock entity2 = new SimpleEntityMock(this.server);
		for (int i = 4; i < 8; i++)
		{
			Objective objective = this.scoreboard.registerNewObjective("test" + i, Criteria.DEATH_COUNT, empty());
			Score score = objective.getScoreFor(entity);
			score.setScore(i);
			Score score2 = objective.getScoreFor(entity2);
			score2.setScore(i);
		}
		this.scoreboard.resetScoresFor(entity);
		for (Score score : this.scoreboard.getScoresFor(entity))
		{
			assertEquals(0, score.getScore());
		}
		for (Score score : this.scoreboard.getScoresFor(entity2))
		{
			assertNotEquals(0, score.getScore());
		}
	}

	@Test
	void getEntityTeam()
	{
		EntityMock entity = new SimpleEntityMock(this.server);
		Team team = this.scoreboard.registerNewTeam("green");
		team.addEntity(entity);
		this.scoreboard.registerNewTeam("cyan").addEntity(entity);
		assertSame(team, this.scoreboard.getEntryTeam(entity.getScoreboardEntry()));
		assertSame(team, this.scoreboard.getEntityTeam(entity));
	}

}
