package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.jetbrains.annotations.NotNull;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.BERRIES;

public class CaveVinesPlantDataMock extends AgeableDataMock implements CaveVinesPlant
{

	public CaveVinesPlantDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CaveVinesPlantDataMock(@NotNull CaveVinesPlantDataMock other)
	{
		super(other);
	}

	@Override
	public boolean hasBerries()
	{
		return super.get(BERRIES);
	}

	@Override
	public void setBerries(boolean berries)
	{
		super.set(BERRIES, berries);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull CaveVinesPlantDataMock clone()
	{
		return new CaveVinesPlantDataMock(this);
	}

}
