package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ItemContainerContentsMock(List<ItemStack> contents) implements ItemContainerContents
{
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
