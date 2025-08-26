package org.mockbukkit.mockbukkit.inventory.meta;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;

import java.util.Map;

/**
 * Mock implementation of an {@link ArmorStandMeta}.
 *
 * @see ItemMetaMock
 */
@EqualsAndHashCode(callSuper = true)
@DelegateDeserialization(SerializableMeta.class)
public class ArmorStandMetaMock extends ItemMetaMock implements ArmorStandMeta
{

	@Getter
	@Setter
	private boolean invisible;
	private boolean noBasePlate;
	private boolean showArms;
	@Getter
	@Setter
	private boolean small;
	@Getter
	@Setter
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
		if (meta instanceof ArmorStandMeta armorStandMeta)
		{
			this.invisible = armorStandMeta.isInvisible();
			this.marker = armorStandMeta.isMarker();
			this.noBasePlate = armorStandMeta.hasNoBasePlate();
			this.showArms = armorStandMeta.shouldShowArms();
			this.small = armorStandMeta.isSmall();
		}
	}

	/**
	 * Gets whether the armor stand has no base plate.
	 *
	 * @return true if the armor stand has no base plate
	 */
	@Override
	public boolean hasNoBasePlate()
	{
		return noBasePlate;
	}

	/**
	 * Sets whether the armor stand should have no base plate.
	 *
	 * @param noBasePlate true to remove the base plate
	 */
	@Override
	public void setNoBasePlate(boolean noBasePlate)
	{
		this.noBasePlate = noBasePlate;
	}

	/**
	 * Gets whether the armor stand should show arms.
	 *
	 * @return true if the armor stand should show arms
	 */
	@Override
	public boolean shouldShowArms()
	{
		return showArms;
	}

	/**
	 * Sets whether the armor stand should show arms.
	 *
	 * @param showArms true to show arms
	 */
	@Override
	public void setShowArms(boolean showArms)
	{
		this.showArms = showArms;
	}

	/**
	 * Creates a clone of this armor stand meta.
	 *
	 * @return a cloned instance of this armor stand meta
	 */
	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull ArmorStandMetaMock clone()
	{
		return new ArmorStandMetaMock(this);
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
