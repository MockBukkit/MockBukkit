package org.mockbukkit.mockbukkit.matcher;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.command.defaults.VersionCommand;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.command.CommandResult;
import org.mockbukkit.mockbukkit.entity.AllayMock;
import org.mockbukkit.mockbukkit.entity.GoatMock;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.entity.SkeletonMock;
import org.mockbukkit.mockbukkit.help.HelpMapMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;
import org.mockbukkit.mockbukkit.sound.AudioExperience;
import org.mockbukkit.mockbukkit.sound.SoundReceiver;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.containsAtLeast;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.containsLessThan;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveCurrentItem;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveFactoryRegistered;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveInventoryViewType;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveItemInInventory;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveItemInInventoryView;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveLore;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveMaterial;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveResponse;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasAnyLore;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasAnyResponse;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasAttacked;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasConsumed;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasCurrentItem;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasFactoryRegistered;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasFailed;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasFiredEventInstance;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasFiredFilteredEvent;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasHeard;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasInventoryViewType;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasItemInInventory;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasItemInInventoryView;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasLore;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasMaterial;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNoLore;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNoOverdueTasks;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNoResponse;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotAttacked;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotConsumed;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotFiredEventInstance;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotFiredFilteredEvent;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotHeard;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotRammed;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotReceived;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotReceivedAny;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasNotTeleported;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasOverdueTasks;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasRammed;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasReceived;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasReceivedAny;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasResponse;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasSucceeded;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasTeleported;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.isInLocation;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.isNotInLocation;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.similarTo;
import static org.mockbukkit.testutils.matcher.AbstractMatcherTest.assertMatches;

/**
 * Behavioural coverage for the {@link MockBukkitMatchers} facade.
 * <p>
 * Every factory method on the facade is a one-line delegate to the dedicated matcher class. Each delegate gets its own
 * nested class named after the facade method, so it is obvious which tests belong to which method; overloaded methods
 * that share a name share a nested class. Every test invokes the delegate through the facade and asserts the returned
 * matcher behaves as expected. The matching logic itself is verified in the tests for the individual matcher classes.
 */
@ExtendWith(MockBukkitExtension.class)
class MockBukkitMatchersTest
{

	private static final String CUSTOM_SOUND_KEY = "custom.sound";

	@MockBukkitInject
	private ServerMock serverMock;
	@MockBukkitInject
	private AllayMock allay;
	@MockBukkitInject
	private GoatMock goat;
	@MockBukkitInject
	private SkeletonMock skeleton;
	@MockBukkitInject
	private HumanEntityMock human;
	@MockBukkitInject
	private ItemMetaMock itemMeta;
	@MockBukkitInject
	private BukkitSchedulerMock scheduler;
	@MockBukkitInject
	private SoundReceiver soundReceiver;
	@MockBukkitInject
	private Location location;

	private static Sound heardAdventureSound()
	{
		return Sound.sound().type(Key.key("music_disc.13")).source(Sound.Source.MUSIC).build();
	}

	private static Sound unheardAdventureSound()
	{
		return Sound.sound().type(Key.key("music_disc.11")).source(Sound.Source.MUSIC).build();
	}

	private void primeHeardSounds()
	{
		soundReceiver.addHeardSound(new AudioExperience(heardAdventureSound(), location));
		soundReceiver.addHeardSound(new AudioExperience(CUSTOM_SOUND_KEY, SoundCategory.MASTER, location, 1f, 1f));
		soundReceiver.addHeardSound(new AudioExperience(org.bukkit.Sound.BLOCK_ANVIL_BREAK, SoundCategory.MASTER, location, 1f, 1f));
	}

	// ===================================================================================
	// Block
	// ===================================================================================

	@Nested
	class HasMaterial
	{

		@Test
		void matches()
		{
			assertMatches(hasMaterial(Material.CHEST), new BlockMock(Material.CHEST));
		}

	}

	@Nested
	class DoesNotHaveMaterial
	{

		@Test
		void matches()
		{
			assertMatches(doesNotHaveMaterial(Material.STONE), new BlockMock(Material.CHEST));
		}

	}

	// ===================================================================================
	// Command
	// ===================================================================================

	@Nested
	class HasAnyResponse
	{

		@Test
		void matches()
		{
			PlayerMock sender = serverMock.addPlayer();
			sender.sendMessage("Hello world!");
			assertMatches(hasAnyResponse(), new CommandResult(true, sender));
		}

	}

	@Nested
	class HasNoResponse
	{

		@Test
		void matches()
		{
			assertMatches(hasNoResponse(), new CommandResult(true, serverMock.addPlayer()));
		}

	}

