package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

		this.projectiles = meta.hasChargedProjectiles() ?
				new ArrayList<>(meta.getChargedProjectiles().stream().map(ItemStack::clone).toList()) :
				new ArrayList<>();
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
		Preconditions.checkArgument(item.getType() == Material.FIREWORK_ROCKET || item.getType().name().contains("ARROW"), "Item %s is not an arrow or firework rocket", item);

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
		{
			return false;
		}
		return super.equals(obj) && Objects.equals(this.getChargedProjectiles(), meta.getChargedProjectiles());
	}

	@Override
	public @NotNull CrossbowMetaMock clone()
	{
		CrossbowMetaMock clone = (CrossbowMetaMock) super.clone();

		clone.projectiles = new ArrayList<>(this.projectiles.stream().map(ItemStack::clone).toList());

		return clone;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized CrossbowMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the CrossbowMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull CrossbowMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		CrossbowMetaMock serialMock = new CrossbowMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.projectiles = (List<ItemStack>) args.get("projectiles");
		return serialMock;
	}

	/**
	 * Serializes the properties of an CrossbowMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the CrossbowMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("projectiles", this.projectiles);
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "CROSSBOW";
	}

}
