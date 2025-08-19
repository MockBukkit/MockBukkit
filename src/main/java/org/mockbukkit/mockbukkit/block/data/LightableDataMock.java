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

	/**
	 * Create a new {@link LightableDataMock} based on an existing {@link LightableDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected LightableDataMock(@NotNull LightableDataMock other)
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
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull LightableDataMock clone()
	{
		return new LightableDataMock(this);
	}

}
