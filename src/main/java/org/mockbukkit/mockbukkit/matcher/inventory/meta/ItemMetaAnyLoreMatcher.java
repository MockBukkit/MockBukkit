package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;

import static org.hamcrest.Matchers.not;

public class ItemMetaAnyLoreMatcher extends TypeSafeMatcher<ItemMetaMock>
{

	@Override
	protected boolean matchesSafely(ItemMetaMock itemMetaMock)
	{
		return itemMetaMock.hasLore();
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have any lore");
	}

	@Override
	protected void describeMismatchSafely(ItemMetaMock itemMeta, Description description)
	{
		description.appendText("had lore ").appendValueList("[", ",", "]", itemMeta.getLore());
	}

	/**
	 *
	 * @return A matcher which matches with any meta with any lore
	 */
	public static @NotNull ItemMetaAnyLoreMatcher hasAnyLore()
	{
		return new ItemMetaAnyLoreMatcher();
	}

	/**
	 *
	 * @return A matcher which matches with any meta without any lore
	 */
	public static @NotNull Matcher<ItemMetaMock> hasNoLore()
	{
		return not(hasAnyLore());
	}

}
