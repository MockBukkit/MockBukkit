package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.attribute.AttributeInstanceMock;
import be.seeseemelk.mockbukkit.potion.ActivePotionEffect;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class LivingEntityMock extends EntityMock implements LivingEntity
{

	private static final double MAX_HEALTH = 20.0;
	protected double health;
	private final double maxHealth = MAX_HEALTH;
	private int maxAirTicks = 300;
	private int remainingAirTicks = 300;
	protected boolean alive = true;
	private boolean gliding = false;
	protected Map<Attribute, AttributeInstanceMock> attributes;
	private final EntityEquipment equipment = new EntityEquipmentMock(this);
	private final Set<UUID> collidableExemptions = new HashSet<>();
	private boolean collidable = true;
	private boolean ai = true;
	private boolean swimming;
	private double absorptionAmount;
	private int arrowCooldown;
	private int arrowsInBody;

	private final Set<ActivePotionEffect> activeEffects = new HashSet<>();

	protected LivingEntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
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
	public double getAbsorptionAmount()
	{
		return absorptionAmount;
	}

	@Override
	public void setAbsorptionAmount(double amount)
	{
		Preconditions.checkArgument(amount >= 0 && Double.isFinite(amount), "amount < 0 or non-finite");
		this.absorptionAmount = amount;
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
		damage(amount, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void damage(double amount, Entity source)
	{
		if (isInvulnerable())
		{
			if (source instanceof HumanEntity)
			{
				if (((Player) source).getGameMode() != GameMode.CREATIVE)
				{
					return;
				}
			}
			else
			{
				return;
			}
		}
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
		                                  modifierFunctions);
		event.setDamage(amount);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			amount = event.getDamage();
			setHealth(health - amount);
		}
	}

	@Override
	public AttributeInstance getAttribute(@NotNull Attribute attribute)
	{
		if (attributes.containsKey(attribute))
			return attributes.get(attribute);
		else
			throw new UnimplementedOperationException();
	}

	@Override
	public void registerAttribute(@NotNull Attribute attribute)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, Vector velocity)
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
	public @NotNull Location getEyeLocation()
	{
		return getLocation().add(0, getEyeHeight(), 0);
	}

	@Override
	public @NotNull List<Block> getLineOfSight(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Block getTargetBlock(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Block getTargetBlock(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable BlockFace getTargetBlockFace(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable TargetBlockInfo getTargetBlockInfo(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable Entity getTargetEntity(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable TargetEntityInfo getTargetEntityInfo(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance)
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
	public Block getTargetBlockExact(int maxDistance, @NotNull FluidCollisionMode fluidCollisionMode)
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
	public RayTraceResult rayTraceBlocks(double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode)
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
	public void setKiller(@Nullable Player killer)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addPotionEffect(@NotNull PotionEffect effect)
	{
		return addPotionEffect(effect, false);
	}

	@Override
	public boolean addPotionEffect(@NotNull PotionEffect effect, boolean force)
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
	public boolean hasPotionEffect(@NotNull PotionEffectType type)
	{
		return getPotionEffect(type) != null;
	}

	@Override
	public PotionEffect getPotionEffect(@NotNull PotionEffectType type)
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
	public void removePotionEffect(@NotNull PotionEffectType type)
	{

		activeEffects.removeIf(effect -> effect.hasExpired() || effect.getPotionEffect().getType().equals(type));
	}

	@Override
	public @NotNull Collection<PotionEffect> getActivePotionEffects()
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
	public boolean hasLineOfSight(@NotNull Entity other)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasLineOfSight(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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

	@Nullable
	@Override
	public EntityEquipment getEquipment()
	{
		return equipment;
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
	public @NotNull Entity getLeashHolder() throws IllegalStateException
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
		return this.gliding;
	}

	@Override
	public void setGliding(boolean gliding)
	{
		this.gliding = gliding;
	}

	@Override
	public boolean isSwimming()
	{
		return this.swimming;
	}

	@Override
	public void setSwimming(boolean swimming)
	{
		if (/*this.isValid() &&*/ this.isSwimming() != swimming)
		{
			EntityToggleSwimEvent event = new EntityToggleSwimEvent(this, swimming);
			Bukkit.getPluginManager().callEvent(event);
			if (event.isCancelled())
			{
				return;
			}
		}
		this.swimming = swimming;
	}

	@Override
	public boolean isRiptiding()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSleeping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isClimbing()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAI(boolean ai)
	{
		if (this instanceof Mob)
		{
			this.ai = ai;
		}
	}

	@Override
	public boolean hasAI()
	{
		return this instanceof Mob && this.ai;
	}

	@Override
	public void attack(@NotNull Entity target)
	{
		Preconditions.checkArgument(target != null, "target == null");

		if (this instanceof Player)
		{
			((Player) this).attack(target);
		}
		else
		{
			// TODO Auto-generated method stub
			throw new UnimplementedOperationException();
		}
	}

	@Override
	public void swingMainHand()
	{
		// Pretend packet gets sent.
	}

	@Override
	public void swingOffHand()
	{
		// Pretend packet gets sent.
	}

	@Override
	public void setCollidable(boolean collidable)
	{
		this.collidable = collidable;
	}

	@Override
	public boolean isCollidable()
	{
		return this.collidable;
	}

	@NotNull
	@Override
	public Set<UUID> getCollidableExemptions()
	{
		return this.collidableExemptions;
	}

	@Nullable
	@Override
	public <T> T getMemory(@NotNull MemoryKey<T> memoryKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public EntityCategory getCategory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArrowsInBody(int count)
	{
		Preconditions.checkArgument(count >= 0, "New arrow amount must be >= 0");
		this.arrowsInBody = count;
	}

	@Override
	public int getBeeStingerCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBeeStingerCooldown(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getBeeStingersInBody()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBeeStingersInBody(int count)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getArrowsInBody()
	{
		return this.arrowsInBody;
	}

	@Override
	public void setArrowCooldown(int ticks)
	{
		this.arrowCooldown = ticks;
	}

	@Override
	public int getArrowCooldown()
	{
		return arrowCooldown;
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

	@Override
	public int getArrowsStuck()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setArrowsStuck(int arrows)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getShieldBlockingDelay()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setShieldBlockingDelay(int delay)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable ItemStack getActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getItemUseRemainingTime()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHandRaisedTime()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull EquipmentSlot getHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isJumping()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJumping(boolean jumping)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void playPickupItemAnimation(@NotNull Item item, int quantity)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public float getHurtDirection()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHurtDirection(float hurtDirection)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