	@Nested
	class HasResponse
	{

		@Test
		void matches()
		{
			PlayerMock sender = serverMock.addPlayer();
			sender.sendMessage("Hello world!");
			assertMatches(hasResponse("Hello world!"), new CommandResult(true, sender));
		}

	}

	@Nested
	class DoesNotHaveResponse
	{

		@Test
		void matches()
		{
			PlayerMock sender = serverMock.addPlayer();
			sender.sendMessage("Hello world!");
			assertMatches(doesNotHaveResponse("Goodbye"), new CommandResult(true, sender));
		}

	}

	@Nested
	class HasSucceeded
	{

		@Test
		void matches()
		{
			assertMatches(hasSucceeded(), new CommandResult(true, serverMock.addPlayer()));
		}

	}

	@Nested
	class HasFailed
	{

		@Test
		void matches()
		{
			assertMatches(hasFailed(), new CommandResult(false, serverMock.addPlayer()));
		}

	}

	@Nested
	class HasReceivedAny
	{

		@Test
		void matches()
		{
			PlayerMock target = serverMock.addPlayer();
			target.sendMessage("Hello world!");
			assertMatches(hasReceivedAny(), target);
		}

	}

	@Nested
	class HasNotReceivedAny
	{

		@Test
		void matches()
		{
			assertMatches(hasNotReceivedAny(), serverMock.addPlayer());
		}

	}

	@Nested
	class HasReceived
	{

		@Test
		void matchesString()
		{
			PlayerMock target = serverMock.addPlayer();
			target.sendMessage("Hello world!");
			assertMatches(hasReceived("Hello world!"), target);
		}

		@Test
		void matchesComponent()
		{
			PlayerMock target = serverMock.addPlayer();
			target.sendMessage(Component.text("Hello world!"));
			assertMatches(hasReceived(Component.text("Hello world!")), target);
		}

	}

	@Nested
	class HasNotReceived
	{

		@Test
		void matches()
		{
			assertMatches(hasNotReceived("Hello world!"), serverMock.addPlayer());
		}

	}

	// ===================================================================================
	// Entity
	// ===================================================================================

	@Nested
	class IsInLocation
	{

		@Test
		void matches()
		{
			PlayerMock entity = serverMock.addPlayer();
			assertMatches(isInLocation(entity.getLocation().clone(), 0), entity);
		}

	}

	@Nested
	class IsNotInLocation
	{

		@Test
		void matches()
		{
			PlayerMock entity = serverMock.addPlayer();
			assertMatches(isNotInLocation(entity.getLocation().clone().add(100, 0, 0), 1), entity);
		}

	}

	@Nested
	class HasTeleported
	{

		private PlayerMock entity;
		private Location destination;

		@BeforeEach
		void setUp()
		{
			this.entity = serverMock.addPlayer();
			this.destination = entity.getLocation().clone().add(20, 0, 0);
			entity.teleport(destination);
		}

		@Test
		void matches()
		{
			assertMatches(hasTeleported(), entity);
		}

		@Test
		void matchesLocation()
		{
			assertMatches(hasTeleported(destination), entity);
		}

		@Test
		void matchesLocationWithinDistance()
		{
			assertMatches(hasTeleported(destination, 0), entity);
		}

	}

	@Nested
	class HasNotTeleported
	{

		private PlayerMock entity;
		private Location destination;

		@BeforeEach
		void setUp()
		{
			this.entity = serverMock.addPlayer();
			this.destination = entity.getLocation().clone().add(20, 0, 0);
		}

		@Test
		void matches()
		{
			assertMatches(hasNotTeleported(), entity);
		}

		@Test
		void matchesLocation()
		{
			assertMatches(hasNotTeleported(destination), entity);
		}

		@Test
		void matchesLocationWithinDistance()
		{
			assertMatches(hasNotTeleported(destination, 0), entity);
		}

	}

	@Nested
	class HasCurrentItem
	{

		@Test
		void matches()
		{
			allay.simulatePlayerInteract(Material.STONE);
			assertMatches(hasCurrentItem(Material.STONE), allay);
		}

	}

	@Nested
	class DoesNotHaveCurrentItem
	{

		@Test
		void matches()
		{
			allay.simulatePlayerInteract(Material.STONE);
			assertMatches(doesNotHaveCurrentItem(Material.DIAMOND), allay);
		}

	}

	@Nested
	class HasRammed
	{

		@Test
		void matches()
		{
			PlayerMock target = serverMock.addPlayer();
			goat.ram(target);
			assertMatches(hasRammed(target), goat);
		}

	}

	@Nested
	class HasNotRammed
	{

