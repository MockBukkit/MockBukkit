package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.HALF;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.SHAPE;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of {@link Stairs}.
 */
public class StairsDataMock extends BlockDataMock implements Stairs
{

	/**
	 * Constructs a new {@link StairsDataMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#STAIRS}
	 *
	 * @param type The material this data is for.
	 */
	public StairsDataMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.STAIRS);
		setShape(Shape.STRAIGHT);
		setWaterlogged(false);
		setFacing(BlockFace.NORTH);
		setHalf(Half.BOTTOM);
	}

	@Override
	public @NotNull Shape getShape()
	{
		return get(SHAPE);
	}

	@Override
	public void setShape(@NotNull Shape shape)
	{
		Preconditions.checkNotNull("Shape cannot be null");
		set(SHAPE, shape);
	}

	@Override
	public @NotNull Half getHalf()
	{
		return get(HALF);
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		Preconditions.checkNotNull(half, "Half cannot be null");
		set(HALF, half);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkNotNull(facing, "BlockFace cannot be null");
		Preconditions.checkArgument(getFaces().contains(facing), "Invalid face. Must be one of " + getFaces());
		set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST);
	}

	@Override
	public boolean isWaterlogged()
	{
		return get(WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		set(WATERLOGGED, waterlogged);
	}

}
