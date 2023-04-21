package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class PlayerInventoryViewTest
{

	private ServerMock server;

	@BeforeEach
	void setUp() throws Exception
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_SetsProperties()
	{
		Player player = server.addPlayer();
		Inventory inventory = new InventoryMock(null, 9, InventoryType.CHEST);

		PlayerInventoryViewMock view = new PlayerInventoryViewMock(player, inventory);
		assertSame(player, view.getPlayer());
		assertSame(player.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
	}

}
