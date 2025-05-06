package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ChargedProjectiles;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
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

	/**
	 * Constructs a new {@link CrossbowMetaMock}.
	 */
	public CrossbowMetaMock()
	{
		super();
	}

	public CrossbowMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link CrossbowMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public CrossbowMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public boolean hasChargedProjectiles()
	{
		return has(DataComponentTypes.CHARGED_PROJECTILES);
	}

	@Override
	public @NotNull List<ItemStack> getChargedProjectiles()
	{
		ChargedProjectiles chargedProjectiles = get(DataComponentTypes.CHARGED_PROJECTILES);
		if (chargedProjectiles == null)
		{
			return List.of();
		}
		return chargedProjectiles.projectiles();
	}

	@Override
	public void setChargedProjectiles(@Nullable List<ItemStack> projectiles)
	{
		if (projectiles == null)
		{
			return;
		}
		set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectiles.chargedProjectiles(projectiles));
	}

	@Override
	public void addChargedProjectile(@NotNull ItemStack item)
	{
		Preconditions.checkArgument(item != null, "item");
		Preconditions.checkArgument(item.getType() == Material.FIREWORK_ROCKET || item.getType().name().contains("ARROW"), "Item %s is not an arrow or firework rocket", item);
		ChargedProjectiles chargedProjectiles = get(DataComponentTypes.CHARGED_PROJECTILES);
		if (chargedProjectiles == null)
		{
			set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectiles.chargedProjectiles(List.of(item)));
		}
		else
		{
			List<ItemStack> projectiles = new ArrayList<>(chargedProjectiles.projectiles());
			projectiles.add(item);
			set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectiles.chargedProjectiles(projectiles));
		}
	}

	@Override
	public @NotNull CrossbowMetaMock clone()
	{
		return (CrossbowMetaMock) super.clone();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized CrossbowMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the CrossbowMetaMock class.
	 */
	public static @NotNull CrossbowMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		CrossbowMetaMock serialMock = new CrossbowMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "CROSSBOW";
	}

}
