package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.DyeColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.ShieldMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ShieldMetaMock extends BannerMetaMock implements ShieldMeta
{

	private DyeColor color;

	/**
	 * Constructs a new {@link ShieldMetaMock}.
	 */
	public ShieldMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link ShieldMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public ShieldMetaMock(@NotNull ItemMeta meta)
	{
		super();

		if(meta instanceof ShieldMeta shieldMeta)
		{
			this.color = shieldMeta.getBaseColor();
		}
	}

	@Override
	public @Nullable DyeColor getBaseColor()
	{
		return this.color;
	}

	@Override
	public void setBaseColor(@Nullable DyeColor color)
	{
		this.color = color;
	}

	@Override
	protected void deserializeInternal(Map<String, Object> serialized)
	{
		super.deserializeInternal(serialized);
		if (serialized.containsKey("base-color"))
		{
			this.color = DyeColor.values()[(int) serialized.get("base-color")];
		}
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();
		if (getBaseColor() != null)
		{
			serialized.put("base-color", color.ordinal());
		}
		return serialized;
	}

	public static ShieldMetaMock deserialize(Map<String, Object> serialized)
	{
		ShieldMetaMock shieldMetaMock = new ShieldMetaMock();
		shieldMetaMock.deserializeInternal(serialized);
		return shieldMetaMock;
	}

}
