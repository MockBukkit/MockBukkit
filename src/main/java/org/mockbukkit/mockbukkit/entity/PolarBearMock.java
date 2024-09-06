package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PolarBear;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link PolarBear}.
 *
 * @see AnimalsMock
 */
public class PolarBearMock extends AnimalsMock implements PolarBear
{

	private boolean isStanding = false;

	/**
	 * Constructs a new {@link PolarBearMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PolarBearMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isStanding()
	{
		return this.isStanding;
	}

	@Override
	public void setStanding(boolean standing)
	{
		this.isStanding = standing;
	}

	@Override
	public boolean canBreed()
	{
		return false;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.POLAR_BEAR;
	}

}
