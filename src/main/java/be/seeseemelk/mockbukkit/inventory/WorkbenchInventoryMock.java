package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

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
	public ItemStack @NotNull [] getContents()
	{
		ItemStack[] contents = new ItemStack[this.getSize()];
		contents[0] = result;
		for (int i = 1; i < getMatrix().length + 1; i++)
		{
			contents[i] = getMatrix()[i];
		}
		return contents;
	}

	@Override
	public void setContents(ItemStack @NotNull [] items)
	{
		Preconditions.checkArgument(items.length <= this.getSize(), "Invalid inventory size (%s); expected %s or less", items.length, this.getSize());

		this.setResult(items.length != 0 ? items[0] : null);
		this.setMatrix(Arrays.copyOfRange(items, 1, items.length));
	}

	@Override
	public ItemStack getItem(int index)
	{
		Preconditions.checkArgument(index >= 0 && index < getContents().length, "Index should be in range (inclusive) [%s,%s] but was %s.".formatted(0, getContents().length, index));
		// crafting result clicked
		if (index == 0)
		{
			return getResult();
		}
		// matrix clicked
		else
		{
			return getContents()[index - 1];
		}
	}

	@Override
	public void setItem(int index, @Nullable ItemStack item)
	{
		if (index == 0)
		{
			setResult(item);
		}
		else
		{
			getMatrix()[index - 1] = item;
		}
	}

	@Override
	public int getSize()
	{
		// matrix size + result slot
		return super.getSize() + 1;
	}

	@Override
	public @Nullable Recipe getRecipe()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
