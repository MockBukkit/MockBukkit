package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Lightable;
import org.jetbrains.annotations.NotNull;

public class LightableDataMock extends BlockDataMock implements Lightable
{

	/**
	 * Constructs a new {@link LightableDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	protected LightableDataMock(@NotNull Material material)
	{
		super(material);
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

}