		@Test
		void matches()
		{
			assertMatches(hasNotRammed(serverMock.addPlayer()), goat);
		}

	}

	@Nested
	class HasItemInInventoryView
	{

		private InventoryMock inventory;

		@BeforeEach
		void setUp()
		{
			this.inventory = serverMock.createInventory(human, InventoryType.BARREL);
			human.openInventory(inventory);
			inventory.addItem(new ItemStack(Material.DIAMOND));
		}

		@Test
		void matchesMaterial()
		{
			assertMatches(hasItemInInventoryView(Material.DIAMOND), human);
		}

		@Test
		void matchesItemStack()
		{
			assertMatches(hasItemInInventoryView(new ItemStack(Material.DIAMOND)), human);
		}

	}

	@Nested
	class DoesNotHaveItemInInventoryView
	{

		@BeforeEach
		void setUp()
		{
			human.openInventory(serverMock.createInventory(human, InventoryType.BARREL));
		}

		@Test
		void matchesMaterial()
		{
			assertMatches(doesNotHaveItemInInventoryView(Material.BEACON), human);
		}

		@Test
		void matchesItemStack()
		{
			assertMatches(doesNotHaveItemInInventoryView(new ItemStack(Material.BEACON)), human);
		}

	}

	@Nested
	class HasInventoryViewType
	{

		@Test
		void matches()
		{
			human.openInventory(serverMock.createInventory(human, InventoryType.BARREL));
			assertMatches(hasInventoryViewType(InventoryType.BARREL), human);
		}

	}

	@Nested
	class DoesNotHaveInventoryViewType
	{

		@Test
		void matches()
		{
			human.openInventory(serverMock.createInventory(human, InventoryType.BARREL));
			assertMatches(doesNotHaveInventoryViewType(InventoryType.LOOM), human);
		}

	}

	@Nested
	class HasConsumed
	{

		@Test
		void matches()
		{
			PlayerMock player = serverMock.addPlayer();
			ItemStack potato = new ItemStack(Material.POTATO);
			player.simulateConsumeItem(potato);
			assertMatches(hasConsumed(potato), player);
		}

	}

	@Nested
	class HasNotConsumed
	{

		@Test
		void matches()
		{
			assertMatches(hasNotConsumed(new ItemStack(Material.POTATO)), serverMock.addPlayer());
		}

	}

	@Nested
	class HasAttacked
	{

		private PlayerMock target;

		@BeforeEach
		void setUp()
		{
			this.target = serverMock.addPlayer();
			skeleton.rangedAttack(target, 0.5f);
		}

		@Test
		void matchesCharge()
		{
			assertMatches(hasAttacked(target, 0.5f), skeleton);
		}

		@Test
		void matchesChargeAndAggressive()
		{
			assertMatches(hasAttacked(target, 0.5f, false), skeleton);
		}

	}

	@Nested
	class HasNotAttacked
	{

		@BeforeEach
		void setUp()
		{
			skeleton.rangedAttack(serverMock.addPlayer(), 0.5f);
		}

		@Test
		void matchesCharge()
		{
			assertMatches(hasNotAttacked(serverMock.addPlayer(), 0.5f), skeleton);
		}

		@Test
		void matchesChargeAndAggressive()
		{
			assertMatches(hasNotAttacked(serverMock.addPlayer(), 0.5f, false), skeleton);
		}

	}

	// ===================================================================================
	// Help
	// ===================================================================================

	@Nested
	class HasFactoryRegistered
	{

		@Test
		void matches()
		{
			HelpMapMock helpMap = serverMock.getHelpMap();
			HelpTopicFactory<VersionCommand> factory = command -> new IndexHelpTopic("", "short text", "perm", Collections.emptyList());
			helpMap.registerHelpTopicFactory(VersionCommand.class, factory);
			assertMatches(hasFactoryRegistered(factory), helpMap);
		}

	}

	@Nested
	class DoesNotHaveFactoryRegistered
	{

		@Test
		void matches()
		{
			HelpMapMock helpMap = serverMock.getHelpMap();
			HelpTopicFactory<VersionCommand> factory = command -> new IndexHelpTopic("", "short text", "perm", Collections.emptyList());
			assertMatches(doesNotHaveFactoryRegistered(factory), helpMap);
		}

	}

	// ===================================================================================
	// Inventory
	// ===================================================================================

	@Nested
	class ContainsAtLeast
	{

		private InventoryMock inventory;

		@BeforeEach
		void setUp()
		{
			InventoryHolder holder = serverMock.addPlayer();
			this.inventory = new InventoryMock(holder, InventoryType.CHEST);
			inventory.addItem(new ItemStack(Material.DIAMOND, 3));
		}

