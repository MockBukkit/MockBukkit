package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.AsyncCatcher;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.attribute.AttributeInstanceMock;
import be.seeseemelk.mockbukkit.attribute.AttributesMock;
import be.seeseemelk.mockbukkit.inventory.EntityEquipmentMock;
import be.seeseemelk.mockbukkit.potion.ActivePotionEffect;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import net.kyori.adventure.util.TriState;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wither;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Mock implementation of a {@link LivingEntity}.
 *
 * @see EntityMock
 */
public abstract class LivingEntityMock extends EntityMock implements LivingEntity
{

	/**
	 * How much health the entity has.
	 */
	protected double health;
	private int maxAirTicks = 300;
	private int remainingAirTicks = 300;
	/**
	 * NoDamage ticks
	 */
	private int noDamageTicks = 0;
	private int maxNoDamageTicks = 20;
	/**
	 * Whether the entity is alive.
	 */
	protected boolean alive = true;
	private boolean gliding = false;
	private boolean jumping = false;

	/**
	 * The attributes this entity has.
	 */
	protected Map<Attribute, AttributeInstanceMock> attributes;
	private final EntityEquipment equipment = new EntityEquipmentMock(this);
	private final Set<UUID> collidableExemptions = new HashSet<>();
	private boolean collidable = true;
	private boolean ai = true;
	private boolean swimming;
	private double absorptionAmount;
	private int arrowCooldown;
	private int arrowsInBody;
	private @Nullable Player killer;

	private final Set<ActivePotionEffect> activeEffects = new HashSet<>();
	private TriState frictionState = TriState.NOT_SET;
	private Entity leashHolder;

