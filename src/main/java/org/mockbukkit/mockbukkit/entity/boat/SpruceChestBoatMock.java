package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.SpruceChestBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.ChestBoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link SpruceChestBoat}.
 *
 * @see ChestBoatMock
 */
public class SpruceChestBoatMock extends ChestBoatMock implements SpruceChestBoat
{

	public SpruceChestBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SPRUCE_CHEST_BOAT;
	}

}
