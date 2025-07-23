package io.papermc.paper.datacomponent.item;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.Nullable;

public record UseCooldownMock(float seconds, @Nullable Key cooldownGroup) implements UseCooldown
{


	static class BuilderMock implements Builder
	{

		private final float seconds;
		private @Nullable Key key;

		BuilderMock(float seconds)
		{
			this.seconds = seconds;
		}

		@Override
		public Builder cooldownGroup(@Nullable Key key)
		{
			this.key = key;
			return this;
		}

		@Override
		public UseCooldown build()
		{
			return new UseCooldownMock(seconds, key);
		}

	}

}
