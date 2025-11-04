package io.papermc.paper.datacomponent.item.blocksattacks;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.set.RegistryKeySet;
import org.bukkit.damage.DamageType;
import org.checkerframework.checker.index.qual.Positive;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record DamageReductionMock(float horizontalBlockingAngle,
								  @Nullable RegistryKeySet<DamageType> type,
								  float base,
								  float factor) implements DamageReduction
{

	static class BuilderMock implements Builder
	{
		private @Nullable RegistryKeySet<DamageType> type;
		private float horizontalBlockingAngle = 90.0F;
		private float base = 0.0F;
		private float factor = 1.0F;

		@Override
		public Builder type(@Nullable RegistryKeySet<DamageType> type)
		{
			this.type = type;
			return this;
		}

		@Override
		public Builder horizontalBlockingAngle(@Positive float horizontalBlockingAngle)
		{
			Preconditions.checkArgument(horizontalBlockingAngle > 0.0F, "horizontalBlockingAngle must be positive and not zero, was %s", horizontalBlockingAngle);
			this.horizontalBlockingAngle = horizontalBlockingAngle;
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
		public DamageReduction build()
		{
			return new DamageReductionMock(this.horizontalBlockingAngle, this.type, this.base, this.factor);
		}

	}

}
