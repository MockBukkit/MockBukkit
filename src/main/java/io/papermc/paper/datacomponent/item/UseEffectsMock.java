package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class UseEffectsMock implements UseEffects
{

	private final boolean canSprint;
	private final boolean interactVibrations;
	private final float speedMultiplier;

	private UseEffectsMock(BuilderMock builder)
	{
		this.canSprint = builder.canSprint;
		this.interactVibrations = builder.interactVibrations;
		this.speedMultiplier = builder.speedMultiplier;
	}

	@Override
	public boolean canSprint()
	{
		return this.canSprint;
	}

	@Override
	public boolean interactVibrations()
	{
		return this.interactVibrations;
	}

	@Override
	public float speedMultiplier()
	{
		return this.speedMultiplier;
	}

	static class BuilderMock implements Builder
	{

		private boolean canSprint = false;
		private boolean interactVibrations = true;
		private float speedMultiplier = 0.2F;

		@Override
		public Builder canSprint(boolean canSprint)
		{
			this.canSprint = canSprint;
			return this;
		}

		@Override
		public Builder interactVibrations(boolean interactVibrations)
		{
			this.interactVibrations = interactVibrations;
			return this;
		}

		@Override
		public Builder speedMultiplier(float speedMultiplier)
		{
			Preconditions.checkArgument(speedMultiplier >= 0.0F && speedMultiplier <= 1.0F, "speedMultiplier must be between 0.0 and 1.0 (inclusive)");
			this.speedMultiplier = speedMultiplier;
			return this;
		}

		@Override
		public UseEffects build()
		{
			return new UseEffectsMock(this);
		}

	}

}
