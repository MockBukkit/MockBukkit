package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.view.builder.InventoryViewBuilder;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

public class MenuTypeMock<V extends InventoryView, B extends InventoryViewBuilder<V>> implements MenuType.Typed<V, B>
{

	private final NamespacedKey key;

	private MenuTypeMock(@NotNull NamespacedKey key)
	{
		Preconditions.checkArgument(key != null, "The menu key is null.");
		this.key = key;
	}

	@Override
	public @NotNull V create(@NotNull HumanEntity humanEntity, @NotNull String s)
	{
		return create(humanEntity, Component.text(s));
	}

	@Override
	public @NotNull V create(@NotNull HumanEntity humanEntity, @NotNull Component component)
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull B builder()
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Typed<InventoryView, InventoryViewBuilder<InventoryView>> typed()
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull <V extends InventoryView, B extends InventoryViewBuilder<V>> Typed<V, B> typed(@NotNull Class<V> viewClass) throws IllegalArgumentException
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Class<? extends InventoryView> getInventoryViewClass()
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
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

	public static MenuTypeMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		return new MenuTypeMock(key);
	}

}
