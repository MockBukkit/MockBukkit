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
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
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
		attributes.put(Attribute.GENERIC_MAX_HEALTH,
				new AttributeInstanceMock(Attribute.GENERIC_MAX_HEALTH, MAX_HEALTH));
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
	 * 
	 * @param expectedGamemode The gamemode the player should be in.
	 */
	public void assertGameMode(GameMode expectedGamemode)
	{
		assertEquals(expectedGamemode, gamemode);
	}
	
	/**
	 * Simulates the player damaging a block just like {@link simulateBlockDamage}.
	 * However, if {@code InstaBreak} is enabled, it will not automatically fire
	 * a {@link BlockBreakEvent}.
	 * It will also still fire a {@link BlockDamageEvent} even if the player is not
	 * in survival mode.
	 * 
	 * @param block The block to damage.
	 * @return The event that has been fired.
	 */
	protected BlockDamageEvent simulateBlockDamagePure(Block block)
	{
		BlockDamageEvent event = new BlockDamageEvent(this, block, getItemInHand(), false);
		Bukkit.getPluginManager().callEvent(event);
		return event;
	}

	/**
	 * Simulates the player damaging a block. Note that this method does not
	 * anything unless the player is in survival mode.
	 * If {@code InstaBreak} is set to true by an event handler, a 
	 * {@link BlockBreakEvent} is immediately fired.
	 * The result will then still be whether or not the {@link BlockDamageEvent} was cancelled
	 * or not, not the later {@link BlockBreakEvent}.
	 * 
	 * @param block The block to damage.
	 * @return {@code true} if the block was damaged, {@code false} if the event
	 *         was cancelled or the player was not in survival gamemode.
	 */
	public boolean simulateBlockDamage(Block block)
	{
		if (gamemode == GameMode.SURVIVAL)
		{
			BlockDamageEvent event = simulateBlockDamagePure(block);
			if (event.getInstaBreak())
			{
				BlockBreakEvent breakEvent = new BlockBreakEvent(block, this);
				Bukkit.getPluginManager().callEvent(breakEvent);
				if (!breakEvent.isCancelled())
					block.setType(Material.AIR);
			}
			
			return !event.isCancelled();
		}
		else
		{
			return false;
		}
	}

	/**
	 * Simulates the player breaking a block. This method will not break the
	 * block if the player is in adventure or spectator mode.
	 * If the player is in survival mode, the player will first damage the block.
	 * 
	 * @param block The block to break.
	 * @return {@code true} if the block was broken, {@code false} if it wasn't
	 *         or if the player was in adventure mode or in spectator mode.
	 */
	public boolean simulateBlockBreak(Block block)
	{
		if ((gamemode == GameMode.SPECTATOR || gamemode == GameMode.ADVENTURE)
				|| (gamemode == GameMode.SURVIVAL && simulateBlockDamagePure(block).isCancelled()))
			return false;

		BlockBreakEvent event = new BlockBreakEvent(block, this);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			block.setType(Material.AIR);
		return !event.isCancelled();
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

		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(source, this, DamageCause.ENTITY_ATTACK,
				modifiers, modifierFunctions);
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
		return null;
	}

	@Override
	public MainHand getMainHand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setWindowProperty(Property prop, int value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InventoryView openWorkbench(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryView openEnchanting(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryView openMerchant(Villager trader, boolean force)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryView openMerchant(Merchant merchant, boolean force)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getItemInHand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemInHand(ItemStack item)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack getItemOnCursor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItemOnCursor(ItemStack item)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasCooldown(Material material)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCooldown(Material material)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCooldown(Material material, int ticks)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSleeping()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSleepTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBlocking()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHandRaised()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getExpToLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entity getShoulderEntityLeft()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShoulderEntityLeft(Entity entity)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Entity getShoulderEntityRight()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShoulderEntityRight(Entity entity)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public double getEyeHeight()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight(boolean ignorePose)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getEyeLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getTargetBlock(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemainingAir()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRemainingAir(int ticks)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaximumAir()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaximumAir(int ticks)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaximumNoDamageTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaximumNoDamageTicks(int ticks)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public double getLastDamage()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastDamage(double damage)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getNoDamageTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNoDamageTicks(int ticks)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Player getKiller()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPotionEffect(PotionEffect effect)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffect(PotionEffect effect, boolean force)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> effects)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType type)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PotionEffect getPotionEffect(PotionEffectType type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePotionEffect(PotionEffectType type)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasLineOfSight(Entity other)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getRemoveWhenFarAway()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRemoveWhenFarAway(boolean remove)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public EntityEquipment getEquipment()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCanPickupItems(boolean pickup)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getCanPickupItems()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeashed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entity getLeashHolder() throws IllegalStateException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setLeashHolder(Entity holder)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGliding()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGliding(boolean gliding)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setAI(boolean ai)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasAI()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCollidable(boolean collidable)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCollidable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectile)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConversing()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptConversationInput(String input)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beginConversation(Conversation conversation)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void abandonConversation(Conversation conversation)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public long getFirstPlayed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastPlayed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasPlayedBefore()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPluginMessage(Plugin source, String channel, byte[] message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getListeningPluginChannels()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplayName(String name)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getPlayerListName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayerListName(String name)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompassTarget(Location loc)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Location getCompassTarget()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress getAddress()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendRawMessage(String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void kickPlayer(String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void chat(String msg)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSneaking()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSneaking(boolean sneak)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSprinting()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSprinting(boolean sprinting)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void loadData()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSleepingIgnored(boolean isSleeping)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSleepingIgnored()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playNote(Location loc, byte instrument, byte note)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playNote(Location loc, Instrument instrument, Note note)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(Location location, String sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void stopSound(Sound sound)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void stopSound(String sound)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void stopSound(Sound sound, SoundCategory category)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void stopSound(String sound, SoundCategory category)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playEffect(Location loc, Effect effect, int data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void playEffect(Location loc, Effect effect, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBlockChange(Location loc, Material material, byte data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendBlockChange(Location loc, int material, byte data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendSignChange(Location loc, String[] lines) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMap(MapView map)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInventory()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void awardAchievement(Achievement achievement)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAchievement(Achievement achievement)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasAchievement(Achievement achievement)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void incrementStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatistic(Statistic statistic, int newValue) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getStatistic(Statistic statistic) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatistic(Statistic statistic, Material material, int newValue) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void incrementStatistic(Statistic statistic, EntityType entityType, int amount)
			throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void decrementStatistic(Statistic statistic, EntityType entityType, int amount)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatistic(Statistic statistic, EntityType entityType, int newValue)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerTime(long time, boolean relative)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public long getPlayerTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getPlayerTimeOffset()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPlayerTimeRelative()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPlayerTime()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerWeather(WeatherType type)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public WeatherType getPlayerWeather()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetPlayerWeather()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void giveExp(int amount)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void giveExpLevels(int amount)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public float getExp()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setExp(float exp)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLevel(int level)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getTotalExperience()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTotalExperience(int exp)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public float getExhaustion()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setExhaustion(float value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public float getSaturation()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSaturation(float value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getFoodLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFoodLevel(int value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Location getBedSpawnLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBedSpawnLocation(Location location)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBedSpawnLocation(Location location, boolean force)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getAllowFlight()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAllowFlight(boolean flight)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hidePlayer(Player player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hidePlayer(Plugin plugin, Player player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showPlayer(Player player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showPlayer(Plugin plugin, Player player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canSee(Player player)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFlying()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFlying(boolean value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlySpeed(float value) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setWalkSpeed(float value) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public float getFlySpeed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWalkSpeed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTexturePack(String url)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setResourcePack(String url)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setResourcePack(String url, byte[] hash)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Scoreboard getScoreboard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScoreboard(Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isHealthScaled()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHealthScaled(boolean scale)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setHealthScale(double scale) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public double getHealthScale()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entity getSpectatorTarget()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSpectatorTarget(Entity entity)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTitle(String title, String subtitle)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetTitle()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public AdvancementProgress getAdvancementProgress(Advancement advancement)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocale()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
