package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RedstoneWallTorchDataMock extends BlockDataMock implements RedstoneWallTorch
{

	/**
	 * Constructs a new {@link RedstoneWallTorchDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public RedstoneWallTorchDataMock(@NotNull Material material)
	{
		super(material);
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
	public boolean isLit()
	{
		return this.get(BlockDataKey.LIT);
	}

	@Override
	public void setLit(boolean lit)
	{
		this.set(BlockDataKey.LIT, lit);
	}

}
