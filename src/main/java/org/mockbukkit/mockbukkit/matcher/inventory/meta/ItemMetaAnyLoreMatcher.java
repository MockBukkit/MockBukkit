package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;

public class ItemMetaAnyLoreMatcher extends TypeSafeMatcher<ItemMetaMock>
{

	@Override
	protected boolean matchesSafely(ItemMetaMock item)
	{
		return item.hasLore();
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

	public static ItemMetaAnyLoreMatcher hasAnyLore()
	{
		return new ItemMetaAnyLoreMatcher();
	}

}
