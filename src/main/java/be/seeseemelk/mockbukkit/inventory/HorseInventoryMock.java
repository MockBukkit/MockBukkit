package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class HorseInventoryMock extends AbstractHorseInventoryMock implements HorseInventory
{

	private static final int ARMOR_SLOT = 1;

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
