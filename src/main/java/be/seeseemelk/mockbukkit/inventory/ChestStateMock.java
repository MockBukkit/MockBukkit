package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.block.BlockStateMock;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.MaterialData;

public class ChestStateMock extends BlockStateMock implements Chest {
    ChestInventoryMock inventory = new ChestInventoryMock(this, "Chest", 9*3);
    @Override
    public Inventory getBlockInventory() {
        return inventory;
    }

    public ChestStateMock() {
    }

    public ChestStateMock(MaterialData data) {
        super(data);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
