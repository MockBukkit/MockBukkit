package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.AsyncCatcher;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import com.google.common.base.Preconditions;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedTransferQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class EntityMock extends Entity.Spigot implements Entity, MessageTarget
{

	private final @NotNull ServerMock server;
	private final @NotNull UUID uuid;
	private Location location;
	private boolean teleported;
	private TeleportCause teleportCause;
	private final MetadataTable metadataTable = new MetadataTable();
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();
	private boolean operator = false;
	private @NotNull Component name = Component.text("entity");
	private @Nullable Component customName = null;
	private boolean customNameVisible = false;
	private boolean invulnerable;
	private boolean glowingFlag = false;
	private final Queue<Component> messages = new LinkedTransferQueue<>();
	private final PermissibleBase perms = new PermissibleBase(this);
	private @NotNull Vector velocity = new Vector(0, 0, 0);
	private float fallDistance;
	private int fireTicks = -20;
	private final int maxFireTicks = 20;
	private boolean removed = false;
	private @Nullable EntityDamageEvent lastDamageEvent;

	protected EntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		Preconditions.checkNotNull(server, "Server cannot be null");
		Preconditions.checkNotNull(uuid, "UUID cannot be null");

		this.server = server;
		this.uuid = uuid;

		if (!Bukkit.getWorlds().isEmpty())
			location = Bukkit.getWorlds().get(0).getSpawnLocation();
		else
			location = new Location(null, 0, 0, 0);
	}

	@Override
	public final int hashCode()
	{
		return uuid.hashCode();
	}

	@Override
	public final boolean equals(Object obj)
	{
		if (obj instanceof EntityMock)
		{
			return uuid.equals(((EntityMock) obj).getUniqueId());
		}
		return false;
	}

	/**
	 * Assert that the actual location of the player is within a certain distance to a given location.
	 *
	 * @param expectedLocation The location the player should be at.
	 * @param maximumDistance  The distance the player may maximumly be separated from the expected location.
	 */
	public void assertLocation(@NotNull Location expectedLocation, double maximumDistance)
	{
		double distance = location.distance(expectedLocation);
		assertEquals(expectedLocation.getWorld(), location.getWorld());
		assertTrue(distance <= maximumDistance, String.format("Distance was <%.3f> but should be less than or equal to <%.3f>", distance,
				maximumDistance));
	}

	/**
	 * Assert that the player teleported to a certain location within a certain distance to a given location. Also
	 * clears the teleported flag.
	 *
	 * @param expectedLocation The location the player should be at.
	 * @param maximumDistance  The distance the player may maximumly be separated from the expected location.
	 */
	public void assertTeleported(@NotNull Location expectedLocation, double maximumDistance)
	{
		assertTrue(teleported, "Player did not teleport");
		assertLocation(expectedLocation, maximumDistance);
		teleported = false;
	}

	/**
	 * Assert that the player hasn't teleported. Also clears the teleported flag.
	 */
	public void assertNotTeleported()
	{
		assertFalse(teleported, "Player was teleported");
		teleported = false;
	}

	/**
	 * Checks if the player has been teleported since the last assert or {@link #clearTeleported}.
	 *
	 * @return {@code true} if the player has been teleported, {@code false} if he hasn't been teleported.
	 */
	public boolean hasTeleported()
	{
		return teleported;
	}

	/**
	 * Clears the teleported flag.
	 */
	public void clearTeleported()
	{
		teleported = false;
	}

	/**
	 * Get the cause of the last teleport.
	 *
	 * @return The cause of the last teleport.
	 */
	public TeleportCause getTeleportCause()
	{
		return teleportCause;
	}

	@Override
	public @NotNull UUID getUniqueId()
	{
		return uuid;
	}

	@Override
	public @NotNull Location getLocation()
	{
		return location.clone();
	}

	@Override
	public @NotNull Set<Player> getTrackedPlayers()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Location getLocation(@Nullable Location loc)
	{
		if (loc == null)
			return null;

		loc.setWorld(location.getWorld());
		loc.setDirection(location.getDirection());
		loc.setX(location.getX());
		loc.setY(location.getY());
		loc.setZ(location.getZ());
		return loc;
	}

	/**
	 * Sets the location of the entity. Note that this will not fire a teleport event.
	 *
	 * @param location The new location of the entity.
	 */
	public void setLocation(@NotNull Location location)
	{
		Preconditions.checkNotNull(location, "Location cannot be null");
		// An entity can be teleported to a null world, i.e. the current world.
		Location position = location.clone();
		if (position.getWorld() == null)
		{
			position.setWorld(getWorld());
		}
		this.location = position;
	}

	@Override
	public @NotNull World getWorld()
	{
		return location.getWorld();
	}

	@Override
	public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue)
	{
		Preconditions.checkNotNull(metadataKey, "Metadata key cannot be null");
		metadataTable.setMetadata(metadataKey, newMetadataValue);
	}

	@Override
	public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey)
	{
		Preconditions.checkNotNull(metadataKey, "Metadata key cannot be null");
		return metadataTable.getMetadata(metadataKey);
	}

	@Override
	public boolean hasMetadata(@NotNull String metadataKey)
	{
		Preconditions.checkNotNull(metadataKey, "Metadata key cannot be null");
		return metadataTable.hasMetadata(metadataKey);
	}

	@Override
	public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin)
	{
		Preconditions.checkNotNull(metadataKey, "Metadata key cannot be null");
		metadataTable.removeMetadata(metadataKey, owningPlugin);
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return persistentDataContainer;
	}

	@Override
	public boolean teleport(@NotNull Location location)
	{
		return teleport(location, TeleportCause.PLUGIN);
	}

	@Override
	public boolean teleport(@NotNull Location location, @NotNull TeleportCause cause)
	{
		Preconditions.checkNotNull(location, "Location cannot be null"); // The world can be null if it's not a player
		location.checkFinite();
		if (this.removed)
		{
			return false;
		}
		//todo: Add passenger logic: don't teleport if it's a vehicle / dismount from the current vehicle if it's a passenger
		EntityTeleportEvent event = new EntityTeleportEvent(this, getLocation(), location);
		if (event.callEvent())
		{
			// There is actually no non-null check in the CraftBukkit implementation
			Preconditions.checkNotNull(event.getTo(), "The location where the entity moved to in the event cannot be null");
			teleportWithoutEvent(event.getTo(), cause);
			return true;
		}
		return false;
	}

	protected void teleportWithoutEvent(@NotNull Location location, @NotNull TeleportCause cause)
	{
		setLocation(location);
		this.teleported = true;
		this.teleportCause = cause;
	}

	@Override
	public boolean teleport(@NotNull Entity destination)
	{
		Preconditions.checkNotNull(destination, "Destination entity cannot be null");
		return teleport(destination, TeleportCause.PLUGIN);
	}

	@Override
	public boolean teleport(@NotNull Entity destination, @NotNull TeleportCause cause)
	{
		Preconditions.checkNotNull(destination, "Destination entity cannot be null");
		Preconditions.checkNotNull(cause, "TeleportCause cannot be null");
		return teleport(destination.getLocation(), cause);
	}

	@Override
	public boolean isOp()
	{
		return operator;
	}

	@Override
	public void setOp(boolean isOperator)
	{
		operator = isOperator;
	}

	@Override
	public @NotNull String getName()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.name);
	}

	/**
	 * Gets the scoreboard entry for this entity.
	 *
	 * @return The scoreboard entry.
	 */
	public @NotNull String getScoreboardEntry()
	{
		return uuid.toString();
	}

	/**
	 * Sets the name of this entity.
	 *
	 * @param name The new name of the entity.
	 */
	public void setName(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		this.name = LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public void sendMessage(@NotNull String message)
	{
		sendMessage(null, message);
	}

	@Override
	public void sendMessage(String... messages)
	{
		sendMessage(null, messages);
	}

	@Override
	public void sendMessage(@Nullable UUID sender, @NotNull String message)
	{
		Preconditions.checkNotNull(message, "Message cannot be null");
		sendMessage(sender == null ? Identity.nil() : Identity.identity(sender), LegacyComponentSerializer.legacySection().deserialize(message), MessageType.SYSTEM);
	}

	@Override
	public void sendMessage(UUID sender, String @NotNull ... messages)
	{
		for (String message : messages)
		{
			sendMessage(message);
		}
	}

	public void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type)
	{
		Preconditions.checkNotNull(source, "Source cannot be null");
		Preconditions.checkNotNull(message, "Message cannot be null");
		Preconditions.checkNotNull(type, "MessageType cannot be null");
		this.messages.add(message);
	}

	@Override
	public @Nullable Component nextComponentMessage()
	{
		return messages.poll();
	}

	@Override
	public boolean isPermissionSet(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		return this.perms.isPermissionSet(name);
	}

	@Override
	public boolean isPermissionSet(@NotNull Permission perm)
	{
		Preconditions.checkNotNull(perm, "Permission cannot be null");
		return this.perms.isPermissionSet(perm);
	}

	@Override
	public boolean hasPermission(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		return this.perms.hasPermission(name);
	}

	@Override
	public boolean hasPermission(@NotNull Permission perm)
	{
		Preconditions.checkNotNull(perm, "Permission cannot be null");
		return this.perms.hasPermission(perm);
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		Preconditions.checkNotNull(name, "Name cannot be null");
		return this.perms.addAttachment(plugin, name, value);
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		return this.perms.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks)
	{
		return this.perms.addAttachment(plugin, name, value, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks)
	{
		return this.perms.addAttachment(plugin, ticks);
	}

	@Override
	public void removeAttachment(@NotNull PermissionAttachment attachment)
	{
		Preconditions.checkNotNull(attachment, "Attachment cannot be null");
		this.perms.removeAttachment(attachment);
	}

	@Override
	public void recalculatePermissions()
	{
		this.perms.recalculatePermissions();
	}

	@Override
	public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return this.perms.getEffectivePermissions();
	}

	@Override
	public @Nullable Component customName()
	{
		return this.customName;
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		this.customName = customName;
	}

	@Override
	public String getCustomName()
	{
		return this.customName == null ? null : LegacyComponentSerializer.legacySection().serialize(this.customName);
	}

	@Override
	public void setCustomName(@Nullable String name)
	{
		this.customName = name == null ? null : LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public void setVelocity(@NotNull Vector velocity)
	{
		Preconditions.checkNotNull(velocity, "Velocity cannot be null");
		this.velocity = velocity;
	}

	@Override
	public @NotNull Vector getVelocity()
	{
		return velocity;
	}

	@Override
	public double getHeight()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getWidth()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOnGround()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<Entity> getNearbyEntities(double x, double y, double z)
	{
		AsyncCatcher.catchOp("getNearbyEntities");
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getEntityId()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getFireTicks()
	{
		return fireTicks;
	}

	@Override
	public int getMaxFireTicks()
	{
		return maxFireTicks;
	}

	@Override
	public void setFireTicks(int ticks)
	{
		this.fireTicks = ticks;
	}

	@Override
	public void setVisualFire(boolean fire)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isVisualFire()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setFreezeTicks(int ticks)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFrozen()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFreezeTickingLocked()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lockFreezeTicks(boolean locked)
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}


	@Override
	public int getFreezeTicks()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxFreezeTicks()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void remove()
	{
		this.removed = true;
	}

	@Override
	public boolean isDead()
	{
		return !this.removed;
	}

	@Override
	public boolean isValid()
	{
		return !this.removed;
	}

	@Override
	public @NotNull ServerMock getServer()
	{
		return this.server;
	}

	@Override
	@Deprecated
	public Entity getPassenger()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean setPassenger(@NotNull Entity passenger)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<Entity> getPassengers()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPassenger(@NotNull Entity passenger)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removePassenger(@NotNull Entity passenger)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean eject()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getFallDistance()
	{
		return fallDistance;
	}

	@Override
	public void setFallDistance(float distance)
	{
		this.fallDistance = distance;

	}

	@Override
	public void setLastDamageCause(@Nullable EntityDamageEvent event)
	{
		this.lastDamageEvent = event;
	}

	@Override
	public @Nullable EntityDamageEvent getLastDamageCause()
	{
		return this.lastDamageEvent;
	}

	@Override
	public int getTicksLived()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTicksLived(int value)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void playEffect(@NotNull EntityEffect type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.UNKNOWN;
	}

	@Override
	public boolean isInsideVehicle()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean leaveVehicle()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity getVehicle()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomNameVisible(boolean flag)
	{
		customNameVisible = flag;

	}

	@Override
	public boolean isCustomNameVisible()
	{
		return customNameVisible;
	}

	@Override
	public void setGlowing(boolean flag)
	{
		glowingFlag = flag;

	}

	@Override
	public boolean isGlowing()
	{
		return glowingFlag;
	}

	@Override
	public void setInvulnerable(boolean flag)
	{
		invulnerable = flag;
	}

	@Override
	public boolean isInvulnerable()
	{
		return invulnerable;
	}

	@Override
	public boolean isSilent()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSilent(boolean flag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean hasGravity()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGravity(boolean gravity)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getPortalCooldown()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPortalCooldown(int cooldown)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public @NotNull Set<String> getScoreboardTags()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addScoreboardTag(@NotNull String tag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeScoreboardTag(@NotNull String tag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PistonMoveReaction getPistonMoveReaction()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRotation(float yaw, float pitch)
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
	public @NotNull BlockFace getFacing()
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
	public boolean isInWater()
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
	public Entity.@NotNull Spigot spigot()
	{
		return this;
	}

	@Override
	public @NotNull Component name()
	{
		return this.name;
	}

	// Paper start

	@Override
	public @NotNull Component teamDisplayName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull HoverEvent<HoverEvent.ShowEntity> asHoverEvent()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getOrigin()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean fromMobSpawner()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Chunk getChunk()
	{
		return getLocation().getChunk();
	}

	@Override
	public CreatureSpawnEvent.@NotNull SpawnReason getEntitySpawnReason()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInRain()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrRain()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrRainOrBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInLava()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTicking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean spawnAt(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean spawnAt(@NotNull Location location, CreatureSpawnEvent.@NotNull SpawnReason reason)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInPowderedSnow()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
