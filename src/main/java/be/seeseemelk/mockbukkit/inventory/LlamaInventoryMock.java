package be.seeseemelk.mockbukkit.inventory;


import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;
import org.jetbrains.annotations.Nullable;

public class LlamaInventoryMock extends AbstractHorseInventoryMock implements LlamaInventory
{

	@Nullable ItemStack decor;

	public LlamaInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder);
	}

	@Override
	public @Nullable ItemStack getDecor()
	{
		return this.decor;
	}

	@Override
	public void setDecor(@Nullable ItemStack stack)
	{
		this.decor = stack;
	}

}
