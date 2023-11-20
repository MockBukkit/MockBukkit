package org.mockbukkit.mockbukkit.matcher.allay;

import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.AllayMock;

public class AllayInventoryContainsMatcher extends TypeSafeMatcher<AllayMock>
{

	private final ItemStack itemStack;

	public AllayInventoryContainsMatcher(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	protected boolean matchesSafely(@NotNull AllayMock allay)
	{
		return allay.getInventory().contains(itemStack);
	}

	@Override
	public void describeTo(@NotNull Description description)
	{
		description.appendText("Should have Itemstack in inventory");
	}

	@Override
	protected void describeMismatchSafely(AllayMock item, @NotNull Description mismatchDescription)
	{
		mismatchDescription.appendText("doesn't have Itemstack \"" + itemStack.toString() + "\" in Inventory");
	}

	@Contract("_ -> new")
	public static @NotNull AllayInventoryContainsMatcher inventoryContains(ItemStack itemStack)
	{
		return new AllayInventoryContainsMatcher(itemStack);
	}

}
