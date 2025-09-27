package org.mockbukkit.mockbukkit.block;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Observer;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.block.data.BlockDataKey;
import org.mockbukkit.mockbukkit.block.data.BlockDataLimitation;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;

import java.util.Set;

public class ObserverDataMock extends BlockDataMock implements Observer
{

	public ObserverDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected ObserverDataMock(@NotNull BlockDataMock other)
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
		Preconditions.checkArgument(blockFace.isCartesian(), "Invalid face, only cartesian face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public boolean isPowered()
	{
		return this.get(BlockDataKey.POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		this.set(BlockDataKey.POWERED, powered);
	}

}
