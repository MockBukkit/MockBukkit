package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link PiglinBrute}.
 *
 * @see PiglinAbstractMock
 */
public class PiglinBruteMock extends PiglinAbstractMock implements PiglinBrute
{

	/**
	 * Constructs a new {@link PiglinBruteMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PiglinBruteMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PIGLIN_BRUTE;
	}

}
