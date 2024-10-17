package org.mockbukkit.mockbukkit.matcher.entity.human;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;

import static org.hamcrest.Matchers.not;

public class HumanEntityInventoryViewItemMatcher extends TypeSafeMatcher<HumanEntityMock>
{

	private final Material material;

	public HumanEntityInventoryViewItemMatcher(Material material)
	{
		this.material = material;
	}

	@Override
	protected boolean matchesSafely(HumanEntityMock humanEntityMock)
	{
		if(humanEntityMock.getOpenInventory().getTopInventory() == null){
			return false;
		}
		return humanEntityMock.getOpenInventory().getTopInventory().contains(material);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to contain any of the following item material ").appendValue(material);
	}

	@Override
	protected void describeMismatchSafely(HumanEntityMock humanEntityMock, Description mismatchDescription)
	{
		if(humanEntityMock.getOpenInventory().getTopInventory() != null)
		{
			mismatchDescription.appendText("had items ");
			mismatchDescription.appendValueList("[", ",", "]", humanEntityMock.getOpenInventory().getTopInventory().getContents());
		}
		else
		{
			mismatchDescription.appendText("did not have an open top inventory");
		}
	}

	/**
	 *
	 * @param material The material of the item required for a match
	 * @return A matcher which matches with any human entity with the specified item in inventory
	 */
	public static @NotNull HumanEntityInventoryViewItemMatcher hasItemInInventoryView(Material material)
	{
		return new HumanEntityInventoryViewItemMatcher(material);
	}

	/**
	 *
	 * @param item The item required for a match
	 * @return A matcher which matches with any human entity with the specified item in inventory
	 */
	public static @NotNull HumanEntityInventoryViewItemMatcher hasItemInInventoryView(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item);
		return new HumanEntityInventoryViewItemMatcher(item.getType());
	}

	/**
	 *
	 * @param item The item required for no match
	 * @return A matcher which matches with any human entity without the specified item in inventory
	 */
	public static @NotNull Matcher<HumanEntityMock> doesNotHaveItemInInventoryView(@NotNull ItemStack item)
	{
		return not(hasItemInInventoryView(item));
	}

	/**
	 *
	 * @param material The material of the item required for no match
	 * @return A matcher which matches with any human entity without the specified item in inventory
	 */
	public static @NotNull Matcher<HumanEntityMock> doesNotHaveItemInInventoryView(Material material)
	{
		return not(hasItemInInventoryView(material));
	}


}
