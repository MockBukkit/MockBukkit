package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BundleContents;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of a {@link BundleMeta}.
 *
 * @see ItemMetaMock
 */
public class BundleMetaMock extends ItemMetaMock implements BundleMeta
{

	/**
	 * Constructs a new {@link BundleMetaMock}.
	 */
	public BundleMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public BundleMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link BundleMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public BundleMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public boolean hasItems()
	{
		BundleContents bundleContents = get(DataComponentTypes.BUNDLE_CONTENTS);
		if (bundleContents == null)
		{
			return false;
		}
		return !bundleContents.contents().isEmpty();
	}

	@Override
	public @NotNull List<ItemStack> getItems()
	{
		BundleContents bundleContents = get(DataComponentTypes.BUNDLE_CONTENTS);
		if (bundleContents == null)
		{
			return List.of();
		}
		return bundleContents.contents();
	}

	@Override
	public void setItems(@Nullable List<ItemStack> items)
	{
		if (items == null)
		{
			unset(DataComponentTypes.BUNDLE_CONTENTS);
		}
		set(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(items));
	}

	@Override
	public void addItem(@NotNull ItemStack item)
	{
		Preconditions.checkArgument(item != null && !item.getType().isAir(), "item is null or air");
		BundleContents bundleContents = get(DataComponentTypes.BUNDLE_CONTENTS);
		if (bundleContents == null)
		{
			set(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(List.of(item)));
		}
		else
		{
			List<ItemStack> contents = new ArrayList<>(bundleContents.contents());
			contents.add(item);
			set(DataComponentTypes.BUNDLE_CONTENTS, BundleContents.bundleContents(contents));
		}
	}

	@Override
	public @NotNull BundleMetaMock clone()
	{
		return (BundleMetaMock) super.clone();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized BundleMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the BundleMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull BundleMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		BundleMetaMock serialMock = new BundleMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "BUNDLE";
	}

}
