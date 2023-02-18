package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a Barrel {@link InventoryType}.
 *
 * @see InventoryMock
 * @see InventoryType#BARREL
 */
public class BarrelInventoryMock extends InventoryMock
{

	/**
	 * Constructs a new {@link BarrelInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public BarrelInventoryMock(InventoryHolder holder)
	{
		super(holder, 27, InventoryType.BARREL);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new BarrelInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
