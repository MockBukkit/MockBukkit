package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class InventoryViewMockTest
{

	private ServerMock server;
	private InventoryViewMock view;

	@BeforeEach
	void setUp() throws Exception
	{
		server = MockBukkit.mock();
		view = new SimpleInventoryViewMock();
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void constructorEmpty_AllNull()
	{
		assertNull(view.getTopInventory());
		assertNull(view.getBottomInventory());
		assertNull(view.getPlayer());
	}

	@Test
	void constructorParameterised_ValuesSet()
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
	void getType_NoneSet_Chest()
	{
		assertEquals(InventoryType.CHEST, view.getType());
	}

	@Test
	void getTopInventory_TopInventorySet_SameReturned()
	{
		InventoryMock inventory = new SimpleInventoryMock();
		view.setTopInventory(inventory);
		assertSame(inventory, view.getTopInventory());
	}

	@Test
	void getBottomInventory_BottomInventorySet_SameReturned()
	{
		InventoryMock inventory = new SimpleInventoryMock();
		view.setBottomInventory(inventory);
		assertSame(inventory, view.getBottomInventory());
	}

	@Test
	void getPlayer_PlayerSet_SameReturned()
	{
		PlayerMock player = server.addPlayer();
		view.setPlayer(player);
		assertSame(player, view.getPlayer());
	}

	@Test
	void getType_TypeSet_SameReturned()
	{
		view.setType(InventoryType.CREATIVE);
		assertEquals(InventoryType.CREATIVE, view.getType());
	}

	@Test
	void getOriginalTitle()
	{
		view.setTitle("Test");
		view.setTitle("Foobar");
		assertEquals("Inventory", view.getOriginalTitle());
	}

	@Test
	void setTitle()
	{
		view.setTitle("Test");
		assertEquals("Test", view.getTitle());
	}
}
