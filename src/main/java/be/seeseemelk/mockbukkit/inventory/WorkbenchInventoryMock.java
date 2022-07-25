package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorkbenchInventoryMock extends InventoryMock implements CraftingInventory
{

	private @Nullable ItemStack result = null;
	private @Nullable ItemStack[] matrix = null;

	public WorkbenchInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.WORKBENCH);
	}

	@Override
	public @Nullable ItemStack getResult()
	{
		return this.result;
	}

	@Override
	public @Nullable ItemStack @NotNull [] getMatrix()
	{
		return this.matrix;
	}

	@Override
	public void setResult(@Nullable ItemStack newResult)
	{
		this.result = newResult;
	}

	@Override
	public void setMatrix(@Nullable ItemStack @NotNull [] contents)
	{
		this.matrix = contents;
	}

	@Override
	public @Nullable Recipe getRecipe()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
