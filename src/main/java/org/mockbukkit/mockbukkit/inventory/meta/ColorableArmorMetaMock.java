package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ColorableArmorMetaMock extends ArmorMetaMock implements ColorableArmorMeta
{

	private @Nullable Integer color;

	static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(0xA06540);

	/**
	 * Constructs a new {@link ColorableArmorMetaMock}.
	 */
	public ColorableArmorMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link ColorableArmorMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public ColorableArmorMetaMock(ItemMeta meta)
	{
		super(meta);

		if(meta instanceof LeatherArmorMeta leatherArmorMeta)
		{
			this.color = leatherArmorMeta.getColor().asRGB();
		}
	}

	@Override
	public @NotNull Color getColor()
	{
		return this.color == null ? DEFAULT_LEATHER_COLOR : Color.fromRGB(this.color & 0xFFFFFF);
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = color == null ? null : color.asRGB(); // Paper
	}

	@Override
	public boolean isDyed()
	{
		return this.color != null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + getColor().hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof ColorableArmorMeta other))
		{
			return false;
		}

		return this.isDyed() ? this.getColor().equals(other.getColor()) : !other.isDyed();
	}

	@Override
	public @NotNull ColorableArmorMetaMock clone()
	{
		ColorableArmorMetaMock clone = (ColorableArmorMetaMock) super.clone();
		clone.color = this.color;
		return clone;
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();
		if (this.isDyed())
		{
			serialized.put("color", this.getColor());
		}
		return serialized;
	}

	@Override
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		super.deserializeInternal(args);
		if (args.containsKey("color"))
		{
			this.color = (int) args.get("color");
		}
	}

	public static ColorableArmorMetaMock deserialize(Map<String, Object> serialized)
	{
		ColorableArmorMetaMock colorableArmorMetaMock = new ColorableArmorMetaMock();
		colorableArmorMetaMock.deserializeInternal(serialized);
		return colorableArmorMetaMock;
	}

}
