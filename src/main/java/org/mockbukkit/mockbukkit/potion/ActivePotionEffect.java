package org.mockbukkit.mockbukkit.potion;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public final class ActivePotionEffect implements Comparable<ActivePotionEffect>
{

	private final @NotNull PotionEffect effect;
	private final int startTick;

	public ActivePotionEffect(@NotNull PotionEffect effect)
	{
		this.effect = effect;
		this.startTick = Bukkit.getCurrentTick();
	}

	public boolean hasExpired()
	{
		return getDuration() == 0;
	}

	@NotNull
	public PotionEffect getPotionEffect()
	{
		return effect;
	}

	public int getDuration()
	{
		if (effect.isInfinite())
		{
			return -1;
		}
		return Math.max(0, effect.getDuration() - Bukkit.getCurrentTick() + startTick);
	}

	@Override
	public int compareTo(@NotNull ActivePotionEffect other)
	{
		// Higher amplifier wins
		int amplifierCompare = Integer.compare(other.effect.getAmplifier(), this.effect.getAmplifier());
		if (amplifierCompare != 0)
		{
			return amplifierCompare;
		}

		// If amplifiers are equal, higher remaining duration wins
		return Integer.compare(other.getDuration(), this.getDuration());
	}

}
