package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link CraftingInventory}.
 *
 * @see InventoryMock
 */
public class WorkbenchInventoryMock extends InventoryMock implements CraftingInventory
{

	private @Nullable ItemStack result = null;

	/**
	 * Constructs a new {@link WorkbenchInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
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
		return this.getContents();
	}

	@Override
	public void setResult(@Nullable ItemStack newResult)
	{
		this.result = newResult;
	}

	@Override
	public void setMatrix(@Nullable ItemStack @NotNull [] contents)
	{
		Preconditions.checkNotNull(contents);
		Preconditions.checkArgument(contents.length <= super.getSize(), "Invalid inventory size. Expected " + super.getSize() + " or less, got " + contents.length);
		super.setContents(contents);
	}

	@Override
	public @Nullable Recipe getRecipe()
	{
		Location location = this.getLocation();
		Preconditions.checkState(location != null, "The location can't be null!");
		Preconditions.checkState(location.getWorld() != null, "The world can't be null!");
		return Bukkit.getCraftingRecipe(this.getContents(), location.getWorld());
	}

}
