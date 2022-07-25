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

	private @Nullable ItemStack result = null;

	public SmithingInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.SMITHING);
	}

	@Override
	public @Nullable ItemStack getResult()
	{
		return this.result;
	}

	@Override
	public void setResult(@Nullable ItemStack newResult)
	{
		this.result = newResult;
	}

	@Override
	public @Nullable Recipe getRecipe()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
