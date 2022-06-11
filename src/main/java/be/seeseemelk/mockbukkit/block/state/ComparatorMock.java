package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Comparator;
import org.jetbrains.annotations.NotNull;

public class ComparatorMock extends TileStateMock implements Comparator
{

	protected ComparatorMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.COMPARATOR)
			throw new IllegalArgumentException("Cannot create a Comparator state from " + material);
	}

	protected ComparatorMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.COMPARATOR)
			throw new IllegalArgumentException("Cannot create a Comparator state from " + block.getType());
	}

	protected ComparatorMock(@NotNull ComparatorMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new ComparatorMock(this);
	}

}
