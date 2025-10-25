package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.HangingMoss;
import org.jetbrains.annotations.NotNull;

public class HangingMossDataMock extends BlockDataMock implements HangingMoss
{

	public HangingMossDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected HangingMossDataMock(@NotNull HangingMossDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isTip()
	{
		return this.get(BlockDataKey.TIP);
	}

	@Override
	public void setTip(boolean tip)
	{
		this.set(BlockDataKey.TIP, tip);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull HangingMossDataMock clone()
	{
		return new HangingMossDataMock(this);
	}

}
