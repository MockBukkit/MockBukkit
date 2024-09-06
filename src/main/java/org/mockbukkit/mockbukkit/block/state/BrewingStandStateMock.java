package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.inventory.BrewerInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Mock implementation of a {@link BrewingStand}.
 *
 * @see ContainerStateMock
 */
public class BrewingStandStateMock extends ContainerStateMock implements BrewingStand
{

	private int recipeBrewingTime = 400;
	private int brewingTime;
	private int fuelLevel;

	/**
	 * Constructs a new {@link BrewingStandStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#BREWING_STAND}
	 *
	 * @param material The material this state is for.
	 */
	public BrewingStandStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BREWING_STAND);
	}

	/**
	 * Constructs a new {@link BrewingStandStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#BREWING_STAND}
	 *
	 * @param block The block this state is for.
	 */
	protected BrewingStandStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BREWING_STAND);
	}

	/**
	 * Constructs a new {@link BrewingStandStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BrewingStandStateMock(@NotNull BrewingStandStateMock state)
	{
		super(state);
		this.brewingTime = state.brewingTime;
		this.fuelLevel = state.fuelLevel;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new BrewerInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BrewingStandStateMock(this);
	}

	@Override
	public int getBrewingTime()
	{
		return this.brewingTime;
	}

	@Override
	public void setBrewingTime(int brewTime)
	{
		this.brewingTime = brewTime;
	}

	@Override
	public void setRecipeBrewTime(@Range(from = 1L, to = 2147483647L) int recipeBrewTime)
	{
		Preconditions.checkArgument(recipeBrewTime > 0, "recipeBrewTime must be positive");
		this.recipeBrewingTime = recipeBrewTime;
	}

	@Override
	public @Range(from = 1L, to = 2147483647L) int getRecipeBrewTime()
	{
		return this.recipeBrewingTime;
	}

	@Override
	public int getFuelLevel()
	{
		return this.fuelLevel;
	}

	@Override
	public void setFuelLevel(int level)
	{
		this.fuelLevel = level;
	}

	@Override
	public @NotNull BrewerInventory getInventory()
	{
		return (BrewerInventory) super.getInventory();
	}

	@Override
	public @NotNull BrewerInventory getSnapshotInventory()
	{
		return (BrewerInventory) super.getSnapshotInventory();
	}

}
