package org.mockbukkit.mockbukkit.matcher.entity.allay;

import org.bukkit.Material;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.AllayMock;

public class AllayCurrentItemMatcher extends TypeSafeMatcher<AllayMock>
{

	private final Material currentItem;

	private AllayCurrentItemMatcher(Material currentItem)
	{
		this.currentItem = currentItem;
	}

	@Override
	protected boolean matchesSafely(AllayMock allay)
	{
		return currentItem.equals(allay.getCurrentItem());
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have current item of material ").appendValue(currentItem);
	}

	@Override
	protected void describeMismatchSafely(AllayMock allay, Description mismatchDescription)
	{
		mismatchDescription.appendText("has current item ").appendValue(allay.getCurrentItem());
	}

	public static AllayCurrentItemMatcher currentItem(Material currentItem)
	{
		return new AllayCurrentItemMatcher(currentItem);
	}

}
