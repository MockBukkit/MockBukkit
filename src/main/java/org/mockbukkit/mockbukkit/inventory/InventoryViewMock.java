package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of an {@link InventoryView}.
 */
public abstract class InventoryViewMock implements InventoryView
{

	private Inventory topInventory;
	private Inventory bottomInventory;
	private HumanEntity player;
	private InventoryType type;
	private String name;
	private String originalTitle;
	private boolean titleChanged = false;
	private ItemStack cursor = null;

	/**
	 * Constructs a new {@link InventoryViewMock} with the provided parameters.
	 *
	 * @param player The player this view is for.
	 * @param name   The name of the view (title).
	 * @param top    The top inventory.
	 * @param bottom The bottom inventory.
	 * @param type   The type of the inventory.
	 */
	protected InventoryViewMock(HumanEntity player, String name, Inventory top, Inventory bottom, InventoryType type)
	{
		this.player = player;
		this.type = type;
		this.name = name;
		topInventory = top;
		bottomInventory = bottom;
	}

	/**
	 * Sets the top inventory.
	 *
	 * @param inventory The top inventory.
	 */
	public void setTopInventory(Inventory inventory)
	{
		topInventory = inventory;
	}

	/**
	 * Sets the bottom inventory.
	 *
	 * @param inventory The bottom inventory.
	 */
	public void setBottomInventory(Inventory inventory)
	{
		bottomInventory = inventory;
	}

	/**
	 * Sets the player viewing.
	 *
	 * @param player The player viewing.
	 */
	public void setPlayer(HumanEntity player)
	{
		this.player = player;
	}

	/**
	 * Sets the type of inventory view.
	 *
	 * @param type The new type of inventory view.
	 */
	public void setType(InventoryType type)
	{
		this.type = type;
	}

	@Override
	public @NotNull Inventory getTopInventory()
	{
		return topInventory;
	}

	@Override
	public @NotNull Inventory getBottomInventory()
	{
		return bottomInventory;
	}

	@Override
	public @NotNull HumanEntity getPlayer()
	{
		return player;
	}

	@Override
	public @NotNull InventoryType getType()
	{
		return type;
	}

	@Override
	public @NotNull String getTitle()
	{
		return name;
	}

	@Override
	public @NotNull String getOriginalTitle()
	{
		return this.originalTitle;
	}

	@Override
	public void setTitle(@NotNull String title)
	{
		if (!this.titleChanged)
		{
			this.originalTitle = this.name;
			this.titleChanged = true;
		}
		this.name = title;
	}

	@Override
	public void setCursor(@Nullable ItemStack item)
	{
		this.player.setItemOnCursor(item);
	}

	@Override
	public @NotNull ItemStack getCursor()
	{
		return this.player.getItemOnCursor();
	}

	@Override
	public void setItem(int slot, @Nullable ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack getItem(int slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Inventory getInventory(int rawSlot)
	{
		// Slot may be -1 if not properly detected due to client bug
		// e.g. dropping an item into part of the enchantment list section of an enchanting table
		if (rawSlot == OUTSIDE || rawSlot == -1)
		{
			return null;
		}
		Preconditions.checkArgument(rawSlot >= 0, "Negative, non outside slot %s", rawSlot);
		Preconditions.checkArgument(rawSlot < this.countSlots(), "Slot %s greater than inventory slot count", rawSlot);

		if (rawSlot < this.getTopInventory().getSize())
		{
			return this.getTopInventory();
		}
		else
		{
			return this.getBottomInventory();
		}
	}

	@Override
	public int convertSlot(int rawSlot)
	{
		int numInTop = this.getTopInventory().getSize();
		// Index from the top inventory as having slots from [0,size]
		if (rawSlot < numInTop)
		{
			return rawSlot;
		}

		// Move down the slot index by the top size
		int slot = rawSlot - numInTop;

		// Player crafting slots are indexed differently. The matrix is caught by the first return.
		// Creative mode is the same, except that you can't see the crafting slots (but the IDs are still used)
		if (this.getType() == InventoryType.CRAFTING || this.getType() == InventoryType.CREATIVE)
		{
			/*
			 * Raw Slots:
			 *
			 * 5             1  2     0
			 * 6             3  4
			 * 7
			 * 8           45
			 * 9  10 11 12 13 14 15 16 17
			 * 18 19 20 21 22 23 24 25 26
			 * 27 28 29 30 31 32 33 34 35
			 * 36 37 38 39 40 41 42 43 44
			 */

			/*
			 * Converted Slots:
			 *
			 * 39             1  2     0
			 * 38             3  4
			 * 37
			 * 36          40
			 * 9  10 11 12 13 14 15 16 17
			 * 18 19 20 21 22 23 24 25 26
			 * 27 28 29 30 31 32 33 34 35
			 * 0  1  2  3  4  5  6  7  8
			 */

			if (slot < 4)
			{
				// Send [5,8] to [39,36]
				return 39 - slot;
			}
			else if (slot > 39)
			{
				// Slot lives in the extra slot section
				return slot;
			}
			else
			{
				// Reset index so 9 -> 0
				slot -= 4;
			}
		}

		// 27 = 36 - 9
		if (slot >= 27)
		{
			// Put into hotbar section
			slot -= 27;
		}
		else
		{
			// Take out of hotbar section
			// 9 = 36 - 27
			slot += 9;
		}

		return slot;

	}

	@NotNull
	@Override
	public InventoryType.SlotType getSlotType(int slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void close()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int countSlots()
	{
		return this.getTopInventory().getSize() + this.getBottomInventory().getSize();
	}

	@Override
	public boolean setProperty(@NotNull InventoryView.Property prop, int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
