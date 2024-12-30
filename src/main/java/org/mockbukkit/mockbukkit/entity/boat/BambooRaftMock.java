package org.mockbukkit.mockbukkit.entity.boat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.boat.BambooRaft;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.BoatMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link BambooRaft}.
 *
 * @see BoatMock
 */
public class BambooRaftMock extends BoatMock implements BambooRaft
{

	public BambooRaftMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.BAMBOO_RAFT;
	}

}
