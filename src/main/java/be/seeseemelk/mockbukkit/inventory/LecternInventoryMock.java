package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.block.Lectern;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.LecternInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This {@link InventoryMock} mocks an {@link org.bukkit.block.Lectern} but pretty much behaves like any small chest.
 */
public class LecternInventoryMock extends InventoryMock implements LecternInventory
{

	private @Nullable Lectern holder;

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

	@Override
	public @Nullable Lectern getHolder()
	{
		return (Lectern) super.getHolder();
	}

}
