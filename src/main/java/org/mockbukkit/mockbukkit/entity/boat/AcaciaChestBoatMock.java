package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.AcaciaChestBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.ChestBoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link AcaciaChestBoat}.
 *
 * @see ChestBoatMock
 */
public class AcaciaChestBoatMock extends ChestBoatMock implements AcaciaChestBoat
{

	public AcaciaChestBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ACACIA_CHEST_BOAT;
	}

}
