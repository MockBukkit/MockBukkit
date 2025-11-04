package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class ChargedProjectilesMock implements ChargedProjectiles
{

	private final List<ItemStack> items;

	private ChargedProjectilesMock(List<ItemStack> items)
	{
		this.items = items;
	}

	@Override
	public @Unmodifiable List<ItemStack> projectiles()
	{
		return items.stream().map(ItemStack::clone).toList();
	}

	static class BuilderMock implements Builder
	{

		private final List<ItemStack> items = new ArrayList<>();

		@Override
		public Builder add(ItemStack stack)
		{
			Preconditions.checkArgument(stack != null, "stack cannot be null");
			Preconditions.checkArgument(!stack.isEmpty(), "stack cannot be empty");
			items.add(stack.clone());
			return this;
		}

		@Override
		public Builder addAll(List<ItemStack> stacks)
		{
			stacks.forEach(this::add);
			return this;
		}

		@Override
		public ChargedProjectiles build()
		{
			return new ChargedProjectilesMock(items);
		}

	}

}
