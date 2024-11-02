package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SilverfishMock extends MonsterMock implements Silverfish
{

	/**
	 * Constructs a new {@link Silverfish} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SilverfishMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SILVERFISH;
	}

}
