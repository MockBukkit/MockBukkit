package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import org.mockbukkit.mockbukkit.inventory.FurnaceInventoryMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of a {@link Furnace}.
 *
 * @see ContainerStateMock
 */
public abstract class AbstractFurnaceStateMock extends ContainerStateMock implements Furnace
{

	private short burnTime;
	private short cookTime;
	private int cookTimeTotal;
	private double cookSpeedMultiplier = 1.0;

	/**
	 * Constructs a new {@link AbstractFurnaceStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#SMOKER}, {@link Material#FURNACE}, and {@link Material#BLAST_FURNACE}.
	 *
	 * @param material The material this state is for.
	 */
	protected AbstractFurnaceStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SMOKER, Material.FURNACE, Material.BLAST_FURNACE);
	}

	/**
	 * Constructs a new {@link AbstractFurnaceStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#SMOKER}, {@link Material#FURNACE}, and {@link Material#BLAST_FURNACE}.
	 *
	 * @param block The block this state is for.
	 */
	protected AbstractFurnaceStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SMOKER, Material.FURNACE, Material.BLAST_FURNACE);
	}

	/**
	 * Constructs a new {@link AbstractFurnaceStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected AbstractFurnaceStateMock(@NotNull AbstractFurnaceStateMock state)
	{
		super(state);
		this.burnTime = state.burnTime;
		this.cookTime = state.cookTime;
		this.cookTimeTotal = state.cookTimeTotal;
		this.cookSpeedMultiplier = state.cookSpeedMultiplier;
	}

	@Override
	protected @NotNull FurnaceInventoryMock createInventory()
	{
		return new FurnaceInventoryMock(this);
	}

	@Override
	public short getBurnTime()
	{
		return this.burnTime;
	}

	@Override
	public void setBurnTime(short burnTime)
	{
		this.burnTime = burnTime;
	}

	@Override
	public short getCookTime()
	{
		return this.cookTime;
	}

	@Override
	public void setCookTime(short cookTime)
	{
		this.cookTime = cookTime;
	}

	@Override
	public int getCookTimeTotal()
	{
		return this.cookTimeTotal;
	}

	@Override
	public void setCookTimeTotal(int cookTimeTotal)
	{
		this.cookTimeTotal = cookTimeTotal;
	}

	@Override
	public @NotNull Map<CookingRecipe<?>, Integer> getRecipesUsed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getCookSpeedMultiplier()
	{
		return this.cookSpeedMultiplier;
	}

	@Override
	public void setCookSpeedMultiplier(double multiplier)
	{
		Preconditions.checkArgument(multiplier >= 0, "Furnace speed multiplier cannot be negative");
		Preconditions.checkArgument(multiplier <= 200, "Furnace speed multiplier cannot more than 200");
		this.cookSpeedMultiplier = multiplier;
	}

	@Override
	public int getRecipeUsedCount(@NotNull NamespacedKey furnaceRecipe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasRecipeUsedCount(@NotNull NamespacedKey furnaceRecipe)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRecipeUsedCount(@NotNull CookingRecipe<?> furnaceRecipe, int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRecipesUsed(@NotNull Map<CookingRecipe<?>, Integer> recipesUsed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public FurnaceInventory getInventory()
	{
		if (!this.isPlaced())
		{
			return this.getSnapshotInventory();
		}
		return (FurnaceInventory) super.getInventory();
	}

	@NotNull
	@Override
	public FurnaceInventory getSnapshotInventory()
	{
		FurnaceInventory rawInventory = (FurnaceInventory) super.getInventory();
		FurnaceInventoryMock mock = new FurnaceInventoryMock(rawInventory.getHolder());
		mock.setResult(rawInventory.getResult());
		mock.setFuel(rawInventory.getFuel());
		mock.setSmelting(rawInventory.getSmelting());
		return mock;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof AbstractFurnaceStateMock that)) return false;
		if (!super.equals(o)) return false;
		return burnTime == that.burnTime
				&& cookTime == that.cookTime
				&& cookTimeTotal == that.cookTimeTotal
				&& Double.compare(cookSpeedMultiplier, that.cookSpeedMultiplier) == 0;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), burnTime, cookTime, cookTimeTotal, cookSpeedMultiplier);
	}

	@Override
	protected String toStringInternal() {
		return super.toStringInternal() +
				", burnTime=" + burnTime +
				", cookTime=" + cookTime +
				", cookTimeTotal=" + cookTimeTotal +
				", cookSpeedMultiplier=" + cookSpeedMultiplier;
	}
}
