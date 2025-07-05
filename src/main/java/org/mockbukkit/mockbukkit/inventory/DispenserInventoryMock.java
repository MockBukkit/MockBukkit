package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a Dispenser {@link InventoryType}.
 *
 * @see InventoryMock
 * @see InventoryType#DISPENSER
 */
public class DispenserInventoryMock extends InventoryMock
{

	/**
	 * Constructs a new {@link DispenserInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public DispenserInventoryMock(InventoryHolder holder)
	{
		super(holder, 9, InventoryType.DISPENSER);
	}

	protected DispenserInventoryMock(@NotNull DispenserInventoryMock inventory)
	{
		super(inventory);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		return new DispenserInventoryMock(this);
	}

}
