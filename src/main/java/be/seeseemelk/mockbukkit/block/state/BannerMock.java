package be.seeseemelk.mockbukkit.block.state;

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

/**
 * Mock implementation of {@link Banner}.
 */
public class BannerMock extends TileStateMock implements Banner
{

	private DyeColor baseColor;
	private @NotNull List<Pattern> patterns = new ArrayList<>();
	private @Nullable Component customName;

	public BannerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Tag.BANNERS);
	}

	protected BannerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Tag.BANNERS);
	}

	protected BannerMock(@NotNull BannerMock state)
	{
		super(state);
		this.baseColor = state.baseColor;
		this.patterns.addAll(state.patterns);
		this.customName = state.customName;
	}

	@Override
	public @NotNull BannerMock getSnapshot()
	{
		return new BannerMock(this);
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

}
