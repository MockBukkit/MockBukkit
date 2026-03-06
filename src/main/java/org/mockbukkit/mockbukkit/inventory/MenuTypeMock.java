package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.view.AnvilView;
import org.bukkit.inventory.view.BeaconView;
import org.bukkit.inventory.view.BrewingStandView;
import org.bukkit.inventory.view.CrafterView;
import org.bukkit.inventory.view.EnchantmentView;
import org.bukkit.inventory.view.FurnaceView;
import org.bukkit.inventory.view.LecternView;
import org.bukkit.inventory.view.LoomView;
import org.bukkit.inventory.view.MerchantView;
import org.bukkit.inventory.view.StonecutterView;
import org.bukkit.inventory.view.builder.InventoryViewBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.Map;

/**
 * Mock implementation of {@link MenuType.Typed}.
 *
 * @param <V> The type of {@link InventoryView} this menu type creates.
 * @param <B> The type of {@link InventoryViewBuilder} this menu type uses.
 */
public class MenuTypeMock<V extends InventoryView, B extends InventoryViewBuilder<V>> implements MenuType.Typed<V, B>
{

	enum BuilderType
	{
		BASE, LOCATION, MERCHANT
	}

	/**
	 * Stores metadata for each menu type. Uses String for inventoryTypeName instead of
	 * InventoryType directly to avoid a circular class initialization dependency:
	 * MenuTypeMock clinit -> InventoryType clinit -> MenuType clinit -> Registry.MENU -> MenuTypeMock.from()
	 */
	record MenuTypeData(
			Class<? extends InventoryView> viewClass,
			String inventoryTypeName,
			int inventorySize,
			BuilderType builderType
	)
	{

		InventoryType inventoryType()
		{
			return InventoryType.valueOf(inventoryTypeName);
		}

	}

	private static final Map<String, MenuTypeData> MENU_TYPE_DATA = Map.ofEntries(
			Map.entry("minecraft:generic_9x1", new MenuTypeData(InventoryView.class, "CHEST", 9, BuilderType.BASE)),
			Map.entry("minecraft:generic_9x2", new MenuTypeData(InventoryView.class, "CHEST", 18, BuilderType.BASE)),
			Map.entry("minecraft:generic_9x3", new MenuTypeData(InventoryView.class, "CHEST", 27, BuilderType.LOCATION)),
			Map.entry("minecraft:generic_9x4", new MenuTypeData(InventoryView.class, "CHEST", 36, BuilderType.BASE)),
			Map.entry("minecraft:generic_9x5", new MenuTypeData(InventoryView.class, "CHEST", 45, BuilderType.BASE)),
			Map.entry("minecraft:generic_9x6", new MenuTypeData(InventoryView.class, "CHEST", 54, BuilderType.LOCATION)),
			Map.entry("minecraft:generic_3x3", new MenuTypeData(InventoryView.class, "DISPENSER", 9, BuilderType.LOCATION)),
			Map.entry("minecraft:crafter_3x3", new MenuTypeData(CrafterView.class, "CRAFTER", 9, BuilderType.LOCATION)),
			Map.entry("minecraft:anvil", new MenuTypeData(AnvilView.class, "ANVIL", 3, BuilderType.LOCATION)),
			Map.entry("minecraft:beacon", new MenuTypeData(BeaconView.class, "BEACON", 1, BuilderType.LOCATION)),
			Map.entry("minecraft:blast_furnace", new MenuTypeData(FurnaceView.class, "BLAST_FURNACE", 3, BuilderType.LOCATION)),
			Map.entry("minecraft:brewing_stand", new MenuTypeData(BrewingStandView.class, "BREWING", 5, BuilderType.LOCATION)),
			Map.entry("minecraft:crafting", new MenuTypeData(InventoryView.class, "WORKBENCH", 10, BuilderType.LOCATION)),
			Map.entry("minecraft:enchantment", new MenuTypeData(EnchantmentView.class, "ENCHANTING", 2, BuilderType.LOCATION)),
			Map.entry("minecraft:furnace", new MenuTypeData(FurnaceView.class, "FURNACE", 3, BuilderType.LOCATION)),
			Map.entry("minecraft:grindstone", new MenuTypeData(InventoryView.class, "GRINDSTONE", 3, BuilderType.LOCATION)),
			Map.entry("minecraft:hopper", new MenuTypeData(InventoryView.class, "HOPPER", 5, BuilderType.LOCATION)),
			Map.entry("minecraft:lectern", new MenuTypeData(LecternView.class, "LECTERN", 1, BuilderType.LOCATION)),
			Map.entry("minecraft:loom", new MenuTypeData(LoomView.class, "LOOM", 4, BuilderType.LOCATION)),
			Map.entry("minecraft:merchant", new MenuTypeData(MerchantView.class, "MERCHANT", 3, BuilderType.MERCHANT)),
			Map.entry("minecraft:shulker_box", new MenuTypeData(InventoryView.class, "SHULKER_BOX", 27, BuilderType.LOCATION)),
			Map.entry("minecraft:smithing", new MenuTypeData(InventoryView.class, "SMITHING", 4, BuilderType.LOCATION)),
			Map.entry("minecraft:smoker", new MenuTypeData(FurnaceView.class, "SMOKER", 3, BuilderType.LOCATION)),
			Map.entry("minecraft:cartography_table", new MenuTypeData(InventoryView.class, "CARTOGRAPHY", 3, BuilderType.LOCATION)),
			Map.entry("minecraft:stonecutter", new MenuTypeData(StonecutterView.class, "STONECUTTER", 2, BuilderType.LOCATION))
	);

