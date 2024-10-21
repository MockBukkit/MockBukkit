package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link LlamaInventory}.
 *
 * @see InventoryMock
 */
public class LlamaInventoryMock extends AbstractHorseInventoryMock implements LlamaInventory
{

	private static final int DECOR_SLOT = 1;

	/**
	 * Constructs a new {@link LlamaInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public LlamaInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder);
	}

	@Override
	public @Nullable ItemStack getDecor()
	{
		return getItem(DECOR_SLOT);
	}

	@Override
	public void setDecor(@Nullable ItemStack stack)
	{
		setItem(DECOR_SLOT, stack);
	}

}
