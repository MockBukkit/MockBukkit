package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingInventory;
import org.jetbrains.annotations.Nullable;


public class SmithingInventoryMock extends InventoryMock implements SmithingInventory
{

	private static final int RESULT_SLOT = 0;

	public SmithingInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.SMITHING);
	}

	@Override
	public @Nullable ItemStack getResult()
	{
		return getItem(RESULT_SLOT);
	}

	@Override
	public void setResult(@Nullable ItemStack result)
	{
		setItem(RESULT_SLOT, result);
	}

	@Override
	public @Nullable Recipe getRecipe()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
