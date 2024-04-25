package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.entity.RangedEntity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.UnimplementedOperationException;

/**
 * Mock implementation of a {@link RangedEntity}.
 *
 * @see MobMock
 */
public interface MockRangedEntity<T extends MobMock> extends RangedEntity
{

	@Override
	default void rangedAttack(@NotNull LivingEntity target, float charge)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	default void setChargingAttack(boolean raiseHands)
	{
		throw new UnimplementedOperationException();
	}

	default boolean hasAttackedWithCharge(LivingEntity target, float charge)
	{
		throw new UnimplementedOperationException();
	}

	default boolean hasAttackedWhileAggressive(LivingEntity target)
	{
		throw new UnimplementedOperationException();
	}

}
