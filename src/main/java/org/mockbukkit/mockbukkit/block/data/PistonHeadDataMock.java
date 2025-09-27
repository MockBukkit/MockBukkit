package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.PistonHead;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Set;

/**
 * Mock implementation of {@link PistonHead}.
 *
 * @see BlockDataMock
 */
public class PistonHeadDataMock extends BlockDataMock implements PistonHead
{

	public PistonHeadDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected PistonHeadDataMock(@NotNull BlockDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Type getType()
	{
		return this.get(BlockDataKey.TYPE_TECHNICAL_PISTON);
	}

	@Override
	public void setType(@NotNull Type type)
	{
		Preconditions.checkArgument(type != null, "type cannot be null!");
		this.set(BlockDataKey.TYPE_TECHNICAL_PISTON, type);
	}

	@Override
	public @NonNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(final BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian(), "Invalid face, only cartesian face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NonNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public boolean isShort()
	{
		return this.get(BlockDataKey.SHORT);
	}

	@Override
	public void setShort(boolean _short)
	{
		this.set(BlockDataKey.SHORT, _short);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull PistonHeadDataMock clone()
	{
		return new PistonHeadDataMock(this);
	}

}
