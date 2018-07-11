package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
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
import be.seeseemelk.mockbukkit.block.BlockMock;
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

	@Test
	public void simulateBlockBreak_Survival_BlockBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SURVIVAL);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertTrue(player.simulateBlockBreak(block));
		server.getPluginManager().assertEventFired(BlockDamageEvent.class);
		server.getPluginManager().assertEventFired(BlockBreakEvent.class);
		block.assertType(Material.AIR);
	}
	
	@Test
	public void simulateBlockBreak_Creative_BlockBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.CREATIVE);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertTrue(player.simulateBlockBreak(block));
		server.getPluginManager().assertEventFired(BlockBreakEvent.class);
		block.assertType(Material.AIR);
	}
	
	@Test
	public void simulateBlockBreak_Spectator_BlockNotBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SPECTATOR);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertFalse(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}
	
	@Test
	public void simulateBlockBreak_Adventure_BlockNotBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.ADVENTURE);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertFalse(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}

	@Test
	public void simulateBlockBreak_BreakCancelled_BlockNotBroken()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockBreak(BlockBreakEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertFalse(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}
	
	@Test
	public void simulateBlockBreak_SurvivalAndDamageCancelled_BlockNotBroken()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockDamage(BlockDamageEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertFalse(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}
	
	@Test
	public void simulateBlockBreak_CreativeAndBreakCancelled_BlockNotBroken()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.CREATIVE);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockBreak(BlockBreakEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertFalse(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}
	
	@Test
	public void simulateBlockBreak_CreativeAndDamageCancelled_BlockBroken()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.CREATIVE);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockDamage(BlockDamageEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertTrue(player.simulateBlockBreak(block));
		block.assertType(Material.AIR);
	}
	
	@Test
	public void simulateBlockDamage_Survival_BlockDamaged()
	{
		player.setGameMode(GameMode.SURVIVAL);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		assertTrue(player.simulateBlockDamage(block));
	}
	
	@Test
	public void simulateBlockDamage_NotSurvival_BlockNotDamaged()
	{
		for (GameMode gm : new GameMode[]{GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR})
		{
			player.setGameMode(gm);
			Block block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
			assertFalse("Block was damaged while in gamemode " + gm.name(), player.simulateBlockDamage(block));
		}
	}
	
	@Test
	public void simulateBlockDamage_NotInstaBreak_NotBroken()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SURVIVAL);
		AtomicBoolean wasBroken = new AtomicBoolean();
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockDamage(BlockDamageEvent event)
			{
				event.setInstaBreak(false);
			}
			
			@EventHandler
			public void onBlockBreak(BlockBreakEvent event)
			{
				wasBroken.set(true);
			}
		}, plugin);
		
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assumeTrue(player.simulateBlockDamage(block));
		assertFalse("BlockBreakEvent was fired", wasBroken.get());
		block.assertType(Material.STONE);
	}
	
	@Test
	public void simulateBlockDamage_InstaBreak_Broken()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SURVIVAL);
		AtomicInteger brokenCount = new AtomicInteger();
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockDamage(BlockDamageEvent event)
			{
				event.setInstaBreak(true);
			}
			
			@EventHandler
			public void onBlockBreak(BlockBreakEvent event)
			{
				brokenCount.incrementAndGet();
			}
		}, plugin);
		
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assumeTrue(player.simulateBlockDamage(block));
		assertEquals("BlockBreakEvent was not fired only once", 1, brokenCount.get());
		block.assertType(Material.AIR);
	}
	
	@Test
	public void simulateBlockBreak_InstaBreak_BreakEventOnlyFiredOnce()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SURVIVAL);
		AtomicInteger brokenCount = new AtomicInteger();
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBlockDamage(BlockDamageEvent event)
			{
				event.setInstaBreak(true);
			}
			
			@EventHandler
			public void onBlockBreak(BlockBreakEvent event)
			{
				brokenCount.incrementAndGet();
			}
		}, plugin);
		
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assumeTrue(player.simulateBlockBreak(block));
		assertEquals("BlockBreakEvent was not fired only once", 1, brokenCount.get());
		block.assertType(Material.AIR);
	}
	
	@Test
	public void getDisplayName_Default_SameAsPlayerUsername()
	{
		assertEquals(player.getName(), player.getDisplayName());
	}
	
	@Test
	public void getDisplayName_NameSet_NameSet()
	{
		player.setDisplayName("Some Display Name");
		assertEquals("Some Display Name", player.getDisplayName());
	}

}
















