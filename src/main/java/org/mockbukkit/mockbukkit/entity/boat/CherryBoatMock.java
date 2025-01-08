package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.CherryBoat;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.BoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link CherryBoat}.
 *
 * @see BoatMock
 */
public class CherryBoatMock extends BoatMock implements CherryBoat
{

	public CherryBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CHERRY_BOAT;
	}

}
