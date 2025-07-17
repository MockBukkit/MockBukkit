package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Objects;

/**
 * Mock implementation of a {@link BrushableBlock}.
 *
 * @see TileStateMock
 */
public class BrushableBlockStateMock extends TileStateMock implements BrushableBlock
{

	private @NotNull ItemStack item = ItemStack.empty();

	protected BrushableBlockStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected BrushableBlockStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected BrushableBlockStateMock(@NotNull BrushableBlockStateMock state)
	{
		super(state);
		this.item = state.item.clone();
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		return this.item.clone();
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		this.item = (item == null) ? ItemStack.empty() : item.clone();
	}

	@Override
	public void setLootTable(@Nullable LootTable table)
	{
		this.setLootTable(table, this.getSeed());
	}

	@Override
	public void setLootTable(@Nullable LootTable table, long seed)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable LootTable getLootTable()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BrushableBlockStateMock getSnapshot()
	{
		return new BrushableBlockStateMock(this);
	}

	@Override
	public final boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof BrushableBlockStateMock that))
		{
			return false;
		}

		if (!super.equals(o))
		{
			return false;
		}

		return item.equals(that.item);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.item);
	}

}
