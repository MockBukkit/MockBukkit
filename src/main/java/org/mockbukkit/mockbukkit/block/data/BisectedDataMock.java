package org.mockbukkit.mockbukkit.block.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Function;

public class BisectedDataMock extends BlockDataMock implements Bisected
{

	private static final String MINECRAFT_UPPER = "upper";
	private static final String MINECRAFT_BOTTOM = "lower";

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public BisectedDataMock(@NotNull Material material)
	{
		super(material);

		setHalf(Half.BOTTOM);
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
		return this.get(BlockDataKey.HALF);
	}

	@Override
	public void setHalf(@NotNull Half half)
	{
		this.set(BlockDataKey.HALF, half);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BisectedDataMock clone()
	{
		return new BisectedDataMock(this);
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class HalfEncoder implements Function<Object, Object>
	{
		public static final HalfEncoder INSTANCE = new HalfEncoder();

		@Override
		public Object apply(Object input)
		{
			if (input == null)
			{
				return null;
			}

			if (!(input instanceof Half half))
			{
				return null;
			}

			return switch (half)
			{
				case BOTTOM -> MINECRAFT_BOTTOM;
				case TOP -> MINECRAFT_UPPER;
			};
		}

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class HalfDecoder implements Function<String, Object>
	{
		public static final HalfDecoder INSTANCE = new HalfDecoder();

		@Nullable
		@Override
		public Half apply(@Nullable String input)
		{
			if (input == null)
			{
				return null;
			}

			String name = input.toLowerCase(Locale.ROOT);

			// Try the minecraft names
			switch (name)
			{
				case MINECRAFT_UPPER: return Half.TOP;
				case MINECRAFT_BOTTOM: return Half.BOTTOM;
				default:break;
			}

			// Try the bukkit names
			for (Half half : Half.values())
			{
				if (half.name().equalsIgnoreCase(name))
				{
					return half;
				}
			}

			return null;
		}

	}
}
