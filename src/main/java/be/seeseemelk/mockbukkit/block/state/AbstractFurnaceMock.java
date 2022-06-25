package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbstractFurnaceMock extends ContainerMock implements Furnace
{

	private short burnTime;
	private short cookTime;
	private int cookTimeTotal;
	private double cookSpeedMultiplier = 1.0;

	protected AbstractFurnaceMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SMOKER, Material.FURNACE, Material.BLAST_FURNACE);
	}

	protected AbstractFurnaceMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SMOKER, Material.FURNACE, Material.BLAST_FURNACE);
	}

	protected AbstractFurnaceMock(@NotNull AbstractFurnaceMock state)
	{
		super(state);
		this.burnTime = state.burnTime;
		this.cookTime = state.cookTime;
		this.cookTimeTotal = state.cookTimeTotal;
		this.cookSpeedMultiplier = state.cookSpeedMultiplier;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
//		return new FurnaceInventoryMock(this); TODO: Not implemented
		return new InventoryMock(null, InventoryType.FURNACE);
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
		return (FurnaceInventory) super.getSnapshotInventory();
	}

}
