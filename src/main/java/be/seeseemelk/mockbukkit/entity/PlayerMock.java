package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings("deprecation")
public class PlayerMock extends LivingEntityMock implements Player
{
	private boolean online;
	private PlayerInventoryMock inventory = null;
	private EnderChestInventoryMock enderChest = null;
	private GameMode gamemode = GameMode.SURVIVAL;
	private String displayName = null;
	private int expTotal = 0;
	private float exp = 0;
	private int foodLevel = 20;
	private float saturation = 5.0F;
	private int expLevel = 0;
	private boolean sneaking = false;
	private boolean whitelisted = true;
	private InventoryView inventoryView;

	private Location compassTarget;
	private Location bedSpawnLocation;
	private ItemStack cursor = null;
	private long firstPlayed = 0;
	private long lastPlayed = 0;

	private final List<AudioExperience> heardSounds = new LinkedList<>();

	private PlayerSpigotMock playerSpigotMock;

	public PlayerMock(ServerMock server, String name)
	{
		this(server, name, UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)));
		this.online = false;
	}

	public PlayerMock(ServerMock server, String name, UUID uuid)
	{
		super(server, uuid);
		setName(name);
		setDisplayName(name);
		this.online = true;

		if (Bukkit.getWorlds().isEmpty())
		{
			MockBukkit.getMock().addSimpleWorld("world");
		}

		setLocation(Bukkit.getWorlds().get(0).getSpawnLocation().clone());
		setCompassTarget(getLocation());
		closeInventory();

		playerSpigotMock = new PlayerSpigotMock();
	}

	@Override
	public EntityType getType()
	{
		return EntityType.PLAYER;
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(attributes, exp, expLevel, expTotal, displayName, gamemode, getHealth(),
		                                       foodLevel, saturation, inventory, enderChest, inventoryView, getMaxHealth(), online, whitelisted,
		                                       compassTarget, bedSpawnLocation, cursor, firstPlayed, lastPlayed);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof PlayerMock))
			return false;
		PlayerMock other = (PlayerMock) obj;
		return Objects.equals(attributes, other.attributes) && Objects.equals(displayName, other.displayName)
		       && gamemode == other.gamemode
		       && Double.doubleToLongBits(getHealth()) == Double.doubleToLongBits(other.getHealth())
		       && Objects.equals(inventory, other.inventory) && Objects.equals(inventoryView, other.inventoryView)
		       && Objects.equals(cursor, other.cursor)
		       && Double.doubleToLongBits(getMaxHealth()) == Double.doubleToLongBits(other.getMaxHealth())
		       && online == other.online && whitelisted == other.whitelisted && isDead() == other.isDead()
		       && firstPlayed == other.firstPlayed && lastPlayed == other.lastPlayed;
	}

	/**
	 * Simulates the player damaging a block just like {@link #simulateBlockDamage(Block)}. However, if
	 * {@code InstaBreak} is enabled, it will not automatically fire a {@link BlockBreakEvent}. It will also still fire
	 * a {@link BlockDamageEvent} even if the player is not in survival mode.
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
	 * Simulates the player damaging a block. Note that this method does not anything unless the player is in survival
	 * mode. If {@code InstaBreak} is set to true by an event handler, a {@link BlockBreakEvent} is immediately fired.
	 * The result will then still be whether or not the {@link BlockDamageEvent} was cancelled or not, not the later
	 * {@link BlockBreakEvent}.
	 *
	 * @param block The block to damage.
	 * @return {@code true} if the block was damaged, {@code false} if the event was cancelled or the player was not in
	 *         survival gamemode.
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
	 * Simulates the player breaking a block. This method will not break the block if the player is in adventure or
	 * spectator mode. If the player is in survival mode, the player will first damage the block.
	 *
	 * @param block The block to break.
	 * @return {@code true} if the block was broken, {@code false} if it wasn't or if the player was in adventure mode
	 *         or in spectator mode.
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

	/**
	 * Simulates the player placing a block. This method will not place the block if the player is in adventure or
	 * spectator mode.
	 *
	 * @param material The material of the location to set to
	 * @param location The location of the material to set to
	 * @return {@code true} if the block was placed, {@code false} if it wasn't or the player was in adventure
	 * 			mode.
	 */
	public boolean simulateBlockPlace(Material material, Location location)
	{
		if (gamemode == GameMode.ADVENTURE || gamemode == GameMode.SPECTATOR)
			return false;
		Block block = location.getBlock();
		BlockPlaceEvent event = new BlockPlaceEvent(block, null, null, null, this, true, null);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			block.setType(material);
		return !event.isCancelled();
	}

	/**
	 * This method simulates the {@link Player} respawning and also calls a {@link PlayerRespawnEvent}. Should the
	 * {@link Player} not be dead (when {@link #isDead()} returns false) then this will throw an
	 * {@link UnsupportedOperationException}. Otherwise, the {@link Location} will be set to
	 * {@link Player#getBedSpawnLocation()} or {@link World#getSpawnLocation()}. Lastly the health of this
	 * {@link Player} will be restored and set to the max health.
	 */
	public void respawn()
	{
		Location respawnLocation = getBedSpawnLocation();
		boolean isBedSpawn = respawnLocation != null;

		if (!isBedSpawn)
		{
			respawnLocation = getLocation().getWorld().getSpawnLocation();
		}

		PlayerRespawnEvent event = new PlayerRespawnEvent(this, respawnLocation, isBedSpawn);
		Bukkit.getPluginManager().callEvent(event);

		// Reset location and health
		setHealth(getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		setLocation(event.getRespawnLocation().clone());
		alive = true;
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
	public InventoryView getOpenInventory()
	{
		return inventoryView;
	}

	@Override
	public void openInventory(InventoryView inventory)
	{
		closeInventory();
		inventoryView = inventory;
	}

	@Override
	public InventoryView openInventory(Inventory inventory)
	{
		closeInventory();
		inventoryView = new PlayerInventoryViewMock(this, inventory);
		return inventoryView;
	}

	@Override
	public void closeInventory()
	{
		if (inventoryView instanceof PlayerInventoryViewMock)
		{
			InventoryCloseEvent event = new InventoryCloseEvent(inventoryView);
			Bukkit.getPluginManager().callEvent(event);
		}

		// reset the cursor as it is a new InventoryView
		cursor = null;
		inventoryView = new SimpleInventoryViewMock(this, null, inventory, InventoryType.CRAFTING);
	}

	/**
	 * This method is an assertion for the currently open {@link InventoryView} for this {@link Player}. The
	 * {@link Predicate} refers to the top inventory, not the {@link PlayerInventory}. It uses the method
	 * {@link InventoryView#getTopInventory()}.
	 *
	 * @param message   The message to display upon failure
	 * @param type      The {@link InventoryType} you are expecting
	 * @param predicate A custom {@link Predicate} to check the opened {@link Inventory}.
	 */
	public void assertInventoryView(String message, InventoryType type, Predicate<Inventory> predicate)
	{
		InventoryView view = getOpenInventory();

		if (view.getType() == type && predicate.test(view.getTopInventory()))
		{
			return;
		}

		fail(message);
	}

	/**
	 * This method is an assertion for the currently open {@link InventoryView} for this {@link Player}. The
	 * {@link Predicate} refers to the top inventory, not the {@link PlayerInventory}. It uses the method
	 * {@link InventoryView#getTopInventory()}.
	 *
	 * @param type      The {@link InventoryType} you are expecting
	 * @param predicate A custom {@link Predicate} to check the opened {@link Inventory}.
	 */
	public void assertInventoryView(InventoryType type, Predicate<Inventory> predicate)
	{
		assertInventoryView("The InventoryView Assertion has failed", type, predicate);
	}

	/**
	 * This method is an assertion for the currently open {@link InventoryView} for this {@link Player}.
	 *
	 * @param type The {@link InventoryType} you are expecting
	 */
	public void assertInventoryView(InventoryType type)
	{
		assertInventoryView("The InventoryView Assertion has failed", type, inv -> true);
	}

	/**
	 * This method is an assertion for the currently open {@link InventoryView} for this {@link Player}.
	 *
	 * @param message The message to display upon failure
	 * @param type    The {@link InventoryType} you are expecting
	 */
	public void assertInventoryView(String message, InventoryType type)
	{
		assertInventoryView(message, type, inv -> true);
	}

	@Override
	public boolean performCommand(String command)
	{
		return Bukkit.dispatchCommand(this, command);
	}

	@Override
	public Inventory getEnderChest()
	{
		if (enderChest == null)
		{
			enderChest = new EnderChestInventoryMock(this);
		}

		return enderChest;
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
		return openMerchant((Merchant) trader, force);
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
		return getInventory().getItemInMainHand();
	}

	@Override
	public void setItemInHand(ItemStack item)
	{
		getInventory().setItemInMainHand(item);
	}

	@Override
	public ItemStack getItemOnCursor()
	{
		return cursor == null ? null : cursor.clone();
	}

	@Override
	public void setItemOnCursor(ItemStack item)
	{
		this.cursor = item == null ? null : item.clone();
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
		// Formula from https://minecraft.gamepedia.com/Experience#Leveling_up
		if (this.expLevel >= 31)
			return (9 * this.expLevel) - 158;
		if (this.expLevel >= 16)
			return (5 * this.expLevel) - 38;
		return (2 * this.expLevel) + 7;
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
		return getEyeHeight(false);
	}

	@Override
	public double getEyeHeight(boolean ignorePose)
	{
		if (isSneaking() && !ignorePose)
			return 1.54D;
		return 1.62D;
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
	public boolean hasLineOfSight(Entity other)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getRemoveWhenFarAway()
	{
		// Players are never despawned until they log off
		return false;
	}

	@Override
	public void setRemoveWhenFarAway(boolean remove)
	{
		// Don't do anything
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
		// Players can not be leashed
		return false;
	}

	@Override
	public Entity getLeashHolder()
	{
		throw new IllegalStateException("Players cannot be leashed");
	}

	@Override
	public boolean setLeashHolder(Entity holder)
	{
		// Players can not be leashed
		return false;
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
		// I am sorry Dave, I'm afraid I can't do that
	}

	@Override
	public boolean hasAI()
	{
		// The Player's intelligence is (probably) not artificial
		return false;
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
		return firstPlayed;
	}

	@Override
	public long getLastPlayed()
	{
		return lastPlayed;
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return firstPlayed > 0;
	}

	public void setLastPlayed(long time)
	{
		if (time > 0)
		{
			lastPlayed = time;

			// Set firstPlayed if this is the first time
			if (firstPlayed == 0)
			{
				firstPlayed = time;
			}
		}
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
		return displayName;
	}

	@Override
	public void setDisplayName(String name)
	{
		this.displayName = name;
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
	public void setCompassTarget(@NotNull Location loc)
	{
		this.compassTarget = loc;
	}

	@NotNull
	@Override
	public Location getCompassTarget()
	{
		return this.compassTarget;
	}

	@Override
	public InetSocketAddress getAddress()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendRawMessage(@Nullable String message)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendRawMessage(@Nullable UUID sender, @NotNull String message)
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
		AsyncPlayerChatEvent eventAsync = new AsyncPlayerChatEvent(false, this, msg,
		        new HashSet<>(Bukkit.getOnlinePlayers()));
		PlayerChatEvent eventSync = new PlayerChatEvent(this, msg);
		MockBukkit.getMock().getScheduler().runTaskAsynchronously(null,
		        () -> Bukkit.getPluginManager().callEvent(eventAsync));
		Bukkit.getPluginManager().callEvent(eventSync);
	}

	@Override
	public boolean isSneaking()
	{
		return sneaking;
	}

	@Override
	public void setSneaking(boolean sneaking)
	{
		this.sneaking = sneaking;
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
	@Deprecated
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
	public void playSound(Location location, String sound, float volume, float pitch)
	{
		// The string sound is equivalent to the internal sound name, not Sound.valueOf()
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, Sound sound, float volume, float pitch)
	{
		playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch)
	{
		// The string sound is equivalent to the internal sound name, not Sound.valueOf()
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch)
	{
		heardSounds.add(new AudioExperience(sound, category, location, volume, pitch));
	}

	public void assertSoundHeard(String message, Sound sound)
	{
		assertSoundHeard(message, sound, e -> true);
	}

	public void assertSoundHeard(String message, Sound sound, Predicate<AudioExperience> predicate)
	{
		for (AudioExperience audio : heardSounds)
		{
			if (audio.getSound() == sound && predicate.test(audio))
			{
				return;
			}
		}

		fail(message);
	}

	public void assertSoundHeard(Sound sound)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound);
	}

	public void assertSoundHeard(Sound sound, Predicate<AudioExperience> predicate)
	{
		assertSoundHeard("Sound Heard Assertion failed", sound, predicate);
	}

	@Override
	public void stopSound(Sound sound)
	{
		stopSound(sound, SoundCategory.MASTER);
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
		// We will just pretend the Sound has stopped.
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
	public void sendSignChange(Location loc, String[] lines)
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
	public void incrementStatistic(Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(Statistic statistic, int newValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(Statistic statistic, Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, Material material, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, Material material, int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStatistic(Statistic statistic, Material material, int newValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void decrementStatistic(Statistic statistic, EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStatistic(Statistic statistic, EntityType entityType)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void incrementStatistic(Statistic statistic, EntityType entityType, int amount)
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
		this.exp += (float) amount / (float) this.getExpToLevel();
		setTotalExperience(this.expTotal + amount);
		while (this.exp < 0.0F)
		{
			float total = this.exp * this.getExpToLevel();

			boolean shouldContinue = this.expLevel > 0;
			this.giveExpLevels(-1);
			if (shouldContinue)
				this.exp = 1.0F + (total / this.getExpToLevel());
		}

		while (this.exp >= 1.0F)
		{
			this.exp = (this.exp - 1.0F) * this.getExpToLevel();
			this.giveExpLevels(1);
			this.exp /= this.getExpToLevel();
		}
	}

	@Override
	public void giveExpLevels(int amount)
	{
		int oldLevel = this.expLevel;
		this.expLevel += amount;
		if (this.expLevel < 0)
		{
			this.expLevel = 0;
			this.exp = 0.0F;
		}
		if (oldLevel != this.expLevel)
		{
			PlayerLevelChangeEvent event = new PlayerLevelChangeEvent(this, oldLevel, this.expLevel);
			Bukkit.getPluginManager().callEvent(event);
		}
	}

	@Override
	public float getExp()
	{
		return exp;
	}

	@Override
	public void setExp(float exp)
	{
		if (exp < 0.0 || exp > 1.0)
			throw new IllegalArgumentException("Experience progress must be between 0.0 and 1.0");
		this.exp = exp;
	}

	@Override
	public int getLevel()
	{
		return expLevel;
	}

	@Override
	public void setLevel(int level)
	{
		this.expLevel = level;
	}

	@Override
	public int getTotalExperience()
	{
		return expTotal;
	}

	@Override
	public void setTotalExperience(int exp)
	{
		this.expTotal = Math.max(0, exp);
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
		return saturation;
	}

	@Override
	public void setSaturation(float value)
	{
		// Saturation is constrained by the current food level
		this.saturation = Math.min(getFoodLevel(), value);
	}

	@Override
	public int getFoodLevel()
	{
		return foodLevel;
	}

	@Override
	public void setFoodLevel(int foodLevel)
	{
		this.foodLevel = foodLevel;
	}

	@Nullable
	@Override
	public Location getBedSpawnLocation()
	{
		return bedSpawnLocation;
	}

	@Override
	public void setBedSpawnLocation(@Nullable Location loc)
	{
		setBedSpawnLocation(loc, false);
	}

	@Override
	public void setBedSpawnLocation(@Nullable Location loc, boolean force)
	{
		if (force || loc == null || loc.getBlock().getType().name().endsWith("_BED"))
		{
			this.bedSpawnLocation = loc;
		}
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
	@Deprecated
	public void hidePlayer(Player player)
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
	@Deprecated
	public void showPlayer(Player player)
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
	public void setFlySpeed(float value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWalkSpeed(float value)
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
	@Deprecated
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
	public void setScoreboard(Scoreboard scoreboard)
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
	public void setHealthScale(double scale)
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
	public String getPlayerListHeader()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getPlayerListFooter()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPlayerListHeader(String header)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPlayerListFooter(String footer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPlayerListHeaderFooter(String header, String footer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendBlockChange(Location loc, BlockData block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void updateCommands()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean discoverRecipe(NamespacedKey recipe)
	{
		return discoverRecipes(Arrays.asList(recipe)) != 0;
	}

	@Override
	public int discoverRecipes(Collection<NamespacedKey> recipes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean undiscoverRecipe(NamespacedKey recipe)
	{
		return undiscoverRecipes(Arrays.asList(recipe)) != 0;
	}

	@Override
	public int undiscoverRecipes(Collection<NamespacedKey> recipes)
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
	public BoundingBox getBoundingBox()
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
	public int getClientViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean sleep(Location location, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void wakeup(boolean setSpawnLocation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Location getBedLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> T getMemory(MemoryKey<T> memoryKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void setMemory(MemoryKey<T> memoryKey, T memoryValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getAbsorptionAmount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAbsorptionAmount(double amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Pose getPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendSignChange(Location loc, String[] lines, DyeColor dyeColor) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void openBook(ItemStack book)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void attack(Entity target)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void swingMainHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void swingOffHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendExperienceChange(float progress)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendExperienceChange(float progress, int level)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getAttackCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasDiscoveredRecipe(NamespacedKey recipe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<NamespacedKey> getDiscoveredRecipes()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean dropItem(boolean dropAll)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<UUID> getCollidableExemptions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public org.bukkit.entity.Player.Spigot spigot()
	{
		return playerSpigotMock;
	}

	public class PlayerSpigotMock extends Player.Spigot
	{

		@Override
		public void sendMessage(@NotNull BaseComponent... components)
		{
			for (BaseComponent component : components)
			{
				sendMessage(component);
			}
		}

		@Override
		public void sendMessage(@NotNull ChatMessageType position, @NotNull BaseComponent... components)
		{
			for (BaseComponent component : components)
			{
				sendMessage(position, component);
			}
		}

		@Override
		public void sendMessage(@NotNull BaseComponent component)
		{
			sendMessage(ChatMessageType.CHAT, component);
		}

		@Override
		public void sendMessage(@NotNull ChatMessageType position, @NotNull BaseComponent component)
		{
			PlayerMock.this.sendMessage(component.toPlainText());
		}
	}
}