		@Test
		void matchesMaterial()
		{
			assertMatches(containsAtLeast(Material.DIAMOND, 3), inventory);
		}

		@Test
		void matchesItemStack()
		{
			assertMatches(containsAtLeast(new ItemStack(Material.DIAMOND), 3), inventory);
		}

	}

	@Nested
	class ContainsLessThan
	{

		private InventoryMock inventory;

		@BeforeEach
		void setUp()
		{
			InventoryHolder holder = serverMock.addPlayer();
			this.inventory = new InventoryMock(holder, InventoryType.CHEST);
			inventory.addItem(new ItemStack(Material.DIAMOND, 3));
		}

		@Test
		void matchesMaterial()
		{
			assertMatches(containsLessThan(Material.DIAMOND, 4), inventory);
		}

		@Test
		void matchesItemStack()
		{
			assertMatches(containsLessThan(new ItemStack(Material.DIAMOND), 4), inventory);
		}

	}

	@Nested
	class SimilarTo
	{

		@Test
		void matchesItemStack()
		{
			assertMatches(similarTo(new ItemStack(Material.DIAMOND)), new ItemStack(Material.DIAMOND, 5));
		}

		@Test
		void matchesMaterial()
		{
			assertMatches(similarTo(Material.DIAMOND), new ItemStack(Material.DIAMOND));
		}

	}

	@Nested
	class HasItemInInventory
	{

		@Test
		void matches()
		{
			ItemStack stone = new ItemStack(Material.STONE);
			allay.simulatePlayerInteract(Material.STONE);
			allay.simulateItemPickup(stone);
			assertMatches(hasItemInInventory(stone), allay);
		}

	}

	@Nested
	class DoesNotHaveItemInInventory
	{

		@Test
		void matches()
		{
			allay.simulatePlayerInteract(Material.STONE);
			allay.simulateItemPickup(new ItemStack(Material.STONE));
			assertMatches(doesNotHaveItemInInventory(new ItemStack(Material.DIAMOND)), allay);
		}

	}

	@Nested
	class HasAnyLore
	{

		@Test
		void matches()
		{
			itemMeta.setLore(List.of("Hello", "world!"));
			assertMatches(hasAnyLore(), itemMeta);
		}

	}

	@Nested
	class HasNoLore
	{

		@Test
		void matches()
		{
			assertMatches(hasNoLore(), itemMeta);
		}

	}

	@Nested
	class HasLore
	{

		@BeforeEach
		void setUp()
		{
			itemMeta.setLore(List.of("Hello", "world!"));
		}

		@Test
		void matchesLegacyStrings()
		{
			assertMatches(hasLore("Hello", "world!"), itemMeta);
		}

		@Test
		void matchesComponents()
		{
			assertMatches(hasLore(Component.text("Hello"), Component.text("world!")), itemMeta);
		}

		@Test
		void matchesComponentList()
		{
			assertMatches(hasLore(List.of(Component.text("Hello"), Component.text("world!"))), itemMeta);
		}

	}

	@Nested
	class DoesNotHaveLore
	{

		@BeforeEach
		void setUp()
		{
			itemMeta.setLore(List.of("Hello", "world!"));
		}

		@Test
		void matchesLegacyStrings()
		{
			assertMatches(doesNotHaveLore("Not the lore"), itemMeta);
		}

		@Test
		void matchesComponents()
		{
			assertMatches(doesNotHaveLore(Component.text("Not the lore")), itemMeta);
		}

		@Test
		void matchesComponentList()
		{
			assertMatches(doesNotHaveLore(List.of(Component.text("Not the lore"))), itemMeta);
		}

	}

	// ===================================================================================
	// Plugin
	// ===================================================================================

	@Nested
	class HasFiredEventInstance
	{

		@Test
		void matches()
		{
			PluginManagerMock pluginManager = serverMock.getPluginManager();
			pluginManager.callEvent(new FacadeTestEvent());
			assertMatches(hasFiredEventInstance(FacadeTestEvent.class), pluginManager);
		}

	}

	@Nested
	class HasNotFiredEventInstance
	{

		@Test
		void matches()
		{
			PluginManagerMock pluginManager = serverMock.getPluginManager();
			pluginManager.callEvent(new FacadeTestEvent());
			assertMatches(hasNotFiredEventInstance(UnfiredEvent.class), pluginManager);
		}

	}

	@Nested
	class HasFiredFilteredEvent
	{

		@Test
		void matches()
		{
			PluginManagerMock pluginManager = serverMock.getPluginManager();
			pluginManager.callEvent(new FacadeTestEvent());
			assertMatches(hasFiredFilteredEvent(FacadeTestEvent.class, event -> true), pluginManager);
		}

	}

