package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Skeleton}.
 *
 * @see AbstractSkeletonMock
 */
public class SkeletonMock extends AbstractSkeletonMock implements Skeleton
{

	private boolean isConverting = false;
	private int conversionTime = 0;
	private int inPowderedSnow = 0;

	/**
	 * Constructs a new {@link SkeletonMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SkeletonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isConverting()
	{
		return this.isConverting;
	}

	/**
	 * Sets if the Skeleton is converting
	 *
	 * @param state If the skeleton is converting
	 */
	public void setConverting(boolean state)
	{
		this.isConverting = state;
	}

	@Override
	public int getConversionTime()
	{
		Preconditions.checkState(this.isConverting(), "Entity is not converting");
		return this.conversionTime;
	}

	@Override
	public void setConversionTime(int time)
	{
		if (time < 0)
		{
			this.conversionTime = -1;
			return;
		}

		this.conversionTime = time;
	}

	@Override
	public int inPowderedSnowTime()
	{
		return this.inPowderedSnow;
	}

	/**
	 * Sets the Time the Entity is in powdered Snow
	 *
	 * @param time The Time in ticks
	 */
	public void setInPowderedSnowTime(int time)
	{
		Preconditions.checkArgument(time > 0, "Time cannot be smaller than 1");
		this.inPowderedSnow = time;
	}

	@Override
	@Deprecated(since = "1.17")
	public @NotNull SkeletonType getSkeletonType()
	{
		return SkeletonType.NORMAL;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SKELETON;
	}

}
