package be.seeseemelk.mockbukkit.potion;

import java.util.concurrent.TimeUnit;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.entity.LivingEntityMock;

/**
 * This class represents an active {@link PotionEffect} which was applied to a {@link LivingEntity}.
 *
 * @author TheBusyBiscuit
 *
 * @see LivingEntityMock#addPotionEffect(PotionEffect)
 */
public final class ActivePotionEffect
{

	private final PotionEffect effect;
	private final long timestamp;

	public ActivePotionEffect(@NotNull PotionEffect effect)
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
	@NotNull
	public PotionEffect getPotionEffect()
	{
		return effect;
	}

}
