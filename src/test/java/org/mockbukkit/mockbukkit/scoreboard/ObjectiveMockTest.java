package org.mockbukkit.mockbukkit.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ObjectiveMockTest
{

	@MockBukkitInject
	private ServerMock server;
	@MockBukkitInject
	private ScoreboardMock scoreboard;
	private ObjectiveMock objective;

	@BeforeEach
	void setUp()
	{
		objective = scoreboard.registerNewObjective("Objective", "dummy");
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
		assertTrue(objective.isModifiable());
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
		assertNotNull(scoreboard.getObjective(name), "Objective was not registered");
		objective.unregister();
		assertNull(scoreboard.getObjective(name), "Objective was not registered");
		assertFalse(objective.isRegistered());
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
		assertNotNull(score1);
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

	@Test
	void testCustomDisplayName()
	{
		objective = scoreboard.registerNewObjective("Objective2", "dummy", (Component) null, RenderType.HEARTS);
		assertEquals("", objective.getDisplayName());
		objective.displayName(Component.text("display"));
		assertEquals("display", objective.getDisplayName());
		objective.displayName(null);
		assertEquals("", objective.getDisplayName());
	}

	@Test
	void testLongDisplayName()
	{
		String longName = "A".repeat(200);
		assertThrows(IllegalArgumentException.class, () -> objective.setDisplayName(longName));
	}

	@Test
	void testLongScore()
	{
		String longName = "A".repeat(200);
		assertThrows(IllegalArgumentException.class, () -> objective.getScore(longName));
	}

	@Test
	void testChangeRenderType()
	{
		assertEquals(RenderType.INTEGER, objective.getRenderType());
		objective.setRenderType(RenderType.HEARTS);
		assertEquals(RenderType.HEARTS, objective.getRenderType());
	}

	@Test
	void testCantUnregisterTwice()
	{
		objective.unregister();
		assertFalse(objective.isRegistered());
		assertThrows(IllegalStateException.class, () -> objective.unregister());
	}

}
