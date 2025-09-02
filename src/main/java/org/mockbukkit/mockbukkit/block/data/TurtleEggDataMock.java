package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.TurtleEgg;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link TurtleEgg}.
 *
 * @see BlockDataMock
 */
public class TurtleEggDataMock extends BlockDataMock implements TurtleEgg
{

	/**
	 * Constructs a new {@link TurtleEgg} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public TurtleEggDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link TurtleEggDataMock} based on an existing {@link TurtleEggDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected TurtleEggDataMock(@NotNull TurtleEggDataMock other)
	{
		super(other);
	}

	@Override
	public int getEggs()
	{
		return this.get(BlockDataKey.EGGS);
	}

	@Override
	public void setEggs(int eggs)
	{
		int minimum = this.getMinimumEggs();
		int maximum = this.getMaximumEggs();
		Preconditions.checkArgument(minimum <= eggs && eggs <= maximum, "Eggs must be between %s and %s", minimum, maximum);
		this.set(BlockDataKey.EGGS, eggs);
	}

	@Override
	public int getMinimumEggs()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MIN_EGGS);
	}

	@Override
	public int getMaximumEggs()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_EGGS);
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
	public @NotNull TurtleEggDataMock clone()
	{
		return new TurtleEggDataMock(this);
	}

}
