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
		checkType(material, Material.SCULK_CATALYST);
	}

	protected SculkCatalystMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_CATALYST);
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
