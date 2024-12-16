package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Mock implementation of an {@link MerchantInventory}.
 *
 * @see InventoryMock
 */
public class MerchantInventoryMock extends InventoryMock implements MerchantInventory
{

	private final Merchant merchant;

	private int selectedRecipeIndex = 0;

	public MerchantInventoryMock(@Nullable InventoryHolder holder, @NotNull Merchant merchant)
	{
		super(holder, InventoryType.MERCHANT);

		this.merchant = Preconditions.checkNotNull(merchant, "The merchant cannot be null");
	}

	@Override
	public int getSelectedRecipeIndex()
	{
		return this.selectedRecipeIndex;
	}

	/**
	 * Get the index of the currently selected recipe.
	 *
	 * @param selectedRecipe the index of the currently selected recipe
	 */
	public void setSelectedRecipeIndex(int selectedRecipe)
	{
		final int maxSize = merchant.getRecipeCount();
		Preconditions.checkArgument(0 <= selectedRecipe && selectedRecipe <= maxSize, "Recipe index out of range, value should be between 0 <= index <= %s", maxSize);

		this.selectedRecipeIndex = selectedRecipe;
	}

	@Override
	public @Nullable MerchantRecipe getSelectedRecipe()
	{
		return merchant.getRecipe(this.selectedRecipeIndex);
	}

	@Override
	public @NotNull Merchant getMerchant()
	{
		return this.merchant;
	}

	@Override
	public final boolean equals(Object object)
	{
		if (!(object instanceof MerchantInventoryMock that))
		{
			return false;
		}
		if (!super.equals(object))
		{
			return false;
		}

		return selectedRecipeIndex == that.selectedRecipeIndex && Objects.equals(merchant, that.merchant);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), merchant, selectedRecipeIndex);
	}

}
