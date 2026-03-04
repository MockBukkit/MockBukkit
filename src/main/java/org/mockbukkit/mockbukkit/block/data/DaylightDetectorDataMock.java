package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.DaylightDetector;
import org.jetbrains.annotations.NotNull;

public class DaylightDetectorDataMock extends BlockDataMock implements DaylightDetector
{

	public DaylightDetectorDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected DaylightDetectorDataMock(@NotNull DaylightDetectorDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isInverted()
	{
		return this.get(BlockDataKey.IS_INVERTED);
	}

	@Override
	public void setInverted(boolean inverted)
	{
		this.set(BlockDataKey.IS_INVERTED, inverted);
	}

	@Override
	public int getPower()
	{
		return this.get(BlockDataKey.POWER);
	}

	@Override
	public void setPower(int power)
	{
		Preconditions.checkArgument(power >= 0 && power <= this.getMaximumPower(), "Power must be between 0 and %s", this.getMaximumPower());
		this.set(BlockDataKey.POWER, power);
	}

	@Override
	public int getMaximumPower()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_POWER);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull DaylightDetectorDataMock clone()
	{
		return new DaylightDetectorDataMock(this);
	}

}
