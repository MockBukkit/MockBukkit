package org.mockbukkit.mockbukkit.matcher.entity.human;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;

public class HumanEntityInventoryViewItemMatcher extends TypeSafeMatcher<HumanEntityMock>
{

	private final Material material;

	public HumanEntityInventoryViewItemMatcher(Material material)
	{
		this.material = material;
	}

	@Override
	protected boolean matchesSafely(HumanEntityMock item)
	{
		return item.getInventory().contains(material);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to contain any of the specified item material");
	}

	public static HumanEntityInventoryViewItemMatcher hasItemInInventoryView(Material material)
	{
		return new HumanEntityInventoryViewItemMatcher(material);
	}

	public static HumanEntityInventoryViewItemMatcher hasItemInInventoryView(ItemStack item)
	{
		return new HumanEntityInventoryViewItemMatcher(item.getType());
	}

}
