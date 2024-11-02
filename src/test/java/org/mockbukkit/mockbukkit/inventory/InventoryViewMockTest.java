package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
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
		InventoryMock top = new InventoryMock(null, 9, InventoryType.CHEST);
		InventoryMock bottom = new InventoryMock(null, 9, InventoryType.CHEST);
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
		InventoryMock inventory = new InventoryMock(null, 9, InventoryType.CHEST);
		view.setTopInventory(inventory);
		assertSame(inventory, view.getTopInventory());
	}

	@Test
	void getBottomInventory_BottomInventorySet_SameReturned()
	{
		InventoryMock inventory = new InventoryMock(null, 9, InventoryType.CHEST);
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

	@Test
	void getItemFromTopInventory()
	{
		ItemStack sword = ItemStack.of(Material.IRON_SWORD);
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		chest.setItem(0, sword);
		view = new PlayerInventoryViewMock(player, chest);

		assertEquals(sword, view.getItem(0));
	}

	@Test
	void getItemFromBottomInventory()
	{
		ItemStack sword = ItemStack.of(Material.IRON_SWORD);
		Player player = server.addPlayer();
		player.getInventory().setItem(0, sword);
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);

		assertEquals(sword, view.getItem(9));
	}

	@Test
	void setItemInTopInventory()
	{
		ItemStack sword = ItemStack.of(Material.IRON_SWORD);
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);
		view.setItem(0, sword);

		assertEquals(sword, chest.getItem(0));
	}

	@Test
	void setItemInBottomInventory()
	{
		ItemStack sword = ItemStack.of(Material.IRON_SWORD);
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);
		view.setItem(9, sword);

		assertEquals(sword, player.getInventory().getItem(0));
	}
}
