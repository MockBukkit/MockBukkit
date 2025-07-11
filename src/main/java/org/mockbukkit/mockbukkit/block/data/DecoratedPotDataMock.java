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
	 */
	public DecoratedPotDataMock()
	{
		super(Material.DECORATED_POT);
		set(BlockDataKey.CRACKED, false);
		set(BlockDataKey.FACING, BlockFace.NORTH);
		set(BlockDataKey.WATERLOGGED, false);
	}

	/**
	 * Create a new {@link DecoratedPotDataMock} based on an existing {@link DecoratedPotDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected DecoratedPotDataMock(@NotNull DecoratedPotDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isCracked()
	{
		return get(BlockDataKey.CRACKED);
	}

	@Override
	public void setCracked(boolean cracked)
	{
		set(BlockDataKey.CRACKED, cracked);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(getFaces().contains(blockFace), "Illegal block face: " + blockFace);
		set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public boolean isWaterlogged()
	{
		return get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull DecoratedPotDataMock clone()
	{
		return new DecoratedPotDataMock(this);
	}

}
