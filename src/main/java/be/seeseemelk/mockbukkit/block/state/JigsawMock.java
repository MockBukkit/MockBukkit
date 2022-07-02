package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jigsaw;
import org.jetbrains.annotations.NotNull;

public class JigsawMock extends TileStateMock implements Jigsaw
{

	public JigsawMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.JIGSAW);
	}

	protected JigsawMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.JIGSAW);
	}

	protected JigsawMock(@NotNull JigsawMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new JigsawMock(this);
	}

}
