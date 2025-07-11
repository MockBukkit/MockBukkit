package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.TNT;
import org.jetbrains.annotations.NotNull;

public class TNTDataMock extends BlockDataMock implements TNT
{

	/**
	 * Constructs a new {@link TNTDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public TNTDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link TNTDataMock} based on an existing {@link TNTDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected TNTDataMock(@NotNull TNTDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isUnstable()
	{
		return this.get(BlockDataKey.UNSTABLE);
	}

	@Override
	public void setUnstable(boolean unstable)
	{
		this.set(BlockDataKey.UNSTABLE, unstable);
	}

	@Override
	@SuppressWarnings({"java:S2975", "java:S1182"})
	public @NotNull TNTDataMock clone()
	{
		return new TNTDataMock(this);
	}

}
