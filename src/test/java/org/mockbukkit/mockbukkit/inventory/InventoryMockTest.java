package org.mockbukkit.mockbukkit.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.inventory.InventoryItemAmountMatcher.containsAtLeast;
import static org.mockbukkit.mockbukkit.matcher.inventory.InventoryItemAmountMatcher.containsLessThan;
import static org.mockbukkit.mockbukkit.matcher.inventory.ItemSimilarityMatcher.similarTo;

class InventoryMockTest
{

	ServerMock server;
	private InventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		inventory = new InventoryMock(null, 9, InventoryType.CHEST);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_SetsSize()
	{
		assertEquals(9, new InventoryMock(null, 9, InventoryType.CHEST).getSize());
		assertEquals(18, new InventoryMock(null, 18, InventoryType.CHEST).getSize());
	}

	@Test
	void constructor_SetsSizeTooSmall()
	{
		assertThrows(IllegalArgumentException.class, () -> new InventoryMock(null, -1, InventoryType.CHEST));
	}

	@Test
	void constructor_SetsSizeTwoParamConstructor()
	{
		assertEquals(10, new InventoryMock(null, InventoryType.WORKBENCH).getSize());
	}

	@Test
	void constructor_SetsType()
	{
		assertEquals(InventoryType.CHEST, new InventoryMock(null, 9, InventoryType.CHEST).getType());
		assertEquals(InventoryType.DROPPER, new InventoryMock(null, 9, InventoryType.DROPPER).getType());
	}

	@Test
	void getItem_Default_AllNull()
	{
		for (int i = 0; i < inventory.getSize(); i++)
		{
			ItemStack item = inventory.getItem(i);
			assertNull(item);
		}
	}

	@Test
	void testClearInventory()
	{
		for (int i = 0; i < inventory.getSize(); i++)
		{
			inventory.addItem(new ItemStackMock(Material.DIRT, 64));
		}

		inventory.clear();

		for (int i = 0; i < inventory.getSize(); i++)
		{
			ItemStack item = inventory.getItem(i);
			assertNull(item);
		}
	}

	@Test
	void testClearSlot()
	{
		inventory.setItem(0, new ItemStackMock(Material.DIAMOND));
		assertEquals(Material.DIAMOND, inventory.getItem(0).getType());

		inventory.clear(0);
		assertNull(inventory.getItem(0));
	}

	@Test
	void testFirstEmpty()
	{
		for (int i = 0; i < inventory.getSize(); i++)
		{
			inventory.addItem(new ItemStackMock(Material.DIRT, 64));
		}

		assertEquals(-1, inventory.firstEmpty());
		inventory.clear();
		assertEquals(0, inventory.firstEmpty());
	}

