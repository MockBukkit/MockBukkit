package org.mockbukkit.mockbukkit.matcher.entity.human;

import org.bukkit.GameMode;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityGameModeMatcher.hasGameMode;

@ExtendWith(MockBukkitExtension.class)
class HumanEntityGameModeMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private HumanEntityMock humanEntity;

	@BeforeEach
	void setUp()
	{
		this.humanEntity = serverMock.addPlayer();
	}

	@Test
	void correctGameMode()
	{
		humanEntity.setGameMode(GameMode.SPECTATOR);
		assertMatches(hasGameMode(GameMode.SPECTATOR), humanEntity);
	}

	@Test
	void incorrectGameMode()
	{
		humanEntity.setGameMode(GameMode.CREATIVE);
		assertDoesNotMatch(hasGameMode(GameMode.SPECTATOR), humanEntity);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasGameMode(GameMode.SURVIVAL);
	}

}
