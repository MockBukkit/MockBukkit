package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of a {@link CompassMeta}.
 *
 * @see ItemMetaMock
 */
public class CompassMetaMock extends ItemMetaMock implements CompassMeta
{

	private @Nullable Location lodestone;
	private boolean tracked;

	/**
	 * Constructs a new {@link CompassMetaMock}.
	 */
	public CompassMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link CompassMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public CompassMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof CompassMeta compass)
		{
			this.lodestone = compass.getLodestone();
			this.tracked = compass.isLodestoneTracked();
		}
	}

	@Override
	public boolean hasLodestone()
	{
		return this.lodestone != null;
	}

	@Override
	public @Nullable Location getLodestone()
	{
		return this.lodestone;
	}

	@Override
	public void setLodestone(@Nullable Location lodestone)
	{
		Preconditions.checkArgument(lodestone == null || lodestone.getWorld() != null, "world is null");
		this.lodestone = lodestone;
	}

	@Override
	public boolean isLodestoneTracked()
	{
		return this.tracked;
	}

	@Override
	public void setLodestoneTracked(boolean tracked)
	{
		this.tracked = tracked;
	}

	@Override
	public boolean isLodestoneCompass()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearLodestone()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = super.hashCode();
		hash = prime * hash + (this.lodestone != null ? this.lodestone.hashCode() : 0);
		hash = prime * hash + (this.tracked ? 1 : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CompassMeta meta))
		{
			return false;
		}
		return super.equals(obj) && Objects.equals(this.lodestone, meta.getLodestone()) && this.tracked == meta.isLodestoneTracked();
	}

	@Override
	public @NotNull CompassMetaMock clone()
	{
		CompassMetaMock clone = (CompassMetaMock) super.clone();
		clone.lodestone = this.lodestone == null ? null : this.lodestone.clone();
		clone.tracked = this.tracked;
		return clone;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized CompassMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the CompassMetaMock class.
	 */
	public static @NotNull CompassMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		CompassMetaMock serialMock = new CompassMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.lodestone = (Location) args.get("lodestone");
		serialMock.tracked = (boolean) args.get("tracked");
		return serialMock;
	}

	/**
	 * Serializes the properties of an CompassMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the CompassMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if (this.lodestone != null)
		{
			serialized.put("lodestone", this.lodestone);
		}
		serialized.put("tracked", this.tracked);
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "COMPASS";
	}

}
