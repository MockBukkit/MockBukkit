package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.MangrovePropagule;
import org.jetbrains.annotations.NotNull;

public class MangrovePropaguleDataMock extends BlockDataMock implements MangrovePropagule
{

	public MangrovePropaguleDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected MangrovePropaguleDataMock(@NotNull MangrovePropaguleDataMock other)
	{
		super(other);
	}

	@Override
	public int getAge()
	{
		return this.get(BlockDataKey.AGE_KEY);
	}

	@Override
	public void setAge(int age)
	{
		Preconditions.checkArgument(age >= 0 && age <= this.getMaximumAge(), "The age must be between 0 and %s", this.getMaximumAge());
		this.set(BlockDataKey.AGE_KEY, age);
	}

	@Override
	public int getMaximumAge()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_AGE);
	}

	@Override
	public boolean isHanging()
	{
		return this.get(BlockDataKey.HANGING_KEY);
	}

	@Override
	public void setHanging(boolean hanging)
	{
		this.set(BlockDataKey.HANGING_KEY, hanging);
	}

	@Override
	public boolean isWaterlogged()
	{
		return super.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		super.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	public int getStage()
	{
		return this.get(BlockDataKey.STAGE_KEY);
	}

	@Override
	public void setStage(int stage)
	{
		Preconditions.checkArgument(stage >= 0 && stage <= this.getMaximumStage(), "The stage must be between 0 and %s", this.getMaximumStage());
		this.set(BlockDataKey.STAGE_KEY, stage);
	}

	@Override
	public int getMaximumStage()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_STAGE);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull MangrovePropaguleDataMock clone()
	{
		return new MangrovePropaguleDataMock(this);
	}

}
