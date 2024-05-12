package be.seeseemelk.mockbukkit.simulate.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface Matrixable
{

	default InventoryClickEvent simulateMatrixSlotClick(int slot)
	{
		return simulateMatrixSlotClick(ClickType.LEFT, slot);
	}

	public InventoryClickEvent simulateMatrixSlotClick(@NotNull ClickType clickType, int slot);

}
