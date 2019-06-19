package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.*;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

public class PlayerInventoryViewTest
{
	private ServerMock server;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}
	
	@Test
	public void constructor_SetsProperties()
	{
		Player player = server.addPlayer();
		Inventory inventory = new SimpleInventoryMock(null, null, 9, InventoryType.CHEST);
		
		PlayerInventoryViewMock view = new PlayerInventoryViewMock(player, inventory);
		assertSame(player, view.getPlayer());
		assertSame(player.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
	}
	
}
