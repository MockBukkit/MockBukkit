package be.seeseemelk.mockbukkit.entity;

import com.destroystokyo.paper.entity.RangedEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

public interface MockRangedEntity<T extends Mob> extends RangedEntity
{

	T getHandle();

	@Override
	default void rangedAttack(@NotNull LivingEntity target, float charge)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	default void setChargingAttack(boolean raiseHands)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
