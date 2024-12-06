package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.ItemEntityMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryViewMockTest
{

	private ServerMock server;
	private InventoryViewMock view;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		view = new SimpleInventoryViewMock();
	}

	@AfterEach
	void tearDown()
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

	@Nested
	class SetTitle
	{

		@Test
		void givenSuccess()
		{
			PlayerMock player = server.addPlayer();
			view.setPlayer(player);
			view.setTopInventory(new InventoryMock(player, InventoryType.CHEST));

			assertEquals("Chest", view.getTitle());
			assertEquals("Chest", view.getOriginalTitle());

			view.setTitle("Test");

			assertEquals("Test", view.getTitle());
			assertEquals("Chest", view.getOriginalTitle());
		}

		@Test
		void givenNullTitle()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> view.setTitle(null));
			assertEquals("Title cannot be null", e.getMessage());
		}

		@Test
		void givenViewWithoutPlayerTitle()
		{
			PlayerMock player = server.addPlayer();
			view.setPlayer(null);
			view.setTopInventory(new InventoryMock(player, InventoryType.CHEST));

			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> view.setTitle("This is a test"));
			assertEquals("NPCs are not currently supported for this function", e.getMessage());
		}

		@Test
		void givenViewWithNonCreatableInventory()
		{
			PlayerMock player = server.addPlayer();
			view.setPlayer(player);
			view.setTopInventory(new InventoryMock(player, InventoryType.CRAFTING));

			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> view.setTitle("This is a test"));
			assertEquals("Only creatable inventories can have their title changed", e.getMessage());
		}

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
	void getItemWrongIndex_1()
	{
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);

		assertNull(view.getItem(-1));
	}

	@Test
	void getItemWrongIndex100()
	{
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);

		assertThrows(IndexOutOfBoundsException.class, () -> view.getItem(100));
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

		// Ensure it is copied
		sword.setAmount(2);
		assertNotEquals(sword, chest.getItem(0));
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

		// Ensure it is copied
		sword.setAmount(2);
		assertNotEquals(sword, player.getInventory().getItem(0));
	}

	@Test
	void setItemNegativeSlot_WithNullItem()
	{
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);

		// Verify no items were dropped (since item was null) [ there can be only 1: the player ]
		assertEquals(List.of(player), player.getWorld().getEntities());
	}

	@Test
	void setItemNegativeSlot_WithValidItem()
	{
		ItemStack sword = ItemStack.of(Material.IRON_SWORD);
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);

		view.setItem(-1, sword);

		List<Entity> entities = player.getWorld().getEntities().stream().filter(p -> !(p instanceof Player)).toList();
		assertEquals(1, entities.size());
		assertInstanceOf(ItemEntityMock.class, entities.getFirst());

		assertEquals(sword, ((ItemEntityMock) entities.getFirst()).getItemStack());

		sword.setAmount(2);
		assertNotEquals(sword, ((ItemEntityMock) entities.getFirst()).getItemStack());
	}

	@Test
	void setItemWrongIndex100()
	{
		ItemStack sword = ItemStack.of(Material.IRON_SWORD);
		Player player = server.addPlayer();
		InventoryMock chest = new ChestInventoryMock(null, 9);
		view = new PlayerInventoryViewMock(player, chest);

		assertThrows(IndexOutOfBoundsException.class, () -> view.setItem(100, sword));
	}

}
