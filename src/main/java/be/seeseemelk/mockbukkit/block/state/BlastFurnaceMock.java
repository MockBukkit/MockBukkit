package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.block.state.AbstractFurnaceMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class BlastFurnaceMock extends AbstractFurnaceMock implements BlastFurnace
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

	@Override
	protected InventoryMock createInventory()
	{
//		return new BlastFurnaceInventoryMock(this); TODO: Not implemented
		return new InventoryMock(this, InventoryType.BLAST_FURNACE);
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
