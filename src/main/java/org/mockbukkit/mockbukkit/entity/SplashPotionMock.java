package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.SplashPotion;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link SplashPotion}.
 *
 * @see ThrownPotionMock
 */
public class SplashPotionMock extends ThrownPotionMock implements SplashPotion
{

	/**
	 * Constructs a new {@link SplashPotion} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SplashPotionMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SPLASH_POTION;
	}

}
