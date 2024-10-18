package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ColorableArmorMetaMock extends ArmorMetaMock implements ColorableArmorMeta
{

	private Integer color;

	static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(0xA06540);

	public ColorableArmorMetaMock()
	{
		super();
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
	public @NotNull ColorableArmorMetaMock clone()
	{
		ColorableArmorMetaMock clone = (ColorableArmorMetaMock) super.clone();
		clone.color = this.color;
		return clone;
	}

	@Override
	public Map<String, Object> serialize()
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
