package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Levelled;
import org.jetbrains.annotations.NotNull;

public class LevelledDataMock extends BlockDataMock implements Levelled
{

	/**
	 * Constructs a new {@link LevelledDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public LevelledDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public int getLevel()
	{
		return this.get(BlockDataKey.LEVEL);
	}

	@Override
	public void setLevel(int level)
	{
		this.set(BlockDataKey.LEVEL, level);
	}

	@Override
	public int getMaximumLevel()
	{
		return 15;
	}

	@Override
	public int getMinimumLevel()
	{
		return 0;
	}

}
