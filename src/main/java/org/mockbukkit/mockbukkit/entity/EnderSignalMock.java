package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EnderSignalMock extends EntityMock implements EnderSignal
{

	private static final String TARGET_NOT_SET_ERROR = "Target location has not been set! Mockbukkit requires that the " +
			"target location be set before the location is retrieved.";

	private Location targetLocation = null;
	private boolean surviveAfterDeath = true;
	private ItemStack item = new ItemStackMock(Material.ENDER_EYE);
	private int life = 0;

	/**
	 * Constructs a new EntityMock on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EnderSignalMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Location getTargetLocation()
	{
		Preconditions.checkState(targetLocation != null, TARGET_NOT_SET_ERROR);
		return targetLocation;
	}

	@Override
	public void setTargetLocation(@NotNull Location location)
	{
		this.setTargetLocation(location, true);
	}

	@Override
	public void setTargetLocation(@NotNull Location location, boolean update)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		Preconditions.checkArgument(this.getWorld().equals(location.getWorld()),
				"Cannot target EnderSignal across worlds");
		this.targetLocation = location.toBlockLocation();

		if (update)
		{
			//ignored
		}
	}

	@Override
	public boolean getDropItem()
	{
		return this.surviveAfterDeath;
	}

	@Override
	public void setDropItem(boolean drop)
	{
		this.surviveAfterDeath = drop;
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		return this.item;
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		if (item == null)
		{
			this.item = new ItemStackMock(Material.ENDER_EYE);
			return;
		}

		this.item = item.clone();
	}

	@Override
	public int getDespawnTimer()
	{
		return this.life;
	}

	@Override
	public void setDespawnTimer(int timer)
	{
		this.life = timer;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.EYE_OF_ENDER;
	}

}
