package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.SculkSensor;
import org.jetbrains.annotations.NotNull;

public class SculkSensorDataMock extends BlockDataMock implements SculkSensor
{

	public SculkSensorDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected SculkSensorDataMock(@NotNull SculkSensorDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Phase getSculkSensorPhase()
	{
		return this.get(BlockDataKey.SCULK_SENSOR_PHASE);
	}

	@Override
	public void setSculkSensorPhase(@NotNull Phase phase)
	{
		this.set(BlockDataKey.SCULK_SENSOR_PHASE, phase);
	}

	@Override
	public int getPower()
	{
		return this.get(BlockDataKey.POWER);
	}

	@Override
	public void setPower(int power)
	{
		Preconditions.checkArgument(0 <= power && power <= getMaximumPower(), "Power must be between 0 and %s", getMaximumPower());
		this.set(BlockDataKey.POWER, power);
	}

	@Override
	public int getMaximumPower()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_POWER);
	}

	@Override
	public boolean isWaterlogged()
	{
		return this.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		this.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	@SuppressWarnings({ "MethodDoesntCallSuperMethod", "java:S2975", "java:S1182" })
	public @NotNull SculkSensorDataMock clone()
	{
		return new SculkSensorDataMock(this);
	}

}
