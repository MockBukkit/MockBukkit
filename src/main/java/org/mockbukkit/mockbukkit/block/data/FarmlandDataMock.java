package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.Farmland;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link Farmland}.
 *
 * @see FarmlandDataMock
 */
public class FarmlandDataMock extends BlockDataMock implements Farmland
{

	/**
	 * Constructs a new {@link Farmland} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public FarmlandDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link Farmland} based on an existing {@link Farmland}.
	 *
	 * @param other the other block data.
	 */
	protected FarmlandDataMock(@NotNull FarmlandDataMock other)
	{
		super(other);
	}

	@Override
	public int getMoisture()
	{
		return this.get(BlockDataKey.MOISTURE);
	}

	@Override
	public void setMoisture(int moisture)
	{
		int maximumMoisture = this.getMaximumMoisture();
		Preconditions.checkArgument(0 <= moisture && moisture <= maximumMoisture, "Moisture must be between 0 and %s", maximumMoisture);
		this.set(BlockDataKey.MOISTURE, moisture);
	}

	@Override
	public int getMaximumMoisture()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_MOISTURE);
	}

	@Override
	public @NotNull FarmlandDataMock clone()
	{
		return new FarmlandDataMock(this);
	}

}
