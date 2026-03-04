package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of an {@link InventoryView} for players.
 *
 * @see InventoryViewMock
 */
public class PlayerInventoryViewMock extends InventoryViewMock
{

	/**
	 * Constructs a new {@link PlayerInventoryViewMock} for the provided player, with the specified top inventory.
	 *
	 * @param player The player to create the view for.
	 * @param top    The top inventory.
	 */
	public PlayerInventoryViewMock(@NotNull HumanEntity player, @NotNull Inventory top)
	{
		super(player, top, player.getInventory(), top.getType());
	}

}
