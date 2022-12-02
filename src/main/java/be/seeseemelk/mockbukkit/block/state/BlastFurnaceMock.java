package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link BlastFurnace}.
 *
 * @see AbstractFurnaceMock
 */
public class BlastFurnaceMock extends AbstractFurnaceMock implements BlastFurnace
{

	/**
	 * Constructs a new {@link BlastFurnaceMock} for the provided {@link Material}.
	 * Only supports {@link Material#BLAST_FURNACE}
	 *
	 * @param material The material this state is for.
	 */
	public BlastFurnaceMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BLAST_FURNACE);
	}

	/**
	 * Constructs a new {@link BlastFurnaceMock} for the provided {@link Block}.
	 * Only supports {@link Material#BLAST_FURNACE}
	 *
	 * @param block The block this state is for.
	 */
	protected BlastFurnaceMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BLAST_FURNACE);
	}

	/**
	 * Constructs a new {@link BlastFurnaceMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BlastFurnaceMock(@NotNull BlastFurnaceMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BlastFurnaceMock(this);
	}

}
