package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DoorDataMock extends BlockDataMock implements Door
{

	/**
	 * Constructs a new {@link DoorDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public DoorDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link DoorDataMock} based on an existing {@link DoorDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected DoorDataMock(@NotNull DoorDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Hinge getHinge()
	{
		return this.get(BlockDataKey.HINGE);
	}

	@Override
	public void setHinge(@NotNull Hinge hinge)
	{
		this.set(BlockDataKey.HINGE, hinge);
	}

	@Override
	public @NotNull Half getHalf()
	{
		return BisectedDataMock.fromString(this.get(BlockDataKey.HALF_MULTI_BLOCK));
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		this.set(BlockDataKey.HALF_MULTI_BLOCK, BisectedDataMock.toString(half));
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
	public @NotNull DoorDataMock clone()
	{
		return new DoorDataMock(this);
	}

}
