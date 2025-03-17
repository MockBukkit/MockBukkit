package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.DecoratedPot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DecoratedPotDataMock extends BlockDataMock implements DecoratedPot
{


	/**
	 * Constructs a new {@link DecoratedPotDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public DecoratedPotDataMock()
	{
		super(Material.DECORATED_POT);
		super.set(BlockDataKey.CRACKED, false);
		super.set(BlockDataKey.FACING, BlockFace.NORTH);
		super.set(BlockDataKey.WATERLOGGED, false);
	}

	@Override
	public boolean isCracked()
	{
		return super.get(BlockDataKey.CRACKED);
	}

	@Override
	public void setCracked(boolean cracked)
	{
		super.set(BlockDataKey.CRACKED, cracked);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return super.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(getFaces().contains(blockFace), "Illegal block face: " + blockFace);
		super.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST);
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

}
