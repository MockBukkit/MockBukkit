package be.seeseemelk.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrewerInventoryMock extends InventoryMock implements BrewerInventory
{

	private ItemStack ingredient;
	private ItemStack fuel;

	public BrewerInventoryMock(InventoryHolder holder)
	{
		super(holder, InventoryType.BREWING);
	}

	@Override
	public @Nullable ItemStack getIngredient()
	{
		checkHasIngredient();
		return this.ingredient;
	}

	@Override
	public void setIngredient(@Nullable ItemStack ingredient)
	{
		Preconditions.checkNotNull(ingredient, "Ingredient cannot be null");
		this.ingredient = ingredient;
	}

	@Override
	public @Nullable ItemStack getFuel()
	{
		checkHasFuel();
		return this.fuel;
	}

	@Override
	public void setFuel(@Nullable ItemStack fuel)
	{
		Preconditions.checkNotNull(fuel, "Fuel cannot be null");
		this.fuel = fuel;
	}

	@Override
	public BrewingStand getHolder()
	{
		return (BrewingStand) super.getHolder();
	}

	@Override
	public @NotNull BrewerInventoryMock getSnapshot()
	{
		BrewerInventoryMock inventory = new BrewerInventoryMock(getHolder());
		if (ingredient != null)
		{
			inventory.setIngredient(ingredient);
		}
		if (fuel != null)
		{
			inventory.setFuel(fuel);
		}
		return inventory;
	}

	private void checkHasFuel()
	{
		Preconditions.checkState(this.fuel != null, "No fuel has been set");
	}

	private void checkHasIngredient()
	{
		Preconditions.checkState(this.ingredient != null, "No ingredient has been set");
	}


}
