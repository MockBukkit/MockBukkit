package io.papermc.paper.datacomponent.item;

import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record PotDecorationsMock(@Nullable ItemType back, @Nullable ItemType left,
								 @Nullable ItemType right, @Nullable ItemType front) implements PotDecorations
{
	static class BuilderMock implements PotDecorations.Builder
	{
		private @Nullable ItemType back;
		private @Nullable ItemType left;
		private @Nullable ItemType right;
		private @Nullable ItemType front;

		@Override
		public Builder back(@Nullable ItemType back)
		{
			this.back = back;
			return this;
		}

		@Override
		public Builder left(@Nullable ItemType left)
		{
			this.left = left;
			return this;
		}

		@Override
		public Builder right(@Nullable ItemType right)
		{
			this.right = right;
			return this;
		}

		@Override
		public Builder front(@Nullable ItemType front)
		{
			this.front = front;
			return this;
		}

		@Override
		public PotDecorations build()
		{
			return new PotDecorationsMock(back, left, right, front);
		}

	}
}
