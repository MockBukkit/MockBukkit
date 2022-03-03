package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;
import be.seeseemelk.mockbukkit.sound.AudioExperience;
import be.seeseemelk.mockbukkit.sound.SoundReceiver;
import be.seeseemelk.mockbukkit.statistic.StatisticsMock;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PlayerMock extends LivingEntityMock implements Player, SoundReceiver
{
	private boolean online;
	private PlayerInventoryMock inventory = null;
	private EnderChestInventoryMock enderChest = null;
	private GameMode gamemode = GameMode.SURVIVAL;
	private String displayName = null;
	private String playerListName = null;
	private int expTotal = 0;
	private float exp = 0;
	private int foodLevel = 20;
	private float saturation = 5.0F;
	private int expLevel = 0;
	private boolean sneaking = false;
	private boolean sprinting = false;
	private boolean allowFlight = false;
	private boolean flying = false;
	private boolean whitelisted = true;
	private InventoryView inventoryView;

	private Location compassTarget;
	private Location bedSpawnLocation;
	private ItemStack cursor = null;
	private long firstPlayed = 0;
	private long lastPlayed = 0;

	private final PlayerSpigotMock playerSpigotMock = new PlayerSpigotMock();
	private final List<AudioExperience> heardSounds = new LinkedList<>();
	private final Map<UUID, Set<Plugin>> hiddenPlayers = new HashMap<>();
	private final Set<UUID> hiddenPlayersDeprecated = new HashSet<>();

	private final Queue<String> title = new LinkedTransferQueue<>();
	private final Queue<String> subitles = new LinkedTransferQueue<>();

	private final StatisticsMock statistics = new StatisticsMock();

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
	}

	@Override
	public @NotNull EntityType getType()
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
	 * @return the event that was fired, {@code null} if the player was not in
	 * survival gamemode.
	 */
	public @Nullable BlockDamageEvent simulateBlockDamage(Block block)
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

			return event;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Simulates the player breaking a block. This method will not break the block if the player is in adventure or
	 * spectator mode. If the player is in survival mode, the player will first damage the block.
	 *
	 * @param block The block to break.
	 * @return The event that was fired, {@code null} if it wasn't or if the player was in adventure mode
	 * or in spectator mode.
	 */
	public @Nullable BlockBreakEvent simulateBlockBreak(Block block)
	{
		if ((gamemode == GameMode.SPECTATOR || gamemode == GameMode.ADVENTURE)
		        || (gamemode == GameMode.SURVIVAL && simulateBlockDamagePure(block).isCancelled()))
			return null;

		BlockBreakEvent event = new BlockBreakEvent(block, this);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			block.setType(Material.AIR);
		return event;
	}

	/**
	 * Simulates the player placing a block. This method will not place the block if the player is in adventure or
	 * spectator mode.
	 *
	 * @param material The material of the location to set to
	 * @param location The location of the material to set to
	 * @return The event that was fired. {@code null} if it wasn't or the player was in adventure
	 * mode.
	 */
	public @Nullable BlockPlaceEvent simulateBlockPlace(Material material, Location location)
	{
		if (gamemode == GameMode.ADVENTURE || gamemode == GameMode.SPECTATOR)
			return null;
		Block block = location.getBlock();
		BlockPlaceEvent event = new BlockPlaceEvent(block, null, null, null, this, true, null);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			block.setType(material);
		}
		return event;
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

		// TODO: Respawn Anchors are not yet supported.
		boolean isAnchorSpawn = false;

		if (!isBedSpawn)
		{
			respawnLocation = getLocation().getWorld().getSpawnLocation();
		}

		PlayerRespawnEvent event = new PlayerRespawnEvent(this, respawnLocation, isBedSpawn, isAnchorSpawn);
		Bukkit.getPluginManager().callEvent(event);

		// Reset location and health
		setHealth(getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		setLocation(event.getRespawnLocation().clone());
		alive = true;
	}

	/**
	 * This method moves player instantly with respect to PlayerMoveEvent
	 *
	 * @param moveLocation Location to move player to
	 * @return The event that is fired
	 */
	public @NotNull PlayerMoveEvent simulatePlayerMove(@NotNull Location moveLocation)
	{
		PlayerMoveEvent event = new PlayerMoveEvent(this, this.getLocation(), moveLocation);
		this.setLocation(event.getTo());
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			this.setLocation(event.getFrom());
		return event;
	}

	@Override
	public @NotNull PlayerInventory getInventory()
	{
		if (inventory == null)
		{
			inventory = (PlayerInventoryMock) Bukkit.createInventory(this, InventoryType.PLAYER);
		}
		return inventory;
	}

	@Override
	public @NotNull GameMode getGameMode()
	{
		return gamemode;
	}

	@Override
	public void setGameMode(@NotNull GameMode mode)
	{
		gamemode = mode;
	}

    @Override
    public GameMode getPreviousGameMode()
    {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
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
	public @NotNull InventoryView getOpenInventory()
	{
		return inventoryView;
	}

	@Override
	public void openInventory(@NotNull InventoryView inventory)
	{
		closeInventory();
		inventoryView = inventory;
	}

	@Override
	public InventoryView openInventory(@NotNull Inventory inventory)
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
	public boolean performCommand(@NotNull String command)
	{
		return Bukkit.dispatchCommand(this, command);
	}

	@Override
	public @NotNull Inventory getEnderChest()
	{
		if (enderChest == null)
		{
			enderChest = new EnderChestInventoryMock(this);
		}

		return enderChest;
	}

	@Override
	public @NotNull MainHand getMainHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setWindowProperty(@NotNull Property prop, int value)
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
	public InventoryView openMerchant(@NotNull Villager trader, boolean force)
	{
		return openMerchant((Merchant) trader, force);
	}

	@Override
	public InventoryView openMerchant(@NotNull Merchant merchant, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getItemInHand()
	{
		return getInventory().getItemInMainHand();
	}

	@Override
	public void setItemInHand(ItemStack item)
	{
		getInventory().setItemInMainHand(item);
	}

	@Override
	public @NotNull ItemStack getItemOnCursor()
	{
		return cursor == null ? new ItemStack(Material.AIR, 0) : cursor.clone();
	}

	@Override
	public void setItemOnCursor(ItemStack item)
	{
		this.cursor = item == null ? null : item.clone();
	}

	@Override
	public boolean hasCooldown(@NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getCooldown(@NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCooldown(@NotNull Material material, int ticks)
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

	@Nullable
	@Override
	public ItemStack getItemInUse()
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
	public @NotNull List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance)
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
	public boolean hasLineOfSight(@NotNull Entity other)
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
	public @NotNull Entity getLeashHolder()
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
	public void acceptConversationInput(@NotNull String input)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean beginConversation(@NotNull Conversation conversation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void abandonConversation(@NotNull Conversation conversation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details)
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
	public @NotNull Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, byte[] message)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<String> getListeningPluginChannels()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getDisplayName()
	{
		return displayName;
	}

	@Override
	public void setDisplayName(String name)
	{
		this.displayName = name;
	}

	@Override
	public @NotNull String getPlayerListName()
	{
		return this.playerListName == null ? getName() : this.playerListName;
	}

	@Override
	public void setPlayerListName(String name)
	{
		this.playerListName = name;
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
	@SuppressWarnings("deprecation")
	public void chat(@NotNull String msg)
	{
		Set<Player> players = new HashSet<>(Bukkit.getOnlinePlayers());
		AsyncPlayerChatEvent asyncEvent = new AsyncPlayerChatEvent(true, this, msg, players);
		org.bukkit.event.player.PlayerChatEvent syncEvent = new org.bukkit.event.player.PlayerChatEvent(this, msg);

		ServerMock server = MockBukkit.getMock();
		server.getScheduler().executeAsyncEvent(asyncEvent);
		server.getPluginManager().callEvent(syncEvent);
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

	public @NotNull PlayerToggleSneakEvent simulateSneak(boolean sneak)
	{
		PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(this, sneak);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			this.sneaking = event.isSneaking();
		}
		return event;
	}

	@Override
	public boolean isSprinting()
	{
		return sprinting;
	}

	@Override
	public void setSprinting(boolean sprinting)
	{
		this.sprinting = sprinting;
	}

	public @NotNull PlayerToggleSprintEvent simulateSprint(boolean sprint)
	{
		PlayerToggleSprintEvent event = new PlayerToggleSprintEvent(this, sprint);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			this.sprinting = event.isSprinting();
		}
		return event;
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
	public boolean isSleepingIgnored()
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
	@Deprecated
	public void playNote(@NotNull Location loc, byte instrument, byte note)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playNote(@NotNull Location loc, @NotNull Instrument instrument, @NotNull Note note)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, float volume, float pitch)
	{
	    playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, float volume, float pitch)
	{
		playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

    @Override
    public void playSound(Entity entity, Sound sound, float volume, float pitch)
    {
        playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
    }

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch)
	{
		heardSounds.add(new AudioExperience(sound, category, location, volume, pitch));
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch)
	{
		heardSounds.add(new AudioExperience(sound, category, location, volume, pitch));
	}

    @Override
    public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch)
    {
        playSound(entity.getLocation(), sound, volume, pitch);
    }

	@Override
	public @NotNull List<AudioExperience> getHeardSounds()
	{
		return heardSounds;
	}

	@Override
	public void stopSound(@NotNull Sound sound)
	{
		stopSound(sound, SoundCategory.MASTER);
	}

	@Override
	public void stopSound(@NotNull String sound)
	{
		stopSound(sound, SoundCategory.MASTER);
	}

	@Override
	public void stopSound(@NotNull Sound sound, SoundCategory category)
	{
		// We will just pretend the Sound has stopped.
	}

	@Override
	public void stopSound(@NotNull String sound, SoundCategory category)
	{
		// We will just pretend the Sound has stopped.
	}

	@Override
	public void stopAllSounds()
	{
		// We will just pretend the Sounds have stopped.
	}

	@Override
	public void playEffect(@NotNull Location loc, @NotNull Effect effect, int data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void playEffect(@NotNull Location loc, @NotNull Effect effect, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean breakBlock(@NotNull Block block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendBlockChange(@NotNull Location loc, @NotNull Material material, byte data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendSignChange(@NotNull Location loc, String[] lines)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendMap(@NotNull MapView map)
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
	public void incrementStatistic(@NotNull Statistic statistic)
	{
		statistics.incrementStatistic(statistic, 1);
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic)
	{
		statistics.decrementStatistic(statistic, 1);
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, int amount)
	{
		statistics.incrementStatistic(statistic, amount);
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, int amount)
	{
		statistics.decrementStatistic(statistic, amount);
	}

	@Override
	public void setStatistic(@NotNull Statistic statistic, int newValue)
	{
		statistics.setStatistic(statistic, newValue);
	}

	@Override
	public int getStatistic(@NotNull Statistic statistic)
	{
		return statistics.getStatistic(statistic);
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		statistics.incrementStatistic(statistic, material, 1);
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		statistics.decrementStatistic(statistic, material, 1);
	}

	@Override
	public int getStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		return statistics.getStatistic(statistic, material);
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		statistics.incrementStatistic(statistic, material, amount);
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		statistics.decrementStatistic(statistic, material, amount);
	}

	@Override
	public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int newValue)
	{
		statistics.setStatistic(statistic, material, newValue);
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		statistics.incrementStatistic(statistic, entityType, 1);
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		statistics.decrementStatistic(statistic, entityType, 1);
	}

	@Override
	public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		return statistics.getStatistic(statistic, entityType);
	}

	@Override
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount)
	{
		statistics.incrementStatistic(statistic, entityType, amount);
	}

	@Override
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount)
	{
		statistics.decrementStatistic(statistic, entityType, amount);
	}

	@Override
	public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int newValue)
	{
		statistics.setStatistic(statistic, entityType, newValue);
	}

	@Override
	public void setPlayerTime(long time, boolean relative)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getCustomName()
	{
		return displayName;
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
	public WeatherType getPlayerWeather()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPlayerWeather(@NotNull WeatherType type)
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
			{
				this.exp = 1.0F + (total / this.getExpToLevel());
			}
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
		return allowFlight;
	}

	@Override
	public void setAllowFlight(boolean flight)
	{
		if (this.isFlying() && !flight)
		{
			flying = false;
		}
		this.allowFlight = flight;
	}

	@Override
	@Deprecated
	public void hidePlayer(@NotNull Player player)
	{
		hiddenPlayersDeprecated.add(player.getUniqueId());
	}

	@Override
	public void hidePlayer(@NotNull Plugin plugin, @NotNull Player player)
	{
		hiddenPlayers.putIfAbsent(player.getUniqueId(), new HashSet<>());
		Set<Plugin> blockingPlugins = hiddenPlayers.get(player.getUniqueId());
		blockingPlugins.add(plugin);
	}

	@Override
	@Deprecated
	public void showPlayer(@NotNull Player player)
	{
		hiddenPlayersDeprecated.remove(player.getUniqueId());
	}

	@Override
	public void showPlayer(@NotNull Plugin plugin, @NotNull Player player)
	{
		if (hiddenPlayers.containsKey(player.getUniqueId()))
		{
			Set<Plugin> blockingPlugins = hiddenPlayers.get(player.getUniqueId());
			blockingPlugins.remove(plugin);
			if (blockingPlugins.isEmpty())
			{
				hiddenPlayers.remove(player.getUniqueId());
			}
		}
	}

	@Override
	public boolean canSee(@NotNull Player player)
	{
		return !hiddenPlayers.containsKey(player.getUniqueId()) &&
		       !hiddenPlayersDeprecated.contains(player.getUniqueId());
	}

	@Override
	public boolean isFlying()
	{
		return flying;
	}

	@Override
	public void setFlying(boolean value)
	{
		if (!this.getAllowFlight() && value) {
			throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
		}
		this.flying = value;
	}

	public @NotNull PlayerToggleFlightEvent simulateToggleFlight(boolean fly)
	{
		PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(this, fly);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			this.flying = event.isFlying();
		}
		return event;
	}

	@Override
	public float getFlySpeed()
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
	public float getWalkSpeed()
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
	@Deprecated
	public void setTexturePack(@NotNull String url)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, byte[] hash)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, @Nullable byte[] hash, @Nullable String prompt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, @Nullable byte[] hash, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, @Nullable byte[] hash, @Nullable String prompt, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Scoreboard getScoreboard()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setScoreboard(@NotNull Scoreboard scoreboard)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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

		List<ItemStack> drops = new ArrayList<>();
		drops.addAll(Arrays.asList(getInventory().getContents()));
		PlayerDeathEvent event = new PlayerDeathEvent(this, drops, 0, getName() + " got killed");
		Bukkit.getPluginManager().callEvent(event);

		// Terminate any InventoryView and the cursor item
		closeInventory();

		// Clear the Inventory if keep-inventory is not enabled
		if (!getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY).booleanValue())
		{
			getInventory().clear();
			// Should someone try to provoke a RespawnEvent, they will now find the Inventory to be empty
		}

		setLevel(0);
		setExp(0);
		setFoodLevel(0);

		alive = false;
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
	public double getHealthScale()
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
		this.title.add(title);
		this.subitles.add(subtitle);
	}

	@Override
	public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		sendTitle(title, subtitle);
	}

	public String nextTitle()
	{
		return title.poll();
	}

	public String nextSubTitle()
	{
		return subitles.poll();
	}

	@Override
	public void resetTitle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
	                          double offsetZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
	                          double offsetY, double offsetZ)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
	                              double offsetZ, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
	                              double offsetY, double offsetZ, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
	                          double offsetZ, double extra)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
	                          double offsetY, double offsetZ, double extra)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
	                              double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
	                              double offsetY, double offsetZ, double extra, T data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull AdvancementProgress getAdvancementProgress(@NotNull Advancement advancement)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getLocale()
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
	public void setPlayerListHeader(String header)
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
	public void sendBlockChange(@NotNull Location loc, @NotNull BlockData block)
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
	public boolean discoverRecipe(@NotNull NamespacedKey recipe)
	{
		return discoverRecipes(Collections.singletonList(recipe)) != 0;
	}

	@Override
	public int discoverRecipes(@NotNull Collection<NamespacedKey> recipes)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean undiscoverRecipe(@NotNull NamespacedKey recipe)
	{
		return undiscoverRecipes(Collections.singletonList(recipe)) != 0;
	}

	@Override
	public int undiscoverRecipes(@NotNull Collection<NamespacedKey> recipes)
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
	public @NotNull BoundingBox getBoundingBox()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockFace getFacing()
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
	public boolean sleep(@NotNull Location location, boolean force)
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
	public @NotNull Location getBedLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> T getMemory(@NotNull MemoryKey<T> memoryKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, T memoryValue)
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
	public @NotNull Pose getPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendSignChange(@NotNull Location loc, String[] lines, @NotNull DyeColor dyeColor) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendSignChange(@NotNull Location loc, @Nullable String[] lines, @NotNull DyeColor dyeColor, boolean hasGlowingText) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void openBook(@NotNull ItemStack book)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void attack(@NotNull Entity target)
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
	public boolean hasDiscoveredRecipe(@NotNull NamespacedKey recipe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<NamespacedKey> getDiscoveredRecipes()
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
	public @NotNull Set<UUID> getCollidableExemptions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendBlockDamage(@NotNull Location loc, float progress)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSaturatedRegenRate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSaturatedRegenRate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getUnsaturatedRegenRate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setUnsaturatedRegenRate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getStarvationRate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStarvationRate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getPing()
	{
		/*
		 * This PlayerMock and the ServerMock exist within
		 * the same machine, therefore there would most
		 * likely be a ping of 0ms.
		 */
		return 0;
	}

	@Override
	public boolean teleport(@NotNull Location location, @NotNull PlayerTeleportEvent.TeleportCause cause)
	{
		Validate.notNull(location, "Location cannot be null");
		Validate.notNull(cause, "Cause cannot be null");

		PlayerTeleportEvent playerTeleportEvent = new PlayerTeleportEvent(this, getLocation(), location, cause);
		Bukkit.getPluginManager().callEvent(playerTeleportEvent);

		if (playerTeleportEvent.isCancelled())
		{
			return false;
		}

		return super.teleport(playerTeleportEvent.getTo(), cause);
	}

	@Override
	public void sendEquipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void hideEntity(Plugin plugin, Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void showEntity(Plugin plugin, Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canSee(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void openSign(Sign sign)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void showDemoScreen()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isAllowingServerListings()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
    
    @Override
    public @NotNull SpawnCategory getSpawnCategory()
    {
        return SpawnCategory.MISC;
    }

    @Override
    public PlayerProfile getPlayerProfile()
    {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }


	@Override
	public @NotNull PlayerSpigotMock spigot()
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
			PlayerMock.this.sendMessage(component.toLegacyText());
		}
	}
}
