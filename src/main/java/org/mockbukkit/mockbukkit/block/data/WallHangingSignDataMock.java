package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallHangingSign;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WallHangingSignDataMock extends BlockDataMock implements WallHangingSign
{

	/**
	 * Constructs a new {@link WallHangingSignDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public WallHangingSignDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link WallHangingSignDataMock} based on an existing {@link WallHangingSignDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected WallHangingSignDataMock(@NotNull WallHangingSignDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
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
		return this.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		this.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull WallHangingSignDataMock clone()
	{
		return new WallHangingSignDataMock(this);
	}

}
