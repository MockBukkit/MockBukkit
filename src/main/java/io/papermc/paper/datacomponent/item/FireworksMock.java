package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.FireworkEffect;
import org.checkerframework.common.value.qual.IntRange;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record FireworksMock(int flightDuration, List<FireworkEffect> effects) implements Fireworks
{

	static class BuilderMock implements Builder
	{

		private int duration = 0;
		private static final int MAX_EXPLOSIONS = 256;
		private final List<FireworkEffect> effects = new ObjectArrayList<>();

		@Override
		public Builder flightDuration(@IntRange(from = 0L, to = 255L) int duration)
		{
			Preconditions.checkArgument(duration >= 0 && duration <= 255, "duration must be an unsigned byte ([%s, %s]), was %s", 0, 255, duration);
			this.duration = duration;
			return this;
		}

		@Override
		public Builder addEffect(FireworkEffect effect)
		{
			Preconditions.checkArgument(
					this.effects.size() + 1 <= MAX_EXPLOSIONS,
					"Cannot have more than %s effects, had %s",
					MAX_EXPLOSIONS,
					this.effects.size() + 1
			);
			this.effects.add(effect);
			return this;
		}

		@Override
		public Builder addEffects(List<FireworkEffect> effects)
		{
			Preconditions.checkArgument(
					this.effects.size() + effects.size() <= MAX_EXPLOSIONS,
					"Cannot have more than %s effects, had %s",
					MAX_EXPLOSIONS,
					this.effects.size() + effects.size()
			);
			this.effects.addAll(effects);
			return this;
		}

		@Override
		public Fireworks build()
		{
			return new FireworksMock(duration, List.copyOf(effects));
		}

	}

}
