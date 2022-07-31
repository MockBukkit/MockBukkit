package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.StonecutterInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StonecutterInventoryMock extends InventoryMock implements StonecutterInventory
{

	public StonecutterInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.STONECUTTER);
	}

	@Override
	public @NotNull InventoryMock getSnapshot()
	{
		StonecutterInventoryMock inventory = new StonecutterInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}

}
