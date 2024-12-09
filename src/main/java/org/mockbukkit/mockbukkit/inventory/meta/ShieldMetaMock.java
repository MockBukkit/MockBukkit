package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.ShieldMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.state.BannerStateMock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShieldMetaMock extends ItemMetaMock implements ShieldMeta, BlockStateMeta
{

	private Banner banner;

	/**
	 * Constructs a new {@link ShieldMetaMock}.
	 */
	public ShieldMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link ShieldMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public ShieldMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if (meta instanceof BlockStateMeta bsMeta && bsMeta.hasBlockState())
		{
			if (bsMeta.getBlockState() instanceof Banner banner)
			{
				this.banner = (Banner) banner.copy();
			}
		}
	}

	@Override
	public @NotNull List<Pattern> getPatterns()
	{
		if (banner == null)
		{
			return new ArrayList<>();
		}

		return banner.getPatterns();
	}

	@Override
	public void setPatterns(@NotNull List<Pattern> patterns)
	{
		if (banner == null)
		{
			if (patterns.isEmpty())
			{
				return;
			}

			banner = getBlockState(null);
		}

		banner.setPatterns(patterns);
	}

	@Override
	public void addPattern(@NotNull Pattern pattern)
	{
		if (banner == null)
		{
			banner = getBlockState(null);
		}

		banner.addPattern(pattern);
	}

	@Override
	public @NotNull Pattern getPattern(int i)
	{
		if (banner == null)
		{
			throw new IndexOutOfBoundsException(i);
		}

		return banner.getPattern(i);
	}

	@Override
	public @NotNull Pattern removePattern(int i)
	{
		if (banner == null)
		{
			throw new IndexOutOfBoundsException(i);
		}

		return banner.removePattern(i);
	}

	@Override
	public void setPattern(int i, @NotNull Pattern pattern)
	{
		if (banner == null)
		{
			throw new IndexOutOfBoundsException(i);
		}

		banner.setPattern(i, pattern);
	}

	@Override
	public int numberOfPatterns()
	{
		if (banner == null)
		{
			return 0;
		}

		return banner.numberOfPatterns();
	}

	@Override
	public @Nullable DyeColor getBaseColor()
	{
		if (banner == null)
		{
			return null;
		}

		return banner.getBaseColor();
	}

	@Override
	public void setBaseColor(@Nullable DyeColor baseColor)
	{
		if (baseColor == null)
		{
			if (banner != null && banner.numberOfPatterns() > 0)
			{
				banner.setBaseColor(DyeColor.WHITE);
			}
			else
			{
				banner = null;
			}
		}
		else
		{
			if (banner == null)
			{
				banner = new BannerStateMock(dyeColorToMaterial(baseColor));
			}

			banner.setBaseColor(baseColor);
		}
	}

	@Override
	protected void deserializeInternal(Map<String, Object> serialized)
	{
		super.deserializeInternal(serialized);

		String baseColor = (String) serialized.get("base-color");
		if (baseColor != null)
		{
			banner = getBlockState(DyeColor.valueOf(baseColor));
		}

		Iterable<?> rawPatternList = (Iterable<?>) serialized.get("patterns");
		if (rawPatternList == null)
		{
			return;
		}

		for (Object obj : rawPatternList)
		{
			Preconditions.checkArgument(obj instanceof Pattern, "Object (%s) in pattern list is not valid", obj.getClass());
			addPattern((Pattern) obj);
		}
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();
		if (banner != null)
		{
			serialized.put("base-color", banner.getBaseColor().toString());

			if (banner.numberOfPatterns() > 0)
			{
				serialized.put("patterns", banner.getPatterns());
			}
		}

		return serialized;
	}

	public static @NotNull ShieldMetaMock deserialize(@NotNull Map<String, Object> serialized)
	{
		ShieldMetaMock shieldMetaMock = new ShieldMetaMock();
		shieldMetaMock.deserializeInternal(serialized);
		return shieldMetaMock;
	}

	@Override
	public boolean hasBlockState()
	{
		return banner != null;
	}

	@Override
	public void clearBlockState()
	{
		banner = null;
	}

	@Override
	public @NotNull BlockState getBlockState()
	{
		if (banner != null) return banner.copy();
		return getBlockState(null);
	}

	@Override
	public void setBlockState(@NotNull BlockState blockState)
	{
		Objects.requireNonNull(blockState);
		Preconditions.checkArgument(blockState instanceof Banner, "Invalid blockState");
		this.banner = (Banner) blockState;
	}

	private static Banner getBlockState(DyeColor color)
	{
		return new BannerStateMock(dyeColorToMaterial(color));
	}

	private static Material dyeColorToMaterial(DyeColor color)
	{
		if (color == null)
		{
			return Material.WHITE_BANNER;
		}

		return switch (color)
		{
			case WHITE -> Material.WHITE_BANNER;
			case ORANGE -> Material.ORANGE_BANNER;
			case MAGENTA -> Material.MAGENTA_BANNER;
			case LIGHT_BLUE -> Material.LIGHT_BLUE_BANNER;
			case YELLOW -> Material.YELLOW_BANNER;
			case LIME -> Material.LIME_BANNER;
			case PINK -> Material.PINK_BANNER;
			case GRAY -> Material.GRAY_BANNER;
			case LIGHT_GRAY -> Material.LIGHT_GRAY_BANNER;
			case CYAN -> Material.CYAN_BANNER;
			case PURPLE -> Material.PURPLE_BANNER;
			case BLUE -> Material.BLUE_BANNER;
			case BROWN -> Material.BROWN_BANNER;
			case GREEN -> Material.GREEN_BANNER;
			case RED -> Material.RED_BANNER;
			case BLACK -> Material.BLACK_BANNER;
			default -> throw new IllegalArgumentException("Unknown banner color " + color.name());
		};
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof ShieldMetaMock that)) return false;
		if (!super.equals(o)) return false;
		return Objects.equals(banner, that.banner);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), banner);
	}

}
