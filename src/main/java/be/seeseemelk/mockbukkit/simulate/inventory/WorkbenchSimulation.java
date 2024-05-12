package be.seeseemelk.mockbukkit.simulate.inventory;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.inventory.WorkbenchInventoryMock;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class WorkbenchSimulation extends InventorySimulation implements InventoryMatrixSimulation, InventoryResultSimulation
{

	private final @NotNull WorkbenchInventoryMock workbenchInventoryMock;

	public WorkbenchSimulation(@NotNull PlayerMock playerMock, @NotNull WorkbenchInventoryMock workbenchInventoryMock)
	{
		super(playerMock);
		this.workbenchInventoryMock = workbenchInventoryMock;
	}


	@Override
	public InventoryClickEvent simulateMatrixSlotClick(@NotNull ClickType clickType, int slot)
	{
		// slot 0 is the result slot. 1-9 is the matrix
		return simulateInventoryClick(clickType, slot + 1);
	}

	@Override
	public InventoryClickEvent simulateResultSlotClick(@NotNull ClickType clickType)
	{
		return simulateInventoryClick(clickType, 0);
	}

}
