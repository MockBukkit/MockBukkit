package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlastFurnaceMock extends TileStateMock implements Bell
{

	public BlastFurnaceMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.BLAST_FURNACE)
			throw new IllegalArgumentException("Cannot create a Blast Furnace state from " + material);
	}

	protected BlastFurnaceMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.BLAST_FURNACE)
			throw new IllegalArgumentException("Cannot create a Blast Furnace state from " + block.getType());
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
