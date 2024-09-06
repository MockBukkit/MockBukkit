package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.block.Lectern;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.LecternInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link LecternInventory}.
 *
 * @see InventoryMock
 */
public class LecternInventoryMock extends InventoryMock implements LecternInventory
{

	/**
	 * Constructs a new {@link LecternInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public LecternInventoryMock(InventoryHolder holder)
	{
		super(holder, InventoryType.LECTERN);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new LecternInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

	@Override
	public @Nullable Lectern getHolder()
	{
		return (Lectern) super.getHolder();
	}

}
