package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of an {@link AbstractHorseInventory}.
 *
 * @see InventoryMock
 */
public class AbstractHorseInventoryMock extends InventoryMock implements AbstractHorseInventory
{

	private static final int SADDLE_SLOT = 0;

	/**
	 * Constructs a new {@link AbstractHorseInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public AbstractHorseInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, 2, InventoryType.CHEST);
	}

	@Override
	public @Nullable ItemStack getSaddle()
	{
		return getItem(SADDLE_SLOT);
	}

	@Override
	public void setSaddle(@Nullable ItemStack saddle)
	{
		setItem(SADDLE_SLOT, saddle);
	}

}
