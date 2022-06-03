package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BellMock extends TileStateMock implements Bell
{

	public BellMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.BELL)
			throw new IllegalArgumentException("Cannot create a Bell state from " + material);
	}

	protected BellMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.BELL)
			throw new IllegalArgumentException("Cannot create a Bell state from " + block.getType());
	}

	protected BellMock(@NotNull TileStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BellMock(this);
	}

}
