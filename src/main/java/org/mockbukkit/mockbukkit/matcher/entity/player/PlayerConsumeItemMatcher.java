package org.mockbukkit.mockbukkit.matcher.entity.player;

import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

public class PlayerConsumeItemMatcher extends TypeSafeMatcher<PlayerMock>
{

	private final ItemStack itemStack;

	public PlayerConsumeItemMatcher(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	protected boolean matchesSafely(PlayerMock item)
	{
		return item.hasConsumed(this.itemStack);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have consumed the specified item");
	}

	public static PlayerConsumeItemMatcher hasConsumed(ItemStack itemStack)
	{
		return new PlayerConsumeItemMatcher(itemStack);
	}

}
