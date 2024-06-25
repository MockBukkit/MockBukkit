package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of an {@link InventoryView} for players.
 *
 * @see InventoryViewMock
 */
public class PlayerInventoryViewMock extends InventoryViewMock
{

	/**
	 * Constructs a new {@link PlayerInventoryViewMock} for the provided player, with the specified top inventory.
	 *
	 * @param player The player to create the view for.
	 * @param top    The top inventory.
	 */
	public PlayerInventoryViewMock(@NotNull HumanEntity player, @NotNull Inventory top)
	{
		super(player, "Inventory", top, player.getInventory(), top.getType());
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
	public void setCursor(@Nullable ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getCursor()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
