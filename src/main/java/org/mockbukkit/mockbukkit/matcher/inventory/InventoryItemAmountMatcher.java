package org.mockbukkit.mockbukkit.matcher.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
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
		description.appendText("to have more than the target amount of the specified item");
	}

	@Override
	public void describeMismatchSafely(InventoryMock inventoryMock, Description description)
	{
		description.appendText("contained the items ").appendValueList("[", ",", "]", inventoryMock);
	}

	public static InventoryItemAmountMatcher containsAtLeast(Material material, int amount)
	{
		return new InventoryItemAmountMatcher(new ItemStack(material), amount);
	}

	public static InventoryItemAmountMatcher containsAtLeast(ItemStack targetItem, int amount)
	{
		return new InventoryItemAmountMatcher(targetItem, amount);
	}

}
