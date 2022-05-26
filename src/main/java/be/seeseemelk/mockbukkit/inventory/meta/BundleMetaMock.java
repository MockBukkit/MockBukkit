package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BundleMetaMock extends ItemMetaMock implements BundleMeta
{

	private List<ItemStack> items = null;

	public BundleMetaMock()
	{
		super();
	}

	public BundleMetaMock(BundleMeta meta)
	{
		super(meta);

		this.items = meta.getItems();
	}

	@Override
	public boolean hasItems()
	{
		return this.items != null && !this.items.isEmpty();
	}

	@Override
	public @NotNull List<ItemStack> getItems()
	{
		return (this.items == null) ? ImmutableList.of() : ImmutableList.copyOf(items);
	}

	@Override
	public void setItems(@Nullable List<ItemStack> items)
	{
		this.items = null;

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

		if (this.items == null)
		{
			this.items = new ArrayList<>();
		}

		this.items.add(item);
	}

	@Override
	public @NotNull BundleMetaMock clone()
	{
		BundleMetaMock clone = (BundleMetaMock) super.clone();
		clone.items = this.items;
		return clone;
	}

}
