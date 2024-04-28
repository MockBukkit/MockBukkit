package org.mockbukkit.mockbukkit.matcher.inventory.holder;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.Contract;
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
	protected void describeMismatchSafely(InventoryHolder item, @NotNull Description mismatchDescription)
	{
		mismatchDescription.appendText("doesn't have Itemstack \"" + itemStack.toString() + "\" in Inventory");
	}

	@Contract("_ -> new")
	public static @NotNull InventoryHolderContainsMatcher inventoryContains(ItemStack itemStack)
	{
		return new InventoryHolderContainsMatcher(itemStack);
	}

}
