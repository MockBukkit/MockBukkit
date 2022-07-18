package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EnchantingInventoryMock extends InventoryMock implements EnchantingInventory
{

	private ItemStack primaryItem = null;
	private ItemStack secondaryItem = null;

	public EnchantingInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.ENCHANTING);
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		this.primaryItem = item;
	}

	@Override
	public @Nullable ItemStack getItem()
	{
		return this.primaryItem;
	}

	@Override
	public void setSecondary(@Nullable ItemStack item)
	{
		this.secondaryItem = item;
	}

	@Override
	public @Nullable ItemStack getSecondary()
	{
		return this.secondaryItem;
	}

}
