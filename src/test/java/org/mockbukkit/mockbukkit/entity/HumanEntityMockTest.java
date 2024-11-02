package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ChestInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryViewMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.inventory.SimpleInventoryViewMock;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent;

class HumanEntityMockTest
{

	private static final int[] REQUIRED_EXP =
			{
					7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 42, 47, 52, 57, 62, 67, 72, 77, 82, 87, 92, 97, 102,
					107, 112, 121, 130, 139, 148, 157, 166, 175, 184, 193
			};

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
		assertEquals(GameMode.SURVIVAL, human.getGameMode());
	}

	@Test
	void assertGameMode_WrongGameMode_Asserts()
	{
		assertNotEquals(GameMode.CREATIVE, human.getGameMode());
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(PlayerGameModeChangeEvent.class, (e) -> e.getNewGameMode() == GameMode.CREATIVE));
	}

	@Test
	void setGameMode_GameModeNotChanged_DoesntCallsEvent()
	{
		//todo: replace with PluginManagerMock#assertEventNotFired once implemented
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
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(InventoryOpenEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv));
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

		assertThat(server.getPluginManager(), hasFiredFilteredEvent(InventoryOpenEvent.class,
				e -> e.getPlayer() == human && e.getInventory() == inv && e.isCancelled()));
		assertEquals(InventoryType.CRAFTING, human.getOpenInventory().getType());
	}

	@Test
	void openInventory_AlreadyOpened_ClosesPrevious()
	{
		Inventory inv1 = server.createInventory(null, 36);
		Inventory inv2 = server.createInventory(null, 36);

		human.openInventory(inv1);
		human.setItemOnCursor(new ItemStackMock(Material.PUMPKIN));

		human.openInventory(inv2);

		assertTrue(human.getItemOnCursor().getType().isAir());
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(InventoryOpenEvent.class, e -> e.getPlayer() == human && e.getInventory() == inv1));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(InventoryOpenEvent.class, e -> e.getPlayer() == human && e.getInventory() == inv2));
	}

	@Test
	void closeInventory_ClosesInventory()
	{
		Inventory inv = server.createInventory(null, 36);
		human.openInventory(inv);

		human.setItemOnCursor(new ItemStackMock(Material.PUMPKIN));
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

		assertThat(server.getPluginManager(), hasFiredFilteredEvent(InventoryCloseEvent.class, e -> e.getPlayer() == human && e.getInventory() == inv));
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
		human.getInventory().setItemInMainHand(new ItemStackMock(Material.SHIELD));
		human.setBlocking(true);
		assertTrue(human.isBlocking());
	}

	@Test
	void isBlocking_shieldRemovedMainHand_notBlocking()
	{
		human.getInventory().setItemInMainHand(new ItemStackMock(Material.SHIELD));
		human.setBlocking(true);
		human.getInventory().setItemInMainHand(new ItemStackMock(Material.AIR));
		assertFalse(human.isBlocking());
	}

	@Test
	void setBlocking_withShieldOffHand_successful()
	{
		human.getInventory().setItemInOffHand(new ItemStackMock(Material.SHIELD));
		human.setBlocking(true);
		assertTrue(human.isBlocking());
	}

	@Test
	void isBlocking_shieldRemovedOffHand_notBlocking()
	{
		human.getInventory().setItemInOffHand(new ItemStackMock(Material.SHIELD));
		human.setBlocking(true);
		human.getInventory().setItemInOffHand(new ItemStackMock(Material.AIR));
		assertFalse(human.isBlocking());
	}

	@Test
	void getSleepTicks_GivenDefaultValue()
	{
		int actual = human.getSleepTicks();
		assertEquals(0, actual);
	}

	@Test
	void getSleepTicks_GivenPossibleValue()
	{
		human.setSleepTicks(100);
		int actual = human.getSleepTicks();
		assertEquals(100, actual);
	}

	@Test
	void isDeeplySleeping_GivenDefaultValue()
	{
		boolean actual = human.isDeeplySleeping();
		assertFalse(actual);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100})
	void isDeeplySleeping_GivenNonDeepSleepLevelIsSleeping(int sleepTicks)
	{
		human.setSleeping(true);
		human.setSleepTicks(sleepTicks);
		boolean actual = human.isDeeplySleeping();
		assertFalse(actual);
	}

	@ParameterizedTest
	@ValueSource(ints = {101, 110, 120, 130, 140, 150})
	void isDeeplySleeping_GivenDeepSleepLevelAndIsSleeping(int sleepTicks)
	{
		human.setSleeping(true);
		human.setSleepTicks(sleepTicks);
		boolean actual = human.isDeeplySleeping();
		assertTrue(actual);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 101, 110, 120, 130, 140, 150})
	void isDeeplySleeping_GivenIsNotSleeping(int sleepTicks)
	{
		human.setSleeping(false);
		human.setSleepTicks(sleepTicks);
		boolean actual = human.isDeeplySleeping();
		assertFalse(actual);
	}

}
