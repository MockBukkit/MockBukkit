package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link EnderCrystal}.
 *
 * @see EntityMock
 */
public class EnderCrystalMock extends EntityMock implements EnderCrystal
{

	private boolean showBottom = false;

	private Location beamTarget = null;

	/**
	 * Constructs a new EntityMock on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EnderCrystalMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isShowingBottom()
	{
		return this.showBottom;
	}

	@Override
	public void setShowingBottom(boolean showing)
	{
		this.showBottom = showing;
	}

	@Override
	public @Nullable Location getBeamTarget()
	{
		return this.beamTarget;
	}

	@Override
	public void setBeamTarget(@Nullable Location location)
	{
		if (location == null)
		{
			this.beamTarget = null;
		} else if (location.getWorld() != this.getWorld())
		{
			throw new IllegalArgumentException("Cannot set beam target location to different world");
		} else
		{
			this.beamTarget = location.toBlockLocation();
		}
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.END_CRYSTAL;
	}

}
