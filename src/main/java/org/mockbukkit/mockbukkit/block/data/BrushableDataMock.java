package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.Brushable;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link Brushable}.
 *
 * @see BlockDataMock
 */
public class BrushableDataMock extends BlockDataMock implements Brushable
{

	/**
	 * Constructs a new {@link BrushableDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public BrushableDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link BrushableDataMock} based on an existing {@link BrushableDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected BrushableDataMock(BrushableDataMock other)
	{
		super(other);
	}

	@Override
	public int getDusted()
	{
		return get(BlockDataKey.DUSTED);
	}

	@Override
	public void setDusted(int dusted)
	{
		Preconditions.checkArgument(0 <= dusted && dusted <= this.getMaximumDusted(), "Dusted level should be between 0 and %s", this.getMaximumDusted());
		this.set(BlockDataKey.DUSTED, dusted);
	}

	@Override
	public int getMaximumDusted()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_DUSTED);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BrushableDataMock clone()
	{
		return new BrushableDataMock(this);
	}

}
