package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TestInstanceBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link TestInstanceBlock}.
 *
 * @see TileStateMock
 */
public class TestInstanceBlockStateMock extends TileStateMock implements TestInstanceBlock
{

	public TestInstanceBlockStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected TestInstanceBlockStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected TestInstanceBlockStateMock(@NotNull TileStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull TestInstanceBlockStateMock getSnapshot()
	{
		return new TestInstanceBlockStateMock(this);
	}

	@Override
	public @NotNull TestInstanceBlockStateMock copy(@NotNull Location location)
	{
		Preconditions.checkNotNull(location);
		return getSnapshot().changeLocation(location);
	}

	@Override
	public @NotNull TestInstanceBlockStateMock copy()
	{
		return getSnapshot().changeLocation(null);
	}

}
