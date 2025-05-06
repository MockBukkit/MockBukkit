package org.mockbukkit.mockbukkit.inventory.meta;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemArmorTrim;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ArmorMetaMock extends ItemMetaMock implements ArmorMeta
{

	public ArmorMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public ArmorMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	public ArmorMetaMock(ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public boolean hasTrim()
	{
		return has(DataComponentTypes.TRIM);
	}

	@Override
	public void setTrim(@Nullable ArmorTrim trim)
	{
		if (trim == null)
		{
			unset(DataComponentTypes.TRIM);
		}
		else
		{
			set(DataComponentTypes.TRIM, ItemArmorTrim.itemArmorTrim(trim).build());
		}
	}

	@Override
	public @Nullable ArmorTrim getTrim()
	{
		return has(DataComponentTypes.TRIM) ? get(DataComponentTypes.TRIM).armorTrim() : null;
	}

	@Override
	public ArmorMetaMock clone()
	{
		return (ArmorMetaMock) super.clone();
	}

}
