package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link GrindstoneInventory}.
 *
 * @see InventoryMock
 */
public class GrindstoneInventoryMock extends InventoryMock implements GrindstoneInventory
{

	/**
	 * Constructs a new {@link GrindstoneInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public GrindstoneInventoryMock(InventoryHolder holder)
	{
		super(holder, InventoryType.GRINDSTONE);
	}

	protected GrindstoneInventoryMock(@NotNull GrindstoneInventoryMock inventory)
	{
		super(inventory);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		return new GrindstoneInventoryMock(this);
	}

}
