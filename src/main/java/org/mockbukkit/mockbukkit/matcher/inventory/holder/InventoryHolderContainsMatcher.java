package org.mockbukkit.mockbukkit.matcher.inventory.holder;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;

import static org.hamcrest.Matchers.not;

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
	public static @NotNull InventoryHolderContainsMatcher hasItemInInventory(@NotNull ItemStack itemStack)
	{
		Preconditions.checkNotNull(itemStack);
		return new InventoryHolderContainsMatcher(itemStack);
	}

	/**
	 * @param itemStack The item stack required for there to be no match
	 * @return A matcher which matches with any inventory without the specified item stack
	 */
	public static @NotNull Matcher<InventoryHolder> doesNotHaveItemInInventory(@NotNull ItemStack itemStack)
	{
		return not(hasItemInInventory(itemStack));
	}


}
