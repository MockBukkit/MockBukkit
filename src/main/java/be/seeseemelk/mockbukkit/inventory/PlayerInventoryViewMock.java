package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class PlayerInventoryViewMock extends InventoryViewMock
{

	public PlayerInventoryViewMock(@NotNull HumanEntity player, @NotNull Inventory top)
	{
		super(player, "Inventory", top, player.getInventory(), top.getType());
	}

}
