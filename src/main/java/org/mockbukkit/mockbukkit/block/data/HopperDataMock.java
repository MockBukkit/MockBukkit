package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Hopper;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class HopperDataMock extends BlockDataMock implements Hopper
{

	/**
	 * Constructs a new {@link HopperDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public HopperDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link HopperDataMock} based on an existing {@link HopperDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected HopperDataMock(@NotNull HopperDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isEnabled()
	{
		return this.get(BlockDataKey.ENABLED);
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.set(BlockDataKey.ENABLED, enabled);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(facing != null, "blockFace cannot be null!");
		Preconditions.checkArgument(facing.isCartesian() && facing != BlockFace.UP, "Invalid face, only cartesian face (excluding UP) are allowed for this property!");
		this.set(BlockDataKey.FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull HopperDataMock clone()
	{
		return new HopperDataMock(this);
	}

}
