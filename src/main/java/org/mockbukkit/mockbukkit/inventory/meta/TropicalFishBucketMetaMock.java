package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of an {@link TropicalFishBucketMeta}.
 *
 * @see ItemMetaMock
 */
public class TropicalFishBucketMetaMock extends ItemMetaMock implements TropicalFishBucketMeta
{

	/**
	 * Constructs a new {@link TropicalFishBucketMetaMock}.
	 */
	public TropicalFishBucketMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public TropicalFishBucketMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link TropicalFishBucketMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public TropicalFishBucketMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
		if (meta instanceof TropicalFishBucketMeta)
		{
			init();
		}
	}

	/**
	 * Defaults any null variables.
	 */
	protected void init()
	{
		if (!has(DataComponentTypes.TROPICAL_FISH_PATTERN_COLOR))
		{
			set(DataComponentTypes.TROPICAL_FISH_PATTERN_COLOR, DyeColor.WHITE);
		}
		if (!has(DataComponentTypes.TROPICAL_FISH_BASE_COLOR))
		{
			set(DataComponentTypes.TROPICAL_FISH_BASE_COLOR, DyeColor.WHITE);
		}
		if (!has(DataComponentTypes.TROPICAL_FISH_PATTERN))
		{
			set(DataComponentTypes.TROPICAL_FISH_PATTERN, TropicalFish.Pattern.KOB);
		}
	}

	@Override
	public @NotNull DyeColor getPatternColor()
	{
		init();
		return get(DataComponentTypes.TROPICAL_FISH_PATTERN_COLOR);
	}

	@Override
	public void setPatternColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color);
		set(DataComponentTypes.TROPICAL_FISH_PATTERN_COLOR, color);
	}

	@Override
	public @NotNull DyeColor getBodyColor()
	{
		init();
		return get(DataComponentTypes.TROPICAL_FISH_BASE_COLOR);
	}

	@Override
	public void setBodyColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color);
		set(DataComponentTypes.TROPICAL_FISH_BASE_COLOR, color);
	}

	@Override
	public @NotNull TropicalFish.Pattern getPattern()
	{
		init();
		return get(DataComponentTypes.TROPICAL_FISH_PATTERN);
	}

	@Override
	public void setPattern(TropicalFish.@NotNull Pattern pattern)
	{
		Preconditions.checkNotNull(pattern);
		set(DataComponentTypes.TROPICAL_FISH_PATTERN, pattern);
	}

	@Override
	public boolean hasVariant()
	{
		return has(DataComponentTypes.TROPICAL_FISH_PATTERN) && has(DataComponentTypes.TROPICAL_FISH_PATTERN_COLOR) && has(DataComponentTypes.TROPICAL_FISH_BASE_COLOR);
	}

	@Override
	public @NotNull TropicalFishBucketMetaMock clone()
	{
		return (TropicalFishBucketMetaMock) super.clone();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized TropicalFishBucketMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the TropicalFishBucketMetaMock class.
	 */
	public static @NotNull TropicalFishBucketMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		TropicalFishBucketMetaMock serialMock = new TropicalFishBucketMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "TROPICAL_FISH_BUCKET";
	}

}
