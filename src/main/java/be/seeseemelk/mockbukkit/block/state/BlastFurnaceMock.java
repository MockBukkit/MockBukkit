package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link BlastFurnace}.
 */
public class BlastFurnaceMock extends AbstractFurnaceMock implements BlastFurnace
{

	public BlastFurnaceMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BLAST_FURNACE);
	}

	protected BlastFurnaceMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BLAST_FURNACE);
	}

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
