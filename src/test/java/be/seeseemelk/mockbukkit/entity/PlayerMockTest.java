package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.block.state.BlockStateMock;
import be.seeseemelk.mockbukkit.block.state.TileStateMock;
import be.seeseemelk.mockbukkit.entity.data.EntityState;
import be.seeseemelk.mockbukkit.inventory.EnderChestInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.map.MapViewMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerExpCooldownChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

	// Taken from https://minecraft.wiki/w/Experience#Leveling_up
	private static final int[] expRequired =
			{
					7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 42, 47, 52, 57, 62, 67, 72, 77, 82, 87, 92, 97, 102,
					107, 112, 121, 130, 139, 148, 157, 166, 175, 184, 193
			};
	private ServerMock server;
	private UUID uuid;
	private PlayerMock player;

	@BeforeEach
	void setUp()
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
		server.addPlayer(player);
	}

	@AfterEach
	void tearDown()
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
	void getInventory_getEquipment_SameInventory()
	{
		assertSame(player.getInventory(), player.getEquipment());
	}

	@Test
	void getEquipment_DropChance()
	{
		assertEquals(1, player.getEquipment().getHelmetDropChance());
		assertEquals(1, player.getEquipment().getChestplateDropChance());
		assertEquals(1, player.getEquipment().getLeggingsDropChance());
		assertEquals(1, player.getEquipment().getBootsDropChance());
		assertEquals(1, player.getEquipment().getItemInMainHandDropChance());
		assertEquals(1, player.getEquipment().getItemInOffHandDropChance());
	}

	@Test
	void getEquipment_SetDropChance()
	{
		EntityEquipment equipment = player.getEquipment();
		assertThrows(UnsupportedOperationException.class, () -> equipment.setHelmetDropChance(0));
		assertThrows(UnsupportedOperationException.class, () -> equipment.setChestplateDropChance(0));
		assertThrows(UnsupportedOperationException.class, () -> equipment.setLeggingsDropChance(0));
		assertThrows(UnsupportedOperationException.class, () -> equipment.setBootsDropChance(0));
		assertThrows(UnsupportedOperationException.class, () -> equipment.setItemInMainHandDropChance(0));
		assertThrows(UnsupportedOperationException.class, () -> equipment.setItemInOffHandDropChance(0));
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
	void getPreviousGameMode()
	{
		player.setGameMode(GameMode.SURVIVAL);
		player.setGameMode(GameMode.CREATIVE);
		player.setGameMode(GameMode.SURVIVAL);
		assertEquals(GameMode.CREATIVE, player.getPreviousGameMode());
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
	void breakBlock_Survival_BlockBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.SURVIVAL);
		BlockMock block = player.getWorld().getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		boolean broken = player.breakBlock(block);
		assertTrue(broken);
		server.getPluginManager().assertEventFired(BlockBreakEvent.class);
		block.assertType(Material.AIR);
	}

	@Test
	void breakBlock_Creative_Sword_BlockNotBroken()
	{
		MockBukkit.load(TestPlugin.class);
		player.setGameMode(GameMode.CREATIVE);
		player.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		BlockMock block = player.getWorld().getBlockAt(0, 0, 0);
		block.setType(Material.STONE);
		boolean broken = player.breakBlock(block);
		assertFalse(broken);
		server.getPluginManager().assertEventFired(BlockBreakEvent.class);
		block.assertType(Material.STONE);
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
			public void onBlockBreak(@NotNull BlockBreakEvent event)
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
			public void onBlockDamage(@NotNull BlockDamageEvent event)
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
			public void onBlockBreak(@NotNull BlockBreakEvent event)
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
			public void onBlockDamage(@NotNull BlockDamageEvent event)
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
	@EnumSource(value = GameMode.class, mode = EnumSource.Mode.EXCLUDE, names = { "SURVIVAL" })
	void simulateBlockDamage_NotSurvival_BlockNotDamaged(@NotNull GameMode nonSurvivalGameMode)
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
			public void onBlockDamage(@NotNull BlockDamageEvent event)
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
			public void onBlockDamage(@NotNull BlockDamageEvent event)
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
			public void onBlockDamage(@NotNull BlockDamageEvent event)
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
		assertNotEquals(player.getDisplayName(), player.getCustomName());
	}

	@Test
	void getDisplayName_NameSet_NameSet()
	{
		player.setDisplayName("Some Display Name");
		player.setCustomName("Some Custom Name");
		assertEquals("Some Display Name", player.getDisplayName());
		assertEquals("Some Custom Name", player.getCustomName());
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
	void testSendTitle()
	{
		player.sendTitle("test1", "test2");
		assertEquals("test1", player.nextTitle());
		assertEquals("test2", player.nextSubTitle());
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
	void testPlaySound_Adventure()
	{
		net.kyori.adventure.sound.Sound sound = net.kyori.adventure.sound.Sound.sound(
				Sound.BLOCK_ANVIL_FALL,
				net.kyori.adventure.sound.Sound.Source.BLOCK,
				0.9f,
				0.8f
		);
		player.playSound(sound);
		player.assertSoundHeard(sound, audio -> player.getLocation().equals(audio.getLocation()));
	}

	@Test
	void testPlaySoundSelfEmitter_Adventure()
	{
		net.kyori.adventure.sound.Sound sound = net.kyori.adventure.sound.Sound.sound(
				Sound.ENTITY_CREEPER_PRIMED,
				net.kyori.adventure.sound.Sound.Source.HOSTILE,
				0.9f,
				0.8f
		);
		player.playSound(sound, net.kyori.adventure.sound.Sound.Emitter.self());
		player.assertSoundHeard(sound, audio -> player.getLocation().equals(audio.getLocation()));
	}

	@Test
	void testPlaySoundLocation_Adventure()
	{
		net.kyori.adventure.sound.Sound sound = net.kyori.adventure.sound.Sound.sound(
				Sound.AMBIENT_CAVE,
				net.kyori.adventure.sound.Sound.Source.AMBIENT,
				0.5f,
				1f
		);
		Location loc = new Location(player.getWorld(), 80D, 30D, 50D);
		player.playSound(sound, loc.getX(), loc.getY(), loc.getZ());
		player.assertSoundHeard(sound, audio -> loc.equals(audio.getLocation()));
	}

	@Test
	void testAssertSoundHeard_Adventure()
	{
		net.kyori.adventure.sound.Sound soundA = net.kyori.adventure.sound.Sound.sound(
				Sound.BLOCK_DEEPSLATE_FALL,
				net.kyori.adventure.sound.Sound.Source.BLOCK,
				1f,
				1f
		);
		net.kyori.adventure.sound.Sound soundB = net.kyori.adventure.sound.Sound.sound(
				soundA.name(),
				net.kyori.adventure.sound.Sound.Source.MASTER,
				soundA.volume(),
				soundA.pitch()
		);
		player.playSound(soundA);
		player.assertSoundHeard(soundA);
		assertThrows(AssertionFailedError.class, () -> player.assertSoundHeard(soundB));
	}

	@Test
	void testPlayNote_OldMethod()
	{
		int note = 10;
		player.playNote(player.getEyeLocation(), (byte) 0, (byte) note);
		player.assertSoundHeard(Sound.BLOCK_NOTE_BLOCK_HARP, audio ->
		{
			return player.getEyeLocation().equals(audio.getLocation()) && audio.getCategory() == SoundCategory.RECORDS
					&& audio.getVolume() == 3.0f && Math.abs(audio.getPitch() - Math.pow(2.0D, (note - 12.0D) / 12.0D)) < 0.01;
		});
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
			public void onPlayerMove(@NotNull PlayerMoveEvent event)
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
			public void onPlayerMove(@NotNull PlayerMoveEvent event)
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
		player.setAllowFlight(true);
		player.setFlying(true);
		assertTrue(player.isFlying());
	}

	@Test
	void testFly_NotAllowed()
	{
		player.setAllowFlight(false);
		assertThrows(IllegalArgumentException.class, () -> player.setFlying(true));
	}

	@Test
	void testFly_DisabledWhenNotAllowed()
	{
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setAllowFlight(false);
		assertFalse(player.isFlying());
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
	void testPlayerHide_InitialState()
	{
		PlayerMock player2 = server.addPlayer();
		assertTrue(player.canSee(player2));
	}

	@Deprecated
	@Test
	void testPlayerHide_OldImplementation()
	{
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(player2);
		assertTrue(player.canSee(player2));
	}

	@Test
	void testPlayerHide_NewImplementation()
	{
		MockPlugin plugin1 = MockBukkit.createMockPlugin("plugin1");
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(plugin1, player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(plugin1, player2);
		assertTrue(player.canSee(player2));
	}

	@Deprecated
	@Test
	void testPlayerHide_OldAndNewPluginWorksSimultaneously()
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
	void testPlayerHide_EachOtherTest()
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
	void testPlayerHide_HideCommandIssuedMultipleTimesOld()
	{
		PlayerMock player2 = server.addPlayer();
		player.hidePlayer(player2);
		player.hidePlayer(player2);
		assertFalse(player.canSee(player2));
		player.showPlayer(player2);
		assertTrue(player.canSee(player2));
	}

	@Test
	void testPlayerHide_HideCommandIssuedMultipleTimesNew()
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
	void testPlayerTeleport_WithCause_EventFired()
	{
		Location from = player.getLocation();
		Location to = player.getLocation().add(10, 10, 10);
		player.teleport(to, PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT);

		server.getPluginManager().assertEventFired(PlayerTeleportEvent.class, event -> from.equals(event.getFrom()) && to.equals(event.getTo()));
		server.getPluginManager().assertEventNotFired(EntityTeleportEvent.class);
	}

	@Test
	void testPlayerTeleport_WithoutCause_EventFired()
	{
		player.teleport(player.getLocation().add(10, 10, 10));

		server.getPluginManager().assertEventFired(PlayerTeleportEvent.class);
		server.getPluginManager().assertEventNotFired(EntityTeleportEvent.class);
	}

	@Test
	void testPlayerTeleport_ChangedWorldEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		World from = player.getWorld();
		Location to = new Location(new WorldMock(), 0, 80, 0);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onChangedWorld(@NotNull PlayerChangedWorldEvent event)
			{
				assertEquals(to, event.getPlayer().getLocation(), "The location should already have changed when the PlayerChangedWorldEvent is fired");
			}
		}, plugin);
		player.teleport(to);
		server.getPluginManager().assertEventFired(PlayerTeleportEvent.class);
		server.getPluginManager().assertEventFired(PlayerChangedWorldEvent.class, event -> event.getFrom() == from);
	}

	@Test
	void testPlayerTeleport_NotCanceled_PlayerTeleported()
	{
		Location teleportLocation = player.getLocation().add(10, 10, 10);
		player.teleport(teleportLocation);

		player.assertTeleported(teleportLocation, 0);
	}

	@Test
	void testPlayerTeleport_Canceled_PlayerNotTeleported()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerTeleport(@NotNull PlayerTeleportEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);

		Location originalLocation = player.getLocation();

		player.teleport(player.getLocation().add(10, 10, 10));

		player.assertNotTeleported();
		player.assertLocation(originalLocation, 0);

	}

	@Test
	void testTeleport_ChangeDestinationInEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Location changedTo = player.getLocation().set(60, 90, -150);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerTeleport(@NotNull PlayerTeleportEvent event)
			{
				event.setTo(new Location(event.getTo().getWorld(), 60, 90, -150));
			}
		}, plugin);
		assertTrue(player.teleport(player.getLocation().add(0, 0, 20)));
		assertEquals(changedTo, player.getLocation());
	}

	@Test
	void testTeleport_CloseInventory()
	{
		Inventory inventory = Bukkit.createInventory(null, 9);
		player.openInventory(inventory);
		assertTrue(player.teleport(player.getLocation().add(8, 9, 10)));
		assertEquals(InventoryType.CRAFTING, player.getOpenInventory().getType());
		server.getPluginManager().assertEventFired(InventoryCloseEvent.class, e -> e.getReason() == InventoryCloseEvent.Reason.TELEPORT);
	}

	@Test
	void testTeleport_DontCloseCraftingInventory()
	{
		ItemStack itemStack = new ItemStack(Material.DEEPSLATE);
		player.getOpenInventory().setCursor(itemStack);
		assertTrue(player.teleport(player.getLocation().add(0, 10, 0)));
		assertEquals(itemStack, player.getOpenInventory().getCursor());
		server.getPluginManager().assertEventNotFired(InventoryCloseEvent.class);
	}

	@Test
	void testPlayerSendSignChange_Valid()
	{
		assertDoesNotThrow(() ->
		{
			player.sendSignChange(player.getLocation(), new String[4], DyeColor.CYAN, true);
		});
	}

	@Test
	void testPlayerSendSignChange_Invalid()
	{
		Location loc = player.getLocation();
		assertThrows(IllegalArgumentException.class, () ->
		{
			player.sendSignChange(loc, new String[2]);
		});
	}

	@Test
	void testPlayerPlayEffect()
	{
		Location loc = player.getLocation();
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		assertDoesNotThrow(() -> player.playEffect(loc, Effect.STEP_SOUND, blockData));
	}

	@Test
	void testPlayerPlayEffect_NullData()
	{
		Location loc = player.getLocation();
		assertThrows(IllegalArgumentException.class, () ->
		{
			player.playEffect(loc, Effect.STEP_SOUND, null);
		});
	}

	@Test
	void testPlayerSendExperienceChange()
	{
		assertDoesNotThrow(() ->
		{
			player.sendExperienceChange(0.5f);
		});
	}

	@Test
	void testPlayerSendBlockDamage()
	{
		Location loc = player.getLocation();
		assertDoesNotThrow(() ->
		{
			player.sendBlockDamage(loc, 0.5f);
		});
	}

	@Test
	void testPlayerSendBlockChange()
	{
		assertDoesNotThrow(() ->
		{
			player.sendBlockUpdate(player.getLocation(), new TileStateMock(Material.CHEST)
			{
				@Override
				public @NotNull BlockState getSnapshot()
				{
					return new BlockStateMock(Material.CHEST);
				}
			});
		});
	}

	@Test
	void testPlayerSendBlockUpdateInvalid()
	{
		Location location = player.getLocation();
		assertThrows(NullPointerException.class, () -> player.sendBlockUpdate(location, null));
		TileStateMock tileStateMock = new TileStateMock(Material.CHEST)
		{
			@Override
			public @NotNull BlockState getSnapshot()
			{
				return new BlockStateMock(Material.CHEST);
			}
		};
		assertThrows(NullPointerException.class, () -> player.sendBlockUpdate(null, tileStateMock));
	}

	@Test
	void testPlayerSendEquipmentChange()
	{
		assertDoesNotThrow(() -> player.sendEquipmentChange(player, EquipmentSlot.CHEST, new ItemStack(Material.DIAMOND_CHESTPLATE)));
	}

	@Test
	void testPlayerSendEquipmentChange_Map()
	{
		assertDoesNotThrow(() -> player.sendEquipmentChange(player, Map.of(EquipmentSlot.CHEST, new ItemStack(Material.DIAMOND_CHESTPLATE))));
	}

	@Test
	void showWinScreen_DoesntThrow()
	{
		assertDoesNotThrow(() -> player.showWinScreen());
	}

	@Test
	void testPlayerSendActionBar()
	{
		assertDoesNotThrow(() ->
		{
			player.sendActionBar("Action!");
		});
	}

	@Test
	void testPlayerSendHealthUpdate()
	{
		assertDoesNotThrow(() ->
		{
			player.sendHealthUpdate();
		});
	}

	@Test
	void testPlayerSendHealthUpdate_Params()
	{
		assertDoesNotThrow(() ->
		{
			player.sendHealthUpdate(20, 10, 0.0f);
		});
	}

	@Test
	void testPlayerSendMultiBlockChange()
	{
		assertDoesNotThrow(() ->
		{
			player.sendMultiBlockChange(new HashMap<>(0));
		});
	}

	@Test
	void testPlayerPlayEffect_IncorrectData()
	{
		Location loc = player.getLocation();
		assertThrows(IllegalArgumentException.class, () ->
		{
			player.playEffect(loc, Effect.STEP_SOUND, 1.0f);
		});
	}

	@SuppressWarnings("UnstableApiUsage")
	void testPlayerSendPluginMessage()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		server.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("MockBukkit");
		assertDoesNotThrow(() -> player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray()));
	}

	@Test
	void testPlayerSerialization()
	{
		Map<String, Object> serialized = player.serialize();
		assertEquals("player", serialized.get("name"));
	}

	@Test
	void testPlayerSpawnParticle_Correct_DataType()
	{
		player.spawnParticle(Particle.ITEM_CRACK, player.getLocation(), 1, new ItemStack(Material.STONE));
	}

	@Test
	void testPlayerSpawnParticle_Incorrect_DataType()
	{
		Location loc = player.getLocation();
		Object wrongObj = new Object();
		assertThrows(IllegalArgumentException.class, () ->
		{
			player.spawnParticle(Particle.ITEM_CRACK, loc, 1, wrongObj);
		});
	}

	@Test
	void setScoreboard()
	{
		ScoreboardManager manager = server.getScoreboardManager();
		assertSame(manager.getMainScoreboard(), player.getScoreboard());
		Scoreboard customScoreboard = manager.getNewScoreboard();
		player.setScoreboard(customScoreboard);
		assertSame(customScoreboard, player.getScoreboard());
	}

	@Test
	void getAddress_Constructor()
	{
		PlayerMock player = server.addPlayer();
		assertNotNull(player.getAddress());
	}

	@Test
	void setAddress()
	{
		PlayerMock player = server.addPlayer();
		InetSocketAddress address = new InetSocketAddress("192.0.2.78", 25565);
		player.setAddress(address);
		assertEquals(address, player.getAddress());
	}

	@Test
	void getAddress_NullWhenNotOnline()
	{
		PlayerMock player = new PlayerMock(server, "testPlayer");
		assertNull(player.getAddress());
		server.addPlayer(player);
		assertNotNull(player.getAddress());
	}

	@Test
	void testPlayerInventoryClick_Dispatched()
	{
		Inventory inventory = Bukkit.createInventory(null, 9);
		player.openInventory(inventory);
		player.simulateInventoryClick(0);
		server.getPluginManager().assertEventFired(event ->
		{
			if (!(event instanceof InventoryClickEvent inventoryClickEvent)) return false;
			if (inventoryClickEvent.getSlot() != 0) return false;
			if (inventoryClickEvent.getClickedInventory() != inventory) return false;
			return inventoryClickEvent.getClick() == ClickType.LEFT;
		});
	}

	@Test
	void testDisconnect()
	{
		assertTrue(player.isOnline());
		assertTrue(player.disconnect());
		assertFalse(player.isOnline());
		assertFalse(server.getOnlinePlayers().contains(player));
		server.getPluginManager().assertEventFired(PlayerQuitEvent.class);
	}

	@Test
	void testReconnect()
	{
		if (player.isOnline())
		{
			player.disconnect();
		}

		assertFalse(player.isOnline());
		assertTrue(player.reconnect());
		assertTrue(player.isOnline());
		assertTrue(server.getOnlinePlayers().contains(player));
		assertTrue(player.hasPlayedBefore());
		server.getPluginManager().assertEventFired(PlayerJoinEvent.class);
	}

	@Test
	void testReconnectWithoutJoiningBefore()
	{
		player = new PlayerMock(server, "testPlayer");

		assertThrows(IllegalStateException.class, () -> player.reconnect());

	}

	@Test
	void testReconnectWithPlayerOnline()
	{
		server.addPlayer(player);
		assertFalse(player.reconnect());
		assertTrue(server.getOnlinePlayers().contains(player));
	}

	@Test
	void testDisconnectWithPlayerOffline()
	{
		server.addPlayer(player);
		assertTrue(player.disconnect());
		assertFalse(player.disconnect());
		assertFalse(server.getOnlinePlayers().contains(player));
	}

	@Test
	void sendMap_RendersMap()
	{
		MapViewMock mapView = new MapViewMock(new WorldMock(), 1);
		AtomicBoolean b = new AtomicBoolean(false);
		mapView.addRenderer(new MapRenderer()
		{
			@Override
			public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
			{
				b.set(true);
			}
		});

		mapView.render(player);

		assertTrue(b.get());
	}

	@Test
	void testPlayerLastDeathLocation_Set()
	{
		assertTrue(player.getLastDeathLocation().getX() == 0.0
				&& player.getLastDeathLocation().getY() == 0.0
				&& player.getLastDeathLocation().getZ() == 0.0);

		Location loc = new Location(new WorldMock(), 69, 69, 69);

		player.setLastDeathLocation(loc);

		assertEquals(loc, player.getLastDeathLocation());

	}

	@Test
	void testIsWhiteListed()
	{
		server.getWhitelistedPlayers().add(player);
		assertTrue(player.isWhitelisted());
	}

	@Test
	void testSetWhiteListed()
	{
		player.setWhitelisted(true);
		assertTrue(player.isWhitelisted());
	}

	@Test
	void testIsBannedDefault()
	{
		assertFalse(player.isBanned());
	}

	@Test
	void testIsBanned()
	{
		player.banPlayer("test");
		assertTrue(player.isBanned());
	}

	@Test
	void testReconnectWithWhiteListEnabled()
	{
		server.setWhitelist(true);

		player.disconnect();
		player.reconnect();
		assertFalse(server.getOnlinePlayers().contains(player));
	}

	@Test
	void testReconnectWithWhiteListEnabledAndPlayerWhiteListed()
	{
		server.setWhitelist(true);
		player.setWhitelisted(true);
		player.disconnect();
		player.reconnect();
		assertTrue(server.getOnlinePlayers().contains(player));
	}

	@Test
	void testKickWithOfflinePlayer()
	{
		PlayerMock player = new PlayerMock(server, "testPlayer", UUID.randomUUID());
		player.kick(Component.text("test"), PlayerKickEvent.Cause.KICK_COMMAND);
		server.getPluginManager().assertEventNotFired(PlayerKickEvent.class);
	}

	@Test
	void testKickWithNullMessage()
	{
		player.kick(null, PlayerKickEvent.Cause.KICK_COMMAND);
		server.getPluginManager().assertEventFired(PlayerKickEvent.class, event -> event.leaveMessage() == Component.empty());
	}

	@Test
	void testSimulateConsumeItem()
	{
		ItemStack consumable = new ItemStack(Material.POTATO);

		player.simulateConsumeItem(consumable);

		player.assertItemConsumed(consumable);
		server.getPluginManager().assertEventFired(GenericGameEvent.class);
		server.getPluginManager().assertEventFired(PlayerItemConsumeEvent.class);
	}

	@Test
	void testSimulateConsumeItemWithNullItem()
	{
		assertThrows(NullPointerException.class, () -> player.simulateConsumeItem(null));
	}

	@Test
	void testSimulateConsumeItemWithInvalidItem()
	{
		ItemStack nonConsumable = new ItemStack(Material.STONE);
		assertThrows(IllegalArgumentException.class, () -> player.simulateConsumeItem(nonConsumable));
	}

	@Test
	void testAssertItemConsumedWithNotConsumedItem()
	{
		ItemStack notConsumed = new ItemStack(Material.APPLE);
		assertThrows(AssertionFailedError.class, () -> player.assertItemConsumed(notConsumed));
	}

	@Test
	void testAssertItemConsumedWithNullItem()
	{
		assertThrows(NullPointerException.class, () -> player.assertItemConsumed(null));
	}

	@Test
	void assertSaid_Spigot_CorrectMessage_DoesNotAssert()
	{
		player.spigot().sendMessage(TextComponent.fromLegacyText("Spigot message"));
		player.assertSaid("Spigot message");
	}

	@Test
	void assertSaid_Spigot_WrongMessage_Asserts()
	{
		player.sendMessage("Spigot message");
		assertThrows(AssertionError.class, () -> player.assertSaid("Some other message"));
	}

	@Test
	void testGetEntityType()
	{
		assertEquals(EntityType.PLAYER, player.getType());
	}

	@Test
	void testAssertInventoryViewDefault()
	{
		player.assertInventoryView(InventoryType.CRAFTING);
	}

	@Test
	void testAssertInventoryViewFailsWithWrongType()
	{
		assertThrows(AssertionError.class, () -> player.assertInventoryView(InventoryType.ANVIL));
	}

	@Test
	void testAssertInventoryViewWithPredicate()
	{
		InventoryMock inventory = server.createInventory(player, InventoryType.LOOM);
		ItemStack item = new ItemStack(Material.POTATO);
		inventory.addItem(item);
		player.openInventory(inventory);
		player.assertInventoryView(InventoryType.LOOM, view -> view.contains(item));
	}

	@Test
	void testAssertInventoryViewWithPredicateFails()
	{
		InventoryMock inventory = server.createInventory(player, InventoryType.LOOM);
		ItemStack item = new ItemStack(Material.POTATO);
		inventory.addItem(item);
		player.openInventory(inventory);
		assertThrows(AssertionError.class, () ->
		{
			player.assertInventoryView(InventoryType.LOOM, view -> view.contains(Material.APPLE));
		});
	}

	@Test
	void testAssertInventoryViewWithStringAndType()
	{
		InventoryMock inventory = server.createInventory(player, InventoryType.LOOM);
		player.openInventory(inventory);
		player.assertInventoryView("Loom", InventoryType.LOOM);
	}

	@Test
	void testDisplayName()
	{
		player.displayName(Component.text("test"));
		assertEquals(Component.text("test"), player.displayName());
	}

	@Test
	void testPlayerListNameDefault()
	{
		assertEquals(Component.text(player.getName()), player.playerListName());
	}

	@Test
	void testPlayerListNameSet()
	{
		player.playerListName(Component.text("test"));
		assertEquals(Component.text("test"), player.playerListName());
	}

	@Test
	void testPlayerListHeaderDefault()
	{
		assertNull(player.playerListHeader());
	}

	@Test
	void testPlayerListHeaderSet()
	{
		player.setPlayerListHeader("test");
		assertEquals(Component.text("test"), player.playerListHeader());
	}

	@Test
	void testPlayerListFooterDefault()
	{
		assertNull(player.playerListFooter());
	}

	@Test
	void testPlayerListFooterSet()
	{
		player.setPlayerListFooter("test");
		assertEquals(Component.text("test"), player.playerListFooter());
	}

	@Test
	void testPlaySoundWithLocationSoundVolumePitch()
	{
		Sound sound = Sound.ENTITY_SLIME_SQUISH;
		float volume = 1;
		float pitch = 1;
		player.playSound(player.getLocation(), sound, volume, pitch);

		player.assertSoundHeard(sound, audio ->
		{
			return player.getLocation().equals(audio.getLocation()) && audio.getVolume() == volume
					&& audio.getPitch() == pitch;
		});
	}

	@Test
	void testPlaySoundStringWithoutCategory()
	{
		String sound = "epic.mockbukkit.theme.song";
		float volume = 0.25F;
		float pitch = 0.75F;
		player.playSound(player.getEyeLocation(), sound, volume, pitch);

		player.assertSoundHeard(sound, audio ->
		{
			return player.getEyeLocation().equals(audio.getLocation()) && audio.getVolume() == volume
					&& audio.getPitch() == pitch;
		});
	}

	@Test
	void testPlaySoundWithEntity()
	{
		Sound sound = Sound.ENTITY_SLIME_SQUISH;
		float volume = 1;
		float pitch = 1;
		player.playSound(player, sound, SoundCategory.AMBIENT, volume, pitch);

		player.assertSoundHeard(sound, audio ->
		{
			return player.getLocation().equals(audio.getLocation()) && audio.getCategory() == SoundCategory.AMBIENT
					&& audio.getVolume() == volume && audio.getPitch() == pitch;
		});

	}

	@Test
	void testPlaySoundWithEntityWithoutCategory()
	{
		Sound sound = Sound.ENTITY_SLIME_SQUISH;
		float volume = 1;
		float pitch = 1;
		player.playSound(player, sound, volume, pitch);

		player.assertSoundHeard(sound, audio ->
		{
			return player.getLocation().equals(audio.getLocation()) && audio.getVolume() == volume
					&& audio.getPitch() == pitch;
		});

	}

	@Test
	void setLastPlayed_ThrowsException()
	{
		PlayerMock player = server.addPlayer();

		assertThrows(UnsupportedOperationException.class, () -> player.setLastPlayed(0));
	}

	@Test
	void getWalkSpeed()
	{
		assertEquals(0.2f, player.getWalkSpeed());
	}

	@Test
	void setWalkSpeed()
	{
		player.setWalkSpeed(0.4f);
		assertEquals(0.4f, player.getWalkSpeed());
	}

	@Test
	void setWalkSpeed_TooLow_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setWalkSpeed(-1.1f));
	}

	@Test
	void setWalkSpeed_TooHigh_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setWalkSpeed(1.1f));
	}

	@Test
	void isHealthScaled()
	{
		assertFalse(player.isHealthScaled());
	}

	@Test
	void setHealthScaled()
	{
		player.setHealthScaled(true);
		assertTrue(player.isHealthScaled());
	}

	@Test
	void getHealthScale()
	{
		assertEquals(20d, player.getHealthScale());
	}

	@Test
	void setHealthScale()
	{
		player.setHealthScale(10d);
		assertEquals(10d, player.getHealthScale());
		assertTrue(player.isHealthScaled());
	}

	@Test
	void setHealthScale_Negative_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setHealthScale(-0.1d));
	}

	@Test
	void setHealthScale_NaN_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setHealthScale(Double.NaN));
	}

	@Test
	void hasPlayedBefore_AddedToServer_False()
	{
		PlayerMock player = server.addPlayer();

		assertFalse(player.hasPlayedBefore());
	}

	@Test
	void hasPlayedBefore_NotAddedToServer_False()
	{
		PlayerMock player = new PlayerMock(server, "player");

		assertFalse(player.hasPlayedBefore());
	}

	@Test
	void testSetOpFalse()
	{
		PlayerMock player = server.addPlayer();
		player.setOp(false);
		assertFalse(player.isOp());
	}

	@Test
	void testSetOpTrue()
	{
		PlayerMock player = server.addPlayer();
		player.setOp(true);
		assertTrue(player.isOp());
	}

	@Test
	void testGetEntityStateDefault()
	{
		PlayerMock player = server.addPlayer();
		assertEquals(EntityState.DEFAULT, player.getEntityState());
	}

	@Test
	void testGetEntityStateSneaking()
	{
		PlayerMock player = server.addPlayer();
		player.setSneaking(true);
		assertEquals(EntityState.SNEAKING, player.getEntityState());
	}

	@Test
	void testGetEntityStateSwimming()
	{
		PlayerMock player = server.addPlayer();
		player.setSwimming(true);
		assertEquals(EntityState.SWIMMING, player.getEntityState());
	}

	@Test
	void testGetEntityStateFlying()
	{
		PlayerMock player = server.addPlayer();
		player.setGliding(true);
		assertEquals(EntityState.GLIDING, player.getEntityState());
	}

	@ParameterizedTest
	@MethodSource("provideInstrument")
	void testPlayNote(Instrument instrument, Sound sound)
	{
		int note = 10;
		player.playNote(player.getEyeLocation(), instrument, new Note(note));
		player.assertSoundHeard(sound, audio ->
		{
			return player.getEyeLocation().equals(audio.getLocation()) && audio.getCategory() == SoundCategory.RECORDS
					&& audio.getVolume() == 3.0f && Math.abs(audio.getPitch() - Math.pow(2.0D, (note - 12.0D) / 12.0D)) < 0.01;
		});
	}

	@Test
	void testPlayerQuitEventGetFired()
	{
		PlayerMock player = server.addPlayer("Player");
		player.disconnect();
		server.getPluginManager().assertEventFired(PlayerQuitEvent.class);
	}

	public static Stream<Arguments> provideInstrument()
	{
		return Stream.of(
				Arguments.of(Instrument.CUSTOM_HEAD, Sound.UI_BUTTON_CLICK),
				Arguments.of(Instrument.PIGLIN, Sound.BLOCK_NOTE_BLOCK_IMITATE_PIGLIN),
				Arguments.of(Instrument.WITHER_SKELETON, Sound.BLOCK_NOTE_BLOCK_IMITATE_WITHER_SKELETON),
				Arguments.of(Instrument.DRAGON, Sound.BLOCK_NOTE_BLOCK_IMITATE_ENDER_DRAGON),
				Arguments.of(Instrument.CREEPER, Sound.BLOCK_NOTE_BLOCK_IMITATE_CREEPER),
				Arguments.of(Instrument.SKELETON, Sound.BLOCK_NOTE_BLOCK_IMITATE_SKELETON),
				Arguments.of(Instrument.ZOMBIE, Sound.BLOCK_NOTE_BLOCK_IMITATE_ZOMBIE),
				Arguments.of(Instrument.XYLOPHONE, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE),
				Arguments.of(Instrument.BANJO, Sound.BLOCK_NOTE_BLOCK_BANJO),
				Arguments.of(Instrument.BASS_DRUM, Sound.BLOCK_NOTE_BLOCK_BASEDRUM),
				Arguments.of(Instrument.BASS_GUITAR, Sound.BLOCK_NOTE_BLOCK_BASS),
				Arguments.of(Instrument.BELL, Sound.BLOCK_NOTE_BLOCK_BELL),
				Arguments.of(Instrument.BIT, Sound.BLOCK_NOTE_BLOCK_BIT),
				Arguments.of(Instrument.CHIME, Sound.BLOCK_NOTE_BLOCK_CHIME),
				Arguments.of(Instrument.COW_BELL, Sound.BLOCK_NOTE_BLOCK_COW_BELL),
				Arguments.of(Instrument.DIDGERIDOO, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO),
				Arguments.of(Instrument.FLUTE, Sound.BLOCK_NOTE_BLOCK_FLUTE),
				Arguments.of(Instrument.GUITAR, Sound.BLOCK_NOTE_BLOCK_GUITAR),
				Arguments.of(Instrument.IRON_XYLOPHONE, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE),
				Arguments.of(Instrument.PLING, Sound.BLOCK_NOTE_BLOCK_PLING),
				Arguments.of(Instrument.SNARE_DRUM, Sound.BLOCK_NOTE_BLOCK_SNARE),
				Arguments.of(Instrument.STICKS, Sound.BLOCK_NOTE_BLOCK_HAT)
		);
	}

	@Test
	void setExpCooldown()
	{
		player.setExpCooldown(10);
		assertEquals(10, player.getExpCooldown());
	}

	@Test
	void setExpCooldown_Negative_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> player.setExpCooldown(-1));
	}

	@Test
	void setExpCooldown_CallsEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);

		Bukkit.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void expCooldownChange(@NotNull PlayerExpCooldownChangeEvent event)
			{
			}
		}, plugin);

		player.setExpCooldown(10);
		server.getPluginManager().assertEventFired(PlayerExpCooldownChangeEvent.class);
		assertEquals(10, player.getExpCooldown());
	}

	@Test
	void addAndRemoveBossBar()
	{
		BossBar bar = BossBar.bossBar(Component.text("Test"), 1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
		player.showBossBar(bar);
		assertEquals(1, player.getBossBars().size());
		assertTrue(player.getBossBars().contains(bar));

		BossBar bossBar = List.copyOf(player.getBossBars()).get(0);
		Component name = bossBar.name();
		assertTrue(name instanceof net.kyori.adventure.text.TextComponent);
		assertEquals("Test", ((net.kyori.adventure.text.TextComponent) name).content());
		assertEquals(1, bossBar.progress());
		assertEquals(BossBar.Color.BLUE, bossBar.color());
		assertEquals(BossBar.Overlay.PROGRESS, bossBar.overlay());

		player.hideBossBar(bar);
		assertEquals(0, player.getBossBars().size());
		assertFalse(player.getBossBars().contains(bar));
	}

	@Test
	void addAndRemoveMultipleBossBar()
	{
		BossBar bar1 = BossBar.bossBar(Component.text("Test1"), 1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
		BossBar bar2 = BossBar.bossBar(Component.text("Test2"), 1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
		BossBar bar3 = BossBar.bossBar(Component.text("Test3"), 1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);

		player.showBossBar(bar1);
		player.showBossBar(bar2);
		player.showBossBar(bar3);

		assertEquals(3, player.getBossBars().size());

		player.hideBossBar(bar2);

		assertEquals(2, player.getBossBars().size());
		assertTrue(player.getBossBars().contains(bar1));
		assertFalse(player.getBossBars().contains(bar2));

		player.hideBossBar(bar1);

		assertEquals(1, player.getBossBars().size());
		assertFalse(player.getBossBars().contains(bar1));

		player.showBossBar(bar2);

		assertEquals(2, player.getBossBars().size());
		assertTrue(player.getBossBars().contains(bar2));
		assertTrue(player.getBossBars().contains(bar3));
	}

	@Test
	void updateViewedBossBar()
	{
		BossBar bar = BossBar.bossBar(Component.text("Test"), 1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS, Collections.singleton(BossBar.Flag.PLAY_BOSS_MUSIC));
		player.showBossBar(bar);

		assertEquals(1, player.getBossBars().size());
		assertTrue(player.getBossBars().contains(bar));

		BossBar bossBar = List.copyOf(player.getBossBars()).get(0);
		Component name = bossBar.name();
		assertTrue(name instanceof net.kyori.adventure.text.TextComponent);
		assertEquals("Test", ((net.kyori.adventure.text.TextComponent) name).content());
		assertEquals(1, bossBar.progress());
		assertEquals(BossBar.Color.BLUE, bossBar.color());
		assertEquals(BossBar.Overlay.PROGRESS, bossBar.overlay());
		assertEquals(Collections.singleton(BossBar.Flag.PLAY_BOSS_MUSIC), bossBar.flags());

		bar.name(Component.text("Test2"));
		name = bossBar.name();
		assertTrue(name instanceof net.kyori.adventure.text.TextComponent);
		assertEquals("Test2", ((net.kyori.adventure.text.TextComponent) name).content());

		bar.progress(0.5f);
		assertEquals(0.5f, bossBar.progress());

		bar.color(BossBar.Color.GREEN);
		assertEquals(BossBar.Color.GREEN, bossBar.color());

		bar.overlay(BossBar.Overlay.NOTCHED_10);
		assertEquals(BossBar.Overlay.NOTCHED_10, bossBar.overlay());

		bar.flags(Collections.singleton(BossBar.Flag.DARKEN_SCREEN));
		assertEquals(Collections.singleton(BossBar.Flag.DARKEN_SCREEN), bossBar.flags());
	}

	@Test
	void testPlayerProfile()
	{
		PlayerProfile profile = player.getPlayerProfile();

		assertEquals(player.getUniqueId(), profile.getId());
		assertEquals(player.getName(), profile.getName());
	}

	@Test
	void testSetPlayerProfile()
	{
		UUID uuid = UUID.randomUUID();
		PlayerProfile profile = Bukkit.createProfile(uuid, "Test");
		player.setPlayerProfile(profile);

		assertEquals(profile, player.getPlayerProfile());
	}

}
