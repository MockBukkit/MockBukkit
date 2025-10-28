package org.mockbukkit.mockbukkit.matcher.entity.skeleton;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.entity.SkeletonMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasAttacked;

@ExtendWith(MockBukkitExtension.class)
class RangedEntityAttackMatcherTest extends AbstractMatcherTest
{

	private static final float CHARGE = 0.5f;

	@MockBukkitInject
	private ServerMock serverMock;
	@MockBukkitInject
	private PlayerMock target;
	@MockBukkitInject
	private SkeletonMock skeleton;

	@Test
	void attacked()
	{
		skeleton.rangedAttack(target, CHARGE);
		assertMatches(hasAttacked(target, CHARGE), skeleton);
	}

	@Test
	void notAttacked()
	{
		assertDoesNotMatch(hasAttacked(target, CHARGE), skeleton);
	}

	@Test
	void attacked_wrongCharge()
	{
		skeleton.rangedAttack(target, CHARGE);
		assertDoesNotMatch(hasAttacked(target, 0.4f), skeleton);
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
		return hasAttacked(target, CHARGE);
	}

}
