package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of an {@link AxolotlBucketMeta}.
 *
 * @see ItemMetaMock
 */
public class AxolotlBucketMetaMock extends ItemMetaMock implements AxolotlBucketMeta
{

	/**
	 * Constructs a new {@link AxolotlBucketMetaMock}.
	 */
	public AxolotlBucketMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public AxolotlBucketMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link AxolotlBucketMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public AxolotlBucketMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull Axolotl.Variant getVariant()
	{
		Preconditions.checkArgument(hasVariant(), "Variant is absent, check hasVariant first!");
		return get(DataComponentTypes.AXOLOTL_VARIANT);
	}

	@Override
	public void setVariant(@NotNull Axolotl.Variant variant)
	{
		Preconditions.checkNotNull(variant);
		set(DataComponentTypes.AXOLOTL_VARIANT, variant);
	}

	@Override
	public boolean hasVariant()
	{
		return has(DataComponentTypes.AXOLOTL_VARIANT);
	}

	@Override
	public @NotNull AxolotlBucketMetaMock clone()
	{
		return (AxolotlBucketMetaMock) super.clone();
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
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "AXOLOTL_BUCKET";
	}

}
