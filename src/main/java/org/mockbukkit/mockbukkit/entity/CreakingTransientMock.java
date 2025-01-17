package org.mockbukkit.mockbukkit.entity;

import org.bukkit.MinecraftExperimental;
import org.bukkit.entity.CreakingTransient;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link CreakingTransient}.
 *
 * @see CreakingMock
 */
@ApiStatus.Experimental
@MinecraftExperimental(MinecraftExperimental.Requires.WINTER_DROP)
public class CreakingTransientMock extends CreakingMock implements CreakingTransient
{

	/**
	 * Constructs a new {@link CreakingTransient} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CreakingTransientMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CREAKING_TRANSIENT;
	}

}
