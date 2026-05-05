package org.mockbukkit.mockbukkit.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.view.AnvilView;
import org.bukkit.inventory.view.BeaconView;
import org.bukkit.inventory.view.FurnaceView;
import org.bukkit.inventory.view.MerchantView;
import org.bukkit.inventory.view.builder.InventoryViewBuilder;
import org.bukkit.inventory.view.builder.MerchantInventoryViewBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class MenuTypeMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Nested
	class GetKey
	{

		@Test
		void anvil()
		{
			MenuType menuType = Registry.MENU.get(NamespacedKey.minecraft("anvil"));
			assertNotNull(menuType);
			assertEquals(NamespacedKey.minecraft("anvil"), menuType.getKey());
		}

		@Test
		void generic9x3()
		{
			MenuType menuType = Registry.MENU.get(NamespacedKey.minecraft("generic_9x3"));
			assertNotNull(menuType);
			assertEquals(NamespacedKey.minecraft("generic_9x3"), menuType.getKey());
		}

		@Test
		void furnace()
		{
			MenuType menuType = Registry.MENU.get(NamespacedKey.minecraft("furnace"));
			assertNotNull(menuType);
			assertEquals(NamespacedKey.minecraft("furnace"), menuType.getKey());
		}

	}

	@Nested
	class Create
	{

		@Test
		void createChest()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X3.create(player, Component.text("Test Chest"));
			assertNotNull(view);
			assertEquals(InventoryType.CHEST, view.getTopInventory().getType());
			assertEquals(27, view.getTopInventory().getSize());
			assertEquals("Test Chest", view.getTitle());
		}

		@Test
		void createSmallChest()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X1.create(player, Component.text("Small Chest"));
			assertNotNull(view);
			assertEquals(InventoryType.CHEST, view.getTopInventory().getType());
			assertEquals(9, view.getTopInventory().getSize());
		}

		@Test
		void createLargeChest()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X6.create(player, Component.text("Large Chest"));
			assertNotNull(view);
			assertEquals(InventoryType.CHEST, view.getTopInventory().getType());
			assertEquals(54, view.getTopInventory().getSize());
		}

		@Test
		void createAnvil()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.ANVIL.create(player, Component.text("Test Anvil"));
			assertNotNull(view);
			assertEquals(InventoryType.ANVIL, view.getTopInventory().getType());
		}

		@Test
		void createFurnace()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.FURNACE.create(player, Component.text("Test Furnace"));
			assertNotNull(view);
			assertEquals(InventoryType.FURNACE, view.getTopInventory().getType());
		}

		@Test
		void createWithStringTitle()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X3.create(player, "String Title");
			assertNotNull(view);
			assertEquals("String Title", view.getTitle());
		}

		@Test
		void createHopper()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.HOPPER.create(player, Component.text("Test Hopper"));
			assertNotNull(view);
			assertEquals(InventoryType.HOPPER, view.getTopInventory().getType());
		}

	}

	@Nested
	class TypedMethod
	{

		@Test
		void typedReturnsSelf()
		{
			MenuType.Typed<InventoryView, InventoryViewBuilder<InventoryView>> typed = MenuType.GENERIC_9X3.typed();
			assertNotNull(typed);
		}

		@Test
		void typedWithCompatibleClass()
		{
			MenuType.Typed<?, ?> typed = MenuType.ANVIL.typed(InventoryView.class);
			assertNotNull(typed);
			assertSame(MenuType.ANVIL, typed);
		}

		@Test
		void typedWithExactClass()
		{
			MenuType.Typed<?, ?> typed = MenuType.ANVIL.typed(AnvilView.class);
			assertNotNull(typed);
			assertSame(MenuType.ANVIL, typed);
		}

		@Test
		void typedWithIncompatibleClassThrows()
		{
			assertThrows(IllegalArgumentException.class, () -> MenuType.ANVIL.typed(BeaconView.class));
		}

	}

	@Nested
	class GetInventoryViewClass
	{

		@Test
		void anvil()
		{
			assertEquals(AnvilView.class, MenuType.ANVIL.getInventoryViewClass());
		}

		@Test
		void genericChest()
		{
			assertEquals(InventoryView.class, MenuType.GENERIC_9X3.getInventoryViewClass());
		}

		@Test
		void furnace()
		{
			assertEquals(FurnaceView.class, MenuType.FURNACE.getInventoryViewClass());
		}

	}

	@Nested
	class Builder
	{

		@Test
		void builderNotNull()
		{
			InventoryViewBuilder<InventoryView> builder = MenuType.GENERIC_9X3.builder();
			assertNotNull(builder);
		}

		@Test
		void buildWithTitle()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X3.builder()
					.title(Component.text("Builder Title"))
					.build(player);
			assertNotNull(view);
			assertEquals("Builder Title", view.getTitle());
			assertEquals(InventoryType.CHEST, view.getTopInventory().getType());
		}

		@Test
		void buildWithoutTitle()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X3.builder()
					.build(player);
			assertNotNull(view);
		}

		@Test
		void copyPreservesTitle()
		{
			InventoryViewBuilder<InventoryView> original = MenuType.GENERIC_9X3.builder()
					.title(Component.text("Original Title"));
			InventoryViewBuilder<InventoryView> copy = original.copy();
			assertNotSame(original, copy);

			HumanEntity player = server.addPlayer();
			InventoryView view = copy.build(player);
			assertEquals("Original Title", view.getTitle());
		}

	}

	@Nested
	class BaseBuilder
	{

		@Test
		void builderNotNull()
		{
			InventoryViewBuilder<InventoryView> builder = MenuType.GENERIC_9X1.builder();
			assertNotNull(builder);
		}

		@Test
		void buildWithTitle()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X1.builder()
					.title(Component.text("Base Title"))
					.build(player);
			assertNotNull(view);
			assertEquals("Base Title", view.getTitle());
		}

		@Test
		void buildWithoutTitle()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X1.builder()
					.build(player);
			assertNotNull(view);
		}

		@Test
		void copyPreservesTitle()
		{
			InventoryViewBuilder<InventoryView> original = MenuType.GENERIC_9X1.builder()
					.title(Component.text("Copy Title"));
			InventoryViewBuilder<InventoryView> copy = original.copy();
			assertNotSame(original, copy);

			HumanEntity player = server.addPlayer();
			InventoryView view = copy.build(player);
			assertEquals("Copy Title", view.getTitle());
		}

		@Test
		void buildCorrectTypeAndSize()
		{
			HumanEntity player = server.addPlayer();
			InventoryView view = MenuType.GENERIC_9X1.builder()
					.title(Component.text("Size Test"))
					.build(player);
			assertEquals(InventoryType.CHEST, view.getTopInventory().getType());
			assertEquals(9, view.getTopInventory().getSize());
		}

	}

	@Nested
	class MerchantBuilder
	{

		@Test
		void builderNotNull()
		{
			MerchantInventoryViewBuilder<MerchantView> builder = MenuType.MERCHANT.builder();
			assertNotNull(builder);
		}

		@Test
		void titleReturnsSelf()
		{
			MerchantInventoryViewBuilder<MerchantView> builder = MenuType.MERCHANT.builder();
			assertSame(builder, builder.title(Component.text("Merchant Title")));
		}

		@Test
		void copyReturnsDifferentInstance()
		{
			MerchantInventoryViewBuilder<MerchantView> original = MenuType.MERCHANT.builder()
					.title(Component.text("Merchant Copy"));
			MerchantInventoryViewBuilder<MerchantView> copy = original.copy();
			assertNotNull(copy);
			assertNotSame(original, copy);
		}

		@Test
		void merchantThrowsUnimplemented()
		{
			MerchantInventoryViewBuilder<MerchantView> builder = MenuType.MERCHANT.builder();
			assertThrows(UnimplementedOperationException.class, () -> builder.merchant(null));
		}

		@Test
		void checkReachableThrowsUnimplemented()
		{
			MerchantInventoryViewBuilder<MerchantView> builder = MenuType.MERCHANT.builder();
			assertThrows(UnimplementedOperationException.class, () -> builder.checkReachable(true));
		}

	}

}
