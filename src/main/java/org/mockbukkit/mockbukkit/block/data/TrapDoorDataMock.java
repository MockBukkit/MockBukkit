package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.TrapDoor;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.HALF;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.OPEN;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.POWERED;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of {@link TrapDoor}.
 */
public class TrapDoorDataMock extends BlockDataMock implements TrapDoor
{

	/**
	 * Constructs a new {@link TrapDoorDataMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#TRAPDOORS}
	 *
	 * @param type The material this data is for.
	 */
	public TrapDoorDataMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.TRAPDOORS);
		setHalf(Half.BOTTOM);
		setOpen(false);
		setPowered(false);
		setWaterlogged(false);
		setFacing(BlockFace.NORTH);
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
	public boolean isOpen()
	{
		return get(OPEN);
	}

	@Override
	public void setOpen(boolean open)
	{
		set(OPEN, open);
	}

	@Override
	public boolean isPowered()
	{
		return get(POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		set(POWERED, powered);
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
