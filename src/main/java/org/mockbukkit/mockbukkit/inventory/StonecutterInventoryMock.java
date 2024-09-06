package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.StonecutterInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link StonecutterInventory}.
 *
 * @see InventoryMock
 */
public class StonecutterInventoryMock extends InventoryMock implements StonecutterInventory
{

	/**
	 * Constructs a new {@link StonecutterInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public StonecutterInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.STONECUTTER);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		StonecutterInventoryMock inventory = new StonecutterInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
