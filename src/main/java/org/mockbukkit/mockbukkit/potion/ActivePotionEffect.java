package org.mockbukkit.mockbukkit.potion;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.LivingEntityMock;

/**
 * This class represents an active {@link PotionEffect} which was applied to a {@link LivingEntity}.
 *
 * @author TheBusyBiscuit
 * @see LivingEntityMock#addPotionEffect(PotionEffect)
 */
public final class ActivePotionEffect
{

	private final @NotNull PotionEffect effect;
	private final int startTick;

	/**
	 * Constructs a new {@link ActivePotionEffect} with the provided {@link PotionEffect}.
	 *
	 * @param effect The effect that's been applied.
	 */
	public ActivePotionEffect(@NotNull PotionEffect effect)
	{
		this.effect = effect;
		this.startTick = Bukkit.getCurrentTick();
	}

	/**
	 * This returns whether this {@link PotionEffect} has expired.
	 *
	 * @return Whether the effect wore off.
	 */
	public boolean hasExpired()
	{
		return getDuration() == 0;
	}

	/**
	 * This method returns the underlying {@link PotionEffect}
	 *
	 * @return The actual {@link PotionEffect}.
	 */
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
}
