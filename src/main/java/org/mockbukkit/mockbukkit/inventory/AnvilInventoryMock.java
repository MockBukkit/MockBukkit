package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of an {@link AnvilInventory}.
 *
 * @see InventoryMock
 */
public class AnvilInventoryMock extends InventoryMock implements AnvilInventory
{

	private @Nullable String renameText;
	private int repairCostAmount = 0;
	private int repairCost = 0;
	private int maxRepairCost = 40;

	/**
	 * Constructs a new {@link AnvilInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public AnvilInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.ANVIL);
	}

	@Override
	public @Nullable String getRenameText()
	{
		return this.renameText;
	}

	@Override
	public int getRepairCostAmount()
	{
		return this.repairCostAmount;
	}

	@Override
	public void setRepairCostAmount(int amount)
	{
		Preconditions.checkArgument(amount >= 0, "Amount cannot be lower than 0");
		this.repairCostAmount = amount;
	}

	@Override
	public int getRepairCost()
	{
		return this.repairCost;
	}

	@Override
	public void setRepairCost(int levels)
	{
		Preconditions.checkArgument(levels >= 0, "Levels cannot be lower than 0");
		this.repairCost = levels;
	}

	@Override
	public int getMaximumRepairCost()
	{
		return this.maxRepairCost;
	}

	@Override
	public void setMaximumRepairCost(int levels)
	{
		Preconditions.checkArgument(levels >= 0, "Levels cannot be lower than 0");
		this.maxRepairCost = levels;
	}

	/**
	 * Sets the result of {@link #getRenameText()}.
	 *
	 * @param text The text to set.
	 */
	public void setRenameText(@Nullable String text)
	{
		this.renameText = text;
	}

}
