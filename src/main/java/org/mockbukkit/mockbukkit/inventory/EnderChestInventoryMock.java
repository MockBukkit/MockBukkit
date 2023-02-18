package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

/**
 * Mock implementation of an Ender Chest {@link InventoryType}.
 *
 * @see InventoryMock
 * @see InventoryType#ENDER_CHEST
 */
public class EnderChestInventoryMock extends InventoryMock
{

	/**
	 * Constructs a new {@link EnderChestInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public EnderChestInventoryMock(InventoryHolder holder)
	{
		super(holder, 27, InventoryType.ENDER_CHEST);
	}

}
