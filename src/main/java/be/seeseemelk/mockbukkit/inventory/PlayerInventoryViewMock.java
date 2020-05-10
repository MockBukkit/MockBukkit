package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class PlayerInventoryViewMock extends InventoryViewMock
{

	public PlayerInventoryViewMock(HumanEntity player, Inventory top)
	{
		super(player, "Inventory", top, player.getInventory(), top.getType());
	}

}
