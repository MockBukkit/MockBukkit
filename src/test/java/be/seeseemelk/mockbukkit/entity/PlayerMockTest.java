package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.inventory.ChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class PlayerMockTest
{
	// Taken from https://minecraft.gamepedia.com/Experience#Leveling_up
	private static int[] expRequired =
	{
		7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 42, 47, 52, 57, 62, 67, 72, 77, 82, 87, 92, 97, 102,
		107, 112, 121, 130, 139, 148, 157, 166, 175, 184, 193
	};
	private ServerMock server;
	private UUID uuid;
	private PlayerMock player;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock(new ServerMock()
		{

			private long ticks = 0;

			@Override
			protected long getCurrentServerTime()
			{
				/*
				 *  This will force the current server time to always be different to
				 *  any prior invocations, this is much more elegant than simply doing
				 *  Thread.sleep!
				 */
				ticks++;
				return super.getCurrentServerTime() + ticks;
			}
		});

		uuid = UUID.randomUUID();
		player = new PlayerMock(server, "player", uuid);
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getInventory_Default_NotNull()
	{
		assertNotNull(player.getInventory());
	}

	@Test
	void testEnderChest()
	{
		assertTrue(player.getEnderChest() instanceof EnderChestInventoryMock);
	}

	@Test
	void getInventory_Twice_SameInventory()
	{
		assertSame(player.getInventory(), player.getInventory());
	}

	@Test
	void getName_Default_CorrectName()
	{
		assertEquals("player", player.getName());
	}

	@Test
	void getUniqueId_Default_CorrectUuid()
	{
		assertEquals(uuid, player.getUniqueId());
	}

	@Test
	void getGameMode_Default_Survival()
	{
		assertEquals(GameMode.SURVIVAL, player.getGameMode());
	}

	@Test
	void setGameMode_GameModeChanged_GameModeSet()
	{
		player.setGameMode(GameMode.CREATIVE);
		assertEquals(GameMode.CREATIVE, player.getGameMode());
	}

	@Test
	void assertGameMode_CorrectGameMode_DoesNotAssert()
	{
		player.assertGameMode(GameMode.SURVIVAL);
	}

	@Test
	void assertGameMode_WrongGameMode_Asserts()
	{
		assertThrows(AssertionError.class, () -> player.assertGameMode(GameMode.CREATIVE));
	}

	@Test
	void getHealth_Default_EqualsToGetMaxHealth()
	{
		assertEquals(player.getMaxHealth(), player.getHealth(), 0);
	}

	@Test
	void setHealth_SomeValue_HealthSetExactly()
	{
		player.setHealth(15.5);
		assertEquals(15.5, player.getHealth(), 0);
	}

	@Test
	void setHealth_NegativeValue_ClampedAtZero()
	{
		player.setHealth(-10.0);
		assertEquals(0, player.getHealth(), 0);
	}

	@Test
	void setHealh_TooHighValue_ClampedAtMaxHealth()
	{
		player.setHealth(player.getMaxHealth() + 10.0);
		assertEquals(player.getMaxHealth(), player.getHealth(), 0);
	}

	@Test
	void getMaxHealth_Default_20()
	{
		assertEquals(20.0, player.getMaxHealth(), 0);
	}

	@Test
	void setMaxHealth_Decreased_HealthAndMaxHealthSet()
	{
		player.setMaxHealth(10.0);
		assertEquals(10.0, player.getMaxHealth(), 0);
		assertEquals(10.0, player.getHealth(), 0);
	}

	@Test
	void setMaxHealth_Increased_MaxHealthSet()
	{
		player.setMaxHealth(30.0);
		assertEquals(30.0, player.getMaxHealth(), 0);
		assertEquals(20.0, player.getHealth(), 0);
	}

	@Test
	void resetMaxHealth_MaxHealthChanged_ResetsBackTo20()
	{
		player.setMaxHealth(30.0);
		player.setHealth(30.0);
		player.resetMaxHealth();
		assertEquals(20.0, player.getMaxHealth(), 0);
		assertEquals(20.0, player.getHealth(), 0);
	}

	@Test
	void damage_LessThanHealth_DamageTaken()
	{
		double health = player.getHealth();
		player.damage(5.0);
		assertEquals(health - 5.0, player.getHealth(), 0);
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
	}

	@Test
	void damage_MoreThanHealth_ClampedAtZeroAndDeathEvent()
	{
		player.damage(50.0, player);
		assertEquals(0, player.getHealth(), 0);
		assertTrue(player.isDead());
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
		server.getPluginManager().assertEventFired(PlayerDeathEvent.class);
	}

	@Test
	void damage_ExactlyHealth_ZeroAndDeathEvent()
	{
		player.damage(player.getHealth());
		assertEquals(0, player.getHealth(), 0);
		assertTrue(player.isDead());
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
		server.getPluginManager().assertEventFired(PlayerDeathEvent.class);
	}

	@Test
	void getAttribute_HealthAttribute_IsMaximumHealth()
	{
		assertEquals(20.0, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue(), 0);
	}

	@Test
	void getOpenInventory_NoneOpened_Null()
	{
		InventoryView view = player.getOpenInventory();
		assertNotNull(player.getOpenInventory());
		assertEquals(InventoryType.CRAFTING, view.getType());
	}

	@Test
	void getOpenInventory_InventorySet_InventorySet()
	{
		InventoryViewMock inventory = new SimpleInventoryViewMock();
		player.openInventory(inventory);
		assertSame(inventory, player.getOpenInventory());
	}

	@Test
	void openInventory_NothingSet_InventoryViewSet()
	{
		InventoryMock inventory = new ChestInventoryMock(null, 9);
		InventoryView view = player.openInventory(inventory);
		assertNotNull(view);
		assertSame(player.getInventory(), view.getBottomInventory());
		assertSame(inventory, view.getTopInventory());
		assertSame(player.getOpenInventory(), view);
	}

	@Test
	void closeInventory_NoneInventory_CraftingView()
	{
		InventoryView view = player.getOpenInventory();
		assertNotNull(view);
		assertEquals(InventoryType.CRAFTING, view.getType());
	}

	@Test
	void performCommand_PerformsCommand()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		plugin.commandReturns = true;
		assertTrue(player.performCommand("mockcommand argA argB"));
		assertEquals("argA", plugin.commandArguments[0]);
		assertEquals("argB", plugin.commandArguments[1]);
		assertSame(player, plugin.commandSender);
	}

	@Test
	void simulateBlockBreak_Survival_BlockBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SURVIVAL);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		BlockBreakEvent event = player.simulateBlockBreak(block);
		assertNotNull(event);
		assertFalse(event.isCancelled());
		server.getPluginManager().assertEventFired(BlockDamageEvent.class);
		server.getPluginManager().assertEventFired(BlockBreakEvent.class);
		block.assertType(Material.AIR);
	}

	@Test
	void simulateBlockBreak_Creative_BlockBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.CREATIVE);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		BlockBreakEvent event = player.simulateBlockBreak(block);
		assertNotNull(event);
		assertFalse(event.isCancelled());
		server.getPluginManager().assertEventFired(BlockBreakEvent.class);
		block.assertType(Material.AIR);
	}

	@Test
	void simulateBlockBreak_Spectator_BlockNotBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SPECTATOR);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertNull(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}

	@Test
	void simulateBlockBreak_Adventure_BlockNotBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.ADVENTURE);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		assertNull(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}

	@Test
	void simulateBlockBreak_BreakCancelled_BlockNotBroken()
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
		BlockBreakEvent event = player.simulateBlockBreak(block);
		assertNotNull(event);
		assertTrue(event.isCancelled());
		block.assertType(Material.STONE);
	}

	@Test
	void simulateBlockBreak_SurvivalAndDamageCancelled_BlockNotBroken()
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
		assertNull(player.simulateBlockBreak(block));
		block.assertType(Material.STONE);
	}

	@Test
	void simulateBlockBreak_CreativeAndBreakCancelled_BlockNotBroken()
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
		BlockBreakEvent event = player.simulateBlockBreak(block);
		assertNotNull(event);
		assertTrue(event.isCancelled());
		block.assertType(Material.STONE);
	}

	@Test
	void simulateBlockBreak_CreativeAndDamageCancelled_BlockBroken()
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
		BlockBreakEvent event = player.simulateBlockBreak(block);
		assertNotNull(event);
		assertFalse(event.isCancelled());
		block.assertType(Material.AIR);
	}

	@Test
	void simulateBlockDamage_Survival_BlockDamaged()
	{
		player.setGameMode(GameMode.SURVIVAL);
		BlockMock block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		BlockDamageEvent event = player.simulateBlockDamage(block);
		assertNotNull(event);
		assertFalse(event.isCancelled());
	}

	@ParameterizedTest
	@EnumSource(value = GameMode.class, mode = EnumSource.Mode.EXCLUDE, names = {"SURVIVAL"})
	void simulateBlockDamage_NotSurvival_BlockNotDamaged(GameMode nonSurvivalGameMode)
	{
		player.setGameMode(nonSurvivalGameMode);
		Block block = server.addSimpleWorld("world").getBlockAt(0, 0, 0);
		assertNull(player.simulateBlockDamage(block), "Block was damaged while in gamemode " + nonSurvivalGameMode.name());
	}

	@Test
	void simulateBlockDamage_NotInstaBreak_NotBroken()
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

		BlockDamageEvent event = player.simulateBlockDamage(block);
		assertNotNull(event);
		assumeFalse(event.isCancelled());

		assertFalse(wasBroken.get(), "BlockBreakEvent was fired");
		block.assertType(Material.STONE);
	}

	@Test
	void simulateBlockDamage_InstaBreak_Broken()
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
		BlockDamageEvent event = player.simulateBlockDamage(block);
		assertNotNull(event);
		assumeFalse(event.isCancelled());

		assertEquals(1, brokenCount.get(), "BlockBreakEvent was not fired only once");
		block.assertType(Material.AIR);
	}

	@Test
	void simulateBlockBreak_InstaBreak_BreakEventOnlyFiredOnce()
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
		BlockBreakEvent event = player.simulateBlockBreak(block);
		assertNotNull(event);
		assumeFalse(event.isCancelled());
		assertEquals(1, brokenCount.get(), "BlockBreakEvent was not fired only once");
		block.assertType(Material.AIR);
	}

	@Test
	void getDisplayName_Default_SameAsPlayerUsername()
	{
		assertEquals(player.getName(), player.getDisplayName());
	}

	@Test
	void getDisplayName_NameSet_NameSet()
	{
		player.setDisplayName("Some Display Name");
		assertEquals("Some Display Name", player.getDisplayName());
	}

	@Test
	void getPlayerListName_Default_SameAsPlayerUsername()
	{
		assertEquals(player.getName(), player.getPlayerListName());
	}

	@Test
	void getPlayerListName_NameSet_NameSet()
	{
		player.setPlayerListName("Some Name");
		assertEquals("Some Name", player.getPlayerListName());
	}

	@Test
	void chat_AnyMessage_AsyncEventFired()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(plugin, plugin);
		player.chat("A message");
		try
		{
			plugin.barrier.await(3, TimeUnit.SECONDS);
		}
		catch (InterruptedException | BrokenBarrierException e)
		{
		}
		catch (TimeoutException e)
		{
			fail("Async event was not fired");
		}
		assertTrue(plugin.asyncEventExecuted);
	}

	@Test
	void getLevel_Default_EqualsZero()
	{
		assertEquals(0, player.getLevel());
	}

	@Test
	void getExp_Default_EqualsZero()
	{
		assertEquals(0, player.getExp(), 0);
	}

	@Test
	void getTotalExperience_Default_EqualsZero()
	{
		assertEquals(0, player.getTotalExperience());
	}

	@Test
	void setLevel_SomeValue_LevelSetExactly()
	{
		player.setLevel(15);
		assertEquals(15, player.getLevel());
	}

	@Test
	void setExp_SomeValue_LevelSetExactly()
	{
		player.setExp(0.5F);
		assertEquals(0.5, player.getExp(), 0.5);
	}

	@Test
	void setExp_GreaterThanOne_ExceptionThrown()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setExp(1.1F));
	}

	@Test
	void setExp_LessThanZero_ExceptionThrown()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setExp(-1.0F));
	}

	@Test
	void setTotalExperience_SomeValue_TotalExpSetExactly()
	{
		player.setTotalExperience(100);
		assertEquals(100, player.getTotalExperience());
	}

	@Test
	void setTotalExperience_NegativeValue_ClampedAtZero()
	{
		player.setTotalExperience(-200);
		assertEquals(0, player.getTotalExperience(), 0);
	}

	@Test
	void getExpToLevel_CorrectExp()
	{
		for (int i = 0; i < expRequired.length; i++)
		{
			player.setLevel(i);
			assertEquals(expRequired[i], player.getExpToLevel());
		}
	}

	@Test
	void giveExpLevel_Negative_ClampedAtZero()
	{
		player.setExp(0.5F);
		player.setLevel(1);
		player.giveExpLevels(-100);
		assertEquals(0, player.getExp(), 0);
		assertEquals(0, player.getLevel());
	}

	@Test
	void giveExp_SomeExp_IncreaseLevel()
	{
		for (int i = 0; i < expRequired.length; i++)
		{
			assertEquals(0, player.getExp(), 0);
			player.giveExp(expRequired[i]);
			assertEquals(i + 1, player.getLevel());
		}
	}

	@Test
	void giveExp_SomeExp_IncreaseMultipleLevels()
	{
		player.giveExp(expRequired[0] + expRequired[1] + expRequired[2]);
		assertEquals(3, player.getLevel());
		assertEquals(expRequired[0] + expRequired[1] + expRequired[2], player.getTotalExperience(), 0);
	}

	@Test
	void giveExp_SomeExp_DecreaseLevel()
	{
		player.giveExp(expRequired[0] + expRequired[1]);
		player.giveExp(-expRequired[1]);
		assertEquals(1, player.getLevel());
		assertEquals(expRequired[0], player.getTotalExperience(), 0);
	}

	@Test
	void giveExp_SomeExp_DecreaseMultipleLevels()
	{
		player.giveExp(expRequired[0] + expRequired[1]);
		player.giveExp(-(expRequired[0] + expRequired[1]));
		assertEquals(0, player.getLevel());
		assertEquals(0.0, player.getTotalExperience(), 0);
	}

	@Test
	void giveExp_SomeLevelChange_LevelEventFired()
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
	void giveExp_NoExpChange_NoEventFired()
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
	void getFood_LevelDefault20()
	{
		int foodLevel = player.getFoodLevel();
		assertEquals(20, foodLevel);
	}

	@Test
	void getFood_LevelChange()
	{
		player.setFoodLevel(10);
		assertEquals(10, player.getFoodLevel());
	}

	@Test
	void getPlayer_SneakingDefault()
	{
		boolean sneaking = player.isSneaking();
		assertFalse(sneaking);
	}

	@Test
	void getPlayer_SneakingChange()
	{
		player.setSneaking(true);
		assertTrue(player.isSneaking());
	}

	@Test
	void getPlayer_SneakingEyeHeight()
	{
		player.setSneaking(true);
		assertNotEquals(player.getEyeHeight(), player.getEyeHeight(true));
	}

	@Test
	void getPlayer_EyeLocationDiffers()
	{
		assertNotEquals(player.getEyeLocation(), player.getLocation());
	}

	@Test
	void dispatchPlayer_PlayerJoinEventFired()
	{
		server.addPlayer();
		PluginManagerMock pluginManager = server.getPluginManager();
		pluginManager.assertEventFired(event -> event instanceof PlayerJoinEvent);
	}

	@Test
	void testCompassDefaultTargetSpawnLocation()
	{
		assertEquals(player.getCompassTarget(), player.getLocation());
	}

	@Test
	void testSetCompassTarget()
	{
		Location loc = new Location(player.getWorld(), 12345678, 100, 12345678);

		player.setCompassTarget(loc);
		assertEquals(loc, player.getCompassTarget());

		player.setCompassTarget(loc);
		assertNotNull(player.getCompassTarget());
	}

	@Test
	void testBedSpawnLocation()
	{
		Location loc = new Location(player.getWorld(), 400, 80, 400);
		loc.getBlock().setType(Material.LIGHT_BLUE_BED);

		assertNull(player.getBedSpawnLocation());

		player.setBedSpawnLocation(loc);
		assertEquals(loc, player.getBedSpawnLocation());

		player.setBedSpawnLocation(null);
		assertNull(player.getBedSpawnLocation());
	}

	@Test
	void testBedSpawnLocationForce()
	{
		Location loc = new Location(player.getWorld(), 400, 80, 400);

		// Location is not actually a Bed and it should fail
		player.setBedSpawnLocation(loc);
		assertNull(player.getBedSpawnLocation());

		// Force the Bed Spawn Location
		player.setBedSpawnLocation(loc, true);
		assertEquals(loc, player.getBedSpawnLocation());
	}

	@Test
	void testBedSpawnLocationRespawn()
	{
		Location loc = new Location(player.getWorld(), 1230, 100, -421310);
		assertNotEquals(loc, player.getLocation());

		// Force the Bed Spawn Location
		player.setBedSpawnLocation(loc, true);

		player.setHealth(0);
		player.respawn();

		assertEquals(loc, player.getLocation());
	}

	@Test
	void testKeepInventoryFalse()
	{
		World world = player.getWorld();
		world.setGameRule(GameRule.KEEP_INVENTORY, false);

		player.getInventory().setItem(0, new ItemStack(Material.DIAMOND));
		player.setHealth(0.0);

		// The Player should have lost their inventory
		assertTrue(player.isDead());
		assertNull(player.getInventory().getItem(0));
	}

	@Test
	void testKeepInventoryTrue()
	{
		World world = player.getWorld();
		world.setGameRule(GameRule.KEEP_INVENTORY, true);

		player.getInventory().setItem(0, new ItemStack(Material.DIAMOND));
		player.setHealth(0.0);

		// The Player should have kept their inventory
		assertTrue(player.isDead());
		assertEquals(Material.DIAMOND, player.getInventory().getItem(0).getType());
	}

	@Test
	void testRespawnEventFired()
	{
		player.setHealth(0);
		assertTrue(player.isDead());

		player.respawn();

		PluginManagerMock pluginManager = server.getPluginManager();
		pluginManager.assertEventFired(event -> event instanceof PlayerRespawnEvent);

		assertFalse(player.isDead());
	}

	@Test
	void testPlaySound()
	{
		Sound sound = Sound.ENTITY_SLIME_SQUISH;
		float volume = 1;
		float pitch = 1;
		player.playSound(player.getLocation(), sound, SoundCategory.AMBIENT, volume, pitch);

		player.assertSoundHeard(sound, audio ->
		{
			return player.getLocation().equals(audio.getLocation()) && audio.getCategory() == SoundCategory.AMBIENT
			&& audio.getVolume() == volume && audio.getPitch() == pitch;
		});
	}

	@Test
	void testPlaySoundString()
	{
		String sound = "epic.mockbukkit.theme.song";
		float volume = 0.25F;
		float pitch = 0.75F;
		player.playSound(player.getEyeLocation(), sound, SoundCategory.RECORDS, volume, pitch);

		player.assertSoundHeard(sound, audio ->
		{
			return player.getEyeLocation().equals(audio.getLocation()) && audio.getCategory() == SoundCategory.RECORDS
			&& audio.getVolume() == volume && audio.getPitch() == pitch;
		});
	}

	@Test
	void testCloseInventoryEvenFired()
	{
		Inventory inv = server.createInventory(null, 36);
		player.openInventory(inv);
		player.setItemOnCursor(new ItemStack(Material.PUMPKIN));
		player.closeInventory();
		server.getPluginManager().assertEventFired(InventoryCloseEvent.class,
		        e -> e.getPlayer() == player && e.getInventory() == inv);
		assertTrue(player.getItemOnCursor().getType().isAir());
	}

	@Test
	void testSaturation()
	{
		// Default level
		assertEquals(5.0F, player.getSaturation(), 0.1F);

		player.setFoodLevel(20);
		player.setSaturation(8);
		assertEquals(8.0F, player.getSaturation(), 0.1F);

		// Testing the constraint
		player.setFoodLevel(20);
		player.setSaturation(10000);
		assertEquals(20.0F, player.getSaturation(), 0.1F);
	}

	@Test
	void testPotionEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.CONFUSION, 3, 1);
		assertTrue(player.addPotionEffect(effect));

		assertTrue(player.hasPotionEffect(effect.getType()));
		assertTrue(player.getActivePotionEffects().contains(effect));

		assertEquals(effect, player.getPotionEffect(effect.getType()));

		player.removePotionEffect(effect.getType());
		assertFalse(player.hasPotionEffect(effect.getType()));
		assertFalse(player.getActivePotionEffects().contains(effect));

	}

	@Test
	void testInstantEffect()
	{
		PotionEffect instant = new PotionEffect(PotionEffectType.HEAL, 0, 1);
		assertTrue(player.addPotionEffect(instant));
		assertFalse(player.hasPotionEffect(instant.getType()));
	}

	@Test
	void testMultiplePotionEffects()
	{
		Collection<PotionEffect> effects = Arrays.asList(new PotionEffect(PotionEffectType.BAD_OMEN, 3, 1),
		                                   new PotionEffect(PotionEffectType.LUCK, 5, 2));

		assertTrue(player.addPotionEffects(effects));

		for (PotionEffect effect : effects)
		{
			assertTrue(player.hasPotionEffect(effect.getType()));
		}
	}

	@Test
	void testFirstPlayed() throws InterruptedException
	{
		PlayerMock player = new PlayerMock(server, "FirstPlayed123");

		assertFalse(player.hasPlayedBefore());
		assertEquals(0, player.getFirstPlayed());
		assertEquals(0, player.getLastPlayed());

		server.addPlayer(player);
		long firstPlayed = player.getFirstPlayed();

		assertTrue(player.hasPlayedBefore());
		assertTrue(firstPlayed > 0);
		assertEquals(player.getFirstPlayed(), player.getLastPlayed());

		// Player reconnects
		server.addPlayer(player);

		assertEquals(firstPlayed, player.getFirstPlayed());
		assertNotEquals(player.getFirstPlayed(), player.getLastPlayed());
	}

	@Test
	void testIllegalArgumentForSpawning()
	{
		World world = new WorldMock();
		Location location = new Location(world, 300, 100, 300);
		assertThrows(IllegalArgumentException.class, () -> world.spawnEntity(location, EntityType.PLAYER));
	}

	@Test
	void testSetRemainingAir()
	{
		player.setRemainingAir(10);
		assertEquals(10, player.getRemainingAir());

		// Just a note: you can set the remaining air above the maximum air
		player.setRemainingAir(10000);
		assertEquals(10000, player.getRemainingAir());

		// And negative
		player.setRemainingAir(-1);
		assertEquals(-1, player.getRemainingAir());
	}

	@Test
	void testSetMaximumAir()
	{
		player.setMaximumAir(10);
		assertEquals(10, player.getMaximumAir());

		// This can be negative too
		player.setMaximumAir(-10);
		assertEquals(-10, player.getMaximumAir());
	}

	@Test
	void testSimulateBlockPlaceValid()
	{
		Location location = new Location(player.getWorld(), 0, 100, 0);
		GameMode originalGM = player.getGameMode();
		player.setGameMode(GameMode.SURVIVAL);
		BlockPlaceEvent event = player.simulateBlockPlace(Material.STONE, location);
		player.setGameMode(originalGM);
		assertNotNull(event);
		assertFalse(event.isCancelled());
	}

	@Test
	void testSimulateBlockPlaceInvalid()
	{
		Location location = new Location(player.getWorld(), 0, 100, 0);
		GameMode originalGM = player.getGameMode();
		player.setGameMode(GameMode.ADVENTURE);
		BlockPlaceEvent event = player.simulateBlockPlace(Material.STONE, location);
		player.setGameMode(originalGM);
		assertNull(event);
	}

	@Test
	void testSimulatePlayerMove()
	{
		World world = server.addSimpleWorld("world");
		player.setLocation(new Location(world, 0, 0, 0));
		PlayerMoveEvent event = player.simulatePlayerMove(new Location(world, 10, 0, 0));
		assertFalse(event.isCancelled());
		server.getPluginManager().assertEventFired(PlayerMoveEvent.class);
		assertEquals(10.0, player.getLocation().getX());
	}

	@Test
	void testSimulatePlayerMove_EventCancelled()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerMove(PlayerMoveEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		World world = server.addSimpleWorld("world");
		player.setLocation(new Location(world, 0, 0, 0));
		PlayerMoveEvent event = player.simulatePlayerMove(new Location(world, 10, 0, 0));
		assertTrue(event.isCancelled());
		server.getPluginManager().assertEventFired(PlayerMoveEvent.class);
		assertEquals(0.0, player.getLocation().getX());
	}

	@Test
	void testSimulatePlayerMove_WithTeleportation()
	{
		final Location teleportLocation = player.getLocation().add(10, 10, 10);
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerMove(PlayerMoveEvent event)
			{
				event.getPlayer().teleport(teleportLocation);
			}
		}, plugin);

		player.simulatePlayerMove(player.getLocation().add(-10, -10, -10));
		player.assertTeleported(teleportLocation, 0);
	}

	@Test
	void testSprint()
	{
		player.setSprinting(true);
		assertTrue(player.isSprinting());
	}

	@Test
	void testFly()
	{
		player.setFlying(true);
		assertTrue(player.isFlying());
	}

	@Test
	void testSneakEventFired()
	{
		PlayerToggleSneakEvent event = player.simulateSneak(true);
		assertNotNull(event);
		assertTrue(player.isSneaking());
		server.getPluginManager().assertEventFired(PlayerToggleSneakEvent.class);
	}

	@Test
	void testSprintEventFired()
	{
		PlayerToggleSprintEvent event = player.simulateSprint(true);
		assertNotNull(event);
		assertTrue(player.isSprinting());
		server.getPluginManager().assertEventFired(PlayerToggleSprintEvent.class);
	}

	@Test
	void testFlightEventFired()
	{
		PlayerToggleFlightEvent event = player.simulateToggleFlight(true);
		assertNotNull(event);
		assertTrue(player.isFlying());
		server.getPluginManager().assertEventFired(PlayerToggleFlightEvent.class);
	}

	@Test
	public void testPlayerHide_InitialState()
	{
		PlayerMock player2 = server.addPlayer();
		assertTrue(player.canSee(player2));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testPlayerHide_OldImplementation()
	{
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(player2);
		assertTrue(player.canSee(player2));
	}

	@Test
	public void testPlayerHide_NewImplementation()
	{
		MockPlugin plugin1 = MockBukkit.createMockPlugin("plugin1");
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(plugin1, player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(plugin1, player2);
		assertTrue(player.canSee(player2));
	}

	@Test
	public void testPlayerHide_OldAndNewPluginWorksSimultaneously()
	{
		MockPlugin plugin1 = MockBukkit.createMockPlugin("plugin1");
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(plugin1, player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(plugin1, player2);
		assertTrue(player.canSee(player2));
	}

	@Deprecated
	@Test
	public void testPlayerHide_EachOtherTest()
	{
		MockPlugin plugin1 = MockBukkit.createMockPlugin("plugin1");
		MockPlugin plugin2 = MockBukkit.createMockPlugin("plugin2");
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(plugin1, player2);
		assertFalse(player.canSee(player2));
		player.hidePlayer(plugin2, player2);
		assertFalse(player.canSee(player2));
		player.hidePlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(plugin2, player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(plugin1, player2);
		assertTrue(player.canSee(player2));
	}

	@Deprecated
	@Test
	public void testPlayerHide_HideCommandIssuedMultipleTimesOld()
	{
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(player2);
		player.hidePlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(player2);
		assertTrue(player.canSee(player2));
	}

	@Test
	public void testPlayerHide_HideCommandIssuedMultipleTimesNew()
	{
		MockPlugin plugin1 = MockBukkit.createMockPlugin("plugin1");
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(plugin1, player2);
		player.hidePlayer(plugin1, player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(plugin1, player2);
		assertTrue(player.canSee(player2));
	}

	@Test
	public void testPlayerTeleport_WithCause_EventFired()
	{
		player.teleport(player.getLocation().add(10, 10, 10), PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT);

		server.getPluginManager().assertEventFired(PlayerTeleportEvent.class);
	}

	@Test
	public void testPlayerTeleport_WithoutCause_EventFired()
	{
		player.teleport(player.getLocation().add(10, 10, 10));

		server.getPluginManager().assertEventFired(PlayerTeleportEvent.class);
	}

	@Test
	public void testPlayerTeleport_NotCanceled_PlayerTeleported()
	{
		Location teleportLocation = player.getLocation().add(10, 10, 10);
		player.teleport(teleportLocation);

		player.assertTeleported(teleportLocation, 0);
	}

	@Test
	public void testPlayerTeleport_Canceled_PlayerNotTeleported()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerTeleport(PlayerTeleportEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);

		Location originalLocation = player.getLocation();

		player.teleport(player.getLocation().add(10, 10, 10));

		player.assertNotTeleported();
		player.assertLocation(originalLocation, 0);

	}

}
