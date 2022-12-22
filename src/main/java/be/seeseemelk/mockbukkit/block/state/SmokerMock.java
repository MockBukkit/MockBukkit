package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Smoker;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Smoker}.
 *
 * @see AbstractFurnaceMock
 */
public class SmokerMock extends AbstractFurnaceMock implements Smoker
{

	/**
	 * Constructs a new {@link SmokerMock} for the provided {@link Material}.
	 * Only supports {@link Material#SMOKER}
	 *
	 * @param material The material this state is for.
	 */
	public SmokerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SMOKER);
	}

	/**
	 * Constructs a new {@link SmokerMock} for the provided {@link Block}.
	 * Only supports {@link Material#SMOKER}
	 *
	 * @param block The block this state is for.
	 */
	protected SmokerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SMOKER);
	}

	/**
	 * Constructs a new {@link SmokerMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
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
