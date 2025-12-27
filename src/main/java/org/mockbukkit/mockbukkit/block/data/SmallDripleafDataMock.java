package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.SmallDripleaf;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class SmallDripleafDataMock extends BlockDataMock implements SmallDripleaf
{

	public SmallDripleafDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected SmallDripleafDataMock(@NotNull SmallDripleafDataMock other)
	{
		super(other);
	}

	@Override
	public @NonNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
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
	public @NotNull Half getHalf()
	{
		return BisectedDataMock.fromString(this.get(BlockDataKey.HALF_MULTI_BLOCK));
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		this.set(BlockDataKey.HALF_MULTI_BLOCK, BisectedDataMock.toString(half));
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull SmallDripleafDataMock clone()
	{
		return new SmallDripleafDataMock(this);
	}

}
