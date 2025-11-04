package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record FoodPropertiesMock(int nutrition, float saturation, boolean canAlwaysEat) implements FoodProperties
{

	@Override
	public Builder toBuilder()
	{
		return new BuilderMock()
				.canAlwaysEat(canAlwaysEat)
				.nutrition(nutrition)
				.saturation(saturation);
	}

	static class BuilderMock implements Builder
	{

		private boolean canAlwaysEat = false;
		private float saturation = 0.0F;
		private int nutrition = 0;

		@Override
		public Builder canAlwaysEat(boolean canAlwaysEat)
		{
			this.canAlwaysEat = canAlwaysEat;
			return this;
		}

		@Override
		public Builder saturation(float saturation)
		{
			this.saturation = saturation;
			return this;
		}

		@Override
		public Builder nutrition(@NonNegative int nutrition)
		{
			Preconditions.checkArgument(nutrition >= 0, "nutrition must be non-negative, was %s", nutrition);
			this.nutrition = nutrition;
			return this;
		}

		@Override
		public FoodProperties build()
		{
			return new FoodPropertiesMock(nutrition, saturation, canAlwaysEat);
		}

	}

}
