package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class DispenserInventoryMock extends InventoryMock
{

	public DispenserInventoryMock(InventoryHolder holder)
	{
		super(holder, 9, InventoryType.DISPENSER);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new DispenserInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
