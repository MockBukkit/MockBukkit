package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.EnderChest;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EnderChestDataMock extends BlockDataMock implements EnderChest
{

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public EnderChestDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link EnderChestDataMock} based on an existing {@link EnderChestDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected EnderChestDataMock(@NotNull EnderChestDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(this.getFaces().contains(facing), "BlockFace %s is not a valid BlockFace.", facing);
		this.set(BlockDataKey.FACING, facing);
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
	public @NotNull EnderChestDataMock clone()
	{
		return new EnderChestDataMock(this);
	}

}
