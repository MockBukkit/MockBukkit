package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.Leaves;
import org.jetbrains.annotations.NotNull;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.DISTANCE;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.PERSISTENT;

public class LeavesDataMock extends BlockDataMock implements Leaves
{

	private final static int MAX_DISTANCE = 7;
	private final static int MIN_DISTANCE = 1;

	public LeavesDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected LeavesDataMock(@NotNull LeavesDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isPersistent()
	{
		return super.get(PERSISTENT);
	}

	@Override
	public void setPersistent(boolean persistent)
	{
		super.set(PERSISTENT, persistent);
	}

	@Override
	public int getDistance()
	{
		return super.get(DISTANCE);
	}

	@Override
	public void setDistance(int distance)
	{
		Preconditions.checkArgument(distance >= MIN_DISTANCE, "The distance must be >= %s", MIN_DISTANCE);
		Preconditions.checkArgument(distance <= MAX_DISTANCE, "The distance must be <= %s", MAX_DISTANCE);

		super.set(DISTANCE, distance);
	}

	@Override
	public int getMaximumDistance()
	{
		return MAX_DISTANCE;
	}

	@Override
	public int getMinimumDistance()
	{
		return MIN_DISTANCE;
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
	public @NotNull LeavesDataMock clone()
	{
		return new LeavesDataMock(this);
	}

}
