package org.mockbukkit.mockbukkit.matcher.entity.goat;

import org.bukkit.entity.LivingEntity;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.GoatMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.UUID;

import static org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher.hasRammed;

@ExtendWith(MockBukkitExtension.class)
class GoatEntityRammedMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	ServerMock serverMock;
	private GoatMock goatMock;
	private LivingEntity target;

	@BeforeEach
	void setUp()
	{
		this.goatMock = new GoatMock(serverMock, UUID.randomUUID());
		this.target = serverMock.addPlayer();
	}

	@Test
	void rammed()
	{
		goatMock.ram(target);
		assertMatches(hasRammed(target), goatMock);
	}

	@Test
	void notRammed()
	{
		assertDoesNotMatch(hasRammed(target), goatMock);
	}

	@Test
	void rammed_differentTarget()
	{
		LivingEntity differentTarget = serverMock.addPlayer();
		goatMock.ram(differentTarget);
		assertDoesNotMatch(hasRammed(target), goatMock);
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
		return hasRammed(target);
	}

}
