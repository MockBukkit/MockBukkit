package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public record ConsumableMock(@NonNegative float consumeSeconds, ItemUseAnimation animation,
							 Key sound, boolean hasConsumeParticles,
							 @Unmodifiable List<ConsumeEffect> consumeEffects) implements Consumable
{

	@Override
	public Builder toBuilder()
	{
		return new BuilderMock()
				.consumeSeconds(consumeSeconds)
				.animation(animation)
				.sound(sound)
				.addEffects(consumeEffects);
	}

	static class BuilderMock implements Builder
	{

		private float consumeSeconds;
		private ItemUseAnimation consumeAnimation;
		private Key eatSound;
		private boolean hasConsumeParticles;
		private final ImmutableList.Builder<ConsumeEffect> effectBuilder = new ImmutableList.Builder<>();

		@Override
		public Builder consumeSeconds(@NonNegative float consumeSeconds)
		{
			Preconditions.checkArgument(consumeSeconds >= 0, "consumeSeconds must be non-negative, was %s", consumeSeconds);
			this.consumeSeconds = consumeSeconds;
			return this;
		}

		@Override
		public Builder animation(ItemUseAnimation animation)
		{
			this.consumeAnimation = animation;
			return this;
		}

		@Override
		public Builder sound(Key sound)
		{
			this.eatSound = sound;
			return this;
		}

		@Override
		public Builder hasConsumeParticles(boolean hasConsumeParticles)
		{
			this.hasConsumeParticles = hasConsumeParticles;
			return this;
		}

		@Override
		public Builder addEffect(ConsumeEffect effect)
		{
			Preconditions.checkNotNull(effect);
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
		public Consumable build()
		{
			return new ConsumableMock(consumeSeconds, consumeAnimation, eatSound, hasConsumeParticles, effectBuilder.build());
		}

	}

}
