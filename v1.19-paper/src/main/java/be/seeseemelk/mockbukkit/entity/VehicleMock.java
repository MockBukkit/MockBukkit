package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Vehicle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class VehicleMock extends EntityMock implements Vehicle
{

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
