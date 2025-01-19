package org.mockbukkit.metaminer.tests;

import org.bukkit.Registry;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemMetaClassFinder
{
	public static List<ItemType> getInduvidualMetaItemTypes()
	{
		Set<Class<? extends ItemMeta>> itemMetaClasses = new HashSet<>();
		List<ItemType> output = new ArrayList<>();
		for (ItemType itemType : Registry.ITEM)
		{
			if (!itemType.asMaterial().isAir() && !itemMetaClasses.contains(itemType.getItemMetaClass()))
			{
				output.add(itemType);
				itemMetaClasses.add(itemType.getItemMetaClass());
			}
		}
		return output;
	}
}
