package io.papermc.paper.datacomponent.item;

import io.papermc.paper.datacomponent.DataComponentType;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;
import java.util.Set;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record TooltipDisplayMock(boolean hideTooltip, Set<DataComponentType> hiddenComponents) implements TooltipDisplay
{

	@Override
	public Set<DataComponentType> hiddenComponents()
	{
		return new ReferenceLinkedOpenHashSet<>(this.hiddenComponents);
	}

	static class BuilderMock implements Builder
	{
		private final Set<DataComponentType> hiddenComponents = new ReferenceLinkedOpenHashSet<>();
		private boolean hideTooltip;

		@Override
		public Builder hideTooltip(boolean hide)
		{
			this.hideTooltip = hide;
			return this;
		}

		@Override
		public Builder addHiddenComponents(DataComponentType... components)
		{
			this.hiddenComponents.addAll(Arrays.asList(components));
			return this;
		}

		@Override
		public Builder hiddenComponents(Set<DataComponentType> components)
		{
			this.hiddenComponents.addAll(components);
			return this;
		}

		@Override
		public TooltipDisplay build()
		{
			return new TooltipDisplayMock(this.hideTooltip, this.hiddenComponents);
		}

	}
}
