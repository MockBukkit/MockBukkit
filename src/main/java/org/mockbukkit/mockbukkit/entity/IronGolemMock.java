package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link IronGolem}.
 *
 * @see GolemMock
 */
public class IronGolemMock extends GolemMock implements IronGolem
{
	private boolean playerCreated;

	/**
	 * Constructs a new {@link IronGolemMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public IronGolemMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isPlayerCreated()
	{
		return this.playerCreated;
	}

	@Override
	public void setPlayerCreated(boolean playerCreated)
	{
		this.playerCreated = playerCreated;
	}

	@Override
	public @Nullable Sound getDeathSound()
	{
		return Sound.ENTITY_IRON_GOLEM_DEATH;
	}

	@Override
	public @Nullable Sound getHurtSound()
	{
		return Sound.ENTITY_IRON_GOLEM_HURT;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.IRON_GOLEM;
	}

}
