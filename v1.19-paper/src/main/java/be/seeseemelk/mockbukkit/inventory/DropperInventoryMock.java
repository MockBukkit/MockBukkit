package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class DropperInventoryMock extends InventoryMock
{

	public DropperInventoryMock(InventoryHolder holder)
	{
		super(holder, 9, InventoryType.DROPPER);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new DropperInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
