package org.mockbukkit.mockbukkit.simulate.entity;

import com.google.common.base.Preconditions;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.LivingEntityMock;

public class LivingEntitySimulation
{

	private final LivingEntityMock livingEntityMock;

	public LivingEntitySimulation(LivingEntityMock livingEntityMock)
	{
		this.livingEntityMock = livingEntityMock;
	}

	/**
	 * Simulate damage to this entity and throw an event.
	 *
	 * @param amount <p>The amount of damage to be done</p>
	 * @param source <p>The damager</p>
	 * @return <p>The EntityDamageEvent that got thrown</p>
	 */
	public EntityDamageEvent simulateDamage(double amount, @NotNull DamageSource source)
	{
		EntityDamageEvent.DamageCause cause = source.getDirectEntity() != null
				? EntityDamageEvent.DamageCause.ENTITY_ATTACK
				: EntityDamageEvent.DamageCause.CUSTOM;
		return simulateDamage(amount, source, cause);
	}

	/**
	 * Simulate damage to this entity and throw an event, stating what caused it.
	 * <p>
	 * The two-argument form has to guess, and guesses {@code ENTITY_ATTACK} whenever the source has a direct entity --
	 * which is wrong for a projectile, an explosion or a potion, since those also have one. Pass the cause here when a
	 * listener under test reads {@link EntityDamageEvent#getCause()}.
	 *
	 * @param amount <p>The amount of damage to be done</p>
	 * @param source <p>The damager</p>
	 * @param cause  <p>The cause to report on the event</p>
	 * @return <p>The EntityDamageEvent that got thrown</p>
	 */
	public EntityDamageEvent simulateDamage(double amount, @NotNull DamageSource source,
			EntityDamageEvent.@NotNull DamageCause cause)
	{
		Preconditions.checkArgument(source != null, "Damage source cannot be null");
		Preconditions.checkArgument(cause != null, "Damage cause cannot be null");

		EntityDamageEvent event;
		if (source.getDirectEntity() != null)
		{
			event = new EntityDamageByEntityEvent(source.getDirectEntity(), livingEntityMock, cause, source, amount);
		}
		else
		{
			event = new EntityDamageEvent(livingEntityMock, cause, source, amount);
		}

		if (event.callEvent())
		{
			livingEntityMock.setLastDamageCause(event);
			amount = event.getDamage();
			livingEntityMock.damage(amount, source.getDirectEntity());
		}
		return event;
	}

	/**
	 * Simulate damage to this entity and throw an event
	 *
	 * @param amount <p>The amount of damage to be done</p>
	 * @param source <p>The damager</p>
	 * @return <p>The event that got thrown</p>
	 */
	public EntityDamageEvent simulateDamage(double amount, @Nullable Entity source)
	{
		DamageType damageType;
		if (source != null)
		{
			damageType = source instanceof HumanEntity ? DamageType.PLAYER_ATTACK : DamageType.MOB_ATTACK;
		}
		else
		{
			damageType = DamageType.GENERIC;
		}
		DamageSource.Builder damageSourceBuilder = DamageSource.builder(damageType);
		if (source != null)
		{
			damageSourceBuilder.withDamageLocation(source.getLocation()).withDirectEntity(source);
		}
		DamageSource damageSource = damageSourceBuilder.build();
		return simulateDamage(amount, damageSource);
	}

}
