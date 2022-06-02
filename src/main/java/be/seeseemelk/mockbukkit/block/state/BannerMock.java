package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BannerMock extends TileStateMock implements Banner
{

	private DyeColor baseColor;
	private List<Pattern> patterns;

	public BannerMock(@NotNull Material material)
	{
		super(material);
		this.patterns = new ArrayList<>();
	}

	protected BannerMock(@NotNull Block block)
	{
		super(block);
		this.patterns = new ArrayList<>();
	}

	protected BannerMock(@NotNull BannerMock state)
	{
		super(state);
		this.baseColor = state.baseColor;
		this.patterns = new ArrayList<>(state.patterns);
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
		this.patterns = new ArrayList<>(patterns);
	}

	@Override
	public void addPattern(@NotNull Pattern pattern)
	{
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
		this.patterns.set(i, pattern);
	}

	@Override
	public int numberOfPatterns()
	{
		return this.patterns.size();
	}

}
