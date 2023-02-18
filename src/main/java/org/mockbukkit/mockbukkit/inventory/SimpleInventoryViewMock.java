package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * A basic inventory view.
 *
 * @see InventoryViewMock
 */
public class SimpleInventoryViewMock extends InventoryViewMock
{

	/**
	 * Constructs a new {@link SimpleInventoryViewMock} with the provided parameters, and "Inventory" as the name.
	 *
	 * @param player The player this view is for.
	 * @param top    The top inventory.
	 * @param bottom The bottom inventory.
	 * @param type   The type of the inventory.
	 */
	public SimpleInventoryViewMock(HumanEntity player, Inventory top, Inventory bottom, InventoryType type)
	{
		super(player, "Inventory", top, bottom, type);
	}

	/**
	 * Creates a very simple mock {@code IntventoryView} with as player, top,
	 * and bottom {@code null}, and as type {@code InventoryType.CHEST}.
	 */
	public SimpleInventoryViewMock()
	{
		this(null, null, null, InventoryType.CHEST);
	}

}
