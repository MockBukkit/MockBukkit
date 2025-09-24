package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BisectedDataMock extends BlockDataMock implements Bisected
{
	private static final String LOWER = "lower";
	private static final String UPPER = "upper";

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public BisectedDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link BisectedDataMock} based on an existing {@link BisectedDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected BisectedDataMock(BisectedDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Half getHalf()
	{
		return fromString(this.get(BlockDataKey.HALF_MULTI_BLOCK));
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		this.set(BlockDataKey.HALF_MULTI_BLOCK, toString(half));
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BisectedDataMock clone()
	{
		return new BisectedDataMock(this);
	}

	/**
	 * Convert the half into their respective representation.
	 *
	 * @param half The original half
	 *
	 * @return The string representation.
	 */
	public static String toString(Half half)
	{
		return switch (half)
		{
			case BOTTOM -> LOWER;
			case TOP -> UPPER;
		};
	}

	/**
	 * Convert the half from a string representation.
	 *
	 * @param half The half as string.
	 *
	 * @return The half converted
	 */
	public static Half fromString(String half)
	{
		return switch (half.toLowerCase(Locale.ROOT))
		{
			case LOWER -> Half.BOTTOM;
			case UPPER -> Half.TOP;
			default -> throw new IllegalArgumentException("Unexpected value: " + half.toLowerCase(Locale.ROOT));
		};
	}

	/**
	 * Check if this bisected block occupies only one block.
	 *
	 * @return {@code true} if occupies only one block, otherwise {@code false}.
	 */
	@ApiStatus.Internal
	public static boolean isSingleBlock(BlockDataMock block)
	{
		return Tag.TRAPDOORS.isTagged(block.getMaterial()) || Tag.STAIRS.isTagged(block.getMaterial());
	}

}
