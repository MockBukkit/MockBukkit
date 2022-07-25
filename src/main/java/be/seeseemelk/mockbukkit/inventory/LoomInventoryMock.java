package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.LoomInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoomInventoryMock extends InventoryMock implements LoomInventory
{

	public LoomInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.LOOM);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		LoomInventoryMock inventory = new LoomInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
