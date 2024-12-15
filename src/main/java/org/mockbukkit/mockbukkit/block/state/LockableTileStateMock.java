package org.mockbukkit.mockbukkit.block.state;

import io.papermc.paper.block.LockableTileState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

public abstract class LockableTileStateMock extends TileStateMock implements LockableTileState
{

	protected LockableTileStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected LockableTileStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected LockableTileStateMock(@NotNull TileStateMock state)
	{
		super(state);
	}

	@Override
	public abstract boolean isLocked();

	@Override
	public abstract @NotNull String getLock();

	@Override
	public abstract void setLock(@Nullable String key);

	@Override
	public void setLockItem(@Nullable ItemStack itemStack)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public abstract @NotNull TileStateMock getSnapshot();

}
