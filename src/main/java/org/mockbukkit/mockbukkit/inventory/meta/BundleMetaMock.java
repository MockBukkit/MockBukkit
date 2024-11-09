package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;
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

	private List<ItemStack> items;

	/**
	 * Constructs a new {@link BundleMetaMock}.
	 */
	public BundleMetaMock()
	{
		super();

		this.items = new ArrayList<>();
	}

	/**
	 * Constructs a new {@link BundleMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public BundleMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof BundleMeta bundleMeta)
		{
			this.items = new ArrayList<>(bundleMeta.getItems());
		}
		else
		{
			this.items = new ArrayList<>();
		}
	}

	@Override
	public boolean hasItems()
	{
		return !this.items.isEmpty();
	}

	@Override
	public @NotNull List<ItemStack> getItems()
	{
		return ImmutableList.copyOf(items);
	}

	@Override
	public void setItems(@Nullable List<ItemStack> items)
	{
		this.items.clear();

		if (items == null)
		{
			return;
		}

		for (ItemStack i : items)
		{
			this.addItem(i);
		}
	}

	@Override
	public void addItem(@NotNull ItemStack item)
	{
		Preconditions.checkArgument(item != null && !item.getType().isAir(), "item is null or air");

		this.items.add(item);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BundleMeta meta))
		{
			return false;
		}
		return super.equals(obj) && this.getItems().equals(meta.getItems());
	}

	@Override
	public @NotNull BundleMetaMock clone()
	{
		BundleMetaMock clone = (BundleMetaMock) super.clone();
		clone.items = new ArrayList<>(this.items.stream().map(ItemStack::clone).toList());
		return clone;
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
		serialMock.items = args.get("items") == null ? new ArrayList<>() : (List<ItemStack>) args.get("items");
		return serialMock;
	}

	/**
	 * Serializes the properties of an BundleMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the BundleMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("items", this.items);
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "BUNDLE";
	}

}
