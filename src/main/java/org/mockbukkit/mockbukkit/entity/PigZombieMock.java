package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PigZombieMock extends ZombieMock implements PigZombie
{

	private int anger = 0;

	/**
	 * Constructs a new {@link PigZombieMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PigZombieMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getAnger()
	{
		return this.anger;
	}

	@Override
	public void setAnger(int level)
	{
		this.anger = level;
	}

	@Override
	public void setAngry(boolean angry)
	{
		this.setAnger(angry ? 400 : 0);
	}

	@Override
	public boolean isAngry()
	{
		return this.getAnger() > 0;
	}

	@Override
	public boolean isConverting()
	{
		return false;
	}

	@Override
	public int getConversionTime()
	{
		throw new UnsupportedOperationException("Not supported by this Entity.");
	}

	@Override
	public void setConversionTime(int conversionTime)
	{
		throw new UnsupportedOperationException("Not supported by this Entity.");
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ZOMBIFIED_PIGLIN;
	}

}
