package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Vehicle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Vehicle}.
 *
 * @see EntityMock
 */
public abstract class VehicleMock extends EntityMock implements Vehicle
{

	/**
	 * Constructs a new {@link VehicleMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected VehicleMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull String toString()
	{
		return "VehicleMock{passenger=" + getPassenger() + '}';
	}

}