	@Test
	void addItem_EmptyInventoryAddsOneStack_OneStackUsed()
	{
		ItemStack stack = new ItemStackMock(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(stack);
		assertNull(remaining);
		ItemStack stored = inventory.getItem(0);
		assertEquals(64, stored.getAmount());
		ItemStack next = inventory.getItem(1);
		assertNull(next);
	}

	@Test
	void addItem_FullInventoryAddsOneStack_NothingAdded()
	{
		ItemStack filler = new ItemStackMock(Material.COBBLESTONE, 1);
		for (int i = 0; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}

		ItemStack stack = new ItemStackMock(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(stack);
		assertEquals(64, remaining.getAmount());

		for (ItemStack stored : inventory.getContents())
		{
			assertEquals(1, stored.getAmount());
			assertEquals(Material.COBBLESTONE, stored.getType());
		}
	}

	@Test
	void addItem_PartiallyFilled_AddsOneStack_HalfAdded()
	{
		ItemStack filler = new ItemStackMock(Material.COBBLESTONE, 1);
		for (int i = 2; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}
		ItemStack preset = new ItemStackMock(Material.DIRT, 48);
		inventory.setItem(0, preset);
		inventory.setItem(2, preset);
		preset.setAmount(64);
		inventory.setItem(1, preset);

		ItemStack store = new ItemStackMock(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(store);
		assertNotNull(remaining);
		assertEquals(32, remaining.getAmount());
	}

	@Test
	void addItem_MultipleItems_ItemsAddedCorrectly()
	{
		ItemStack filler = new ItemStackMock(Material.COBBLESTONE, 1);
		for (int i = 1; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}

		ItemStack preset = new ItemStackMock(Material.DIRT, 48);
		inventory.setItem(0, preset);

		ItemStack item1 = new ItemStackMock(Material.COBBLESTONE, 64);
		ItemStack item2 = new ItemStackMock(Material.DIRT, 64);
		HashMap<Integer, ItemStack> map = inventory.addItem(item1, item2);
		assertEquals(1, map.size());
		assertTrue(map.containsKey(1));
		assertEquals(48, map.get(1).getAmount());
	}

	@Test
	void setContents_OneItemAndOneNull_SetAndRestCleared()
	{
		ItemStack filler = new ItemStackMock(Material.COBBLESTONE, 1);
		for (int i = 1; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}

		ItemStack item = new ItemStackMock(Material.DIRT, 32);

		inventory.setContents(new ItemStack[]{ item });

		assertTrue(item.isSimilar(inventory.getItem(0)));
		for (int i = 1; i < inventory.getSize(); i++)
		{
			ItemStack emptyItem = inventory.getItem(i);
			assertNull(emptyItem);
		}
	}

	@Test
	void setContents_ArrayWithNulls_NullsIgnores()
	{
		assertDoesNotThrow(() -> inventory.setContents(new ItemStack[]{ null }));
	}

	@Test
	void iterator_SeveralItems_IteratorsOverItems()
	{
		ItemStack item1 = new ItemStackMock(Material.COBBLESTONE, 64);
		ItemStack item2 = new ItemStackMock(Material.DIRT, 64);
		inventory.addItem(item1, item2);

		ListIterator<ItemStack> iterator = inventory.iterator();
		assertEquals(item1, iterator.next());
		assertEquals(item2, iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	void assertTrueForAll_ChecksIfNullOnEmptyInventory_DoesNotAssert()
	{
		assertThat(inventory.stream().toList(), empty());
	}

	@Test
	void assertTrueForAll_ChecksIfNullOnNonEmptyInventory_Asserts()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 1));
		assertThat(inventory.stream().toList(), not(empty()));
	}

	@Test
	void assertTrueForNonNulls_NumberOfExecutionsOnInventoryOneItem_EqualToOne()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 1));
		assertThat(inventory.stream().toList(), hasSize(1));
	}

	@Test
	void assertTrueForSome_OneItemMeetsCondition_DoesNotAssert()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 1));
		assertThat(inventory.stream().map(ItemStack::getAmount).toList(), hasItem(greaterThan(0)));
	}

	@Test
	void assertTrueForSome_NoItemsMeetCondition_Asserts()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 1));
		assertThat(inventory.stream().map(ItemStack::getAmount).toList(), not(hasItem(greaterThan(16))));
	}

	@Test
	void assertContainsAny_ContainsThem_DoesNotAssert()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 16));
		assertThat(inventory, hasItem(similarTo(new ItemStack(Material.DIRT))));
	}

	@Test
	void assertContainsAny_DoesNotContainThem_Asserts()
	{
		inventory.addItem(new ItemStackMock(Material.SHORT_GRASS, 16));
		ItemStack item = new ItemStackMock(Material.DIRT);
		assertThat(inventory, not(hasItem(similarTo(item))));
	}

	@Test
	void assertContainsAtLeast_ContainsExactly_DoesNotAssert()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 4));
		assertThat(inventory, containsAtLeast(Material.DIRT, 4));
	}

	@Test
	void assertContainsAtLeast_ContainsMore_DoesNotAssert()
	{
		inventory.addItem(new ItemStackMock(Material.DIRT, 8));
		assertThat(inventory, containsAtLeast(Material.DIRT, 4));
	}

	@Test
	void assertContainsAtLeast_DoesNotContainEnough_Asserts()
	{
		inventory.addItem(new ItemStackMock(Material.SHORT_GRASS, 3));
		assertThat(inventory, containsLessThan(Material.DIRT, 4));
	}

	@Test
	void testContentsAndStorageContentsEqual()
	{
		assertArrayEquals(inventory.getContents(), inventory.getStorageContents());
	}

	@Test
	void testContainsItemStack()
	{
		inventory.addItem(new ItemStackMock(Material.STONE));
		assertTrue(inventory.contains(new ItemStackMock(Material.STONE)));
	}

	@Test
	void testContainsItemStackAmount()
	{
		inventory.addItem(new ItemStackMock(Material.STONE, 2));
		assertTrue(inventory.contains(new ItemStackMock(Material.STONE), 2));
	}

	@Test
	void testContainsItemStackFalse()
	{
		inventory.addItem(new ItemStackMock(Material.SHORT_GRASS));
		assertFalse(inventory.contains(new ItemStackMock(Material.STONE)));
	}

	@Test
	void testContainsMaterial()
	{
		inventory.addItem(new ItemStackMock(Material.STONE));
		assertTrue(inventory.contains(Material.STONE));
	}

	@Test
	void testContainsMaterialAmount()
	{
		inventory.addItem(new ItemStackMock(Material.STONE, 2));
		assertTrue(inventory.contains(Material.STONE, 2));
	}

	@Test
	void testContainsMaterialFalse()
	{
		inventory.addItem(new ItemStackMock(Material.SHORT_GRASS));
		assertFalse(inventory.contains(Material.STONE));
	}

	@Test
	void testContainsAtLeast()
	{
		inventory.addItem(new ItemStackMock(Material.STONE, 3));
		assertTrue(inventory.containsAtLeast(new ItemStackMock(Material.STONE), 3));
	}

	@Test
	void testContainsAtLeastExtra()
	{
		inventory.addItem(new ItemStackMock(Material.STONE, 6));
		assertTrue(inventory.containsAtLeast(new ItemStackMock(Material.STONE), 3));
	}

	@Test
	void testContainsAtLeastFalse()
	{
		inventory.addItem(new ItemStackMock(Material.STONE));
		assertFalse(inventory.containsAtLeast(new ItemStackMock(Material.STONE), 3));
	}

	@Test
	void getMaxStackSize_ReturnsExpected()
	{
		assertEquals(64, inventory.getMaxStackSize());

		inventory.setMaxStackSize(15);
		assertEquals(15, inventory.getMaxStackSize());
	}

	@Test
	void addItem_setMaxStackSize_EmptyInventoryAddsOneStack_OneStackUsed()
	{
		inventory.setMaxStackSize(30);
		ItemStack stack = new ItemStackMock(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(stack);
		assertNull(remaining);
		assertEquals(30, inventory.getItem(0).getAmount());
		assertEquals(30, inventory.getItem(1).getAmount());
		assertEquals(4, inventory.getItem(2).getAmount());
		assertNull(inventory.getItem(3));
	}

	@Test
	void addItem_setMaxStackSize_PartiallyFilled_AddsOneStack_HalfAdded()
	{
		ItemStack filler = new ItemStackMock(Material.COBBLESTONE, 1);
		for (int i = 3; i < inventory.getSize(); i++)
		{
			inventory.setItem(i, filler);
		}
		inventory.setMaxStackSize(32);
		inventory.setItem(0, new ItemStackMock(Material.DIRT, 20));
		inventory.setItem(1, new ItemStackMock(Material.DIRT, 32));
		inventory.setItem(2, new ItemStackMock(Material.DIRT, 20));

		ItemStack store = new ItemStackMock(Material.DIRT, 64);
		ItemStack remaining = inventory.addItem(store);
		assertNotNull(remaining);
		assertEquals(40, remaining.getAmount());
	}

	@Test
	void testAll_Material()
	{
		inventory.setItem(0, new ItemStackMock(Material.STONE));
		HashMap<Integer, ? extends ItemStack> all = inventory.all(Material.STONE);
		assertEquals(Material.STONE, all.get(0).getType());
	}

	@Test
	void testAll_ItemStack()
	{
		inventory.setItem(0, new ItemStackMock(Material.STONE));
		inventory.setItem(1, new ItemStackMock(Material.STONE, 2));
		HashMap<Integer, ? extends ItemStack> all = inventory.all(new ItemStackMock(Material.STONE, 2));
		assertEquals(Material.STONE, all.get(1).getType());
		assertEquals(2, all.get(1).getAmount());
	}

	@Test
	void testFirst_Material()
	{
		inventory.setItem(0, new ItemStackMock(Material.DIRT));
		inventory.setItem(1, new ItemStackMock(Material.STONE));
		assertEquals(1, inventory.first(Material.STONE));
	}

	@Test
	void testFirst_ItemStack()
	{
		inventory.clear(0);
		inventory.setItem(1, new ItemStackMock(Material.DIRT));
		inventory.setItem(2, new ItemStackMock(Material.STONE, 2));
		assertEquals(2, inventory.first(new ItemStackMock(Material.STONE, 2)));
	}

	@Test
	void testRemove_Material()
	{
		inventory.setItem(0, new ItemStackMock(Material.STONE));
		inventory.remove(Material.STONE);
		assertNull(inventory.getItem(0));
	}

	@Test
	void testRemove_ItemStack()
	{
		inventory.setItem(0, new ItemStackMock(Material.STONE));
		inventory.setItem(1, new ItemStackMock(Material.STONE, 2));
		inventory.remove(new ItemStackMock(Material.STONE, 2));
		assertEquals(Material.STONE, inventory.getItem(0).getType());
		assertNull(inventory.getItem(1));
	}

	@Test
	void testGetViewersDefault()
	{
		assertEquals(0, inventory.getViewers().size());
	}

	@Test
	void testAddViewer()
	{
		Player player = server.addPlayer();
		inventory.addViewer(player);
		assertEquals(1, inventory.getViewers().size());
		assertTrue(inventory.getViewers().contains(player));
	}

	@Test
	void testNullViewerThrowsException()
	{
		assertThrows(NullPointerException.class, () -> inventory.addViewer(null));
	}

	@Test
	void testRemoveViewer()
	{
		Player player = server.addPlayer();
		inventory.addViewer(player);
		assertTrue(inventory.getViewers().contains(player));
		inventory.removeViewer(player);
		assertEquals(0, inventory.getViewers().size());
		assertFalse(inventory.getViewers().contains(player));
	}

	@Test
	void testAddMultipleViewersList()
	{
		List<HumanEntity> players = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			players.add(server.addPlayer());
		}
		inventory.addViewers(players);

		assertEquals(10, inventory.getViewers().size());
		for (HumanEntity player : players)
		{
			assertTrue(inventory.getViewers().contains(player));
		}
	}

	@Test
	void testAddMultipleViewersListWithNullEntries()
	{
		List<HumanEntity> players = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			players.add(server.addPlayer());
		}
		players.add(null);

		assertThrows(NullPointerException.class, () -> inventory.addViewers(players));
	}

	@Test
	void testAddMultipleViewersVarargs()
	{
		Player player1 = server.addPlayer();
		Player player2 = server.addPlayer();
		Player player3 = server.addPlayer();

		inventory.addViewers(player1, player2, player3);

		assertEquals(3, inventory.getViewers().size());
		assertTrue(inventory.getViewers().contains(player1));
		assertTrue(inventory.getViewers().contains(player2));
		assertTrue(inventory.getViewers().contains(player3));
	}

	@Test
	void testAddMultipleViewersVarargsWithNullEntries()
	{
		Player player1 = server.addPlayer();
		Player player2 = server.addPlayer();
		Player player3 = server.addPlayer();

		assertThrows(NullPointerException.class, () -> inventory.addViewers(player1, player2, null, player3));
	}

	@Test
	void testOpenInventoryAddViewers()
	{
		Player player = server.addPlayer();
		player.openInventory(inventory);

		assertEquals(1, inventory.getViewers().size());
		assertTrue(inventory.getViewers().contains(player));
	}

	@Test
	void closeInventoryRemoveViewer()
	{
		Player player = server.addPlayer();
		player.openInventory(inventory);
		assertTrue(inventory.getViewers().contains(player));
		player.closeInventory();

		assertEquals(0, inventory.getViewers().size());
		assertFalse(inventory.getViewers().contains(player));
	}

	@Test
	void testClose()
	{
		Player player = server.addPlayer();
		player.openInventory(inventory);
		assertEquals(1, inventory.getViewers().size());
		assertEquals(1, inventory.close());
		assertEquals(0, inventory.getViewers().size());
		assertNotSame(inventory, player.getOpenInventory().getTopInventory());
	}

	@Test
	void removeItem_ShouldOnlyRemoveSimilarItems()
	{

		ItemStack diamond = new ItemStackMock(Material.DIAMOND);
		ItemStack emerald = new ItemStackMock(Material.EMERALD);

		inventory.addItem(emerald);

		HashMap<Integer, ItemStack> result = inventory.removeItem(diamond);

		assertEquals(1, result.values().size());
		assertEquals(0, inventory.getNumberOfItems(diamond));
		assertEquals(1, inventory.getNumberOfItems(emerald));
	}

	@Test
	void removeItem_ShouldOnlyRemoveTheCorrectAmount()
	{
		ItemStack diamond = new ItemStackMock(Material.DIAMOND);
		ItemStack emerald = new ItemStackMock(Material.EMERALD);

		// Add 2x diamonds and 1x emerald
		inventory.addItem(diamond, diamond);
		inventory.addItem(emerald);

		HashMap<Integer, ItemStack> result = inventory.removeItem(diamond);

		assertTrue(result.isEmpty());
		assertEquals(1, inventory.getNumberOfItems(diamond));
		assertEquals(1, inventory.getNumberOfItems(emerald));
	}

	@Test
	void removeItem_WhenInExcessive()
	{
		ItemStack diamond = new ItemStackMock(Material.DIAMOND);
		ItemStack emerald = new ItemStackMock(Material.EMERALD);

		inventory.addItem(diamond);
		inventory.addItem(emerald);

		HashMap<Integer, ItemStack> result = inventory.removeItem(diamond, diamond);

		assertEquals(1, result.values().size());
		assertEquals(0, inventory.getNumberOfItems(diamond));
		assertEquals(1, inventory.getNumberOfItems(emerald));
	}

	@Test
	void getLocation_WithEntityHolder()
	{
		Player player = server.addPlayer();
		Location expectedLocation = new Location(player.getWorld(), 10, 20, 30);
		player.teleport(expectedLocation);

		inventory = new InventoryMock(player, InventoryType.CHEST);
		Location location = inventory.getLocation();

		assertNotNull(location);
		assertEquals(expectedLocation, location);
	}

	@Test
	void getLocation_WithoutEntityHolder_WithNoWorldCreatedYet()
	{
		Location location = inventory.getLocation();

		assertNotNull(location);
		assertEquals(Bukkit.getWorlds().getFirst().getSpawnLocation(), location);
	}

	@Test
	void getLocation_WithoutEntityHolder_WithWorldCreated()
	{
		World world = server.addSimpleWorld("world");
		world.setSpawnLocation(1, 2, 3);

		Location location = inventory.getLocation();

		assertNotNull(location);
		assertEquals(new Location(world, 1, 2, 3), location);
	}

	@Test
	void getLocation_WithNonEntityHolder()
	{
		Player player = server.addPlayer();
		final Location expectedLocation = new Location(player.getWorld(), 10, 20, 30);
		player.teleport(expectedLocation);
		inventory = new InventoryMock(player, InventoryType.PLAYER);

		assertEquals(expectedLocation, inventory.getLocation());
	}

	@Nested
	class SetCustomTitle
	{

		@ParameterizedTest
		@EnumSource(InventoryType.class)
		void givenNullTitle(InventoryType type)
		{
			InventoryMock customInventory = new InventoryMock(null, type);
			assertEquals(type.defaultTitle(), customInventory.getTitle());
			assertNull(customInventory.getCustomTitle());

			customInventory.setCustomTitle(null);
			assertEquals(type.defaultTitle(), customInventory.getTitle());
			assertNull(customInventory.getCustomTitle());
		}

		@ParameterizedTest
		@EnumSource(InventoryType.class)
		void givenCustomTitle(InventoryType type)
		{
			InventoryMock customInventory = new InventoryMock(null, type);
			assertEquals(type.defaultTitle(), customInventory.getTitle());
			assertNull(customInventory.getCustomTitle());

			customInventory.setCustomTitle(Component.text("This is a custom title"));
			assertEquals(Component.text("This is a custom title"), customInventory.getTitle());
			assertEquals(Component.text("This is a custom title"), customInventory.getCustomTitle());

			customInventory.setCustomTitle(null);
			assertEquals(type.defaultTitle(), customInventory.getTitle());
			assertNull(customInventory.getCustomTitle());
		}

	}

	@Nested
	class IsIdentical {

		@MockBukkitInject
		private ServerMock serverMock;

		@Test
		void givenItemChanges() {

			InventoryMock inventoryA = new InventoryMock(null, InventoryType.CHEST);
			InventoryMock inventoryB = new InventoryMock(null, InventoryType.CHEST);
			assertIsIdentical(inventoryA, inventoryB);

			inventoryA.addItem(ItemStack.of(Material.DIAMOND));
			assertIsNotIdentical(inventoryA, inventoryB);

			inventoryB.addItem(ItemStack.of(Material.DIAMOND));
			assertIsIdentical(inventoryA, inventoryB);

			inventoryA.addItem(ItemStack.of(Material.DIAMOND));
			assertIsNotIdentical(inventoryA, inventoryB);
		}

		@Test
		void givenDifferentTypes() {

			InventoryMock inventoryA = new InventoryMock(null, InventoryType.DROPPER);
			InventoryMock inventoryB = new InventoryMock(null, InventoryType.CHEST);
			assertIsNotIdentical(inventoryA, inventoryB);
		}

		@Test
		void givenDifferentMaxSizes() {

			InventoryMock inventoryA = new InventoryMock(null, InventoryType.CHEST);
			InventoryMock inventoryB = new InventoryMock(null, InventoryType.CHEST);
			assertIsIdentical(inventoryA, inventoryB);

			inventoryA.setMaxStackSize(1);
			inventoryB.setMaxStackSize(2);
			assertIsNotIdentical(inventoryA, inventoryB);

			inventoryA.setMaxStackSize(5);
			inventoryB.setMaxStackSize(5);
			assertIsIdentical(inventoryA, inventoryB);
		}

		@Test
		void givenDifferentHolders() {

			Player player = server.addPlayer();

			InventoryMock inventoryA = new InventoryMock(null, InventoryType.CHEST);
			InventoryMock inventoryB = new InventoryMock(null, InventoryType.CHEST);
			InventoryMock inventoryC = new InventoryMock(player, InventoryType.CHEST);

			assertIsIdentical(inventoryA, inventoryB);
			assertIsNotIdentical(inventoryB, inventoryC);
		}

		@Test
		void givenDifferentTitles() {

			InventoryMock inventoryA = new InventoryMock(null, InventoryType.CHEST);
			InventoryMock inventoryB = new InventoryMock(null, InventoryType.CHEST);

			assertIsIdentical(inventoryA, inventoryB);

			inventoryA.setCustomTitle(Component.text("This is a custom title"));
			assertIsNotIdentical(inventoryA, inventoryB);
		}

		public static void assertIsIdentical(@Nullable InventoryMock inventoryA, @Nullable InventoryMock inventoryB) {
			if (inventoryA == null) {
				assertNull(inventoryB);
			} else
			{
				assertTrue(inventoryA.isIdentical(inventoryB));
			}
		}

		public static void assertIsNotIdentical(@Nullable InventoryMock inventoryA, @Nullable InventoryMock inventoryB) {
			if (inventoryA == null) {
				assertNotNull(inventoryB);
			} else
			{
				assertFalse(inventoryA.isIdentical(inventoryB));
			}
		}

	}

}
