package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.AmethystCluster;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of {@link AmethystCluster}.
 */
public class AmethystClusterDataMock extends BlockDataMock implements AmethystCluster
{

	/**
	 * Constructs a new {@link AmethystClusterDataMock} for the provided {@link Material}.
	 * Only supports {@link Material#AMETHYST_CLUSTER}
	 *
	 * @param type The material this data is for.
	 */
	public AmethystClusterDataMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Material.AMETHYST_CLUSTER);
		setFacing(BlockFace.NORTH);
		setWaterlogged(false);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return super.get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkNotNull(facing, "Facing cannot be null");
		Preconditions.checkArgument(getFaces().contains(facing), "Invalid face. Must be one of " + getFaces());
		super.set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
	}

	@Override
	public boolean isWaterlogged()
	{
		return super.get(WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		super.set(WATERLOGGED, waterlogged);
	}

}
