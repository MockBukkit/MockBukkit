package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class SimpleInventoryViewMock extends InventoryViewMock
{

	public SimpleInventoryViewMock(HumanEntity player, Inventory top, Inventory bottom, InventoryType type)
	{
		super(player, top, bottom, type);
	}

	/**
	 * Creates a very simple mock {@link IntventoryView} with as player, top,
	 * and bottom {@code null}, and as type {@link InventoryType.CHEST}.
	 */
	public SimpleInventoryViewMock()
	{
		this(null, null, null, InventoryType.CHEST);
	}

}
