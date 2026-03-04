package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Powerable;
import org.jetbrains.annotations.NotNull;

public class PowerableDataMock extends BlockDataMock implements Powerable
{

	public PowerableDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected PowerableDataMock(@NotNull PowerableDataMock other)
	{
		super(other);
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
	public @NotNull PowerableDataMock clone()
	{
		return new PowerableDataMock(this);
	}

}
