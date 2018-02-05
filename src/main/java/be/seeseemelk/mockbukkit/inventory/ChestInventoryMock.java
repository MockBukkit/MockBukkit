package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class ChestInventoryMock extends InventoryMock
{

	public ChestInventoryMock(InventoryHolder holder, String name, int size)
	{
		super(holder, name, size, InventoryType.CHEST);
	}

}
