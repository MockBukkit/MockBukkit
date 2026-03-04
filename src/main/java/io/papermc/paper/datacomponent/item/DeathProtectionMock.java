package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record DeathProtectionMock(@Unmodifiable List<ConsumeEffect> deathEffects) implements DeathProtection
{


	static class BuilderMock implements Builder
	{

		ImmutableList.Builder<ConsumeEffect> effectBuilder = new ImmutableList.Builder<>();

		@Override
		public Builder addEffect(ConsumeEffect effect)
		{
			Preconditions.checkArgument(effect != null, "effect is null");
			effectBuilder.add(effect);
			return this;
		}

		@Override
		public Builder addEffects(List<ConsumeEffect> effects)
		{
			effects.forEach(this::addEffect);
			return this;
		}

		@Override
		public DeathProtection build()
		{
			return new DeathProtectionMock(effectBuilder.build());
		}

	}

}
