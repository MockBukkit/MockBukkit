package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Mock implementation of a {@link CrossbowMeta}.
 *
 * @see ItemMetaMock
 */
public class CrossbowMetaMock extends ItemMetaMock implements CrossbowMeta
{

	private List<ItemStack> projectiles;

	/**
	 * Constructs a new {@link CrossbowMetaMock}.
	 */
	public CrossbowMetaMock()
	{
		super();

		this.projectiles = new ArrayList<>();
	}

	/**
	 * Constructs a new {@link CrossbowMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public CrossbowMetaMock(@NotNull CrossbowMeta meta)
	{
		super(meta);

		if (meta.hasChargedProjectiles())
		{
			this.projectiles = new ArrayList<>(meta.getChargedProjectiles());
		}
	}

	@Override
	public boolean hasChargedProjectiles()
	{
		return !this.projectiles.isEmpty();
	}

	@Override
	public @NotNull List<ItemStack> getChargedProjectiles()
	{
		return ImmutableList.copyOf(this.projectiles);
	}

	@Override
	public void setChargedProjectiles(@Nullable List<ItemStack> projectiles)
	{
		this.projectiles.clear();

		if (projectiles == null)
		{
			return;
		}

		for (ItemStack i : projectiles)
		{
			this.addChargedProjectile(i);
		}
	}

	@Override
	public void addChargedProjectile(@NotNull ItemStack item)
	{
		Preconditions.checkArgument(item != null, "item");
		Preconditions.checkArgument(
				item.getType() == Material.FIREWORK_ROCKET || item.getType().name().contains("ARROW"),
				"Item %s is not an arrow or firework rocket", item);

		this.projectiles.add(item);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (projectiles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CrossbowMeta meta))
			return false;
		return super.equals(obj) && Objects.equals(this.getChargedProjectiles(), meta.getChargedProjectiles());
	}

	@Override
	public @NotNull CrossbowMetaMock clone()
	{
		CrossbowMetaMock clone = (CrossbowMetaMock) super.clone();

		clone.projectiles = new ArrayList<>(this.projectiles);

		return clone;
	}

}
