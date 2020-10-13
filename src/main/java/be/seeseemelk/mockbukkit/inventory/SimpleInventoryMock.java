package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class SimpleInventoryMock extends InventoryMock
{

    public SimpleInventoryMock(InventoryHolder holder, int size, InventoryType type)
    {
        super(holder, size, type);
    }

    /**
     * Creates a very simple inventory with no holder, as name
     * {@code Inventory}, a size of 9, and an inventory type of
     * {@code InventoryType.CHEST}.
     */
    public SimpleInventoryMock()
    {
        super(null, 9, InventoryType.CHEST);
    }

    /**
     * Creates a new inventory with size inherited from {@linkplain InventoryType#getDefaultSize()}.
     */
    public SimpleInventoryMock(InventoryHolder holder, InventoryType type)
    {
        super(holder, type);
    }
}
