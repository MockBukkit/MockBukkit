package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.InventoryView;
import org.junit.After;
import org.junit.Assert;
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
	// Taken from https://minecraft.gamepedia.com/Experience#Leveling_up
	private static int[] expRequired = new int[]{7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 42, 47, 52, 57, 62, 67, 72, 77, 82, 87, 92, 97, 102, 107, 112, 121, 130, 139, 148, 157, 166, 175, 184, 193};
	private ServerMock server;
	private UUID uuid;
	private PlayerMock player;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		uuid = UUID.randomUUID();
		player = new PlayerMock(server, "player", uuid);
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
		InventoryMock inventory = new ChestInventoryMock(null, 9);
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

	@Test
	public void chat_AnyMessage_AsyncEventFired()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(plugin, plugin);
		player.chat("A message");
		try
		{
			plugin.barrier.await(3, TimeUnit.SECONDS);
		}
		catch (InterruptedException | BrokenBarrierException e)
		{}
		catch (TimeoutException e)
		{
			fail("Async event was not fired");
		}
		assertTrue(plugin.asyncEventExecuted);
	}

	@Test
	public void getLevel_Default_EqualsZero()
	{
		assertEquals(0, player.getLevel());
	}

	@Test
	public void getExp_Default_EqualsZero()
	{
		assertEquals(0, player.getExp(), 0);
	}

	@Test
	public void getTotalExperience_Default_EqualsZero()
	{
		assertEquals(0, player.getTotalExperience());
	}

	@Test
	public void setLevel_SomeValue_LevelSetExactly()
	{
		player.setLevel(15);
		assertEquals(15, player.getLevel());
	}

	@Test
	public void setExp_SomeValue_LevelSetExactly()
	{
		player.setExp(0.5F);
		assertEquals(0.5, player.getExp(), 0.5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setExp_GreaterThanOne_ExceptionThrown()
	{
		player.setExp(1.1F);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setExp_LessThanZero_ExceptionThrown()
	{
		player.setExp(-1.0F);
	}

	@Test
	public void setTotalExperience_SomeValue_TotalExpSetExactly()
	{
		player.setTotalExperience(100);
		assertEquals(100, player.getTotalExperience());
	}

	@Test
	public void setTotalExperience_NegativeValue_ClampedAtZero()
	{
		player.setTotalExperience(-200);
		assertEquals(0, player.getTotalExperience(), 0);
	}

	@Test
	public void getExpToLevel_CorrectExp()
	{
		for (int i = 0; i < expRequired.length; i++)
		{
			player.setLevel(i);
			assertEquals(expRequired[i], player.getExpToLevel());
		}
	}

	@Test
	public void giveExpLevel_Negative_ClampedAtZero()
	{
		player.setExp(0.5F);
		player.setLevel(1);
		player.giveExpLevels(-100);
		assertEquals(0, player.getExp(), 0);
		assertEquals(0, player.getLevel());
	}

	@Test
	public void giveExp_SomeExp_IncreaseLevel()
	{
		for (int i = 0; i < expRequired.length; i++)
		{
			assertEquals(0, player.getExp(), 0);
			player.giveExp(expRequired[i]);
			assertEquals(i+1, player.getLevel());
		}
	}

	@Test
	public void giveExp_SomeExp_IncreaseMultipleLevels()
	{
		player.giveExp(expRequired[0] + expRequired[1] + expRequired[2]);
		assertEquals(3, player.getLevel());
		assertEquals(expRequired[0] + expRequired[1] + expRequired[2], player.getTotalExperience(), 0);
	}

	@Test
	public void giveExp_SomeExp_DecreaseLevel()
	{
		player.giveExp(expRequired[0] + expRequired[1]);
		player.giveExp(-expRequired[1]);
		assertEquals(1, player.getLevel());
		assertEquals(expRequired[0], player.getTotalExperience(), 0);
	}

	@Test
	public void giveExp_SomeExp_DecreaseMultipleLevels()
	{
		player.giveExp(expRequired[0] + expRequired[1]);
		player.giveExp(-(expRequired[0] + expRequired[1]));
		assertEquals(0, player.getLevel());
		assertEquals(0.0, player.getTotalExperience(), 0);
	}

	@Test
	public void giveExp_SomeLevelChange_LevelEventFired()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		AtomicInteger levelCount = new AtomicInteger();
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onLevelChangeEvent(PlayerLevelChangeEvent event)
			{
				levelCount.incrementAndGet();
			}

			@EventHandler
			public void onExpChangeEvent(PlayerExpChangeEvent event)
			{
				fail("PlayerExpChangeEvent should not be called");
			}
		}, plugin);
		player.giveExp(expRequired[0]);
		assertEquals(1, levelCount.get());
	}

	@Test
	public void giveExp_NoExpChange_NoEventFired()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onLevelChangeEvent(PlayerLevelChangeEvent event)
			{
				fail("PlayerLevelChangeEvent should not be called");
			}

			@EventHandler
			public void onExpChangeEvent(PlayerExpChangeEvent event)
			{
				fail("PlayerExpChangeEvent should not be called");
			}
		}, plugin);
		player.giveExp(0);
	}

	@Test
	public void getFood_LevelDefault20(){
		int foodLevel = player.getFoodLevel();
		Assert.assertEquals(foodLevel, 20);
	}

	@Test
	public void getFood_LevelChange(){
		player.setFoodLevel(10);
		Assert.assertEquals(player.getFoodLevel(), 10);
	}

	@Test
	public void getPlayer_SneakingDefault(){
		boolean sneaking = player.isSneaking();
		assertFalse(sneaking);
	}

	@Test
	public void getPlayer_SneakingChange(){
		player.setSneaking(true);
		assertTrue(player.isSneaking());
	}
	
	@Test
	public void getPlayer_SneakingEyeHeight() {
		player.setSneaking(true);
		assertNotEquals(player.getEyeHeight(), player.getEyeHeight(true));
	}
	
	@Test
	public void getPlayer_EyeLocationDiffers(){
		assertNotEquals(player.getEyeLocation(), player.getLocation());
	}

	@Test
	public void dispatchPlayer_PlayerJoinEventFired() {
		PlayerMock player = server.addPlayer();
		PluginManagerMock pluginManager = server.getPluginManager();
		pluginManager.assertEventFired(event -> event instanceof PlayerJoinEvent);
	}

}
