package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallSign;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of a {@link WallSign}.
 */
public class WallSignDataMock extends BlockDataMock implements WallSign
{

	/**
	 * Constructs a new {@link WallSignDataMock} for the provided {@link Material}. Only
	 * supports materials in {@link Tag#WALL_SIGNS}
	 *
	 * @param type The material this data is for.
	 */
	public WallSignDataMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.WALL_SIGNS);
		this.setFacing(BlockFace.NORTH);
		this.setWaterlogged(false);
	}

	/**
	 * Create a new {@link WallSignDataMock} based on an existing {@link WallSignDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected WallSignDataMock(WallSignDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return super.get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkNotNull(facing, "BlockFace cannot be null");
		Preconditions.checkArgument(getFaces().contains(facing), "Invalid face. Must be one of " + getFaces());
		super.set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
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

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull WallSignDataMock clone()
	{
		return new WallSignDataMock(this);
	}

}
