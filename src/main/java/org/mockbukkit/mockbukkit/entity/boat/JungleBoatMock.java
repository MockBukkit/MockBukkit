package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.JungleBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.BoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link JungleBoat}.
 *
 * @see BoatMock
 */
public class JungleBoatMock extends BoatMock implements JungleBoat
{

	public JungleBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.JUNGLE_BOAT;
	}

}
