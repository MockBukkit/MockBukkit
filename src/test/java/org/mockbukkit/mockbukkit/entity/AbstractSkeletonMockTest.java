package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasAttacked;
import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasNotAttacked;

@ExtendWith(MockBukkitExtension.class)
class AbstractSkeletonMockTest
{

	@MockBukkitInject
	private AbstractSkeletonMock skeleton;

	@Test
	void testSetSkeletonTypeThrows()
	{
		assertThrows(UnsupportedOperationException.class, () -> skeleton.setSkeletonType(Skeleton.SkeletonType.NORMAL));
	}

	@Test
	void testShouldBurnInDayDefault()
	{
		assertTrue(skeleton.shouldBurnInDay());
	}

	@Test
	void testSetShouldBurnInDay()
	{
		skeleton.setShouldBurnInDay(false);
		assertFalse(skeleton.shouldBurnInDay());
	}

	@Test
	void testIsChargingAttackDefault()
	{
		assertFalse(skeleton.isChargingAttack());
	}

	@Test
	void testSetChargingAttack()
	{
		skeleton.setChargingAttack(true);
		assertTrue(skeleton.isChargingAttack());
	}

	@Test
	void testRangedAttack(@MockBukkitInject Player player)
	{
		skeleton.rangedAttack(player, 0.5f);
		assertThat(skeleton, hasAttacked(player, 0.5f));
	}

	@Test
	void testRangedAttackThrowsWithNullEntity()
	{
		assertThrows(NullPointerException.class, () -> skeleton.rangedAttack(null, 0.5f));
	}

	@Test
	void testRangedAttackThrowsWithInvalidCharge(@MockBukkitInject Player player)
	{
		assertThrows(IllegalArgumentException.class, () -> skeleton.rangedAttack(player, -0.5f));
		assertThrows(IllegalArgumentException.class, () -> skeleton.rangedAttack(player, 1.5f));
	}

	@Test
	void testAssertAttackedThrowsWithNoAttack(@MockBukkitInject Player player)
	{
		assertThat(skeleton, hasNotAttacked(player, 0.5f));
	}

	@Test
	void testAssertAttackThrowsWithInvalidCharge(@MockBukkitInject Player player)
	{
		skeleton.rangedAttack(player, 0.5f);

		assertThrows(IllegalArgumentException.class, () -> skeleton.hasAttackedWithCharge(player, -0.5f));
		assertThrows(IllegalArgumentException.class, () -> skeleton.hasAttackedWithCharge(player, 1.5f));
	}

	@Test
	void testAssertAttackThrowsWithWrongCharge(@MockBukkitInject Player player)
	{
		skeleton.rangedAttack(player, 0.5f);

		assertThat(skeleton, hasNotAttacked(player, 0.6f));
	}

	@Test
	void testAssertAgressiveAttack(@MockBukkitInject Player player)
	{
		skeleton.setChargingAttack(true);
		skeleton.rangedAttack(player, 0.5f);
		assertThat(skeleton, hasAttacked(player, 0.5f, true));
	}

	@Test
	void testAssertAgressiveAttackThrowsWhenNotAgressive(@MockBukkitInject Player player)
	{
		skeleton.rangedAttack(player, 0.5f);
		assertThat(skeleton, hasNotAttacked(player, 0.5f, true));
	}

}
