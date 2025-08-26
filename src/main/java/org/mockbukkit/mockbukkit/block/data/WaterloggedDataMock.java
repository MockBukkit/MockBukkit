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

	/**
	 * Create a new {@link WaterloggedDataMock} based on an existing {@link WaterloggedDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected WaterloggedDataMock(WaterloggedDataMock other)
	{
		super(other);
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

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull WaterloggedDataMock clone()
	{
		return new WaterloggedDataMock(this);
	}

}