	/**
	 * Constructs a new {@link LivingEntityMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected LivingEntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);

		attributes = new EnumMap<>(Attribute.class);
		double maxHealth = AttributesMock.getDefaultValue(Attribute.GENERIC_MAX_HEALTH);
		attributes.put(Attribute.GENERIC_MAX_HEALTH,
				new AttributeInstanceMock(Attribute.GENERIC_MAX_HEALTH, maxHealth));
		double movementSpeed = AttributesMock.getDefaultValue(Attribute.GENERIC_MOVEMENT_SPEED);
		attributes.put(Attribute.GENERIC_MOVEMENT_SPEED,
				new AttributeInstanceMock(Attribute.GENERIC_MOVEMENT_SPEED, movementSpeed));
		resetMaxHealth();
		setHealth(maxHealth);
	}

	@Override
	public double getHealth()
	{
		return health;
	}

	@Override
	public void remove()
	{
		this.health = 0;
		this.alive = false;
		super.remove();
	}

	@Override
	public boolean isDead()
	{
		return !this.alive || !super.isValid();
	}

	@Override
	public boolean isValid()
	{
		return !isDead();
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

		this.alive = false;
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
		setMaxHealth(AttributesMock.getDefaultValue(Attribute.GENERIC_MAX_HEALTH));
	}

	@Override
	public void damage(double amount)
	{
		damage(amount, (Entity) null);
	}

	@Override
	public void damage(double amount, @Nullable Entity source)
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
		setHealth(health - amount);
	}

	@Override
	public void damage(double amount, @NotNull DamageSource source)
	{
		throw new UnimplementedOperationException();
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
		Map<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
		modifiers.put(EntityDamageEvent.DamageModifier.BASE, 1.0);
		Map<EntityDamageEvent.DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(
				EntityDamageEvent.DamageModifier.class);
		modifierFunctions.put(EntityDamageEvent.DamageModifier.BASE, damage -> damage);

		EntityDamageEvent event;
		if (source.getDirectEntity() != null)
		{
			event = new EntityDamageByEntityEvent(source.getDirectEntity(), this,
					EntityDamageEvent.DamageCause.ENTITY_ATTACK, source, amount);
		}
		else
		{
			event = new EntityDamageEvent(this, EntityDamageEvent.DamageCause.CUSTOM, source, amount);
		}
		if (event.callEvent())
		{
			setLastDamageCause(event);
			amount = event.getDamage();
			this.damage(amount);
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
		Preconditions.checkNotNull(attribute, "Attribute cannot be null");
		this.attributes.put(attribute, new AttributeInstanceMock(attribute, AttributesMock.getDefaultValue(attribute)));
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile,
			@Nullable Vector velocity)
	{
		Preconditions.checkNotNull(projectile, "Projectile cannot be null");
		T entity = launchProjectile(projectile);
		if (velocity != null)
		{
			entity.setVelocity(velocity);
		}
		return entity;
	}

	@Override
	public double getEyeHeight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void startUsingItem(@NotNull EquipmentSlot hand)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack getItemInUse()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getItemInUseTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemInUseTicks(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void completeUsingActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getActiveItemRemainingTime()
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
	public void setActiveItemRemainingTime(@Range(from = 0L, to = 2147483647L) int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getActiveItemUsedTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EquipmentSlot getActiveItemHand()
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
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BlockFace getTargetBlockFace(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable TargetBlockInfo getTargetBlockInfo(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Entity getTargetEntity(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable TargetEntityInfo getTargetEntityInfo(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Block getTargetBlockExact(int maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Block getTargetBlockExact(int maxDistance, @NotNull FluidCollisionMode fluidCollisionMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(double maxDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode)
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
		return this.maxNoDamageTicks;
	}

	@Override
	public void setMaximumNoDamageTicks(int ticks)
	{
		this.maxNoDamageTicks = ticks;
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
		return this.noDamageTicks;
	}

	@Override
	public void setNoDamageTicks(int ticks)
	{
		this.noDamageTicks = ticks;
	}

	@Override
	public int getNoActionTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setNoActionTicks(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public @Nullable Player getKiller()
	{
		return this.killer;
	}

	@Override
	public void setKiller(@Nullable Player killer)
	{
		this.killer = killer;
	}

	@Override
	public boolean addPotionEffect(@NotNull PotionEffect effect)
	{
		return addPotionEffect(effect, false);
	}

	@Override
	@Deprecated(since = "1.15")
	public boolean addPotionEffect(@NotNull PotionEffect effect, boolean force)
	{
		AsyncCatcher.catchOp("effect add");
		Preconditions.checkNotNull(effect, "PotionEffect cannot be null");
		// Bukkit now allows multiple effects of the same type,
		// the force/success attributes are now obsolete
		activeEffects.add(new ActivePotionEffect(effect));
		return true;
	}

	@Override
	public boolean addPotionEffects(@NotNull Collection<PotionEffect> effects)
	{
		Preconditions.checkNotNull(effects, "PotionEffect cannot be null");

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
		Preconditions.checkNotNull(type, "Potion type cannot be null");
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
		Preconditions.checkNotNull(type, "Potion type cannot be null");
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
	public boolean clearActivePotionEffects()
	{
		final boolean res = !activeEffects.isEmpty();
		activeEffects.clear();
		return res;
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

	@Nullable
	@Override
	public EntityEquipment getEquipment()
	{
		return this.equipment;
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
	public boolean teleport(@NotNull Location location, PlayerTeleportEvent.@NotNull TeleportCause cause)
	{
		if (isDead())
		{
			return false;
		}
		return super.teleport(location, cause);
	}

	@Override
	public boolean isLeashed()
	{
		if (!(this.leashHolder instanceof Mob))
		{
			return false;
		}
		return this.leashHolder != null;
	}

	@Override
	public @NotNull Entity getLeashHolder() throws IllegalStateException
	{
		if (!this.isLeashed())
		{
			throw new IllegalStateException("Entity is currently not leashed");
		}
		return this.leashHolder;
	}

	@Override
	public boolean setLeashHolder(Entity holder)
	{
		if (this instanceof Wither || !(this instanceof Mob))
		{
			return false;
		}

		if (holder != null && holder.isDead())
		{
			return false;
		}
		this.leashHolder = holder;
		return true;
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
		if (this.isValid() && this.isSwimming() != swimming)
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
		Preconditions.checkNotNull(target, "Target cannot be null");

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
	public void playHurtAnimation(float yaw)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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

	@Override
	public @Nullable Sound getHurtSound()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Sound getDeathSound()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Sound getFallDamageSound(int fallHeight)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Sound getFallDamageSoundSmall()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Sound getFallDamageSoundBig()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Sound getDrinkingSound(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Sound getEatingSound(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canBreatheUnderwater()
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
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBeeStingerCooldown(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBeeStingersInBody()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBeeStingersInBody(int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setNextArrowRemoval(@Range(from = 0L, to = 2147483647L) int i)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getNextArrowRemoval()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setNextBeeStingerRemoval(@Range(from = 0L, to = 2147483647L) int i)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getNextBeeStingerRemoval()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public int getArrowsStuck()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArrowsStuck(int arrows)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getShieldBlockingDelay()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShieldBlockingDelay(int delay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack getActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getItemUseRemainingTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHandRaisedTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EquipmentSlot getHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isJumping()
	{
		return this.jumping;
	}

	@Override
	public void setJumping(boolean jumping)
	{
		this.jumping = jumping;
	}

	@Override
	public void playPickupItemAnimation(@NotNull Item item, int quantity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getHurtDirection()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHurtDirection(float hurtDirection)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile,
			@Nullable Vector velocity, @Nullable Consumer<? super T> function)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void knockback(double strength, double directionX, double directionZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void broadcastSlotBreak(@NotNull EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void broadcastSlotBreak(@NotNull EquipmentSlot slot, @NotNull Collection<Player> players)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack damageItemStack(@NotNull ItemStack stack, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void damageItemStack(@NotNull EquipmentSlot slot, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull TriState getFrictionState()
	{
		return this.frictionState;
	}

	@Override
	public void setFrictionState(@NotNull TriState state)
	{
		Preconditions.checkNotNull(state, "State cannot be null");
		this.frictionState = state;
	}

	@Override
	public @Nullable BlockFace getTargetBlockFace(int maxDistance, @NotNull FluidCollisionMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable RayTraceResult rayTraceEntities(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArrowsInBody(int count, boolean fireEvent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getBodyYaw()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBodyYaw(float bodyYaw)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getForwardsMovement()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getUpwardsMovement()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getSidewaysMovement()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
