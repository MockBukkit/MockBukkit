package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class SimpleInventoryMock extends InventoryMock
{

	public SimpleInventoryMock(InventoryHolder holder, String name, int size, InventoryType type)
	{
		super(holder, name, size, type);
	}

	/**
	 * Creates a very simple inventory with no holder, as name
	 * {@code Inventory}, a size of 9, and an inventory type of
	 * {@code InventoryType.CHEST}.
	 */
	public SimpleInventoryMock()
	{
		super(null, "Inventory", 9, InventoryType.CHEST);
	}

}
