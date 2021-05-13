package be.seeseemelk.mockbukkit.inventory;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

class PlayerInventoryViewTest
{
	private ServerMock server;

	@BeforeEach
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_SetsProperties()
	{
		Player player = server.addPlayer();
		Inventory inventory = new SimpleInventoryMock(null, 9, InventoryType.CHEST);

		PlayerInventoryViewMock view = new PlayerInventoryViewMock(player, inventory);
		assertSame(player, view.getPlayer());
		assertSame(player.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
	}

}
