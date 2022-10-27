package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Smoker;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Smoker}.
 */
public class SmokerMock extends AbstractFurnaceMock implements Smoker
{

	public SmokerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SMOKER);
	}

	protected SmokerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SMOKER);
	}

	protected SmokerMock(@NotNull SmokerMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SmokerMock(this);
	}

}
