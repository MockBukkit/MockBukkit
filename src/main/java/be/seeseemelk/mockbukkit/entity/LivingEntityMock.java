package be.seeseemelk.mockbukkit.entity;

import java.util.*;

import be.seeseemelk.mockbukkit.attribute.AttributeInstanceMock;
import com.google.common.base.Function;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public abstract class LivingEntityMock extends EntityMock implements LivingEntity
{
	private static final double MAX_HEALTH = 20.0;
	private double health;
	private double maxHealth = MAX_HEALTH;
	protected Map<Attribute, AttributeInstanceMock> attributes;

	public LivingEntityMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);

		attributes = new EnumMap<>(Attribute.class);
		attributes.put(Attribute.GENERIC_MAX_HEALTH,
				new AttributeInstanceMock(Attribute.GENERIC_MAX_HEALTH, 20));
		this.setMaxHealth(MAX_HEALTH);
		this.setHealth(MAX_HEALTH);
	}

	@Override
	public double getHealth()
	{
		return health;
	}

	@Override
	public void setHealth(double health)
	{
		if (health <= 0)
		{
			this.health = 0;
			Event event;
			if(this instanceof PlayerMock){
				event = new PlayerDeathEvent((Player) this, new ArrayList<>(), 0, getName() + " got killed");
			} else {
				event = new EntityDeathEvent(this, new ArrayList<>(), 0);
			}
			Bukkit.getPluginManager().callEvent(event);
		}
		else if (health > getMaxHealth())
		{
			this.health = getMaxHealth();
		}
		else
		{
			this.health = health;
		}
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

	@Override
	public void damage(double amount)
	{
		Map<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
		modifiers.put(EntityDamageEvent.DamageModifier.BASE, 1.0);
		Map<EntityDamageEvent.DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
		modifierFunctions.put(EntityDamageEvent.DamageModifier.BASE, damage -> damage);

		EntityDamageEvent event = new EntityDamageEvent(this, EntityDamageEvent.DamageCause.CUSTOM, modifiers, modifierFunctions);
		event.setDamage(amount);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			amount = event.getDamage();
			setHealth(health - amount);
		}
	}

	@Override
	public void damage(double amount, Entity source)
	{
		Map<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
		modifiers.put(EntityDamageEvent.DamageModifier.BASE, 1.0);
		Map<EntityDamageEvent.DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
		modifierFunctions.put(EntityDamageEvent.DamageModifier.BASE, damage -> damage);

		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(source, this, EntityDamageEvent.DamageCause.ENTITY_ATTACK,
				modifiers, modifierFunctions);
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
	public BoundingBox getBoundingBox()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPersistent()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPersistent(boolean persistent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockFace getFacing()
	{
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRemainingAir(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaximumAir()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setMaximumAir(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPotionEffect(PotionEffect effect, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> effects)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removePotionEffect(PotionEffectType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public EntityEquipment getEquipment()
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

}
