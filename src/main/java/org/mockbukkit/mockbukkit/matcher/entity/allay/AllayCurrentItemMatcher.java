package org.mockbukkit.mockbukkit.matcher.entity.allay;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.AllayMock;

import static org.hamcrest.Matchers.not;

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

	/**
	 *
	 * @param currentItem The material of the item to be held for there to be a match
	 * @return A matcher which matches with any allay holding the specified item
	 */
	public static @NotNull AllayCurrentItemMatcher hasCurrentItem(@NotNull Material currentItem)
	{
		Preconditions.checkNotNull(currentItem);
		return new AllayCurrentItemMatcher(currentItem);
	}

	/**
	 *
	 * @param currentItem The material of the item to be held for there to be no match
	 * @return A matcher which matches with any allay not holding the specified item
	 */
	public static @NotNull Matcher<AllayMock> doesNotHaveCurrentItem(@NotNull Material currentItem)
	{
		return not(hasCurrentItem(currentItem));
	}
}
