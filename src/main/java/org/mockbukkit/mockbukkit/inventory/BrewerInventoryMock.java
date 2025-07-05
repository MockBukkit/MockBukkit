package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link BrewerInventory}.
 *
 * @see InventoryMock
 */
public class BrewerInventoryMock extends InventoryMock implements BrewerInventory
{

	private static final int INGREDIENT_SLOT = 3;
	private static final int FUEL_SLOT = 4;

	/**
	 * Constructs a new {@link BrewerInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public BrewerInventoryMock(InventoryHolder holder)
	{
		super(holder, InventoryType.BREWING);
	}

	protected BrewerInventoryMock(@NotNull BrewerInventoryMock inventory)
	{
		super(inventory);
	}

	@Override
	public @Nullable ItemStack getIngredient()
	{
		checkHasIngredient();
		return getItem(INGREDIENT_SLOT);
	}

	@Override
	public void setIngredient(@Nullable ItemStack ingredient)
	{
		Preconditions.checkNotNull(ingredient, "Ingredient cannot be null");
		setItem(INGREDIENT_SLOT, ingredient);
	}

	@Override
	public @Nullable ItemStack getFuel()
	{
		checkHasFuel();
		return getItem(FUEL_SLOT);
	}

	@Override
	public void setFuel(@Nullable ItemStack fuel)
	{
		Preconditions.checkNotNull(fuel, "Fuel cannot be null");
		setItem(FUEL_SLOT, fuel);
	}

	@Override
	public BrewingStand getHolder()
	{
		return (BrewingStand) super.getHolder();
	}

	@Override
	public @NotNull BrewerInventoryMock getSnapshot()
	{
		return new BrewerInventoryMock(this);
	}

	private void checkHasFuel()
	{
		Preconditions.checkState(getItem(FUEL_SLOT) != null, "No fuel has been set");
	}

	private void checkHasIngredient()
	{
		Preconditions.checkState(getItem(INGREDIENT_SLOT) != null, "No ingredient has been set");
	}

}
