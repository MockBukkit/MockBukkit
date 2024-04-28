package org.mockbukkit.mockbukkit.matcher.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

public class InventoryItemAmountMatcher extends TypeSafeMatcher<InventoryMock>
{

	private final ItemStack targetItem;
	private final int targetAmount;

	public InventoryItemAmountMatcher(ItemStack targetItem, int targetAmount)
	{
		this.targetItem = targetItem;
		this.targetAmount = targetAmount;
	}

	@Override
	protected boolean matchesSafely(InventoryMock inventoryMock)
	{
		int itemAmount = 0;
		for (ItemStack item : inventoryMock)
		{
			if (item.isSimilar(targetItem))
			{
				itemAmount += item.getAmount();
			}
		}
		return itemAmount >= targetAmount;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have more than the target amount ").appendValue(targetAmount).appendText(" of item ").appendValue(targetItem);
	}

	@Override
	public void describeMismatchSafely(InventoryMock inventoryMock, Description description)
	{
		description.appendText("contained the items ").appendValueList("[", ",", "]", inventoryMock);
	}

	/**
	 *
	 * @param material The material of the items
	 * @param amount The amount of the items required for a match
	 * @return A matcher which matches with any inventory with more than the required amount of items
	 */
	public static @NotNull InventoryItemAmountMatcher containsAtLeast(Material material, int amount)
	{
		return new InventoryItemAmountMatcher(new ItemStack(material), amount);
	}

	/**
	 *
	 * @param targetItem The target item
	 * @param amount The amount of the items required for a match
	 * @return A matcher which matches with any inventory with more than the required amount of items
	 */
	public static @NotNull InventoryItemAmountMatcher containsAtLeast(ItemStack targetItem, int amount)
	{
		return new InventoryItemAmountMatcher(targetItem, amount);
	}

}
