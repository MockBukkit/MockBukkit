package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.Cake;
import org.jetbrains.annotations.NotNull;

public class CakeDataMock extends BlockDataMock implements Cake
{

	public CakeDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CakeDataMock(@NotNull CakeDataMock other)
	{
		super(other);
	}

	@Override
	public int getBites()
	{
		return this.get(BlockDataKey.BITES);
	}

	@Override
	public void setBites(int bites)
	{
		this.set(BlockDataKey.BITES, bites);
	}

	@Override
	public int getMaximumBites()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_BITES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull CakeDataMock clone()
	{
		return new CakeDataMock(this);
	}


}
