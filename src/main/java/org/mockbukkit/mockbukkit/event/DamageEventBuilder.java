package org.mockbukkit.mockbukkit.event;

import com.google.common.base.Preconditions;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Builds an {@link EntityDamageByEntityEvent} without the caller having to touch a deprecated constructor.
 * <p>
 * Obtain one from {@link MockEvents#damage()}.
 */
public class DamageEventBuilder
{

	private Entity damager;
	private Entity target;
	private double amount = 1.0;
	private DamageType damageType = DamageType.GENERIC;
	private EntityDamageEvent.@Nullable DamageCause cause;

	DamageEventBuilder()
	{
	}

	/**
	 * @param damager The entity dealing the damage.
	 * @return This builder.
	 */
	public @NotNull DamageEventBuilder by(@NotNull Entity damager)
	{
		Preconditions.checkArgument(damager != null, "Damager cannot be null");
		this.damager = damager;
		return this;
	}

	/**
	 * @param target The entity taking the damage.
	 * @return This builder.
	 */
	public @NotNull DamageEventBuilder to(@NotNull Entity target)
	{
		Preconditions.checkArgument(target != null, "Target cannot be null");
		this.target = target;
		return this;
	}

	/**
	 * @param amount How much damage is dealt. Defaults to 1.
	 * @return This builder.
	 */
	public @NotNull DamageEventBuilder amount(double amount)
	{
		this.amount = amount;
		return this;
	}

	/**
	 * @param damageType The damage type carried on the source. Defaults to {@link DamageType#GENERIC}.
	 * @return This builder.
	 */
	public @NotNull DamageEventBuilder type(@NotNull DamageType damageType)
	{
		Preconditions.checkArgument(damageType != null, "Damage type cannot be null");
		this.damageType = damageType;
		return this;
	}

	/**
	 * @param cause The cause reported by {@link EntityDamageEvent#getCause()}. Defaults to
	 *              {@code ENTITY_ATTACK}.
	 * @return This builder.
	 */
	public @NotNull DamageEventBuilder cause(EntityDamageEvent.@NotNull DamageCause cause)
	{
		Preconditions.checkArgument(cause != null, "Cause cannot be null");
		this.cause = cause;
		return this;
	}

	/**
	 * @return The built event, not yet fired.
	 */
	public @NotNull EntityDamageByEntityEvent build()
	{
		Preconditions.checkState(this.damager != null, "A damager is required -- call by(...)");
		Preconditions.checkState(this.target != null, "A target is required -- call to(...)");

		DamageSource source = DamageSource.builder(this.damageType)
				.withDirectEntity(this.damager)
				.withCausingEntity(this.damager)
				.build();
		EntityDamageEvent.DamageCause damageCause = this.cause == null
				? EntityDamageEvent.DamageCause.ENTITY_ATTACK
				: this.cause;

		return new EntityDamageByEntityEvent(this.damager, this.target, damageCause, source, this.amount);
	}

}
