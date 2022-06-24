package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Conduit;
import org.jetbrains.annotations.NotNull;

public class ConduitMock extends TileStateMock implements Conduit
{

	protected ConduitMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.CONDUIT)
			throw new IllegalArgumentException("Cannot create a Conduit state from " + material);
	}

	protected ConduitMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.CONDUIT)
			throw new IllegalArgumentException("Cannot create a Conduit state from " + block.getType());
	}

	protected ConduitMock(@NotNull ConduitMock state)
	{
		super(state);
	}

	@Override
	public @NotNull ConduitMock getSnapshot()
	{
		return new ConduitMock(this);
	}

}
