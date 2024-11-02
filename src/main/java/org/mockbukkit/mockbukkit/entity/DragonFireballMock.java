package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link DragonFireball}.
 *
 * @see FireballMock
 */
public class DragonFireballMock extends FireballMock implements DragonFireball
{

	/**
	 * Constructs a new {@link DragonFireballMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public DragonFireballMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.DRAGON_FIREBALL;
	}

}
