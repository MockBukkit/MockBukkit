package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Campfire;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.LIT;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.SIGNAL_FIRE;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of {@link Campfire}.
 */
public class CampfireDataMock extends BlockDataMock implements Campfire
{

	/**
	 * Constructs a new {@link CampfireDataMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#CAMPFIRES}
	 *
	 * @param type The material this data is for.
	 */
	public CampfireDataMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.CAMPFIRES);
		setFacing(BlockFace.NORTH);
		setLit(true);
		setSignalFire(false);
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
		return Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}

	@Override
	public boolean isLit()
	{
		return super.get(LIT);
	}

	@Override
	public void setLit(boolean lit)
	{
		super.set(LIT, lit);
	}

	@Override
	public boolean isSignalFire()
	{
		return super.get(SIGNAL_FIRE);
	}

	@Override
	public void setSignalFire(boolean signalFire)
	{
		super.set(SIGNAL_FIRE, signalFire);
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
