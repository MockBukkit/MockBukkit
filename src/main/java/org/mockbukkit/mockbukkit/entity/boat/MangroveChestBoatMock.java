package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.MangroveChestBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.ChestBoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link MangroveChestBoat}.
 *
 * @see ChestBoatMock
 */
public class MangroveChestBoatMock extends ChestBoatMock implements MangroveChestBoat
{

	public MangroveChestBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.MANGROVE_CHEST_BOAT;
	}

}
