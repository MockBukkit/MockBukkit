package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ChiseledBookshelfInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link ChiseledBookshelfInventory}.
 *
 * @see InventoryMock
 */
public class ChiseledBookshelfInventoryMock extends InventoryMock implements ChiseledBookshelfInventory
{

	public ChiseledBookshelfInventoryMock(@Nullable ChiseledBookshelf holder)
	{
		super(holder, InventoryType.CHISELED_BOOKSHELF);
	}

	protected ChiseledBookshelfInventoryMock(ChiseledBookshelfInventoryMock inventory)
	{
		super(inventory);
	}

	@Override
	public @Nullable ChiseledBookshelf getHolder()
	{
		return (ChiseledBookshelf) super.getHolder();
	}

	@Override
	public @NotNull ChiseledBookshelfInventoryMock getSnapshot()
	{
		return new ChiseledBookshelfInventoryMock(this);
	}

}
