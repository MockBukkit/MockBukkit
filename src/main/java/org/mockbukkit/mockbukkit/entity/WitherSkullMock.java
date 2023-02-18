package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link WitherSkull}.
 *
 * @see FireballMock
 */
public class WitherSkullMock extends FireballMock implements WitherSkull
{

	private boolean charged;

	/**
	 * Constructs a new {@link WitherSkullMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WitherSkullMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setCharged(boolean charged)
	{
		this.charged = charged;
	}

	@Override
	public boolean isCharged()
	{
		return this.charged;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WITHER_SKULL;
	}

}
