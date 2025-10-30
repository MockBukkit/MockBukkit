package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.CaveVines;
import org.jetbrains.annotations.NotNull;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.BERRIES;

public class CaveVineDataMock extends AgeableDataMock implements CaveVines
{

	public CaveVineDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CaveVineDataMock(@NotNull CaveVineDataMock other)
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
	public @NotNull CaveVineDataMock clone()
	{
		return new CaveVineDataMock(this);
	}

}
