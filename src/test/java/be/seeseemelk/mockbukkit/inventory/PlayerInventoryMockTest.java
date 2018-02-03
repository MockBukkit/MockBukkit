package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class PlayerInventoryMockTest
{
	private ServerMock server;
	private InventoryMock inventory;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		inventory = new PlayerInventoryMock(null, "Inventory");
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getSize_Default_40()
	{
		assertEquals(40, inventory.getSize());
	}
	
	@Test
	public void getHolder_HolderSet_GetsHolder()
	{
		PlayerMock player = server.addPlayer();
		PlayerInventoryMock inventory = new PlayerInventoryMock(player, "");
		assertSame(player, inventory.getHolder());
	}

}
