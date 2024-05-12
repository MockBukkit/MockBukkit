package be.seeseemelk.mockbukkit.simulate.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface Resultable
{

	default InventoryClickEvent simulateResultSlotClick()
	{
		return simulateResultSlotClick(ClickType.LEFT);
	}

	public InventoryClickEvent simulateResultSlotClick(@NotNull ClickType clickType);

}
