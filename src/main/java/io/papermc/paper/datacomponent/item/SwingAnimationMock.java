package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.index.qual.Positive;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class SwingAnimationMock implements SwingAnimation
{

	private final Animation type;
	private final int duration;

	private SwingAnimationMock(BuilderMock builder)
	{
		this.type = builder.type;
		this.duration = builder.duration;
	}

	@Override
	public Animation type()
	{
		return this.type;
	}

	@Override
	public @Positive int duration()
	{
		return this.duration;
	}

	static class BuilderMock implements Builder
	{

		private Animation type = Animation.WHACK;
		private int duration = 6;

		@Override
		public Builder type(Animation type)
		{
			this.type = type;
			return this;
		}

		@Override
		public Builder duration(@Positive int duration)
		{
			Preconditions.checkArgument(duration > 0, "duration must be positive");
			this.duration = duration;
			return this;
		}

		@Override
		public SwingAnimation build()
		{
			return new SwingAnimationMock(this);
		}

	}

}
