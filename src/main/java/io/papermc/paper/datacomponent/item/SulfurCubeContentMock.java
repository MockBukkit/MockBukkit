package io.papermc.paper.datacomponent.item;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "UnstableApiUsage", "NonExtendableApiUsage" })
public record SulfurCubeContentMock(ItemStack absorbedItem) implements SulfurCubeContent
{

	@Override
	public ItemStack absorbedItem()
	{
		return this.absorbedItem;
	}

}
