package org.mockbukkit.mockbukkit.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.view.builder.LocationInventoryViewBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Mock implementation of {@link LocationInventoryViewBuilder}.
 *
 * @param <V> The type of {@link InventoryView} this builder creates.
 */
public class LocationInventoryViewBuilderMock<V extends InventoryView> implements LocationInventoryViewBuilder<V>
{

	private final MenuTypeMock<V, ?> menuType;
	private @Nullable Component title;

	LocationInventoryViewBuilderMock(@NotNull MenuTypeMock<V, ?> menuType)
	{
		this.menuType = menuType;
	}

	private LocationInventoryViewBuilderMock(@NotNull MenuTypeMock<V, ?> menuType, @Nullable Component title)
	{
		this.menuType = menuType;
		this.title = title;
	}

	@Override
	public @NotNull LocationInventoryViewBuilder<V> copy()
	{
		return new LocationInventoryViewBuilderMock<>(menuType, title);
	}

	@Override
	public @NotNull LocationInventoryViewBuilder<V> title(@Nullable Component title)
	{
		this.title = title;
		return this;
	}

	@Override
	public @NotNull LocationInventoryViewBuilder<V> checkReachable(boolean checkReachable)
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull LocationInventoryViewBuilder<V> location(@Nullable Location location)
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull V build(@NotNull HumanEntity player)
	{
		Component effectiveTitle = title != null ? title : menuType.getMenuTypeData().inventoryType().defaultTitle();
		return menuType.create(player, effectiveTitle);
	}

}
