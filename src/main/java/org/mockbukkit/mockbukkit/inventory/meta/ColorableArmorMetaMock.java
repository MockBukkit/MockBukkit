package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;

import java.util.Map;

@DelegateDeserialization(SerializableMeta.class)
public class ColorableArmorMetaMock extends ArmorMetaMock implements ColorableArmorMeta
{

	static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(0xA06540);

	private @Nullable Color color;

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

		if (meta instanceof LeatherArmorMeta leatherArmorMeta)
		{
			this.color = leatherArmorMeta.getColor();
		}
	}

	@Override
	public @NotNull Color getColor()
	{
		return this.color == null ? DEFAULT_LEATHER_COLOR : this.color;
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = color;
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
			this.color = (Color) args.get("color");
		}
	}

	@Override
	protected String getTypeName()
	{
		return "COLORABLE_ARMOR";
	}

	public static ColorableArmorMetaMock deserialize(Map<String, Object> serialized)
	{
		ColorableArmorMetaMock colorableArmorMetaMock = new ColorableArmorMetaMock();
		colorableArmorMetaMock.deserializeInternal(serialized);
		return colorableArmorMetaMock;
	}

}
