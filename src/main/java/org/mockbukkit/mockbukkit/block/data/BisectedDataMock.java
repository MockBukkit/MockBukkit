package org.mockbukkit.mockbukkit.block.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.data.decoder.EnumPropertyDecode;
import org.mockbukkit.mockbukkit.block.data.encoder.PropertyEncoder;

import java.util.Locale;

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
	public static final class HalfEncoder implements PropertyEncoder<Half>
	{
		public static final HalfEncoder INSTANCE = new HalfEncoder();

		@Override
		public @Nullable Object encode(@Nullable Bisected.Half half)
		{
			return switch (half)
			{
				case null -> null;
				case BOTTOM -> MINECRAFT_BOTTOM;
				case TOP -> MINECRAFT_UPPER;
			};
		}

		@Override
		public @NotNull Class<Half> getValueType()
		{
			return Half.class;
		}

	}

	public static final class HalfDecoder extends EnumPropertyDecode<Half>
	{
		public static final HalfDecoder INSTANCE = new HalfDecoder();

		private HalfDecoder()
		{
			super(Half.class);
		}

		@Override
		public @Nullable Half decode(@Nullable Object value)
		{
			if (value instanceof String input)
			{
				String name = input.toLowerCase(Locale.ROOT);

				// Try the minecraft names
				switch (name)
				{
					case MINECRAFT_UPPER: return Half.TOP;
					case MINECRAFT_BOTTOM: return Half.BOTTOM;
					default:break;
				}
			}

			return super.decode(value);
		}

	}
}
