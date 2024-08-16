package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.DyeColor;
import org.bukkit.inventory.meta.ShieldMeta;
import org.jetbrains.annotations.Nullable;

public class ShieldMetaMock extends BannerMetaMock implements ShieldMeta
{

	private DyeColor color;

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

}
