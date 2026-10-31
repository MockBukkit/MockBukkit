package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.PotentSulfur;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link PotentSulfur}.
 *
 * @see TileStateMock
 */
public class PotentSulfurStateMock extends TileStateMock implements PotentSulfur
{

	/**
	 * Constructs a new {@link PotentSulfurStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#POTENT_SULFUR}
	 *
	 * @param material The material this state is for.
	 */
	public PotentSulfurStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.POTENT_SULFUR);
	}

	/**
	 * Constructs a new {@link PotentSulfurStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#POTENT_SULFUR}
	 *
	 * @param block The block this state is for.
	 */
	protected PotentSulfurStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.POTENT_SULFUR);
	}

	/**
	 * Constructs a new {@link PotentSulfurStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected PotentSulfurStateMock(@NotNull PotentSulfurStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull PotentSulfurStateMock getSnapshot()
	{
		return new PotentSulfurStateMock(this);
	}

	@Override
	public @NotNull PotentSulfurStateMock copy()
	{
		return new PotentSulfurStateMock(this);
	}

}
