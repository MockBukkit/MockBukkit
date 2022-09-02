package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SkeletonMock extends AbstractSkeletonMock implements Skeleton
{

	private boolean isConverting = false;
	private int conversionTime = 0;
	private int inPowderedSnow = 0;

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
	@Deprecated
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
