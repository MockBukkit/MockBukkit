package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.SpruceBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.BoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link SpruceBoat}.
 *
 * @see BoatMock
 */
public class SpruceBoatMock extends BoatMock implements SpruceBoat
{

	public SpruceBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SPRUCE_BOAT;
	}

}
