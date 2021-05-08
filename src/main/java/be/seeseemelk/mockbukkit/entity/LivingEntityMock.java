package be.seeseemelk.mockbukkit.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.google.common.base.Function;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.attribute.AttributeInstanceMock;
import be.seeseemelk.mockbukkit.potion.ActivePotionEffect;

public abstract class LivingEntityMock extends EntityMock implements LivingEntity
{

	private static final double MAX_HEALTH = 20.0;
	protected double health;
	private double maxHealth = MAX_HEALTH;
	private int maxAirTicks = 300;
	private int remainingAirTicks = 300;
	protected boolean alive = true;
	protected Map<Attribute, AttributeInstanceMock> attributes;

	private final Set<ActivePotionEffect> activeEffects = new HashSet<>();

	public LivingEntityMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);

		attributes = new EnumMap<>(Attribute.class);
		attributes.put(Attribute.GENERIC_MAX_HEALTH, new AttributeInstanceMock(Attribute.GENERIC_MAX_HEALTH, 20));
		this.setMaxHealth(MAX_HEALTH);
		this.setHealth(MAX_HEALTH);
	}

	@Override
	public double getHealth()
	{
		return health;
	}

	@Override
	public boolean isDead()
	{
		return !alive;
	}

	@Override
	public void setHealth(double health)
	{
		if (health > 0)
		{
			this.health = Math.min(health, getMaxHealth());
			return;
		}

		this.health = 0;

		EntityDeathEvent event = new EntityDeathEvent(this, new ArrayList<>(), 0);
		Bukkit.getPluginManager().callEvent(event);

		alive = false;
	}

	@Override
	public double getMaxHealth()
	{
		return getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	}

	@Override
	public void setMaxHealth(double health)
	{
		getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
		if (this.health > health)
		{
			this.health = health;
		}
	}

	@Override
	public void resetMaxHealth()
	{
		setMaxHealth(maxHealth);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void damage(double amount)
	{
		damage(amount, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void damage(double amount, Entity source)
	{
		Map<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
		modifiers.put(EntityDamageEvent.DamageModifier.BASE, 1.0);
		Map<EntityDamageEvent.DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(
		    EntityDamageEvent.DamageModifier.class);
		modifierFunctions.put(EntityDamageEvent.DamageModifier.BASE, damage -> damage);

		EntityDamageEvent event = source != null ?
		                          new EntityDamageByEntityEvent(source, this,
		                                  EntityDamageEvent.DamageCause.ENTITY_ATTACK, modifiers, modifierFunctions)
		                          :
		                          new EntityDamageEvent(this, EntityDamageEvent.DamageCause.CUSTOM, modifiers,
		                                  modifierFunctions)
		                          ;
		event.setDamage(amount);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			amount = event.getDamage();
			setHealth(health - amount);
		}
	}

	@Override
	public AttributeInstance getAttribute(Attribute attribute)
	{
		if (attributes.containsKey(attribute))
			return attributes.get(attribute);
		else
			throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectile)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity)
	{
		T entity = launchProjectile(projectile);
		entity.setVelocity(velocity);
		return entity;
	}

	@Override
	public double getEyeHeight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getEyeHeight(boolean ignorePose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Location getEyeLocation()
	{
		return getLocation().add(0, getEyeHeight(), 0);
	}

	@Override
	public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Block getTargetBlock(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Block getTargetBlockExact(int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Block getTargetBlockExact(int maxDistance, FluidCollisionMode fluidCollisionMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceBlocks(double maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTraceBlocks(double maxDistance, FluidCollisionMode fluidCollisionMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getRemainingAir()
	{
		return remainingAirTicks;
	}

	@Override
	public void setRemainingAir(int ticks)
	{
		this.remainingAirTicks = ticks;
	}

	@Override
	public int getMaximumAir()
	{
		return maxAirTicks;
	}

	@Override
	public void setMaximumAir(int ticks)
	{
		this.maxAirTicks = ticks;
	}

	@Override
	public int getMaximumNoDamageTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setMaximumNoDamageTicks(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getLastDamage()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLastDamage(double damage)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getNoDamageTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setNoDamageTicks(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Player getKiller()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPotionEffect(PotionEffect effect)
	{
		return addPotionEffect(effect, false);
	}

	@Override
	@Deprecated
	public boolean addPotionEffect(PotionEffect effect, boolean force)
	{
		if (effect != null)
		{
			// Bukkit now allows multiple effects of the same type,
			// the force/success attributes are now obsolete
			activeEffects.add(new ActivePotionEffect(effect));
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> effects)
	{
		boolean successful = true;

		for (PotionEffect effect : effects)
		{
			if (!addPotionEffect(effect))
			{
				successful = false;
			}
		}

		return successful;
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType type)
	{
		return getPotionEffect(type) != null;
	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType type)
	{
		for (PotionEffect effect : getActivePotionEffects())
		{
			if (effect.getType().equals(type))
			{
				return effect;
			}
		}

		return null;
	}

	@Override
	public void removePotionEffect(PotionEffectType type)
	{

		activeEffects.removeIf(effect -> effect.hasExpired() || effect.getPotionEffect().getType().equals(type));
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects()
	{
		Set<PotionEffect> effects = new HashSet<>();
		Iterator<ActivePotionEffect> iterator = activeEffects.iterator();

		while (iterator.hasNext())
		{
			ActivePotionEffect effect = iterator.next();

			if (effect.hasExpired())
			{
				iterator.remove();
			}
			else
			{
				effects.add(effect.getPotionEffect());
			}
		}

		return effects;
	}

	@Override
	public boolean hasLineOfSight(Entity other)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getRemoveWhenFarAway()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRemoveWhenFarAway(boolean remove)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanPickupItems(boolean pickup)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getCanPickupItems()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isLeashed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getLeashHolder() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setLeashHolder(Entity holder)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isGliding()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGliding(boolean gliding)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSwimming()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSwimming(boolean swimming)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isRiptiding()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAI(boolean ai)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasAI()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCollidable(boolean collidable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isCollidable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EntityCategory getCategory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArrowsInBody(int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getArrowsInBody()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArrowCooldown(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getArrowCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setInvisible(boolean invisible)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInvisible()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
