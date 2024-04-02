package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.AsyncCatcher;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlayerList;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.data.EntityState;
import be.seeseemelk.mockbukkit.map.MapViewMock;
import be.seeseemelk.mockbukkit.sound.AudioExperience;
import be.seeseemelk.mockbukkit.sound.SoundReceiver;
import be.seeseemelk.mockbukkit.statistic.StatisticsMock;
import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.Title;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.entity.LookAnchor;
import io.papermc.paper.entity.TeleportFlag;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.math.Position;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.util.TriState;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameEvent;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.Tag;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.sign.Side;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerExpCooldownChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of a {@link Player}.
 *
 * @see HumanEntityMock
 */
public class PlayerMock extends HumanEntityMock implements Player, SoundReceiver
{

	private static final Component DEFAULT_KICK_COMPONENT = Component.text("You are not whitelisted on this server!");

	private @NotNull GameMode previousGamemode = super.getGameMode();

	private boolean online;
	private @Nullable Component displayName = null;
	private @Nullable Component playerListName = null;
	private @Nullable Component playerListHeader = null;
	private @Nullable Component playerListFooter = null;
	private int expTotal = 0;
	private float exp = 0;
	private int expCooldown = 0;
	private boolean sneaking = false;
	private boolean sprinting = false;
	private boolean allowFlight = false;
	private boolean flying = false;
	private boolean scaledHealth = false;
	private double healthScale = 20;
	private Location compassTarget;
	private @Nullable Location bedSpawnLocation;
	private @Nullable InetSocketAddress address;

	private final PlayerSpigotMock playerSpigotMock = new PlayerSpigotMock();
	private final List<AudioExperience> heardSounds = new LinkedList<>();
	private final Map<UUID, Set<Plugin>> hiddenPlayers = new HashMap<>();
	private final Set<UUID> hiddenPlayersDeprecated = new HashSet<>();

	private final Queue<String> title = new LinkedTransferQueue<>();
	private final Queue<String> subitles = new LinkedTransferQueue<>();

	private final Set<BossBar> bossBars = new HashSet<>();

	private Scoreboard scoreboard;
	private final StatisticsMock statistics = new StatisticsMock();

	private final Set<String> channels = new HashSet<>();

	private final List<ItemStack> consumedItems = new LinkedList<>();
	private Locale locale;
	private @NotNull PlayerProfile playerProfile;

	/**
	 * Constructs a new {@link PlayerMock} for the provided server with the specified name.
	 * The players UUID will be generated from the name.
	 *
	 * @param server The player's server.
	 * @param name   The player's name.
	 * @see ServerMock#addPlayer
	 */
	public PlayerMock(@NotNull ServerMock server, @NotNull String name)
	{
		this(server, name, UUID.randomUUID());
	}

	/**
	 * Constructs a new {@link PlayerMock} for the provided server with the specified name and {@link UUID}.
	 * Does NOT add the player to the server.
	 *
	 * @param server The player's server.
	 * @param name   The player's name.
	 * @param uuid   The player's {@link UUID}.
	 * @see ServerMock#addPlayer
	 */
	public PlayerMock(@NotNull ServerMock server, @NotNull String name, @NotNull UUID uuid)
	{
		super(server, uuid);
		Preconditions.checkNotNull(name, "Name cannot be null");
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

		// NMS Player#createAttributes
		attributes.get(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);

		Random random = ThreadLocalRandom.current();
		address = new InetSocketAddress("192.0.2." + random.nextInt(255), random.nextInt(32768, 65535));
		scoreboard = server.getScoreboardManager().getMainScoreboard();
		locale = Locale.ENGLISH;
		playerProfile = Bukkit.createProfile(uuid, name);
	}

	/**
	 * Simulates a disconnection from the server.
	 *
	 * @return True if the player was disconnected, false if they were already offline.
	 */
	public boolean disconnect()
	{
		if (!online)
		{
			return false;
		}
		this.online = false;

		Component message = MiniMessage.miniMessage()
				.deserialize("<name> has left the Server!", Placeholder.component("name", this.displayName()));

		PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this, message, PlayerQuitEvent.QuitReason.DISCONNECTED);
		Bukkit.getPluginManager().callEvent(playerQuitEvent);

		this.server.getPlayerList().disconnectPlayer(this);

