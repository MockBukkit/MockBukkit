package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.google.common.base.Preconditions;
import net.kyori.adventure.util.TriState;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
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
import org.mockbukkit.mockbukkit.AsyncCatcher;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.attribute.AttributeInstanceMock;
import org.mockbukkit.mockbukkit.attribute.AttributesMock;
import org.mockbukkit.mockbukkit.entity.ai.BrainMock;
import org.mockbukkit.mockbukkit.entity.data.EntityState;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.EntityEquipmentMock;
import org.mockbukkit.mockbukkit.potion.ActivePotionEffect;
import org.mockbukkit.mockbukkit.simulate.entity.LivingEntitySimulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	private final BrainMock brain = new BrainMock();
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
	private boolean riptiding = false;

	/**
	 * The attributes this entity has.
	 */
	protected Map<Attribute, AttributeInstanceMock> attributes;
	private final EntityEquipment equipment = new EntityEquipmentMock(this);
	private final Set<UUID> collidableExemptions = new HashSet<>();
	private boolean collidable = true;
	private boolean ai = true;
	private boolean swimming;
	private boolean sleeping;
	private boolean climbing;
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

		attributes = new HashMap<>();
		double maxHealth = AttributesMock.getDefaultValue(Attribute.MAX_HEALTH);
		attributes.put(Attribute.MAX_HEALTH, new AttributeInstanceMock(Attribute.MAX_HEALTH, maxHealth));
		double movementSpeed = AttributesMock.getDefaultValue(Attribute.MOVEMENT_SPEED);
		attributes.put(Attribute.MOVEMENT_SPEED, new AttributeInstanceMock(Attribute.MOVEMENT_SPEED, movementSpeed));
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

		EntityDeathEvent event = new EntityDeathEvent(this, DamageSource.builder(DamageType.GENERIC).build(), new ArrayList<>());
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
		return getAttribute(Attribute.MAX_HEALTH).getValue();
	}

	@Override
	public void setMaxHealth(double health)
	{
		getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
		if (this.health > health)
		{
			this.health = health;
		}
	}

	@Override
	public void resetMaxHealth()
	{
		setMaxHealth(AttributesMock.getDefaultValue(Attribute.MAX_HEALTH));
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
		return new LivingEntitySimulation(this).simulateDamage(amount,source);
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
		return new LivingEntitySimulation(this).simulateDamage(amount, source);
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
		return this.launchProjectile(projectile, null);
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity)
	{
		return this.launchProjectile(projectile, velocity, null);
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity, @Nullable Consumer<? super T> function)
	{
		Preconditions.checkNotNull(projectile, "Projectile cannot be null");

		World world = getWorld();
		Preconditions.checkNotNull(world, "World cannot be null when launching a projectile");

		// The throwable spawn location is slightly lower than the eye level (See: ThrowableProjectile constructor)
		Location spawnLocation = getEyeLocation().subtract(0, 0.10000000149011612D, 0);
		T entity = world.createEntity(spawnLocation, projectile);
		Preconditions.checkNotNull(entity, "Projectile (%s) not supported", projectile.getName());

		if (velocity != null)
		{
			entity.setVelocity(velocity);
		}

		if (function != null)
		{
			function.accept(entity);
		}

		return entity;
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

	protected double getEyeHeight(EntityState pose)
	{
		return entityData.getEyeHeight(getSubType(), pose);
	}

	@Override
	public double getEyeHeight(boolean ignorePose)
	{
		if (ignorePose)
		{
			return getEyeHeight(EntityState.DEFAULT);
		}

		return getEyeHeight(getEntityState());
	}

	@Override
	public double getEyeHeight()
	{
		return getEyeHeight(false);
	}

	@Override
	protected EntityState getEntityState()
	{
		if (isSleeping())
		{
			return EntityState.SLEEPING;
		}

		if (isSneaking())
		{
			return EntityState.SNEAKING;
		}

		if (isSwimming())
		{
			return EntityState.SWIMMING;
		}

		return super.getEntityState();
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
		return addPotionEffect(effect, true); // force is ignored
	}

	@Override
	@Deprecated(since = "1.15")
	public boolean addPotionEffect(@NotNull PotionEffect effect, boolean force)
	{
		// Bukkit now allows multiple effects of the same type,
		// the force/success attributes are now obsolete
		addPotionEffect(effect, EntityPotionEffectEvent.Cause.PLUGIN);
		return true; // legacy behaviour always returned true
	}

	/**
	 * Adds a potion effect. If the event is canceled, no effect will be added.
	 *
	 * @param effect The Potion Effect to add.
	 * @param cause  The cause.
	 * @return The event containing details about adding the potion effect.
	 */
	public EntityPotionEffectEvent addPotionEffect(@NotNull PotionEffect effect, EntityPotionEffectEvent.Cause cause)
	{
		AsyncCatcher.catchOp("effect add");
		Preconditions.checkNotNull(effect, "PotionEffect cannot be null");

		/*
		Applying completely new effect -> old: null, new: new effect, action: add, override: false
		A single effects runs out (on time) (not possible via this method because @NotNull) -> old: effect that ran out, new: null, action: remove, override: true
		Applying a new effect but effect type already active -> old: existing effect, new: new effect, action: changed, override: true
		Clearing all effects (not possible via this method) -> old: effect that was cleared, new: null, action: clear, override: true

		 Notes:
		 If multiple effects are cleared, then for each one an EntityPotionEffectEvent is called.
		 */

		PotionEffect oldEffect = getPotionEffect(effect.getType());
		EntityPotionEffectEvent.Action action = oldEffect == null ? EntityPotionEffectEvent.Action.ADDED : EntityPotionEffectEvent.Action.CHANGED;
		boolean override = oldEffect != null;


		EntityPotionEffectEvent event = new EntityPotionEffectEvent(this, oldEffect, effect, cause, action, override);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			activeEffects.add(new ActivePotionEffect(effect));
		}
		return event;
	}

	/**
	 * Adds multiple potion effects. If one event is canceled, the effect from that event won't be added.
	 *
	 * @param effects The Potion Effects to add.
	 * @param cause The cause.
	 * @return A list of events containing details about adding the potion effects.
	 */
	public List<EntityPotionEffectEvent> addPotionEffects(@NotNull Collection<PotionEffect> effects, EntityPotionEffectEvent.Cause cause)
	{
		Preconditions.checkNotNull(effects, "PotionEffect cannot be null");
		return effects.stream().map(potionEffect -> addPotionEffect(potionEffect, cause)).toList();
	}

	@Override
	public boolean addPotionEffects(@NotNull Collection<PotionEffect> effects)
	{
		addPotionEffects(effects, EntityPotionEffectEvent.Cause.PLUGIN);
		return true; // legacy behaviour always returned true
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
		return this.riptiding;
	}

	@Override
	public void setRiptiding(boolean isRiptiding)
	{
		this.riptiding = isRiptiding;
	}

	/**
	 * Set whether this entity is slumbering.
	 *
	 * @param sleeping If this entity is slumbering
	 */
	public void setSleeping(boolean sleeping)
	{
		this.sleeping = sleeping;
	}

	@Override
	public boolean isSleeping()
	{
		return this.sleeping;
	}

	/**
	 * Set whether this entity is climbing.
	 *
	 * @param climbing If this entity is climbing
	 */
	public void setClimbing(boolean climbing)
	{
		this.climbing = climbing;
	}

	@Override
	public boolean isClimbing()
	{
		return this.climbing;
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
		return brain.getMemory(memoryKey).orElse(null);
	}

	@Override
	public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue)
	{
		brain.setMemory(memoryKey, memoryValue);
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
	public void setBodyYaw(float bodyYaw)
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
	public void setArrowsInBody(int count, boolean fireEvent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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

	@Override
	public void heal(double amount, @NotNull EntityRegainHealthEvent.RegainReason regainReason)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canUseEquipmentSlot(@NotNull EquipmentSlot equipmentSlot)
	{
		throw new UnimplementedOperationException();
	}

}
