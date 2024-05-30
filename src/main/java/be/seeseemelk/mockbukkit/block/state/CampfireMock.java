package be.seeseemelk.mockbukkit.block.state;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Campfire;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Campfire}.
 *
 * @see TileStateMock
 */
public class CampfireMock extends TileStateMock implements Campfire
{

	private static final int MAX_SLOTS = 4;

	private ItemStack[] items = new ItemStack[MAX_SLOTS];
	private int[] cookingProgress = new int[MAX_SLOTS];
	private int[] cookingTime = new int[MAX_SLOTS];
	private boolean[] cookingDisabled = new boolean[MAX_SLOTS];

	/**
	 * Constructs a new {@link CampfireMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#CAMPFIRES}
	 *
	 * @param material The material this state is for.
	 */
	public CampfireMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Tag.CAMPFIRES);
	}

	/**
	 * Constructs a new {@link CampfireMock} for the provided {@link Block}.
	 * Only supports materials in {@link Tag#CAMPFIRES}
	 *
	 * @param block The block this state is for.
	 */
	protected CampfireMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Tag.CAMPFIRES);
	}

	/**
	 * Constructs a new {@link CampfireMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected CampfireMock(@NotNull CampfireMock state)
	{
		super(state);
		this.items = state.items.clone();
		this.cookingProgress = state.cookingProgress.clone();
		this.cookingTime = state.cookingTime.clone();
		this.cookingDisabled = state.cookingDisabled.clone();
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new CampfireMock(this);
	}

	@Override
	public int getSize()
	{
		return MAX_SLOTS;
	}

	@Override
	public @Nullable ItemStack getItem(int index)
	{
		checkSlot(index); // Sanity check
		return this.items[index];
	}

	@Override
	public void setItem(int index, @Nullable ItemStack item)
	{
		checkSlot(index);
		this.items[index] = item;
	}

	@Override
	public int getCookTime(int index)
	{
		checkSlot(index);
		return this.cookingTime[index];
	}

	@Override
	public void setCookTime(int index, int cookTime)
	{
		checkSlot(index);
		this.cookingTime[index] = cookTime;
	}

	@Override
	public int getCookTimeTotal(int index)
	{
		checkSlot(index);
		return this.cookingProgress[index];
	}

	@Override
	public void setCookTimeTotal(int index, int cookTimeTotal)
	{
		checkSlot(index);
		this.cookingProgress[index] = cookTimeTotal;
	}

	@Override
	public void stopCooking()
	{
		for (int i = 0; i < this.cookingDisabled.length; ++i)
		{
			this.stopCooking(i);
		}
	}

	@Override
	public void startCooking()
	{
		for (int i = 0; i < this.cookingDisabled.length; ++i)
		{
			this.startCooking(i);
		}
	}

	@Override
	public boolean stopCooking(int index)
	{
		checkSlot(index);
		boolean previous = this.isCookingDisabled(index);
		this.cookingDisabled[index] = true;
		return previous;
	}

	@Override
	public boolean startCooking(int index)
	{
		checkSlot(index);
		boolean previous = this.isCookingDisabled(index);
		this.cookingDisabled[index] = false;
		return previous;
	}

	@Override
	public boolean isCookingDisabled(int index)
	{
		checkSlot(index);
		return this.cookingDisabled[index];
	}

	// TODO: Implement a 'cookTick' method to simulate one server tick worth of
	// cooking items. This currently isn't possible as there's no default recipes to
	// change the item types once fully cooked.

	private static void checkSlot(int index)
	{
		int maxSlots = MAX_SLOTS - 1;
		Validate.isTrue(index >= 0 && index <= maxSlots,
				"Slot index must be between 0 and " + maxSlots + " (inclusive)");
	}

}
