package io.papermc.paper.datacomponent.item.blocksattacks;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ItemDamageFunctionMock(float threshold, float base, float factor) implements ItemDamageFunction
{

	@Override
	public int damageToApply(float damageAmount)
	{
		return damageAmount < this.threshold ? 0 : (int) Math.floor(this.base + this.factor * damageAmount);
	}

	static class BuilderMock implements Builder
	{
		private float threshold = 1.0F;
		private float base = 0.0F;
		private float factor = 1.0F;

		@Override
		public Builder threshold(@NonNegative float threshold)
		{
			Preconditions.checkArgument(threshold >= 0.0F, "threshold must be non-negative, was %s", threshold);
			this.threshold = threshold;
			return this;
		}

		@Override
		public Builder base(float base)
		{
			this.base = base;
			return this;
		}

		@Override
		public Builder factor(float factor)
		{
			this.factor = factor;
			return this;
		}

		@Override
		public ItemDamageFunction build()
		{
			return new ItemDamageFunctionMock(this.threshold, this.base, this.factor);
		}

	}

}
