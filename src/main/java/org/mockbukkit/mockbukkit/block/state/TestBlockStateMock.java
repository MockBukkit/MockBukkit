package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TestBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link TestBlock}.
 *
 * @see TileStateMock
 */
public class TestBlockStateMock extends TileStateMock implements TestBlock
{

	public TestBlockStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected TestBlockStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected TestBlockStateMock(@NotNull TileStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull TestBlockStateMock getSnapshot()
	{
		return new TestBlockStateMock(this);
	}

	@Override
	public @NotNull TestBlockStateMock copy(@NotNull Location location)
	{
		Preconditions.checkNotNull(location);
		return getSnapshot().changeLocation(location);
	}

	@Override
	public @NotNull TestBlockStateMock copy()
	{
		return getSnapshot().changeLocation(null);
	}

}
