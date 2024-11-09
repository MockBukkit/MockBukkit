package org.mockbukkit.mockbukkit.inventory.meta;

import org.apache.commons.lang3.Validate;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of an {@link TropicalFishBucketMeta}.
 *
 * @see ItemMetaMock
 */
public class TropicalFishBucketMetaMock extends ItemMetaMock implements TropicalFishBucketMeta
{

	private DyeColor patternColor;
	private DyeColor bodyColor;
	private TropicalFish.Pattern pattern;

	/**
	 * Constructs a new {@link TropicalFishBucketMetaMock}.
	 */
	public TropicalFishBucketMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link TropicalFishBucketMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public TropicalFishBucketMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof TropicalFishBucketMeta bucketMeta)
		{
			if (meta instanceof TropicalFishBucketMetaMock mock)
			{
				mock.checkVars();
			}
			this.patternColor = bucketMeta.getPatternColor();
			this.bodyColor = bucketMeta.getBodyColor();
			this.pattern = bucketMeta.getPattern();
		}
	}

	/**
	 * Defaults any null variables.
	 */
	protected void checkVars()
	{
		if (this.patternColor == null)
		{
			this.patternColor = DyeColor.WHITE;
		}
		if (this.bodyColor == null)
		{
			this.bodyColor = DyeColor.WHITE;
		}
		if (this.pattern == null)
		{
			this.pattern = TropicalFish.Pattern.KOB;
		}
	}

	@Override
	public @NotNull DyeColor getPatternColor()
	{
		Validate.notNull(patternColor, "Pattern color is not set");
		return patternColor;
	}

	@Override
	public void setPatternColor(@NotNull DyeColor color)
	{
		checkVars();
		this.patternColor = color;
	}

	@Override
	public @NotNull DyeColor getBodyColor()
	{
		Validate.notNull(bodyColor, "Body color is not set");
		return bodyColor;
	}

	@Override
	public void setBodyColor(@NotNull DyeColor color)
	{
		checkVars();
		this.bodyColor = color;
	}

	@Override
	public @NotNull TropicalFish.Pattern getPattern()
	{
		Validate.notNull(pattern, "Pattern is not set");
		return pattern;
	}

	@Override
	public void setPattern(TropicalFish.@NotNull Pattern pattern)
	{
		checkVars();
		this.pattern = pattern;
	}

	@Override
	public boolean hasVariant()
	{
		return patternColor != null && bodyColor != null && pattern != null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bodyColor == null) ? 0 : bodyColor.hashCode());
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		result = prime * result + ((patternColor == null) ? 0 : patternColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof TropicalFishBucketMetaMock meta))
		{
			return false;
		}
		return super.equals(obj) && patternColor == meta.patternColor && bodyColor == meta.bodyColor && pattern == meta.pattern;
	}

	@Override
	public @NotNull TropicalFishBucketMetaMock clone()
	{
		TropicalFishBucketMetaMock clone = (TropicalFishBucketMetaMock) super.clone();
		clone.patternColor = this.patternColor;
		clone.bodyColor = this.bodyColor;
		clone.pattern = this.pattern;
		return clone;
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
		serialMock.bodyColor = (DyeColor) args.get("body-color");
		serialMock.patternColor = (DyeColor) args.get("pattern-color");
		serialMock.pattern = (TropicalFish.Pattern) args.get("pattern");
		return serialMock;
	}

	/**
	 * Serializes the properties of an TropicalFishBucketMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the TropicalFishBucketMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		checkVars();
		serialized.put("body-color", bodyColor);
		serialized.put("pattern-color", patternColor);
		serialized.put("pattern", pattern);
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "TROPICAL_FISH_BUCKET";
	}

}
