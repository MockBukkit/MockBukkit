package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CrafterInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link CrafterInventory}.
 *
 * @see InventoryMock
 * @see InventoryType#CRAFTER
 */
public class CrafterInventoryMock extends InventoryMock implements CrafterInventory
{

	/**
	 * Constructs a new {@link CrafterInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 *
	 * @see InventoryMock#InventoryMock(InventoryHolder, InventoryType)
	 */
	public CrafterInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.CRAFTER);
	}

	protected CrafterInventoryMock(@NotNull CrafterInventoryMock inventory)
	{
		super(inventory);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		return new CrafterInventoryMock(this);
	}

}
