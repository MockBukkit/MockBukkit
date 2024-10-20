package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of an {@link LeatherArmorMeta}.
 *
 * @see ItemMetaMock
 */
public class LeatherArmorMetaMock extends ItemMetaMock implements LeatherArmorMeta
{

	private Color color;

	/**
	 * Constructs a new {@link LeatherArmorMetaMock}.
	 */
	public LeatherArmorMetaMock()
	{
		super();
		this.color = Bukkit.getItemFactory().getDefaultLeatherColor();
	}

	/**
	 * Constructs a new {@link LeatherArmorMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public LeatherArmorMetaMock(@NotNull LeatherArmorMeta meta)
	{
		super(meta);
		this.color = meta.getColor();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		if (!(obj instanceof LeatherArmorMeta other))
		{
			return false;
		}

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
		serialMock.color = Color.fromARGB((int) args.get("color"));
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
		serialized.put("color", color.asARGB());
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "LEATHER_ARMOR";
	}

}
