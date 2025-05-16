package org.mockbukkit.mockbukkit.block.state;

import io.papermc.paper.block.LockableTileState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Objects;

public abstract class LockableTileStateMock extends TileStateMock implements LockableTileState
{

	private @NotNull String lock = "";

	protected LockableTileStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected LockableTileStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected LockableTileStateMock(@NotNull LockableTileStateMock state)
	{
		super(state);
		this.lock = state.lock;
	}

	@Override
	public boolean isLocked()
	{
		return !this.lock.isEmpty();
	}

	@Override
	public @NotNull String getLock()
	{
		return this.lock;
	}

	@Override
	public void setLock(@Nullable String key)
	{
		this.lock = key == null ? "" : key;
	}

	@Override
	public void setLockItem(@Nullable ItemStack itemStack)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof LockableTileStateMock that)) return false;
		if (!super.equals(o)) return false;

		return Objects.equals(lock, that.lock);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), lock);
	}

}
