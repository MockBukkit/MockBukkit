package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Crafter;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.CrafterInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link Crafter}.
 *
 * @see ContainerStateMock
 */
public class CrafterStateMock extends ContainerStateMock implements Crafter
{

	private final boolean[] disabledSlots = new boolean[9];
	private boolean triggered = false;

	public CrafterStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected CrafterStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected CrafterStateMock(@NotNull CrafterStateMock state)
	{
		super(state);
		System.arraycopy(state.disabledSlots, 0, this.disabledSlots, 0, 9);
		this.triggered = state.triggered;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new CrafterInventoryMock(this);
	}

	@Override
	public @NotNull CrafterStateMock getSnapshot()
	{
		return new CrafterStateMock(this);
	}

	@Override
	public @NotNull CrafterStateMock copy()
	{
		return new CrafterStateMock(this);
	}

	@Override
	public boolean isSlotDisabled(int i)
	{
		return this.disabledSlots[i];
	}

	@Override
	public void setSlotDisabled(int i, boolean b)
	{
		this.disabledSlots[i] = b;
	}

	@Override
	public int getCraftingTicks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCraftingTicks(int i)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTriggered()
	{
		return this.triggered;
	}

	@Override
	public void setTriggered(boolean b)
	{
		this.triggered = b;
	}

	@Override
	public @Nullable LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLootTable(@Nullable LootTable lootTable)
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
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long l)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public boolean canPlayerLoot(@NotNull UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayerLooted(@NotNull UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID uuid)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setHasPlayerLooted(@NotNull UUID uuid, boolean b)
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
	public long setNextRefill(long l)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
