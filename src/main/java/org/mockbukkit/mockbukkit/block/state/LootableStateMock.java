package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.block.Block;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.Objects;

/**
 * Mock implementation of a {@link Lootable}.
 *
 * @see ContainerStateMock
 */
@NullMarked
public abstract class LootableStateMock extends ContainerStateMock implements Lootable, Nameable
{
	private @Nullable LootTable lootTable = null;
	private long seed = 0;

	protected LootableStateMock(Material material)
	{
		super(material);
	}

	protected LootableStateMock(Block block)
	{
		super(block);
	}

	protected LootableStateMock(LootableStateMock state)
	{
		super(state);
		this.lootTable = state.lootTable;
		this.seed = state.seed;
	}

	@Override
	public @Nullable LootTable getLootTable()
	{
		return this.lootTable;
	}

	@Override
	public void setLootTable(@Nullable LootTable table)
	{
		this.setLootTable(table, this.getSeed());
	}

	@Override
	public void setLootTable(@Nullable LootTable table, long seed)
	{
		this.lootTable = table;
		this.seed = seed;
	}

	@Override
	public void setSeed(long seed)
	{
		this.seed = seed;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	protected abstract InventoryMock createInventory();

	@Override
	public abstract LootableStateMock getSnapshot();

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof LootableStateMock that))
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		return seed == that.seed && Objects.equals(lootTable, that.lootTable);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), lootTable, seed);
	}

}
