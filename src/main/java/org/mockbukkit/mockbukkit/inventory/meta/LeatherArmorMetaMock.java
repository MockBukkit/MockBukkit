package org.mockbukkit.mockbukkit.inventory.meta;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Mock implementation of an {@link LeatherArmorMeta}.
 *
 * @see ItemMetaMock
 */
public class LeatherArmorMetaMock extends ItemMetaMock implements LeatherArmorMeta
{

	private @Nullable Color color;

	/**
	 * Constructs a new {@link LeatherArmorMetaMock}.
	 */
	public LeatherArmorMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public LeatherArmorMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link LeatherArmorMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public LeatherArmorMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull LeatherArmorMetaMock clone()
	{
		return (LeatherArmorMetaMock) super.clone();
	}

	@Override
	public boolean isDyed()
	{
		return has(DataComponentTypes.DYED_COLOR);
	}

	@Override
	public @NotNull Color getColor()
	{
		DyedItemColor dyedItemColor = get(DataComponentTypes.DYED_COLOR);
		if (dyedItemColor == null)
		{
			return Bukkit.getItemFactory().getDefaultLeatherColor();
		}
		return dyedItemColor.color();
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		if (color == null)
		{
			unset(DataComponentTypes.DYED_COLOR);
		}
		else
		{
			set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(color));
		}
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized LeatherArmorMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the LeatherArmorMetaMock class.
	 */
	public static @NotNull LeatherArmorMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		LeatherArmorMetaMock serialMock = new LeatherArmorMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "LEATHER_ARMOR";
	}

}
