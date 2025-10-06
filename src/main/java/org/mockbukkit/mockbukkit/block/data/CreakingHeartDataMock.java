package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.data.type.CreakingHeart;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class CreakingHeartDataMock extends BlockDataMock implements CreakingHeart
{

	public CreakingHeartDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CreakingHeartDataMock(@NotNull BlockDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull State getCreakingHeartState()
	{
		return this.get(BlockDataKey.CREAKING_HEART_STATE);
	}

	@Override
	public void setCreakingHeartState(@NotNull State state)
	{
		Preconditions.checkArgument(state != null, "state cannot be null!");
		this.set(BlockDataKey.CREAKING_HEART_STATE, state);
	}

	@Override
	public boolean isNatural()
	{
		return this.get(BlockDataKey.NATURAL);
	}

	@Override
	public void setNatural(boolean natural)
	{
		this.set(BlockDataKey.NATURAL, natural);
	}

	@Override
	public @NotNull Axis getAxis()
	{
		return this.get(BlockDataKey.AXIS);
	}

	@Override
	public void setAxis(@NotNull Axis axis)
	{
		Preconditions.checkArgument(axis != null, "axis cannot be null!");
		this.set(BlockDataKey.AXIS, axis);
	}

	@Override
	public @NotNull Set<Axis> getAxes()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.AXES);
	}

	@Override
	public @NotNull CreakingHeartDataMock clone()
	{
		return new CreakingHeartDataMock(this);
	}

}
