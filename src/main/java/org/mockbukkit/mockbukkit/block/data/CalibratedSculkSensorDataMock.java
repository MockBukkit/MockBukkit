package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.CalibratedSculkSensor;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class CalibratedSculkSensorDataMock extends SculkSensorDataMock implements CalibratedSculkSensor
{

	public CalibratedSculkSensorDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CalibratedSculkSensorDataMock(@NotNull CalibratedSculkSensorDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public @NotNull CalibratedSculkSensorDataMock clone()
	{
		return new CalibratedSculkSensorDataMock(this);
	}

}
