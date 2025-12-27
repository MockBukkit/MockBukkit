package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.BigDripleaf;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class BigDripleafDataMock extends BlockDataMock implements BigDripleaf
{

	public BigDripleafDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected BigDripleafDataMock(@NotNull BigDripleafDataMock other)
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
	public @NotNull Tilt getTilt()
	{
		return this.get(BlockDataKey.TILT);
	}

	@Override
	public void setTilt(@NotNull Tilt tilt)
	{
		Preconditions.checkArgument(tilt != null, "tilt cannot be null!");
		this.set(BlockDataKey.TILT, tilt);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BigDripleafDataMock clone()
	{
		return new BigDripleafDataMock(this);
	}

}