	@Nested
	class HasNotFiredFilteredEvent
	{

		@Test
		void matches()
		{
			PluginManagerMock pluginManager = serverMock.getPluginManager();
			pluginManager.callEvent(new FacadeTestEvent());
			assertMatches(hasNotFiredFilteredEvent(FacadeTestEvent.class, event -> false), pluginManager);
		}

	}

	// ===================================================================================
	// Scheduler
	// ===================================================================================

	@Nested
	class HasNoOverdueTasks
	{

		@Test
		void matches()
		{
			scheduler.saveOverdueTasks();
			assertMatches(hasNoOverdueTasks(), scheduler);
		}

	}

	@Nested
	class HasOverdueTasks
	{

		@Test
		void matches() throws InterruptedException
		{
			CountDownLatch tasksSaved = new CountDownLatch(1);
			CountDownLatch taskStarted = new CountDownLatch(1);
			scheduler.runTaskAsynchronously(null, () ->
			{
				try
				{
					taskStarted.countDown();
					tasksSaved.await();
				}
				catch (InterruptedException ignored)
				{
					// Code will end after reaching this point, therefore no-op
				}
			});
			taskStarted.await();
			scheduler.saveOverdueTasks();
			tasksSaved.countDown();
			assertMatches(hasOverdueTasks(), scheduler);
		}

	}

	// ===================================================================================
	// Sound
	// ===================================================================================

	@Nested
	class HasHeard
	{

		@BeforeEach
		void setUp()
		{
			primeHeardSounds();
		}

		@Test
		void matchesAdventureSoundWithFilter()
		{
			assertMatches(hasHeard(heardAdventureSound(), experience -> true), soundReceiver);
		}

		@Test
		void matchesAdventureSound()
		{
			assertMatches(hasHeard(heardAdventureSound()), soundReceiver);
		}

		@Test
		void matchesSoundKeyWithFilter()
		{
			assertMatches(hasHeard(CUSTOM_SOUND_KEY, experience -> true), soundReceiver);
		}

		@Test
		void matchesSoundKey()
		{
			assertMatches(hasHeard(CUSTOM_SOUND_KEY), soundReceiver);
		}

		@Test
		void matchesBukkitSoundWithFilter()
		{
			assertMatches(hasHeard(org.bukkit.Sound.BLOCK_ANVIL_BREAK, experience -> true), soundReceiver);
		}

		@Test
		void matchesBukkitSound()
		{
			assertMatches(hasHeard(org.bukkit.Sound.BLOCK_ANVIL_BREAK), soundReceiver);
		}

	}

	@Nested
	class HasNotHeard
	{

		@BeforeEach
		void setUp()
		{
			primeHeardSounds();
		}

		@Test
		void matchesAdventureSound()
		{
			assertMatches(hasNotHeard(unheardAdventureSound()), soundReceiver);
		}

		@Test
		void matchesSoundKeyWithFilter()
		{
			assertMatches(hasNotHeard("missing.sound", experience -> true), soundReceiver);
		}

		@Test
		void matchesSoundKey()
		{
			assertMatches(hasNotHeard("missing.sound"), soundReceiver);
		}

		@Test
		void matchesBukkitSoundWithFilter()
		{
			assertMatches(hasNotHeard(org.bukkit.Sound.BLOCK_ANVIL_FALL, experience -> true), soundReceiver);
		}

		@Test
		void matchesBukkitSound()
		{
			assertMatches(hasNotHeard(org.bukkit.Sound.BLOCK_ANVIL_FALL), soundReceiver);
		}

	}

	/**
	 * A custom event with a stable constructor so the plugin-manager delegates can be exercised without depending on
	 * the constructor of a real Bukkit event.
	 */
	static class FacadeTestEvent extends Event
	{

		private static final HandlerList HANDLERS = new HandlerList();

		@Override
		public @NotNull HandlerList getHandlers()
		{
			return HANDLERS;
		}

		public static @NotNull HandlerList getHandlerList()
		{
			return HANDLERS;
		}

	}

	/**
	 * A custom event that is never fired, used as the reference for the "has not fired" plugin-manager delegates.
	 * Real Bukkit events such as player login events are fired as a side effect of adding players during setup, so
	 * they cannot be relied upon to be absent.
	 */
	static class UnfiredEvent extends Event
	{

		private static final HandlerList HANDLERS = new HandlerList();

		@Override
		public @NotNull HandlerList getHandlers()
		{
			return HANDLERS;
		}

		public static @NotNull HandlerList getHandlerList()
		{
			return HANDLERS;
		}

	}

}
