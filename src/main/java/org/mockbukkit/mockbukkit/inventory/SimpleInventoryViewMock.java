package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * A basic inventory view.
 *
 * @see InventoryViewMock
 */
public class SimpleInventoryViewMock extends InventoryViewMock
{

	/**
	 * Constructs a new {@link SimpleInventoryViewMock} with the provided parameters, and "Inventory" as the name.
	 *
	 * @param player The player this view is for.
	 * @param top    The top inventory.
	 * @param bottom The bottom inventory.
	 * @param type   The type of the inventory.
	 */
	public SimpleInventoryViewMock(HumanEntity player, Inventory top, Inventory bottom, InventoryType type)
	{
		super(player, top, bottom, type);
	}

	/**
	 * Creates a very simple mock {@code IntventoryView} with as player, top,
	 * and bottom {@code null}, and as type {@code InventoryType.CHEST}.
	 */
	public SimpleInventoryViewMock()
	{
		this(null, null, null, InventoryType.CHEST);
	}

	@Override
	public void setCursor(@Nullable ItemStack item)
	{
		this.getPlayer().setItemOnCursor(item);
	}

	@Override
	public @NotNull ItemStack getCursor()
	{
		return this.getPlayer().getItemOnCursor();
	}

	@Override
	public @Nullable Inventory getInventory(int rawSlot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int convertSlot(int rawSlot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setProperty(@NotNull InventoryView.Property prop, int value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
