package org.mockbukkit.mockbukkit.inventory.meta;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of an {@link ArmorStandMeta}.
 *
 * @see ItemMetaMock
 */
public class ArmorStandMetaMock extends ItemMetaMock implements ArmorStandMeta
{

	private boolean invisible;
	private boolean noBasePlate;
	private boolean showArms;
	private boolean small;
	private boolean marker;

	/**
	 * Constructs a new {@link ArmorStandMetaMock}.
	 */
	public ArmorStandMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link ArmorStandMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public ArmorStandMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof ArmorStandMeta armorStandMeta)
		{
			this.invisible = armorStandMeta.isInvisible();
			this.marker = armorStandMeta.isMarker();
			this.noBasePlate = armorStandMeta.hasNoBasePlate();
			this.showArms = armorStandMeta.shouldShowArms();
			this.small = armorStandMeta.isSmall();
		}
	}

	@Override
	public boolean isInvisible()
	{
		return invisible;
	}

	@Override
	public boolean hasNoBasePlate()
	{
		return noBasePlate;
	}

	@Override
	public boolean shouldShowArms()
	{
		return showArms;
	}

	@Override
	public boolean isSmall()
	{
		return small;
	}

	@Override
	public boolean isMarker()
	{
		return marker;
	}

	@Override
	public void setInvisible(boolean invisible)
	{
		this.invisible = invisible;
	}

	@Override
	public void setNoBasePlate(boolean noBasePlate)
	{
		this.noBasePlate = noBasePlate;
	}

	@Override
	public void setShowArms(boolean showArms)
	{
		this.showArms = showArms;
	}

	@Override
	public void setSmall(boolean small)
	{
		this.small = small;
	}

	@Override
	public void setMarker(boolean marker)
	{
		this.marker = marker;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (invisible ? 1 : 0);
		result = prime * result + (noBasePlate ? 1 : 0);
		result = prime * result + (showArms ? 1 : 0);
		result = prime * result + (small ? 1 : 0);
		result = prime * result + (marker ? 1 : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ArmorStandMeta meta))
		{
			return false;
		}
		return super.equals(obj) &&
				this.isInvisible() == meta.isInvisible() &&
				this.hasNoBasePlate() == meta.hasNoBasePlate() &&
				this.shouldShowArms() == meta.shouldShowArms() &&
				this.isSmall() == meta.isSmall() &&
				this.isMarker() == meta.isMarker();
	}

	@Override
	public @NotNull ArmorStandMetaMock clone()
	{
		ArmorStandMetaMock clone = (ArmorStandMetaMock) super.clone();

		clone.invisible = this.invisible;
		clone.marker = this.marker;
		clone.noBasePlate = this.noBasePlate;
		clone.showArms = this.showArms;
		clone.small = this.small;

		return clone;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized ArmorStandMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the ArmorStandMetaMock class.
	 */
	public static @NotNull ArmorStandMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		ArmorStandMetaMock serialMock = new ArmorStandMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.invisible = (boolean) args.get("invisible");
		serialMock.marker = (boolean) args.get("marker");
		serialMock.noBasePlate = (boolean) args.get("no-base-plate");
		serialMock.showArms = (boolean) args.get("show-arms");
		serialMock.small = (boolean) args.get("small");
		return serialMock;
	}

	/**
	 * Serializes the properties of an ArmorStandMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the ArmorStandMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("invisible", this.invisible);
		serialized.put("marker", this.marker);
		serialized.put("no-base-plate", this.noBasePlate);
		serialized.put("show-arms", this.showArms);
		serialized.put("small", this.small);
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "ARMOR_STAND";
	}

}
