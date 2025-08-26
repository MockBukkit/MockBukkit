package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.util.NbtParser;

import java.util.Map;

@DelegateDeserialization(SerializableMeta.class)
public class ColorableArmorMetaMock extends ArmorMetaMock implements ColorableArmorMeta
{

	static final int DEFAULT_LEATHER_COLOR = Color.fromRGB(0xA06540).asARGB();

	private @Nullable Integer color;

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

		if (meta instanceof ColorableArmorMeta colorableArmorMeta)
		{
			this.color = colorableArmorMeta.isDyed() ? colorableArmorMeta.getColor().asARGB() : null;
		}
	}

	@Override
	public @NotNull Color getColor()
	{
		return this.color == null ? Color.fromARGB(DEFAULT_LEATHER_COLOR) : Color.fromARGB(this.color);
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = (color == null ? null : color.asARGB());
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
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull ColorableArmorMetaMock clone()
	{
		return new ColorableArmorMetaMock(this);
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();
		if (this.isDyed())
		{
			serialized.put("color", this.getColor().serialize());
		}
		return serialized;
	}

	@Override
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		super.deserializeInternal(args);
		if (args.containsKey("color"))
		{
			this.color = Color.deserialize(NbtParser.parseMap(args.get("color"), NbtParser::parseDouble)).asARGB();
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
