package org.mockbukkit.mockbukkit.matcher.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ItemSimilarityMatcher extends TypeSafeMatcher<ItemStack>
{

	private final ItemStack itemStack;

	public ItemSimilarityMatcher(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	protected boolean matchesSafely(ItemStack item)
	{
		return this.itemStack.isSimilar(item);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to be similar to the following item ").appendValue(itemStack);
	}

	@Override
	public void describeMismatchSafely(ItemStack itemStack, Description description){
		description.appendText("was of type ").appendValue(itemStack.getType());
	}

	public static ItemSimilarityMatcher similarTo(ItemStack itemStack)
	{
		return new ItemSimilarityMatcher(itemStack);
	}

	public static ItemSimilarityMatcher similarTo(Material itemMaterial)
	{
		return new ItemSimilarityMatcher(new ItemStack(itemMaterial));
	}

}
