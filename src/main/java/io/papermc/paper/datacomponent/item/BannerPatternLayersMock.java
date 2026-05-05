package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.block.banner.Pattern;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record BannerPatternLayersMock(@Unmodifiable List<Pattern> patterns) implements BannerPatternLayers
{

	static class BuilderMock implements Builder
	{

		List<Pattern> patterns = new ArrayList<>();

		@Override
		public Builder add(Pattern pattern)
		{
			Preconditions.checkNotNull(pattern);
			patterns.add(pattern);
			return this;
		}

		@Override
		public Builder addAll(List<Pattern> patterns)
		{
			patterns.forEach(this::add);
			return this;
		}

		@Override
		public BannerPatternLayers build()
		{
			return new BannerPatternLayersMock(List.copyOf(patterns));
		}

	}

}
