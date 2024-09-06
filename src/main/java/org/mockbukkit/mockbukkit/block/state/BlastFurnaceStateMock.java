package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link BlastFurnace}.
 *
 * @see AbstractFurnaceStateMock
 */
public class BlastFurnaceStateMock extends AbstractFurnaceStateMock implements BlastFurnace
{

	/**
	 * Constructs a new {@link BlastFurnaceStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#BLAST_FURNACE}
	 *
	 * @param material The material this state is for.
	 */
	public BlastFurnaceStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BLAST_FURNACE);
	}

	/**
	 * Constructs a new {@link BlastFurnaceStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#BLAST_FURNACE}
	 *
	 * @param block The block this state is for.
	 */
	protected BlastFurnaceStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BLAST_FURNACE);
	}

	/**
	 * Constructs a new {@link BlastFurnaceStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BlastFurnaceStateMock(@NotNull BlastFurnaceStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BlastFurnaceStateMock(this);
	}

}
