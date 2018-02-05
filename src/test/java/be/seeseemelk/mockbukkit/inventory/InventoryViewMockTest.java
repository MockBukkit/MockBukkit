package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class InventoryViewMockTest
{
	private ServerMock server;
	private InventoryViewMock view;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		view = new SimpleInventoryViewMock();
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}
	
	@Test
	public void constructorEmpty_AllNull()
	{
		assertNull(view.getTopInventory());
		assertNull(view.getBottomInventory());
		assertNull(view.getPlayer());
	}
	
	@Test
	public void constructorParameterised_ValuesSet()
	{
		Player player = server.addPlayer();
		InventoryMock top = new SimpleInventoryMock();
		InventoryMock bottom = new SimpleInventoryMock();
		view = new SimpleInventoryViewMock(player, top, bottom, InventoryType.DROPPER);
		assertSame(player, view.getPlayer());
		assertSame(top, view.getTopInventory());
		assertSame(bottom, view.getBottomInventory());
		assertSame(InventoryType.DROPPER, view.getType());
	}
	
	@Test
	public void getType_NoneSet_Chest()
	{
		assertEquals(InventoryType.CHEST, view.getType());
	}
	
	@Test
	public void getTopInventory_TopInventorySet_SameReturned()
	{
		InventoryMock inventory = new SimpleInventoryMock();
		view.setTopInventory(inventory);
		assertSame(inventory, view.getTopInventory());
	}
	
	@Test
	public void getBottomInventory_BottomInventorySet_SameReturned()
	{
		InventoryMock inventory = new SimpleInventoryMock();
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




























