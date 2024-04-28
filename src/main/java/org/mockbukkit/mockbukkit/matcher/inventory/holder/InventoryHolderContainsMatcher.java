package org.mockbukkit.mockbukkit.matcher.inventory.holder;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;

public class InventoryHolderContainsMatcher extends TypeSafeMatcher<InventoryHolder>
{

	private final ItemStack itemStack;

	public InventoryHolderContainsMatcher(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	protected boolean matchesSafely(InventoryHolder inventoryHolder)
	{
		return inventoryHolder.getInventory().contains(itemStack);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following item stack in inventory").appendValue(itemStack);
	}

	@Override
	protected void describeMismatchSafely(InventoryHolder inventoryHolder, @NotNull Description mismatchDescription)
	{
		mismatchDescription.appendText("had the items ").appendValueList("[", ",", "]", inventoryHolder.getInventory());
	}

	/**
	 * @param itemStack The item stack required for there to be a match
	 * @return A matcher which matches with any inventory of specified item stack
	 */
	public static @NotNull InventoryHolderContainsMatcher inventoryContains(ItemStack itemStack)
	{
		return new InventoryHolderContainsMatcher(itemStack);
	}

}
