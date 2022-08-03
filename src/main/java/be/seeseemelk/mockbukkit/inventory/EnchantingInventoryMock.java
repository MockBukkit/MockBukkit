package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EnchantingInventoryMock extends InventoryMock implements EnchantingInventory
{

	private static final int ITEM_SLOT = 0;
	private static final int SECONDARY_SLOT = 1;

	public EnchantingInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.ENCHANTING);
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
	public void setSecondary(@Nullable ItemStack item)
	{
		setItem(SECONDARY_SLOT, item);
	}

	@Override
	public @Nullable ItemStack getSecondary()
	{
		return getItem(SECONDARY_SLOT);
	}

}
