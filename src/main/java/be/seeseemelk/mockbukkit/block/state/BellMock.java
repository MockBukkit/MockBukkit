package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link Bell}.
 */
public class BellMock extends TileStateMock implements Bell
{

	public BellMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BELL);
	}

	protected BellMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BELL);
	}

	protected BellMock(@NotNull BellMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BellMock(this);
	}

}
