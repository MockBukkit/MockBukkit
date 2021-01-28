package be.seeseemelk.mockbukkit.entity;

import java.util.concurrent.TimeUnit;

import org.bukkit.potion.PotionEffect;

class ActivePotionEffect
{

	private final PotionEffect effect;
	private final long timestamp;

	ActivePotionEffect(PotionEffect effect)
	{
		this.effect = effect;
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * This returns whether this {@link PotionEffect} has expired.
	 *
	 * @return Whether the effect wore off.
	 */
	public boolean hasExpired()
	{
		int ticks = effect.getDuration() * 20;
		return ticks < 1 || timestamp + TimeUnit.SECONDS.toMillis(ticks) < System.currentTimeMillis();
	}

	/**
	 * This method returns the underlying {@link PotionEffect}
	 *
	 * @return The actual {@link PotionEffect}.
	 */
	public PotionEffect getPotionEffect()
	{
		return effect;
	}

}
