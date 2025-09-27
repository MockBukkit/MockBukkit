package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.TechnicalPiston;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Set;

/**
 * Mock implementation of {@link TechnicalPiston}.
 *
 * @see BlockDataMock
 */
public class TechnicalPistonDataMock extends BlockDataMock implements TechnicalPiston
{

	public TechnicalPistonDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected TechnicalPistonDataMock(@NotNull TechnicalPistonDataMock other)
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
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull TechnicalPistonDataMock clone()
	{
		return new TechnicalPistonDataMock(this);
	}

}
