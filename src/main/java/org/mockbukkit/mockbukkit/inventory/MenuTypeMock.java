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
import org.bukkit.inventory.view.builder.InventoryViewBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

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
	 * MenuTypeMock init -> InventoryType init -> MenuType init -> Registry.MENU -> MenuTypeMock.from()
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
		Class<? extends InventoryView> viewClass = resolveViewClass(jsonObject.get("inventoryViewClass").getAsString());
		BuilderType builderType = BuilderType.valueOf(jsonObject.get("builderType").getAsString());
		String inventoryTypeName = jsonObject.get("inventoryTypeName").getAsString();
		int inventorySize = jsonObject.get("inventorySize").getAsInt();

		MenuTypeData data = new MenuTypeData(viewClass, inventoryTypeName, inventorySize, builderType);
		return new MenuTypeMock<>(key, data);
	}

	private static Class<? extends InventoryView> resolveViewClass(String simpleName)
	{
		if ("InventoryView".equals(simpleName))
		{
			return InventoryView.class;
		}
		try
		{
			return Class.forName("org.bukkit.inventory.view." + simpleName).asSubclass(InventoryView.class);
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Unknown inventory view class: " + simpleName, e);
		}
	}

}
