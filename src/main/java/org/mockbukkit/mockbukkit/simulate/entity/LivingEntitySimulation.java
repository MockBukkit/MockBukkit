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
		EntityDamageEvent.DamageCause cause = cause(source);
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

	/**
	 * The cause Paper reports for a damage of this source: from its type, else an attack when an entity dealt it.
	 *
	 * @param source <p>The source of the damage</p>
	 * @return <p>The cause of the event</p>
	 */
	private static EntityDamageEvent.DamageCause cause(@NotNull DamageSource source)
	{
		DamageType type = source.getDamageType();
		if (type == DamageType.FALL || type == DamageType.ENDER_PEARL)
		{
			return EntityDamageEvent.DamageCause.FALL;
		}
		if (type == DamageType.IN_FIRE)
		{
			return EntityDamageEvent.DamageCause.FIRE;
		}
		if (type == DamageType.CAMPFIRE)
		{
			return EntityDamageEvent.DamageCause.CAMPFIRE;
		}
		if (type == DamageType.ON_FIRE)
		{
			return EntityDamageEvent.DamageCause.FIRE_TICK;
		}
		if (type == DamageType.LAVA)
		{
			return EntityDamageEvent.DamageCause.LAVA;
		}
		if (type == DamageType.HOT_FLOOR)
		{
			return EntityDamageEvent.DamageCause.HOT_FLOOR;
		}
		if (type == DamageType.IN_WALL)
		{
			return EntityDamageEvent.DamageCause.SUFFOCATION;
		}
		if (type == DamageType.CRAMMING)
		{
			return EntityDamageEvent.DamageCause.CRAMMING;
		}
		if (type == DamageType.DROWN)
		{
			return EntityDamageEvent.DamageCause.DROWNING;
		}
		if (type == DamageType.STARVE)
		{
			return EntityDamageEvent.DamageCause.STARVATION;
		}
		if (type == DamageType.CACTUS || type == DamageType.SWEET_BERRY_BUSH || type == DamageType.STALAGMITE)
		{
			return EntityDamageEvent.DamageCause.CONTACT;
		}
		if (type == DamageType.FLY_INTO_WALL)
		{
			return EntityDamageEvent.DamageCause.FLY_INTO_WALL;
		}
		if (type == DamageType.OUT_OF_WORLD)
		{
			return EntityDamageEvent.DamageCause.VOID;
		}
		if (type == DamageType.GENERIC_KILL)
		{
			return EntityDamageEvent.DamageCause.KILL;
		}
		if (type == DamageType.OUTSIDE_BORDER)
		{
			return EntityDamageEvent.DamageCause.WORLD_BORDER;
		}
		if (type == DamageType.MAGIC || type == DamageType.INDIRECT_MAGIC)
		{
			return EntityDamageEvent.DamageCause.MAGIC;
		}
		if (type == DamageType.WITHER)
		{
			return EntityDamageEvent.DamageCause.WITHER;
		}
		if (type == DamageType.FALLING_ANVIL || type == DamageType.FALLING_BLOCK || type == DamageType.FALLING_STALACTITE)
		{
			return EntityDamageEvent.DamageCause.FALLING_BLOCK;
		}
		if (type == DamageType.DRAGON_BREATH)
		{
			return EntityDamageEvent.DamageCause.DRAGON_BREATH;
		}
		if (type == DamageType.DRY_OUT)
		{
			return EntityDamageEvent.DamageCause.DRYOUT;
		}
		if (type == DamageType.FREEZE)
		{
			return EntityDamageEvent.DamageCause.FREEZE;
		}
		if (type == DamageType.LIGHTNING_BOLT)
		{
			return EntityDamageEvent.DamageCause.LIGHTNING;
		}
		if (type == DamageType.THORNS)
		{
			return EntityDamageEvent.DamageCause.THORNS;
		}
		if (type == DamageType.SONIC_BOOM)
		{
			return EntityDamageEvent.DamageCause.SONIC_BOOM;
		}
		if (type == DamageType.EXPLOSION)
		{
			return EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
		}
		if (type == DamageType.PLAYER_EXPLOSION)
		{
			return EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
		}
		if (type == DamageType.ARROW || type == DamageType.TRIDENT || type == DamageType.FIREBALL || type == DamageType.UNATTRIBUTED_FIREBALL
				|| type == DamageType.WITHER_SKULL || type == DamageType.THROWN || type == DamageType.MOB_PROJECTILE)
		{
			return EntityDamageEvent.DamageCause.PROJECTILE;
		}
		return source.getDirectEntity() != null ? EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.CUSTOM;
	}

}
