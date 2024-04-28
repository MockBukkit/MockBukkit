package org.mockbukkit.mockbukkit.matcher.entity.ranged;

import com.google.common.base.Preconditions;
import org.bukkit.entity.LivingEntity;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.MobMock;
import org.mockbukkit.mockbukkit.entity.MockRangedEntity;

public class RangedEntityAttackMatcher extends TypeSafeMatcher<MockRangedEntity<? extends MobMock>>
{

	private final LivingEntity target;
	private final float charge;
	private final boolean aggressive;

	public RangedEntityAttackMatcher(LivingEntity target, float charge, boolean aggressive)
	{
		Preconditions.checkNotNull(target, "target cannot be null");
		Preconditions.checkArgument(charge >= 0F && charge <= 1F, "Charge must be between 0 and 1");
		this.target = target;
		this.charge = charge;
		this.aggressive = aggressive;
	}

	@Override
	protected boolean matchesSafely(MockRangedEntity<? extends MobMock> mockRangedEntity)
	{
		if (!mockRangedEntity.hasAttackedWithCharge(target, charge))
		{
			return false;
		}
		return !aggressive || mockRangedEntity.hasAttackedWhileAggressive(target);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have attacked following target").appendValue(target);
	}

	@Override
	protected void describeMismatchSafely(MockRangedEntity<? extends MobMock> mockRangedEntity, Description description)
	{
		if (mockRangedEntity.hasAttackedWithCharge(target, charge) && (!aggressive || mockRangedEntity.hasAttackedWhileAggressive(target)))
		{
			description.appendText("has attacked the target");
		}
		else
		{
			description.appendText("has not attacked the target");
		}
	}

	/**
	 *
	 * @param target The required target
	 * @param charge The required charge
	 * @return A matcher which matches with any ranged entity that has attacked the specified target with the specified charge
	 */
	public static @NotNull RangedEntityAttackMatcher hasAttacked(LivingEntity target, float charge)
	{
		return hasAttacked(target, charge, false);
	}

	/**
	 *
	 * @param target The required target
	 * @param charge The required charge
	 * @param aggressive Whether a check for aggressiveness should be done
	 * @return A matcher which matches with any ranged entity that has attacked the specified target with the
	 * specified charge while being aggressive
	 */
	public static @NotNull RangedEntityAttackMatcher hasAttacked(LivingEntity target, float charge, boolean aggressive)
	{
		return new RangedEntityAttackMatcher(target, charge, aggressive);
	}

}
