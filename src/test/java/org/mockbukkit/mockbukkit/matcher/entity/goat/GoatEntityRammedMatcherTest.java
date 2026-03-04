package org.mockbukkit.mockbukkit.matcher.entity.goat;

import org.bukkit.entity.LivingEntity;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.GoatMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher.hasRammed;

@ExtendWith(MockBukkitExtension.class)
class GoatEntityRammedMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	@MockBukkitInject
	private GoatMock goatMock;
	@MockBukkitInject
	private LivingEntity target;

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
