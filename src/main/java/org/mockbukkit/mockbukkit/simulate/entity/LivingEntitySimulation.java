package org.mockbukkit.mockbukkit.simulate.entity;

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
		EntityDamageEvent event;
		if (source.getDirectEntity() != null)
		{
			event = new EntityDamageByEntityEvent(source.getDirectEntity(), livingEntityMock, EntityDamageEvent.DamageCause.ENTITY_ATTACK, source, amount);
		}
		else
		{
			event = new EntityDamageEvent(livingEntityMock, EntityDamageEvent.DamageCause.CUSTOM, source, amount);
		}
		if (event.callEvent())
		{
			livingEntityMock.setLastDamageCause(event);
			amount = event.getDamage();
			livingEntityMock.damage(amount);
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
