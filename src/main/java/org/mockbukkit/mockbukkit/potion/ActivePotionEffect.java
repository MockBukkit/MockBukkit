package org.mockbukkit.mockbukkit.potion;

import org.mockbukkit.mockbukkit.entity.LivingEntityMock;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents an active {@link PotionEffect} which was applied to a {@link LivingEntity}.
 *
 * @author TheBusyBiscuit
 * @see LivingEntityMock#addPotionEffect(PotionEffect)
 */
public final class ActivePotionEffect
{

	private final @NotNull PotionEffect effect;
	private final int tickTimestamp;

	/**
	 * Constructs a new {@link ActivePotionEffect} with the provided {@link PotionEffect}.
	 *
	 * @param effect The effect that's been applied.
	 */
	public ActivePotionEffect(@NotNull PotionEffect effect)
	{
		this.effect = effect;
		this.tickTimestamp = Bukkit.getCurrentTick();
	}

	/**
	 * This returns whether this {@link PotionEffect} has expired.
	 *
	 * @return Whether the effect wore off.
	 */
	public boolean hasExpired()
	{
		int ticks = effect.getDuration();
		return !effect.isInfinite() && (ticks < 1 || tickTimestamp + ticks < Bukkit.getCurrentTick());
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

}
