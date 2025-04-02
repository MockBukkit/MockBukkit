package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Snowable;
import org.jetbrains.annotations.NotNull;

public class SnowableDataMock extends BlockDataMock implements Snowable
{

	/**
	 * Constructs a new {@link SnowableDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	protected SnowableDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public boolean isSnowy()
	{
		return this.get(BlockDataKey.SNOWY);
	}

	@Override
	public void setSnowy(boolean snowy)
	{
		this.set(BlockDataKey.SNOWY, snowy);
	}

}
