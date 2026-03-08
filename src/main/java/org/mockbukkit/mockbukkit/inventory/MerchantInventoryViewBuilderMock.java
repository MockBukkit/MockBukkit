package org.mockbukkit.mockbukkit.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.view.builder.MerchantInventoryViewBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Mock implementation of {@link MerchantInventoryViewBuilder}.
 *
 * @param <V> The type of {@link InventoryView} this builder creates.
 */
public class MerchantInventoryViewBuilderMock<V extends InventoryView> implements MerchantInventoryViewBuilder<V>
{

	private final MenuTypeMock<V, ?> menuType;
	private @Nullable Component title;

	MerchantInventoryViewBuilderMock(@NotNull MenuTypeMock<V, ?> menuType)
	{
		this.menuType = menuType;
	}

	private MerchantInventoryViewBuilderMock(@NotNull MenuTypeMock<V, ?> menuType, @Nullable Component title)
	{
		this.menuType = menuType;
		this.title = title;
	}

	@Override
	public @NotNull MerchantInventoryViewBuilder<V> copy()
	{
		return new MerchantInventoryViewBuilderMock<>(menuType, title);
	}

	@Override
	public @NotNull MerchantInventoryViewBuilder<V> title(@Nullable Component title)
	{
		this.title = title;
		return this;
	}

	@Override
	public @NotNull MerchantInventoryViewBuilder<V> merchant(@Nullable Merchant merchant)
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull MerchantInventoryViewBuilder<V> checkReachable(boolean checkReachable)
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
