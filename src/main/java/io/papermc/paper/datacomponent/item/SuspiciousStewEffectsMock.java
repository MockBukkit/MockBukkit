package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.potion.SuspiciousEffectEntry;

import java.util.Collection;
import java.util.List;

public record SuspiciousStewEffectsMock(List<SuspiciousEffectEntry> effects) implements SuspiciousStewEffects
{

	static class BuilderMock implements Builder
	{

		ImmutableList.Builder<SuspiciousEffectEntry> effects = new ImmutableList.Builder<>();

		@Override
		public Builder add(SuspiciousEffectEntry entry)
		{
			Preconditions.checkNotNull(entry);
			effects.add(entry);
			return this;
		}

		@Override
		public Builder addAll(Collection<SuspiciousEffectEntry> entries)
		{
			entries.forEach(this::add);
			return this;
		}

		@Override
		public SuspiciousStewEffects build()
		{
			return new SuspiciousStewEffectsMock(effects.build());
		}

	}

}
