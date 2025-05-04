package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.Sapling;
import org.jetbrains.annotations.NotNull;

public class SaplingDataMock extends BlockDataMock implements Sapling
{

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public SaplingDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public int getStage()
	{
		return this.get(BlockDataKey.STAGE_KEY);
	}

	@Override
	public void setStage(int stage)
	{
		Preconditions.checkArgument(stage >= 0 && stage <= this.getMaximumStage(), "The stage must be between 0 and %s", this.getMaximumStage());
		this.set(BlockDataKey.STAGE_KEY, stage);
	}

	@Override
	public int getMaximumStage()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_STAGE);
	}

}
