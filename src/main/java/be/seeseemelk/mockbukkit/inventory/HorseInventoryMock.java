package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HorseInventoryMock extends AbstractHorseInventoryMock implements HorseInventory
{

	public HorseInventoryMock(InventoryHolder holder)
	{
		super(holder);
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
