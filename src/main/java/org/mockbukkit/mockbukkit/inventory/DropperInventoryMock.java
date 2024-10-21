package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a Dropper {@link InventoryType}.
 *
 * @see InventoryMock
 * @see InventoryType#DROPPER
 */
public class DropperInventoryMock extends InventoryMock
{

	/**
	 * Constructs a new {@link DropperInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public DropperInventoryMock(InventoryHolder holder)
	{
		super(holder, 9, InventoryType.DROPPER);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new DropperInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
