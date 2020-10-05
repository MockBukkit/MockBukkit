package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ShulkerBoxInventoryMock extends InventoryMock
{

	public ShulkerBoxInventoryMock(InventoryHolder holder)
	{
		super(holder, 27, InventoryType.SHULKER_BOX);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new ShulkerBoxInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
