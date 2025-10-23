package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Ladder;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;

public class LadderDataMock extends BlockDataMock implements Ladder

{
	/**
	 * Constructs a new {@link LadderDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public LadderDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link LadderDataMock} based on an existing {@link LadderDataMock}.
	 *
	 * @param other the other block data.
	 */
	public LadderDataMock(@NotNull LadderDataMock other)
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
		Preconditions.checkArgument(this.getFaces().contains(facing), "BlockFace %s is not a valid BlockFace.", facing);
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
		return super.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		super.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull LadderDataMock clone()
	{
		return new LadderDataMock(this);
	}

}
