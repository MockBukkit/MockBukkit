package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jigsaw;
import org.jetbrains.annotations.NotNull;

public class JigsawMock extends TileStateMock implements Jigsaw
{

	protected JigsawMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.JIGSAW)
			throw new IllegalArgumentException("Cannot create a Jigsaw state from " + material);
	}

	protected JigsawMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.JIGSAW)
			throw new IllegalArgumentException("Cannot create a Jigsaw state from " + block.getType());
	}

	protected JigsawMock(@NotNull TileStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new JigsawMock(this);
	}

}
