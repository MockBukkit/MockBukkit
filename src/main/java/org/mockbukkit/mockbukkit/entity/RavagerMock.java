package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ravager;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Ravager}.
 *
 * @see RaiderMock
 */
public class RavagerMock extends RaiderMock implements Ravager
{
	private int attackTicks;
	private int stunnedTicks;
	private int roarTicks;

	/**
	 * Constructs a new {@link RavagerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public RavagerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getAttackTicks()
	{
		return this.attackTicks;
	}

	@Override
	public void setAttackTicks(int ticks)
	{
		this.attackTicks = ticks;
	}

	@Override
	public int getStunnedTicks()
	{
		return this.stunnedTicks;
	}

	@Override
	public void setStunnedTicks(int ticks)
	{
		this.stunnedTicks = ticks;
	}

	@Override
	public int getRoarTicks()
	{
		return this.roarTicks;
	}

	@Override
	public void setRoarTicks(int ticks)
	{
		this.roarTicks = ticks;
	}

	@Override
	public @NotNull Sound getCelebrationSound()
	{
		return Sound.ENTITY_RAVAGER_CELEBRATE;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.RAVAGER;
	}

}
