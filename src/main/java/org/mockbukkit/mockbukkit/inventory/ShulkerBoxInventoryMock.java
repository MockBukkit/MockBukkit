package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a Shulker Box {@link InventoryType}.
 *
 * @see InventoryMock
 * @see InventoryType#SHULKER_BOX
 */
public class ShulkerBoxInventoryMock extends InventoryMock
{

	/**
	 * Constructs a new {@link ShulkerBoxInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public ShulkerBoxInventoryMock(InventoryHolder holder)
	{
		super(holder, 27, InventoryType.SHULKER_BOX);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new ShulkerBoxInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
