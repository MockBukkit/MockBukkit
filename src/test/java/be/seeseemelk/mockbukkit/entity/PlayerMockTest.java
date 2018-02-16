package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;

public class PlayerMockTest
{
	private ServerMock server;
	private UUID uuid;
	private PlayerMock player;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		uuid = UUID.randomUUID();
		player = new PlayerMock("player", uuid);
	}
	
	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getInventory_Default_NotNull()
	{
		assertNotNull(player.getInventory());
	}
	
	@Test
	public void getInventory_Twice_SameInventory()
	{
		assertSame(player.getInventory(), player.getInventory());
	}

	@Test
	public void getName_Default_CorrectName()
	{
		assertEquals("player", player.getName());
	}
	
	@Test
	public void getUniqueId_Default_CorrectUuid()
	{
		assertEquals(uuid, player.getUniqueId());
	}

	@Test
	public void getGameMode_Default_Survival()
	{
		assertEquals(GameMode.SURVIVAL, player.getGameMode());
	}
	
	@Test
	public void setGameMode_GameModeChanged_GameModeSet()
	{
		player.setGameMode(GameMode.CREATIVE);
		assertEquals(GameMode.CREATIVE, player.getGameMode());
	}
	
	@Test
	public void assertGameMode_CorrectGameMode_DoesNotAssert()
	{
		player.assertGameMode(GameMode.SURVIVAL);
	}
	
	@Test(expected = AssertionError.class)
	public void assertGameMode_WrongGameMode_Asserts()
	{
		player.assertGameMode(GameMode.CREATIVE);
	}
	
	@Test
	public void getHealth_Default_EqualsToGetMaxHealth()
	{
		assertEquals(player.getMaxHealth(), player.getHealth(), 0);
	}
	
	@Test
	public void setHealth_SomeValue_HealthSetExactly()
	{
		player.setHealth(15.5);
		assertEquals(15.5, player.getHealth(), 0);
	}
	
	@Test
	public void setHealth_NegativeValue_ClampedAtZero()
	{
		player.setHealth(-10.0);
		assertEquals(0, player.getHealth(), 0);
	}
	
	@Test
	public void setHealh_TooHighValue_ClampedAtMaxHealth()
	{
		player.setHealth(player.getMaxHealth() + 10.0);
		assertEquals(player.getMaxHealth(), player.getHealth(), 0);
	}
	
	@Test
	public void getMaxHealth_Default_20()
	{
		assertEquals(20.0, player.getMaxHealth(), 0);
	}
	
	@Test
	public void setMaxHealth_Decreased_HealthAndMaxHealthSet()
	{
		player.setMaxHealth(10.0);
		assertEquals(10.0, player.getMaxHealth(), 0);
		assertEquals(10.0, player.getHealth(), 0);
	}
	
	@Test
	public void setMaxHealth_Increased_MaxHealthSet()
	{
		player.setMaxHealth(30.0);
		assertEquals(30.0, player.getMaxHealth(), 0);
		assertEquals(20.0, player.getHealth(), 0);
	}
	
	@Test
	public void resetMaxHealth_MaxHealthChanged_ResetsBackTo20()
	{
		player.setMaxHealth(30.0);
		player.setHealth(30.0);
		player.resetMaxHealth();
		assertEquals(20.0, player.getMaxHealth(), 0);
		assertEquals(20.0, player.getHealth(), 0);
	}
	
	@Test
	public void damage_LessThanHealth_DamageTaken()
	{
		double health = player.getHealth();
		player.damage(5.0);
		assertEquals(health - 5.0, player.getHealth(), 0);
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
	}
	
	@Test
	public void damage_MoreThanHealth_ClampedAtZeroAndDeathEvent()
	{
		player.damage(50.0, player);
		assertEquals(0, player.getHealth(), 0);
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
		server.getPluginManager().assertEventFired(PlayerDeathEvent.class);
	}
	
	@Test
	public void damage_ExactlyHealth_ZeroAndDeathEvent()
	{
		player.damage(player.getHealth());
		assertEquals(0, player.getHealth(), 0);
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
		server.getPluginManager().assertEventFired(PlayerDeathEvent.class);
	}
	
	@Test
	public void getAttribute_HealthAttribute_IsMaximumHealth()
	{
		assertEquals(20.0, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue(), 0);
	}
	
	@Test
	public void getOpenInventory_NoneOpened_Null()
	{
		InventoryView view = player.getOpenInventory();
		assertNotNull(player.getOpenInventory());
		assertEquals(InventoryType.CRAFTING, view.getType());
	}
	
	@Test
	public void getOpenInventory_InventorySet_InventorySet()
	{
		InventoryViewMock inventory = new SimpleInventoryViewMock();
		player.openInventory(inventory);
		assertSame(inventory, player.getOpenInventory());
	}
	
	@Test
	public void openInventory_NothingSet_InventoryViewSet()
	{
		InventoryMock inventory = new ChestInventoryMock(null, "Inventory", 9);
		InventoryView view = player.openInventory(inventory);
		assertNotNull(view);
		assertSame(player.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
		assertSame(player.getOpenInventory(), view);
	}
	
	@Test
	public void closeInventory_NoneInventory_CraftingView()
	{
		InventoryView view = player.getOpenInventory();
		assertNotNull(view);
		assertEquals(InventoryType.CRAFTING, view.getType());
	}
	
	@Test
	public void performCommand_PerformsCommand()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = true;
		assertTrue(player.performCommand("mockcommand argA argB"));
		assertEquals("argA", plugin.commandArguments[0]);
		assertEquals("argB", plugin.commandArguments[1]);
		assertSame(player, plugin.commandSender);
	}
	
}






