		return true;
	}

	/**
	 * Simulates a connection to the server.
	 *
	 * @return True if the player was connected, false if they were already online.
	 */
	public boolean reconnect()
	{
		if (Arrays.stream(server.getPlayerList().getOfflinePlayers()).noneMatch(it -> it.getUniqueId().equals(this.getUniqueId())))
		{
			throw new IllegalStateException("Player was never online");
		}
		if (server.hasWhitelist() && !server.getWhitelistedPlayers().contains(this))
		{
			return false;
		}
		if (online)
		{
			return false;
		}

		this.online = true;

		server.addPlayer(this);

		return true;
	}

	/**
	 * Simulates a Player consuming an Edible Item
	 *
	 * @param consumable The Item to consume
	 */
	public void simulateConsumeItem(@NotNull ItemStack consumable)
	{
		Preconditions.checkNotNull(consumable, "Consumed Item can't be null");
		Preconditions.checkArgument(consumable.getType().isEdible(), "Item is not Consumable");

		//Since we have no Bukkit way of differentiating between drinks and food, here is a rough estimation of
		//how it would sound like

		//Drinks:Slurp Slurp Slurp
		//Food: Yum Yum Yum

		GenericGameEvent consumeStartEvent =
				new GenericGameEvent(
						GameEvent.ITEM_INTERACT_START,
						this.getLocation(),
						this,
						16,
						!Bukkit.isPrimaryThread());

		Bukkit.getPluginManager().callEvent(consumeStartEvent);

		PlayerItemConsumeEvent event = new PlayerItemConsumeEvent(this, consumable);
		Bukkit.getPluginManager().callEvent(event);

		if (event.isCancelled())
		{
			GenericGameEvent stopConsumeEvent =
					new GenericGameEvent(
							GameEvent.ITEM_INTERACT_FINISH,
							this.getLocation(),
							this,
							16,
							!Bukkit.isPrimaryThread());
			Bukkit.getPluginManager().callEvent(stopConsumeEvent);
		}

		consumedItems.add(consumable);
	}

	/**
	 * Asserts a Player has consumed the given Item
	 *
	 * @param consumable The Item to asserts has been consumed
	 */
	public void assertItemConsumed(@NotNull ItemStack consumable)
	{
		Preconditions.checkNotNull(consumable, "Consumed Item can't be null");
		if (!consumedItems.contains(consumable))
		{
			fail();
		}
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PLAYER;
	}

	/**
	 * Simulates the player damaging a block just like {@link #simulateBlockDamage(Block)}. However, if
	 * {@code InstaBreak} is enabled, it will not automatically fire a {@link BlockBreakEvent}. It will also still fire
	 * a {@link BlockDamageEvent} even if the player is not in survival mode.
	 *
	 * @param block The block to damage.
	 * @return The event that has been fired.
	 */
	protected @NotNull BlockDamageEvent simulateBlockDamagePure(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
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
	public @Nullable BlockDamageEvent simulateBlockDamage(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		if (super.getGameMode() != GameMode.SURVIVAL)
		{
			return null;
		}

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

	/**
	 * Simulates the player breaking a block. This method will not break the block if the player is in adventure or
	 * spectator mode. If the player is in survival mode, the player will first damage the block.
	 *
	 * @param block The block to break.
	 * @return The event that was fired, {@code null} if it wasn't or if the player was in adventure mode
	 * or in spectator mode.
	 */
	public @Nullable BlockBreakEvent simulateBlockBreak(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		if ((super.getGameMode() == GameMode.SPECTATOR || super.getGameMode() == GameMode.ADVENTURE)
				|| (super.getGameMode() == GameMode.SURVIVAL && simulateBlockDamagePure(block).isCancelled()))
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
	public @Nullable BlockPlaceEvent simulateBlockPlace(@NotNull Material material, @NotNull Location location)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		Preconditions.checkNotNull(location, "Location cannot be null");
		if (super.getGameMode() == GameMode.ADVENTURE || super.getGameMode() == GameMode.SPECTATOR)
			return null;
		Block block = location.getBlock();
		BlockState blockState = block.getState();
		block.setType(material);
		BlockPlaceEvent event = new BlockPlaceEvent(block, blockState, null, getItemInHand(), this, true, EquipmentSlot.HAND);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled() || !event.canBuild())
		{
			blockState.update(true, false);
		}
		return event;
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param slot The slot in the player's open inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(int slot)
	{
		return simulateInventoryClick(getOpenInventory(), slot);
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param inventoryView The inventory view we want to click
	 * @param slot          The slot in the provided Inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(@NotNull InventoryView inventoryView, int slot)
	{
		return simulateInventoryClick(inventoryView, ClickType.LEFT, slot);
	}

	/**
	 * Simulates the player clicking an Inventory.
	 *
	 * @param inventoryView The inventory view we want to click
	 * @param clickType     The click type we want to fire
	 * @param slot          The slot in the provided Inventory
	 * @return The event that was fired.
	 */
	public @NotNull InventoryClickEvent simulateInventoryClick(@NotNull InventoryView inventoryView, @NotNull ClickType clickType, int slot)
	{
		Preconditions.checkNotNull(inventoryView, "InventoryView cannot be null");
		InventoryClickEvent inventoryClickEvent = new InventoryClickEvent(inventoryView, InventoryType.SlotType.CONTAINER, slot, clickType, InventoryAction.UNKNOWN);
		Bukkit.getPluginManager().callEvent(inventoryClickEvent);
		return inventoryClickEvent;
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
		Preconditions.checkNotNull(moveLocation, "Location cannot be null");
		PlayerMoveEvent event = new PlayerMoveEvent(this, this.getLocation(), moveLocation);
		this.setLocation(event.getTo());
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			this.setLocation(event.getFrom());
		return event;
	}

	@Override
	public void setGameMode(@NotNull GameMode mode)
	{
		Preconditions.checkNotNull(mode, "GameMode cannot be null");
		if (super.getGameMode() == mode)
			return;

		PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this, mode, PlayerGameModeChangeEvent.Cause.UNKNOWN, null);
		if (!event.callEvent())
			return;

		this.previousGamemode = super.getGameMode();
		super.setGameMode(mode);
	}

	@Override
	public boolean isWhitelisted()
	{
		return server.getWhitelistedPlayers().contains(this);
	}

	@Override
	public void setWhitelisted(boolean value)
	{
		if (value)
		{
			server.getWhitelistedPlayers().add(this);
		}
		else
		{
			server.getWhitelistedPlayers().remove(this);
		}
	}

	@Override
	public Player getPlayer()
	{
		return (isOnline()) ? this : null;
	}

	@Override
	public boolean isOnline()
	{
		return getServer().getPlayer(getUniqueId()) != null;
	}

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBanned()
	{
		return MockBukkit.getMock().getBanList(BanList.Type.NAME).isBanned(getName());
	}

	@Override
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String reason, @Nullable Instant expires, @Nullable String source)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String reason, @Nullable Duration duration, @Nullable String source)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BanEntry<org.bukkit.profile.PlayerProfile> ban(@Nullable String reason, @Nullable Date expires, @Nullable String source)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public void assertInventoryView(String message, InventoryType type, @NotNull Predicate<Inventory> predicate)
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
	public void assertInventoryView(InventoryType type, @NotNull Predicate<Inventory> predicate)
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
	public void updateInventory()
	{
		// Normally a packet would be sent here to update the player's inventory.
		// We just pretend that this happened!
	}

	@Override
	public boolean performCommand(@NotNull String command)
	{
		Preconditions.checkNotNull(command, "Command cannot be null");
		return Bukkit.dispatchCommand(this, command);
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
	public EntityEquipment getEquipment()
	{
		return (EntityEquipment) getInventory();
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
		return this.server.getPlayerList().getFirstPlayed(getUniqueId());
	}

	@Override
	@Deprecated
	public long getLastPlayed()
	{
		return getLastSeen();
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return server.getPlayerList().hasPlayedBefore(getUniqueId());
	}

	/**
	 * No longer used.
	 *
	 * @param time N/A.
	 * @see MockPlayerList#setLastSeen(UUID, long)
	 * @deprecated Moved to {@link MockPlayerList}.
	 */
	@Deprecated(forRemoval = true)
	public void setLastPlayed(long time)
	{
		throw new UnsupportedOperationException("Deprecated; Does not do anything");
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("name", getName());
		return result;
	}

	@Override
	public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, byte[] message)
	{
		Preconditions.checkNotNull(source, "Source cannot be null");
		Preconditions.checkNotNull(channel, "Channel cannot be null");
		StandardMessenger.validatePluginMessage(getServer().getMessenger(), source, channel, message);
	}

	@Override
	public @NotNull Set<String> getListeningPluginChannels()
	{
		return ImmutableSet.copyOf(channels);
	}

	@Override
	public @NotNull Component displayName()
	{
		return this.displayName;
	}

	@Override
	public void displayName(@Nullable Component displayName)
	{
		this.displayName = displayName;
	}

	@Override
	@Deprecated
	public @NotNull String getDisplayName()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.displayName);
	}

	@Override
	@Deprecated
	public void setDisplayName(@NotNull String name)
	{
		this.displayName = LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public @NotNull String getScoreboardEntry()
	{
		return getName();
	}

	@Override
	public void playerListName(@Nullable Component name)
	{
		this.playerListName = name;
	}

	@Override
	public @NotNull Component playerListName()
	{
		return this.playerListName == null ? name() : this.playerListName;
	}

	@Override
	public @Nullable Component playerListHeader()
	{
		return this.playerListHeader;
	}

	@Override
	public @Nullable Component playerListFooter()
	{
		return this.playerListFooter;
	}

	@Override
	@Deprecated
	public @NotNull String getPlayerListName()
	{
		return this.playerListName == null ? getName() : LegacyComponentSerializer.legacySection().serialize(this.playerListName);
	}

	@Override
	@Deprecated
	public void setPlayerListName(@Nullable String name)
	{
		this.playerListName = name == null ? null : LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public void setCompassTarget(@NotNull Location loc)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		this.compassTarget = loc;
	}

	@NotNull
	@Override
	public Location getCompassTarget()
	{
		return this.compassTarget;
	}

	/**
	 * Sets the {@link InetSocketAddress} returned by {@link #getAddress}.
	 *
	 * @param address The address to set.
	 */
	public void setAddress(@Nullable InetSocketAddress address)
	{
		this.address = address;
	}

	@Override
	public @Nullable InetSocketAddress getAddress()
	{
		return (isOnline()) ? address : null;
	}

	@Override
	public int getProtocolVersion()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable InetSocketAddress getVirtualHost()
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
	@Deprecated
	public void kickPlayer(String message)
	{
		kick(Component.text(message));
	}

	@Override
	public void kick()
	{
		kick(DEFAULT_KICK_COMPONENT);
	}

	@Override
	public void kick(@Nullable Component message)
	{
		kick(message, PlayerKickEvent.Cause.PLUGIN);
	}

	@Override
	public void kick(@Nullable Component message, PlayerKickEvent.@NotNull Cause cause)
	{
		AsyncCatcher.catchOp("player kick");
		if (!isOnline()) return;
		PlayerKickEvent event =
				new PlayerKickEvent(this,
						Component.text("Plugin"),
						message == null ? net.kyori.adventure.text.Component.empty() : message,
						cause);

		Bukkit.getPluginManager().callEvent(event);
		server.getPlayerList().disconnectPlayer(this);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void chat(@NotNull String msg)
	{
		Preconditions.checkNotNull(msg, "Message cannot be null");
		Set<Player> players = new HashSet<>(Bukkit.getOnlinePlayers());
		AsyncPlayerChatEvent asyncEvent = new AsyncPlayerChatEvent(true, this, msg, players);
		AsyncChatEvent asyncChatEvent = new AsyncChatEvent(
				true,
				this,
				new HashSet<>(Bukkit.getOnlinePlayers()),
				ChatRenderer.defaultRenderer(),
				Component.text(msg),
				Component.text(msg),
				SignedMessage.system(msg, Component.text(msg))
		);
		PlayerChatEvent syncEvent = new PlayerChatEvent(this, msg);

		server.getScheduler().executeAsyncEvent(asyncChatEvent);
		server.getScheduler().executeAsyncEvent(asyncEvent);
		server.getPluginManager().callEvent(syncEvent);
	}

	@Override
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String reason, @Nullable Instant expires, @Nullable String source, boolean kickPlayer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String reason, @Nullable Duration duration, @Nullable String source, boolean kickPlayer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BanEntry<org.bukkit.profile.PlayerProfile> ban(@Nullable String reason, @Nullable Date expires, @Nullable String source, boolean kickPlayer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BanEntry<InetAddress> banIp(@Nullable String reason, @Nullable Date expires, @Nullable String source, boolean kickPlayer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BanEntry<InetAddress> banIp(@Nullable String reason, @Nullable Instant expires, @Nullable String source, boolean kickPlayer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BanEntry<InetAddress> banIp(@Nullable String reason, @Nullable Duration duration, @Nullable String source, boolean kickPlayer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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

	/**
	 * Simulates sneaking.
	 *
	 * @param sneak Whether the player is beginning to sneak.
	 * @return The event.
	 */
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

	/**
	 * Simulates sprinting.
	 *
	 * @param sprint Whether the player is beginning to sprint.
	 * @return The event.
	 */
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
		playNote(loc, Instrument.getByType(instrument), note);
	}

	@Override
	public void playNote(@NotNull Location loc, @NotNull Instrument instrument, @NotNull Note note)
	{
		playNote(loc, instrument, note.getId());
	}

	private void playNote(@NotNull Location loc, @NotNull Instrument instrument, byte note)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(instrument, "Instrument cannot be null");
		Sound sound = switch (instrument)
		{
			case BANJO -> Sound.BLOCK_NOTE_BLOCK_BANJO;
			case BASS_DRUM -> Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
			case BASS_GUITAR -> Sound.BLOCK_NOTE_BLOCK_BASS;
			case BELL -> Sound.BLOCK_NOTE_BLOCK_BELL;
			case BIT -> Sound.BLOCK_NOTE_BLOCK_BIT;
			case CHIME -> Sound.BLOCK_NOTE_BLOCK_CHIME;
			case COW_BELL -> Sound.BLOCK_NOTE_BLOCK_COW_BELL;
			case DIDGERIDOO -> Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
			case FLUTE -> Sound.BLOCK_NOTE_BLOCK_FLUTE;
			case GUITAR -> Sound.BLOCK_NOTE_BLOCK_GUITAR;
			case IRON_XYLOPHONE -> Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE;
			case PIANO -> Sound.BLOCK_NOTE_BLOCK_HARP;
			case PLING -> Sound.BLOCK_NOTE_BLOCK_PLING;
			case SNARE_DRUM -> Sound.BLOCK_NOTE_BLOCK_SNARE;
			case STICKS -> Sound.BLOCK_NOTE_BLOCK_HAT;
			case XYLOPHONE -> Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
			case ZOMBIE -> Sound.BLOCK_NOTE_BLOCK_IMITATE_ZOMBIE;
			case SKELETON -> Sound.BLOCK_NOTE_BLOCK_IMITATE_SKELETON;
			case CREEPER -> Sound.BLOCK_NOTE_BLOCK_IMITATE_CREEPER;
			case DRAGON -> Sound.BLOCK_NOTE_BLOCK_IMITATE_ENDER_DRAGON;
			case WITHER_SKELETON -> Sound.BLOCK_NOTE_BLOCK_IMITATE_WITHER_SKELETON;
			case PIGLIN -> Sound.BLOCK_NOTE_BLOCK_IMITATE_PIGLIN;
			case CUSTOM_HEAD -> Sound.UI_BUTTON_CLICK; // What the Fuck Mojang?
		};
		float pitch = (float) Math.pow(2.0D, (note - 12.0D) / 12.0D);
		playSound(loc, sound, SoundCategory.RECORDS, 3, pitch);
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, float volume, float pitch)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		heardSounds.add(new AudioExperience(sound, SoundCategory.MASTER, location, volume, pitch));
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, float volume, float pitch)
	{
		playSound(location, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull Sound sound, float volume, float pitch)

	{
		playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull String sound, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		Preconditions.checkNotNull(category, "Category cannot be null");
		heardSounds.add(new AudioExperience(sound, category, location, volume, pitch));
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch, long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		Preconditions.checkNotNull(category, "Category cannot be null");
		heardSounds.add(new AudioExperience(sound, category, location, volume, pitch));
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		Preconditions.checkNotNull(category, "Category cannot be null");
		heardSounds.add(new AudioExperience(sound, category, entity.getLocation(), volume, pitch));
	}

	@Override
	public @NotNull TriState hasFlyingFallDamage()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFlyingFallDamage(@NotNull TriState flyingFallDamage)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasSeenWinScreen(boolean hasSeenWinScreen)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasSeenWinScreen()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(@NotNull Entity entity, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playSound(net.kyori.adventure.sound.@NotNull Sound sound)
	{
		playSound(sound, net.kyori.adventure.sound.Sound.Emitter.self()); // Not strictly equivalent since normally the sound does not follow the entity.
	}

	@Override
	public void playSound(net.kyori.adventure.sound.@NotNull Sound sound, double x, double y, double z)
	{
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		heardSounds.add(new AudioExperience(sound, new Location(getWorld(), x, y, z)));
	}

	@Override
	public void playSound(net.kyori.adventure.sound.@NotNull Sound sound, net.kyori.adventure.sound.Sound.@NotNull Emitter emitter)
	{
		Preconditions.checkNotNull(emitter, "Emitter cannot be null");
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		if (emitter == net.kyori.adventure.sound.Sound.Emitter.self())
		{
			emitter = this;
		}
		Preconditions.checkArgument(emitter instanceof Entity, "Sound emitter must be an Entity or self()");
		heardSounds.add(new AudioExperience(sound, ((Entity) emitter).getLocation()));
	}

	@Override
	public @NotNull List<AudioExperience> getHeardSounds()
	{
		return heardSounds;
	}

	@Override
	public void addHeardSound(@NotNull AudioExperience audioExperience)
	{
		Preconditions.checkNotNull(audioExperience, "AudioExperience cannot be null");
		SoundReceiver.super.addHeardSound(audioExperience);
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
	public void stopSound(@NotNull Sound sound, @Nullable SoundCategory category)
	{
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		// We will just pretend the Sound has stopped.
	}

	@Override
	public void stopSound(@NotNull String sound, @Nullable SoundCategory category)
	{
		Preconditions.checkNotNull(sound, "Sound cannot be null");
		// We will just pretend the Sound has stopped.
	}

	@Override
	public void stopSound(@NotNull SoundCategory category)
	{
		// We will just pretend the Sound has stopped.
	}

	@Override
	public void stopAllSounds()
	{
		// We will just pretend all Sounds have stopped.
	}

	@Override
	@Deprecated
	public void playEffect(@NotNull Location loc, @NotNull Effect effect, int data)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(effect, "Effect cannot be null");
		// Pretend packet gets sent.
	}

	@Override
	public <T> void playEffect(@NotNull Location loc, @NotNull Effect effect, @Nullable T data)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(effect, "Effect cannot be null");
		if (data != null)
		{
			Preconditions.checkArgument(effect.getData() != null && effect.getData().isAssignableFrom(data.getClass()), "Wrong kind of data for this effect! (Expected " + effect.getData() + ", got " + data.getClass());
		}
		else
		{
			// The axis is optional for ELECTRIC_SPARK
			Preconditions.checkArgument(effect.getData() == null || effect == Effect.ELECTRIC_SPARK, "Wrong kind of data for this effect!");
		}
	}

	@Override
	public boolean breakBlock(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		Preconditions.checkArgument(block.getWorld().equals(getWorld()), "Cannot break blocks across worlds");

		BlockBreakEvent event = new BlockBreakEvent(block, this);

		boolean swordNoBreak = getGameMode() == GameMode.CREATIVE && getEquipment().getItemInMainHand().getType().name().contains("SWORD");
		event.setCancelled(swordNoBreak);

		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
		{
			block.setType(Material.AIR);
			// todo: BlockDropItemEvent when BlockMock#getDrops is implemented.
		}

		return !event.isCancelled();
	}

	@Override
	@Deprecated
	public void sendBlockChange(@NotNull Location loc, @NotNull Material material, byte data)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(material, "Material cannot be null");
		// Pretend we sent the block change.
	}

	@Override
	public void sendBlockChange(@NotNull Location loc, @NotNull BlockData block)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(block, "Block cannot be null");
		// Pretend we sent the block change.
	}

	@Override
	public void sendBlockChanges(@NotNull Collection<BlockState> blocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendBlockChanges(@NotNull Collection<BlockState> blocks, boolean suppressLightUpdates)
	{
		// Pretend we sent the block change.
	}

	@Override
	public void sendSignChange(@NotNull Location loc, @Nullable List<? extends Component> lines, @NotNull DyeColor dyeColor, boolean hasGlowingText) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(dyeColor, "DyeColor cannot be null");
		if (lines == null)
		{
			lines = new ArrayList<>(4);
		}
		if (lines.size() < 4)
		{
			throw new IllegalArgumentException("Must have at least 4 lines");
		}
	}

	@Override
	@Deprecated
	public void sendSignChange(@NotNull Location loc, String[] lines)
	{
		this.sendSignChange(loc, lines, DyeColor.BLACK);
	}

	@Override
	public void sendSignChange(@NotNull Location loc, String[] lines, @NotNull DyeColor dyeColor) throws IllegalArgumentException
	{
		this.sendSignChange(loc, lines, dyeColor, false);
	}

	@Override
	public void sendSignChange(@NotNull Location loc, @Nullable String @Nullable [] lines, @NotNull DyeColor dyeColor, boolean hasGlowingText) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkNotNull(dyeColor, "DyeColor cannot be null");
		if (lines == null)
		{
			lines = new String[4];
		}
		if (lines.length < 4)
		{
			throw new IllegalArgumentException("Must have at least 4 lines");
		}
	}

	@Override
	public void sendBlockUpdate(@NotNull Location loc, @NotNull TileState tileState) throws IllegalArgumentException
	{
		Preconditions.checkNotNull(loc);
		Preconditions.checkNotNull(tileState);
		//Pretend we sent block update
	}

	@Override
	public void sendPotionEffectChange(@NotNull LivingEntity entity, @NotNull PotionEffect effect)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendPotionEffectChangeRemove(@NotNull LivingEntity entity, @NotNull PotionEffectType type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendMap(@NotNull MapView map)
	{
		Preconditions.checkNotNull(map, "Map cannot be null");
		if (!(map instanceof MapViewMock mapView))
			return;

		mapView.render(this);

		// Pretend the map packet gets sent.
	}

	@Override
	public void showWinScreen()
	{
		// You won!
	}

	@Override
	@Deprecated
	public void sendActionBar(@NotNull String message)
	{
		Preconditions.checkNotNull(message, "Message cannot be null");
		// Pretend we sent the action bar.
	}

	@Override
	@Deprecated
	public void sendActionBar(char alternateChar, @NotNull String message)
	{
		Preconditions.checkNotNull(message, "Message cannot be null");
		// Pretend we sent the action bar.
	}

	@Override
	@Deprecated
	public void sendActionBar(@NotNull BaseComponent... message)
	{
		Preconditions.checkNotNull(message, "Message cannot be null");
		// Pretend we sent the action bar.
	}

	@Override
	@Deprecated
	public void setPlayerListHeaderFooter(BaseComponent @NotNull [] header, BaseComponent @NotNull [] footer)
	{
		this.playerListHeader = BungeeComponentSerializer.get().deserialize(Arrays.stream(header).filter(Objects::nonNull).toArray(BaseComponent[]::new));
		this.playerListFooter = BungeeComponentSerializer.get().deserialize(Arrays.stream(footer).filter(Objects::nonNull).toArray(BaseComponent[]::new));
	}

	@Override
	@Deprecated
	public void setPlayerListHeaderFooter(@Nullable BaseComponent header, @Nullable BaseComponent footer)
	{
		this.playerListHeader = BungeeComponentSerializer.get().deserialize(new BaseComponent[]{ header });
		this.playerListFooter = BungeeComponentSerializer.get().deserialize(new BaseComponent[]{ footer });
	}

	@Override
	@Deprecated
	public void setTitleTimes(int fadeInTicks, int stayTicks, int fadeOutTicks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setSubtitle(BaseComponent[] subtitle)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setSubtitle(BaseComponent subtitle)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void showTitle(@Nullable BaseComponent[] title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void showTitle(@Nullable BaseComponent title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void showTitle(@Nullable BaseComponent[] title, @Nullable BaseComponent[] subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void showTitle(@Nullable BaseComponent title, @Nullable BaseComponent subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void sendTitle(@NotNull Title title)
	{
		Preconditions.checkNotNull(title, "Title is null");
	}

	@Override
	@Deprecated
	public void updateTitle(@NotNull Title title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void hideTitle()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable GameMode getPreviousGameMode()
	{
		return previousGamemode;
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
	public int getExpCooldown()
	{
		return this.expCooldown;
	}

	@Override
	public void setExpCooldown(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "Cooldown ticks must be greater than or equal to 0");
		this.expCooldown = ticks;

		PlayerExpCooldownChangeEvent event = new PlayerExpCooldownChangeEvent(this, ticks, PlayerExpCooldownChangeEvent.ChangeReason.PLUGIN);
		Bukkit.getPluginManager().callEvent(event);
	}

	@Override
	public void giveExp(int amount, boolean applyMending)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int applyMending(int amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @Range(from = 0L, to = 2147483647L) int calculateTotalExperiencePoints()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setExperienceLevelAndProgress(@Range(from = 0L, to = 2147483647L) int totalExperience)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public int getExperiencePointsNeededForNextLevel()
	{
		throw new UnimplementedOperationException();
	}


	@Nullable
	@Override
	public Location getBedSpawnLocation()
	{
		return bedSpawnLocation;
	}

	@Override
	public @Nullable Location getRespawnLocation()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastLogin()
	{
		return this.server.getPlayerList().getLastLogin(getUniqueId());
	}

	@Override
	public long getLastSeen()
	{
		return this.server.getPlayerList().getLastSeen(getUniqueId());
	}

	@Override
	public void setBedSpawnLocation(@Nullable Location loc)
	{
		setBedSpawnLocation(loc, false);
	}

	@Override
	public void setRespawnLocation(@Nullable Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBedSpawnLocation(@Nullable Location loc, boolean force)
	{
		if (force || loc == null || Tag.BEDS.isTagged(loc.getBlock().getType()))
		{
			this.bedSpawnLocation = loc;
		}
	}

	@Override
	public void setRespawnLocation(@Nullable Location location, boolean b)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		Preconditions.checkNotNull(player, "Player cannot be null");
		hiddenPlayersDeprecated.add(player.getUniqueId());
	}

	@Override
	public void hidePlayer(@NotNull Plugin plugin, @NotNull Player player)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Preconditions.checkNotNull(player, "Player cannot be null");
		hiddenPlayers.putIfAbsent(player.getUniqueId(), new HashSet<>());
		Set<Plugin> blockingPlugins = hiddenPlayers.get(player.getUniqueId());
		blockingPlugins.add(plugin);
	}

	@Override
	@Deprecated
	public void showPlayer(@NotNull Player player)
	{
		Preconditions.checkNotNull(player, "Player cannot be null");
		hiddenPlayersDeprecated.remove(player.getUniqueId());
	}

	@Override
	public void showPlayer(@NotNull Plugin plugin, @NotNull Player player)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Preconditions.checkNotNull(player, "Player cannot be null");
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
		Preconditions.checkNotNull(player, "Player cannot be null");
		return !hiddenPlayers.containsKey(player.getUniqueId()) &&
				!hiddenPlayersDeprecated.contains(player.getUniqueId());
	}


	@Override
	@ApiStatus.Experimental
	public void hideEntity(@NotNull Plugin plugin, @NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@ApiStatus.Experimental
	public void showEntity(@NotNull Plugin plugin, @NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canSee(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isListed(@NotNull Player other)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean unlistPlayer(@NotNull Player other)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean listPlayer(@NotNull Player other)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}


	@Override
	public boolean isFlying()
	{
		return flying;
	}

	@Override
	public void setFlying(boolean value)
	{
		if (!this.getAllowFlight() && value)
		{
			throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
		}
		this.flying = value;
	}

	/**
	 * Simulates toggling flight.
	 *
	 * @param fly Whether the player is starting to fly.
	 * @return The event.
	 */
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
		return (float) (this.attributes.get(Attribute.GENERIC_MOVEMENT_SPEED).getValue() * 2);
	}

	@Override
	public void setWalkSpeed(float value)
	{
		Preconditions.checkArgument(value > -1, value + " is too low");
		Preconditions.checkArgument(value < 1, value + " is too high");

		this.attributes.get(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(value / 2);
	}

	@Override
	@Deprecated
	public void setTexturePack(@NotNull String url)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
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
	@Deprecated
	public void setResourcePack(@NotNull String url, @Nullable byte[] hash, @Nullable String prompt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, byte[] hash, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setResourcePack(@NotNull String url, @Nullable byte[] hash, @Nullable String prompt, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, byte @Nullable [] hash, @Nullable Component prompt, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull UUID uuid, @NotNull String url, @NotNull String hash, @Nullable Component resourcePackPrompt, boolean required)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull UUID uuid, @NotNull String url, byte @Nullable [] hash, @Nullable Component prompt, boolean force)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.20")
	public void setResourcePack(@NotNull UUID uuid, @NotNull String s, @Nullable byte[] bytes, @Nullable String s1, boolean b)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Scoreboard getScoreboard()
	{
		return this.scoreboard;
	}

	@Override
	public void setScoreboard(@NotNull Scoreboard scoreboard)
	{
		Preconditions.checkNotNull(scoreboard, "Scoreboard cannot be null");
		this.scoreboard = scoreboard;
	}

	@Override
	public @Nullable WorldBorder getWorldBorder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWorldBorder(@Nullable WorldBorder border)
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

		List<ItemStack> drops = new ArrayList<>(Arrays.asList(getInventory().getContents()));
		PlayerDeathEvent event = new PlayerDeathEvent(this, drops, 0, getName() + " got killed");
		Bukkit.getPluginManager().callEvent(event);

		// Terminate any InventoryView and the cursor item
		closeInventory();

		// Clear the Inventory if keep-inventory is not enabled
		if (!getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))
		{
			getInventory().clear();
			// Should someone try to provoke a RespawnEvent, they will now find the Inventory to be empty
		}

		setLevel(0);
		setExp(0);
		setFoodLevel(0);

		setBlocking(false);
		alive = false;
	}

	@Override
	public boolean isHealthScaled()
	{
		return this.scaledHealth;
	}

	@Override
	public void setHealthScaled(boolean scale)
	{
		this.scaledHealth = scale;
	}

	@Override
	public double getHealthScale()
	{
		return this.healthScale;
	}

	@Override
	public void setHealthScale(double scale)
	{
		Preconditions.checkArgument(scale >= 0, "Must be greater than 0");
		Preconditions.checkArgument(scale != Double.NaN, scale + " is not a number!");
		// There is also too high but... what constitutes too high?

		this.healthScale = scale;
		this.scaledHealth = true;
	}

	@Override
	public void sendHealthUpdate(double health, int foodLevel, float saturationLevel)
	{
		// Pretend we sent the health update.
	}

	@Override
	public void sendHealthUpdate()
	{
		// Pretend we sent the health update.
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
	@Deprecated
	public void sendTitle(String title, String subtitle)
	{
		this.title.add(title);
		this.subitles.add(subtitle);
	}

	@Override
	@Deprecated
	public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		sendTitle(title, subtitle);
	}

	/**
	 * @return The next title sent to the player.
	 */
	public @Nullable String nextTitle()
	{
		return title.poll();
	}

	/**
	 * @return The next subtitle sent to the player.
	 */
	public @Nullable String nextSubTitle()
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
	public void showBossBar(@NotNull BossBar bar)
	{
		Preconditions.checkNotNull(bar, "Bossbar cannot be null");
		this.bossBars.add(bar);
	}

	@Override
	public void hideBossBar(@NotNull BossBar bar)
	{
		Preconditions.checkNotNull(bar, "Bossbar cannot be null");
		this.bossBars.remove(bar);
	}

	/**
	 * Gets an unmodifiable set of all active boss bars currently shown to this player.
	 * Helper method to {@link PlayerMock#activeBossBars()}.
	 *
	 * @see #activeBossBars()
	 */
	public @UnmodifiableView @NotNull Set<BossBar> getBossBars()
	{
		return Collections.unmodifiableSet(this.bossBars);
	}

	@Override
	public @UnmodifiableView @NotNull Iterable<? extends BossBar> activeBossBars()
	{
		return getBossBars();
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count)
	{
		this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count);
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count)
	{
		this.spawnParticle(particle, x, y, z, count, null);
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, T data)
	{
		this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, data);
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, T data)
	{
		this.spawnParticle(particle, x, y, z, count, 0, 0, 0, data);
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
							  double offsetZ)
	{
		this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
							  double offsetY, double offsetZ)
	{
		this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);

	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
								  double offsetZ, T data)
	{
		this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
								  double offsetY, double offsetZ, T data)
	{
		this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1, data);

	}

	@Override
	public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
							  double offsetZ, double extra)
	{
		this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
	}

	@Override
	public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
							  double offsetY, double offsetZ, double extra)
	{
		this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);

	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY,
								  double offsetZ, double extra, T data)
	{
		this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);

	}

	@Override
	public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX,
								  double offsetY, double offsetZ, double extra, @Nullable T data)
	{
		Preconditions.checkNotNull(particle, "Particle cannot be null");
		if (data != null && !particle.getDataType().isInstance(data))
		{
			throw new IllegalArgumentException("data should be " + particle.getDataType() + " got " + data.getClass());
		}
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
		return locale().toLanguageTag();
	}

	@Override
	public boolean getAffectsSpawning()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAffectsSpawning(boolean affects)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setViewDistance(int viewDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSimulationDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSimulationDistance(int simulationDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public int getNoTickViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setNoTickViewDistance(int viewDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSendViewDistance()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSendViewDistance(int viewDistance)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getPlayerListHeader()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.playerListHeader);
	}

	@Override
	public void setPlayerListHeader(@Nullable String header)
	{
		this.playerListHeader = header == null ? null : LegacyComponentSerializer.legacySection().deserialize(header);
	}

	@Override
	public String getPlayerListFooter()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.playerListFooter);
	}

	@Override
	public void setPlayerListFooter(@Nullable String footer)
	{
		this.playerListFooter = footer == null ? null : LegacyComponentSerializer.legacySection().deserialize(footer);
	}

	@Override
	public void setPlayerListHeaderFooter(@Nullable String header, @Nullable String footer)
	{
		this.playerListHeader = header == null ? null : LegacyComponentSerializer.legacySection().deserialize(header);
		this.playerListFooter = footer == null ? null : LegacyComponentSerializer.legacySection().deserialize(footer);
	}

	@Override
	public void updateCommands()
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
	public @NotNull Locale locale()
	{
		return this.locale;
	}

	@Override
	public void openBook(@NotNull ItemStack book)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void openSign(@NotNull Sign sign, @NotNull Side side)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, @NotNull String hash)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, @NotNull String hash, boolean required)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setResourcePack(@NotNull String url, @NotNull String hash, boolean required, @Nullable Component resourcePackPrompt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PlayerResourcePackStatusEvent.@Nullable Status getResourcePackStatus()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @Nullable String getResourcePackHash()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasResourcePack()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addResourcePack(@NotNull UUID id, @NotNull String url, @Nullable byte[] hash, @Nullable String prompt, boolean force)
	{

	}

	@Override
	public void removeResourcePack(@NotNull UUID id)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeResourcePacks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PlayerProfile getPlayerProfile()
	{
		return this.playerProfile;
	}

	@Override
	public void setPlayerProfile(@NotNull PlayerProfile profile)
	{
		Preconditions.checkNotNull(profile, "Profile cannot be null");
		this.playerProfile = profile;
	}

	@Override
	public float getCooldownPeriod()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getCooledAttackStrength(float adjustTicks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void resetCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> @NotNull T getClientOption(@NotNull ClientOption<T> option)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Firework boostElytra(@NotNull ItemStack firework)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendOpLevel(byte level)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addAdditionalChatCompletions(@NotNull Collection<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeAdditionalChatCompletions(@NotNull Collection<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable String getClientBrandName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addCustomChatCompletions(@NotNull Collection<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeCustomChatCompletions(@NotNull Collection<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomChatCompletions(@NotNull Collection<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(double x, double y, double z, @NotNull LookAnchor playerAnchor)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Entity entity, @NotNull LookAnchor playerAnchor, @NotNull LookAnchor entityAnchor)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void showElderGuardian(boolean silent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getWardenWarningCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWardenWarningCooldown(int cooldown)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getWardenTimeSinceLastWarning()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWardenTimeSinceLastWarning(int time)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getWardenWarningLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWardenWarningLevel(int warningLevel)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void increaseWardenWarningLevel()
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
	public void broadcastSlotBreak(@NotNull EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Duration getIdleDuration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void resetIdleDuration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<Player> getTrackedBy()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity, @Nullable Consumer<? super T> function)
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
	public void sendExperienceChange(float progress)
	{
		this.sendExperienceChange(progress, this.getLevel());
	}

	@Override
	public void sendExperienceChange(float progress, int level)
	{
		Preconditions.checkArgument(progress >= 0.0 && progress <= 1.0, "Experience progress must be between 0.0 and 1.0 (%s)", progress);
		Preconditions.checkArgument(level >= 0, "Experience level must not be negative (%s)", level);
	}

	@Override
	public void sendBlockDamage(@NotNull Location loc, float progress)
	{
		Preconditions.checkNotNull(loc, "Location cannot be null");
		Preconditions.checkArgument(progress >= 0.0 && progress <= 1.0, "progress must be between 0.0 and 1.0 (inclusive)");
	}

	@Override
	public void sendMultiBlockChange(@NotNull Map<? extends Position, BlockData> blockChanges)
	{
		Preconditions.checkNotNull(blockChanges, "Block changes cannot be null");
		//Pretend to send the packet
	}

	@Override
	public void sendBlockDamage(@NotNull Location loc, float progress, int destroyerIdentity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendHurtAnimation(float yaw)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendMultiBlockChange(@NotNull Map<? extends Position, BlockData> blockChanges, boolean suppressLightUpdates)
	{
		Preconditions.checkNotNull(blockChanges, "Block changes cannot be null");
		//Pretend to send the packet
	}

	@Override
	public void sendBlockDamage(@NotNull Location loc, float progress, @NotNull Entity source)
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
	@SuppressWarnings("UnstableApiUsage")
	public boolean teleport(@NotNull Location location, @NotNull PlayerTeleportEvent.TeleportCause cause,
							TeleportFlag @NotNull ... flags)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		Preconditions.checkNotNull(location.getWorld(), "World cannot be null");
		Preconditions.checkNotNull(cause, "Cause cannot be null");

		Set<TeleportFlag.Relative> relativeArguments = EnumSet.noneOf(TeleportFlag.Relative.class);
		Set<TeleportFlag> allFlags = new HashSet<>();
		for (TeleportFlag flag : flags)
		{
			if (flag instanceof TeleportFlag.Relative relativeTeleportFlag)
			{
				relativeArguments.add(relativeTeleportFlag);
			}
			allFlags.add(flag);
		}

		boolean dismount = !allFlags.contains(TeleportFlag.EntityState.RETAIN_VEHICLE);
		boolean ignorePassengers = allFlags.contains(TeleportFlag.EntityState.RETAIN_PASSENGERS);

		if (ignorePassengers && hasPassengers() && location.getWorld() != this.getWorld())
		{
			return false;
		}
		if (!dismount && getVehicle() != null && location.getWorld() != this.getWorld())
		{
			return false;
		}

		location.checkFinite();

		if (isDead() || (!ignorePassengers && hasPassengers()))
		{
			return false;
		}
		if (location.getWorld() != getWorld())
		{
			// Don't allow teleporting between worlds while keeping passengers
			// and if remaining on vehicle.
			if ((ignorePassengers && hasPassengers())
					|| (!dismount && isInsideVehicle()))
			{
				return false;
			}
		}

		PlayerTeleportEvent event = new PlayerTeleportEvent(this, getLocation(), location, cause);
		if (!event.callEvent())
		{
			return false;
		}

		// Close any foreign inventory
		if (getOpenInventory().getType() != InventoryType.CRAFTING)
		{
			closeInventory(InventoryCloseEvent.Reason.TELEPORT);
		}

		World previousWorld = getWorld();
		teleportWithoutEvent(event.getTo(), cause);

		// Detect player dimension change
		if (!location.getWorld().equals(previousWorld))
		{
			new PlayerChangedWorldEvent(this, previousWorld).callEvent();
		}
		return true;
	}

	@Override
	public void sendEquipmentChange(@NotNull LivingEntity entity, @NotNull EquipmentSlot slot, @NotNull ItemStack item)
	{
		Preconditions.checkNotNull(entity, "entity must not be null");
		Preconditions.checkNotNull(slot, "slot must not be null");
		Preconditions.checkNotNull(item, "item must not be null");
		// Pretend the packet gets sent.
	}

	@Override
	public void sendEquipmentChange(@NotNull LivingEntity entity, @NotNull Map<EquipmentSlot, ItemStack> equipmentChanges)
	{
		equipmentChanges.forEach((slot, stack) -> sendEquipmentChange(entity, slot, stack));
	}

	@Override
	public boolean isOp()
	{
		return server.getPlayerList().getOperators().stream()
				.anyMatch(op -> op.getPlayer() == this);
	}

	@Override
	public void setOp(boolean isOperator)
	{

		if (isOperator)
		{
			server.getPlayerList().addOperator(this.getUniqueId());
		}
		else
		{
			server.getPlayerList().removeOperator(this.getUniqueId());
		}

	}

	@Override
	protected EntityState getEntityState()
	{
		if (this.isSneaking())
		{
			return EntityState.SNEAKING;
		}
		if (this.isGliding())
		{
			return EntityState.GLIDING;
		}
		if (this.isSwimming())
		{
			return EntityState.SWIMMING;
		}
		if (this.isSleeping())
		{
			return EntityState.SLEEPING;
		}
		return super.getEntityState();
	}

	@Override
	public Player.@NotNull Spigot spigot()
	{
		return playerSpigotMock;
	}

	/**
	 * Mock implementation of a {@link Player.Spigot}.
	 */
	public class PlayerSpigotMock extends Player.Spigot
	{

		@Override
		@Deprecated
		public void sendMessage(@NotNull BaseComponent @NotNull ... components)
		{
			sendMessage(ChatMessageType.CHAT, components);
		}

		@Override
		@Deprecated
		public void sendMessage(@NotNull ChatMessageType position, @NotNull BaseComponent @NotNull ... components)
		{
			Preconditions.checkNotNull(position, "Position must not be null");
			Preconditions.checkNotNull(components, "Component must not be null");
			Component comp = BungeeComponentSerializer.get().deserialize(components);
			PlayerMock.this.sendMessage(comp);
		}

		@Override
		@Deprecated
		public void sendMessage(@NotNull BaseComponent component)
		{
			sendMessage(ChatMessageType.CHAT, component);
		}

		@Override
		@Deprecated
		public void sendMessage(@NotNull ChatMessageType position, @NotNull BaseComponent component)
		{
			sendMessage(position, new BaseComponent[]{ component });
		}

	}

	/**
	 * Sets player locale
	 *
	 * @param locale the locale
	 */
	public void setLocale(@NotNull Locale locale)
	{
		Preconditions.checkNotNull(locale, "locale cannot be null");
		this.locale = locale;
	}

}
