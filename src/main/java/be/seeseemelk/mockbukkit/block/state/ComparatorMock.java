package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Comparator;
import org.jetbrains.annotations.NotNull;

public class ComparatorMock extends TileStateMock implements Comparator
{

	protected ComparatorMock(@NotNull Material material)
	{
		super(material);
		checkType(material == Material.COMPARATOR);
	}

	protected ComparatorMock(@NotNull Block block)
	{
		super(block);
		checkType(block.getType() == Material.COMPARATOR);
	}

	protected ComparatorMock(@NotNull ComparatorMock state)
	{
		super(state);
	}

	@Override
	public @NotNull ComparatorMock getSnapshot()
	{
		return new ComparatorMock(this);
	}

}
