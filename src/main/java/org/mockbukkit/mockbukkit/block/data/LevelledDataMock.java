package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
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
		Preconditions.checkArgument(level >= this.getMinimumLevel() && level <= this.getMaximumLevel(), "Level should be between %s and %s", this.getMinimumLevel(), this.getMaximumLevel());
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
