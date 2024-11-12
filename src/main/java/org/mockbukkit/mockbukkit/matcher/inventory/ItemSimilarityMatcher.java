package org.mockbukkit.mockbukkit.matcher.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;

public class ItemSimilarityMatcher extends TypeSafeMatcher<ItemStack>
{

	private final ItemStack itemStack;

	public ItemSimilarityMatcher(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	protected boolean matchesSafely(ItemStack itemStack)
	{
		return this.itemStack.isSimilar(itemStack);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to be similar to the following item ").appendValue(itemStack);
	}

	@Override
	public void describeMismatchSafely(ItemStack itemStack, Description description)
	{
		description.appendText("was of type ").appendValue(itemStack.getType());
	}

	/**
	 * Matches when two item stacks are similar to each other using the {@link ItemStack#isSimilar(ItemStack)} method
	 * @param itemStack The required item stack to be similar to for a match
	 * @return A matcher which matches when an item stack is similar to the specified item stack
	 */
	public static @NotNull ItemSimilarityMatcher similarTo(@NotNull ItemStack itemStack)
	{
		Preconditions.checkNotNull(itemStack);
		return new ItemSimilarityMatcher(itemStack);
	}

	/**
	 * Matches when two item stacks are similar to each other using the {@link ItemStack#isSimilar(ItemStack)} method
	 * @param itemMaterial The required material of the item stack to be similar to for a match
	 * @return A matcher which matches when an item stack is similar to the specified item stack
	 */
	public static @NotNull ItemSimilarityMatcher similarTo(@NotNull Material itemMaterial)
	{
		Preconditions.checkNotNull(itemMaterial);
		return new ItemSimilarityMatcher(new ItemStack(itemMaterial));
	}

}
