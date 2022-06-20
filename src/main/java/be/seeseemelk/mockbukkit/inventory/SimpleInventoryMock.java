package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleInventoryMock extends InventoryMock
{

	/**
	 * Creates a new inventory with size inherited from {@linkplain InventoryType#getDefaultSize()}.
	 *
	 * @param holder The {@link InventoryHolder}
	 * @param size   The size for this {@link InventoryMock}.
	 * @param type   The {@link InventoryType} for this {@link InventoryMock}
	 */
	public SimpleInventoryMock(@Nullable InventoryHolder holder, int size, @NotNull InventoryType type)
	{
		super(holder, size, type);
	}

	/**
	 * Creates a new inventory with size inherited from {@linkplain InventoryType#getDefaultSize()}.
	 *
	 * @param holder The {@link InventoryHolder}
	 * @param type   The {@link InventoryType} for this {@link InventoryMock}
	 */
	public SimpleInventoryMock(@Nullable InventoryHolder holder, @NotNull InventoryType type)
	{
		super(holder, type);
	}

	/**
	 * Creates a very simple inventory with no holder, as name
	 * {@code Inventory}, a size of 9, and an inventory type of
	 * {@code InventoryType.CHEST}.
	 */
	public SimpleInventoryMock()
	{
		super(null, 9, InventoryType.CHEST);
	}
}
