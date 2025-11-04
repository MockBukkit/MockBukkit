package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record SeededContainerLootMock(Key lootTable, long seed) implements SeededContainerLoot
{

	static class BuilderMock implements Builder
	{

		private long seed = 0L;
		private Key key;

		BuilderMock(Key key)
		{
			this.key = key;
		}

		@Override
		public Builder lootTable(Key key)
		{
			this.key = key;
			return this;
		}

		@Override
		public Builder seed(long seed)
		{
			this.seed = seed;
			return this;
		}

		@Override
		public SeededContainerLoot build()
		{
			Preconditions.checkNotNull(key);
			return new SeededContainerLootMock(key, seed);
		}

	}

}
