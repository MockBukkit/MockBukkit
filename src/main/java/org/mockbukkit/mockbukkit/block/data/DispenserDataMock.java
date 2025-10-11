package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Dispenser;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Mock implementation of {@link Dispenser}.
 *
 * @see BlockDataMock
 */
public class DispenserDataMock extends BlockDataMock implements Dispenser
{

	public DispenserDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected DispenserDataMock(@NotNull BlockDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isTriggered()
	{
		return this.get(BlockDataKey.TRIGGERED);
	}

	@Override
	public void setTriggered(boolean triggered)
	{
		this.set(BlockDataKey.TRIGGERED, triggered);
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
	public @NotNull DispenserDataMock clone()
	{
		return new DispenserDataMock(this);
	}

}
