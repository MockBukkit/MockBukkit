package io.papermc.paper.datacomponent.item;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class UseRemainderMock implements UseRemainder
{

	private final ItemStack itemStack;

	UseRemainderMock(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}

	@Override
	public ItemStack transformInto()
	{
		return itemStack.clone();
	}

}
