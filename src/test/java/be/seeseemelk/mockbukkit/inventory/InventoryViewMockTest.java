package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.bukkit.event.inventory.InventoryType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class InventoryViewMockTest
{
	@SuppressWarnings("unused")
	private ServerMock server;
	private InventoryViewMock view;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		view = new InventoryViewMock();
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getTopInventory_NoneSet_Null()
	{
		assertNull(view.getTopInventory());
	}
	
	@Test
	public void getBottomInventory_NoneSet_Null()
	{
		assertNull(view.getBottomInventory());
	}
	
	@Test
	public void getPlayer_NoneSet_Null()
	{
		assertNull(view.getPlayer());
	}
	
	@Test
	public void getType_NoneSet_Chest()
	{
		assertEquals(InventoryType.CHEST, view.getType());
	}
	
	@Test
	public void getTopInventory_TopInventorySet_SameReturned()
	{
		InventoryMock inventory = new InventoryMock(null, null, 9);
		view.setTopInventory(inventory);
		assertSame(inventory, view.getTopInventory());
	}
	
	@Test
	public void getBottomInventory_BottomInventorySet_SameReturned()
	{
		InventoryMock inventory = new InventoryMock(null, null, 9);
		view.setBottomInventory(inventory);
		assertSame(inventory, view.getBottomInventory());
	}
	
	@Test
	public void getPlayer_PlayerSet_SameReturned()
	{
		PlayerMock player = server.addPlayer();
		view.setPlayer(player);
		assertSame(player, view.getPlayer());
	}
	
	@Test
	public void getType_TypeSet_SameReturned()
	{
		view.setType(InventoryType.CREATIVE);
		assertEquals(InventoryType.CREATIVE, view.getType());
	}

}






























