package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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


}
