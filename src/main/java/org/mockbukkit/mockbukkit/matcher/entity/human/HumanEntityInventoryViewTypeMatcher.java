package org.mockbukkit.mockbukkit.matcher.entity.human;

import com.google.common.base.Preconditions;
import org.bukkit.event.inventory.InventoryType;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;

import static org.hamcrest.Matchers.not;

public class HumanEntityInventoryViewTypeMatcher extends TypeSafeMatcher<HumanEntityMock>
{

	private final InventoryType inventoryType;

	public HumanEntityInventoryViewTypeMatcher(InventoryType inventoryType)
	{
		this.inventoryType = inventoryType;
	}

	@Override
	protected boolean matchesSafely(HumanEntityMock humanEntityMock)
	{
		return humanEntityMock.getOpenInventory().getType() == inventoryType;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following inventory view type ").appendValue(inventoryType);
	}

	@Override
	protected void describeMismatchSafely(HumanEntityMock humanEntityMock, Description mismatchDescription)
	{
		mismatchDescription.appendText("was of type ").appendValue(humanEntityMock.getOpenInventory().getType());
	}

	/**
	 *
	 * @param inventoryType The required inventory
	 * @return A matcher which matches with any human entity with the specified inventory
	 */
	public static @NotNull HumanEntityInventoryViewTypeMatcher hasInventoryViewType(@NotNull InventoryType inventoryType)
	{
		Preconditions.checkNotNull(inventoryType);
		return new HumanEntityInventoryViewTypeMatcher(inventoryType);
	}

	/**
	 *
	 * @param inventoryType The required inventory for no match
	 * @return A matcher which matches with any human entity without the specified inventory
	 */
	public static @NotNull Matcher<HumanEntityMock> doesNotHaveInventoryViewType(@NotNull InventoryType inventoryType)
	{
		return not(hasInventoryViewType(inventoryType));
	}

}
