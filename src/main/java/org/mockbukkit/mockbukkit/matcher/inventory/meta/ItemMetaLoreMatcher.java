package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;

import java.util.Arrays;
import java.util.List;

public class ItemMetaLoreMatcher extends TypeSafeMatcher<ItemMetaMock>
{

	private final List<? extends Component> targetLore;

	public ItemMetaLoreMatcher(List<? extends Component> targetLore)
	{
		this.targetLore = targetLore;
	}

	@Override
	protected boolean matchesSafely(ItemMetaMock item)
	{
		List<Component> lore = item.lore();
		if (lore == null || lore.size() != targetLore.size())
		{
			return false;
		}
		for (int i = 0; i < lore.size(); i++)
		{
			if (!lore.get(i).equals(targetLore.get(i)))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following lore ").appendValueList("[", ",", "]", this.targetLore);
	}

	@Override
	protected void describeMismatchSafely(ItemMetaMock itemMeta, Description description)
	{
		description.appendText("had lore ").appendValueList("[", ",", "]", itemMeta.getLore());
	}

	public static ItemMetaLoreMatcher hasLore(String... legacyLoreItems)
	{
		LegacyComponentSerializer legacy = LegacyComponentSerializer.legacySection();
		return new ItemMetaLoreMatcher(Arrays.stream(legacyLoreItems).map(legacy::deserialize).toList());
	}

	public static ItemMetaLoreMatcher hasLore(Component... loreItems)
	{
		return new ItemMetaLoreMatcher(Arrays.asList(loreItems));
	}

	public static ItemMetaLoreMatcher hasLore(List<Component> lore)
	{
		return new ItemMetaLoreMatcher(lore);
	}

}
