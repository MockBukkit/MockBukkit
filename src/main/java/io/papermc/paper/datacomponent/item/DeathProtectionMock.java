package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public record DeathProtectionMock(@Unmodifiable List<ConsumeEffect> deathEffects) implements DeathProtection
{


	static class BuilderMock implements Builder
	{

		ImmutableList.Builder<ConsumeEffect> effectBuilder = new ImmutableList.Builder<>();

		@Override
		public Builder addEffect(ConsumeEffect effect)
		{
			effectBuilder.add(Preconditions.checkNotNull(effect));
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
