package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BeaconInventory;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeaconInventoryMock extends InventoryMock implements BeaconInventory
{

	private @Nullable ItemStack item;

	public BeaconInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.BEACON);
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		this.item = item;
	}

	@Override
	public @Nullable ItemStack getItem()
	{
		return this.item;
	}

	@Override
	public @NotNull BeaconInventoryMock getSnapshot()
	{
		BeaconInventoryMock inventory = new BeaconInventoryMock(getHolder());
		inventory.setItem(getItem());
		return inventory;
	}

}
