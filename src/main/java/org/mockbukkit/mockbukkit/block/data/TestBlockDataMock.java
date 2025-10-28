package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.TestBlock;
import org.jetbrains.annotations.NotNull;

public class TestBlockDataMock extends BlockDataMock implements TestBlock
{

	/**
	 * Constructs a new {@link TestBlock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public TestBlockDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link TestBlockDataMock} based on an existing {@link TestBlockDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected TestBlockDataMock(@NotNull TestBlockDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Mode getMode()
	{
		return this.get(BlockDataKey.MODE);
	}

	@Override
	public void setMode(@NotNull Mode mode)
	{
		Preconditions.checkArgument(mode != null, "mode cannot be null!");
		this.set(BlockDataKey.MODE, mode);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull TestBlockDataMock clone()
	{
		return new TestBlockDataMock(this);
	}

}