	private final NamespacedKey key;
	private final MenuTypeData menuTypeData;

	private MenuTypeMock(@NotNull NamespacedKey key, @NotNull MenuTypeData menuTypeData)
	{
		Preconditions.checkArgument(key != null, "The menu key is null.");
		Preconditions.checkArgument(menuTypeData != null, "The menu type data is null.");
		this.key = key;
		this.menuTypeData = menuTypeData;
	}

	@Override
	public @NotNull V create(@NotNull HumanEntity player, @NotNull String title)
	{
		return create(player, Component.text(title));
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull V create(@NotNull HumanEntity player, Component title)
	{
		Preconditions.checkArgument(title != null, "title cannot be null");

		ServerMock server = MockBukkit.getMock();
		InventoryMock inventory;

		if (menuTypeData.inventoryType() == InventoryType.CHEST)
		{
			inventory = server.createInventory(player, menuTypeData.inventorySize(), title);
		}
		else
		{
			inventory = server.createInventory(player, menuTypeData.inventoryType(), title);
		}

		PlayerInventoryViewMock view = new PlayerInventoryViewMock(player, inventory);
		return (V) view;
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull B builder()
	{
		return (B) switch (menuTypeData.builderType())
		{
			case BASE -> new InventoryViewBuilderMock<>(this);
			case LOCATION -> new LocationInventoryViewBuilderMock<>(this);
			case MERCHANT -> new MerchantInventoryViewBuilderMock<>(this);
		};
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull Typed<InventoryView, InventoryViewBuilder<InventoryView>> typed()
	{
		return typed(InventoryView.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull <V extends InventoryView, B extends InventoryViewBuilder<V>> Typed<V, B> typed(@NotNull Class<V> viewClass) throws IllegalArgumentException
	{
		Preconditions.checkArgument(viewClass.isAssignableFrom(menuTypeData.viewClass()),
				"Cannot type MenuType %s to view class %s (expected %s)",
				key, viewClass.getSimpleName(), menuTypeData.viewClass().getSimpleName());
		return (Typed<V, B>) this;
	}

	@Override
	public @NotNull Class<? extends InventoryView> getInventoryViewClass()
	{
		return menuTypeData.viewClass();
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	public @NotNull Key key()
	{
		return this.key;
	}

	MenuTypeData getMenuTypeData()
	{
		return menuTypeData;
	}

	/**
	 * Creates a new {@link MenuTypeMock} from the provided {@link JsonObject}.
	 *
	 * @param jsonObject The JSON data to construct from.
	 * @return The created {@link MenuTypeMock}.
	 */
	@ApiStatus.Internal
	public static MenuTypeMock<?, ?> from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		MenuTypeData data = MENU_TYPE_DATA.get(key.toString());
		Preconditions.checkArgument(data != null, "Unknown menu type: %s", key);
		return new MenuTypeMock<>(key, data);
	}

}