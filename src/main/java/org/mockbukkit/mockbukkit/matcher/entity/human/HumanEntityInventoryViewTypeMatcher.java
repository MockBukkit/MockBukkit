package org.mockbukkit.mockbukkit.matcher.entity.human;

import org.bukkit.event.inventory.InventoryType;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;

public class HumanEntityInventoryViewTypeMatcher extends TypeSafeMatcher<HumanEntityMock>
{

	private final InventoryType inventoryType;

	public HumanEntityInventoryViewTypeMatcher(InventoryType inventoryType)
	{
		this.inventoryType = inventoryType;
	}

	@Override
	protected boolean matchesSafely(HumanEntityMock item)
	{
		return item.getOpenInventory().getType() == inventoryType;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the specified inventory view type");
	}

	@Override
	protected void describeMismatchSafely(HumanEntityMock item, Description mismatchDescription)
	{
		mismatchDescription.appendText("was of type ").appendValue(item.getOpenInventory().getType());
	}

	public static HumanEntityInventoryViewTypeMatcher hasInventoryViewType(InventoryType inventoryType)
	{
		return new HumanEntityInventoryViewTypeMatcher(inventoryType);
	}

}
