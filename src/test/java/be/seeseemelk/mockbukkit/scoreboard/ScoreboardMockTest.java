package be.seeseemelk.mockbukkit.scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Set;

import org.bukkit.scoreboard.Objective;
import org.junit.Before;
import org.junit.Test;

public class ScoreboardMockTest
{
	private ScoreboardMock scoreboard;

	@Before
	public void setUp() throws Exception
	{
		scoreboard = new ScoreboardMock();
	}

	@Test
	public void registerObjective_DummyObjective_ObjectiveRegistered()
	{
		Objective objective = scoreboard.registerNewObjective("My objective", "dummy");
		assertNotNull(objective);
		assertSame(objective, scoreboard.getObjective("My objective"));
	}
	
	@Test
	public void getObjectivesByCriteria_TwoDifferentObjectives_ReturnsOne()
	{
		Objective objective = scoreboard.registerNewObjective("Correct", "dummy");
		scoreboard.registerNewObjective("Incorrect", "player_kills");
		
		Set<Objective> objectives = scoreboard.getObjectivesByCriteria("dummy");
		assertEquals(1, objectives.size());
		assertSame(objective, objectives.iterator().next());
	}

}
