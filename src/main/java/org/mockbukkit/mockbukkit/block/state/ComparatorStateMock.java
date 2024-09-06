package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Comparator;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Comparator}.
 *
 * @see TileStateMock
 */
public class ComparatorStateMock extends TileStateMock implements Comparator
{

	/**
	 * Constructs a new {@link ComparatorStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#COMPARATOR}
	 *
	 * @param material The material this state is for.
	 */
	public ComparatorStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.COMPARATOR);
	}

	/**
	 * Constructs a new {@link ComparatorStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#COMPARATOR}
	 *
	 * @param block The block this state is for.
	 */
	protected ComparatorStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.COMPARATOR);
	}

	/**
	 * Constructs a new {@link ComparatorStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected ComparatorStateMock(@NotNull ComparatorStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull ComparatorStateMock getSnapshot()
	{
		return new ComparatorStateMock(this);
	}

}
