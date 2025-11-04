package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class PotionContentsMock implements PotionContents
{

	private final @Nullable PotionType potion;
	private final @Nullable Color customColor;
	private final List<PotionEffect> customEffects;
	private final @Nullable String customName;

	private PotionContentsMock(@Nullable PotionType potion, @Nullable Color customColor, List<PotionEffect> customEffects, @Nullable String customName)
	{
		this.potion = potion;
		this.customColor = customColor;
		this.customEffects = customEffects;
		this.customName = customName;
	}

	@Override
	public @Nullable PotionType potion()
	{
		return potion;
	}

	@Override
	public @Nullable Color customColor()
	{
		return customColor == null ? null : Color.fromRGB(customColor.asRGB());
	}

	@Override
	public @Unmodifiable List<PotionEffect> customEffects()
	{
		return customEffects;
	}

	@Override
	public @Nullable String customName()
	{
		return customName;
	}

	@Override
	public @Unmodifiable List<PotionEffect> allEffects()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public Color computeEffectiveColor()
	{
		throw new UnimplementedOperationException();
	}

	static class BuilderMock implements Builder
	{

		private final List<PotionEffect> customEffects = new ArrayList<>();
		private @Nullable PotionType potion;
		private @Nullable Color color;
		private @Nullable String customName;

		@Override
		public Builder potion(@Nullable PotionType type)
		{
			this.potion = type;
			return this;
		}

		@Override
		public Builder customColor(@Nullable Color color)
		{
			this.color = color;
			return this;
		}

		@Override
		public Builder customName(@Nullable String name)
		{
			Preconditions.checkArgument(name == null || name.length() <= Short.MAX_VALUE, "Custom name is longer than %s characters", Short.MAX_VALUE);
			this.customName = name;
			return this;
		}

		@Override
		public Builder addCustomEffect(PotionEffect effect)
		{
			customEffects.add(effect);
			return this;
		}

		@Override
		public Builder addCustomEffects(List<PotionEffect> effects)
		{
			effects.forEach(this::addCustomEffect);
			return this;
		}

		@Override
		public PotionContents build()
		{
			return new PotionContentsMock(potion, color, List.copyOf(customEffects), customName);
		}

	}

}
