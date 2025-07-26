package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.Candle;
import org.jetbrains.annotations.NotNull;

public class CandleDataMock extends BlockDataMock implements Candle
{

	/**
	 * Constructs a new {@link CandleDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	protected CandleDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public int getCandles()
	{
		return this.get(BlockDataKey.CANDLES);
	}

	@Override
	public void setCandles(int candles)
	{
		Preconditions.checkArgument(candles >= this.getMinimumCandles() && candles <= this.getMaximumCandles(), "Candles must be between %s and %s", this.getMinimumCandles(), this.getMaximumCandles());
		this.set(BlockDataKey.CANDLES, candles);
	}

	@Override
	public int getMaximumCandles()
	{
		return 4;
	}

	@Override
	public int getMinimumCandles()
	{
		return 1;
	}

	@Override
	public boolean isLit()
	{
		return this.get(BlockDataKey.LIT);
	}

	@Override
	public void setLit(boolean lit)
	{
		this.set(BlockDataKey.LIT, lit);
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
