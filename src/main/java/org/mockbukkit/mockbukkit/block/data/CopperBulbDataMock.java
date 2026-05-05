package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.CopperBulb;
import org.jetbrains.annotations.NotNull;

public class CopperBulbDataMock extends BlockDataMock implements CopperBulb
{

	public CopperBulbDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CopperBulbDataMock(@NotNull CopperBulbDataMock other)
	{
		super(other);
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
	public boolean isPowered()
	{
		return this.get(BlockDataKey.POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		this.set(BlockDataKey.POWERED, powered);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull CopperBulbDataMock clone()
	{
		return new CopperBulbDataMock(this);
	}

}
