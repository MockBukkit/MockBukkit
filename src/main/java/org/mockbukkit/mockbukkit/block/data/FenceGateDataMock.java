package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Gate;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FenceGateDataMock extends BlockDataMock implements Gate
{

	/**
	 * Constructs a new {@link FenceGateDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public FenceGateDataMock(@NotNull Material material)
	{
		super(material);
		setInWall(false);
	}

	/**
	 * Create a new {@link FenceGateDataMock} based on an existing {@link FenceGateDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected FenceGateDataMock(@NotNull FenceGateDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isInWall()
	{
		return this.get(BlockDataKey.IN_WALL);
	}

	@Override
	public void setInWall(boolean inWall)
	{
		this.set(BlockDataKey.IN_WALL, inWall);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(facing != null, "blockFace cannot be null!");
		Preconditions.checkArgument(facing.isCartesian() && facing.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public boolean isOpen()
	{
		return this.get(BlockDataKey.OPEN);
	}

	@Override
	public void setOpen(boolean open)
	{
		this.set(BlockDataKey.OPEN, open);
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
	public @NotNull FenceGateDataMock clone()
	{
		return new FenceGateDataMock(this);
	}

}
