package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link HorseInventory}.
 *
 * @see InventoryMock
 */
public class HorseInventoryMock extends AbstractHorseInventoryMock implements HorseInventory
{

	private static final int ARMOR_SLOT = 1;

	/**
	 * Constructs a new {@link HorseInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public HorseInventoryMock(InventoryHolder holder)
	{
		super(holder);
	}

	@Override
	public ItemStack getArmor()
	{
		return this.getItem(ARMOR_SLOT);
	}

	@Override
	public void setArmor(@Nullable ItemStack stack)
	{
		this.setItem(ARMOR_SLOT, stack);
	}

}
