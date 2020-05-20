package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This {@link ItemMetaMock} mocks the implementation of {@link LeatherArmorMeta}.
 *
 * @author TheBusyBiscuit
 **/
public class LeatherArmorMetaMock extends ItemMetaMock implements LeatherArmorMeta
{

	private Color color;

	public LeatherArmorMetaMock()
	{
		super();
		this.color = Bukkit.getItemFactory().getDefaultLeatherColor();
	}

	public LeatherArmorMetaMock(LeatherArmorMeta meta)
	{
		super(meta);
		this.color = meta.getColor();
	}

	@Override
	public LeatherArmorMetaMock clone()
	{
		LeatherArmorMetaMock mock = (LeatherArmorMetaMock) super.clone();
		mock.setColor(color);
		return mock;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + color.hashCode();
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
		if (!(obj instanceof LeatherArmorMeta))
		{
			return false;
		}

		LeatherArmorMeta other = (LeatherArmorMeta) obj;
		return Objects.equals(color, other.getColor());
	}

	@Override
	public @NotNull Color getColor()
	{
		return color;
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = color == null ? Bukkit.getItemFactory().getDefaultLeatherColor() : color;
	}
}
