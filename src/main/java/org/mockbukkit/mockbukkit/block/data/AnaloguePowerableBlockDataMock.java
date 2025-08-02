package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.AnaloguePowerable;
import org.jetbrains.annotations.NotNull;

public class AnaloguePowerableBlockDataMock extends BlockDataMock implements AnaloguePowerable
{

	/**
	 * Constructs a new {@link AnaloguePowerableBlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public AnaloguePowerableBlockDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link AnaloguePowerableBlockDataMock} based on an existing {@link AnaloguePowerableBlockDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected AnaloguePowerableBlockDataMock(AnaloguePowerableBlockDataMock other)
	{
		super(other);
	}

	@Override
	public int getPower()
	{
		return this.get(BlockDataKey.POWER);
	}

	@Override
	public void setPower(int power)
	{
		Preconditions.checkArgument(power >= 0 && power <= this.getMaximumPower(), "Power must be between 0 and %s", this.getMaximumPower());
		this.set(BlockDataKey.POWER, power);
	}

	@Override
	public int getMaximumPower()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_POWER);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull AnaloguePowerableBlockDataMock clone()
	{
		return new AnaloguePowerableBlockDataMock(this);
	}

}
