package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.DarkOakChestBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.ChestBoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link DarkOakChestBoat}.
 *
 * @see ChestBoatMock
 */
public class DarkOakChestBoatMock extends ChestBoatMock implements DarkOakChestBoat
{

	public DarkOakChestBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.DARK_OAK_CHEST_BOAT;
	}

}
