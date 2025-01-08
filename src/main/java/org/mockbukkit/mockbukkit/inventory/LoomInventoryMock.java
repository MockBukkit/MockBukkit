package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.LoomInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link LoomInventory}.
 *
 * @see InventoryMock
 */
public class LoomInventoryMock extends InventoryMock implements LoomInventory
{

	/**
	 * Constructs a new {@link LoomInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public LoomInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.LOOM);
	}

	protected LoomInventoryMock(@NotNull LoomInventoryMock inventory)
	{
		super(inventory);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		return new LoomInventoryMock(this);
	}

}
