package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of an {@link AxolotlBucketMeta}.
 *
 * @see ItemMetaMock
 */
public class AxolotlBucketMetaMock extends ItemMetaMock implements AxolotlBucketMeta
{

	private Axolotl.Variant variant;

	/**
	 * Constructs a new {@link AxolotlBucketMetaMock}.
	 */
	public AxolotlBucketMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link AxolotlBucketMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public AxolotlBucketMetaMock(@NotNull AxolotlBucketMeta meta)
	{
		super(meta);

		this.variant = meta.getVariant();
	}

	@Override
	public @NotNull Axolotl.Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Axolotl.Variant variant)
	{
		if (variant == null)
		{
			variant = Axolotl.Variant.LUCY;
		}
		this.variant = variant;
	}

	@Override
	public boolean hasVariant()
	{
		return this.variant != null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.variant != null ? this.variant.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof AxolotlBucketMeta meta))
		{
			return false;
		}
		return super.equals(obj) && this.variant == meta.getVariant();
	}

	@Override
	public @NotNull AxolotlBucketMetaMock clone()
	{
		AxolotlBucketMetaMock clone = (AxolotlBucketMetaMock) super.clone();

		clone.variant = this.variant;

		return clone;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized AxolotlBucketMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the AxolotlBucketMetaMock class.
	 */
	public static @NotNull AxolotlBucketMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		AxolotlBucketMetaMock serialMock = new AxolotlBucketMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.variant = (Axolotl.Variant) args.get("variant");
		return serialMock;
	}

	/**
	 * Serializes the properties of an AxolotlBucketMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the AxolotlBucketMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("variant", this.variant);
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "AXOLOTL_BUCKET";
	}

}
