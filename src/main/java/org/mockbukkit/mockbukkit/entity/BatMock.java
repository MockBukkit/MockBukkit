package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of an {@link Bat}.
 *
 * @see AmbientMock
 */
public class BatMock extends AmbientMock implements Bat
{

	private boolean awake = true;
	private Location targetPosition;

	/**
	 * Constructs a new {@link BatMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public BatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isAwake()
	{
		return this.awake;
	}

	@Override
	public void setAwake(boolean state)
	{
		this.awake = state;
	}

	@Override
	public @Nullable Location getTargetLocation()
	{
		return this.targetPosition;
	}

	@Override
	public void setTargetLocation(@Nullable Location location)
	{
		this.targetPosition = location;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.BAT;
	}

}
