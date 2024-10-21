package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BeaconInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link BeaconInventory}.
 *
 * @see InventoryMock
 */
public class BeaconInventoryMock extends InventoryMock implements BeaconInventory
{

	private static final int ITEM_SLOT = 0;

	/**
	 * Constructs a new {@link AbstractHorseInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public BeaconInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.BEACON);
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		setItem(ITEM_SLOT, item);
	}

	@Override
	public @Nullable ItemStack getItem()
	{
		return getItem(ITEM_SLOT);
	}

	@Override
	public @NotNull BeaconInventoryMock getSnapshot()
	{
		BeaconInventoryMock inventory = new BeaconInventoryMock(getHolder());
		inventory.setItem(getItem());
		return inventory;
	}

}
