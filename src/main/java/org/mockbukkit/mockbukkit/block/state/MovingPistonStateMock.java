package org.mockbukkit.mockbukkit.block.state;

import io.papermc.paper.block.MovingPiston;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Mock implementation of a {@link MovingPiston}.
 *
 * @see BlockStateMock
 */
public class MovingPistonStateMock extends TileStateMock implements MovingPiston
{

	protected MovingPistonStateMock(@NotNull Block block)
	{
		super(block);
	}

	public MovingPistonStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected MovingPistonStateMock(@NotNull MovingPistonStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull MovingPistonStateMock getSnapshot()
	{
		return new MovingPistonStateMock(this);
	}

	@Override
	public @NotNull MovingPistonStateMock copy()
	{
		return new MovingPistonStateMock(this);
	}

	@Override
	public @NotNull BlockData getMovingBlock()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockFace getDirection()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isExtending()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPistonHead()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
