package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of an {@link BannerMeta}.
 *
 * @see ItemMetaMock
 */
public class BannerMetaMock extends ItemMetaMock implements BannerMeta
{

	private List<Pattern> patterns;

	/**
	 * Constructs a new {@link BannerMetaMock}.
	 */
	public BannerMetaMock()
	{
		super();

		this.patterns = new ArrayList<>();
	}

	/**
	 * Constructs a new {@link BannerMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public BannerMetaMock(@NotNull BannerMeta meta)
	{
		super(meta);

		this.patterns = new ArrayList<>(meta.getPatterns());
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.patterns.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BannerMeta meta))
			return false;
		return super.equals(obj) && this.patterns.equals(meta.getPatterns());
	}

	@Override
	public @NotNull BannerMetaMock clone()
	{
		BannerMetaMock clone = (BannerMetaMock) super.clone();

		clone.patterns = new ArrayList<>(this.patterns);

		return clone;
	}

}
