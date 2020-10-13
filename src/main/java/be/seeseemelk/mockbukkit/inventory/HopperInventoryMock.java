package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class HopperInventoryMock extends InventoryMock
{

	public HopperInventoryMock(InventoryHolder holder)
	{
		super(holder, 9, InventoryType.HOPPER);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new HopperInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
