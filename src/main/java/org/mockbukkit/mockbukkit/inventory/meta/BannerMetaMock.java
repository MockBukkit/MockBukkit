package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		{
			return false;
		}
		return super.equals(obj) && this.patterns.equals(meta.getPatterns());
	}

	@Override
	public @NotNull BannerMetaMock clone()
	{
		BannerMetaMock clone = (BannerMetaMock) super.clone();

		clone.patterns = new ArrayList<>(this.patterns);

		return clone;
	}

	@Override
	protected void deserializeInternal(Map<String, Object> serialized)
	{
		super.deserializeInternal(serialized);
		this.setPatterns(((List<Map<String, Object>>) serialized.get("patterns")).stream().map(Pattern::new).toList());
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized BannerMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the BannerMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull BannerMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		BannerMetaMock serialMock = new BannerMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	/**
	 * Serializes the properties of an BannerMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the BannerMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("patterns", this.patterns.stream().map(Pattern::serialize).toList());
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "BANNER";
	}

}
