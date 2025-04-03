package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.NotNull;

public class BisectedDataMock extends BlockDataMock implements Bisected
{

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public BisectedDataMock(@NotNull Material material)
	{
		super(material);

		setHalf(Half.BOTTOM);
	}

	@Override
	public @NotNull Half getHalf()
	{
		return this.get(BlockDataKey.HALF);
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		this.set(BlockDataKey.HALF, half);
	}

}
