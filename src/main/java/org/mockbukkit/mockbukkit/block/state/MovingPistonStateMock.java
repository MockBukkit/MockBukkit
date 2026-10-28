package org.mockbukkit.mockbukkit.block.state;

import io.papermc.paper.block.MovingPiston;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Mock implementation of a {@link MovingPiston}.
 *
 * @see TileStateMock
 */
@NullMarked
public class MovingPistonStateMock extends TileStateMock implements MovingPiston
{

	protected MovingPistonStateMock(Material material)
	{
		super(material);
	}

	protected MovingPistonStateMock(Block block)
	{
		super(block);
	}

	protected MovingPistonStateMock(MovingPistonStateMock state)
	{
		super(state);
	}

	@Override
	public BlockData getMovingBlock()
	{
		//TODO: Auto-generated method stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public BlockFace getDirection()
	{
		//TODO: Auto-generated method stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public boolean isExtending()
	{
		//TODO: Auto-generated method stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public boolean isPistonHead()
	{
		//TODO: Auto-generated method stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public MovingPistonStateMock getSnapshot()
	{
		return new MovingPistonStateMock(this);
	}

}
