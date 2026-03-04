package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class PlayerInventoryViewTest
{

	@MockBukkitInject
	private Player player;

	@Test
	void constructor_SetsProperties()
	{
		Inventory inventory = new InventoryMock(null, 9, InventoryType.CHEST);

		PlayerInventoryViewMock view = new PlayerInventoryViewMock(player, inventory);
		assertSame(player, view.getPlayer());
		assertSame(player.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
	}

}
