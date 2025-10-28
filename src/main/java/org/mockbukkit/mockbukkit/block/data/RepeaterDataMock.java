package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Repeater;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class RepeaterDataMock extends BlockDataMock implements Repeater
{

	/**
	 * Constructs a new {@link RepeaterDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public RepeaterDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link RepeaterDataMock} based on an existing {@link RepeaterDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected RepeaterDataMock(@NotNull RepeaterDataMock other)
	{
		super(other);
	}

	@Override
	public int getDelay()
	{
		return this.get(BlockDataKey.DELAY);
	}

	@Override
	public void setDelay(int delay)
	{
		Preconditions.checkArgument(delay >= this.getMinimumDelay() && delay <= this.getMaximumDelay(), "Delay must be between %s and %s", this.getMinimumDelay(), this.getMaximumDelay());
		this.set(BlockDataKey.DELAY, delay);
	}

	@Override
	public int getMinimumDelay()
	{
		return 1;
	}

	@Override
	public int getMaximumDelay()
	{
		return 4;
	}

	@Override
	public boolean isLocked()
	{
		return this.get(BlockDataKey.LOCKED);
	}

	@Override
	public void setLocked(boolean locked)
	{
		this.set(BlockDataKey.LOCKED, locked);
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
	public boolean isPowered()
	{
		return this.get(BlockDataKey.POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		this.set(BlockDataKey.POWERED, powered);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull RepeaterDataMock clone()
	{
		return new RepeaterDataMock(this);
	}

}
