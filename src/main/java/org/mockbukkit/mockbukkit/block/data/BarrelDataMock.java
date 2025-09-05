package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Barrel;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BarrelDataMock extends BlockDataMock implements Barrel
{

	/**
	 * Constructs a new {@link BarrelDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public BarrelDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link BarrelDataMock} based on an existing {@link BarrelDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected BarrelDataMock(BarrelDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(this.getFaces().contains(facing), "BlockFace %s is not a valid BlockFace.", facing);
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
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BarrelDataMock clone()
	{
		return new BarrelDataMock(this);
	}

}
