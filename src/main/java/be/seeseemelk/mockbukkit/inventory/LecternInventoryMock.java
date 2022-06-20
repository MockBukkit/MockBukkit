package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * This {@link InventoryMock} mocks an {@link org.bukkit.block.Lectern} but pretty much behaves like any small chest.
 */
public class LecternInventoryMock extends InventoryMock
{
	public LecternInventoryMock(InventoryHolder holder)
	{
		super(holder, InventoryType.LECTERN);
	}

	@Override
	@NotNull
	public Inventory getSnapshot()
	{
		Inventory inventory = new LecternInventoryMock(getHolder());
		inventory.setContents(getContents());
		return inventory;
	}
}
