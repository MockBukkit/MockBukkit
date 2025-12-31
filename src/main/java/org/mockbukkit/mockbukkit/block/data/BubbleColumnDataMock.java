package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.BubbleColumn;
import org.jetbrains.annotations.NotNull;

public class BubbleColumnDataMock extends BlockDataMock implements BubbleColumn
{

	public BubbleColumnDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected BubbleColumnDataMock(@NotNull BubbleColumnDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isDrag()
	{
		return this.get(BlockDataKey.DRAG);
	}

	@Override
	public void setDrag(boolean drag)
	{
		this.set(BlockDataKey.DRAG, drag);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BubbleColumnDataMock clone()
	{
		return new BubbleColumnDataMock(this);
	}


}
