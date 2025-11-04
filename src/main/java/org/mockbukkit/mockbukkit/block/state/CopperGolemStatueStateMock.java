package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CopperGolemStatue;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link CopperGolemStatue}.
 *
 * @see TileStateMock
 */
public class CopperGolemStatueStateMock extends TileStateMock implements CopperGolemStatue
{
	/**
	 * Constructs a new {@link CopperGolemStatueStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#CONDUIT}
	 *
	 * @param material The material this state is for.
	 */
	public CopperGolemStatueStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected CopperGolemStatueStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected CopperGolemStatueStateMock(@NotNull CopperGolemStatueStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull CopperGolemStatueStateMock getSnapshot()
	{
		return new CopperGolemStatueStateMock(this);
	}

}
