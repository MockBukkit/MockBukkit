package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.DyeColor;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.util.NbtParser;

import java.util.Map;

/**
 * Mock implementation of an {@link TropicalFishBucketMeta}.
 *
 * @see ItemMetaMock
 */
@DelegateDeserialization(SerializableMeta.class)
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

		if (meta instanceof TropicalFishBucketMeta bucketMeta)
		{
			if (bucketMeta.hasPattern())
			{
				this.pattern = bucketMeta.getPattern();
			}
			if (bucketMeta.hasPatternColor())
			{
				this.patternColor = bucketMeta.getPatternColor();
			}
			if (bucketMeta.hasBodyColor())
			{
				this.bodyColor = bucketMeta.getBodyColor();
			}
		}
	}

	@Override
	public @NotNull DyeColor getPatternColor()
	{
		Preconditions.checkState(this.hasPatternColor(), "Pattern color is absent, check hasPatternColor first!");
		return patternColor;
	}

	@Override
	public void setPatternColor(@NotNull DyeColor color)
	{
		this.patternColor = color;
	}

	@Override
	public @NotNull DyeColor getBodyColor()
	{
		Preconditions.checkState(this.hasBodyColor(), "Body color is absent, check hasBodyColor first!");
		return bodyColor;
	}

	@Override
	public void setBodyColor(@NotNull DyeColor color)
	{
		this.bodyColor = color;
	}

	@Override
	public @NotNull TropicalFish.Pattern getPattern()
	{
		Preconditions.checkState(this.hasPattern(), "Pattern is absent, check hasPattern first!");
		return pattern;
	}

	@Override
	public void setPattern(TropicalFish.@NotNull Pattern pattern)
	{
		this.pattern = pattern;
	}

	@Override
	public boolean hasPattern()
	{
		return this.pattern != null;
	}

	@Override
	public boolean hasBodyColor()
	{
		return this.bodyColor != null;
	}

	@Override
	public boolean hasPatternColor()
	{
		return this.patternColor != null;
	}

	@Override
	@Deprecated(since = "26.2", forRemoval = true)
	public boolean hasVariant()
	{
		return this.hasPattern() || this.hasBodyColor() || this.hasPatternColor();
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
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull TropicalFishBucketMetaMock clone()
	{
		return new TropicalFishBucketMetaMock(this);
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
		serialMock.bodyColor = NbtParser.parseEnum(args.get("body-color"), DyeColor.class);
		serialMock.patternColor = NbtParser.parseEnum(args.get("pattern-color"), DyeColor.class);
		serialMock.pattern = NbtParser.parseEnum(args.get("pattern"), TropicalFish.Pattern.class);
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
		if (hasBodyColor())
		{
			serialized.put("body-color", getBodyColor());
		}
		if (hasPatternColor())
		{
			serialized.put("pattern-color", getPatternColor());
		}
		if (hasPattern())
		{
			serialized.put("pattern", getPattern());
		}
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "TROPICAL_FISH_BUCKET";
	}

}
