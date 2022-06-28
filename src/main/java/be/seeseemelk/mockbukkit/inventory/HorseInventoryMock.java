package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HorseInventoryMock extends InventoryMock implements HorseInventory
{

	public HorseInventoryMock(InventoryHolder holder)
	{
		super(holder, 2, InventoryType.CHEST);
	}

	@Override
	public ItemStack getSaddle()
	{
		return this.getItem(0);
	}

	@Override
	public void setSaddle(@Nullable ItemStack stack)
	{
		this.setItem(0, stack);
	}

	@Override
	public ItemStack getArmor()
	{
		return this.getItem(1);
	}

	@Override
	public void setArmor(@Nullable ItemStack stack)
	{
		this.setItem(1, stack);
	}

}
