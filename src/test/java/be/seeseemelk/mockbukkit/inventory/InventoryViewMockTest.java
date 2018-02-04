package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

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
	public void getTopInventory_TopInventorySet_SameReturned()
	{
		InventoryMock inventory = new InventoryMock(null, null, 9);
		view.setTopInventory(inventory);
		assertSame(inventory, view.getTopInventory());
	}

}
