package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BannerPatternLayers;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
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

	/**
	 * Constructs a new {@link BannerMetaMock}.
	 */
	public BannerMetaMock()
	{
		super();
	}

	public BannerMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link BannerMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public BannerMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull List<Pattern> getPatterns()
	{
		if (has(DataComponentTypes.BANNER_PATTERNS))
		{
			return get(DataComponentTypes.BANNER_PATTERNS).patterns();
		}
		return List.of();
	}

	@Override
	public void setPatterns(@NotNull List<Pattern> patterns)
	{
		Preconditions.checkNotNull(patterns);
		set(DataComponentTypes.BANNER_PATTERNS, BannerPatternLayers.bannerPatternLayers().addAll(patterns).build());
	}

	@Override
	public void addPattern(@NotNull Pattern pattern)
	{
		Preconditions.checkNotNull(pattern);
		List<Pattern> previous = getPatterns();
		BannerPatternLayers.Builder builder = BannerPatternLayers.bannerPatternLayers();
		builder.addAll(previous);
		builder.add(pattern);
		set(DataComponentTypes.BANNER_PATTERNS, builder.build());
	}

	@Override
	public @NotNull Pattern getPattern(int i)
	{
		return getPatterns().get(i);
	}

	@Override
	public @NotNull Pattern removePattern(int i)
	{
		List<Pattern> previous = new ArrayList<>(getPatterns());
		Pattern removed = previous.remove(i);
		set(DataComponentTypes.BANNER_PATTERNS, BannerPatternLayers.bannerPatternLayers(previous));
		return removed;
	}

	@Override
	public void setPattern(int i, @NotNull Pattern pattern)
	{
		List<Pattern> previous = new ArrayList<>(getPatterns());
		previous.set(i, pattern);
		set(DataComponentTypes.BANNER_PATTERNS, BannerPatternLayers.bannerPatternLayers(previous));
	}

	@Override
	public int numberOfPatterns()
	{
		return getPatterns().size();
	}

	@Override
	public @NotNull BannerMetaMock clone()
	{
		return (BannerMetaMock) super.clone();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized BannerMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the BannerMetaMock class.
	 */
	public static @NotNull BannerMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		BannerMetaMock serialMock = new BannerMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "BANNER";
	}

}
