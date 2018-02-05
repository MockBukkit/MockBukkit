package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class PlayerInventoryView extends InventoryViewMock
{
	
	public PlayerInventoryView(HumanEntity player, Inventory top)
	{
		super(player, top, player.getInventory(), top.getType());
	}
	
}
