package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import io.papermc.paper.registry.keys.SoundEventKeys;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
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
				.hasConsumeParticles(hasConsumeParticles)
				.addEffects(consumeEffects);
	}

	static class BuilderMock implements Builder
	{

		private float consumeSeconds = 1.6F;
		private ItemUseAnimation consumeAnimation = ItemUseAnimation.EAT;
		private Key eatSound = SoundEventKeys.ENTITY_GENERIC_EAT;
		private boolean hasConsumeParticles = true;
		private final List<ConsumeEffect> effects = new ObjectArrayList<>();

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
		public Builder effects(List<ConsumeEffect> effects)
		{
			this.effects.clear();
			return this.addEffects(effects);
		}

		@Override
		public Builder addEffect(ConsumeEffect effect)
		{
			Preconditions.checkNotNull(effect);
			this.effects.add(effect);
			return this;
		}

		@Override
		public Builder addEffects(List<ConsumeEffect> effects)
		{
			this.effects.addAll(effects);
			return this;
		}

		@Override
		public Consumable build()
		{
			return new ConsumableMock(consumeSeconds, consumeAnimation, eatSound, hasConsumeParticles, new ObjectArrayList<>(effects));
		}

	}

}
