package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.ChestInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Chest}.
 *
 * @see TileStateMock
 */
public class ChestStateMock extends ContainerStateMock implements Chest
{

	private boolean isOpen = false;

	/**
	 * Constructs a new {@link ChestStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#CHEST} and {@link Material#TRAPPED_CHEST}.
	 *
	 * @param material The material this state is for.
	 */
	public ChestStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.CHEST, Material.TRAPPED_CHEST);
	}

	/**
	 * Constructs a new {@link ChestStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#CHEST} and {@link Material#TRAPPED_CHEST}.
	 *
	 * @param block The block this state is for.
	 */
	protected ChestStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.CHEST, Material.TRAPPED_CHEST);
	}

	/**
	 * Constructs a new {@link ChestStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected ChestStateMock(@NotNull ChestStateMock state)
	{
		super(state);
		this.isOpen = state.isOpen;
	}

	@Override
	public void setLootTable(LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLootTable(@Nullable LootTable lootTable, long l)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void open()
	{
		isOpen = true;
	}

	@Override
	public void close()
	{
		isOpen = false;
	}

	@Override
	public boolean isOpen()
	{
		return isOpen;
	}

	@Override
	public @NotNull Inventory getBlockInventory()
	{
		return getInventory();
	}

	@Override
	public boolean isBlocked()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new ChestInventoryMock(this, 27);
	}

	@Override
	public @NotNull ChestStateMock getSnapshot()
	{
		return new ChestStateMock(this);
	}

	@Override
	public @NotNull ChestStateMock copy()
	{
		return new ChestStateMock(this);
	}

	@Override
	public boolean isRefillEnabled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canPlayerLoot(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayerLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setHasPlayerLooted(@NotNull UUID player, boolean looted)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPendingRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getNextRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long setNextRefill(long refillAt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
