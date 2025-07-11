package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Furnace;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.LIT;

public class FurnaceDataMock extends BlockDataMock implements Furnace
{

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public FurnaceDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link FurnaceDataMock} based on an existing {@link FurnaceDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected FurnaceDataMock(@NotNull FurnaceDataMock other)
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
		Preconditions.checkArgument(facing != null, "blockFace cannot be null!");
		Preconditions.checkArgument(facing.isCartesian() && facing.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public boolean isLit()
	{
		return this.get(LIT);
	}

	@Override
	public void setLit(boolean lit)
	{
		this.set(LIT, lit);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull FurnaceDataMock clone()
	{
		return new FurnaceDataMock(this);
	}

}
