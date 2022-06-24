package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ChestInventoryMock extends InventoryMock
{

	public ChestInventoryMock(InventoryHolder holder, int size)
	{
		super(holder, size, InventoryType.CHEST);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new ChestInventoryMock(getHolder(), getSize());
		inventory.setContents(getContents());
		return inventory;
	}

}
