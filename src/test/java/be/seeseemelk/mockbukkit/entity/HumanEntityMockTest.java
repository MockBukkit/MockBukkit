package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HumanEntityMockTest
{

	private static final int[] REQUIRED_EXP =
	{ 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 42, 47, 52, 57, 62, 67, 72, 77, 82, 87, 92, 97, 102,
			107, 112, 121, 130, 139, 148, 157, 166, 175, 184, 193 };

	private ServerMock server;
	private HumanEntityMock human;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		human = server.addPlayer();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void assertGameMode_CorrectGameMode_DoesNotAssert()
	{
		human.assertGameMode(GameMode.SURVIVAL);
	}

	@Test
	void assertGameMode_WrongGameMode_Asserts()
	{
		assertThrows(AssertionError.class, () -> human.assertGameMode(GameMode.CREATIVE));
	}

	@Test
	void getGameMode_Default_Survival()
	{
		assertEquals(GameMode.SURVIVAL, human.getGameMode());
	}

	@Test
	void setGameMode_GameModeChanged_GameModeSet()
	{
		human.setGameMode(GameMode.CREATIVE);
		assertEquals(GameMode.CREATIVE, human.getGameMode());
	}

	@Test
	void setGameMode_GameModeChanged_CallsEvent()
	{
		human.setGameMode(GameMode.CREATIVE);
		server.getPluginManager().assertEventFired(PlayerGameModeChangeEvent.class,
				(e) -> e.getNewGameMode() == GameMode.CREATIVE);
	}

	@Test
	void setGameMode_GameModeNotChanged_DoesntCallsEvent()
	{
		// todo: replace with PluginManagerMock#assertEventNotFired once implemented
		AtomicBoolean bool = new AtomicBoolean(false);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerGameModeChange(PlayerGameModeChangeEvent event)
			{
				bool.set(true);
			}
		}, MockBukkit.createMockPlugin());

		human.setGameMode(GameMode.SURVIVAL);

		assertFalse(bool.get());
	}

	@Test
	void getExpToLevel_CorrectExp()
	{
		for (int i = 0; i < REQUIRED_EXP.length; i++)
		{
			((Player) human).setLevel(i);
			assertEquals(REQUIRED_EXP[i], human.getExpToLevel());
		}
	}

	@Test
	void testSaturation()
	{
		// Default level
		assertEquals(5.0F, human.getSaturation(), 0.1F);

		human.setFoodLevel(20);
		human.setSaturation(8);
		assertEquals(8.0F, human.getSaturation(), 0.1F);

		// Testing the constraint
		human.setFoodLevel(20);
		human.setSaturation(10000);
		assertEquals(20.0F, human.getSaturation(), 0.1F);
	}

	@Test
	void getFood_LevelDefault20()
	{
		int foodLevel = human.getFoodLevel();
		assertEquals(20, foodLevel);
	}

	@Test
	void getFood_LevelChange()
	{
		human.setFoodLevel(10);
		assertEquals(10, human.getFoodLevel());
	}

	@Test
	void isSleeping()
	{
		human.setSleeping(false);
		assertFalse(human.isSleeping());
		human.setSleeping(true);
		assertTrue(human.isSleeping());
	}

	@Test
	void getOpenInventory_NoneOpened_Null()
	{
		InventoryView view = human.getOpenInventory();
		assertNotNull(view);
		assertEquals(InventoryType.CRAFTING, view.getType());
	}

	@Test
	void getOpenInventory_InventorySet_InventorySet()
	{
		InventoryViewMock inventory = new SimpleInventoryViewMock();
		human.openInventory(inventory);
		assertSame(inventory, human.getOpenInventory());
	}

	@Test
	void openInventory_NothingSet_InventoryViewSet()
	{
		InventoryMock inventory = new ChestInventoryMock(null, 9);
		InventoryView view = human.openInventory(inventory);
		assertNotNull(view);
		assertSame(human.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
		assertSame(human.getOpenInventory(), view);
	}

	@Test
	void closeInventory_NoneInventory_CraftingView()
	{
		InventoryView view = human.getOpenInventory();
		assertNotNull(view);
		assertEquals(InventoryType.CRAFTING, view.getType());
	}

	@Test
	void openInventory_OpenInventoryEvent_Fired()
	{
		Inventory inv = server.createInventory(null, 36);
		human.openInventory(inv);
		server.getPluginManager().assertEventFired(InventoryOpenEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv);
	}

	@Test
	void openInventory_OpenInventoryEvent_Cancelled()
	{
		Inventory inv = server.createInventory(null, 36);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onEvent(InventoryOpenEvent e)
			{
				e.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		human.openInventory(inv);

		server.getPluginManager().assertEventFired(InventoryOpenEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv && e.isCancelled());
		assertEquals(InventoryType.CRAFTING, human.getOpenInventory().getType());
	}

	@Test
	void openInventory_AlreadyOpened_ClosesPrevious()
	{
		Inventory inv1 = server.createInventory(null, 36);
		Inventory inv2 = server.createInventory(null, 36);

		human.openInventory(inv1);
		human.setItemOnCursor(new ItemStack(Material.PUMPKIN));

		human.openInventory(inv2);

		assertTrue(human.getItemOnCursor().getType().isAir());
		server.getPluginManager().assertEventFired(InventoryCloseEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv1);
		server.getPluginManager().assertEventFired(InventoryOpenEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv2);
	}

	@Test
	void closeInventory_ClosesInventory()
	{
		Inventory inv = server.createInventory(null, 36);
		human.openInventory(inv);

		human.setItemOnCursor(new ItemStack(Material.PUMPKIN));
		human.closeInventory();

		assertTrue(human.getItemOnCursor().getType().isAir());
		assertEquals(InventoryType.CRAFTING, human.getOpenInventory().getType());
	}

	@Test
	void closeInventory_CloseInventoryEvent_Fired()
	{
		Inventory inv = server.createInventory(null, 36);
		human.openInventory(inv);

		human.closeInventory();

		server.getPluginManager().assertEventFired(InventoryCloseEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv);
	}

	@Test
	void setBlocking_noShield_notSuccessful()
	{
		human.setBlocking(true);
		assertFalse(human.isBlocking());
	}

	@Test
	void setBlocking_withShieldMainHand_successful()
	{
		human.getInventory().setItemInMainHand(new ItemStack(Material.SHIELD));
		human.setBlocking(true);
		assertTrue(human.isBlocking());
	}

	@Test
	void isBlocking_shieldRemovedMainHand_notBlocking()
	{
		human.getInventory().setItemInMainHand(new ItemStack(Material.SHIELD));
		human.setBlocking(true);
		human.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
		assertFalse(human.isBlocking());
	}

	@Test
	void setBlocking_withShieldOffHand_successful()
	{
		human.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
		human.setBlocking(true);
		assertTrue(human.isBlocking());
	}

	@Test
	void isBlocking_shieldRemovedOffHand_notBlocking()
	{
		human.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
		human.setBlocking(true);
		human.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
		assertFalse(human.isBlocking());
	}

}
