package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Conduit;
import org.jetbrains.annotations.NotNull;

public class ConduitMock extends TileStateMock implements Conduit
{

	public ConduitMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.CONDUIT);
	}

	protected ConduitMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.CONDUIT);
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
