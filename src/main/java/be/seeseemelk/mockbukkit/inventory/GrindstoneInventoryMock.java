package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class GrindstoneInventoryMock extends InventoryMock
{

	public GrindstoneInventoryMock(InventoryHolder holder)
	{
		super(holder, InventoryType.GRINDSTONE);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		GrindstoneInventoryMock inventory = new GrindstoneInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}
}
