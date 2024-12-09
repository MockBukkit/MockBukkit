package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Mock implementation of a {@link Banner}.
 *
 * @see TileStateMock
 */
public class BannerStateMock extends TileStateMock implements Banner
{

	private @NotNull DyeColor baseColor;
	private @NotNull List<Pattern> patterns = new ArrayList<>();
	private @Nullable Component customName;

	/**
	 * Constructs a new {@link BannerStateMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#BANNERS}
	 *
	 * @param material The material this state is for.
	 */
	public BannerStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Tag.BANNERS);
		baseColor = materialToDyeColor(material);
	}

	/**
	 * Constructs a new {@link BannerStateMock} for the provided {@link Block}.
	 * Only supports materials in {@link Tag#BANNERS}
	 *
	 * @param block The block this state is for.
	 */
	protected BannerStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Tag.BANNERS);
		baseColor = materialToDyeColor(block.getType());
	}

	/**
	 * Constructs a new {@link BannerStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BannerStateMock(@NotNull BannerStateMock state)
	{
		super(state);
		this.baseColor = state.baseColor;
		this.patterns.addAll(state.patterns);
		this.customName = state.customName;
	}

	@Override
	public @NotNull BannerStateMock getSnapshot()
	{
		return new BannerStateMock(this);
	}

	@Override
	public @NotNull BannerStateMock copy()
	{
		return new BannerStateMock(this);
	}

	@Override
	public @NotNull DyeColor getBaseColor()
	{
		return this.baseColor;
	}

	@Override
	public void setBaseColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color, "Color cannot be null");
		this.baseColor = color;
	}

	@Override
	public @NotNull List<Pattern> getPatterns()
	{
		return new ArrayList<>(this.patterns);
	}

	@Override
	public void setPatterns(@NotNull List<Pattern> patterns)
	{
		Preconditions.checkNotNull(patterns, "Patterns cannot be null");
		this.patterns = new ArrayList<>(patterns);
	}

	@Override
	public void addPattern(@NotNull Pattern pattern)
	{
		Preconditions.checkNotNull(pattern, "Pattern cannot be null");
		this.patterns.add(pattern);
	}

	@Override
	public @NotNull Pattern getPattern(int i)
	{
		return this.patterns.get(i);
	}

	@Override
	public @NotNull Pattern removePattern(int i)
	{
		return this.patterns.remove(i);
	}

	@Override
	public void setPattern(int i, @NotNull Pattern pattern)
	{
		Preconditions.checkNotNull(pattern, "Pattern cannot be null");
		this.patterns.set(i, pattern);
	}

	@Override
	public int numberOfPatterns()
	{
		return this.patterns.size();
	}

	@Override
	public @Nullable Component customName()
	{
		return this.customName;
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		this.customName = customName;
	}

	@Override
	public @Nullable String getCustomName()
	{
		return this.customName == null ? null : LegacyComponentSerializer.legacySection().serialize(this.customName);
	}

	@Override
	public void setCustomName(@Nullable String name)
	{
		this.customName = name == null ? null : LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof BannerStateMock that)) return false;
		if (!super.equals(o)) return false;
		return baseColor == that.baseColor && Objects.equals(patterns, that.patterns) && Objects.equals(customName, that.customName);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), baseColor, patterns, customName);
	}

	static DyeColor materialToDyeColor(Material material)
	{
		if (material == null)
		{
			return DyeColor.WHITE;
		}

		return switch (material)
		{
			case WHITE_BANNER -> DyeColor.WHITE;
			case ORANGE_BANNER -> DyeColor.ORANGE;
			case MAGENTA_BANNER -> DyeColor.MAGENTA;
			case LIGHT_BLUE_BANNER -> DyeColor.LIGHT_BLUE;
			case YELLOW_BANNER -> DyeColor.YELLOW;
			case LIME_BANNER -> DyeColor.LIME;
			case PINK_BANNER -> DyeColor.PINK;
			case GRAY_BANNER -> DyeColor.GRAY;
			case LIGHT_GRAY_BANNER -> DyeColor.LIGHT_GRAY;
			case CYAN_BANNER -> DyeColor.CYAN;
			case PURPLE_BANNER -> DyeColor.PURPLE;
			case BLUE_BANNER -> DyeColor.BLUE;
			case BROWN_BANNER -> DyeColor.BROWN;
			case GREEN_BANNER -> DyeColor.GREEN;
			case RED_BANNER -> DyeColor.RED;
			case BLACK_BANNER -> DyeColor.BLACK;
			default -> throw new IllegalArgumentException("Unknown banner material " + material.name());
		};
	}

}
