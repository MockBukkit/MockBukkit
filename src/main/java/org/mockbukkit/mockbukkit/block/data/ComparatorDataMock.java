package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Comparator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;

public class ComparatorDataMock extends BlockDataMock implements Comparator
{

	public ComparatorDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected ComparatorDataMock(@NotNull ComparatorDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Mode getMode()
	{
		return this.get(BlockDataKey.COMPARATOR_MODE);
	}

	@Override
	public void setMode(@NotNull Mode mode)
	{
		Preconditions.checkArgument(mode != null, "mode cannot be null!");
		this.set(BlockDataKey.COMPARATOR_MODE, mode);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(FACING, blockFace);
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

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull ComparatorDataMock clone()
	{
		return new ComparatorDataMock(this);
	}

}
