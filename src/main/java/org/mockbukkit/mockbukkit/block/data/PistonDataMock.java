package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Piston;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Mock implementation of {@link Piston}.
 *
 * @see BlockDataMock
 */
public class PistonDataMock extends BlockDataMock implements Piston
{

	public PistonDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected PistonDataMock(@NotNull BlockDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isExtended()
	{
		return this.get(BlockDataKey.EXTENDED);
	}

	@Override
	public void setExtended(boolean extended)
	{
		this.set(BlockDataKey.EXTENDED, extended);
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
		Preconditions.checkArgument(blockFace.isCartesian(), "Invalid face, only cartesian face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull PistonDataMock clone()
	{
		return new PistonDataMock(this);
	}

}
