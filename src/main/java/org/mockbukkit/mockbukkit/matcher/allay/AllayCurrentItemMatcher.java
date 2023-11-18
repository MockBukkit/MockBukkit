package org.mockbukkit.mockbukkit.matcher.allay;

import org.bukkit.Material;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.AllayMock;

import java.lang.reflect.Field;

public class AllayCurrentItemMatcher extends TypeSafeMatcher<AllayMock>
{

	private Material currentItem;
	private AllayMock allay;

	private AllayCurrentItemMatcher(Material currentItem)
	{
		this.currentItem = currentItem;
	}

	@Override
	protected boolean matchesSafely(AllayMock allay)
	{
		this.allay = allay;
		try
		{
			Field currentItemField;
			currentItemField = allay.getClass().getDeclaredField("currentItem");
			currentItemField.setAccessible(true);
			return currentItemField.get(allay).equals(currentItem);
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
		description.appendText("should have current item ");
	}

	@Override
	protected void describeMismatchSafely(AllayMock item, Description mismatchDescription)
	{
		mismatchDescription.appendText(" has current item \"" + currentItem.toString() + "\"");
	}

	public static AllayCurrentItemMatcher currentItem(Material currentItem)
	{
		return new AllayCurrentItemMatcher(currentItem);
	}
}
