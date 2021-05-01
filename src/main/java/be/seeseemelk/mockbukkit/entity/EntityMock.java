package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedTransferQueue;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pose;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionRemovedExecutor;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;

public abstract class EntityMock extends Entity.Spigot implements Entity, MessageTarget
{
	private final ServerMock server;
	private final UUID uuid;
	private Location location;
	private boolean teleported;
	private TeleportCause teleportCause;
	private MetadataTable metadataTable = new MetadataTable();
	private PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();
	private boolean operator = false;
	private String name = "entity";
	private final Queue<String> messages = new LinkedTransferQueue<>();
	private final Set<PermissionAttachment> permissionAttachments = new HashSet<>();
	private Vector velocity = new Vector(0, 0, 0);
	private float fallDistance;
	private int fireTicks = -20;
	private int maxFireTicks = 20;

	public EntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
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
	public void assertLocation(Location expectedLocation, double maximumDistance)
	{
		double distance = location.distance(expectedLocation);
		assertEquals(expectedLocation.getWorld(), location.getWorld());
		assertTrue(String.format("Distance was <%.3f> but should be less than or equal to <%.3f>", distance,
		                         maximumDistance), distance <= maximumDistance);
	}

	/**
	 * Assert that the player teleported to a certain location within a certain distance to a given location. Also
	 * clears the teleported flag.
	 *
	 * @param expectedLocation The location the player should be at.
	 * @param maximumDistance  The distance the player may maximumly be separated from the expected location.
	 */
	public void assertTeleported(Location expectedLocation, double maximumDistance)
	{
		assertTrue("Player did not teleport", teleported);
		assertLocation(expectedLocation, maximumDistance);
		teleported = false;
	}

	/**
	 * Assert that the player hasn't teleported. Also clears the teleported flag.
	 */
	public void assertNotTeleported()
	{
		assertFalse("Player was teleported", teleported);
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
	public UUID getUniqueId()
	{
		return uuid;
	}

	@Override
	public Location getLocation()
	{
		return location.clone();
	}

	@Override
	public Location getLocation(Location loc)
	{
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
	public void setLocation(Location location)
	{
		this.location = location;
	}

	@Override
	public World getWorld()
	{
		return location.getWorld();
	}

	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
	{
		metadataTable.setMetadata(metadataKey, newMetadataValue);
	}

	@Override
	public List<MetadataValue> getMetadata(String metadataKey)
	{
		return metadataTable.getMetadata(metadataKey);
	}

	@Override
	public boolean hasMetadata(String metadataKey)
	{
		return metadataTable.hasMetadata(metadataKey);
	}

	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin)
	{
		metadataTable.removeMetadata(metadataKey, owningPlugin);
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return persistentDataContainer;
	}

	@Override
	public boolean teleport(Location location)
	{
		return teleport(location, TeleportCause.PLUGIN);
	}

	@Override
	public boolean teleport(Location location, TeleportCause cause)
	{
		this.location = location;
		teleported = true;
		teleportCause = cause;
		return true;
	}

	@Override
	public boolean teleport(Entity destination)
	{
		return teleport(destination, TeleportCause.PLUGIN);
	}

	@Override
	public boolean teleport(Entity destination, TeleportCause cause)
	{
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
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of this entity.
	 *
	 * @param name The new name of the entity.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void sendMessage(String message)
	{
		sendMessage(null, message);
	}

	@Override
	public void sendMessage(String[] messages)
	{
		sendMessage(null, messages);
	}

	@Override
	public void sendMessage(UUID sender, String message)
	{
		messages.add(message);
	}

	@Override
	public void sendMessage(UUID sender, String[] messages)
	{
		for (String message : messages)
		{
			sendMessage(message);
		}
	}

	@Override
	public String nextMessage()
	{
		return messages.poll();
	}

	@Override
	public boolean isPermissionSet(String name)
	{
		for (PermissionAttachment attachment : permissionAttachments)
		{
			Map<String, Boolean> permissions = attachment.getPermissions();

			if (permissions.containsKey(name) && permissions.get(name).booleanValue())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPermissionSet(Permission perm)
	{
		return isPermissionSet(perm.getName().toLowerCase(Locale.ENGLISH));
	}

	@Override
	public boolean hasPermission(String name)
	{
		if (isPermissionSet(name))
		{
			return true;
		}

		Permission perm = server.getPluginManager().getPermission(name);
		return perm != null ? hasPermission(perm) : Permission.DEFAULT_PERMISSION.getValue(isOp());
	}

	@Override
	public boolean hasPermission(Permission perm)
	{
		return isPermissionSet(perm) || perm.getDefault().getValue(isOp());
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
	{
		PermissionAttachment attachment = addAttachment(plugin);
		attachment.setPermission(name, value);
		return attachment;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		PermissionAttachment attachment = new PermissionAttachment(plugin, this);
		permissionAttachments.add(attachment);
		return attachment;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment)
	{
		if (attachment == null)
		{
			throw new IllegalArgumentException("Attachment cannot be null");
		}

		if (permissionAttachments.contains(attachment))
		{
			permissionAttachments.remove(attachment);
			PermissionRemovedExecutor ex = attachment.getRemovalCallback();

			if (ex != null)
			{
				ex.attachmentRemoved(attachment);
			}

			recalculatePermissions();
		}
		else
		{
			throw new IllegalArgumentException("Given attachment is not part of Permissible object " + this);
		}
	}

	@Override
	public void recalculatePermissions()
	{

	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		HashSet<PermissionAttachmentInfo> permissionAttachmentInfos = new HashSet<>();

		for (PermissionAttachment permissionAttachment : permissionAttachments)
		{
			for (Map.Entry<String, Boolean> entry : permissionAttachment.getPermissions().entrySet())
			{
				permissionAttachmentInfos.add(new PermissionAttachmentInfo(this, entry.getKey(), permissionAttachment, entry.getValue()));
			}
		}

		return permissionAttachmentInfos;
	}

	@Override
	public String getCustomName()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCustomName(String name)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setVelocity(@NotNull Vector velocity)
	{
		this.velocity = velocity;
	}

	@Override
	public Vector getVelocity()
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
	public List<Entity> getNearbyEntities(double x, double y, double z)
	{
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
	public void remove()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isDead()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isValid()
	{
		return !isDead();
	}

	@Override
	public ServerMock getServer()
	{
		return server;
	}

	@Override
	public Entity getPassenger()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setPassenger(Entity passenger)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<Entity> getPassengers()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addPassenger(Entity passenger)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removePassenger(Entity passenger)
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
	public void setLastDamageCause(EntityDamageEvent event)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
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
	public void playEffect(EntityEffect type)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

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
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isCustomNameVisible()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGlowing(boolean flag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isGlowing()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setInvulnerable(boolean flag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isInvulnerable()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
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
	public Set<String> getScoreboardTags()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addScoreboardTag(String tag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeScoreboardTag(String tag)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction()
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
	public Pose getPose()
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
	public Entity.Spigot spigot()
	{
		return this;
	}
}
