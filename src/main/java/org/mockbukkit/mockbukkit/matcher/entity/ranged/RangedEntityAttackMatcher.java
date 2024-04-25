package org.mockbukkit.mockbukkit.matcher.entity.ranged;

import com.google.common.base.Preconditions;
import org.bukkit.entity.LivingEntity;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
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
	protected boolean matchesSafely(MockRangedEntity<? extends MobMock> item)
	{
		if (!item.hasAttackedWithCharge(target, charge))
		{
			return false;
		}
		return !aggressive || item.hasAttackedWhileAggressive(target);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have attacked specified target");
	}

	@Override
	protected void describeMismatchSafely(MockRangedEntity<? extends MobMock> item, Description description)
	{
		if (item.hasAttackedWithCharge(target, charge) && (!aggressive || item.hasAttackedWhileAggressive(target)))
		{
			description.appendText("has attacked the target");
		}
		else
		{
			description.appendText("has not attacked the target");
		}
	}

	public static RangedEntityAttackMatcher hasAttacked(LivingEntity target, float charge)
	{
		return hasAttacked(target, charge, false);
	}

	public static RangedEntityAttackMatcher hasAttacked(LivingEntity target, float charge, boolean aggressive)
	{
		return new RangedEntityAttackMatcher(target, charge, aggressive);
	}

}
