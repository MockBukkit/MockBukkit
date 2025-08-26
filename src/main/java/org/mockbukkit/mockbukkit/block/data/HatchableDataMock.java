package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.Hatchable;
import org.jetbrains.annotations.NotNull;

public class HatchableDataMock extends BlockDataMock implements Hatchable
{

	/**
	 * Constructs a new {@link Hatchable} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public HatchableDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link Hatchable} based on an existing {@link Hatchable}.
	 *
	 * @param other the other block data.
	 */
	protected HatchableDataMock(@NotNull HatchableDataMock other)
	{
		super(other);
	}

	@Override
	public int getHatch()
	{
		return this.get(BlockDataKey.HATCH);
	}

	@Override
	public void setHatch(int hatch)
	{
		int maximum = this.getMaximumHatch();
		Preconditions.checkArgument(0 <= hatch && hatch <= maximum, "Hatch must be between 0 and %s", maximum);
		this.set(BlockDataKey.HATCH, hatch);
	}

	@Override
	public int getMaximumHatch()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_HATCH);
	}

	@Override
	public @NotNull HatchableDataMock clone()
	{
		return new HatchableDataMock(this);
	}

}
