package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import io.papermc.paper.math.Position;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkCatalyst;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link SculkCatalyst}.
 *
 * @see TileStateMock
 */
public class SculkCatalystMock extends TileStateMock implements SculkCatalyst
{

	/**
	 * Constructs a new {@link SculkCatalystMock} for the provided {@link Material}.
	 * Only supports {@link Material#SCULK_CATALYST}
	 *
	 * @param material The material this state is for.
	 */
	public SculkCatalystMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_CATALYST);
	}

	/**
	 * Constructs a new {@link SculkCatalystMock} for the provided {@link Block}.
	 * Only supports {@link Material#SCULK_CATALYST}
	 *
	 * @param block The block this state is for.
	 */
	protected SculkCatalystMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_CATALYST);
	}

	/**
	 * Constructs a new {@link SculkCatalystMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SculkCatalystMock(@NotNull SculkCatalystMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SculkCatalystMock(this);
	}

	@Override
	public void bloom(@NotNull Block block, int charges)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void bloom(@NotNull Position position, int charge)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
