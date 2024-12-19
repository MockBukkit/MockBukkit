package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.MerchantInventoryMock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Mock implementation of an {@link AbstractVillager}.
 *
 * @see AgeableMock
 */
public abstract class AbstractVillagerMock extends AgeableMock implements Merchant, AbstractVillager, InventoryHolder
{
	private final MerchantInventoryMock inventory = new MerchantInventoryMock(this, this);
	private final List<MerchantRecipe> recipes = new ArrayList<>();

	private @Nullable HumanEntity trader;

	/**
	 * Constructs a new {@link AbstractVillagerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected AbstractVillagerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);

		updateTrades();
	}

	@Override
	public @NotNull MerchantInventoryMock getInventory()
	{
		return this.inventory;
	}

	@Override
	public void resetOffers()
	{
		this.recipes.clear();
		updateTrades();
	}

	@Override
	public @NotNull List<MerchantRecipe> getRecipes()
	{
		return List.copyOf(this.recipes);
	}

	@Override
	public void setRecipes(@NotNull List<MerchantRecipe> recipes)
	{
		List<MerchantRecipe> clonedRecipes = recipes.stream()
				.map(MerchantRecipe::new)
				.toList();

		this.recipes.clear();
		this.recipes.addAll(clonedRecipes);
	}

	@Override
	public @NotNull MerchantRecipe getRecipe(int i) throws IndexOutOfBoundsException
	{

		return new MerchantRecipe(this.recipes.get(i));
	}

	@Override
	public void setRecipe(int i, @NotNull MerchantRecipe recipe) throws IndexOutOfBoundsException
	{
		this.recipes.set(i, new MerchantRecipe(recipe));
	}

	@Override
	public int getRecipeCount()
	{
		return this.recipes.size();
	}

	@Override
	public boolean isTrading()
	{
		return this.getTrader() != null;
	}

	@Override
	public @Nullable HumanEntity getTrader()
	{
		return this.trader;
	}

	/**
	 * Sets the player this merchant is trading with, or {@code null} if it is not
	 * currently trading.
	 *
	 * @param trader the trader, or null
	 */
	public void setTrader(@Nullable HumanEntity trader)
	{
		this.trader = trader;
	}

	protected abstract void updateTrades();

}
