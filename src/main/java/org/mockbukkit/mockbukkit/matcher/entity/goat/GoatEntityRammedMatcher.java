package org.mockbukkit.mockbukkit.matcher.entity.goat;

import org.bukkit.entity.LivingEntity;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.GoatMock;

public class GoatEntityRammedMatcher extends TypeSafeMatcher<GoatMock>
{

	private final LivingEntity target;

	public GoatEntityRammedMatcher(LivingEntity target)
	{
		this.target = target;
	}

	@Override
	protected boolean matchesSafely(GoatMock item)
	{
		return item.hasRammedEntity(target);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have rammed the specified entity");
	}

	public static GoatEntityRammedMatcher hasRammed(LivingEntity target)
	{
		return new GoatEntityRammedMatcher(target);
	}

}
