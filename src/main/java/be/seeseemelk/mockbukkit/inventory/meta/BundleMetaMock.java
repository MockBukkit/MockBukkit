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

	private List<ItemStack> items;

	public BundleMetaMock()
	{
		super();

		this.items = new ArrayList<>();
	}

	public BundleMetaMock(@NotNull BundleMeta meta)
	{
		super(meta);

		this.items = new ArrayList<>(meta.getItems());
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
			return false;
		return super.equals(obj) && this.getItems().equals(meta.getItems());
	}

	@Override
	public @NotNull BundleMetaMock clone()
	{
		BundleMetaMock clone = (BundleMetaMock) super.clone();
		clone.items = new ArrayList<>(this.items);
		return clone;
	}

}
