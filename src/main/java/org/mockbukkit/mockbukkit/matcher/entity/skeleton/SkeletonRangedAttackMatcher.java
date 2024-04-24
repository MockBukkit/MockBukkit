package org.mockbukkit.mockbukkit.matcher.entity.skeleton;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.LivingEntity;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.AbstractSkeletonMock;

import java.lang.reflect.Field;
import java.util.Map;

public class SkeletonRangedAttackMatcher extends TypeSafeMatcher<AbstractSkeletonMock>
{

	private final LivingEntity target;
	private final float charge;
	private final boolean aggressive;

	public SkeletonRangedAttackMatcher(LivingEntity target, float charge, boolean aggressive)
	{
		Preconditions.checkNotNull(target, "target cannot be null");
		Preconditions.checkArgument(charge >= 0F && charge <= 1F, "Charge must be between 0 and 1");
		this.target = target;
		this.charge = charge;
		this.aggressive = aggressive;
	}

	@Override
	protected boolean matchesSafely(AbstractSkeletonMock item)
	{
		try
		{
			Field attackedMobsField = item.getClass().getDeclaredField("attackedMobs");
			attackedMobsField.setAccessible(true);
			Map<LivingEntity, Pair<Float, Boolean>> attackedMobs = (Map<LivingEntity, Pair<Float, Boolean>>) attackedMobsField.get(item);
			if (!attackedMobs.containsKey(target) || attackedMobs.get(target).getLeft() != charge)
			{
				return false;
			}
			return !aggressive || attackedMobs.get(target).getRight();
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have attacked specified target");
	}

	public static SkeletonRangedAttackMatcher hasAttacked(LivingEntity target, float charge)
	{
		return hasAttacked(target, charge, false);
	}

	public static SkeletonRangedAttackMatcher hasAttacked(LivingEntity target, float charge, boolean aggressive)
	{
		return new SkeletonRangedAttackMatcher(target, charge, aggressive);
	}

}
