package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class MenuTypeMock implements MenuType.Typed
{
	private final NamespacedKey key;

	private MenuTypeMock(@NotNull NamespacedKey key)
	{
		Preconditions.checkArgument(key != null, "The menu key is null.");
		this.key = key;
	}

	@Override
	public @NotNull InventoryView create(@NotNull HumanEntity humanEntity, @NotNull String s)
	{
		return create(humanEntity, Component.text(s));
	}

	@Override
	public @NotNull InventoryView create(@NotNull HumanEntity humanEntity, @NotNull Component component)
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Typed<InventoryView> typed()
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull <V extends InventoryView> Typed<V> typed(@NotNull Class<V> aClass) throws IllegalArgumentException
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
