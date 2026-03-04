package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Snowable;
import org.jetbrains.annotations.NotNull;

public class SnowableDataMock extends BlockDataMock implements Snowable
{

	/**
	 * Constructs a new {@link SnowableDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	protected SnowableDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link SnowableDataMock} based on an existing {@link SnowableDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected SnowableDataMock(@NotNull SnowableDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isSnowy()
	{
		return this.get(BlockDataKey.SNOWY);
	}

	@Override
	public void setSnowy(boolean snowy)
	{
		this.set(BlockDataKey.SNOWY, snowy);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull SnowableDataMock clone()
	{
		return new SnowableDataMock(this);
	}

}
