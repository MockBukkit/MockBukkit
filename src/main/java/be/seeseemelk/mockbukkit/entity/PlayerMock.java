package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.google.common.base.Charsets;
import com.google.common.base.Function;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.attribute.AttributeInstanceMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;


@SuppressWarnings("deprecation")
public class PlayerMock extends EntityMock implements Player
{
	private static final double MAX_HEALTH = 20.0;
	private boolean online;
	private PlayerInventoryMock inventory = null;
	private GameMode gamemode = GameMode.SURVIVAL;
	private double maxHealth = MAX_HEALTH;
	private double health = 20.0;
	private boolean whitelisted = true;
	private Map<Attribute, AttributeInstanceMock> attributes;
	private InventoryView inventoryView;
	
	{
		attributes = new EnumMap<>(Attribute.class);
		attributes.put(Attribute.GENERIC_MAX_HEALTH, new AttributeInstanceMock(Attribute.GENERIC_MAX_HEALTH, MAX_HEALTH));
	}

	public PlayerMock(String name)
	{
		this(name, UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)));
		this.online = false;
	}

	public PlayerMock(String name, UUID uuid)
	{
		super(uuid);
		setName(name);
		this.online = true;

		if (Bukkit.getWorlds().size() == 0)
		{
			MockBukkit.getMock().addSimpleWorld("world");
		}

		setLocation(Bukkit.getWorlds().get(0).getSpawnLocation().clone());
		closeInventory();
	}
	
	/**
	 * Assert that the player is in a specific gamemode.
	 * @param expectedGamemode The gamemode the player should be in.
	 */
	public void assertGameMode(GameMode expectedGamemode)
	{
		assertEquals(expectedGamemode, gamemode);
	}

	@Override
	public PlayerInventory getInventory()
	{
		if (inventory == null)
		{
			inventory = (PlayerInventoryMock) Bukkit.createInventory(this, InventoryType.PLAYER);
		}
		return inventory;
	}
	
	@Override
	public GameMode getGameMode()
	{
		return gamemode;
	}

	@Override
	public void setGameMode(GameMode mode)
	{
		gamemode = mode;
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
			PlayerDeathEvent event = new PlayerDeathEvent(this, new ArrayList<>(), 0, getName() + " got killed");
			Bukkit.getPluginManager().callEvent(event);
		}
		else if (health > maxHealth)
		{
			this.health = maxHealth;
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
		setMaxHealth(MAX_HEALTH);
	}

	@Override
	public void damage(double amount)
	{
		Map<DamageModifier, Double> modifiers = new EnumMap<>(DamageModifier.class);
		modifiers.put(DamageModifier.BASE, 1.0);
		Map<DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(DamageModifier.class);
		modifierFunctions.put(DamageModifier.BASE, damage -> damage);
		
		EntityDamageEvent event = new EntityDamageEvent(this, DamageCause.CUSTOM, modifiers, modifierFunctions);
		event.setDamage(amount);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			setHealth(health - amount);
		}
	}

	@Override
	public void damage(double amount, Entity source)
	{
		Map<DamageModifier, Double> modifiers = new EnumMap<>(DamageModifier.class);
		modifiers.put(DamageModifier.BASE, 1.0);
		Map<DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(DamageModifier.class);
		modifierFunctions.put(DamageModifier.BASE, damage -> damage);
		
		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(source, this, DamageCause.ENTITY_ATTACK, modifiers, modifierFunctions);
		event.setDamage(amount);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			setHealth(health - amount);
		}
	}
	
	@Override
	public boolean isWhitelisted()
	{
		return this.whitelisted;
	}

	@Override
	public void setWhitelisted(boolean value)
	{
		this.whitelisted = value;
	}

	@Override
	public Player getPlayer()
	{
		if (online)
		{
			return this;
		}
		return null;
	}
	
	@Override
	public boolean isOnline()
	{
		return this.online;
	}

	@Override
	public boolean isBanned()
	{
		return MockBukkit.getMock().getBanList(BanList.Type.NAME).isBanned(getName());
	}
	
	@Override
	public AttributeInstance getAttribute(Attribute attribute)
	{
		if (attributes.containsKey(attribute))
		{
			return attributes.get(attribute);
		}
		else
		{
			throw new UnimplementedOperationException();
		}
	}
	
	@Override
	public InventoryView getOpenInventory()
	{
		return inventoryView;
	}
	
	@Override
	public void openInventory(InventoryView inventory)
	{
		inventoryView = inventory;
	}
	
	@Override
	public InventoryView openInventory(Inventory inventory)
	{
		inventoryView = new PlayerInventoryViewMock(this, inventory);
		return inventoryView;
	}
	
	@Override
	public void closeInventory()
	{
		inventoryView = new SimpleInventoryViewMock(this, null, inventory, InventoryType.CRAFTING); 
	}
	
	@Override
	public boolean performCommand(String command)
	{
		return Bukkit.dispatchCommand(this, command);
	}

	@Override
	public Inventory getEnderChest()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public MainHand getMainHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setWindowProperty(Property prop, int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openWorkbench(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openEnchanting(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openMerchant(Villager trader, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InventoryView openMerchant(Merchant merchant, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack getItemInHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemInHand(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack getItemOnCursor()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItemOnCursor(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCooldown(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getCooldown(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCooldown(Material material, int ticks)
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
	public int getSleepTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBlocking()
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
	public int getExpToLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getShoulderEntityLeft()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShoulderEntityLeft(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getShoulderEntityRight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShoulderEntityRight(Entity entity)
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
	public double getEyeHeight(boolean ignoreSneaking)
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
	public void setVelocity(Vector velocity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Vector getVelocity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getHeight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getWidth()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOnGround()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<Entity> getNearbyEntities(double x, double y, double z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getFireTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxFireTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFireTicks(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void remove()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isDead()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isValid()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Server getServer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getPassenger()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setPassenger(Entity passenger)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<Entity> getPassengers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPassenger(Entity passenger)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removePassenger(Entity passenger)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean eject()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getFallDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFallDistance(float distance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLastDamageCause(EntityDamageEvent event)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTicksLived()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTicksLived(int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playEffect(EntityEffect type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EntityType getType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInsideVehicle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean leaveVehicle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getVehicle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomNameVisible(boolean flag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isCustomNameVisible()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGlowing(boolean flag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isGlowing()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setInvulnerable(boolean flag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInvulnerable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSilent()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSilent(boolean flag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasGravity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGravity(boolean gravity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getPortalCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPortalCooldown(int cooldown)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<String> getScoreboardTags()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addScoreboardTag(String tag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeScoreboardTag(String tag)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPermissionSet(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPermissionSet(Permission perm)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPermission(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPermission(Permission perm)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void recalculatePermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getCustomName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomName(String name)
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
	public boolean isConversing()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void acceptConversationInput(String input)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean beginConversation(Conversation conversation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void abandonConversation(Conversation conversation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getFirstPlayed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastPlayed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayedBefore()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendPluginMessage(Plugin source, String channel, byte[] message)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<String> getListeningPluginChannels()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setDisplayName(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getPlayerListName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPlayerListName(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCompassTarget(Location loc)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Location getCompassTarget()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public InetSocketAddress getAddress()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendRawMessage(String message)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void kickPlayer(String message)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void chat(String msg)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSneaking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSneaking(boolean sneak)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSprinting()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSprinting(boolean sprinting)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void saveData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void loadData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSleepingIgnored(boolean isSleeping)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSleepingIgnored()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playNote(Location loc, byte instrument, byte note)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playNote(Location loc, Instrument instrument, Note note)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, String sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopSound(Sound sound)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopSound(String sound)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopSound(Sound sound, SoundCategory category)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopSound(String sound, SoundCategory category)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playEffect(Location loc, Effect effect, int data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void playEffect(Location loc, Effect effect, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendBlockChange(Location loc, Material material, byte data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendBlockChange(Location loc, int material, byte data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendSignChange(Location loc, String[] lines) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendMap(MapView map)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void updateInventory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void awardAchievement(Achievement achievement)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeAchievement(Achievement achievement)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasAchievement(Achievement achievement)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(Statistic statistic, int newValue) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(Statistic statistic, Material material, int newValue) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, EntityType entityType, int amount)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void decrementStatistic(Statistic statistic, EntityType entityType, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setStatistic(Statistic statistic, EntityType entityType, int newValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setPlayerTime(long time, boolean relative)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public long getPlayerTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getPlayerTimeOffset()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPlayerTimeRelative()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void resetPlayerTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setPlayerWeather(WeatherType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public WeatherType getPlayerWeather()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void resetPlayerWeather()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void giveExp(int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void giveExpLevels(int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public float getExp()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setExp(float exp)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLevel(int level)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getTotalExperience()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTotalExperience(int exp)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public float getExhaustion()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setExhaustion(float value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public float getSaturation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSaturation(float value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getFoodLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFoodLevel(int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public Location getBedSpawnLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBedSpawnLocation(Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setBedSpawnLocation(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean getAllowFlight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAllowFlight(boolean flight)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void hidePlayer(Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void showPlayer(Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean canSee(Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFlying()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFlying(boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setFlySpeed(float value) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setWalkSpeed(float value) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public float getFlySpeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getWalkSpeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTexturePack(String url)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setResourcePack(String url)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setResourcePack(String url, byte[] hash)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public Scoreboard getScoreboard()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setScoreboard(Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isHealthScaled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHealthScaled(boolean scale)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void setHealthScale(double scale) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public double getHealthScale()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getSpectatorTarget()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSpectatorTarget(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void sendTitle(String title, String subtitle)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void resetTitle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public AdvancementProgress getAdvancementProgress(Advancement advancement)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getLocale()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void hidePlayer(Plugin plugin, Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void showPlayer(Plugin plugin, Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
