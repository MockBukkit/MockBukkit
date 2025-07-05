package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Waterlogged;
import org.jetbrains.annotations.NotNull;

public class WaterloggedDataMock extends BlockDataMock implements Waterlogged
{

	/**
	 * Constructs a new {@link WaterloggedDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public WaterloggedDataMock(@NotNull Material material)
	{
		super(material);
		setWaterlogged(false);
	}

	@Override
	public boolean isWaterlogged()
	{
		return this.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		this.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

}
