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
	protected boolean matchesSafely(GoatMock goatMock)
	{
		return goatMock.hasRammedEntity(target);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have rammed the following entity ").appendValue(target);
	}

	@Override
	protected void describeMismatchSafely(GoatMock goatMock, Description mismatchDescription)
	{
		mismatchDescription.appendText("was value ").appendValue(goatMock.hasRammedEntity(target));
	}

	/**
	 *
	 * @param target The target required to have been hit for there to be a match
	 * @return A matcher which matches with any goat that has targeted the specified target
	 */
	public static GoatEntityRammedMatcher hasRammed(LivingEntity target)
	{
		return new GoatEntityRammedMatcher(target);
	}

}
