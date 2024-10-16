package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.destroystokyo.paper.entity.RangedEntity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	default void setChargingAttack(boolean raiseHands)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
