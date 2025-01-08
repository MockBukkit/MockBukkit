package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import io.papermc.paper.math.Position;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.SculkCatalyst;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link SculkCatalyst}.
 *
 * @see TileStateMock
 */
public class SculkCatalystStateMock extends TileStateMock implements SculkCatalyst
{

	/**
	 * Constructs a new {@link SculkCatalystStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#SCULK_CATALYST}
	 *
	 * @param material The material this state is for.
	 */
	public SculkCatalystStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_CATALYST);
	}

	/**
	 * Constructs a new {@link SculkCatalystStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#SCULK_CATALYST}
	 *
	 * @param block The block this state is for.
	 */
	protected SculkCatalystStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_CATALYST);
	}

	/**
	 * Constructs a new {@link SculkCatalystStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SculkCatalystStateMock(@NotNull SculkCatalystStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull SculkCatalystStateMock getSnapshot()
	{
		return new SculkCatalystStateMock(this);
	}

	@Override
	public @NotNull SculkCatalystStateMock copy()
	{
		return new SculkCatalystStateMock(this);
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
