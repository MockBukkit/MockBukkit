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
		return timestamp + TimeUnit.SECONDS.toMillis(effect.getDuration() * 20) < System.currentTimeMillis();
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
