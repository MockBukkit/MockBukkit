package be.seeseemelk.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkCatalyst;
import org.jetbrains.annotations.NotNull;

public class SculkCatalystMock extends TileStateMock implements SculkCatalyst
{

	public SculkCatalystMock(@NotNull Material material)
	{
		super(material);
		Preconditions.checkArgument(material == Material.SCULK_CATALYST, "Cannot create a Sculk Catalyst state from " + material);
	}

	protected SculkCatalystMock(@NotNull Block block)
	{
		super(block);
		Preconditions.checkArgument(block.getType() == Material.SCULK_CATALYST, "Cannot create a Sculk Catalyst state from " + block.getType());
	}

	protected SculkCatalystMock(@NotNull SculkCatalystMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SculkCatalystMock(this);
	}

}
