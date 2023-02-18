package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CartographyInventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link CartographyInventory}.
 *
 * @see InventoryMock
 */
public class CartographyInventoryMock extends InventoryMock implements CartographyInventory
{

	/**
	 * Constructs a new {@link CartographyInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public CartographyInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.CARTOGRAPHY);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		CartographyInventoryMock inventory = new CartographyInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
