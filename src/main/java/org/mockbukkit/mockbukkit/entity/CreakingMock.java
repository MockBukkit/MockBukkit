package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Creaking;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link Creaking}.
 *
 * @see MonsterMock
 */
@ApiStatus.Experimental
public class CreakingMock extends MonsterMock implements Creaking
{

	/**
	 * Constructs a new {@link Creaking} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CreakingMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CREAKING;
	}

}
