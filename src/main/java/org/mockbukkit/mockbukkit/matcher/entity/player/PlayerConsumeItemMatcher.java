package org.mockbukkit.mockbukkit.matcher.entity.player;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.hamcrest.Matchers.not;

public class PlayerConsumeItemMatcher extends TypeSafeMatcher<PlayerMock>
{

	private final ItemStack itemStack;

	public PlayerConsumeItemMatcher(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	protected boolean matchesSafely(PlayerMock playerMock)
	{
		return playerMock.hasConsumed(this.itemStack);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have consumed the following item ").appendValue(itemStack);
	}

	@Override
	protected void describeMismatchSafely(PlayerMock playerMock, Description description)
	{
		if (playerMock.hasConsumed(itemStack))
		{
			description.appendText("had consumed the item");
		}
		else
		{
			description.appendText("had not consumed the item");
		}
	}

	/**
	 * @param itemStack The required item to have been consumed
	 * @return A matcher which matches with any player that has consumed the specified item
	 */
	public static @NotNull PlayerConsumeItemMatcher hasConsumed(@NotNull ItemStack itemStack)
	{
		Preconditions.checkNotNull(itemStack);
		return new PlayerConsumeItemMatcher(itemStack);
	}

	/**
	 * @param itemStack The required item to not have been consumed
	 * @return A matcher which matches with any player that has not consumed the specified item
	 */
	public static @NotNull Matcher<PlayerMock> hasNotConsumed(@NotNull ItemStack itemStack)
	{
		return not(hasConsumed(itemStack));
	}


}
