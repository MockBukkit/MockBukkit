package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.Golem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Golem}.
 *
 * @see CreatureMock
 */
public class GolemMock extends CreatureMock implements Golem
{

	/**
	 * Constructs a new {@link GolemMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected GolemMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable Sound getAmbientSound()
	{
		return null;
	}

	@Override
	public @Nullable Sound getHurtSound()
	{
		return null;
	}

	@Override
	public @Nullable Sound getDeathSound()
	{
		return null;
	}

}
