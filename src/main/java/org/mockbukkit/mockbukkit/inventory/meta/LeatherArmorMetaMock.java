package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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

	/**
	 * Constructs a new {@link LeatherArmorMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public LeatherArmorMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof LeatherArmorMeta leatherArmorMeta)
		{
			this.color = leatherArmorMeta.isDyed() ? leatherArmorMeta.getColor() : null;
		}
	}

	@Override
	public @NotNull LeatherArmorMetaMock clone()
	{
		LeatherArmorMetaMock mock = (LeatherArmorMetaMock) super.clone();
		mock.setColor(color);
		return mock;
	}

	@Override
	public boolean isDyed()
	{
		return color != null;
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
		if (!(obj instanceof LeatherArmorMeta other))
		{
			return false;
		}

		return this.isDyed() ? this.getColor().equals(other.getColor()) : !other.isDyed();
	}

	@Override
	public @NotNull Color getColor()
	{
		return color == null ? Bukkit.getItemFactory().getDefaultLeatherColor() : color;
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = color;
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
		if(args.containsKey("color"))
		{
			serialMock.color = Color.fromARGB((int) args.get("color"));
		}
		return serialMock;
	}

	/**
	 * Serializes the properties of an LeatherArmorMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the LeatherArmorMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if(isDyed())
		{
			serialized.put("color", color.asARGB());
		}
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "LEATHER_ARMOR";
	}

}
