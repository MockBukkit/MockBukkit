package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Comparator;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link Comparator}.
 */
public class ComparatorMock extends TileStateMock implements Comparator
{

	public ComparatorMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.COMPARATOR);
	}

	protected ComparatorMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.COMPARATOR);
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
