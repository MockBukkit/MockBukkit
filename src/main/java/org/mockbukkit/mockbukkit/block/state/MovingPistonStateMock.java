package org.mockbukkit.mockbukkit.block.state;

import io.papermc.paper.block.MovingPiston;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

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
	public @NotNull org.bukkit.block.data.BlockData getMovingBlock()
	{
		// TODO Auto-generated method stub
		throw new org.mockbukkit.mockbukkit.exception.UnimplementedOperationException();
	}

	@Override
	public @NotNull org.bukkit.block.BlockFace getDirection()
	{
		// TODO Auto-generated method stub
		throw new org.mockbukkit.mockbukkit.exception.UnimplementedOperationException();
	}

	@Override
	public boolean isExtending()
	{
		// TODO Auto-generated method stub
		throw new org.mockbukkit.mockbukkit.exception.UnimplementedOperationException();
	}

	@Override
	public boolean isPistonHead()
	{
		// TODO Auto-generated method stub
		throw new org.mockbukkit.mockbukkit.exception.UnimplementedOperationException();
	}

}
