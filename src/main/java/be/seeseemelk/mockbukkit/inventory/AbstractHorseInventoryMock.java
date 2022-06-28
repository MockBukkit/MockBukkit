package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AbstractHorseInventoryMock extends InventoryMock implements AbstractHorseInventory
{

	@Nullable ItemStack saddle;

	public AbstractHorseInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, 2, InventoryType.CHEST);
	}

	@Override
	public @Nullable ItemStack getSaddle()
	{
		return this.saddle;
	}

	@Override
	public void setSaddle(@Nullable ItemStack saddle)
	{
		this.saddle = saddle;
	}

}
