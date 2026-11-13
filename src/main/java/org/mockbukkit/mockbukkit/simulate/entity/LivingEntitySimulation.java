package org.mockbukkit.mockbukkit.simulate.entity;

import org.bukkit.NamespacedKey;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.LivingEntityMock;

import java.util.Map;

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
		EntityDamageEvent.DamageCause cause = Causes.BY_TYPE.get(source.getDamageType().getKey());
		if (cause != null)
		{
			return cause;
		}
		return source.getDirectEntity() != null ? EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.CUSTOM;
	}

	/**
	 * The causes Paper reports for the damage types that have their own, by the key of the type: each server builds its
	 * own damage types.
	 */
	private static final class Causes
	{

		private static final Map<NamespacedKey, EntityDamageEvent.DamageCause> BY_TYPE = Map.ofEntries(
				Map.entry(NamespacedKey.minecraft("fall"), EntityDamageEvent.DamageCause.FALL),
				Map.entry(NamespacedKey.minecraft("ender_pearl"), EntityDamageEvent.DamageCause.FALL),
				Map.entry(NamespacedKey.minecraft("in_fire"), EntityDamageEvent.DamageCause.FIRE),
				Map.entry(NamespacedKey.minecraft("campfire"), EntityDamageEvent.DamageCause.CAMPFIRE),
				Map.entry(NamespacedKey.minecraft("on_fire"), EntityDamageEvent.DamageCause.FIRE_TICK),
				Map.entry(NamespacedKey.minecraft("lava"), EntityDamageEvent.DamageCause.LAVA),
				Map.entry(NamespacedKey.minecraft("hot_floor"), EntityDamageEvent.DamageCause.HOT_FLOOR),
				Map.entry(NamespacedKey.minecraft("in_wall"), EntityDamageEvent.DamageCause.SUFFOCATION),
				Map.entry(NamespacedKey.minecraft("cramming"), EntityDamageEvent.DamageCause.CRAMMING),
				Map.entry(NamespacedKey.minecraft("drown"), EntityDamageEvent.DamageCause.DROWNING),
				Map.entry(NamespacedKey.minecraft("starve"), EntityDamageEvent.DamageCause.STARVATION),
				Map.entry(NamespacedKey.minecraft("cactus"), EntityDamageEvent.DamageCause.CONTACT),
				Map.entry(NamespacedKey.minecraft("sweet_berry_bush"), EntityDamageEvent.DamageCause.CONTACT),
				Map.entry(NamespacedKey.minecraft("stalagmite"), EntityDamageEvent.DamageCause.CONTACT),
				Map.entry(NamespacedKey.minecraft("fly_into_wall"), EntityDamageEvent.DamageCause.FLY_INTO_WALL),
				Map.entry(NamespacedKey.minecraft("out_of_world"), EntityDamageEvent.DamageCause.VOID),
				Map.entry(NamespacedKey.minecraft("generic_kill"), EntityDamageEvent.DamageCause.KILL),
				Map.entry(NamespacedKey.minecraft("outside_border"), EntityDamageEvent.DamageCause.WORLD_BORDER),
				Map.entry(NamespacedKey.minecraft("magic"), EntityDamageEvent.DamageCause.MAGIC),
				Map.entry(NamespacedKey.minecraft("indirect_magic"), EntityDamageEvent.DamageCause.MAGIC),
				Map.entry(NamespacedKey.minecraft("wither"), EntityDamageEvent.DamageCause.WITHER),
				Map.entry(NamespacedKey.minecraft("falling_anvil"), EntityDamageEvent.DamageCause.FALLING_BLOCK),
				Map.entry(NamespacedKey.minecraft("falling_block"), EntityDamageEvent.DamageCause.FALLING_BLOCK),
				Map.entry(NamespacedKey.minecraft("falling_stalactite"), EntityDamageEvent.DamageCause.FALLING_BLOCK),
				Map.entry(NamespacedKey.minecraft("dragon_breath"), EntityDamageEvent.DamageCause.DRAGON_BREATH),
				Map.entry(NamespacedKey.minecraft("dry_out"), EntityDamageEvent.DamageCause.DRYOUT),
				Map.entry(NamespacedKey.minecraft("freeze"), EntityDamageEvent.DamageCause.FREEZE),
				Map.entry(NamespacedKey.minecraft("lightning_bolt"), EntityDamageEvent.DamageCause.LIGHTNING),
				Map.entry(NamespacedKey.minecraft("thorns"), EntityDamageEvent.DamageCause.THORNS),
				Map.entry(NamespacedKey.minecraft("sonic_boom"), EntityDamageEvent.DamageCause.SONIC_BOOM),
				Map.entry(NamespacedKey.minecraft("explosion"), EntityDamageEvent.DamageCause.BLOCK_EXPLOSION),
				Map.entry(NamespacedKey.minecraft("player_explosion"), EntityDamageEvent.DamageCause.ENTITY_EXPLOSION),
				Map.entry(NamespacedKey.minecraft("arrow"), EntityDamageEvent.DamageCause.PROJECTILE),
				Map.entry(NamespacedKey.minecraft("trident"), EntityDamageEvent.DamageCause.PROJECTILE),
				Map.entry(NamespacedKey.minecraft("fireball"), EntityDamageEvent.DamageCause.PROJECTILE),
				Map.entry(NamespacedKey.minecraft("unattributed_fireball"), EntityDamageEvent.DamageCause.PROJECTILE),
				Map.entry(NamespacedKey.minecraft("wither_skull"), EntityDamageEvent.DamageCause.PROJECTILE),
				Map.entry(NamespacedKey.minecraft("thrown"), EntityDamageEvent.DamageCause.PROJECTILE),
				Map.entry(NamespacedKey.minecraft("mob_projectile"), EntityDamageEvent.DamageCause.PROJECTILE)
		);

		private Causes()
		{
			throw new UnsupportedOperationException("Utility class");
		}

	}

}
