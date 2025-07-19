package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

public class ItemContainerContentsMock implements ItemContainerContents
{

	private final List<ItemStack> contents;

	private ItemContainerContentsMock(List<ItemStack> contents)
	{
		this.contents = contents;
	}

	@Override
	public @Unmodifiable List<ItemStack> contents()
	{
		return contents.stream()
				.map(ItemStack::clone)
				.toList();
	}

	static class BuilderMock implements Builder
	{

		private final List<ItemStack> items = new ArrayList<>();

		@Override
		public Builder add(ItemStack stack)
		{
			Preconditions.checkArgument(stack != null, "Item cannot be null");
			checkSize(1);
			items.add(stack);
			return this;
		}

		@Override
		public Builder addAll(List<ItemStack> stacks)
		{
			Preconditions.checkNotNull(stacks);
			checkSize(stacks.size());
			stacks.forEach(stack -> Preconditions.checkArgument(stack != null, "Cannot pass null item!"));
			items.addAll(stacks);
			return this;
		}

		@Override
		public ItemContainerContents build()
		{
			return new ItemContainerContentsMock(items);
		}

		private void checkSize(int extra)
		{
			Preconditions.checkArgument(items.size() + extra <= 256, "Cannot have more than %s items, had %s", 256, items.size() + extra);
		}

	}

}
