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

	/**
	 * Create a new {@link LevelledDataMock} based on an existing {@link LevelledDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected LevelledDataMock(@NotNull LevelledDataMock other)
	{
		super(other);
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
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_LEVEL);
	}

	@Override
	public int getMinimumLevel()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MIN_LEVEL);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull LevelledDataMock clone()
	{
		return new LevelledDataMock(this);
	}

}
