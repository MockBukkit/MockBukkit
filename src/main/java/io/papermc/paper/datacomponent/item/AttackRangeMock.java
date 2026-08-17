package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class AttackRangeMock implements AttackRange
{

	private final float minReach;
	private final float maxReach;
	private final float minCreativeReach;
	private final float maxCreativeReach;
	private final float hitboxMargin;
	private final float mobFactor;

	private AttackRangeMock(BuilderMock builder)
	{
		this.minReach = builder.minReach;
		this.maxReach = builder.maxReach;
		this.minCreativeReach = builder.minCreativeReach;
		this.maxCreativeReach = builder.maxCreativeReach;
		this.hitboxMargin = builder.hitboxMargin;
		this.mobFactor = builder.mobFactor;
	}

	@Override
	public @Range(from = 0L, to = 64L) float minReach()
	{
		return this.minReach;
	}

	@Override
	public @Range(from = 0L, to = 64L) float maxReach()
	{
		return this.maxReach;
	}

	@Override
	public @Range(from = 0L, to = 64L) float minCreativeReach()
	{
		return this.minCreativeReach;
	}

	@Override
	public @Range(from = 0L, to = 64L) float maxCreativeReach()
	{
		return this.maxCreativeReach;
	}

	@Override
	public @Range(from = 0L, to = 1L) float hitboxMargin()
	{
		return this.hitboxMargin;
	}

	@Override
	public @Range(from = 0L, to = 2L) float mobFactor()
	{
		return this.mobFactor;
	}

	static class BuilderMock implements Builder
	{

		private float minReach = 0F;
		private float maxReach = 3F;
		private float minCreativeReach = 0F;
		private float maxCreativeReach = 5F;
		private float hitboxMargin = 0.3F;
		private float mobFactor = 1F;

		@Override
		public Builder minReach(@Range(from = 0L, to = 64L) float minReach)
		{
			Preconditions.checkArgument(minReach >= 0.0F && minReach <= 64.0F, "minReach must be in range [0,64] was %s", minReach);
			this.minReach = minReach;
			return this;
		}

		@Override
		public Builder maxReach(@Range(from = 0L, to = 64L) float maxReach)
		{
			Preconditions.checkArgument(maxReach >= 0.0F && maxReach <= 64.0F, "maxReach must be in range [0,64] was %s", maxReach);
			this.maxReach = maxReach;
			return this;
		}

		@Override
		public Builder minCreativeReach(@Range(from = 0L, to = 64L) float minCreativeReach)
		{
			Preconditions.checkArgument(minCreativeReach >= 0.0F && minCreativeReach <= 64.0F, "minCreativeReach must be in range [0,64] was %s", minCreativeReach);
			this.minCreativeReach = minCreativeReach;
			return this;
		}

		@Override
		public Builder maxCreativeReach(@Range(from = 0L, to = 64L) float maxCreativeReach)
		{
			Preconditions.checkArgument(maxCreativeReach >= 0.0F && maxCreativeReach <= 64.0F, "maxCreativeReach must be in range [0,64] was %s", maxCreativeReach);
			this.maxCreativeReach = maxCreativeReach;
			return this;
		}

		@Override
		public Builder hitboxMargin(@Range(from = 0L, to = 1L) float hitboxMargin)
		{
			Preconditions.checkArgument(hitboxMargin >= 0.0F && hitboxMargin <= 1.0F, "hitboxMargin must be in range [0,1] was %s", hitboxMargin);
			this.hitboxMargin = hitboxMargin;
			return this;
		}

		@Override
		public Builder mobFactor(@Range(from = 0L, to = 2L) float mobFactor)
		{
			Preconditions.checkArgument(mobFactor >= 0.0F && mobFactor <= 2.0F, "mobFactor must be in range [0,2] was %s", mobFactor);
			this.mobFactor = mobFactor;
			return this;
		}

		@Override
		public AttackRange build()
		{
			return new AttackRangeMock(this);
		}

	}

}
