package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Fox}.
 *
 * @see AnimalsMock
 */
public class FoxMock extends AnimalsMock implements Fox
{

	private Type foxType = Type.RED;
	private boolean crouching = false;
	private boolean sleeping = false;
	private boolean facePlanted = false;
	private AnimalTamer firstTrustedPlayer = null;
	private AnimalTamer secondTrustedPlayer = null;
	private boolean interested = false;
	private boolean leaping = false;
	private boolean defending = false;
	private boolean sitting = false;

	/**
	 * Constructs a new {@link FoxMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public FoxMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Type getFoxType()
	{
		return this.foxType;
	}

	@Override
	public void setFoxType(@NotNull Type type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		this.foxType = type;
	}

	@Override
	public boolean isCrouching()
	{
		return this.crouching;
	}

	@Override
	public void setCrouching(boolean crouching)
	{
		this.crouching = crouching;
	}

	@Override
	public void setSleeping(boolean sleeping)
	{
		this.sleeping = sleeping;
	}

	@Override
	public boolean isSleeping()
	{
		return this.sleeping;
	}

	@Override
	public @Nullable AnimalTamer getFirstTrustedPlayer()
	{
		return this.firstTrustedPlayer;
	}

	@Override
	public void setFirstTrustedPlayer(@Nullable AnimalTamer player)
	{
		this.firstTrustedPlayer = player;
	}

	@Override
	public @Nullable AnimalTamer getSecondTrustedPlayer()
	{
		return this.secondTrustedPlayer;
	}

	@Override
	public void setSecondTrustedPlayer(@Nullable AnimalTamer player)
	{
		this.secondTrustedPlayer = player;
	}

	@Override
	public boolean isFaceplanted()
	{
		return this.facePlanted;
	}

	@Override
	public void setFaceplanted(boolean faceplanted)
	{
		this.facePlanted = faceplanted;
	}

	@Override
	public void setInterested(boolean interested)
	{
		this.interested = interested;
	}

	@Override
	public boolean isInterested()
	{
		return this.interested;
	}

	@Override
	public void setLeaping(boolean leaping)
	{
		this.leaping = leaping;
	}

	@Override
	public boolean isLeaping()
	{
		return this.leaping;
	}

	@Override
	public void setDefending(boolean defending)
	{
		this.defending = defending;
	}

	@Override
	public boolean isDefending()
	{
		return this.defending;
	}

	@Override
	public boolean isSitting()
	{
		return this.sitting;
	}

	@Override
	public void setSitting(boolean sitting)
	{
		this.sitting = sitting;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.FOX;
	}

}
