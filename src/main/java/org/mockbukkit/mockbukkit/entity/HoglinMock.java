package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hoglin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Hoglin}.
 *
 * @see AnimalsMock
 */
public class HoglinMock extends AnimalsMock implements Hoglin
{

	private boolean immuneToZombification = false;
	private boolean huntable = false;
	private int conversionTime = -1;

	/**
	 * Constructs a new Animal on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public HoglinMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.HOGLIN;
	}

	@Override
	public boolean isImmuneToZombification()
	{
		return this.immuneToZombification;
	}

	@Override
	public void setImmuneToZombification(boolean isImmune)
	{
		this.immuneToZombification = isImmune;
	}

	@Override
	public boolean isAbleToBeHunted()
	{
		return this.huntable;
	}

	@Override
	public void setIsAbleToBeHunted(boolean isHuntable)
	{
		this.huntable = isHuntable;
	}

	@Override
	public int getConversionTime()
	{
		Preconditions.checkState(this.isConverting(), "Entity not converting");
		return this.conversionTime;
	}

	@Override
	public void setConversionTime(int time)
	{
		if (time < 0)
		{
			this.conversionTime = -1;
			this.setImmuneToZombification(false);
		} else
		{
			this.conversionTime = time;
		}
	}

	@Override
	public boolean isConverting()
	{
		return !this.isImmuneToZombification() && this.hasAI();
	}

}
