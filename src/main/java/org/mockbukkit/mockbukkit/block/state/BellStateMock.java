package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Bell}.
 *
 * @see TileStateMock
 */
public class BellStateMock extends TileStateMock implements Bell
{

	/**
	 * Constructs a new {@link BellStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#BELL}
	 *
	 * @param material The material this state is for.
	 */
	public BellStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BELL);
	}

	/**
	 * Constructs a new {@link BellStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#BELL}
	 *
	 * @param block The block this state is for.
	 */
	protected BellStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BELL);
	}

	/**
	 * Constructs a new {@link BellStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BellStateMock(@NotNull BellStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BellStateMock getSnapshot()
	{
		return new BellStateMock(this);
	}

	@Override
	public @NotNull BellStateMock copy()
	{
		return new BellStateMock(this);
	}

	@Override
	public boolean ring(@Nullable Entity entity, @Nullable BlockFace direction)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean ring(@Nullable Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean ring(@Nullable BlockFace direction)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean ring()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isShaking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getShakingTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isResonating()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getResonatingTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
