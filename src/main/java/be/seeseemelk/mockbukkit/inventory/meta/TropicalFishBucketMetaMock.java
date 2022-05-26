package be.seeseemelk.mockbukkit.inventory.meta;

import org.apache.commons.lang3.Validate;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;

public class TropicalFishBucketMetaMock extends ItemMetaMock implements TropicalFishBucketMeta
{

	private DyeColor patternColor;
	private DyeColor bodyColor;
	private TropicalFish.Pattern pattern;

	public TropicalFishBucketMetaMock()
	{
		super();
	}

	public TropicalFishBucketMetaMock(TropicalFishBucketMeta meta)
	{
		super(meta);

		this.patternColor = meta.getPatternColor();
		this.bodyColor = meta.getBodyColor();
		this.pattern = meta.getPattern();
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
	public @NotNull TropicalFishBucketMetaMock clone()
	{
		TropicalFishBucketMetaMock clone = (TropicalFishBucketMetaMock) super.clone();
		clone.patternColor = this.patternColor;
		clone.bodyColor = this.bodyColor;
		clone.pattern = this.pattern;
		return clone;
	}

}
