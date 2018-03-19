package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedTransferQueue;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.command.MessageTarget;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;

public abstract class EntityMock implements Entity, MessageTarget
{
	private final UUID uuid;
	private Location location;
	private boolean teleported;
	private TeleportCause teleportCause;
	private MetadataTable metadataTable = new MetadataTable();
	private boolean operator = false;
	private String name = "entity";
	private final Queue<String> messages = new LinkedTransferQueue<>();
	private final Set<PermissionAttachment> permissionAttachments = new HashSet<>();
	
	public EntityMock(UUID uuid)
	{
		this.uuid = uuid;
		
		if (Bukkit.getWorlds().size() > 0)
			location = Bukkit.getWorlds().get(0).getSpawnLocation();
		else
			location = new Location(null, 0, 0, 0);
	}
	
	@Override
	public int hashCode()
	{
		return uuid.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof EntityMock)
			return uuid.equals(((EntityMock) obj).getUniqueId());
		return false;
	}
	
	/**
	 * Assert that the actual location of the player is within a certain distance to a given location.
	 * @param expectedLocation The location the player should be at.
	 * @param maximumDistance The distance the player may maximumly be separated from the expected location. 
	 */
	public void assertLocation(Location expectedLocation, double maximumDistance)
	{
		double distance = location.distance(expectedLocation);
		assertEquals(expectedLocation.getWorld(), location.getWorld());
		assertTrue(String.format("Distance was <%.3f> but should be less than or equal to <%.3f>", distance,
				maximumDistance), distance <= maximumDistance);
	}
	
	/**
	 * Assert that the player teleported to a certain location within a certain distance to a given location.
	 * Also clears the teleported flag.
	 * @param expectedLocation The location the player should be at.
	 * @param maximumDistance The distance the player may maximumly be separated from the expected location.
	 */
	public void assertTeleported(Location expectedLocation, double maximumDistance)
	{
		assertTrue("Player did not teleport", teleported);
		assertLocation(expectedLocation, maximumDistance);
		teleported = false;
	}
	
	/**
	 * Assert that the player hasn't teleported.
	 * Also clears the teleported flag.
	 */
	public void assertNotTeleported()
	{
		assertFalse("Player was teleported", teleported);
		teleported = false;
	}
	
	/**
	 * Checks if the player has been teleported since the last assert or {@link #clearTeleported}.
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
	 * Sets the location of the entity.
	 * Note that this will not fire a teleport event.
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
	 * @param name The new name of the entity.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void sendMessage(String message)
	{
		messages.add(message);
	}

	@Override
	public void sendMessage(String[] messages)
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
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isPermissionSet(Permission perm)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean hasPermission(String name)
	{
		for (PermissionAttachment attachment : permissionAttachments)
		{
			Map<String, Boolean> permissions = attachment.getPermissions();
			if (permissions.containsKey(name) && permissions.get(name) == true)
				return true;
		}
		return false;
	}
	
	@Override
	public boolean hasPermission(Permission perm)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
		
	}
	
	@Override
	public void recalculatePermissions()
	{
		
	}
	
	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
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
	public void setVelocity(Vector velocity)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Vector getVelocity()
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
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getMaxFireTicks()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setFireTicks(int ticks)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Server getServer()
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setFallDistance(float distance)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
		
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
	public EntityType getType()
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
	
}
