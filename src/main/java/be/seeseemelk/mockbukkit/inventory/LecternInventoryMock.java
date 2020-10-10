package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

/**
 * This {@link InventoryMock} mocks an {@link org.bukkit.block.Lectern} but pretty much behaves like any small chest.
 */
public class LecternInventoryMock extends InventoryMock {
    public LecternInventoryMock(InventoryHolder holder) {
        super(holder, InventoryType.LECTERN);
    }
}
