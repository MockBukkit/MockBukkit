package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Furnace}.
 *
 * @see AbstractFurnaceStateMock
 */
public class FurnaceStateMock extends AbstractFurnaceStateMock implements Furnace
{

	/**
	 * Constructs a new {@link FurnaceStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#FURNACE}
	 *
	 * @param material The material this state is for.
	 */
	public FurnaceStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.FURNACE);
	}

	/**
	 * Constructs a new {@link FurnaceStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#FURNACE}
	 *
	 * @param block The block this state is for.
	 */
	protected FurnaceStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.FURNACE);
	}

	/**
	 * Constructs a new {@link FurnaceStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected FurnaceStateMock(@NotNull FurnaceStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull FurnaceStateMock getSnapshot()
	{
		return new FurnaceStateMock(this);
	}

	@Override
	public @NotNull FurnaceStateMock copy()
	{
		return new FurnaceStateMock(this);
	}

}
